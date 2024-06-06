package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.TagDto;
import com.taoshao.domain.dto.TagListDto;
import com.taoshao.domain.entity.Tag;
import com.taoshao.domain.vo.PageVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author taoshao
 * @since 2024-06-04 20:41:29
 */
public interface TagService extends IService<Tag> {
    /**
     * 分页查询标签列表
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 添加标签
     * @param tagDto
     * @return
     */
    ResponseResult addTag(TagDto tagDto);
}

