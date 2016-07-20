var REST_HOST = 'http://localhost:4567/api/';
var TOKEN = "";
var USER_INFO;

var SetupTransactions = function(clean) {
  clean = clean || false;

  if(clean) {
    $('#login-container').hide();
    $('#tabs-container').removeClass('hide');
  }

  $.ajax({
    url: REST_HOST + 'game/list',
    method: 'get',
    dataType: 'json',
    data: { username : 'data' },
    success: function(transactionData) {
      var tableBody = $('#tabs-container').find('table tbody');
      $(tableBody).html('');
      for(trans of transactionData) {
        var rowData =  `
        <tr>
        <td>${trans.emitDate}</td>
        <td>RD$ ${trans.betAmount}</td>
        <td>${trans.issuedIn.type}</td>
        <td>${trans.numbers}</td>
        <td>${typeof trans.winnerIn !== 'undefined' && trans.winnerIn !== null ? 'Ganador' : 'Perdedor'}</td>
        </tr>`;
        console.log(trans.winnerIn);
        $(tableBody).append(rowData);
      }
    }
  });
}

var SetupUserInfo = function() {
  var userData = USER_INFO;
  console.log(userData);
  $('#user-info').find('h4').text(`Bienvenido ${userData.firstName} ${userData.lastName}`);
  $('#user-info').find('p').text(`Balance actual: RD$ ${ userData.account ? userData.account.balance : 0 }`);
}

var UserLogin = function(userData) {
  if(userData.username) {
    localStorage.setItem('user', JSON.stringify(userData));
    // Login was successful!!
    USER_INFO = userData;
    SetupUserInfo();
    SetupTransactions(true);

  }
}

var PlayPale = function() {
  var data = {
    type: 'PALE',
    username: USER_INFO.username,
    nums : $('#numA').val() + ',' + $('#numB').val() + ',' + $('#numC').val(),
    bet: $('#atb').val()
  };


  $.ajax({
    url: REST_HOST + 'game/create',
    dataType: 'json',
    method: 'post',
    data: data,
    success: function(data) {
       Materialize.toast(data, 4000);
    }
  })
}

$(function() {
  if(localStorage.user){
    USER_INFO = JSON.parse(localStorage.user);
    SetupTransactions(true);
    SetupUserInfo();
  }

  $('#logout-btn').click(function() {
    localStorage.removeItem('user');
    location.reload();
  })

  $('#trans-link').click(function() {
    SetupTransactions(false);
  });

  $('#login-form').submit(function(e) {
    e.preventDefault();

    $.ajax({
      url: REST_HOST + 'login',
      data: $(this).serialize(),
      method: 'post',
      dataType: 'json',
      success: function(userData){
        console.log(userData);
        UserLogin(userData);
      },
      error: function(e) {
        // alert("So sad :( Ther was an error...");
        UserLogin();
      }
    });
  });

  $('#pale-form').submit(function(e) {
    e.preventDefault();
    PlayPale();
  })

});
