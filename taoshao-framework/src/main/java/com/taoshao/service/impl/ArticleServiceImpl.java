package com.taoshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddArticleDto;
import com.taoshao.domain.dto.ArticleListDto;
import com.taoshao.domain.dto.UpdateArticleDto;
import com.taoshao.domain.entity.Article;
import com.taoshao.domain.entity.ArticleTag;
import com.taoshao.domain.entity.Category;
import com.taoshao.domain.enums.AppHttpCodeEnum;
import com.taoshao.domain.vo.*;
import com.taoshao.exception.SystemException;
import com.taoshao.mapper.ArticleMapper;
import com.taoshao.service.ArticleService;
import com.taoshao.service.ArticleTagService;
import com.taoshao.service.CategoryService;
import com.taoshao.utils.BeanCopyUtils;
import com.taoshao.utils.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.taoshao.constants.RedisConstants.ARTICLE_VIEWCOUNT_KEY;
import static com.taoshao.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * @Author taoshao
 * @Date 2024/5/29
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章（不是草稿）
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        //从 redis 中获取 viewCount
        for (Article article : articles) {
            Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEWCOUNT_KEY, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有 categoryId 就要求查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //状态为正式发布到的
        lambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //对 isTop 进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        List<Article> articles = page.getRecords();

        //从 redis 中获取 viewCount
        for (Article article : articles) {
            Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEWCOUNT_KEY, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }


        //查询 categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从 redis 中获取 viewCount
        Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEWCOUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());
        //转化成Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新 redis 中对应 id 的浏览量
        redisCache.incrementCacheMapValue(ARTICLE_VIEWCOUNT_KEY, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto addArticleDto) {
        //校验
        if (addArticleDto == null) {
            throw new SystemException(AppHttpCodeEnum.PARAMS_ERROR);
        }
        if (addArticleDto.getCategoryId() == null) {
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_CAN_EMPTY);
        }
        String title = addArticleDto.getTitle();
        String content = addArticleDto.getContent();
        String summary = addArticleDto.getSummary();
        List<Long> tags = addArticleDto.getTags();
        String thumbnail = addArticleDto.getThumbnail();
        if (CollectionUtils.isEmpty(tags)) {
            throw new SystemException(AppHttpCodeEnum.TAGS_NOT_CAN_EMPTY);
        }
        if (StringUtils.isBlank(title)) {
            throw new SystemException(AppHttpCodeEnum.TITLE_NOT_CAN_EMPTY);
        }
        if (StringUtils.isBlank(content)) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_CONTENT_NOT_CAN_EMPTY);
        }
        if (StringUtils.isBlank(summary)) {
            throw new SystemException(AppHttpCodeEnum.SUMMARY_NOT_CAN_EMPTY);
        }
        if (StringUtils.isBlank(thumbnail)) {
            throw new SystemException(AppHttpCodeEnum.THUMBNAIL_NOT_CAN_EMPTY);
        }

        //添加 博客
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = tags.stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }


    @Override
    public PageVo pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(articleListDto.getTitle()), Article::getTitle, articleListDto.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(articleListDto.getSummary()), Article::getSummary, articleListDto.getSummary());

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        List<PageArticleListVo> pageArticleListVos = page(page, queryWrapper)
                .getRecords()
                .stream()
                .map(article -> {
                    PageArticleListVo pageArticleListVo = BeanCopyUtils.copyBean(article, PageArticleListVo.class);
                    return pageArticleListVo;
                }).collect(Collectors.toList());

        PageVo pageVo = new PageVo(pageArticleListVos, page.getTotal());
        return pageVo;
    }

    @Override
    public ArticleVo getArticleById(Long id) {
        Article article = getById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        List<Long> tagIds = articleTagService.getBaseMapper().selectList(queryWrapper)
                .stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        articleVo.setTags(tagIds);

        return articleVo;
    }

    @Override
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
