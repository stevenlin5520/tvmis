package com.steven.television.services.impl;

import com.steven.television.dao.TSystemMapper;
import com.steven.television.entity.Page;
import com.steven.television.entity.TSystem;
import com.steven.television.services.SystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @desc
 * @author steven
 * @date 2020/11/9 22:35
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private TSystemMapper tSystemMapper;

    @Override
    public List<TSystem> list() {
        return tSystemMapper.list();
    }

    @Override
    public Page<TSystem> list(Page<TSystem> pager) {
        pager.returnData(tSystemMapper.selectByPage(pager),tSystemMapper.selectByPageCount(pager));
        return pager;
    }

    @Override
    public TSystem selectById(String id) {
        return tSystemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        return tSystemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TSystem tSystem) {
        return tSystemMapper.insert(tSystem);
    }

    @Override
    public int update(TSystem tSystem) {
        return tSystemMapper.updateByPrimaryKeySelective(tSystem);
    }

    @Override
    public String selectValueByKey(String key){
        return tSystemMapper.selectValueByKey(key);
    }
}
