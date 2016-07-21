var REST_HOST = 'http://localhost:4567/api/';
var TOKEN = "";
var USER_INFO;

var SaveUser = function(user){
  USER_INFO = user;
  localStorage.setItem('user', JSON.stringify(user));
}

var SetupTransactions = function(clean) {
  clean = clean || false;

  if(clean) {
    $('#login-container').hide();
    $('#tabs-container').removeClass('hide');
  }

  $.ajax({
    url: REST_HOST + 'game/list',
    method: 'get',
    data: {username : USER_INFO.username}
    dataType: 'json',
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
  if(userData && userData.username) {
    SaveUser(userData);
    SetupUserInfo();
    SetupTransactions(true);
  }
  else {
    swal('Lo Sentimos', 'Se ha producido un error intentando iniciar sesion. Porfavor, intente nuevamete revisando su usuario y contrasena. ', 'error');
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
      if(data.status === 'Win') {
        swal('Muchas Felicidades!!!', 'Usted ha ganado el PALE!', 'success');
      }
      else if(data.status === 'Lose') {
        swal('Lo sentimos...', 'Al parecer usted no ha ganado este juego. Intente nuevamente mas adelante.', 'error');
      }
      else {
        swal('Lo sentimos, se ha producido un error', 'Ha habido un error en la transaccion. No podemos procesarle correctamente.', 'error');
      }
      if(data.user){
        SaveUser(data.user);
      }
      //clear inputs:
      $('#pale-form input').val('');
    }
  });
}

var PlayLoto = function() {
  var data = {
    type: 'LOTO',
    username: USER_INFO.username,
    nums : $('#loto-nums').val(),
    bet: 50 //static xD
  };


  $.ajax({
    url: REST_HOST + 'game/create',
    dataType: 'json',
    method: 'post',
    data: data,
    success: function(data) {
      if(data.status === 'Win') {
        swal('Muchas Felicidades!!!', 'Usted ha ganado el LOTO!', 'success');
      }
      else if(data.status === 'Lose') {
        swal('Lo sentimos...', 'Al parecer usted no ha ganado este juego. Intente nuevamente mas adelante.', 'error');
      }
      else {
        swal('Lo sentimos, se ha producido un error', 'Ha habido un error en la transaccion. No podemos procesarle correctamente.', 'error');
      }

      if(data.user){
        SaveUser(data.user);
      }
      //clear inputs:
      $('#loto-form input').val('');
    }
  });
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
  });

  $('#loto-form').submit(function(e) {
    e.preventDefault();
    PlayLoto();
  });
});
