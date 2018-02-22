package com.jianma.fzkb;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.service.DesignerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ImportTest {

	@Autowired
	@Qualifier(value="designerServiceImpl")
	private DesignerService designerServiceImpl;
	
	@Test
	public void addDesigner() throws IOException{
		List<Designer> list = this.readXlsx("d:\\result.xlsx");
		list.remove(0);
		for (Designer designer : list){
			System.out.println(designer.getRealname());
			designer.setCreateTime(new Date());
			designer.setPassword("111111");
			designer.setRole(0);
			designerServiceImpl.createDesigner(designer);
		}
		
	}
	
	public List<Designer> readXlsx(String path) throws IOException {

		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		Designer designer = null;
		List<Designer> list = new ArrayList<Designer>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					designer = new Designer();
					XSSFCell cell1 = xssfRow.getCell(0);
					XSSFCell cell2 = xssfRow.getCell(1);
					XSSFCell cell3 = xssfRow.getCell(2);
					
					String email = getValue(cell1);
					designer.setUsername(email);
					
					String realname = getValue(cell2);
					designer.setRealname(realname);

					String address = getValue(cell3);
					designer.setAddress(address);


					list.add(designer);
				}
			}
		}
		return list;
	}
	
	private String getValue(XSSFCell xssfRow) {
		if (xssfRow != null) {
			if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
				return String.valueOf(xssfRow.getBooleanCellValue());
			} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
				return String.valueOf(xssfRow.getNumericCellValue());
			} else {
				return String.valueOf(xssfRow.getStringCellValue());
			}
		}
		return "";
	}
}
