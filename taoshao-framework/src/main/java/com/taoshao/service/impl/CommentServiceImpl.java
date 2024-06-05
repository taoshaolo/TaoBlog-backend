package com.taoshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.Comment;
import com.taoshao.domain.entity.User;
import com.taoshao.domain.vo.CommentVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.exception.SystemException;
import com.taoshao.mapper.CommentMapper;
import com.taoshao.service.CommentService;
import com.taoshao.service.UserService;
import com.taoshao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.taoshao.constants.SystemConstants.ARTICLE_COMMENT;
import static com.taoshao.constants.SystemConstants.ROOT_COMMENT;
import static com.taoshao.domain.enums.AppHttpCodeEnum.CONTENT_NOT_NULL;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-01 17:11:46
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对 articleId 进行判断， commentType = 0 时
        queryWrapper.eq(ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
        //根评论 rootId 为 -1
        queryWrapper.eq(Comment::getRootId, ROOT_COMMENT);
        //根评论降序排序
        queryWrapper.orderByDesc(Comment::getCreateTime);

        //评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
//        if (StringUtils.hasText(comment.getContent())){
//            throw new SystemException(CONTENT_NOT_NULL);
//        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论id 查询所对应的子评论的集合
     *
     * @param id 根评论id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历 vo 集合
        commentVos.stream()
                .forEach(commentVo -> {
                    // 通过createBy查询用户的昵称并赋值
                    Long createBy = commentVo.getCreateBy();
                    if (createBy != null) {
                        User user = userService.getById(createBy);
                        if (user != null) {
                            commentVo.setUsername(user.getNickName());
                        }
                    }

                    // 通过toCommentUserId查询用户的昵称并赋值
                    if (commentVo.getToCommentUserId() != ROOT_COMMENT) {
                        Long toCommentId = commentVo.getToCommentId();
                        if (toCommentId != null) {
                            User toCommentUser = userService.getById(toCommentId);
                            if (toCommentUser != null) {
                                commentVo.setToCommentUserName(toCommentUser.getNickName());
                            }
                        }
                    }
                });

//        for (CommentVo commentVo : commentVos) {
//            //通过 createBy 查询用户的昵称并赋值
//            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
//            commentVo.setUsername(nickName);
//            //通过 toCommentUserId 查询用户的昵称并赋值
//            //如果 toCommentUserId 不为 -1 才进行查询
//            if (commentVo.getToCommentUserId() != ROOT_COMMENT ) {
//                String toCommentUserName = userService.getById(commentVo.getToCommentId()).getNickName();
//                commentVo.setToCommentUserName(toCommentUserName);
//            }
//        }
        return commentVos;
    }
}

