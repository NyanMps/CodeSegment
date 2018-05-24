private void getListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Page<Book> page = new Page<>();
    String paraName = request.getParameter("name");
    String caid = request.getParameter("caid");

    int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
    page.setPageNumber(pageNumber);

    int total = bookService.getBookCount(paraName, caid);
    page.setTotal(total);

    // 首先设置 PageNumber 和 Total 后才能使用 getStart、getPageSize
    List<Book> bookList = bookService.getBookAndCategoryPage(page.getStart(), page.getPageSize(), paraName, caid);
    page.setRows(bookList);

    JSONObject jsonObject = JSONObject.fromObject(page);
    response.getWriter().print(jsonObject);
}