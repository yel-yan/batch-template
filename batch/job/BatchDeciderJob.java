package com.zao.xjz.batch.job;

import com.zao.xjz.batch.decider.DateDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 决策器
 **/
@Component
public class BatchDeciderJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DateDecider dateDecider;

    @Bean
    public Job deciderJob() {
        // 首先执行deciderStep1(),然后使用自定义的日期决策器
        // 如果返回周末weekend那就执行deciderStep2()
        // 如果返回工作日workingDay那就执行deciderStep3()
        // 无论deciderStep3()的结果是什么都会执行deciderStep4()
        return jobBuilderFactory.get("deciderJob")
                .start(deciderStep1())
                .next(dateDecider)
                .from(dateDecider).on("weekend").to(deciderStep2())
                .from(dateDecider).on("workingDay").to(deciderStep3())
                .from(deciderStep3()).on("*").to(deciderStep4())
                .end()
                .build();
    }

    private Step deciderStep1() {
        return stepBuilderFactory.get("deciderStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("deciderStep1执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step deciderStep2() {
        return stepBuilderFactory.get("deciderStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("deciderStep2执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step deciderStep3() {
        return stepBuilderFactory.get("deciderStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("deciderStep3执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step deciderStep4() {
        return stepBuilderFactory.get("deciderStep4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("deciderStep4执行步骤四操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}