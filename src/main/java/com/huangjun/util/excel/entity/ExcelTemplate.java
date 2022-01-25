package com.huangjun.util.excel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangjun
 * @since 2022-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExcelTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String excelId;

    private String excelName;

    private String sheetName;

    private String headerName;

    private String needCheck;

    private Long ruleId;

    private Integer columnNum;

    private String itemCode;


}
