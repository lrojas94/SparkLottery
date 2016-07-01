
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1><i class="fa fa-trophy"></i> El numero ganador es:
                <small>
                    <#list winningNumbers as num>
                        ${num} |
                    </#list>
                </small>
            </h1><!-- Title -->
        </div>
        <div class="col-xs-12">
            <#if didWin>
                <h4>
                    Usted ha sido agradraciado con el Premio! Su cuenta cuenta ahora con:
                    <span class="label label-success">${user.getAccount().getBalance()?string.currency}</span>!
                    <br><br>
                    Es importante para nosotros que deje un mensaje. Abajo tendra un acceso a un link que le permitira
                    contarle al mundo lo suertudo que es!
                </h4>
                <a href="/winner/add" class="btn btn-success btn-block">Que suertudo soy!</a>
            <#else>
                <p>
                    Usted no ha tenido suerte. No obstante, procure intenter nuevamente y tendra
                    la posibilidad de ganar una vez mas!
                    <br><br>
                    Puede intentar jugar nuevamente en el link a continuacion:
                </p>
                <a href="/game/play" class="btn btn-info btn-block">Intentar de nuevo</a>
            </#if>

        </div>
    </div>
</div>
