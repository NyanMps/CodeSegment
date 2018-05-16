import jxl.*;
import jxl.write.*;
import java.io.*;
public class CreateExcel{
	public static void main(String... args) throws Exception {
		//1.通过Workbook构造一个内存中的对象
		WritableWorkbook wwb = Workbook.
		createWorkbook(new File("emp.xls"));
		//2.添加Sheet
		WritableSheet ws = wwb.createSheet("employee",0);
		//3.构造单元格
		// 表头
		Label id = new Label(0,0,"id");
		Label name = new Label(1,0,"name");
		Label sal = new Label(2,0,"sal");

		// 数据
		Label vid = new Label(0,1,"ET001");
		Label vname = new Label(1,1,"etoak");
		Label vsal = new Label(2,1,"1234.5678");

		//添加单元格
		ws.addCell(id);ws.addCell(name);ws.addCell(sal);
		ws.addCell(vid);ws.addCell(vname);ws.addCell(vsal);

		//写出磁盘
		wwb.write();
		wwb.close();
	}
}