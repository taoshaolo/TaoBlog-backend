package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.TagDto;
import com.taoshao.domain.dto.TagListDto;
import com.taoshao.domain.dto.TagDto;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.domain.vo.TagVo;
import com.taoshao.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTagById(@PathVariable Long id){
        return tagService.deleteTagById(id);
    }

    @GetMapping("/{id}")
    public ResponseResult<TagVo> getTagById(@PathVariable Long id){
        TagVo tagVo = tagService.getTagById(id);
        return ResponseResult.okResult(tagVo);
    }
    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto){
        return tagService.updateTag(tagDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}
