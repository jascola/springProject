package com.jascola.spring.business.biz;

import com.jascola.spring.business.entity.AvBookEntity;
import com.jascola.spring.business.mapper.ResourceAvBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceAvBookBiz {

    @Autowired
    private ResourceAvBookMapper resourceAvBookMapper;

    public AvBookEntity getAvBookEntityById(Long id){
        return resourceAvBookMapper.getAvBookEntityById(id);
    }



}
