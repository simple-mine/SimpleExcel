package com.huangjun.util.excel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huangjun.util.excel.entity.ExcelRule;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangjun
 * @since 2022-01-24
 */
public interface IExcelRuleService extends IService<ExcelRule> {

    Map<Long, ExcelRule> getMapByIds(List<Long> ids);
}
