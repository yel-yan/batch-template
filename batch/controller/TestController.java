package com.zao.xjz.batch.controller;

import com.zao.xjz.aspect.annotation.NoNeedAuth;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("api/test")
@NoNeedAuth
public class TestController {
    @Autowired
    SimpleJobLauncher jobLauncher;

    @Autowired
    Job myJob;

    @GetMapping("testJob")
    public  void testJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyCompleteException, NoSuchJobException, NoSuchJobExecutionException {
        //    后置参数：使用JobParameters中绑定参数 addLong addString 等方法
        JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
        jobLauncher.run(myJob, jobParameters);
    }
}
