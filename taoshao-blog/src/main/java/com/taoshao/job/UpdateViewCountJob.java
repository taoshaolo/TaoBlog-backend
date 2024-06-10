package com.taoshao.job;

import com.taoshao.domain.entity.Article;
import com.taoshao.service.ArticleService;
import com.taoshao.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.taoshao.constants.RedisConstants.ARTICLE_VIEWCOUNT_KEY;

/**
 * @Author taoshao
 * @Date 2024/6/3
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

//    @Scheduled(cron = "0/5 * * * * ?")//测试
    @Scheduled(cron = "0 0/10 * * * ?")
    private void updateViewCount() {
        //获取 redis 中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEWCOUNT_KEY);

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
    }

}
