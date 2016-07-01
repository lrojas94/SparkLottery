<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <img class="img img-responsive center-block" src="/img/pale.png" alt="">
            <br><br><br>
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

    <div class="row">
        <form action="/game/pale" method="post" role="form">
            <div class="col-xs-12">
                <legend>Formulario para jugar el PALE</legend>
                <div class="row">
                    <div class="col-xs-12">
                        <p>Usted cuenta con <span class="label label-default">RD$${user.getAccount().getBalance()}</span>. Si usted ganara este juego
                            recibiria un total de <span class="label label-success">RD$${multiplier!"1,000.00"}</span> por cada peso apostado.
                            <br>
                            Recuerde que para jugar solo ha de escribir 3 numeros en las siguientes casillas.
                            Si es capaz de acertar los 3, entonces sera el ganador de este asombroso premio!
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-group">
                                <label for="ammount">Cantidad a Apostar</label>
                                <input type="number" min="1" step="0.01" max="${user.getAccount().getBalance()}"
                                       title="Es necesario establecer la cantidad a apostar"
                                       name="ammount" id="ammount" class="form-control" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <label for="nums"><i class="fa fa-money"></i> Numeros a jugar!</label>
                        </div>

                        <div class="col-xs-4">
                            <input type="number" value="${numa!""}" min="0" step="1" max="100" class="form-control" name="numa" id="numa" placeholder="">
                        </div>
                        <div class="col-xs-4">
                            <input type="number" value="${numb!""}" min="0" step="1" max="100" class="form-control" name="numb" id="numb" placeholder="">
                        </div>
                        <div class="col-xs-4">
                            <input type="number" value="${numc!""}" min="0" step="1" max="100" class="form-control" name="numc" id="numc" placeholder="">
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <button type="submit" class="btn btn-success btn-block">Jugar!</button>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>