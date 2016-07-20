<div class="container">
    <h2>Admin</h2>
    <h3>${user.getFirstName()}</h3>

    <table class="table table-hover table-responsive">
        <thead>
            <th>Name</th>
            <th>Username</th>
            <th>Delete</th>
            <th>Permisos de Administrador</th>
        </thead>
        <tbody>
            <#list users as u>
                <tr>
                    <td>${u.getFirstName()} ${u.getLastName()}</td>
                    <td>${u.getUsername()}</td>
                    <td>
                    <form method="post" action="/admin/delete/${u.getId()}">
                        <button class="btn btn-danger" type="submit"><span class="glyphicon glyphicon-trash"></span> Delete</button>
                    </form></td>
                    <td>
                        <#if u.getAdmin()>

                            <a class='btn btn-info' href="/admin/removeadmin/${u.getId()}">Deshacer Admin</a>
                        <#else>

                            <a class='btn btn-info' href="/admin/makeadmin/${u.getId()}">Hacer Admin</a>
                        </#if>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>