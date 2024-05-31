package com.taoshao.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.constants.SystemConstants;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.Article;
import com.taoshao.domain.entity.Category;
import com.taoshao.domain.vo.CategoryVo;
import com.taoshao.mapper.CategoryMapper;
import com.taoshao.service.ArticleService;
import com.taoshao.service.CategoryService;
import com.taoshao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.taoshao.constants.SystemConstants.ARTICLE_STATUS_NORMAL;
import static com.taoshao.constants.SystemConstants.STATUS_NORMAL;

/**
 * 分类表(Category)表服务实现类
 *
 * @author taoshao
 * @since 2024-05-30 09:46:16
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表状态为0（已发布）
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId()).collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);

        categories = categories.stream()
                .filter(category -> STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}

