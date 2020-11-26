package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.Page;
import com.steven.television.entity.TForm;
import com.steven.television.entity.TPlay;
import com.steven.television.services.FormService;
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
 * @date 2020/11/22 12:59
 */
@RequestMapping("form")
@Controller
public class FormController extends BaseController {

    @Resource
    private FormService formService ;

    @GetMapping("list")
    public ModelAndView list(Page<TForm> pager){
        ModelAndView modelAndView = new ModelAndView("view/form/form_list");
        //分页
        pager.setStartRow((pager.getPage()-1)*pager.getLimit());
        Page<TForm> supplierPage = formService.list(pager);
        modelAndView.addObject("pager",supplierPage);
        return modelAndView;
    }

    @GetMapping("edit")
    public String edit(String id, Model model){
        TForm form = formService.selectById(id);
        model.addAttribute("bean",form);
        return "view/form/form_list";
    }


    /*@GetMapping("view")
    public String view(String id, Model model){
        TForm form = formService.selectById(id);
        if(form != null){
            Map map = new HashMap(2);
            map.put("formId",form.getFormId());
            List<TPlay> tPlays = formService.listPlay(map);
            model.addAttribute("plays",tPlays);
            model.addAttribute("playImage",tPlays.get(0).getPlayScreen());
            model.addAttribute("playVideo",tPlays.get(0).getPlayVideo());
        }
        model.addAttribute("bean",form);
        return "view/form/form_view";
    }*/

    @GetMapping("viewForm")
    @ResponseBody
    public Result view(String id){
        TForm form = formService.selectById(id);
        if(form != null){
            Map map = new HashMap(2);
            map.put("formId",form.getFormId());
            List<TPlay> tPlays = formService.listPlay(map);
            Map<String, Object> map1 = new HashMap<String, Object>(4);
            map1.put("list",tPlays);
            map1.put("filePath",filePath);
            return Result.success(map1);
        }
        return Result.failed("没有录入节目");
    }

    @PostMapping("save")
    @ResponseBody
    public Result save(TForm form){
        int result = 0;
        form.setStatus(1);
        if(StringUtil.isBlank(form.getFormId())){
            form.setFormId(UUIDUtil.getUUIDSTR());
            form.setCreateTime(new Date());
            result = formService.insert(form);
        }else{
            form.setUpdateTime(new Date());
            result = formService.update(form);
        }

        return Result.toResult(result > 0 ,result);
    }

    @PostMapping("deleted")
    @ResponseBody
    public Result deleted(String id){
        int result = formService.deleteById(id);
        return Result.toResult(result > 0, result);
    }

}
