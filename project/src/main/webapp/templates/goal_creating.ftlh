<!doctype html>
<html lang="en">
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
<body>
<#if status??>
    <h2 style="text-align: center">
        <span style="color: red; ">${status}</span>
    </h2>
</#if>
<button onclick="window.location.href='/profile'">Profile</button>
<br>
<form id="my_form" action="/goal/create" method="post">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" placeholder="title" required>
    <br>
    <label for="description">Description:</label>
    <input type="text" id="description" name="description" placeholder="description">
    <br>
    <label for="money">Money:</label>
    <input type="number" id="money" name="money" placeholder="money" min="1" required>
    <hr>
    <h2>Duration:</h2>
    <label for="day">Number of days:</label>
    <input type="number" id="day" name="day" placeholder="number of days" min="0" max="365" value="1">
    <br>
    <label for="hour">Number of hours:</label>
    <input type="number" id="hour" name="hour" placeholder="number of hours" min="0" max="23" value="0">
    <br>
    <label for="minute">Number of minutes:</label>
    <input type="number" id="minute" name="minute" placeholder="number of minutes" min="0" max="59" value="0">
    <br>
    <hr>

    <input type="radio" id="visible_1"
           name="visible" value="true" checked>
    <label for="visible_1">Yes</label>
    <br>
    <input type="radio" id="visible_2"
           name="visible" value="false">
    <label for="visible_2">No</label>
    <#--<input type="hidden" name="userId" value="${user.id}">-->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit">
</form>
</body>
</html>