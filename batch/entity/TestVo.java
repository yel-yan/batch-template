package com.zao.xjz.batch.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import java.io.Serializable;

/**
 * @description: 实体类
 */
@Data
public class TestVo extends Model<TestVo> {
    /**
     * 编号
     */

    private Integer id;
    /**
     * 字段1
     */

    private Object field1;
    /**
     * 字段2
     */

    private Object field2;
    /**
     * 字段3
     */

    private Object field3;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
