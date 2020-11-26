package com.steven.television.services.impl;

import com.steven.television.dao.TSupplierMapper;
import com.steven.television.entity.Page;
import com.steven.television.entity.TSupplier;
import com.steven.television.services.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:33
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private TSupplierMapper supplierMapper;

    @Override
    public List<TSupplier> list() {
        return supplierMapper.list();
    }

    @Override
    public Page<TSupplier> list(Page<TSupplier> pager) {
        pager.returnData(supplierMapper.selectByPage(pager),supplierMapper.selectByPageCount(pager));
        return pager;
    }

    @Override
    public TSupplier selectById(String id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TSupplier tSupplier = selectById(id);
        if(tSupplier == null){
            return 0;
        }
        tSupplier.setUpdateTime(new Date());
        tSupplier.setStatus(-1);
        return supplierMapper.updateByPrimaryKeySelective(tSupplier);
    }

    @Override
    public int insert(TSupplier tSupplier) {
        return supplierMapper.insert(tSupplier);
    }

    @Override
    public int update(TSupplier tSupplier) {
        return supplierMapper.updateByPrimaryKeySelective(tSupplier);
    }
}
