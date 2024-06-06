package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.TagDto;
import com.taoshao.domain.dto.TagListDto;
import com.taoshao.domain.entity.Tag;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author taoshao
 * @Date 2024/6/4
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping("/content/tag")
    public ResponseResult addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }
}
