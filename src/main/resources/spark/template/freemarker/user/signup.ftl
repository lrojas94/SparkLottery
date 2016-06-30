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
            <form action="/user/create" method="post" role="form">
                <legend>Registro de Usuario</legend>
                <br>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="username"><i class="fa fa-user"></i> Nombre de Usuario</label>
                        <input required type="text" class="form-control" name="username" id="username" placeholder="Nombre de Usuario...">
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="email"><i class="fa fa-key"></i> Correo Electronico</label>
                        <input required type="email" class="form-control" name="email" id="email" placeholder="e.g: lrojas@gmail.com">
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="password"><i class="fa fa-key"></i> Contrasena</label>
                        <input required type="password" class="form-control" name="password" id="password" placeholder="">
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="password_2">Repetir Contrasena</label>
                        <input required type="password" class="form-control" name="password_2" id="password_2" placeholder="">
                    </div>
                </div>
                <!--  -->
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="firstName"><i class="fa fa-key"></i> Primer Nombre</label>
                        <input required type="text" class="form-control" name="firstName" id="firstName" placeholder="e.g: Pedro">
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="lastName"><i class="fa fa-key"></i> Apellidos</label>
                        <input required type="text" class="form-control" name="lastName" id="lastName" placeholder="e.g: Zahran">
                    </div>
                </div>
                <button type="submit" class="btn btn-info btn-block">Registrarse</button>
            </form>
        </div>
    </div>
</div>