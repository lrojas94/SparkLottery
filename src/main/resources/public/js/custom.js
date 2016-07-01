/**
 * Created by MEUrena on 6/30/16.
 */

// DataTables

var Helpers = function(){
    var languageForDataTable = {
        search : "Buscar Entradas",
        paginate: {
            first:    'Inicio',
            previous: 'Anterior',
            next:     'Siguiente',
            last:     'Ultimo'
        },
        info : "Mostrando del _START_ al _END_ de un total de _TOTAL_ Entradas",
        lengthMenu : "Mostrar _MENU_ Entradas",
        emptyTable: "No hay datos a mostrar.",
        infoEmpty: ""
    };

    $('.submit-form').click(function (e) {
        e.preventDefault();
        var form = $(this).closest('form');
        console.log(form);
        form.submit();
    });

    var FormDataToJson = function(form){
        var formData = $(form).serializeArray();
        var jsonFormData = {};
        for(i in formData){
            jsonFormData[formData[i].name] = formData[i].value;
        }
        return jsonFormData;
    };

    return {
        FormDataToJson : FormDataToJson,
        DTLanguage: languageForDataTable
    };
};

var Winners = function(){
    var HelpersNamespace = Helpers();
    $('#winner-table').dataTable({
        language: HelpersNamespace.DTLanguage,
        pageLength: 5,
        lengthMenu: [5,25,50],
        searching: false,
        search: false,
        ordering: false,
        saveState: true,
        serverSide: true,
        ajax: {
            url : "/datatable/winners",
            type: "POST",
            data: function(data){
                return JSON.stringify(data);
            }
        },
        columnDefs: [
            {
                targets : 0,
                data: function(row,type,val,meta){
                    var winner = row;
                    var winnerContainer = $('#winner-container-template').clone();
                    winnerContainer.attr("id","");
                    //add title:
                    winnerContainer.find('.winner-heading').append(winner.player.firstName + " " + winner.player.lastName);
                    winnerContainer.find('.winner-lead').append(winner.comment);
                    var img = winnerContainer.find('.img-responsive');
                    img.attr("src",winner.path);

                    return winnerContainer;
                },
                render: function(row,type,val,meta){
                    return row.html();
                }
            }
        ]
    });
};

var Transactions = function(){ 
    //Init data tables for user related stuff:
    var HelpersNamespace = Helpers();

    $('#show-user-transactions').dataTable({
        fixedHeader : {
            header: true
        },
        language : HelpersNamespace.DTLanguage,
        lengthMenu : [5,10,25,50],
        pageLength : 5
    });

    $('#show-user-tickets').dataTable({
        fixedHeader : {
            header: true
        },
        language : HelpersNamespace.DTLanguage,
        lengthMenu : [5,10,25,50],
        pageLength : 5
    });

    return {};
};

$(function() {
    var WinnerNamespace = Winners();
    var TransactionNamespace = Transactions();
    var HelpersNamespace = Helpers();
});


// bootstrap-fileinput

$("#uploaded_file").fileinput({
    overwriteInitial: true,
    maxFileSize: 5120,
    showClose: false,
    showCaption: false,
    browseLabel: '',
    removeLabel: '',
    browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
    removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
    removeTitle: 'Cancel or reset changes',
    elErrorContainer: '#kv-avatar-errors-1',
    msgErrorClass: 'alert alert-block alert-danger',
    defaultPreviewContent: '<img src="/img/default_avatar_male.jpg" alt="Your Avatar" style="width:160px">',
    layoutTemplates: {main2: '{preview} {remove} {browse}'},
    allowedFileExtensions: ["jpg", "png", "gif"]
});