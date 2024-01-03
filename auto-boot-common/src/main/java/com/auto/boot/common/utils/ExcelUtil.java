package com.auto.boot.common.utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

/**
 * @ClassName ExcelUtil
 * @Description excel处理工具类
 * @Author hong.weijie
 * @Date 2022-09-01 15:33:57
 **/
public class ExcelUtil {

    /**
     * 设置Excel某列的值显示为下拉框.
     * @param sheet 模板sheet页（需要设置下拉框的sheet）
     * @param firstRow  添加下拉框对应开始行
     * @param endRow    添加下拉框对应结束行
     * @return XSSFSheet 设置好的sheet.
     */
    public static XSSFSheet setXSSFValidation(XSSFSheet sheet, String[] options, int firstRow, int endRow, int column){
        //设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, column, column);
        // 数据有效性对象
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                .createExplicitListConstraint(options);
        XSSFDataValidation dataValidate = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(dataValidate);
        return sheet;
    }

    /**
     * 将单元格值转为字符串
     * @param cell 单元格
     * @return
     */
    public static String getFormatCell(XSSFCell cell) {
        if(cell != null){
            CellType cellType = cell.getCellType();
            switch (cellType){
                case BLANK:
                    return "";
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    DataFormatter formatter = new DataFormatter();
                    return formatter.formatCellValue(cell);
            }
        }
        return "";
    }
}
