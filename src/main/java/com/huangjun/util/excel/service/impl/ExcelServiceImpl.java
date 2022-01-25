package com.huangjun.util.excel.service.impl;

import com.huangjun.util.excel.entity.BizChcekResult;
import com.huangjun.util.excel.entity.ExcelRule;
import com.huangjun.util.excel.entity.ExcelTemplate;
import com.huangjun.util.excel.exception.ExcelException;
import com.huangjun.util.excel.service.ExcelBizService;
import com.huangjun.util.excel.service.ExcelService;
import com.huangjun.util.excel.service.IExcelRuleService;
import com.huangjun.util.excel.service.IExcelTemplateService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @Author: huangjun
 * @Date: 2022/1/24 10:51
 * @Version 1.0
 */
@Service("excelService")
public class ExcelServiceImpl implements ExcelService {

    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel


    @Autowired
    private IExcelTemplateService excelTemplateService;
    @Autowired
    private IExcelRuleService ruleService;
    @Autowired
    private ExcelBizService excelBizService;


    @Override
    public  Map<String,String> importExcel(MultipartFile file,String excelId) throws IOException {
        Workbook workbook = getWorkbook(file);
        List<ExcelTemplate> templateList = excelTemplateService.lambdaQuery().eq(ExcelTemplate::getExcelId, excelId).list();
        ArrayList<ExcelTemplate> sheetNameList = templateList.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(Comparator.comparing(ExcelTemplate::getSheetName))), ArrayList::new)
        );
        List<Map<String,String>> mapList = new ArrayList<>();
        Map<String,String> errorMap = new HashMap<>();
        for (ExcelTemplate sheetName : sheetNameList) {
            List<ExcelTemplate> sheetList = templateList.stream().filter(excelTemplate1 -> excelTemplate1.getSheetName().equals(sheetName.getExcelName())).collect(Collectors.toList());
            Map<Long, ExcelRule> ruleMap = ruleService.getMapByIds(sheetList.stream().map(ExcelTemplate::getRuleId).collect(Collectors.toList()));
            Sheet sheet = workbook.getSheet(sheetName.getSheetName());
            if (sheet == null)
                continue;

            for (int r = sheet.getFirstRowNum(); r < sheet.getLastRowNum(); r++) {
                Map<String,String> rowMap = new HashMap<>(sheetList.size());
                Row row = sheet.getRow(r);
                if(row==null)
                    continue;
                if (row.getLastCellNum() != sheetList.size())
                    throw new ExcelException("数据库配置列数与导入列数不一致");

                for (ExcelTemplate template : sheetList) {
                    String cellValue = getValue(row.getCell(template.getColumnNum()));
                    if (ruleMap != null && ruleMap.size() > 0){
                        ExcelRule rule = ruleMap.get(template.getRuleId());
                        String checkResult = checkData(cellValue,rule);
                        if (checkResult != null)
                            errorMap.put("第"+r+"行",checkResult);
                        rowMap.put(template.getItemCode(),cellValue);
                    }
                }
                BizChcekResult bizCheck = excelBizService.bizCheckExcelData(rowMap);
                if (bizCheck.getCheckResult())
                    mapList.add(rowMap);
                else
                    errorMap.put("第"+r+"行",bizCheck.getErrorMsg());
            }

            if (mapList.size() == 1000){
                excelBizService.saveBatchExcelData(mapList);
                mapList.clear();
            }
        }
        return errorMap;
    }

    private Workbook getWorkbook(MultipartFile file) throws IOException{
        if (file == null)
            throw new ExcelException("文件为空");
        InputStream inputStream = file.getInputStream();
        String filename = file.getOriginalFilename();
        if (filename == null)
            throw new ExcelException("文件名为空");
        Workbook wb;
        if (filename.endsWith(excel2003L))
            wb = wb = new HSSFWorkbook(inputStream);
        else if (filename.endsWith(excel2007U))
            wb = new XSSFWorkbook(inputStream);
        else
            throw new ExcelException("解析的文件格式有误！");
        return wb;
    }

    private  String getValue(Cell cell) {
        String value = "";
        if(null==cell){
            return value;
        }
        switch (cell.getCellType()) {
            //数值型
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(date);;
                }else {// 纯数字
                    BigDecimal big=new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //解决1234.0  去掉后面的.0
                    if(null!=value&&!"".equals(value.trim())){
                        String[] item = value.split("[.]");
                        if(1<item.length&&"0".equals(item[1])){
                            value=item[0];
                        }
                    }
                }
                break;
            //字符串类型
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                //读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " "+ cell.getBooleanCellValue();
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if("null".endsWith(value.trim())){
            value="";
        }
        return value;
    }

    private String checkData(String val,ExcelRule rule){
        Pattern pattern=Pattern.compile(rule.getRule());
        Matcher match=pattern.matcher(val);
        if (!match.matches())
            return rule.getErrMsg();
        return null;
    }
}
