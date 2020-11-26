package com.steven.television.services;

import com.steven.television.entity.Page;
import com.steven.television.entity.TSupplier;

import java.util.List;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:31
 */
public interface SupplierService {

    List<TSupplier> list();

    Page<TSupplier> list(Page<TSupplier> pager);

    TSupplier selectById(String id);

    int deleteById(String id);

    int insert(TSupplier tSupplier);

    int update(TSupplier tSupplier);
}
