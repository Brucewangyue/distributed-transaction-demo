package com.vv.lcnservera.dao;

import com.vv.lcnservera.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//@Component
public interface OrderDao {
    int update(Order entity);
}
