package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author taoshao
 * @since 2024-05-30 09:46:16
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取分类列表
     * @return
     */
    ResponseResult getCategoryList();

}

