package com.i2f.framework.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@MapperScan(basePackages = "com.i2f.dao2",sqlSessionTemplateRef = "sqlSessionTemplateRefSecondary")
@ConditionalOnProperty(prefix = "spring.datasource.secondary", name = "enabled", havingValue = "true")
public class DataSourceSecondaryConfig {

    @Value("${spring.datasource.secondary.mappers-location:classpath:mapper2/**/*Mapper.xml}")
    private String mappersLocation;

    public String getMappersLocation() {
        return mappersLocation;
    }

    public void setMappersLocation(String mappersLocation) {
        this.mappersLocation = mappersLocation;
    }

    @Bean(name = "dataSourceSecondary")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="sqlSessionFactorySecondary")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("dataSourceSecondary")DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mappersLocation));
        return bean.getObject();
    }

    @Bean(name="transactionManagerSecondary")
    public DataSourceTransactionManager secondaryTransactionManager(@Qualifier("dataSourceSecondary")DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="sqlSessionTemplateRefSecondary")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("sqlSessionFactorySecondary") SqlSessionFactory sqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
