package com.steven.television.controller;

import com.steven.television.common.BaseController;
import com.steven.television.constant.CommonConstrant;
import com.steven.television.entity.Page;
import com.steven.television.entity.TSupplier;
import com.steven.television.entity.TUser;
import com.steven.television.entity.UserInfo;
import com.steven.television.services.SupplierService;
import com.steven.television.services.SystemService;
import com.steven.television.services.UserService;
import com.steven.television.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @desc
 * @author steven
 * @date 2020/11/13-12:46
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Resource
	private UserService userService;
	@Resource
	private SupplierService supplierService;
	@Resource
	private SystemService systemService;

	@GetMapping("list")
	public ModelAndView list(Page<TUser> pager){
		ModelAndView modelAndView = new ModelAndView("view/user/user_list");
		//分页
		pager.setStartRow((pager.getPage()-1)*pager.getLimit());
		Page<TUser> userPage = userService.list(pager);
		modelAndView.addObject("pager",userPage);
		return modelAndView;
	}

	@GetMapping("edit")
	public String edit(String id, Model model){
		TUser tUser = userService.selectById(id);
		if(tUser == null){
			tUser = new TUser();
		}
		List<TSupplier> list = supplierService.list();
		List<Object> collect = list.stream().filter(s -> s.getAuditStatus() == 1).collect(Collectors.toList());
		model.addAttribute("bean",tUser);
		model.addAttribute("suppliers",collect);
		model.addAttribute("birthday", DateUtil.getDateStr(tUser.getUserBirthday()));
		return "view/user/user_edit";
	}

	@PostMapping("save")
	@ResponseBody
	public Result save(@RequestBody TUser tUser){
		int result = 0;
		if(StringUtil.isBlank(tUser.getUserId())){
			tUser.setUserId(UUIDUtil.getUUIDSTR());
			tUser.setCreateTime(new Date());
			String value = systemService.selectValueByKey(CommonConstrant.SYS_USER_INIT_PASSWORD);
			if(StringUtil.isBlank(value)){
				return Result.failed("服务异常");
			}
			tUser.setUserPwd(value);
			tUser.setStatus(1);
			result = userService.insert(tUser);
		}else{
			tUser.setUpdateTime(new Date());
			result = userService.update(tUser);
		}

		return Result.toResult(result > 0 ,result);
	}

	@PostMapping("deleted")
	@ResponseBody
	public Result deleted(String id){
		int result = userService.deleteById(id);
		return Result.toResult(result > 0, result);
	}


	/**
	 * 修改个人资料
	 * @return
	 */
	@GetMapping("toEditInfo")
	public String toEditInfo(Model model){
		TUser tUser = userService.selectById(userId);
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(tUser,userInfo);
		if(userInfo != null && StringUtils.isNotBlank(userInfo.getUserAddress())){
			String userAddress = userInfo.getUserAddress();
			String[] split = userAddress.split("-");
			model.addAttribute("province",split[0]);
			model.addAttribute("city",split[1]);
			model.addAttribute("area",split[2]);
		}
		model.addAttribute("user",userInfo);
		model.addAttribute("birthday",DateUtil.getDateStr(userInfo.getUserBirthday()));
		return "view/user/user_info";
	}


	/**
	 * 查看用户信息
	 * @param id
	 * @return
	 */
	@PostMapping("userInfo")
	@ResponseBody
	public Result userInfo(String id){
		if(StringUtil.isBlank(id)){
			return Result.failed("参数有误");
		}
		TUser tUser = userService.selectById(id);
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(tUser,userInfo);

		return Result.success(userInfo);
	}


	/**
	 * 修改密码
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@PostMapping("updatePassword")
	@ResponseBody
	public Result updatePassword(String userId,String userName,String oldPassword,String newPassword){
		if(!ParamsUtil.validateBlank(userId,userName,oldPassword,newPassword)){
			return Result.failed("参数不全");
		}
		TUser tUser = userService.selectById(userId);
		if(StringUtil.isNotEquale(tUser.getUserName(),userName) || StringUtil.isNotEquale(tUser.getUserPwd(),oldPassword)){
			return Result.failed("用户名或密码错误");
		}
		tUser.setUserPwd(oldPassword);
		tUser.setUpdateTime(new Date());
		int update = userService.update(tUser);
		if(update == 0){
			return Result.failed("修改失败");
		}

		return Result.success("修改成功");
	}
}