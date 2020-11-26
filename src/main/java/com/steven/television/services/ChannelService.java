package com.steven.television.services;

import com.steven.television.entity.Page;
import com.steven.television.entity.TChannel;
import com.steven.television.entity.TSupplier;

import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:31
 */
public interface ChannelService {

    List<TChannel> list();

    Page<TChannel> list(Page<TChannel> pager);

    TChannel selectById(String id);

    int deleteById(String id);

    int insert(TChannel tChannel);

    int update(TChannel tChannel);

    List<TChannel> selectByCondition(Map map);
}
