package com.zao.xjz.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @description: 并行处理
 **/
@Component
public class BatchSplitJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job splitJob() {
        return jobBuilderFactory.get("splitJob")
                .start(splitFlow1())
                .split(new SimpleAsyncTaskExecutor()).add(splitFlow2())
                .end()
                .build();
    }

    private Flow splitFlow1() {
        return new FlowBuilder<Flow>("splitFlow1")
                .start(splitStep1())
                .next(splitStep2())
                .build();
    }

    private Flow splitFlow2() {
        return new FlowBuilder<Flow>("splitFlow2")
                .start(splitStep3())
                .build();
    }

    private Step splitStep1() {
        return stepBuilderFactory.get("splitStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("splitStep1执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step splitStep2() {
        return stepBuilderFactory.get("splitStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("splitStep2执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step splitStep3() {
        return stepBuilderFactory.get("splitStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("splitStep3执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
