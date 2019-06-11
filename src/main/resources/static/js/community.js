/*
* 提交回复
* */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容！！！！！！")
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (respose) {
            if (respose.code == 200) {
                window.location.reload();
            } else {
                if (respose.code == 2003) {
                    var isAccepted = confirm(respose.message)
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=9d63011b7811c6191bcc&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(respose.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**/
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse")
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in")
        e.removeAttribute("data-collapse")
        e.classList.remove("active")
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body",
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media",
                    }).append(mediaLeftElement) .append(mediaBodyElement);
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 comments",
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
            });
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }

    }
}
