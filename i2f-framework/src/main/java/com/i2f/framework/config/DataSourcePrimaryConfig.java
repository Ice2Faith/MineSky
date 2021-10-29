package com.i2f.framework.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author ltb
 * @date 2021/8/31
 */
@Configuration
@MapperScan(basePackages = "com.i2f.dao",sqlSessionTemplateRef = "sqlSessionTemplateRefPrimary")
public class DataSourcePrimaryConfig {

    @Value("${spring.datasource.primary.mappers-location:classpath:mapper/*.xml}")
    private String mappersLocation;

    public String getMappersLocation() {
        return mappersLocation;
    }

    public void setMappersLocation(String mappersLocation) {
        this.mappersLocation = mappersLocation;
    }

    @Bean(name = "dataSourcePrimary")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    @Primary
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="sqlSessionFactoryPrimary")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("dataSourcePrimary")DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mappersLocation));
        return bean.getObject();
    }

    @Bean(name="transactionManagerPrimary")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("dataSourcePrimary")DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="sqlSessionTemplateRefPrimary")
    @Primary
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("sqlSessionFactoryPrimary") SqlSessionFactory sqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
