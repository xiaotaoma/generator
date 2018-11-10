package com.test.dao;

import com.test.model.User;
import java.lang.Integer;

public interface UserMapper{

    int insert(User user);

    int update(User user);

    User getByPid(Integer userId);

}