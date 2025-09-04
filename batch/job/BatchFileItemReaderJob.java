package com.zao.xjz.batch.job;

import com.zao.xjz.batch.entity.FileVo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @description: 文件数据读取
 */
@Component
public class BatchFileItemReaderJob {
    /**
     * 任务创建工厂
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * 步骤创建工厂
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job fileItemReaderJob() {
        return jobBuilderFactory.get("fileItemReaderJob")
                .start(fileItemReaderJobStep())
                .build();
    }

    private Step fileItemReaderJobStep() {
        return stepBuilderFactory.get("fileItemReaderJobStep")
                .<FileVo, FileVo>chunk(2)
                .reader(fileItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<FileVo> fileItemReader() {
        FlatFileItemReader<FileVo> reader = new FlatFileItemReader<>();

        // 设置文件资源地址
        reader.setResource(new ClassPathResource("file"));

        // 忽略第一行
        reader.setLinesToSkip(1);

        // AbstractLineTokenizer的三个实现类之一，以固定分隔符处理行数据读取,
        // 使用默认构造器的时候，使用逗号作为分隔符，也可以通过有参构造器来指定分隔符
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        // 设置属性名，类似于表头
        tokenizer.setNames("id", "field1", "field2", "field3");

        // 将每行数据转换为TestData对象
        DefaultLineMapper<FileVo> mapper = new DefaultLineMapper<>();

        // 设置LineTokenizer
        mapper.setLineTokenizer(tokenizer);

        // 设置映射方式，即读取到的文本怎么转换为对应的POJO
        mapper.setFieldSetMapper(fieldSet -> {
            FileVo fileVo = new FileVo();
            fileVo.setId(fieldSet.readInt("id"));
            fileVo.setField1(fieldSet.readString("field1"));
            fileVo.setField2(fieldSet.readString("field2"));
            fileVo.setField3(fieldSet.readString("field3"));
            return fileVo;
        });
        reader.setLineMapper(mapper);
        return reader;
    }

//    public FlatFileItemReader<Person> csvItemReader() {
//        FlatFileItemReader<Person> csvItemReader = new FlatFileItemReader<>();
//        csvItemReader.setResource(new ClassPathResource("data/sample-data.csv"));
//        csvItemReader.setLineMapper(new DefaultLineMapper<Person>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setNames(new String[]{"name", "age"});
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
//                setTargetType(Person.class);
//            }});
//        }});
//        return csvItemReader;
//    }
}
