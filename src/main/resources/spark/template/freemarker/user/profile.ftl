<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1>${User}
                <br>
                <small>
                ${User.getEmail()}
                </small>
            </h1><!-- Username -->
        </div>
        <div class="col-xs-12">
            <p>
            <#if user?? && User == user>
                Fondos: ${user.getAccount().getBalance()?string.currency}
                <br>
                <a href="/user/addFunds" class="btn btn-success"><i class="fa fa-plus"></i> Agregar fondos</a>
            </#if>
            </p>
        </div>
        <div class="col-xs-12">
            <h3>Tickets jugados</h3>
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