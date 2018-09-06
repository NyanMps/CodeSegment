@RequestMapping(value = "/export")
@ResponseBody
public void export(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, PlanWeekVo planWeek) {
	Wrapper<PlanWeekVo> wrapper = this.parseConditions(planWeek);
	List<PlanWeekVo> list = (List<PlanWeekVo>) new PlanWeekWarpper(planWeekMapper.selectCustom(wrapper)).warp();

	String path;
	try {
		path = ResourceUtils.getFile("classpath:static/template/n13Template.xlsx").getAbsolutePath();
	} catch (FileNotFoundException e) {
		throw  new RuntimeException("模板文件不存在！");
	}

	// 获取导出excel指定模版
	TemplateExportParams params = new TemplateExportParams(path);
	// 标题开始行
	params.setHeadingStartRow(0);
	// 标题行数
	params.setHeadingRows(2);
	// 设置sheetName，若不设置该参数，则使用得原本得sheet名称
	params.setSheetName("N+13周计划");
	params.setTempParams("t");
	Map<String,Object> data = Maps.newHashMap();
	data.put("list", list);
	modelMap.put(TemplateExcelConstants.MAP_DATA, data);
	modelMap.put(TemplateExcelConstants.PARAMS, params);
	modelMap.put(TemplateExcelConstants.FILE_NAME, "N+13周计划" + DateUtil.getDays());
	PoiBaseView.render(modelMap, request, response, TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
}