package com.derucci.minibackend.mapper;

import com.derucci.minibackend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> list();
}
