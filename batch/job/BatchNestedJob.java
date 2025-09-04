package com.zao.xjz.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @description: 任务嵌套
 **/
@Component
public class BatchNestedJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    // 任务仓库
    @Autowired
    private JobRepository jobRepository;

    // 事务管理器
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    /**
     * 父任务
     * @return
     */
    @Bean
    public Job parentJob() {
        return jobBuilderFactory.get("parentJob")
                .start(childJobOneStep())
                .next(childJobTwoStep())
                .build();
    }

    /**
     * 将任务转换为特殊的步骤，将任务childJobOne()转成步骤childJobOneStep()
     * @return
     */
    private Step childJobOneStep() {
        return new JobStepBuilder(new StepBuilder("childJobOneStep"))
                .job(childJobOne())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * 将任务转换为特殊的步骤，将任务childJobTwo()转成步骤childJobTwoStep()
     * @return
     */
    private Step childJobTwoStep() {
        return new JobStepBuilder(new StepBuilder("childJobTwoStep"))
                .job(childJobTwo())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * 子任务一，Job执行子任务一
     * @return
     */
    private Job childJobOne() {
        return jobBuilderFactory.get("childJobOne")
                .start(
                        stepBuilderFactory.get("childJobOneStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务一执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }

    /**
     * 子任务二，Job执行子任务二
     * @return
     */
    private Job childJobTwo() {
        return jobBuilderFactory.get("childJobTwo")
                .start(
                        stepBuilderFactory.get("childJobTwoStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务二执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }

}
