package com.taoshao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.entity.ArticleTag;
import com.taoshao.mapper.ArticleTagMapper;
import com.taoshao.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-06 18:46:39
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

