<div class="border-up">

    <div id="comments-section">
        <div id="comment-section">
            <#if commentsDto.comments?size gt 0>
                <p style="margin: 10px;">Comment section</p>
            </#if>

            <div id="post-comment" style="margin-top: 3px;margin-bottom: 3px;">
                <#list commentsDto.comments as comment>
                    <div class="row" style="margin-left: 0;margin-right: 0;">
                        <div class="col d-lg-flex justify-content-lg-end align-items-lg-start"
                             style="max-width: 20%;padding-top: 10px;">
                            <#--<div class="border rounded-circle"
                                 style="background-image: url(/resources/img/avatar.jpg);background-color: rgba(150,106,39,0.6);background-size: cover;background-position: bottom;background-repeat: no-repeat;height: 40px;width: 40px;"></div>-->                        </div>
                        <div class="col align-self-start" style="max-width: 80%;">
                            <p class="text-left"
                               style="font-size: 15px;filter: blur(0px);font-style: normal;font-weight: bold;margin: 0;">
                                @${comment.user.alias}</p>
                            <p class="text-break text-left" style="font-size: 14px;">${comment.text}
                            </p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <#--Тут пишут комменты-->
    <div id="post-write-section" style="margin-top: 3px;margin-bottom: 3px;">
        <div class="row" style="margin-left: 0;margin-right: 0;">
            <div class="col d-lg-flex justify-content-lg-end align-items-lg-start"
                 style="max-width: 20%;padding-top: 10px;">
                <#--<div class="border rounded-circle"
                     style="background-image: url(/resources/img/avatar.jpg);background-color: rgba(150,106,39,0.6);background-size: cover;background-position: bottom;background-repeat: no-repeat;height: 50px;width: 50px;"></div>-->
            </div>
            <div class="col d-lg-flex justify-content-center align-items-center justify-content-lg-center align-items-lg-center"
                 style="max-width: 70%;">
                            <textarea id="text-area-post-${commentsDto.goalId}" class="form-control-sm"
                                      style="min-width: 100%;margin-top: 4px;height: 39px;"></textarea>
            </div>
            <div class="col d-lg-flex justify-content-lg-start align-items-lg-center"
                 style="max-width: 10%;padding-right: 0px;padding-left: 0px; cursor: pointer"
                 onclick="sendComment(${commentsDto.goalId}, $('#text-area-post-'+${commentsDto.goalId}).val());">
                <div id="send-button-<#--${post.id}-->">
                    <i class="fas fa-paper-plane"
                       style="color: rgb(122,122,122);font-size: 20px;"></i>
                </div>
            </div>
        </div>
    </div>
</div>