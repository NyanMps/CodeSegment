public String export2Excel() throws Exception {
	List<Employee> list = empService.querySomeEmp(name, 0, 100000);
	Workbook wb = new XSSFWorkbook();
	Sheet sheet = wb.createSheet("员工列表");
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("序号");
	header.createCell(1).setCellValue("姓名");
	header.createCell(2).setCellValue("所在部门");
	header.createCell(3).setCellValue("性别");
	header.createCell(4).setCellValue("年龄");
	header.createCell(5).setCellValue("编号");

	final int[] i = {1};
	list.forEach(emp -> {
		Row row = sheet.createRow(i[0]);
		row.createCell(0).setCellValue(i[0]++);
		row.createCell(1).setCellValue(emp.getEmpName());
		row.createCell(2).setCellValue(emp.getOrganizationByOrgaId().getOrgaName());
		row.createCell(3).setCellValue(emp.getSex());
		row.createCell(4).setCellValue(emp.getAge());
		row.createCell(5).setCellValue(emp.getEmpCode());
	});

	ServletContext context = ServletActionContext.getServletContext();
	OutputStream os = new FileOutputStream(context.getRealPath("/WEB-INF/temp.xlsx"));
	wb.write(os);
	wb.close();
	is = new FileInputStream(new File(context.getRealPath("/WEB-INF/temp.xlsx")));
	return SUCCESS;
}