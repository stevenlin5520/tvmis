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

    <script type="text/javascript" src="${rootPath}/js/jquery2.1.4.js"></script>
    <link rel="stylesheet" type="text/css" href="${rootPath}/css/common.css">
    <link rel="stylesheet" href="${rootPath}/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="${rootPath}/layui/layui.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    <script type="text/javascript" src="${rootPath}/js/DateUtil.js"></script>
</head>

<body>

<div style="margin: 10px;">
    <form class="layui-form" action="${rootPath}/user/save" style="margin-top:10px;" method="post">
        <input type="text" style="display: none;" name="userId" value="${bean.userId}">
        <table style="border-spacing:0px 10px;border-collapse:separate;">
            <colgroup>
                <col width="150px">
                <col width="300px">
                <col width="150px">
                <col width="300px">
            </colgroup>
            <tr>
                <td style="text-align: center">
                    <label >用户姓名：</label>
                </td>
                <td >
                        <input type="text" name="userName" value="${bean.userName}" lay-verify="required" autocomplete="off" placeholder="请输入用户姓名" class="layui-input">
                </td>
                <td style="text-align: center">
                    <label >手机号码：</label>
                </td>
                <td >
                    <input type="text" name="userPhone" value="${bean.userPhone}" lay-verify="required" autocomplete="off" placeholder="请输入手机号码" class="layui-input">
                </td>
            </tr>
            <tr>
                <td style="text-align: center">
                    <label >用户邮箱：</label>
                </td>
                <td >
                    <input type="text" name="userEmail" value="${bean.userEmail}" lay-verify="required" autocomplete="off" placeholder="请输入用户邮箱" class="layui-input">
                </td>
                <td style="text-align: center">
                    <label >用户头像：</label>
                </td>
                <td >
                    <button type="button" class="layui-btn" id="uploadImage" style="margin-right: 50px;">
                        <i class="layui-icon">&#xe67c;</i>上传头像
                    </button>
                    <%--<button type="button" class="layui-btn" id="viewImage" onclick="javascrtpt:window.location.href='http://blog.sina.com.cn/mleavs'">
                        <i class="layui-icon">&#xe655;</i>查看头像
                    </button>--%>
                    <input type="text" name="userPhoto" style="display: none;" value="${bean.userPhoto}" autocomplete="off" placeholder="请添加用户头像" class="layui-input">
                </td>
            </tr>
            <tr >
                <td style="text-align: center">
                    <label >用户性别：</label>
                </td>
                <td >
                    <input type="radio" name="userSex" value="1" title="男" <c:if test="${bean.userSex==1}">checked</c:if> >
                    <input type="radio" name="userSex" value="2" title="女" <c:if test="${bean.userSex==2}">checked</c:if> >
                </td>
                <td style="text-align: center">
                    <label >用户权限：</label>
                </td>
                <td >
                    <select name="userType" class="layui-select layui-input-inline">
                        <option value="1" <c:if test="${bean.userType==1}">selected</c:if>>录入员</option>
                        <option value="2" <c:if test="${bean.userType==2}">selected</c:if>>审核员</option>
                        <option value="3" <c:if test="${bean.userType==3}">selected</c:if>>供应商</option>
                    </select>
                </td>
            </tr>
            <tr >
                <td style="text-align: center">
                    <label >用户生日：</label>
                </td>
                <td >
                    <input type="text" name="userBirthday" value="${birthday}" id="userBirthday"  autocomplete="off" placeholder="请输入用户生日" class="layui-input">
                </td>
                <td style="text-align: center">
                    <label >所属机构：</label>
                </td>
                <td >
                    <select name="orgId" class="layui-select layui-input-inline" lay-verify="required">
                        <option value="" >请选择平台/供应商</option>
                        <c:forEach var="item" items="${suppliers}">
                            <option value="${item.supplierId}" <c:if test="${item.supplierId==bean.orgId}">selected</c:if> >${item.supplierName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr >
                <td style="text-align: center">
                    <label >用户地址：</label>
                </td>
                <td colspan="3">
<%--                    <input type="text" name="userAddress" value="${bean.userAddress}" autocomplete="off" placeholder="请输入用户地址" class="layui-input">--%>
                    <div class="layui-form-item userAddress">
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
                </td>
            </tr>
            <tr>
                <td style="text-align: center">
                    <label >用户描述：</label>
                </td>
                <td colspan="3" rowspan="2" style="">
                    <textarea name="userDescribe" placeholder="请输入内容" class="layui-textarea">${bean.userDescribe}</textarea>
                </td>
            </tr>
        </table>
       <div class="layui-form-item" align="right" style="margin-top: 20px;margin-right: 20px;">
           <div class="layui-input-block">
               <button type="submit" class="layui-btn" lay-submit="" lay-filter="save" >提交保存</button>
           </div>
       </div>
    </form>
</div>
<script type="text/javascript">


    window.onload=function(){
        //初始化图片
        let userPhoto = "${bean.userPhoto}";
        if(userPhoto == null || userPhoto == ''){
            return ;
        }
        $(".view-image").remove();
        let html = "<button type=\"button\" class=\"layui-btn view-image\" onclick=\"viewImage('${bean.userPhoto}')\">" +
            "                        <i class=\"layui-icon\">&#xe655;</i>查看头像\n" +
            "                    </button>";
        $("#uploadImage").after(html);
    }

    var form, $,areaData;
    layui.config({
        base : "${rootPath}/js/"
    }).extend({
        "address_js" : "address"
    })
    layui.use(['form', 'layedit', 'laydate','upload',"address"], function() {
        let form = layui.form
            , layedit = layui.layedit
            , laydate = layui.laydate
            , address_js = layui.address;
        //获取省信息
        address_js.provinces();

        //自定义验证规则
        form.verify({
            sysKey: function (value) {
                if (value.length = 0) {
                    return '信息不能为空';
                }
            },
            sysValue: function (value) {
                if (value.length = 0) {
                    return '信息不能为空';
                }
            }
        });
        //监听提交
        form.on('submit(save)', function(data){
            /**
             * 先整理地址数据
             * */
            console.log(data)

            // $.ajaxSettings.async = false;
            $.get("json/address.json", function (res) {
                var res = res;
                let address = "";
                let i;
                let j;
                if(data.field.province != ""){
                    for(i in res){
                        if(data.field.province === res[i].code){
                            address += res[i].name;
                            break;
                        }
                    }
                }
                let citys
                if(data.field.city != ''){
                    citys = res[i].childs;
                    for(j in citys){
                        if(data.field.city === citys[j].code){
                            address = address+"-"+citys[j].name;
                            break;
                        }
                    }
                }
                if(data.field.area != '' && citys != null){
                    let areas = citys[j].childs;
                    for(let k in areas){
                        if(data.field.area === areas[k].code){
                            address = address+"-"+areas[k].name;
                            break;
                        }
                    }
                }
                data.field.userAddress = address;

                console.log('提交form',data);
                let loading = layer.msg('保存中', {
                    icon: 16
                    ,anim: -1
                    ,shade: 0.5    //遮罩
                    ,fixed: true
                    ,time: false    //手动关闭
                });
                $.ajax({
                    url: data.form.action,
                    data: JSON.stringify(data.field),
                    dataType: 'json',
                    // contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
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
                            let index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);//关闭当前页
                            window.parent.location.reload();
                        }
                    },
                    complete: function(){
                        layer.close(loading)
                    }
                })
            });
            return false;
        });

        //上传图片监听
        let upload = layui.upload;
        upload.render({
            elem: '#uploadImage' //绑定元素
            ,url: 'media/uploadFile' //上传接口
            ,done: function(res){
                //上传完毕回调
                console.log(11111,res)
                if(res.state){
                    layer.msg("上传成功", {time: 1500, anim: 6});
                    $(".view-image").remove();
                    let html = "<button type=\"button\" class=\"layui-btn view-image\" onclick=\"viewImage('"+res.result+"')\">" +
                        "                        <i class=\"layui-icon\">&#xe655;</i>查看头像\n" +
                        "                    </button>";
                    $("#uploadImage").after(html);
                    $("input[name=userPhoto]").val(res.result);
                }else{
                    layer.msg("上传失败", {time: 1500, anim: 6});
                }
            }
            ,error: function(res){
                console.log("操作异常",res)
                layer.msg("服务异常", {time: 1500, anim: 6});
            }
        });

        //日期监听
        laydate.render({
            elem: '#userBirthday' //指定元素
        });

    })

    function viewImage(image){
        window.open("${filePath}/"+image,"查看头像")
    }
</script>
</body>
</html>
