package com.vv.lcnserverb.dao;

import com.vv.lcnserverb.entity.Pay;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayDao {
    int update(Pay entity);
}
