<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>replenish balance</title>
</head>
<body>
<form action="/money/replenish" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label for="money">How much:</label>
    <input type="number" name="money" id="money" placeholder="money" value="0">
    <br>
    <input type="submit">
</form>
</body>
</html>