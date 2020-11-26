package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.Page;
import com.steven.television.entity.TSupplier;
import com.steven.television.entity.TSystem;
import com.steven.television.entity.TUser;
import com.steven.television.services.SupplierService;
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
import java.util.List;

/**
 * @author steven
 * @desc
 * @date 2020/11/10 17:56
 */
@Controller
@RequestMapping("supplier")
public class SupplierController extends BaseController {

    @Resource
    private SupplierService supplierService ;

    @GetMapping("list")
    public ModelAndView list(Page<TSupplier> pager){
        ModelAndView modelAndView = new ModelAndView("view/supplier/supplier_list");
        //分页
        pager.setStartRow((pager.getPage()-1)*pager.getLimit());
        Page<TSupplier> supplierPage = supplierService.list(pager);
        modelAndView.addObject("pager",supplierPage);
        return modelAndView;
    }

    @GetMapping("edit")
    public String edit(String id, Model model){
        TSupplier tSupplier = supplierService.selectById(id);
        model.addAttribute("bean",tSupplier);
        return "view/supplier/supplier_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public Result save(TSupplier tSupplier){
        int result = 0;
        tSupplier.setStatus(1);
        if(StringUtil.isBlank(tSupplier.getSupplierId())){
            tSupplier.setSupplierId(UUIDUtil.getUUIDSTR());
            tSupplier.setCreateTime(new Date());
            tSupplier.setAuditStatus(1);
            result = supplierService.insert(tSupplier);
        }else{
            tSupplier.setUpdateTime(new Date());
            result = supplierService.update(tSupplier);
        }

        return Result.toResult(result > 0 ,result);
    }

    @PostMapping("deleted")
    @ResponseBody
    public Result deleted(String id){
        int result = supplierService.deleteById(id);
        return Result.toResult(result > 0, result);
    }

}
