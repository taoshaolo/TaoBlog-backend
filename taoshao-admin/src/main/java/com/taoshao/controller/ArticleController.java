package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddArticleDto;
import com.taoshao.domain.dto.ArticleListDto;
import com.taoshao.domain.dto.UpdateArticleDto;
import com.taoshao.domain.vo.ArticleVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author taoshao
 * @Date 2024/6/7
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        PageVo pageVo = articleService.pageArticleList(pageNum, pageSize, articleListDto);
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping("/{id}")
    public ResponseResult<ArticleVo> getArticleById(@PathVariable Long id) {
        ArticleVo articleVo = articleService.getArticleById(id);
        return ResponseResult.okResult(articleVo);

    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        return articleService.delete(id);
    }
}
