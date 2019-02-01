package com.tak.mapper;
import com.tak.pojo.News;
import java.util.List;

public interface NewsMapper {
    List<News> listNews();
    int addNews(News news);
    int deleteAll();

}
