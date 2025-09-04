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
import org.springframework.stereotype.Component;

/**
 * @description: 一个Flow包含多个Step
 **/
@Component
public class BatchFlowJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                .start(flow())
                .next(flowStep3())
                .end()
                .build();
    }

    private Step flowStep1() {
        return stepBuilderFactory.get("flowStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("flowStep1执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step flowStep2() {
        return stepBuilderFactory.get("flowStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("flowStep2执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step flowStep3() {
        return stepBuilderFactory.get("flowStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("flowStep3执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * 创建一个Flow对象，包含若干个Step
     *
     * @return
     */
    private Flow flow() {
        return new FlowBuilder<Flow>("flow")
                .start(flowStep1())
                .next(flowStep2())
                .build();
    }
}