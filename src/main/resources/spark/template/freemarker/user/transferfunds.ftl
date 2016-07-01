<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h2>Transferencia de fondos <br>
                <small>Fondos: RD${user.getAccount().getBalance()?string.currency}</small>
            </h2>

        </div>
    </div>

    <#if errors??>
        <div class="row">
            <div class="col-xs-12">
                <div class="alert alert-danger">
                    ${errors}
                </div>
            </div>
        </div>
    </#if>
    <form action="/user/transferfunds" method="post" role="form">
    	<legend>Formulario de Transferencia de Fondos.</legend>
        <input type="hidden" id="currentAmmount" value="${user.getAccount().getBalance()}">
    	<div class="row">
            <div class="col-xs-6">
                <div class="form-group">
                    <label for="user">Usuario destino</label>
                    <select name="transferTo" id="user" class="form-control">
                    <#list users as u>
                        <option value="${u.getId()}">${u} (${u.getUsername()})</option>
                    </#list>
                    </select>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <label for="ammountToAdd">Cantidad a Transferir</label>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">$</span>
                        <input type="number" min="0.01" step="0.01" max="${user.getAccount().getBalance() - 100.00}" name="ammountToAdd" class="form-control" id="ammountToAdd">
                    </div>

                </div>
            </div>
            <div class="col-xs-12">
                <div class="form-group">
                    <label for="msg">Mensaje <small>(opcional)</small></label>
                    <textarea name="msg" id="msg" class="form-control" rows="5"></textarea>
                </div>
            </div>
            <div class="col-xs-12">
                <button type="submit" class="btn btn-success btn-block">Transferir</button>
            </div>
        </div>

    </form>
</div>