package com.taoshao.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddCategoryDto;
import com.taoshao.domain.dto.CategoryListDto;
import com.taoshao.domain.dto.UpdateCategoryDto;
import com.taoshao.domain.entity.Category;
import com.taoshao.domain.vo.CategoryVo;
import com.taoshao.domain.vo.ExcelCategoryVo;
import com.taoshao.domain.vo.GetCategoryVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.service.CategoryService;
import com.taoshao.utils.BeanCopyUtils;
import com.taoshao.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.taoshao.domain.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @Author taoshao
 * @Date 2024/6/7
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导入的数据
            List<Category> list = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {

            ResponseResult result = ResponseResult.errorResult(SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
        //如果出现异常也要响应json
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        PageVo pageVo = categoryService.pageCategoryList(pageNum, pageSize, categoryListDto);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddCategoryDto addCategoryDto) {
        return categoryService.add(addCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseResult<GetCategoryVo> getCategoryById(@PathVariable Long id) {
        GetCategoryVo getCategoryVo = categoryService.getCategoryById(id);
        return ResponseResult.okResult(getCategoryVo);
    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateCategoryDto updateCategoryDto) {
        return categoryService.updateCategory(updateCategoryDto);
    }

}
