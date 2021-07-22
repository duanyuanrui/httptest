package com.qa.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;

import java.io.File;
import java.util.Iterator;

public class ExcelDataProvider implements Iterator<Object[]> {

	private Workbook book = null;
	private Sheet sheet = null;
	private int beginrowNum = 0;
	private int rowNum = 0;
	private int curRowNo = 0;
	private int columnNum = 14;

	public ExcelDataProvider(String methodname) {
		try {
            if(methodname.equals("getListUsersTest")){
                columnNum=2;
            }
            if(methodname.equals("loginTest")){
                columnNum=4;
            }

			File directory = new File(".");
			this.book = Workbook.getWorkbook(new File(directory
					.getAbsolutePath() + "/src/test/resources/DataProvider.xls"));
			this.sheet = book.getSheet(methodname);
			System.out.println("methodname= " + methodname);
			this.rowNum = sheet.getRows();
			setColunmsAndRows();
			curRowNo = beginrowNum + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		if (this.rowNum == 0 || this.curRowNo >= this.rowNum) {
			try {
				book.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	
		boolean has = false;
		int startrowno = curRowNo;
		for (int i = startrowno; i < rowNum; i++) {
			if (sheet.getRow(curRowNo)[0].getCellFormat().getBackgroundColour()
					.equals(Colour.DEFAULT_BACKGROUND)) {
				has = true;
				curRowNo = i;
				break;
			} else {
				curRowNo = curRowNo + 1;
				continue;
			}
		}
		if (has) {
			return true;
		} else {
			try {
				book.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		// else
		// return true;
	}

	@Override
	public Object[] next() {
		Cell[] c = sheet.getRow(curRowNo);
		Object r[] = new Object[columnNum];
		for (int i = 0; i < columnNum; i++) {
			r[i] = c[i].getContents();
			// if(i==4){
			// String[] ary = c[i].getContents().split(",");
			// r[i]= ary;
			// }
			// else if (i==3){
			// int ref =Integer.parseInt(c[i].getContents());
			// r[i] = ref;
			// }
			// else{
			// r[i] = c[i].getContents();
			// }
		}
		this.curRowNo++;
		return r;
	}

	public void setColunmsAndRows() {
		Cell c;
		String term = "";
		int i = 0;
		while (!"#begin".equals(term)) {
			c = sheet.getCell(0, i);
			term = c.getContents();
			i = i + 1;
		}
		this.beginrowNum = i;
		i = 0;
		term = "";
		while (!"#end".equals(term)) {
			c = sheet.getCell(0, i);
			term = c.getContents();
			i = i + 1;
		}
		this.rowNum = i - 1;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove unsupported.");
	}
}