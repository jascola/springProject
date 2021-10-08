package com.jascola.spring.business.mapper;

import com.jascola.spring.business.entity.AvBookEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceAvBookMapper {

     AvBookEntity getAvBookEntityById(Long id);

}
