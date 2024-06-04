package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.Comment;
import com.taoshao.service.CommentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.taoshao.constants.SystemConstants.ARTICLE_COMMENT;
import static com.taoshao.constants.SystemConstants.LINK_COMMENT;

/**
 * @Author taoshao
 * @Date 2024/6/1
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")

    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(LINK_COMMENT, null, pageNum, pageSize);
    }
}
