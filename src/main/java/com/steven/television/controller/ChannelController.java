package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.Page;
import com.steven.television.entity.TChannel;
import com.steven.television.services.ChannelService;
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
 * @date 2020/11/10 14:53
 */
@Controller
@RequestMapping(value = "channel",produces = "application/json;charset=utf-8")
public class ChannelController extends BaseController {

    @Resource
    private ChannelService channelService;

    @GetMapping("list")
    public ModelAndView list(Page<TChannel> pager){
        ModelAndView modelAndView = new ModelAndView("view/channel/channel_list");
        //分页
        pager.setStartRow((pager.getPage()-1)*pager.getLimit());
        Page<TChannel> supplierPage = channelService.list(pager);
        modelAndView.addObject("pager",supplierPage);
        return modelAndView;
    }

    /**
     * 前端页面播放查看频道数据Tab
     * @return
     */
    @GetMapping("listTabs")
    @ResponseBody
    public Result listTabs(){
        List<TChannel> list = channelService.list();
        return Result.success(list);
    }

    @GetMapping("edit")
    public String edit(String id, Model model){
        TChannel tChannel = channelService.selectById(id);
        model.addAttribute("bean",tChannel);
        return "view/channel/channel_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public Result save(TChannel tChannel){
        int result = 0;
        if(StringUtil.isBlank(tChannel.getChannelId())){
            tChannel.setChannelId(UUIDUtil.getUUIDSTR());
            tChannel.setCreateTime(new Date());
            tChannel.setStatus(1);
            result = channelService.insert(tChannel);
        }else{
            tChannel.setUpdateTime(new Date());
            result = channelService.update(tChannel);
        }

        return Result.toResult(result > 0 ,result);
    }

    @PostMapping("deleted")
    @ResponseBody
    public Result deleted(String id){
        int result = channelService.deleteById(id);
        return Result.toResult(result > 0, result);
    }
}
