function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (respose) {
            if (respose.code == 200) {
                $("#comment_section").hide();
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
