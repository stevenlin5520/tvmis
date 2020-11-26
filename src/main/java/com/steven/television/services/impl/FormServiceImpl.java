package com.steven.television.services.impl;

import com.steven.television.dao.TFormMapper;
import com.steven.television.dao.TPlayMapper;
import com.steven.television.dao.TSupplierMapper;
import com.steven.television.entity.Page;
import com.steven.television.entity.TForm;
import com.steven.television.entity.TPlay;
import com.steven.television.entity.TSupplier;
import com.steven.television.services.FormService;
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
public class FormServiceImpl implements FormService {

    @Resource
    private TFormMapper formMapper;
    @Resource
    private TPlayMapper playMapper;

    @Override
    public List<TForm> list() {
        return formMapper.list();
    }

    @Override
    public Page<TForm> list(Page<TForm> pager) {
        pager.returnData(formMapper.selectByPage(pager),formMapper.selectByPageCount(pager));
        return pager;
    }

    @Override
    public TForm selectById(String id) {
        return formMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(String id) {
        TForm form = selectById(id);
        if(form == null){
            return 0;
        }
        form.setUpdateTime(new Date());
        form.setStatus(-1);
        return formMapper.updateByPrimaryKeySelective(form);
    }

    @Override
    public int insert(TForm form) {
        return formMapper.insert(form);
    }

    @Override
    public int update(TForm form) {
        return formMapper.updateByPrimaryKeySelective(form);
    }

    @Override
    public List<TPlay> listPlay(Map map) {
        return playMapper.selectByDateOrForm(map);
    }
}
