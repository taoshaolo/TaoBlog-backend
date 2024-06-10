package com.taoshao.domain.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    CONTENT_NOT_NULL(506,"评论内容不能为空"),
    FILE_TYPE_ERROR(507,"文件类型错误"),
    USERNAME_NOT_NULL(508,"用户名不能为空"),
    NICKNAME_NOT_NULL(509,"昵称不能为空"),
    PASSWORD_NOT_NULL(510,"密码不能为空"),
    EMAIL_NOT_NULL(511,"邮箱不能为空"),
    NICKNAME_EXIST(512,"昵称已存在"),
    TAG_EXIST(513,"标签已存在"),
    TAG_NOT_EXIST(514,"标签不存在"),
    TAG_IN_USE(515,"标签使用中"),
    MENU_NOT_EXIST(516,"菜单不存在"),
    NOT_DELETE(517,"不能删除自己"),

    CATEGORY_EXIST(520,"分类名称已存在"),
    PARAMS_ERROR(521,"请求参数错误"),
    CATEGORY_NOT_CAN_EMPTY(522,"分类不能为空"),
    TITLE_NOT_CAN_EMPTY(523,"标题不能为空"),
    ARTICLE_CONTENT_NOT_CAN_EMPTY(524,"文章内容不能为空"),
    SUMMARY_NOT_CAN_EMPTY(525,"摘要不能为空"),
    THUMBNAIL_NOT_CAN_EMPTY(526,"缩略图不能为空"),
    TAGS_NOT_CAN_EMPTY(527,"标签不能为空");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
