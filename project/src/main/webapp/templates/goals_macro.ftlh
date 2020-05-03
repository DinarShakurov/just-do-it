<#macro out goal>

    <script>
        function hide(id, goalId, wait) {
            var x = document.getElementById(id);
            if (x.style.display === "none") {
                x.style.display = "block";
            } else {
                x.style.display = "none";
            }
            if ($('#' + id).text().length == 0) {
                getComments(id, goalId, wait);
            }
        }

        function getComments(divId, goalId, wait) {
            let data;
            if (wait != null) {

                data = {
                    goal_id: goalId,
                    w: true
                }
            } else {
                data = {
                    goal_id: goalId,
                    w: false
                }
            }

            $.get("/comment/get", data, function (response) {
                $('#' + divId).html(response);
                getComments(divId, goalId, true);
            })

        }
    </script>

    <script>
        function sendComment(id, text) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            let body = {
                text: text,
                goalId: id
            }
            $.ajax({
                url: "/comment/send",
                method: "POST",
                data: JSON.stringify(body),
                contentType: "application/json",
                dataType: "json",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
            })

        }
    </script>

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
                        <h4>Result: ${goal.result}%</h4>
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
        <div id="action-section"
             class="d-flex d-sm-flex d-md-flex justify-content-end align-items-center justify-content-sm-end align-items-sm-center justify-content-md-end align-items-md-center border-up"
             style="padding: 2px;">
            <div id="comment-icon"
                 onclick="hide('comments-section-goal-${goal.id}', ${goal.id});"
                 style="cursor: pointer">
                <i class="fas fa-comment-alt"
                   style="padding: 6px;font-size: 15px;color: rgb(132,132,132);font-weight: normal;font-style: normal;"></i>
            </div>
            <div id="like-icon" onclick="alert('like')" style="cursor:pointer;">
                <i class="far fa-heart unliked" style="padding: 6px;font-size: 15px;"></i>
            </div>
        </div>

        <div id="comments-section-goal-${goal.id}" style="display: none"></div>

    </div>
</#macro>