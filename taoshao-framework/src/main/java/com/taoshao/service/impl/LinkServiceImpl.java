package com.taoshao.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddLinkDto;
import com.taoshao.domain.dto.LinkListDto;
import com.taoshao.domain.dto.UpdateLinkDto;
import com.taoshao.domain.entity.Link;
import com.taoshao.domain.vo.GetLinkVo;
import com.taoshao.domain.vo.LinkVo;
import com.taoshao.domain.vo.PageLinkListVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.mapper.LinkMapper;
import com.taoshao.service.LinkService;
import com.taoshao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.taoshao.constants.SystemConstants.LINK_STATUS_NORMAL;

/**
 * 友链(Link)表服务实现类
 *
 * @author taoshao
 * @since 2024-05-30 16:33:25
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public PageVo pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkListDto.getName()),Link::getName,linkListDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkListDto.getStatus()),Link::getStatus,linkListDto.getStatus());


        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        //Link 封装成 PageLinkListVo
        List<PageLinkListVo> pageLinkListVos = page(page, queryWrapper).getRecords()
                .stream()
                .map(link -> BeanCopyUtils.copyBean(link, PageLinkListVo.class))
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(pageLinkListVos, page.getTotal());
        return pageVo;
    }

    @Override
    public ResponseResult add(AddLinkDto addLinkDto) {
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public GetLinkVo getLinkById(Long id) {
        Link link = getById(id);
        GetLinkVo getLinkVo = BeanCopyUtils.copyBean(link, GetLinkVo.class);
        return getLinkVo;
    }

    @Override
    public ResponseResult updateLink(UpdateLinkDto updateLinkDto) {
        Link link = BeanCopyUtils.copyBean(updateLinkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }
}

