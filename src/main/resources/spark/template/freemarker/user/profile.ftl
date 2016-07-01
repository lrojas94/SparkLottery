<#if User??>
<div class="container">
    <#if message?? >
    <div class="row">
        <div class="col-xs-12">
                <div class="alert alert-${message_type!"success"}">${message}</div>
        </div>
    </div>
    </#if>
    <div class="row">
        <div class="col-xs-12">
            <h2>${User}
            </h2><!-- Username -->
            <span class="badge">Correo Electronico: ${User.getEmail()}</span>
        </div>

        <#if user?? && (User == user || user.getAdmin()) && User.getUsername() != "admin">
        <#assign account=User.getAccount()>
        <div class="col-xs-12">
            <h3>
                Fondos: ${User.getAccount().getBalance()?string.currency}
            </h3>
            <div class="row">
                <div class="col-xs-6">
                    <a href="/user/${User.getId()}/addfunds" class="btn btn-success btn-block"><i class="fa fa-plus"></i> Agregar fondos</a>
                </div>
                <div class="col-xs-6">

                    <a href="/user/transferfunds" class="btn btn-info btn-block"><i class="fa fa-plus"></i> Transferir fondos</a>
                </div>
            </div>
            <br>
        </div>
            <br>
        <div class="col-xs-12">
            <div class="panel panel-primary">
                <div class="panel-heading">Transacciones realizadas</div>
                <div class="panel-body">
                    <table class="table table-hover table-responsive" id="show-user-transactions">
                        <thead>
                        <td hidden>Id</td>
                        <th>Monto Transferido</th>
                        <th>Metodo de Transferencia</th>
                        <th>Descripcion</th>
                        <th>Fecha</th>
                        <th>Mensaje adjunto</th>
                        </thead>
                        <tbody>
                        <!-- For ticket in ticket -->
                            <#list account.getTransactions() as trans>
                            <tr>
                                <td hidden>${trans.getId()}</td>
                                <td>${trans.getAmmount()?string.currency}</td>
                                <td>${trans.getMethod()}</td>
                                <td>${trans.getDescription()}</td>
                                <td>${trans.getIssuedDate()!"DESCONOCIDO"}</td>
                                <td>${trans.getMessage()}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        </#if>
        <div class="col-xs-12">
            <br>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Tickets jugados
                </div>
                <div class="panel-body">
                    <table class="table table-hover table-responsive" id="show-user-tickets">
                        <thead>
                        <th>Juego</th>
                        <th>Numero de Ticket</th>
                        <th>Monto apostado</th>
                        </thead>
                        <tbody>
                        <!-- For ticket in ticket -->
                            <#list User.getTickets() as ticket>
                            <tr>
                                <td>${ticket.getIssuedIn().getType()}</td>
                                <td>${ticket.getNumbers()}</td>
                                <td>${ticket.getBetAmount()?string.currency}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</div>
<#else>
<div class="container">
    <div class="row">
        <br>
        <div class="alert alert-danger">USUARIO NO ENCONTRADO</div>
    </div>
</div>
</#if>

