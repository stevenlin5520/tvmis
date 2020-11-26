package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.entity.TUser;
import com.steven.television.entity.UserInfo;
import com.steven.television.services.UserService;
import com.steven.television.util.Result;
import com.steven.television.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc
 * @author steven
 * @date 2020/11/8 23:57
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("/index2")
    public String index(HttpServletRequest request, HttpServletResponse response,Model model){
        TUser tUser = userService.selectByToken(sessionId);
        if(tUser != null) {
            response.addCookie(new Cookie("userName", tUser.getUserName()));
            response.addCookie(new Cookie("userId", userId));
            response.addCookie(new Cookie("userOrg", tUser.getOrgId()));
            response.addCookie(new Cookie("userType", tUser.getUserType() + ""));
            response.addCookie(new Cookie("userPhoto",filePath+"/"+tUser.getUserPhoto()));
        }
        return "index";
    }

    @PostMapping("loginIn")
    @ResponseBody
    public Result loginIn(HttpServletRequest request, HttpServletResponse response){
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        if(StringUtil.isBlank(user) || StringUtil.isBlank(password)){
            return Result.failed("参数不全");
        }
        Map map = new HashMap(4);
        map.put("user",user);
        map.put("password",password);
        List<TUser> tUsers = userService.selectByLoginInfo(map);
        if(tUsers == null || tUsers.size() == 0 || tUsers.size() > 1){
            return Result.failed("账号或者密码有误");
        }

        TUser tUser = tUsers.get(0);
        tUser.setUserToken(sessionId);
        int update = userService.update(tUser);
        if(update == 0){
            return Result.failed("登陆失败");
        }
        return Result.success("index2");
    }


    @GetMapping("loginOut")
    public void loginOut() throws IOException {
        session.invalidate();
        response.sendRedirect("login.html");
    }
}
