package com.taoshao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.taoshao.domain.entity.Tag;
import com.taoshao.mapper.TagMapper;
import com.taoshao.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-04 20:41:31
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

