package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.Article;

/**
 * @Author taoshao
 * @Date 2024/5/29
 */
public interface ArticleService extends IService<Article> {

    /**
     * 热门文章列表
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 文章列表
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 文章详情
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 更新文章浏览量
     * @param id
     * @return
     */
    ResponseResult updateViewCount(Long id);
}
