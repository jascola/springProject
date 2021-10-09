package com.jascola.spring.business.biz.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jascola.spring.business.biz.ResourceAvBookService;
import com.jascola.spring.business.entity.AvBookEntity;
import com.jascola.spring.business.mapper.ResourceAvBookMapper;
import org.springframework.stereotype.Service;

@Service
public class ResourceAvBookBiz  extends ServiceImpl<ResourceAvBookMapper, AvBookEntity> implements ResourceAvBookService {


}
