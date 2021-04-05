package com.derucci.minibackend.controller;

import com.derucci.minibackend.pojo.User;
import com.derucci.minibackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/listUser")
    public List<User> ListUser() {
        List<User> users = userService.list();
        return users;
    }
}
