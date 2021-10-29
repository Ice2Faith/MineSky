package com.i2f.batch.jobs;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author ltb
 * @date 2021/9/18
 */
@Configuration
@EnableScheduling
public class ScheduleingJobConfig{

    @Autowired
    @Qualifier("handleJob")
    private Job jpaJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(fixedDelay = 30*1000L)
    public void doJpaJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        System.out.println("schedule job start.");
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .toJobParameters();
        JobExecution run = jobLauncher.run(jpaJob, jobParameter);
        Long id=run.getId();
        System.out.println("schedule job end:"+id);
    }
}
