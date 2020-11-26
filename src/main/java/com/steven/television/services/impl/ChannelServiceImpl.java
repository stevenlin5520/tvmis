package com.steven.television.services.impl;

import com.steven.television.dao.TChannelMapper;
import com.steven.television.dao.TSupplierMapper;
import com.steven.television.entity.Page;
import com.steven.television.entity.TChannel;
import com.steven.television.entity.TSupplier;
import com.steven.television.services.ChannelService;
import com.steven.television.services.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:33
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Resource
    private TChannelMapper channelMapper;

    @Override
    public List<TChannel> list() {
        return channelMapper.list();
    }

    @Override
    public Page<TChannel> list(Page<TChannel> pager) {
        pager.returnData(channelMapper.selectByPage(pager),channelMapper.selectByPageCount());
        return pager;
    }

    @Override
    public TChannel selectById(String id) {
        return channelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TChannel tChannel = selectById(id);
        if(tChannel == null){
            return 0;
        }
        tChannel.setUpdateTime(new Date());
        tChannel.setStatus(-1);
        return channelMapper.updateByPrimaryKeySelective(tChannel);
    }

    @Override
    public int insert(TChannel tChannel) {
        return channelMapper.insert(tChannel);
    }

    @Override
    public int update(TChannel tChannel) {
        return channelMapper.updateByPrimaryKeySelective(tChannel);
    }


    @Override
    public List<TChannel> selectByCondition(Map map) {
        return channelMapper.selectByCondition(map);
    }
}
