package com.zao.xjz.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchLambdaJob {

    /**
     * 创建任务对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * 执行任务对象
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 创建任务
     *
     * @return
     */
    @Bean
    public Job lambdaJob() {
        return jobBuilderFactory.get("lambdaJob")
                // 执行lambdaStep
                .start(lambdaStep()).build();
    }

    public Step lambdaStep() {
        return stepBuilderFactory.get("lambdaStep").tasklet((stepContribution, chunkContext) -> {
                    System.out.println("lambdaStep执行步骤....");
                    return RepeatStatus.FINISHED;
                }
        ).build();
    }
}