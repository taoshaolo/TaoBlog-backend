package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.TagDto;
import com.taoshao.domain.dto.TagListDto;
import com.taoshao.domain.dto.TagDto;
import com.taoshao.domain.entity.Tag;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.domain.vo.TagVo;

import java.util.List;


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

    /**
     * 根据id删除标签
     * @param id
     * @return
     */
    ResponseResult deleteTagById(Long id);

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    TagVo getTagById(Long id);

    /**
     * 修改标签
     * @param tagDto
     * @return
     */
    ResponseResult updateTag(TagDto tagDto);

    /**
     * 查询所有标签
     * @return
     */
    List<TagVo> listAllTag();
}

