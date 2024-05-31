package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.Link;
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
}

