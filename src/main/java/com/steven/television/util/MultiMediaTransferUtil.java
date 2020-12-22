package com.steven.television.util;

import it.sauronsoftware.jave.Encoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * @desc 多媒体传输工具
 * @author steven
 * @date 2020/11/13 15:57
 */
public class MultiMediaTransferUtil {

    private static List<String> videoTypes = Arrays.asList("mp4","avi","rmvb","rm","mov","flv","3gp","mkv","f4v","qsv");
    private static List<String> imageTypes = Arrays.asList("png","jpg","jpeg");

    public static String fileUpload(HttpServletRequest request,String targetLoc) throws IOException {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(!commonsMultipartResolver.isMultipart(request)){
            return "FAIL";
        }
        //将request变成多部分request
        MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
        //获取multiRequest 中所有的文件名
        Iterator iter=multiRequest.getFileNames();
        while(iter.hasNext())
        {
            //一次遍历所有文件
            MultipartFile file=multiRequest.getFile(iter.next().toString());
            if(file!=null)
            {
                String path=targetLoc+file.getOriginalFilename();
                //上传
                file.transferTo(new File(path));
            }

        }

        return null;
    }


    /**
     * 上传图片、视频、其他文件
     * @param request
     * @param targetLoc
     * @return
     * @throws IOException
     */
    public static String fileUpload2(HttpServletRequest request,String targetLoc) throws IOException {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(!commonsMultipartResolver.isMultipart(request)){
            return "FAIL";
        }
        MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
        Iterator iter=multiRequest.getFileNames();
        while(iter.hasNext())
        {
            MultipartFile file=multiRequest.getFile(iter.next().toString());
            if(file!=null)
            {
                String path=targetLoc+file.getOriginalFilename();
                //上传
                file.transferTo(new File(path));
            }

        }

        return null;
    }

    /**
     * 文件上传
     * @param file
     * @param targetLoc
     * @return 文件名
     * @throws IOException
     */
    public static String fileUpload(MultipartFile file,String targetLoc) throws IOException{
        if(file.isEmpty()){
            return "FAIL";
        }
        File targetFileLoc = new File(targetLoc);
        if(!targetFileLoc.exists() || !targetFileLoc.isDirectory()){
            targetFileLoc .mkdir();
        }
        String fileName = file.getOriginalFilename();
        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //创建的文件名
        String fileId = UUIDUtil.getUUIDCaseStr();
        //文件存放全路径
        String diskFileName = targetLoc + fileId + suffix;
        file.transferTo(new File(diskFileName));
        return fileId+suffix;
    }

    /**
     * 文件上传
     * @param file
     * @param targetLoc
     * @return
     * @throws IOException
     */
    public static Result fileUploadReturnObject(MultipartFile file, String targetLoc) throws IOException{
        if(file.isEmpty()){
            return Result.failed("空文件");
        }
        File targetFileLoc = new File(targetLoc);
        if(!targetFileLoc.exists() || !targetFileLoc.isDirectory()){
            targetFileLoc .mkdir();
        }
        String fileName = file.getOriginalFilename();
        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //创建的文件名
        String fileId = UUIDUtil.getUUIDCaseStr();
        //文件存放全路径
        String diskFileName = targetLoc + fileId + suffix;
        file.transferTo(new File(diskFileName));
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("fileId",fileId);
        map.put("fileName",fileId+suffix);
        return Result.success(map);
    }


    /**
     * 上传图片、视频、其他文件
     * @param file
     * @param targetLoc
     * @return 文件名
     * @throws IOException
     */
    public static Map<String,Object> fileUpload2(MultipartFile file,String targetLoc) throws IOException{
        Map<String, Object> map = new HashMap<String, Object>(4);
        if(file.isEmpty()){
            map.put("state",false);
            map.put("msg","FAIL");
            return map;
        }
        File targetFileLoc = new File(targetLoc);
        if(!targetFileLoc.exists() || !targetFileLoc.isDirectory()){
            targetFileLoc .mkdir();
        }
        String fileName = file.getOriginalFilename();
        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //创建的文件名
        String fileId = UUIDUtil.getUUIDCaseStr();
        //文件存放全路径
        String diskFileName = targetLoc + fileId + suffix;
        File destFile = new File(diskFileName);
        file.transferTo(destFile);

        map.put("state",true);
        map.put("msg","上传成功");
        map.put("fileId",fileId+suffix);
        if(videoTypes.contains(suffix.substring(1))){
            Map videoInfo = getVideoInfo(destFile);
            if(videoInfo != null && videoInfo.size()>0){
                map.put("fileType","video");
                map.put("duration",videoInfo.get("duration"));
                return map;
            }

            //解析错误就删除文件
            destFile.delete();
            map.put("state",false);
            map.put("msg","文件不支持");
            return map;
        }

        map.put("fileType","image");
        return map;
    }


    /**
     * 获取视频时长（单位：秒）
     * @param file
     * @return
     */
    private static Map getVideoInfo(File file) {
        Map<String, Object> map = new HashMap<String, Object>();
        Encoder encoder = new Encoder();
        try {
            it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(file);
            long duration = m.getDuration();
            long secondDuration = duration / 1000;
            //获取视频秒数
            map.put("duration", secondDuration);
            //获取视频格式
            map.put("format", m.getFormat());
            //获取视频宽高
            map.put("width", m.getVideo().getSize().getWidth());
            //获取视频长高
            map.put("height", m.getVideo().getSize().getHeight());
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            //获取视频大小
            String size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) +"MB";
            map.put("size", size);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //file.delete();
        }
        return map;
    }

}
