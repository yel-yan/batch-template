package com.zao.xjz.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * @description: 简单的数据读取，泛型为读取数据的类型
 **/
public class BatchSimpleItemReader implements ItemReader<String> {
    private Iterator<String> iterator;

    public BatchSimpleItemReader(List<String> data) {
        this.iterator = data.iterator();
    }

    /**
     * 数据一个接着一个读取
     *
     * @return
     * @throws Exception
     * @throws UnexpectedInputException
     * @throws ParseException
     * @throws NonTransientResourceException
     */
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 遍历集合的数据 NAVIGATION, 返回null时代表数据读取已完成
        return iterator.hasNext() ? iterator.next() : null;
    }
}