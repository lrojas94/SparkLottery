<#if User??>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h2>Agregando fondos para: ${User}
                <br>
                <span class="badge">${User.getEmail()}</span>
            </h2><!-- Username -->
        </div>
        <div class="col-xs-12">
            <!-- ON THE WORKS.. -->
            <#if error??>
            <div class="col-xs-12">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </div>
            </#if>
            <form action="/user/${User.getId()}/addfunds" method="post" role="form">
                <legend>Metodo de Pago</legend>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-group">
                            <label for="ammountToAdd">Monto a agregar: </label>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">$</span>
                                <input type="number" min="0.01" step="0.01" name="ammountToAdd" class="form-control" id="ammountToAdd">
                            </div>
                            </div>
                    </div>

                    <div class="col-xs-6">
                        <div class="form-group">
                            <label for="creditCard">Numero de Tarjeta de Credito: </label>
                            <input type=text
                                   pattern="\b(?:4[0-9]{12}(?:[0-9]{3})?|5[12345][0-9]{14}|3[47][0-9]{13}|3(?:0[012345]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35[0-9]{3})[0-9]{11})\b"
                                   class="form-control" name="creditCard" id="creditCard" placeholder="Ingrese los 16 numeros de su tarjeta." required>
                        </div>
                    </div>
                    <div class="col-xs-2">
                        <div class="form-group">
                            <label for="month">Mes: </label>
                            <input type="number" class="form-control" name="month" id="month" placeholder="Mes de Venc." required>
                        </div>
                    </div>
                    <div class="col-xs-2">
                        <div class="form-group">
                            <label for="year">Ano: </label>
                            <input type="number" class="form-control" name="year" id="year" placeholder="Ano de Venc." required>
                        </div>
                    </div>
                    <div class="col-xs-2">
                        <div class="form-group">
                            <label for="cvc">CVC: </label>
                            <input type="number" class="form-control" name="cvc" id="cvc" placeholder="CVC" required>
                        </div>
                    </div>
                    <div class="col-xs-12">
                        <div class="form-group">
                            <label for="cctype">Tipo de Tarjeta de Credito</label>
                            <select class="form-control" id="cctype" name="cctype">
                                <option selected="selected" value="VISA">Visa</option>
                                <option value="MASTERCARD">MasterCard</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-group">
                            <label for="msg">Mensaje <small>(opcional)</small></label>
                            <textarea name="msg" id="msg" class="form-control" rows="5"></textarea>
                        </div>
                    </div>
                    <div class="col-xs-12">
                        <button type="submit" class="btn btn-success btn-block"><i class="fa fa-plus"></i> Agregar Fondos</button>
                    </div>
                </div>
            </form>
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