package com.huangjun.util.excel.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangjun.util.excel.mapper.ExcelTemplateMapper;
import com.huangjun.util.excel.service.ExcelBizService;
import com.huangjun.util.excel.service.ExcelService;
import com.huangjun.util.excel.service.IExcelRuleService;
import com.huangjun.util.excel.service.IExcelTemplateService;
import com.huangjun.util.excel.service.impl.ExcelRuleServiceImpl;
import com.huangjun.util.excel.service.impl.ExcelServiceImpl;
import com.huangjun.util.excel.service.impl.ExcelTemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Map;

@Configuration
@ConditionalOnClass
public class ExcelConfiguration {
    @Autowired
    private ApplicationContext context;

    @Bean
    @ConditionalOnMissingBean(ExcelBizService.class)
    public ExcelBizService createExcelBizService(){
        Map<String, ExcelBizService> beans = context.getBeansOfType(ExcelBizService.class);
        if (beans == null || beans.size() == 0)
            throw new RuntimeException("请实现ExcelBizService接口");
        return new ArrayList<>(beans.values()).get(0);
    }

    @Bean
    @ConditionalOnMissingBean(ExcelService.class)
    public ExcelService excelService(){
        return new ExcelServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(IExcelTemplateService.class)
    public IExcelTemplateService excelTemplateService(){
        return new ExcelTemplateServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(IExcelRuleService.class)
    public IExcelRuleService excelRuleService(){
        return new ExcelRuleServiceImpl();
    }

}
