<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <img class="img img-responsive center-block" src="http://vignette3.wikia.nocookie.net/logopedia/images/e/e6/Lotto_Poland_2010.png/revision/latest?cb=20100722174748" alt="">
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
        <form action="/game/loto" method="post" role="form">
            <div class="col-xs-12">
                <legend>Formulario para jugar el LOTO</legend>
                <div class="row">
                    <div class="col-xs-12">
                        <p>Usted cuenta con <span class="label label-default">RD${user.getAccount().getBalance()}</span>. Si usted ganara este juego
                            tendria un total de <span class="label label-success">RD$${prize!"1,000,000"}</span>
                            <br>
                            Recuerde que para jugar solo ha de escribir 20 numeros separados por coma en la siguiente
                            caja de entradas. Si logra acertar 5, automaticamente se llevara el premio!
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label for="nums"><i class="fa fa-money"></i> Numeros a jugar!</label>
                    <input type="text" title="Escriba 20 numeros separados por comas y menores a 100" value="${nums!""}"
                           pattern="((0*(?:[1-9][0-9]?))(,\s*(0*(?:[1-9][0-9]?))){19})" class="form-control" name="nums" id="nums" placeholder="1,2,3,..">
                </div>
            </div>
            <div class="col-xs-12">
                <button type="submit" class="btn btn-success btn-block">Jugar!</button>
            </div>
        </form>
    </div>
</div>