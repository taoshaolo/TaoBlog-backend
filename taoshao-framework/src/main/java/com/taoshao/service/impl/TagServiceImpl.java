package com.taoshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.TagListDto;
import com.taoshao.domain.entity.Tag;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.domain.vo.TagVo;
import com.taoshao.mapper.TagMapper;
import com.taoshao.service.TagService;
import com.taoshao.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签(Tag)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-04 20:41:31
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        // 分页查询并转换为TagVo列表
        List<TagVo> tagVoList = page(page, queryWrapper)
                .getRecords()
                .stream()
                .map(tag -> {
                    TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
                    return tagVo;
                }).collect(Collectors.toList());
        // 封装数据返回
        PageVo pageVo = new PageVo(tagVoList,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}

