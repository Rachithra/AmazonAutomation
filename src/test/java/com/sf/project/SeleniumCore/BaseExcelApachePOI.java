package com.sf.project.SeleniumCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
/**
 * @author : Rachithra R
 *  *
 *  *  MOD	EMPID	  DATE		    DESCRIPTION
 *  * ---	-----     ------	    -----------
 *  * 001	XXX	    07.19.2020 	    Initial Development
 *  * 002   XXX     07.20.2020      Added description for the methods
 *  *
 *  * PURPOSE : To fetch the values from the MS Excel data file
 */

public class BaseExcelApachePOI {
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row   =null;
    private XSSFCell cell = null;
    public  String path ;
    public  FileInputStream fis = null;
    private static final Logger logger=LogManager.getLogger(BaseExcelApachePOI.class);



    /**
     * Assigns the file path to the Apache POI XSSF Workbook
     * @param path  - Path of the file
     * Author       - Rachithra R
     */
    public BaseExcelApachePOI(String path) {

        this.path=path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {
            logger.error("Exeption_BaseExcelApachePOI:"+e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * This method will fetch the value from the requested cell and return the same in String format
     * @param sheetName     - The name of the sheet in the workbook which holds the value
     * @param colName       - The name of the column that holds the value
     * @param rowNum        - The row number of the cell that holds the value
     * @return              - Return the cell value in String format
     * @throws UnsupportedFileFormatException
     * Author               - Rachithra R
     */
    public String getCellData(String sheetName,String colName,int rowNum) throws UnsupportedFileFormatException {
        try{
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                logger.info(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(col_Num);

            if (cell == null)
                return "";
            logger.info("Cell Type :"+cell.getCellType());
            // Alternatively, get the value and format it yourself
            switch (cell.getCellType()) {
                case STRING:
                    logger.info("GetRichStringCellValue :"+cell.getRichStringCellValue().getString());
                    return cell.getStringCellValue();

                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        logger.info("Cell Value :"+cell.getDateCellValue());
                        return String.valueOf(cell.getDateCellValue());
                    } else {
                        logger.info("Get numeric cell value"+cell.getNumericCellValue());
                        return String.valueOf(cell.getNumericCellValue());
                    }

                case BOOLEAN:
                    logger.info("Get boolean cell value :"+cell.getBooleanCellValue());
                    return String.valueOf(cell.getBooleanCellValue());

                case FORMULA:
                    logger.info("Get formula :"+cell.getCellFormula());
                    return cell.getCellFormula();

                case BLANK:
                    logger.info("Blank cell");
                    return "";

                default:
                    logger.info("Cell type does not match any specified types");
                    return "Cell type does not match any specified type";
            }
        } catch (Exception e) {
            logger.error("Exception_getCellData :"+e.getMessage());
            e.printStackTrace();
            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }
}
