<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="/resources/js/sign_up_validation.js"></script>
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
    <form id="signUpForm" action="/signUp" method="post">
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
        <input type="button" value="js-valid" onclick="trySubmit()">
        <input type="submit">
    </form>
</div>
</body>
</html>