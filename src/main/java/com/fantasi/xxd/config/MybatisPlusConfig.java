package com.fantasi.xxd.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxd
 * @date 2019/12/27 9:57
 */
@Configuration
@MapperScan(basePackages = {"com.fantasi.xxd.dao"})
public class MybatisPlusConfig {

    @ConfigurationProperties(prefix = "config.datasource.db1")
    @Bean("db1Source")
    public DataSource db1Source() {
        System.out.println("---------- db1 datasource init ----------");
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @ConfigurationProperties(prefix = "config.datasource.db2")
    @Bean("db2Source")
    public DataSource db2Source() {
        System.out.println("---------- db2 datasource init ----------");
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     * @return
     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor(){
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
//        performanceInterceptor.setMaxTime(1000);
//        /*<!--SQL是否格式化 默认false-->*/
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

    /**
     * mybatis-plus 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setLimit(1000);
    }

    @Bean("multipleDataSource")
    @Primary
    public DataSource multipleDataSource(@Qualifier("db1Source") DataSource db1,
                                         @Qualifier("db2Source") DataSource db2) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DBTypeEnum.db1Source.getValue(), db1);
        targetDataSource.put(DBTypeEnum.db2Source.getValue(), db2);
        dynamicDataSource.setTargetDataSources(targetDataSource);
        dynamicDataSource.setDefaultTargetDataSource(db1);
        return dynamicDataSource;
    }

//    /**
//     * 配置事务管理器
//     */
//    @Bean
//    public DataSourceTransactionManager transactionManager(@Qualifier("multipleDataSource")DynamicDataSource multipleDataSource) throws Exception {
//        return new DataSourceTransactionManager(multipleDataSource);
//    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("multipleDataSource") DataSource multipleDataSource) throws Exception {
        System.out.println("==============================");
        //***导入MybatisSqlSession配置***
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        //指明数据源
        sqlSessionFactory.setDataSource(multipleDataSource);
        //指明mapper.xml位置(配置文件中指明的xml位置会失效用此方式代替，具体原因未知)
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**Mapper.xml"));
        //指明实体扫描(多个package用逗号或者分号分隔   )
        sqlSessionFactory.setTypeAliasesPackage("com.fantasi.xxd.entity.**");
        //***导入Mybatis配置***
        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.init(globalConfiguration());
//        StdOutImpl stdOut = new StdOutImpl("org.apache.ibatis.logging.stdout.StdOutImpl");
//        configuration.setLogImpl(stdOut);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor()});
        //***导入全局配置***
        sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }

    /**
     * 在代码中配置MybatisPlus替换掉application.yml中的配置
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfiguration() {
//        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
//        //主键类型 0:数据库ID自增, 1:用户输入ID,2:全局唯一ID (数字类型唯一ID), 3:全局唯一ID UUID
//        conf.setIdType(0);
//        //字段策略(拼接sql时用于判断属性值是否拼接) 0:忽略判断,1:非NULL判断,2:非空判断
//        conf.setFieldStrategy(2);
//        //驼峰下划线转换含查询column及返回column(column下划线命名create_time，返回java实体是驼峰命名createTime，开启后自动转换否则保留原样)
//        conf.setDbColumnUnderline(true);
//        //是否动态刷新mapper
//        conf.setRefresh(true);
//        return conf;
        // 全局配置文件
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        // 默认为自增
        dbConfig.setIdType(IdType.AUTO);
        // 手动指定db 的类型, 这里是mysql
//        dbConfig.setDbType(DbType.MYSQL);
        globalConfig.setDbConfig(dbConfig);
//        globalConfig.setRefresh(true);
//        if (!ProjectStageUtil.isProd(projectStage)) {
//            // 如果是dev环境,则使用 reload xml的功能,方便调试
//            globalConfig.setRefresh(true);
//        }
        // 逻辑删除注入器
//        LogicSqlInjector injector = new LogicSqlInjector();
//        globalConfig.setSqlInjector(injector);
        return globalConfig;
    }
}
