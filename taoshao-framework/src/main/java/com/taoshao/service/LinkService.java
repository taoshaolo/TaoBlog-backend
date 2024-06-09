package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddLinkDto;
import com.taoshao.domain.dto.LinkListDto;
import com.taoshao.domain.dto.UpdateLinkDto;
import com.taoshao.domain.entity.Link;
import com.taoshao.domain.vo.GetLinkVo;
import com.taoshao.domain.vo.PageVo;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 友链(Link)表服务接口
 *
 * @author taoshao
 * @since 2024-05-30 16:33:25
 */
public interface LinkService extends IService<Link> {

    /**
     * 查询所有友链
     * @return
     */
    ResponseResult getAllLink();

    /**
     * 分页查询友链列表
     * @param pageNum
     * @param pageSize
     * @param linkListDto
     * @return
     */
    PageVo pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    /**
     * 新增友链
     * @param addLinkDto
     * @return
     */
    ResponseResult add(AddLinkDto addLinkDto);

    /**
     * 删除友链
     * @param id
     * @return
     */
    ResponseResult delete(Long id);

    /**
     * 根据id查询友联
     * @param id
     * @return
     */
    GetLinkVo getLinkById(Long id);

    /**
     * 修改友链
     * @param updateLinkDto
     * @return
     */
    ResponseResult updateLink(UpdateLinkDto updateLinkDto);
}

