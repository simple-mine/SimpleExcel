package com.huangjun.util.excel.service;

import com.huangjun.util.excel.entity.BizChcekResult;

import java.util.List;
import java.util.Map;

/**
 * @Author: huangjun
 * @Date: 2022/1/24 15:29
 * @Version 1.0
 */
public interface ExcelBizService {

    /**
     * 传入一行
     * @param rowMap 一行的数据
     * @return 业务校验结果
     */
    BizChcekResult bizCheckExcelData(Map<String, String> rowMap);

    void saveBatchExcelData(List<Map<String, String>> rowListMap);
}
