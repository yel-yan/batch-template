package com.zao.xjz.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 多任务执行
 **/
@Component
public class BatchMultiJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job multiJob() {
        return jobBuilderFactory.get("multiJob")
                .start(multiStep1())
                .next(multiStep2())
                .next(multiStep3())
                .build();
    }

    private Step multiStep1() {
        return stepBuilderFactory.get("multiStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("multiStep1执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step multiStep2() {
        return stepBuilderFactory.get("multiStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("multiStep2执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step multiStep3() {
        return stepBuilderFactory.get("multiStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("multiStep3执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}