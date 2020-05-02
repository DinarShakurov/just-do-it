<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>
</head>
<body>
<#if error??>
    <h2 style="text-align: center">
        <span style="color: red; ">Пользователь с таким <b>email</b> уже зарегестрирован</span>
    </h2>
</#if>
<h2>Welcome, noname</h2>
<b>Sign Up</b>
<br>
<form action="/signUp" method="post">
    <label for="email">Email: </label>
    <input type="text" name="email" id="email" placeholder="email" required>
    <br>
    <label for="password">Password: </label>
    <input type="password" name="password" id="password" placeholder="password" required>
    <br>
    <label for="username">Name</label>
    <input type="text" name="username" id="username" placeholder="name" required>
    <br>
    <label for="alias">Alias</label>
    <input type="text" name="alias" id="alias" placeholder="alias" required>
    <br>
    <input type="submit">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
</body>
</html>