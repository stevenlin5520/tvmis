package com.steven.television.services;

import com.steven.television.entity.Page;
import com.steven.television.entity.TBreakRule;
import com.steven.television.entity.TSupplier;

import java.util.List;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:31
 */
public interface BreakRuleService {

    List<TBreakRule> list();

    Page<TBreakRule> list(Page<TBreakRule> pager);

    TBreakRule selectById(String id);

    int deleteById(String id);

    int insert(TBreakRule rule);

    int update(TBreakRule rule);

    void autoTelevisionWarn();

    void autoSupplierWarn();

    void autoSupplierBacklist();
}
