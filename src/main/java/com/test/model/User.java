package com.test.model;

import java.math.BigDecimal;

public class User{

    private Integer userId;
    private String userName;
    private BigDecimal userAmount;

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getUserAmount() {
        return userAmount;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAmount(BigDecimal userAmount) {
        this.userAmount = userAmount;
    }

}