package com.steven.television.services;

import com.steven.television.entity.Page;
import com.steven.television.entity.TForm;
import com.steven.television.entity.TPlay;

import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:31
 */
public interface FormService {

    List<TForm> list();

    Page<TForm> list(Page<TForm> pager);

    TForm selectById(String id);

    int deleteById(String id);

    int insert(TForm tSupplier);

    int update(TForm tSupplier);

    List<TPlay> listPlay(Map map);
}
