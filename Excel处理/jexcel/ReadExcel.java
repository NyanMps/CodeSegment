import java.io.*;
import jxl.*;

public class ReadExcel{
	// 使用 jexcel 读取老版 Excel
	public static void main(String[] args) throws Exception {
		Workbook wb = Workbook.getWorkbook(new File("test.xls"));
		Sheet sheet = wb.getSheet(0);
		
		// 列数
		// int count = sheet.getColumns();
		int count = sheet.getRows();
		for(int i = 0; i < count; i++){
			Cell[] cell = sheet.getRow(i);
			for(Cell c : cell){
				System.out.print(c.getContents() + "\t");
			}
			System.out.println();
		}
	}
}