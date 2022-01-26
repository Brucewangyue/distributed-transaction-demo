package com.vv.serviceb.dao;

import com.vv.serviceb.entity.LocalEvent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
//@Component
public interface LocalEventDao {
    int insert(LocalEvent entity);

    List<LocalEvent> getUnSentList();

    int updateStatusToSent(long id);
}
