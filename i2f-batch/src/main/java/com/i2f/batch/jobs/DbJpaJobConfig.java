package com.i2f.batch.jobs;

import com.i2f.batch.listener.SimpleItemProcessLister;
import com.i2f.batch.listener.SimpleItemReadListener;
import com.i2f.batch.listener.SimpleItemWriteListener;
import com.i2f.batch.listener.SimpleJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ltb
 * @date 2021/9/18
 */
@Configuration
@EnableBatchProcessing
public class DbJpaJobConfig {
    private Logger logger = LoggerFactory.getLogger(DbJpaJobConfig.class);

    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Resource
    private EntityManagerFactory emf;

    @Resource
    private SimpleJobListener simpleJobListener;

    @Resource
    private SimpleItemReadListener simpleItemReadListener;

    @Resource
    private SimpleItemProcessLister simpleItemProcessLister;

    @Resource
    private SimpleItemWriteListener simpleItemWriteListener;

    @Bean
    public Job handleJob(){
        return jobBuilderFactory.get("handleJob")
                .incrementer(new RunIdIncrementer())
                .start(handleStartStep()) // 转换处理密码，电话号码等敏感信息
                .next(handleNextStep()) // 转换处理性别解码
                .listener(simpleJobListener)
                .build();
    }

    @Bean
    public Step handleStartStep(){
        return stepBuilderFactory.get("handleStartStep")
                .<UserBean, UserSecurityBean>chunk(10)
                .faultTolerant().retryLimit(3).retry(Exception.class).skip(SQLException.class).skipLimit(20)
                .reader(startReader())
                .processor(startProcessor())
                .writer(startWriter())
                .listener(simpleItemReadListener)
                .listener(simpleItemProcessLister)
                .listener(simpleItemWriteListener)
                .build();
    }

    @Bean
    public ItemReader<? extends UserBean> startReader(){
        JpaPagingItemReader<UserBean> reader=new JpaPagingItemReader<>();
        String sql="select * from sys_user";

        try{
            JpaNativeQueryProvider<UserBean> provider=new JpaNativeQueryProvider<>();
            provider.setSqlQuery(sql);
            provider.setEntityClass(UserBean.class);
            provider.afterPropertiesSet();

            reader.setEntityManagerFactory(emf);
            reader.setPageSize(3);
            reader.setQueryProvider(provider);
            reader.afterPropertiesSet();

            reader.setSaveState(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return reader;
    }

    @Bean
    public ItemProcessor<UserBean, UserSecurityBean> startProcessor(){
        return new ItemProcessor<UserBean, UserSecurityBean>() {
            @Override
            public UserSecurityBean process(UserBean userBean) throws Exception {
                UserSecurityBean securityBean=new UserSecurityBean();
                securityBean.setId(userBean.getId());
                securityBean.setAccount(userBean.getAccount());
                securityBean.setPassword(userBean.getPassword());
                securityBean.setAge(userBean.getAge());
                securityBean.setSex(userBean.getSex());
                securityBean.setTel(userBean.getTel());
                securityBean.setPassword("******");
                String tel=userBean.getTel();
                if(tel!=null && tel.length()>5){
                    tel="***"+tel.substring(tel.length()-4,tel.length());
                }
                securityBean.setTel(tel);
                return securityBean;
            }
        };
    }

    @Bean
    public ItemWriter<UserSecurityBean> startWriter(){
        return new ItemWriter<UserSecurityBean>() {
            @Override
            public void write(List<? extends UserSecurityBean> list) throws Exception {
                JpaItemWriter<UserSecurityBean> writer=new JpaItemWriter<>();
                writer.setEntityManagerFactory(emf);
                writer.afterPropertiesSet();
                writer.write(list);
            }
        };
    }


    ///////////////////////////////////////////////////////////////////////

    @Bean
    public Step handleNextStep(){
        return stepBuilderFactory.get("handleNextStep")
                .<UserSecurityBean, UserWebBean>chunk(10)
                .faultTolerant().retryLimit(3).retry(Exception.class).skip(SQLException.class).skipLimit(20)
                .reader(nextReader())
                .processor(nextProcessor())
                .writer(nextWriter())
                .listener(simpleItemReadListener)
                .listener(simpleItemProcessLister)
                .listener(simpleItemWriteListener)
                .build();
    }

    @Bean
    public ItemReader<? extends UserSecurityBean> nextReader(){
        JpaPagingItemReader<UserSecurityBean> reader=new JpaPagingItemReader<>();
        String sql="select * from sys_user_secure";

        try{
            JpaNativeQueryProvider<UserSecurityBean> provider=new JpaNativeQueryProvider<>();
            provider.setSqlQuery(sql);
            provider.setEntityClass(UserSecurityBean.class);
            provider.afterPropertiesSet();

            reader.setEntityManagerFactory(emf);
            reader.setPageSize(2);
            reader.setQueryProvider(provider);
            reader.afterPropertiesSet();

            reader.setSaveState(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return reader;
    }

    @Bean
    public ItemProcessor<UserSecurityBean, UserWebBean> nextProcessor(){
        return new ItemProcessor<UserSecurityBean, UserWebBean>() {
            @Override
            public UserWebBean process(UserSecurityBean userBean) throws Exception {
                UserWebBean webBean=new UserWebBean();
                webBean.setId(userBean.getId());
                webBean.setAccount(userBean.getAccount());
                webBean.setPassword(userBean.getPassword());
                webBean.setAge(userBean.getAge());
                webBean.setSex(userBean.getSex());
                webBean.setTel(userBean.getTel());
                String sex=userBean.getSex();
                if("1".equals(sex)){
                    sex="男";
                }else if("2".equals(sex)){
                    sex="女";
                }else{
                    sex="未知";
                }
                String ageDesc="";
                int age=userBean.getAge();
                if(age>150){
                    ageDesc="这是什么年代，这么年长了";
                }
                else if(age>100){
                    ageDesc="百岁老人";
                }else if(age>80){
                    ageDesc="高龄老人";
                }else if(age>60){
                    ageDesc="退休老人";
                }else if(age>45){
                    ageDesc="中老年人";
                }else if(age>30){
                    ageDesc="中年人";
                }else if(age>18){
                    ageDesc="成年人";
                }else if(age>12){
                    ageDesc="青年人";
                }else if(age>6){
                    ageDesc="小学生";
                }else if(age>3){
                    ageDesc="小孩子";
                }else if(age>=0){
                    ageDesc="婴幼儿";
                }else{
                    ageDesc="别闹，你这没出生呢！";
                }

                webBean.setAgeDesc(ageDesc);
                webBean.setSex(sex);
                return webBean;
            }
        };
    }

    @Bean
    public ItemWriter<UserWebBean> nextWriter(){
        return new ItemWriter<UserWebBean>() {
            @Override
            public void write(List<? extends UserWebBean> list) throws Exception {
                JpaItemWriter<UserWebBean> writer=new JpaItemWriter<>();
                writer.setEntityManagerFactory(emf);
                writer.afterPropertiesSet();
                writer.write(list);
            }
        };
    }
}
