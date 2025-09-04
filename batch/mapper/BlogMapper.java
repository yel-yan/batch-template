package com.zao.xjz.batch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zao.xjz.batch.entity.BlogInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BlogMapper extends BaseMapper<BlogInfo> {
    @Insert("INSERT INTO bloginfo ( blogAuthor, blogUrl, blogTitle, blogItem )   VALUES ( #{blogAuthor}, #{blogUrl},#{blogTitle},#{blogItem}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BlogInfo bloginfo);

    @Select("select blogAuthor, blogUrl, blogTitle, blogItem from bloginfo where blogAuthor < #{authorId}")
    List<BlogInfo> queryInfoById(Map<String , Integer> map);
}
