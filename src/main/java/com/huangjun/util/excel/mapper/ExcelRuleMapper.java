package com.huangjun.util.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangjun.util.excel.entity.ExcelRule;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangjun
 * @since 2022-01-24
 */
@Mapper
public interface ExcelRuleMapper extends BaseMapper<ExcelRule> {

    @MapKey("id")
    Map<Long, ExcelRule> getMapByIds(List<Long> ids);
}
