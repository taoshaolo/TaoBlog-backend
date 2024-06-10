package com.taoshao.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddCategoryDto;
import com.taoshao.domain.dto.CategoryListDto;
import com.taoshao.domain.dto.UpdateCategoryDto;
import com.taoshao.domain.entity.Article;
import com.taoshao.domain.entity.Category;
import com.taoshao.domain.enums.AppHttpCodeEnum;
import com.taoshao.domain.vo.CategoryVo;
import com.taoshao.domain.vo.GetCategoryVo;
import com.taoshao.domain.vo.PageCategoryListVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.exception.SystemException;
import com.taoshao.mapper.CategoryMapper;
import com.taoshao.service.ArticleService;
import com.taoshao.service.CategoryService;
import com.taoshao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.taoshao.constants.SystemConstants.ARTICLE_STATUS_NORMAL;
import static com.taoshao.constants.SystemConstants.STATUS_NORMAL;

/**
 * 分类表(Category)表服务实现类
 *
 * @author taoshao
 * @since 2024-05-30 09:46:16
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表状态为0（已发布）
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId()).collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);

        categories = categories.stream()
                .filter(category -> STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //状态正常
        queryWrapper.eq(Category::getStatus, STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public PageVo pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        //能根据分类名称进行模糊查询
        //能根据状态进行查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryListDto.getName()),Category::getName,categoryListDto.getName());
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getStatus()),Category::getStatus,categoryListDto.getStatus());

        //创建了一个新的Page对象
        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        //分页查询方法
        //把 Category 封装成 CategoryVo
        List<PageCategoryListVo> pageCategoryListVos = page(page, queryWrapper)
                .getRecords()
                .stream()
                .map(category -> BeanCopyUtils.copyBean(category, PageCategoryListVo.class))
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(pageCategoryListVos, page.getTotal());
        return pageVo;
    }

    @Override
    public ResponseResult add(AddCategoryDto addCategoryDto) {
        String name = addCategoryDto.getName();
        if (name == null){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_CAN_EMPTY);
        }
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,addCategoryDto.getName());

        int count = count(queryWrapper);
        if (count > 0){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_EXIST);
        }

        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public GetCategoryVo getCategoryById(Long id) {
        Category category = getById(id);
        GetCategoryVo getCategoryVo = BeanCopyUtils.copyBean(category, GetCategoryVo.class);
        return getCategoryVo;
    }

    @Override
    public ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto) {

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,updateCategoryDto.getName());
        // 排除当前正在更新的分类 !=
        queryWrapper.ne(Category::getId,updateCategoryDto.getId());

        int count = count(queryWrapper);
        if (count > 0){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_EXIST);
        }

        Category category = BeanCopyUtils.copyBean(updateCategoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }
}

