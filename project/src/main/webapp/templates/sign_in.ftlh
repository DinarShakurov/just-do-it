<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign In</title>
    <script type="text/javascript" src="https://vk.com/js/api/openapi.js?168"></script>
</head>
<body>
<button onclick="window.location.href='/signUp'">Sign Up</button>
<br>
<#if error??>
    <h2 style="text-align: center">
        <span style="color: red; ">${error.message}</span>
    </h2>

</#if>
<h2>Welcome, noname</h2>
<b>Login and password</b>
<br>
<form action="/signIn" method="post">
    <label for="email">Email: </label>
    <input type="text" name="email" id="email" placeholder="email" required>
    <br>
    <label for="password">Password: </label>
    <input type="password" name="password" id="password" placeholder="password" required>
    <br>
    <input type="checkbox" name="remember-me" id="remember-me">
    <label for="remember-me">Remember me</label>
    <br>
    <input type="submit">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
<br>
<div id="vk_auth"></div>
<script type="text/javascript">
    window.onload = function () {
        VK.init({apiId: 7485308});
        VK.Widgets.Auth("vk_auth", {"authUrl":"https://oauth.vk.com/authorize?client_id=7485308&display=page&redirect_uri=http://localhost/vk/code&scope=offline,email&response_type=code&v=5.107"});
    }
</script>
</body>
</html>