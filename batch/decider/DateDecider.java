package com.zao.xjz.batch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * @description: 日期决策器
 **/
@Component
public class DateDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        // 判断今天是周末还是工作日
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        System.out.println("今天是: " + dayOfWeek);

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return new FlowExecutionStatus("weekend");
        } else {
            return new FlowExecutionStatus("workingDay");
        }
    }
}