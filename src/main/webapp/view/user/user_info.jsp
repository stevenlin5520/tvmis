<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>My JSP 'view_list.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${rootPath}/css/public.css" media="all" />
	<script type="text/javascript" src="${rootPath}/js/jquery2.1.4.js"></script>
	<link rel="stylesheet" href="${rootPath}/layui/css/layui.css" media="all" />
	<script type="text/javascript" src="${rootPath}/layui/layui.js"></script>


</head>
<body class="childrenBody">
<form class="layui-form layui-row" action="${rootPath}/user/save" method="post">
	<div class="layui-col-md3 layui-col-xs12 user_right">
		<div class="layui-upload-list">
			<img class="layui-upload-img layui-circle userFaceBtn userAvatar" id="userFace" src="${filePath}/${user.userPhoto}">
		</div>
		<input name="userPhoto" value="${user.userPhoto}" style="display: none" lay-verify="userPhoto">
		<button type="button" class="layui-btn layui-btn-primary userFaceBtn"><i class="layui-icon">&#xe67c;</i> 掐指一算，我要换一个头像了</button>
	</div>
	<div class="layui-col-md6 layui-col-xs12">
		<div class="layui-form-item">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-block">
				<input type="text" value="${user.userName}" disabled class="layui-input layui-disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限组别</label>
			<div class="layui-input-block">
				<c:if test="${user.userType == 1}">
					<input type="text" value="系统录入员" disabled class="layui-input layui-disabled">
				</c:if>
				<c:if test="${user.userType == 2}">
					<input type="text" value="系统审核员" disabled class="layui-input layui-disabled">
				</c:if>
				<c:if test="${user.userType == 3}">
					<input type="text" value="供应商" disabled class="layui-input layui-disabled">
				</c:if>
				<c:if test="${user.userType == 9}">
					<input type="text" value="超级管理员" disabled class="layui-input layui-disabled">
				</c:if>
			</div>
		</div>
		<div class="layui-form-item" pane="">
			<label class="layui-form-label">用户性别</label>
			<div class="layui-input-block userSex">
				<input type="radio" name="userSex" value="1" title="男" <c:if test="${user.userSex==1}">checked</c:if> >
				<input type="radio" name="userSex" value="2" title="女" <c:if test="${user.userSex==2}">checked</c:if>>
				<input type="radio" name="userSex" value="0" title="保密" <c:if test="${user.userSex==0}">checked</c:if>>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号码</label>
			<div class="layui-input-block">
				<input type="tel" name="userPhone" value="${user.userPhone}" placeholder="请输入手机号码" lay-verify="phone" class="layui-input userPhone">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">出生年月</label>
			<div class="layui-input-block">
				<input type="text" name="userBirthday" value="${birthday}"  placeholder="请输入出生年月" lay-verify="userBirthday" readonly class="layui-input userBirthday">
			</div>
		</div>
		<div class="layui-form-item userAddress">
			<label class="layui-form-label">家庭住址</label>
			<input type="text" name="userAddress" value="${user.userAddress}" lay-verify="userAddress" style="display: none">
			<div class="layui-input-inline">
				<select name="province" lay-filter="province" class="province">
					<option value="${province}">请选择省</option>
				</select>
			</div>
			<div class="layui-input-inline">
				<select name="city" lay-filter="city" disabled>
					<option value="${city}">请选择市</option>
				</select>
			</div>
			<div class="layui-input-inline">
				<select name="area" lay-filter="area" disabled>
					<option value="${area}">请选择县/区</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">用户邮箱</label>
			<div class="layui-input-block">
				<input type="text" name="userEmail" value="${user.userEmail}" placeholder="请输入邮箱" lay-verify="email" class="layui-input userEmail">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">自我描述</label>
			<div class="layui-input-block">
				<textarea placeholder="请输入内容" name="userDescribe" class="layui-textarea myself">${user.userDescribe}</textarea>
			</div>
		</div>
		<input type="text" name="userId" value="${user.userId}" style="display: none;">
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="submit">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	var form, $,areaData;
	layui.config({
		base : "${rootPath}/js/"
	}).extend({
		"address" : "address"
	})
	layui.use(['form','layer','upload','laydate',"address"],function(){
		form = layui.form;
		$ = layui.jquery;
		var layer = parent.layer === undefined ? layui.layer : top.layer,
				upload = layui.upload,
				laydate = layui.laydate,
				address = layui.address;

		//上传头像
		upload.render({
			elem: '.userFaceBtn',
			url: 'media/uploadFile',
			method : "post",
			done: function(res){
				//上传完毕回调
				if(res.state){
					layer.msg("上传成功", {time: 1500, anim: 6});
					$("input[name=userPhoto]").val(res.result);
					$('#userFace').attr('src',"${filePath}/"+res.result);
				}else{
					layer.msg("上传失败", {time: 1500, anim: 6});
				}
			},error: function(res){
				console.log("操作异常",res)
				layer.msg("服务异常", {time: 1500, anim: 6});
			}
		});

		//添加验证规则
		form.verify({
			userBirthday : function(value){
				if(!/^(\d{4})[\u4e00-\u9fa5]|[-\/](\d{1}|0\d{1}|1[0-2])([\u4e00-\u9fa5]|[-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/.test(value)){
					return "出生日期格式不正确！";
				}
			}
		})
		//选择出生日期
		laydate.render({
			elem: '.userBirthday',
			format: 'yyyy-MM-dd',
			trigger: 'click',
			max : 0,
			mark : {"0-12-15":"生日"},
			done: function(value, date){

			}
		});

		//获取省信息
		address.provinces();

		//提交个人资料
		form.on("submit(submit)",function(param){
			let loading = layer.msg('提交中，请稍候', {
				icon: 16
				,anim: -1
				,shade: 0.5    //遮罩
				,fixed: true
				,time: false    //手动关闭
			});
			postSave(param.form.action,param.field,loading);
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		})
	})

	function postSave(url,data,loading){
		let address = "";
		$.get("json/address.json", function (res) {
			/**
			 * 先整理地址数据
			 * */
			let i;
			let j;
			if(data.province != ""){
				for(i in res){
					if(data.province === res[i].code){
						address += res[i].name;
						break;
					}
				}
			}
			let citys
			if(data.city != ''){
				citys = res[i].childs;
				for(j in citys){
					if(data.city === citys[j].code){
						address = address+"-"+citys[j].name;
						break;
					}
				}
			}
			if(data.area != '' && citys != null){
				let areas = citys[j].childs;
				for(let k in areas){
					if(data.area === areas[k].code){
						address = address+"-"+areas[k].name;
						break;
					}
				}
			}
			data.userAddress = address;
			console.log(data)

			$.ajax({
				url: url,
				data: JSON.stringify(data),
				dataType: 'json',
				contentType: 'application/json;charset=UTF-8',
				type: 'post',
				success: function(res){
					if(!res.state){
						layer.open({
							type: 0,
							title: '提示',
							content: res.msg,
							icon: -1,
							time: 2000
						})
					}else{
						setTimeout(function(){
                            parent.location.reload();
                        },500);
						layer.msg("提交成功！", {time: 1500, anim: 6});
					}
				},
				complete: function(){
					layer.close(loading)
				}
			})
		});
	}
</script>
</body>
</html>