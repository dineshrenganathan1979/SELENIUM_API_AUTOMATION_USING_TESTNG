package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EXCEL_Reader {
	
	private static XSSFWorkbook workbook;
    private static XSSFSheet worksheet;
    private static FileInputStream fis=null;
    
    public static Object [][] getExcelData(String sheetName)
    {
    	try {
    		fis = new FileInputStream(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData.xlsx"));
    		workbook = new XSSFWorkbook(fis);
    		worksheet =workbook.getSheet(sheetName);
    	}catch(IOException e) {
    		System.out.println(e.getMessage());
    	}
    	   	
    	int rowCount =worksheet.getLastRowNum();
    	int colCount =worksheet.getRow(0).getLastCellNum();
    	Object[][] data = new Object[rowCount][colCount];
    	
    	for(int i=0; i<rowCount; i++) {
    		
    		for(int j=0; j<colCount; j++) {
    			data[i][j] = worksheet.getRow(i+1).getCell(j).toString();
    			//System.out.println(data[i][j]);
    					
    		}
    		
    	}
    	   
    	
    	return data;
    }
    
       
    
}
