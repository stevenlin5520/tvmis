package com.steven.television.services;

import com.steven.television.entity.*;
import com.steven.television.util.Result;

import java.util.List;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 13:31
 */
public interface ImportService {

    List<TImport> list();

    Page<ImportVo> list(Page<ImportVo> pager, String orgId, int type);

    Page<ImportVo> list(Page<ImportVo> pager, String search);

    TImport selectById(String id);

    int deleteById(String id);

    int insert(TImport tImport);

    int update(TImport tImport);

    Result buildProject(TImport tImport,int auditStatus,String content);

    Result saveAuditTelevision(TTelevision tTelevison, int auditStatus, String content);
}
