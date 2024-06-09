package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddCategoryDto;
import com.taoshao.domain.dto.CategoryListDto;
import com.taoshao.domain.dto.UpdateCategoryDto;
import com.taoshao.domain.entity.Category;
import com.taoshao.domain.vo.CategoryVo;
import com.taoshao.domain.vo.GetCategoryVo;
import com.taoshao.domain.vo.PageVo;

import java.util.List;


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


    /**
     * 查询所有分类
     * @return
     */
    List<CategoryVo> listAllCategory();

    /**
     * 分页查询分类列表
     * @param pageNum
     * @param pageSize
     * @param categoryListDto
     * @return
     */
    PageVo pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    /**
     * 新增分类
     * @param addCategoryDto
     * @return
     */
    ResponseResult add(AddCategoryDto addCategoryDto);

    /**
     * 删除分类
     * @param id
     * @return
     */
    ResponseResult delete(Long id);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    GetCategoryVo getCategoryById(Long id);

    /**
     * 更新分类
     * @param updateCategoryDto
     * @return
     */
    ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto);
}

