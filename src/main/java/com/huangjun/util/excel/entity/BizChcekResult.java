package com.huangjun.util.excel.entity;

import lombok.Data;

/**
 * @Author: huangjun
 * @Date: 2022/1/24 15:53
 * @Version 1.0
 */
@Data
public class BizChcekResult {
    private Boolean checkResult = Boolean.TRUE;
    private String errorMsg;
}
