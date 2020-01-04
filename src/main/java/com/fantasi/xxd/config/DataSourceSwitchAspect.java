package com.fantasi.xxd.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xxd
 * @date 2019/12/27 14:52
 */
@Component
@Aspect
@Order(-100) //保证AOP在事务注解之前生效,Order的值越小,优先级越高
public class DataSourceSwitchAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@within(com.fantasi.xxd.config.DataSource) || @annotation(com.fantasi.xxd.config.DataSource)")
    public void pointCut(){

    }

    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        logger.info("选择数据源---"+dataSource.value().getValue());
        DbContextHolder.setDbType(dataSource.value());
    }

    @After("pointCut()")
    public void doAfter(){
        DbContextHolder.clearDbType();
    }
}
