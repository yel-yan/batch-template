package com.zao.xjz.batch.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String blogAuthor;
    private String blogUrl;
    private String blogTitle;
    private String blogItem;
}
