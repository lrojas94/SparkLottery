
<!-- Footer -->
<footer id="footer">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <ul class="list-inline">
                    <li>
                        <a href="/">Inicio</a>
                    </li>
                    <li class="footer-menu-divider">&sdot;</li>
                    <#if user??>
                        <li>
                            <a href="/user/${user.getId()}">Perfil</a>
                        </li>
                    <#else>
                        <li>
                            <a href="/user/login">Iniciar sesion</a>
                        </li>
                        <li class="footer-menu-divider">&sdot;</li>
                        <li>
                            <a href="/user/create">Registrarse</a>
                        </li
                    </#if>
                    <li class="footer-menu-divider">&sdot;</li>
                    <li>
                        <a href="#contact">Informacion</a>
                    </li>
                </ul>
                <p class="copyright text-muted small">Copyright &copy; Luis E. Rojas | Manuel E. Urena | Edward Romero</p>
            </div>
        </div>
    </div>
</footer>