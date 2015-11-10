<h1>User Details:</h1>

<table>
    <tr>
        <td>User Id:</td>
        <td><?php echo $_SERVER["HTTP_X_REMOTE_USER_ID"]; ?></td>
    </tr>
    <tr>
        <td>Display Name:</td>
        <td><?php echo $_SERVER["HTTP_X_REMOTE_USER_DISPLAY_NAME"]; ?></td>
    </tr>
    <tr>
        <td>Email:</td>
        <td><?php echo $_SERVER["HTTP_X_REMOTE_USER_EMAIL"]; ?></td>
    </tr>
    <tr>
        <td>Roles:</td>
        <td><?php echo $_SERVER["HTTP_X_REMOTE_USER_ROLES"]; ?></td>
    </tr>

</table>