$(function () {
    $.ajax({
        type: "get",
        url: "${pageContext.request.contextPath}/CategoryServlet?method=getAll2Json",
        dataType: "json",
        success: function (data) {
            var $sel = $("select#category");
            $.each(data, function (index, e) {
                var opt = $("<option></option>");
                opt.val(e.id).text(e.name);
                $sel.append(opt);
            });
        }
    })
});