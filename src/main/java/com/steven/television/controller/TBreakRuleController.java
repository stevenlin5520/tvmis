package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.Page;
import com.steven.television.entity.TBreakRule;
import com.steven.television.entity.TSupplier;
import com.steven.television.services.BreakRuleService;
import com.steven.television.services.SupplierService;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import com.steven.television.util.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc
 * @author steven
 * @date 2020/11/15 10:20
 */
@Controller
@RequestMapping("rule")
public class TBreakRuleController extends BaseController {

    @Resource
    private BreakRuleService breakRuleService;
    @Resource
    private SupplierService supplierService;

    @GetMapping("list")
    public ModelAndView list(Page<TBreakRule> pager){
        ModelAndView modelAndView = new ModelAndView("view/break/break_list");
        //分页
        pager.setStartRow((pager.getPage()-1)*pager.getLimit());
        Page<TBreakRule> supplierPage = breakRuleService.list(pager);
        modelAndView.addObject("pager",supplierPage);
        return modelAndView;
    }

    @GetMapping("edit")
    public String edit(String id, Model model){
        TBreakRule breakRule = breakRuleService.selectById(id);
        List<TSupplier> list = supplierService.list();
        List<TSupplier> suppliers = list.stream().filter(s -> s.getAuditStatus() == 1).collect(Collectors.toList());
        model.addAttribute("bean",breakRule);
        model.addAttribute("suppliers",suppliers);
        return "view/break/break_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public Result save(@RequestBody TBreakRule breakRule){
        int result = 0;
        if(StringUtil.isBlank(breakRule.getRuleId())){
            breakRule.setRuleId(UUIDUtil.getUUIDSTR());
            breakRule.setCreateTime(new Date());
            breakRule.setStatus(1);
            breakRule.setRuleType(1);
            result = breakRuleService.insert(breakRule);
        }else{
            breakRule.setUpdateTime(new Date());
            result = breakRuleService.update(breakRule);
        }

        return Result.toResult(result > 0 ,result);
    }

    @PostMapping("deleted")
    @ResponseBody
    public Result deleted(String id){
        int result = breakRuleService.deleteById(id);
        return Result.toResult(result > 0, result);
    }
}
