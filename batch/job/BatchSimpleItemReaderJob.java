package com.zao.xjz.batch.job;

import com.zao.xjz.batch.reader.BatchSimpleItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 简单的数据读取
 **/
@Component
public class BatchSimpleItemReaderJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleItemReaderJob() {
        return jobBuilderFactory
                .get("simpleItemReaderJob")
                .start(simpleItemReaderStep())
                .build();
    }

    public Step simpleItemReaderStep() {
        return stepBuilderFactory
                .get("simpleItemReaderStep")
                .<String, String>chunk(2)
                .reader(simpleItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    public ItemReader<String> simpleItemReader() {
        List<String> strings = Arrays.asList("存款", "余额", "资金","冻结");
        return new BatchSimpleItemReader(strings);
    }
}
