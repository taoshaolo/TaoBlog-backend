package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddArticleDto;
import com.taoshao.domain.dto.ArticleListDto;
import com.taoshao.domain.dto.UpdateArticleDto;
import com.taoshao.domain.entity.Article;
import com.taoshao.domain.vo.ArticleVo;
import com.taoshao.domain.vo.PageVo;

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

    /**
     * 添加博文
     * @param addArticleDto
     * @return
     */
    ResponseResult add(AddArticleDto addArticleDto);

    /**
     * 分页查询文章列表w
     * @param pageNum
     * @param pageSize
     * @param articleListDto
     * @return
     */
    PageVo pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    ArticleVo getArticleById(Long id);

    /**
     * 更新文章
     *
     * @param updateArticleDto
     * @return
     */
    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    /**
     * 删除文章
     * @param id
     * @return
     */
    ResponseResult delete(Long id);
}
