package com.zao.xjz.batch.job;

import com.zao.xjz.batch.entity.JsonVo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @description: json文件读取
 **/
@Component
public class BatchJsonFileItemReaderJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jsonFileItemReaderJob() {
        return jobBuilderFactory.get("jsonFileItemReaderJob")
                .start(jsonFileItemReaderStep())
                .build();
    }

    private Step jsonFileItemReaderStep() {
        return stepBuilderFactory.get("step")
                .<JsonVo, JsonVo>chunk(2)
                .reader(jsonItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<JsonVo> jsonItemReader() {
        // 设置json文件地址
        ClassPathResource resource = new ClassPathResource("file.json");

        // 设置json文件转换的目标对象类型
        JacksonJsonObjectReader<JsonVo> jacksonJsonObjectReader = new JacksonJsonObjectReader<>(JsonVo.class);
        JsonItemReader<JsonVo> reader = new JsonItemReader<>(resource, jacksonJsonObjectReader);

        // 给reader设置一个别名
        reader.setName("testDataJsonItemReader");
        return reader;
    }
}
