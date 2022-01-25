package com.huangjun.util.excel.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: huangjun
 * @Date: 2022/1/24 10:51
 * @Version 1.0
 */
public interface ExcelService {
    Map<String,String> importExcel(MultipartFile file, String excelId) throws IOException;
}
