package com.steven.television.services.impl;

import com.steven.television.dao.TTelevisionMapper;
import com.steven.television.entity.Page;
import com.steven.television.entity.TTelevision;
import com.steven.television.entity.TelevisionVo;
import com.steven.television.services.TelevisionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 14:04
 */
@Service
public class TelevisionServiceImpl implements TelevisionService {

    @Resource
    private TTelevisionMapper televisionMapper;

    @Override
    public Page<TTelevision> list(Page<TTelevision> page,Integer type,String orgId,String auditState) {
        Map<String, Object> map = new HashMap<String, Object>(4);
        map.put("startRow",page.getStartRow());
        map.put("limit",page.getLimit());
        map.put("type",type);
        map.put("orgId",orgId);
        //表示审核查询请求
        if(auditState == "999"){
            map.put("search"," and audit_state in ('2','3')");
        }else{
            map.put("auditState",auditState);
        }
        List<TTelevision> tTelevisions = televisionMapper.selectByPage(map);
        int count = televisionMapper.selectByPageCount(map);
        page.returnData(tTelevisions,count);
        return page;
    }

    @Override
    public TTelevision selectById(String id) {
        return televisionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TTelevision television = new TTelevision();
        television.setTvId(id);
        television.setUpdateTime(new Date());
        television.setStatus(-1);
        return televisionMapper.updateByPrimaryKeySelective(television);
    }

    @Override
    public int insert(TTelevision television) {
        return televisionMapper.insert(television);
    }

    @Override
    public int update(TTelevision television) {
        return televisionMapper.updateByPrimaryKeySelective(television);
    }

    @Override
    public List<TTelevision> selectByPage(Map map) {
        return televisionMapper.selectByPage(map);
    }

    @Override
    public List<TelevisionVo> televisionList(String channelId){
        return televisionMapper.televisionList(channelId);
    }
}
