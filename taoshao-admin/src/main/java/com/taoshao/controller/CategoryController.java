package com.taoshao.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.Category;
import com.taoshao.domain.enums.AppHttpCodeEnum;
import com.taoshao.domain.vo.CategoryVo;
import com.taoshao.domain.vo.ExcelCategoryVo;
import com.taoshao.service.CategoryService;
import com.taoshao.utils.BeanCopyUtils;
import com.taoshao.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
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
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
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
}
