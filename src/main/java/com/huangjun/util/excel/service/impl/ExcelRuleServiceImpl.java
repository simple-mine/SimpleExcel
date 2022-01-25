package com.huangjun.util.excel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjun.util.excel.entity.ExcelRule;
import com.huangjun.util.excel.mapper.ExcelRuleMapper;
import com.huangjun.util.excel.service.IExcelRuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjun
 * @since 2022-01-24
 */
@Service
public class ExcelRuleServiceImpl extends ServiceImpl<ExcelRuleMapper, ExcelRule> implements IExcelRuleService {

    @Override
    public Map<Long, ExcelRule> getMapByIds(List<Long> ids) {
        if (ids.size() == 0)
            return null;
        return baseMapper.getMapByIds(ids);
    }
}
