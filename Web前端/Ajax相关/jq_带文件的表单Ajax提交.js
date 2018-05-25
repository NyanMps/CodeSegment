// 表单中中的 button 必须设置 type=button 否则会 submit
function submit2ajax() {
    // FormData 是 HTML5 中的
    var  data = new FormData($("form")[0]);
    $.ajax({
        type: "post",
        url: "BookServlet",
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            $("span#msg").text(data);
            clear();
        }
    })
}