package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.ImportVo;
import com.steven.television.entity.Page;
import com.steven.television.entity.TImport;
import com.steven.television.entity.TTelevision;
import com.steven.television.services.ChannelService;
import com.steven.television.services.ImportService;
import com.steven.television.services.SupplierService;
import com.steven.television.services.TelevisionService;
import com.steven.television.util.DateUtil;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author steven
 * @desc
 * @date 2020/11/10 14:54
 */
@Controller
@RequestMapping(value = "import",produces = "application/json;cahrset=utf-8")
public class ImportController extends BaseController {

    @Resource
    private ImportService importService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private TelevisionService televisionService;
    @Resource
    private ChannelService channelService;

    @RequestMapping("list")
    public ModelAndView list(Page<ImportVo> pager, Integer type, String orgId,String search,Integer auditStatus,String channelId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/import/import_list");
        String condition = " and t1.type="+type;
        if(StringUtil.isNotBlank(channelId)){
            condition += " and t1.channel_id='"+channelId+"'";
        }
        if(StringUtil.isNotBlank(search)){
            condition += " and t2.tv_name like '%"+search+"%'";
        }
        if(auditStatus != null){
            condition += " and t1.import_audit="+auditStatus;
        }
        modelAndView.addObject("pager",importService.list(pager,condition));
        modelAndView.addObject("channelList",channelService.list());
        modelAndView.addObject("type",type);
        modelAndView.addObject("orgId",orgId);
        modelAndView.addObject("search",search);
        modelAndView.addObject("auditStatus",auditStatus);
        modelAndView.addObject("channelId",channelId);
        modelAndView.addObject("page",pager.getPage());
        modelAndView.addObject("limit",pager.getLimit());
        return modelAndView;
    }

    @GetMapping("edit")
    public String edit(String id, int type, Model model){
        TImport bean = importService.selectById(id);
        if(bean != null){
            model.addAttribute("bean",bean);
            TTelevision tTelevision = televisionService.selectById(bean.getTelevisionId());
            model.addAttribute("tvName",tTelevision.getTvName());
            model.addAttribute("orgId",tTelevision.getSupplierId());
            model.addAttribute("startTime", DateUtil.getDateTimeStr(bean.getStartTime()));
            model.addAttribute("endTime",DateUtil.getDateTimeStr(bean.getEntTime()));
        }else{
            model.addAttribute("bean",new TImport());
        }
        model.addAttribute("suppliers",supplierService.list());
        model.addAttribute("channelList",channelService.list());
        model.addAttribute("type",type);
        return "view/import/import_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public Result save(@RequestBody TImport tImport){
        int result = 0;
        tImport.setStatus(1);
        tImport.setImportAudit(1);
        tImport.setCommitUserId(userId);
        tImport.setCommitUserName(userInfo.getUserName());
        tImport.setEntTime(DateUtil.dateAddTime(tImport.getStartTime(),tImport.getImportLength()));
        if(StringUtil.isBlank(tImport.getImportId())){
            tImport.setImportId(UUIDUtil.getUUIDSTR());
            tImport.setCreateTime(new Date());
            result = importService.insert(tImport);
        }else{
            tImport.setUpdateTime(new Date());
            result = importService.update(tImport);
        }

        return Result.toResult(result > 0 ,result);
    }

    @PostMapping("deleted")
    @ResponseBody
    public Result deleted(String id){
        int result = importService.deleteById(id);
        return Result.toResult(result > 0, result);
    }

    /**
     * 撤销申请
     * @param id
     * @return
     */
    @PostMapping("cancelAudit")
    @ResponseBody
    public Result cancelAudit(String id){
        TImport tImport = importService.selectById(id);
        if(tImport == null){
            return Result.failed("未找到申请记录");
        }
        tImport.setImportAudit(4);
        tImport.setCommitUserId(userId);
        tImport.getCommitUserName();
        int result = importService.update(tImport);
        return Result.toResult(result > 0, result);
    }


    /**
     * 获取播放申请列表占位信息
     * @param date
     * @param type
     * @param channelId
     * @param length
     * @return
     */
    @PostMapping("formList")
    @ResponseBody
    public Result formList(String date,int type,String channelId,int length) throws ParseException {
        if(StringUtil.isBlank(date) || StringUtil.isBlank(channelId)){
            return Result.failed("未选中播放日期或频道");
        }
        return importService.formList(date,type,channelId,length);
    }
}
