package com.zao.xjz.batch.job;

import com.zao.xjz.batch.entity.TestVo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 数据库数据读取
 **/
@Component
public class BatchDataSourceItemReaderJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 注入数据源
     */
    @Autowired
    private DataSource dataSource;

    @Bean
    public Job dataSourceItemReaderJob() throws Exception {
        return jobBuilderFactory.get("dataSourceItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() throws Exception {
        return stepBuilderFactory.get("step")
                .<TestVo, TestVo>chunk(2)
                .reader(dataSourceItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ItemReader<TestVo> dataSourceItemReader() throws Exception {
        JdbcPagingItemReader<TestVo> reader = new JdbcPagingItemReader<>();
        // 设置数据源
        reader.setDataSource(dataSource);
        // 每次取多少条记录
        reader.setFetchSize(5);
        // 设置每页数据量
        reader.setPageSize(5);

        // 指定sql查询语句 select id,field1,field2,field3 from TEST
        //MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();
        // 这里使用Oracle
        OraclePagingQueryProvider oraclePagingQueryProvider = new OraclePagingQueryProvider();
        //设置查询字段
        oraclePagingQueryProvider.setSelectClause("id,field1,field2,field3");
        // 设置从哪张表查询
        oraclePagingQueryProvider.setFromClause("from TEST");

        // 将读取到的数据转换为Test对象
        reader.setRowMapper((resultSet, rowNum) -> {
            TestVo testVo = new TestVo();
            testVo.setId(resultSet.getInt(1));
            // 读取第一个字段，类型为String
            testVo.setField1(resultSet.getString(2));
            testVo.setField2(resultSet.getString(3));
            testVo.setField3(resultSet.getString(4));
            return testVo;
        });

        Map<String, Order> sort = new HashMap<>(1);
        sort.put("id", Order.ASCENDING);
        // 设置排序,通过id 升序
        oraclePagingQueryProvider.setSortKeys(sort);
        reader.setQueryProvider(oraclePagingQueryProvider);
        // 设置namedParameterJdbcTemplate等属性
        reader.afterPropertiesSet();
        return reader;
    }
}
