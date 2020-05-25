<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Creating Goal</title>
    <#--    <script>
            function updateTextInput(val) {
                document.getElementById('textInput').value=val;
            }
        </script>-->
    <#--    <input type="range" name="rangeInput" min="0" max="100" onchange="updateTextInput(this.value);">
        <input type="text" id="textInput" value="">-->
</head>
<style>
    .error {
        color: #ff0000;
    }
</style>
<body>

<#if status??>
    <h2 style="text-align: center">
        <span style="color: red; ">${status}</span>
    </h2>
</#if>
<button onclick="window.location.href='/profile'">Profile</button>
<br>
<form id="my_form" action="/goal/create" method="post">
    <@spring.bind "goalCreatingForm"/>
    <@spring.showErrors "<br>", "error"/>
    <br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    Title:
    <@spring.formInput "goalCreatingForm.title"/>
    <@spring.showErrors "<br>", "error"/>

    <br>
    Description:
    <@spring.formInput "goalCreatingForm.description"/>
    <@spring.showErrors "<br>", "error"/>

    <br>
    Money:
    <@spring.formInput "goalCreatingForm.money" "" "number"/>
    <@spring.showErrors "<br>", "error"/>

    <hr>
    <h2>Duration:</h2>
    Number of days:
    <@spring.formInput "goalCreatingForm.day" "" "number"/>
    <@spring.showErrors "<br>", "error"/>

    <br>
    Number of hours:
    <@spring.formInput "goalCreatingForm.hour" "" "number"/>
    <@spring.showErrors "<br>", "error"/>

    <br>
    Number of minutes:
    <@spring.formInput "goalCreatingForm.minute" "" "number"/>
    <@spring.showErrors "<br>", "error"/>

    <br>
    <hr>
    <@spring.formRadioButtons "goalCreatingForm.visible", radioValues, "<br>"/>
    <br>

    <input type="submit">
</form>
</body>
</html>