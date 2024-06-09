package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddLinkDto;
import com.taoshao.domain.dto.LinkListDto;
import com.taoshao.domain.dto.UpdateLinkDto;
import com.taoshao.domain.vo.GetLinkVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author taoshao
 * @Date 2024/6/9
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        PageVo pageVo = linkService.pageLinkList(pageNum,pageSize,linkListDto);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddLinkDto addLinkDto){
        return linkService.add(addLinkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        return linkService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseResult<GetLinkVo> getLinkById(@PathVariable Long id){
        GetLinkVo getLinkVo = linkService.getLinkById(id);
        return ResponseResult.okResult(getLinkVo);
    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateLinkDto updateLinkDto){
        return linkService.updateLink(updateLinkDto);
    }


}
