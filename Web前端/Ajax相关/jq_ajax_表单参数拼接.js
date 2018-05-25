// 表单中中的 button 必须设置 type=button 否则会 submit
function submit2ajax() {
    var $form = $("form").serializeArray();
    $.ajax({
        type: "post",
        url: "CategoryServlet",
        data: splice($form),
        success: function (data) {
            $("span#msg").text(data);
            // 清空表单输入的内容
            clear();
        }
    })
}

// 注意使用隐藏域，比如 method=add
function splice(array) {
    var result = [];
    $.each(array, function (index, e) {
        result.push(e.name + "=" + e.value);
        result.push("&");
    });

    result.pop();
    return result.join('').toString();
}

function clear() {
    $("form")[0].reset();
}