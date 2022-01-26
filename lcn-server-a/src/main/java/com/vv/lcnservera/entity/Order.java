package com.vv.lcnservera.entity;

import java.io.Serializable;

public class Order implements Serializable {
    private long id;
    private int status;

    public Order(long id, int status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private static final long serialVersionUID = 1L;
}
