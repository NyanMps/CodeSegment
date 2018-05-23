private void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name");
    String caid = request.getParameter("caid");

    List<Book> list = bookService.getBookAndCategoryPage(0, 100000, name, caid);

    Workbook workbook = new XSSFWorkbook();
    // 添加 sheet
    Sheet sheet = workbook.createSheet("书籍列表");
    Row row = sheet.createRow(0);
    row.createCell(0).setCellValue("编号");
    row.createCell(1).setCellValue("名字");
    row.createCell(2).setCellValue("作者");
    row.createCell(3).setCellValue("价格");
    row.createCell(4).setCellValue("所在类别");

    int i = 1;
    for (Book book : list) {
        Row row1 = sheet.createRow(i);
        row1.createCell(0).setCellValue(i++);
        row1.createCell(1).setCellValue(book.getName());
        row1.createCell(2).setCellValue(book.getAuthor());
        row1.createCell(3).setCellValue(book.getPrice());
        row1.createCell(4).setCellValue(book.getCategory().getName());
    }

    response.setHeader("Content-Disposition", "attachment;filename=book.xlsx");
    workbook.write(response.getOutputStream());
    workbook.close();
}