package com.steven.television.services;

import com.steven.television.entity.Page;
import com.steven.television.entity.TTelevision;
import com.steven.television.entity.TelevisionVo;

import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 14:03
 */
public interface TelevisionService {

    Page<TTelevision> list(Page<TTelevision> pager,Integer type,String orgId,String auditState);

    Page<TTelevision> list(Page<TTelevision> pager,Integer type,String orgId,String auditState,String search);

    TTelevision selectById(String id);

    int deleteById(String id);

    int insert(TTelevision television);

    int update(TTelevision television);

    List<TTelevision> selectByPage(Map map);


    List<TelevisionVo> televisionList(String channelId);
}
