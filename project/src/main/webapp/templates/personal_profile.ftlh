<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Profile</title>
    <style>
        td {
            border: 1px solid black;
            text-align: center;
        }
    </style>
</head>
<body>
<h2>Balance: ${user.balance}</h2>
Email: ${user.email}
<br>
Name: ${user.name}
<br>
<button onclick="window.location.href='/goal/create'">Create goal</button>
<form method="post" action="/logout">
    <input type="submit" value="LogOut">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
<br><br>
<div class="active-goals">
    <#if active??>
        <h2>Active goals:</h2>

        <table style="background-color: darkgreen; border: 4px solid black; width: 100%">
            <tr>
                <th>Timer:</th>
                <th>Title</th>
                <th>Description</th>
                <th>Money</th>
                <th>Result</th>
                <th>Visible for other</th>
                <th>Duration</th>
            </tr>
            <#list active as goal>
                <tr>
                    <td>
                        Days: ${(goal.durationLeft / 86400000) ? int}
                        <br>
                        Hours: ${((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int}
                        <br>
                        Minute: ${((goal.durationLeft -(goal.durationLeft / 86400000)?int * 86400000- ((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int * 3600000) / 60000) ? int}
                        <br>
                        Seconds:${((goal.durationLeft -(goal.durationLeft / 86400000)?int * 86400000 - ((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int * 3600000 - ((goal.durationLeft -(goal.durationLeft / 86400000)?int * 86400000- ((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int * 3600000) / 60000) ? int * 60000)/1000)?int }
                    </td>
                    <td>${goal.title}</td>
                    <td>${goal.description}</td>
                    <td>${goal.money}</td>
                    <td>
                        <form action="/goal/change" method="post">
                            <label>result:
                                <input style="width: 45px" type="number" name="result" value="${goal.result}">
                            </label>
                            <input type="hidden" name="goalId" value="${goal.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="submit" value="Change">
                        </form>
                    </td>
                    <td>${goal.visibleFotOther?string("true", "false")}</td>
                    <td>Day: ${goal.day}<br>Hour: ${goal.hour}<br>Minute: ${goal.minute}</td>
                </tr>
            </#list>
        </table>
    </#if>
</div>

<div class="waiting-goals">
    <#if waiting??>
        <h2 onload="setUp(this)">Waiting goals</h2>

        <table style="background-color: yellow; border: 4px solid black; width:100%">
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Money</th>
                <th>Result</th>
                <th>Visible for other</th>
                <th>Duration</th>
            </tr>
            <#list waiting as goal>
                <tr>
                    <td>${goal.title}</td>
                    <td>${goal.description}</td>
                    <td>${goal.money}</td>
                    <td>
                        <form action="/goal/complete" method="post">
                            <label>result:
                                <input style="width: 45px" type="number" name="result" value="${goal.result}">
                            </label>
                            <input type="hidden" name="goal_id" value="${goal.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="submit" value="Complete">
                        </form>
                    </td>
                    <td>${goal.visibleFotOther?string("true", "false")}</td>
                    <td>Day: ${goal.day}<br>Hour: ${goal.hour}<br>Minute: ${goal.minute}</td>
                </tr>
            </#list>
        </table>

    </#if>
</div>
</body>
</html>