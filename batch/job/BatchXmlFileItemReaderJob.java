package com.zao.xjz.batch.job;

import com.zao.xjz.batch.entity.XmlVo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: xml文件读取
 **/
@Component
public class BatchXmlFileItemReaderJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job xmlFileItemReaderJob() {
        return jobBuilderFactory.get("xmlFileItemReaderJob")
                .start(xmlFileItemReaderStep())
                .build();
    }

    private Step xmlFileItemReaderStep() {
        return stepBuilderFactory.get("xmlFileItemReaderStep")
                .<XmlVo, XmlVo>chunk(2)
                .reader(xmlFileItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<XmlVo> xmlFileItemReader() {
        StaxEventItemReader<XmlVo> reader = new StaxEventItemReader<>();

        // 设置xml文件源
        reader.setResource(new ClassPathResource("file.xml"));

        // 指定xml文件的根标签
        reader.setFragmentRootElementName("test");

        // 将xml数据转换为XmlVo对象
        XStreamMarshaller marshaller = new XStreamMarshaller();

        // 指定需要转换的目标数据类型
        Map<String, Class<XmlVo>> map = new HashMap<>(1);
        map.put("test", XmlVo.class);
        marshaller.setAliases(map);
        reader.setUnmarshaller(marshaller);
        return reader;
    }
}
