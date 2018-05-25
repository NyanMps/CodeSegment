// 此插件也是构造了一个 form 表单进行上传的
function addPic() {
  $.ajaxFileUpload({
    type: "post",
    url: "${pageContext.request.contextPath}/BookpicServlet",
    // 上传控件的 ID
    fileElementId: "pic",
    // 需要携带的参数
    data: {"bookid": bookid},
    dataType: "json",
    success: function (data) {
      if (data.flag == 'suc') {
        // Do something
      }
    }
  })
}