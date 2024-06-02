package com.taoshao.service;

import com.taoshao.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author taoshao
 * @Date 2024/6/2
 */
public interface UploadService {
    /**
     * 图片上传
     * @param img
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);
}
