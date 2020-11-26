package com.steven.television.common;

import com.steven.television.constant.CommonConstrant;
import com.steven.television.entity.TUser;
import com.steven.television.services.SystemService;
import com.steven.television.services.UserService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author steven
 * @desc
 * @date 2020/11/9 22:25
 */
public class BaseController {

    public Logger log = Logger.getLogger(this.getClass().getName());
    /**
     * 系统不进行登录拦截的URL
     */
    private List<String> passUrlList = Arrays.asList("/channel/listTabs","/television/televisionList","/loginIn");

    @Resource
    private SystemService systemService;
    @Resource
    private UserService userService;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected String rootPath;
    protected String filePath;
    protected String sessionId;
    protected String userId;
    protected TUser userInfo;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        System.out.println("BaseController.before");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"),true));
    }

    /**
     * action执行前拦截系统
     * @param request
     * @param response
     */
    @ModelAttribute
    public void beforeInvoke(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.request=request;
        this.response=response;
        this.session=request.getSession();
        this.sessionId=session.getId();

        if(!filterUrl(request)){
            validateToken(request, response);
        }

        this.rootPath = request.getRemoteHost()+":"+request.getLocalPort()+request.getContextPath();
        String rootPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"";
        System.out.println("rootPath = " + rootPath);
        request.setAttribute("rootPath",rootPath);
        filePath = getFilePath();
        System.out.println("filePath = " + filePath);
        request.setAttribute("filePath",filePath);
    }

    /**
     * 获取文件读取位置
     * @return
     */
    private String getFilePath(){
        return systemService.selectValueByKey(CommonConstrant.FILE_READ_PATH);
    }

    /**
     * 校验用户登录是否过期
     * @param request
     * @return
     */
    private void validateToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        TUser tUser = userService.selectByToken(sessionId);
        if(tUser == null){
            //重新登录
            response.sendRedirect("login.html");
            return;
        }
        if(tUser.getStatus() == -1){
            //系统删除该用户，是黑名单用户，禁止访问
            response.sendRedirect("login.html");
            return;
        }
        this.userId = tUser.getUserId();
        this.userInfo = tUser;
    }

    /**
     * 过滤请求
     * @param request
     * @return
     */
    private boolean filterUrl(HttpServletRequest request){
        if(passUrlList.contains(request.getServletPath())){
            System.out.println(request.getServletPath()+"通过拦截器");
            return true;
        }
        return false;
    }
}
