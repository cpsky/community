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
        $.getJSON("/comment/" + id, function (data) {
            var commentBody = $("comment-body-" + id);
            var items = [];
            $.each(data.data, function( comment ) {
                var c = $("<div/>",{
                    "class":"col-lg-12 col-md-12 col-sm-12 comments",
                    html: comment.content,
                });
                items.push(c);
            });
            commentBody.appendChild($("<div/>",{
                "class":"col-lg-12 col-md-12 col-sm-12 collapse sub-comments",
                "id": "comment-" + id,
                html: items.join( "" )
            }));
            //展开二级评论
            comments.addClass("in");
            //标记展开状态
            e.setAttribute("data-collapse", "in")
            e.classList.add("active")
        });
    }
}
