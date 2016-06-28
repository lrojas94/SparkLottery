<div class="container">
    <#if error_message?? >
        <br>
        <div class="row">
            <div class="col-xs-12">
                <div class="alert alert-danger">
                    ${error_message}
                </div>
            </div>
        </div>
    </#if>
    <div class="row">
        <div class="col-xs-12">
            <form action="/user/login" method="post" role="form">
                <legend>Inicio de Sesion</legend>

                <div class="form-group">
                    <label for="username"><i class="fa fa-user"></i> Nombre de Usuario</label>
                    <input type="text" class="form-control" name="username" id="username" placeholder="Input...">
                </div>
                <div class="form-group">
                    <label for="password"><i class="fa fa-key"></i> Contrasena</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Input...">
                </div>
                <button type="submit" class="btn btn-primary">Iniciar sesion</button>
            </form>
        </div>
    </div>
</div>