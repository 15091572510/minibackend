package com.derucci.minibackend.service.impl;

import com.derucci.minibackend.mapper.UserMapper;
import com.derucci.minibackend.pojo.User;
import com.derucci.minibackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    public List<User> list() {
        return userMapper.list();
    }
}
