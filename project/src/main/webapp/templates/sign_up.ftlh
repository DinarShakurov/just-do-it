<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>
</head>
<style>
    .error {
        color: #ff0000;
    }
</style>
<body>
<#if error??>
    <h2 style="text-align: center">
        <span style="color: red; ">Пользователь с таким <b>email</b> уже зарегестрирован</span>
    </h2>
</#if>
<h2>Welcome, noname</h2>
<b>Sign Up</b>
<br>
<div>
    <@spring.bind "signUpForm"/>
    <form action="/signUp" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        Email:
        <@spring.formInput "signUpForm.email"/>
        <@spring.showErrors "<br>", "error"/>
        <br>
        Password:
        <@spring.formInput "signUpForm.password"/>
        <@spring.showErrors "<br>", "error"/>
        <br>
        Name:
        <@spring.formInput "signUpForm.username"/>
        <@spring.showErrors "<br>", "error"/>
        <br>
        Alias:
        <@spring.formInput "signUpForm.alias"/>
        <@spring.showErrors "<br>", "error"/>
        <br>
        <input type="submit">
    </form>
</div>
</body>
</html>