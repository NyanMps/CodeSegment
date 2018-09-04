public void test() {
	 // 获取当前周的周一和周末
	System.out.println(String.format("min:%s, max:%s", DateTime.now().dayOfWeek().withMinimumValue().toString("yyyy-MM-dd"),
			DateTime.now().dayOfWeek().withMaximumValue().toString("yyyy-MM-dd")));

	// 获取当前周是当年内第几周
	System.out.println(DateTime.now().getWeekOfWeekyear());
	// 获取当前周所在年份
	System.out.println(DateTime.now().getWeekyear());

	System.out.println(DateTime.now().plusWeeks(19).toString("yyyy-MM-dd"));
	System.out.println(DateTime.now().plusWeeks(19).getDayOfWeek());

	// 当前周周日 加上偏移周数，得出的时间
	DateTime newDate = DateTime.now().dayOfWeek().withMaximumValue().plusWeeks(5);
	System.out.println(newDate);
}

private void fillingPlannedDate(PlanWeek planWeek, int weekCount) {
	// 基准日期：取当前周的周日
	DateTime base = DateTime.now().dayOfWeek().withMaximumValue();
	//  加上偏移周数，得出新时间
	DateTime newDate = base.plusWeeks(weekCount);
	// 偏移日期对应的年
	int year = newDate.getWeekyear();
	// 偏移日期对应的周
	int week = newDate.getWeekOfWeekyear();
	// 计算日期范围： 月/日 - 月/日
	String range = base.toString("MM/dd") + "-" + newDate.toString("MM/dd");

	planWeek.setPlanYear(String.valueOf(year));
	planWeek.setPlanWeek(String.valueOf(week));
	// 计划日期范围
	planWeek.setPlanDateRange(range);
}