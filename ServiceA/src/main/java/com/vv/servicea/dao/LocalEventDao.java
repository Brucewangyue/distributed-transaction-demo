package com.vv.servicea.dao;

import com.vv.servicea.entity.LocalEvent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
//@Component
public interface LocalEventDao {
    int insert(LocalEvent entity);

    List<LocalEvent> getUnSentList();

    int updateStatusToSent(long id);
}
