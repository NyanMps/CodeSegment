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


// 创建对象的方法
DateTime dateTime1 = new DateTime();
System.out.println(dateTime1); // out: 2016-02-26T16:02:57.582+08:00
DateTime dateTime2 = new DateTime(2016,2,14,0,0,0);
System.out.println(dateTime2); // out: 2016-02-14T00:00:00.000+08:00
DateTime dateTime3 = new DateTime(1456473917004L);
System.out.println(dateTime3); // out: 2016-02-26T16:05:17.004+08:00
DateTime dateTime4 = new DateTime(new Date());
System.out.println(dateTime4); // out: 2016-02-26T16:07:59.970+08:00
DateTime dateTime5 = new DateTime("2016-02-15T00:00:00.000+08:00");
System.out.println(dateTime5); // out: 2016-02-15T00:00:00.000+08:00

DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
DateTime baseDate = new DateTime(dateFormat.parse("201812"));

// plus/minus开头的方法进行加、减
DateTime startDate = baseDate.minusMonths(2);