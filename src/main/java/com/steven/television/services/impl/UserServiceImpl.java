package com.steven.television.services.impl;

import com.steven.television.dao.TUserMapper;
import com.steven.television.entity.Page;
import com.steven.television.entity.TSystem;
import com.steven.television.entity.TUser;
import com.steven.television.services.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/9 22:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper userMapper;

    @Override
    public List<TUser> list() {
        return userMapper.list();
    }

    @Override
    public Page<TUser> list(Page<TUser> page) {
        List<TUser> list = userMapper.listByPage(page);
        int total = userMapper.listByPageCount(page);
        page.returnData(list,total);
        return page;
    }

    @Override
    public TUser selectById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TUser tUser = new TUser();tUser.setUserId(id);
        tUser.setUpdateTime(new Date());
        tUser.setStatus(-1);
        return userMapper.updateByPrimaryKeySelective(tUser);
    }

    @Override
    public int insert(TUser tUser) {
        return userMapper.insert(tUser);
    }

    @Override
    public int update(TUser tUser) {
        return userMapper.updateByPrimaryKeySelective(tUser);
    }

    @Override
    public TUser selectByToken(String token) {
        return userMapper.selectByToken(token);
    }


    @Override
    public List<TUser> selectByLoginInfo(Map map) {
        return userMapper.selectByLoginInfo(map);
    }
}
