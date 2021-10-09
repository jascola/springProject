package com.jascola.spring.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_av_book")
public class AvBookEntity {

    private Long id;

    private String worksName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }
}
