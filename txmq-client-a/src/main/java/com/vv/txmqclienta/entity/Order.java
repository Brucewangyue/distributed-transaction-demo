package com.vv.txmqclienta.entity;

import java.io.Serializable;

public class Order {
    private int id;
    private String orderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
