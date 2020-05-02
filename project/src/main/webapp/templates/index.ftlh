<#import "goals_macro.ftlh" as goals_macro>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>HZ</title>
    <link rel="stylesheet" href="/resources/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/assets/css/styles.css">
    <link rel="stylesheet" href="/resources/assets/css/Team-Grid.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_parameter" content="${_csrf.parameterName}"/>
</head>

<body class="d-lg-flex" style="background-color: rgb(249,249,249);">
<div class="row" style="min-width: 100%;">
    <div class="col d-xl-flex justify-content-xl-center content" style="background-color: #a7a7a7;">
        <div class="d-lg-flex" id="main" style="background-color: rgba(255,255,255,0);">
            <div id="task-tabs" style="background-color: rgba(255,255,255,0);width: 509px;">
                <div>
                    <ul class="nav nav-tabs" style="opacity: 1;background-color: #5c0000;">
                        <li class="nav-item"><a class="nav-link active" role="tab" data-toggle="tab" href="#active">Active</a></li>
                        <li class="nav-item"><a class="nav-link" role="tab" data-toggle="tab" href="#waiting">Awaiting</a></li>
                        <li class="nav-item"><a class="nav-link" role="tab" data-toggle="tab" href="#completed">Completed</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" role="tabpanel" id="active">
                            <#if profile.active??>
                                <div id="task-feed">
                                    <#list profile.active as goal>
                                        <div id="task-holder" class="border1"
                                             style="background-color: #acffb4;margin-bottom: 10px;margin-top: 10px;">
                                            <div id="task-info-section">
                                                <div>
                                                    <div class="row" style="margin-left: 0;margin-right: 0;">
                                                        <div class="col d-lg-flex justify-content-lg-center">
                                                            <div>
                                                                <h4>${goal.title}</h4>
                                                                <p class="text-break">${goal.description}</p>
                                                            </div>
                                                        </div>
                                                        <div class="col d-flex d-lg-flex flex-column justify-content-lg-start align-items-lg-center">
                                                            <h4>Time left:</h4>
                                                            Days: ${(goal.durationLeft / 86400000) ? int}
                                                            <br>
                                                            Hours: ${((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int}
                                                            <br>
                                                            Minute: ${((goal.durationLeft -(goal.durationLeft / 86400000)?int * 86400000- ((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int * 3600000) / 60000) ? int}
                                                            <br>
                                                            Seconds:${((goal.durationLeft -(goal.durationLeft / 86400000)?int * 86400000 - ((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int * 3600000 - ((goal.durationLeft -(goal.durationLeft / 86400000)?int * 86400000- ((goal.durationLeft - (goal.durationLeft / 86400000)?int * 86400000)/3600000) ? int * 3600000) / 60000) ? int * 60000)/1000)?int }
                                                            <br>
                                                            <h4>${goal.money}$ </h4>
                                                        </div>
                                                        <div class="col d-lg-flex flex-column justify-content-lg-start align-items-lg-center">
                                                            <form action="/goal/change" method="post">
                                                                <label>result:
                                                                    <input style="width: 45px" type="number" name="result"
                                                                           value="${goal.result}">
                                                                </label>
                                                                <input type="hidden" name="goalId" value="${goal.id}">
                                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                                                <button class="btn btn-primary" type="submit">Change</button>
                                                            </form>
                                                            <h5>Day: ${goal.day}<br>Hour: ${goal.hour}<br>Minute: ${goal.minute}</h5>
                                                        </div>
                                                    </div>
                                                    <div class="row" style="margin-right: 0;margin-left: 0;">
                                                        <div class="col">
                                                            <div class="progress" style="background-color: rgb(0,0,0);">
                                                                <div class="progress-bar bg-success" aria-valuenow="${goal.result}"
                                                                     aria-valuemin="0"
                                                                     aria-valuemax="100" style="width: ${goal.result}%;">${goal.result}%
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="comments-section" class="border-up">
                                                <div id="comment-section">
                                                    <p>Comment section</p>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            </#if>
                        </div>
                        <div class="tab-pane" role="tabpanel" id="waiting">
                            <#if profile.waiting??>
                                <div id="task-feed">
                                    <#list profile.waiting as goal>
                                        <div id="task-holder" class="border1"
                                             style="background-color: #acffb4;margin-bottom: 10px;margin-top: 10px;">
                                            <div id="task-info-section">
                                                <div>
                                                    <div class="row" style="margin-left: 0;margin-right: 0;">
                                                        <div class="col d-lg-flex justify-content-lg-center">
                                                            <div>
                                                                <h4>${goal.title}</h4>
                                                                <p class="text-break">${goal.description}</p>
                                                            </div>
                                                        </div>
                                                        <div class="col d-flex d-lg-flex flex-column justify-content-lg-start align-items-lg-center">
                                                            <h4>${goal.money}$ </h4>
                                                        </div>
                                                        <div class="col d-lg-flex flex-column justify-content-lg-start align-items-lg-center">
                                                            <form action="/goal/complete" method="post">
                                                                <label>result:
                                                                    <input style="width: 45px" type="number" name="result"
                                                                           value="${goal.result}">
                                                                </label>
                                                                <input type="hidden" name="goalId" value="${goal.id}">
                                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                                                <button class="btn btn-primary" type="submit">Complete</button>
                                                            </form>
                                                            <h5>Day: ${goal.day}<br>Hour: ${goal.hour}<br>Minute: ${goal.minute}</h5>
                                                        </div>
                                                    </div>
                                                    <div class="row" style="margin-right: 0;margin-left: 0;">
                                                        <div class="col">
                                                            <div class="progress" style="background-color: rgb(0,0,0);">
                                                                <div class="progress-bar bg-success" aria-valuenow="${goal.result}"
                                                                     aria-valuemin="0"
                                                                     aria-valuemax="100" style="width: ${goal.result}%;">${goal.result}%
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="comments-section" class="border-up">
                                                <div id="comment-section">
                                                    <p>Comment section</p>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            </#if>
                        </div>
                        <div class="tab-pane" role="tabpanel" id="completed">
                            <#if profile.completed??>
                                <div id="task-feed">
                                    <#list profile.completed as goal>
                                        <@goals_macro.out goal/>
                                    </#list>
                                </div>
                            </#if>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col d-xl-flex justify-content-xl-start menu1" style="background-color: #000000;max-width: 22%;">
        <div>
            <h2 style="color: rgb(255,255,255);">Email: ${profile.user.email}</h2>
            <h2 style="color: rgb(255,255,255);">Name: ${profile.user.name}</h2>
            <h2 style="color: rgb(255,255,255);">Balance: ${profile.user.balance}$</h2>
            <button class="btn btn-primary" onclick="window.location.href='/money/replenish'">Replenish balance</button>
            <br>
            <button class="btn btn-primary" onclick="window.location.href='/goal/create'">Create goal</button>
            <br>
            <form method="post" action="/logout">
                <button class="btn btn-primary" type="submit">Log Out</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </form>
        </div>
    </div>
</div>
<script src="/resources/assets/js/jquery.min.js"></script>
<script src="/resources/assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>