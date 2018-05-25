private void addBookAndPic(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    SmartUpload su = new SmartUpload();
    su.initialize(this, request, response);
    try {
        su.upload();
        Request req = su.getRequest();
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String author = req.getParameter("author");
        String date = req.getParameter("date");

        Book book = new Book();
        // 省略各种 set 方法

        // 获得文件数据
        Files files = su.getFiles();
        for (int i = 0; i < files.getCount(); i++) {
            File file = files.getFile(i);
            String fName = file.getFileName();
            String fileExt = file.getFileExt();
            String newName = CommonsUtils.getUUIDTo32() + "." + fileExt;
            file.saveAs("/myfiles/" + newName);

            // 保存到数据库
            Bookpic bookpic = new Bookpic();
            bookpic.setBookid(bookid);
            bookpic.setSavepath("/myfiles/" + newName);
            bookpic.setShowname(fName);
            bookpicService.addBookPic(bookpic);
        }
    } catch (SmartUploadException | IOException e) {
        e.printStackTrace();
    }
}