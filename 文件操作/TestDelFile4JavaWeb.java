private void delPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String id = request.getParameter("id");
    // 删除文件
    ServletContext context = this.getServletContext();
    Bookpic bookpic = bookpicService.getBookpicById(id);
    String path = context.getRealPath(bookpic.getSavepath());
    File file = new File(path);
    if (file.exists()) {
        if (!file.delete()) {
            response.getWriter().print("删除失败！");
            return;
        }
    }

    // 删除数据库
    int flag = bookpicService.removeBookpicById(id);

    if (flag == 0) {
        response.getWriter().print("删除失败！");
    } else {
        response.getWriter().print("suc");
    }
}