package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.constant.CommonConstrant;
import com.steven.television.entity.Page;
import com.steven.television.entity.TSupplier;
import com.steven.television.entity.TSystem;
import com.steven.television.services.SystemService;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/9 22:33
 */
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {

    @Resource
    private SystemService systemService;

    @GetMapping("list2")
    public ModelAndView list(){
        List<TSystem> list = systemService.list();
        ModelAndView modelAndView = new ModelAndView("view/system/system_list");
        modelAndView.addObject("list",list);
        return modelAndView;
    }

    @GetMapping("list")
    public ModelAndView list(Page<TSystem> pager){
        ModelAndView modelAndView = new ModelAndView("view/system/system_list");
        pager.setStartRow((pager.getPage()-1)*pager.getLimit());
        Page<TSystem> supplierPage = systemService.list(pager);
        modelAndView.addObject("pager",supplierPage);
        return modelAndView;
    }

    @GetMapping("edit")
    public String edit(String id, Model model){
        TSystem tSystem = systemService.selectById(id);
        model.addAttribute("bean",tSystem);
        return "view/system/system_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public Result save(TSystem tSystem){
        int result = 0;
        if(StringUtil.isBlank(tSystem.getSysId())){
            tSystem.setSysId(UUIDUtil.getUUIDSTR());
            tSystem.setSysTime(new Date());
            result = systemService.insert(tSystem);
        }else{
            result = systemService.update(tSystem);
        }

        return Result.toResult(result > 0 ,result);
    }

    @PostMapping("deleted")
    @ResponseBody
    public Result deleted(String id){
        int result = systemService.deleteById(id);
        return Result.toResult(result > 0, result);
    }

    @GetMapping("getSysInfo")
    @ResponseBody
    public Result getSysInfo(){
        Map<String, String> map = new HashMap<String, String>(16);
        List<TSystem> list = systemService.list();
        for (TSystem tSystem : list) {
            if(StringUtil.isEquale(tSystem.getSysKey(), CommonConstrant.SYS_MAIN_COPYRIGHT)){
                map.put("copyright",tSystem.getSysValue());
            }else if(StringUtil.isEquale(tSystem.getSysKey(), CommonConstrant.SYS_MAIN_LOGO)){
                map.put("sysLogo",tSystem.getSysValue());
            }
        }
        return Result.success(map);
    }
}
