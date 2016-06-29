<#if User??>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h2>${User}
            </h2><!-- Username -->
            <span class="badge">Correo Electronico: ${User.getEmail()}</span>
        </div>
        <#if user?? && User == user && User.getUsername() != "admin">
        <div class="col-xs-12">
            <h3>
                Fondos: ${User.getAccount().getBalance()?string.currency}
                <a href="/user/${User.getId()}/addfunds" class="btn btn-success pull-right"><i class="fa fa-plus"></i> Agregar fondos</a>
            </h3>
        </div>
        </#if>
        <div class="col-xs-12">
            <br>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Tickets jugados
                </div>
                <div class="panel-body">
                    <table class="table table-hover table-responsive">
                        <thead>
                        <th>Juego</th>
                        <th>Numero de Ticket</th>
                        <th>Monto apostado</th>
                        </thead>
                        <tbody>
                        <!-- For ticket in ticket -->
                            <#list User.getTickets() as ticket>
                            <tr>
                                <td>ticket.getIssuedIn().getType()</td>
                                <td>0000000</td>
                                <td>ticket.getBetAmount()</td>
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

