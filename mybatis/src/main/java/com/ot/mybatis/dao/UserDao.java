package com.ot.mybatis.dao;

import com.ot.mybatis.domain.QueryDto;
import com.ot.mybatis.domain.User;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

public interface UserDao {

    List<User> findAll() throws IOException;

    List<User> findByName(String name);

    List<User> findByName1(String name);

    Long save(User user);

    List<User> findByIdAndUserName(User user);

    List<User> findByIdAndUserNameChoose(User user);

    List<User> findByIds1(@Param("ids") List<Long> ids);

    List<User> findByIds2(@Param("ids") Long[] ids);

    List<User> findByIds3(QueryDto queryDto);

    List<User> findUserAndAccount();

    List<User> findUserAndAccountAndRole();

    User findById(Long id);

    List<User> findAllByPage() throws IOException;
}
