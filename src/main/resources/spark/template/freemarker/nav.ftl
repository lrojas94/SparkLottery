<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand topnav" href="/">SparkLoto</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
            <#if !user??>
                <li>
                    <a href="/user/create">Registrate</a>
                </li>
                <li>
                    <a href="/user/login">Inicia Sesion</a>
                </li>
            <#else >
                <li class="disabled">
                    <a href="#">${user}</a>
                </li>
                <li>
                    <a href="/user/${user.getId()}">Perfil</a>
                </li>
            </#if>
                <li>
                    <a href="#services">Ganadores</a>
                </li>
                <li>
                    <a href="#contact">Informacion</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>