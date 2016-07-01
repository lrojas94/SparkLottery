<div class="container">
    <h2>Admin</h2>
    <h3>${user.getFirstName()}</h3>

    <table class="table table-hover table-responsive">
        <thead>
            <th>Name</th>
            <th>Username</th>
            <th>Delete</th>
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
                </tr>
            </#list>
        </tbody>
    </table>
</div>