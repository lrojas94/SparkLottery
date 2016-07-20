var REST_HOST = 'http://localhost:4567/api/';

var UserLogin = function(userData) {
  // if(userData.username) {
    //Login was successful!!
    $('#login-container').remove();
    $('#tabs-container').removeClass('hide');
    $.ajax({
      url: REST_HOST + 'game/list',
      method: 'get',
      dataType: 'json',
      data: { some : 'data' },
      success: function(transactionData) {
        var tableBody = $('#tabs-container').find('table tbody');
        for(trans of transactionData) {
          var rowData =  `
          <tr>
          <td>${trans.emitDate}</td>
          <td>${trans.betAmmount}</td>
          <td>${trans.issuedIn.type}</td>
          <td>${trans.numbers}</td>
          <td>${trans.winnerIn !== 'undefined' && trans.winnerIn !== null ? 'GANADOR' : 'PERDEDOR'}</td>
          </tr>`;
          $(tableBody).append(rowData);
        }
      }
    });
  // }
}

$(function() {

  $('#login-form').submit(function(e) {

    e.preventDefault();

    $.ajax({
      url: REST_HOST + 'login',
      data: $(this).serialize(),
      method: 'post',
      dataType: 'json',
      success: function(userData){
        console.log(userData);
        UserLogin();
      },
      error: function(e) {
        // alert("So sad :( Ther was an error...");
        UserLogin();
      }
    });
  });

});
