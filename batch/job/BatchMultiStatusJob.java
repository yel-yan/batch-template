package com.zao.xjz.batch.job;



import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 多任务根据状态执行
 **/
@Component
public class BatchMultiStatusJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job multiStatusJob() {
        return jobBuilderFactory.get("multiStatusJob")
                .start(multiStatusStep1())
                .on(ExitStatus.COMPLETED.getExitCode()).to(multiStatusStep2())
                .from(multiStatusStep2())
                .on(ExitStatus.COMPLETED.getExitCode()).to(multiStatusStep3())
                .from(multiStatusStep3())
                .end()
                .build();
    }

    private Step multiStatusStep1() {
        return stepBuilderFactory.get("multiStatusStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("multiStatusStep1执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step multiStatusStep2() {
        return stepBuilderFactory.get("multiStatusStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("multiStatusStep2执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step multiStatusStep3() {
        return stepBuilderFactory.get("multiStatusStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("multiStatusStep3执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}