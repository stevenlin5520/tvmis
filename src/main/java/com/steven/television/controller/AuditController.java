package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.ImportVo;
import com.steven.television.entity.Page;
import com.steven.television.entity.TImport;
import com.steven.television.entity.TTelevision;
import com.steven.television.services.ImportService;
import com.steven.television.services.SupplierService;
import com.steven.television.services.TelevisionService;
import com.steven.television.util.ParamsUtil;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc 审核员审核节目/广告
 * @author steven
 * @date 2020/11/10 14:56
 */
@Controller
@RequestMapping(value = "audit",produces = "application/json;charset=utf-8")
public class AuditController extends BaseController {

    @Resource
    private ImportService importService;
    @Resource
    private TelevisionService televisionService;
    @Resource
    private SupplierService supplierService;

    /**
     * 节目审核列表
     * @param pager
     * @param type
     * @param orgId
     * @param auditStatus
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(Page<ImportVo> pager, Integer type, String orgId,Integer auditStatus,String search){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/audit/audit_list");
        String sql = "";
        if(StringUtil.isNotBlank(orgId)){
            sql += " and t2.supplier_id='"+orgId+"'";
        }
        if(type != null && Arrays.asList(1, 2).contains(type)){
            sql += " and t1.type="+type;
        }
        if(auditStatus == null){
            sql += " and t1.import_audit in (1,3)";
        }else if(auditStatus != null){
            sql += " and t1.import_audit="+auditStatus;
        }
        if(StringUtil.isNotBlank(search)){
            sql += " and t2.tv_name like '%"+search+"%'";
        }
        modelAndView.addObject("pager",importService.list(pager,sql));
        modelAndView.addObject("type",type);
        modelAndView.addObject("page",pager.getPage());
        modelAndView.addObject("limit",pager.getLimit());
        modelAndView.addObject("orgId",orgId);
        modelAndView.addObject("search",search);
        modelAndView.addObject("auditStatus",auditStatus);
        return modelAndView;
    }


    @PostMapping("saveAuditInfo")
    @ResponseBody
    public Result saveAuditInfo(String id,Integer auditStatus,String content){
        if(StringUtil.isBlank(id) || auditStatus== null){
            return Result.failed("参数不全");
        }else if(auditStatus == 3 && StringUtil.isBlank(content)){
            return Result.failed("请说明拒绝原因");
        }
        TImport tImport = importService.selectById(id);
        if(tImport == null || tImport.getStatus()==-1 || !Arrays.asList(1,3).contains(tImport.getImportAudit())){
            return Result.failed("该条审核信息出错");
        }
        return importService.buildProject(tImport,auditStatus,content);
    }

    @RequestMapping("televisionList")
    public ModelAndView list(Page<TTelevision> pager, Integer type, String orgId, String auditStatus){
        ModelAndView modelAndView = new ModelAndView("view/audit/audit_television_list");
        modelAndView.addObject("type",type);
        modelAndView.addObject("orgId",orgId);
        modelAndView.addObject("auditStatus",auditStatus);
        if(auditStatus != null && !Arrays.asList("2","3").contains(auditStatus)){
            return modelAndView;
        }
        auditStatus = "999";
        modelAndView.addObject("pager", televisionService.list(pager,type,orgId,auditStatus));
        modelAndView.addObject("suppliers",supplierService.list());
        return modelAndView;
    }

    @PostMapping("saveAuditTelevision")
    @ResponseBody
    public Result saveAuditTelevision(String id,Integer auditStatus,String content){
        if(StringUtil.isBlank(id) || auditStatus== null){
            return Result.failed("参数不全");
        }else if(auditStatus == 2 && StringUtil.isBlank(content)){
            return Result.failed("请说明拒绝原因");
        }
        TTelevision tTelevision = televisionService.selectById(id);
        if(tTelevision == null || tTelevision.getStatus()==-1 || !Arrays.asList("2","3").contains(tTelevision.getAuditState())){
            return Result.failed("该条审核信息出错");
        }
        return importService.saveAuditTelevision(tTelevision,auditStatus,content);
    }
}
