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

                <#if user.getAdmin()>
                    <li>
                        <a href="/admin">Admin</a>
                    </li>
                </#if>

                <li>
                    <a href="/game/play">Probar mi suerte!</a>
                </li>
                <#if user.canPublishInWinners()>
                    <li>
                        <a href="/winner/add">Publicar Comentario Ganador!</a>
                    </li>
                </#if>
                <li>
                    <a href="/user/logout">Cerrar sesion</a>
                </li>
            </#if>
                <li>
                    <a href="/winner/list">Ganadores</a>
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

<#if template_name?? && template_name == "index.ftl">
<!-- Header -->
<a name="about"></a>
<div class="intro-header">
    <div class="container">

        <div class="row">
            <div class="col-lg-12">
                <div class="intro-message">
                    <h1>SparkLoto</h1>
                    <h3>Loteria Automatizada</h3>
                    <hr class="intro-divider">
                    <ul class="list-inline intro-social-buttons">
                        <#if !user??>
                            <li>
                                <a href="/user/login" class="btn btn-default btn-lg"><i class="fa fa-user"></i> Inicia Sesion</a>
                            </li>
                            <li>
                                <a href="/user/create" class="btn btn-default btn-lg"><i class="fa fa-check"></i> Registrate</a>
                            </li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>

    </div>
    <!-- /.container -->

</div>
<!-- /.intro-header -->
<#else>
<div class="container-fluid">
    <div class="jumbotron" id="header-small">
        <h1>SparkLoto</h1>
    </div>
</div>
</#if>