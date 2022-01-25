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
public class ExcelRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String rule;

    private String errMsg;

}
