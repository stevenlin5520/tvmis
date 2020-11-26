package com.steven.television.services;

import com.steven.television.entity.Page;
import com.steven.television.entity.TUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * @desc  
 * @author steven
 * @date 2020/11/9-22:41
 */
public interface UserService {

    List<TUser> list();

    Page<TUser> list(Page<TUser> page);

    TUser selectById(String id);

    int deleteById(String id);

    int insert(TUser tUser);

    int update(TUser tUser);

    TUser selectByToken(String token);

    List<TUser> selectByLoginInfo(Map map);
}
