package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.Page;
import com.steven.television.entity.TTelevision;
import com.steven.television.entity.TelevisionVo;
import com.steven.television.services.SupplierService;
import com.steven.television.services.TelevisionService;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/10 17:57
 */
@Controller
@RequestMapping("television")
public class TelevisionController extends BaseController {

    @Resource
    private TelevisionService televisionService;
    @Resource
    private SupplierService supplierService;

    @RequestMapping("list")
    public ModelAndView list(Page<TTelevision> pager,Integer type,String orgId,String auditStatus){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/television/television_list");
        modelAndView.addObject("pager",televisionService.list(pager,type,orgId,auditStatus));
        modelAndView.addObject("suppliers",supplierService.list());
        modelAndView.addObject("type",type);
        modelAndView.addObject("orgId",orgId);
        modelAndView.addObject("auditStatus",auditStatus);
        return modelAndView;
    }

    @PostMapping("search")
    @ResponseBody
    public Result search(int type, String orgId,String tvName){
        String sql = " and audit_state='1'";
        if(type == 1 || type == 2){
            sql += " and tv_type="+type;
        }
        if(StringUtil.isNotBlank(orgId)){
            sql += " and supplier_id='"+orgId+"'";
        }
        if(StringUtil.isNotBlank(tvName)){
            sql += " and tv_name like concat('%','"+tvName+"','%')";
        }
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("search",sql);
        List<TTelevision> list = televisionService.selectByPage(map);
        return Result.success(list);
    }

    @GetMapping("edit")
    public String edit(String id, Model model){
        TTelevision television = televisionService.selectById(id);
        model.addAttribute("bean",television);
        model.addAttribute("suppliers",supplierService.list());
        return "view/television/television_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public Result save(TTelevision television){
        int result = 0;
        television.setStatus(1);
        if(StringUtil.isBlank(television.getTvId())){
            television.setAuditState("3");
            television.setTvId(UUIDUtil.getUUIDSTR());
            television.setCreateTime(new Date());
            //TODO 添加组织机构
            result = televisionService.insert(television);
        }else{
            TTelevision selectTelevision = televisionService.selectById(television.getTvId());
            if(StringUtil.isEquale(selectTelevision.getTvScreen(),television.getTvScreen()) || StringUtil.isEquale(selectTelevision.getTvLocation(),television.getTvLocation())){
                television.setAuditState("3");
            }
            television.setUpdateTime(new Date());
            result = televisionService.update(television);
        }

        return Result.toResult(result > 0 ,result);
    }

    @PostMapping("deleted")
    @ResponseBody
    public Result deleted(String id){
        int result = televisionService.deleteById(id);
        return Result.toResult(result > 0, result);
    }

    @GetMapping("televisionList")
    @ResponseBody
    public Result televisionList(String channelId){
        if(StringUtil.isBlank(channelId)){
            return Result.failed("参数为空");
        }
        List<TelevisionVo> result = televisionService.televisionList(channelId);
        HashMap<String, Object> map = new HashMap<String, Object>(4);
        map.put("list",result);
        map.put("filePath",filePath);
        return Result.success(map);
    }
}
