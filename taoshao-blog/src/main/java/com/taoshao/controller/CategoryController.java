package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类表(Category)表控制层
 *
 * @author taoshao
 * @since 2024-05-30 09:46:10
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}

