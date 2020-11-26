package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.constant.CommonConstrant;
import com.steven.television.services.SystemService;
import com.steven.television.util.MultiMediaTransferUtil;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 17:44
 */
@Controller
@RequestMapping("media")
public class MediaController extends BaseController {

    @Resource
    private SystemService systemService;

    @PostMapping("uploadFile")
    @ResponseBody
    public Result uploadFile(MultipartFile file, HttpServletRequest request){
        try {
            String filePath = systemService.selectValueByKey(CommonConstrant.FILE_PATH);
            String fileId = MultiMediaTransferUtil.fileUpload(file, filePath);
            if(StringUtil.isEquale(fileId,"FAIL")){
                return Result.failed("上传失败");
            }
            return Result.success(fileId);
        }catch(IOException e){
            return Result.failed("服务器异常");
        }
    }

    /**
     * 上传图片、视频、其他文件
     * @param file
     * @param request
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public Map upload(MultipartFile file, HttpServletRequest request){
        try {
            String filePath = systemService.selectValueByKey(CommonConstrant.FILE_PATH);
//            String fileId = MultiMediaTransferUtil.fileUpload(file, filePath);
            Map<String, Object> map = MultiMediaTransferUtil.fileUpload2(file, filePath);

            return map;
        }catch(IOException e){
            Map<String, Object> map = new HashMap<String, Object>(4);
            map.put("state",false);
            map.put("msg","服务器异常");
            return map;
        }
    }
}
