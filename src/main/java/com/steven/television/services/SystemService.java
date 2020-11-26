package com.steven.television.services;

import com.steven.television.entity.Page;
import com.steven.television.entity.TSystem;

import java.util.List;

/**
 *
 * @desc
 * @author steven
 * @date 2020/11/9-22:34
 */
public interface SystemService {

    List<TSystem> list();
    Page<TSystem> list(Page<TSystem> pager);

    TSystem selectById(String id);

    int deleteById(String id);

    int insert(TSystem tSystem);

    int update(TSystem tSystem);

    String selectValueByKey(String key);

}
