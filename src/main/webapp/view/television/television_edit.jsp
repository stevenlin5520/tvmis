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
    <link rel="stylesheet" href="${rootPath}/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="${rootPath}/layui/layui.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

    <style type="text/css">
        .layui-form-label2{
            float: left;
            display: block;
            padding: 9px 15px;
            font-weight: 400;
            line-height: 20px;
            text-align: right;
        }
    </style>
</head>

<body>

<div style="margin: 10px;">
    <form class="layui-form" action="${rootPath}/television/save" style="margin-top:10px;" method="post">
        <input type="text" style="display: none;" name="tvId" value="${bean.tvId}">
        <table style="border-spacing:0px 10px;border-collapse:separate;">
            <colgroup>
                <col width="150px">
                <col width="300px">
                <col width="150px">
                <col width="300px">
            </colgroup>
            <tr>
                <td style="text-align: center">
                    <label >节目名称：</label>
                </td>
                <td >
                    <input type="text" name="tvName" value="${bean.tvName}" lay-verify="required" autocomplete="off" placeholder="请输入节目名称" class="layui-input">
                </td>
                <td style="text-align: center">
                    <label >节目类型：</label>
                </td>
                <td >
                    <select name="tvType" lay-filter="type">
                        <option value="1" <c:if test="${bean.tvType==1}">selected</c:if> >节目</option>
                        <option value="2" <c:if test="${bean.tvType==2}">selected</c:if> >广告</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="text-align: center">
                    <label >节目截图：</label>
                </td>
                <td >
                    <button type="button" class="layui-btn" id="uploadImage" style="margin-right: 50px;">
                        <i class="layui-icon">&#xe67c;</i>上传头像
                    </button>
                    <input type="text" style="display: none;" name="tvScreen" value="${bean.tvScreen}" autocomplete="off" placeholder="请输入用户邮箱" class="layui-input">
                </td>
                <td style="text-align: center">
                    <label >节目视频：</label>
                </td>
                <td >
                    <button type="button" class="layui-btn" id="uploadVideo" style="margin-right: 50px;">
                        <i class="layui-icon">&#xe67c;</i>上传视频
                    </button>
                    <input type="text" name="tvLocation" style="display: none;" value="${bean.tvLocation}" autocomplete="off" placeholder="请添加节目视频" class="layui-input">
                </td>
            </tr>
            <tr >
                <td style="text-align: center">
                    <label >节目时长(秒)：</label>
                </td>
                <td >
                    <input type="text" readonly name="tvLength" value="${bean.tvLength}" lay-verify="required" autocomplete="off" placeholder="请输入节目时长" class="layui-input">
                </td>
                <td style="text-align: center">
                    <label >供应商：</label>
                </td>
                <td class="supplier">
                    <select name="supplierId" lay-filter="type" lay-verify="required" class="supplierSelect">
                        <option value="" >请选择供应商</option>
                        <c:forEach var="supplier" items="${suppliers}" varStatus="status">
                            <option value="${supplier.supplierId}" <c:if test="${bean.supplierId==supplier.supplierId}">selected</c:if> >${supplier.supplierName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="text-align: center">
                    <label >节目描述：</label>
                </td>
                <td colspan="3" rowspan="2" style="">
                    <textarea name="tvDesc" placeholder="请输入内容" class="layui-textarea">${bean.tvDesc}</textarea>
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
    var form

    function loadButton(){
        let tvScreen = "${bean.tvScreen}";
        if(tvScreen == null || tvScreen == ''){
            return ;
        }
        $(".view-image").remove();
        let html = "<button type=\"button\" class=\"layui-btn view-image\" onclick=\"viewImage('${bean.tvScreen}')\">" +
            "                        <i class=\"layui-icon\">&#xe655;</i>查看图片\n" +
            "                    </button>";
        $("#uploadImage").after(html);
        $(".view-video").remove();
        let html2 = "<button type=\"button\" class=\"layui-btn view-video\" onclick=\"viewImage('${bean.tvLocation}')\">" +
            "                        <i class=\"layui-icon\">&#xe655;</i>查看视频\n" +
            "                    </button>";
        $("#uploadVideo").after(html2);
    }

    window.onload=function(){
        loadButton()

        //供应商选择
        let cookieList = document.cookie.split("; ");
        let userType
        let userOrg
        for(let i in cookieList){
            let item = cookieList[i]
            if(cookieList[i].startsWith("userType=")){
                userType = item.substring(9,item.length)
            }else if(cookieList[i].startsWith("userOrg=")){
                userOrg = item.substring(8,item.length)
            }
        }
        if(userType == 3){
            console.log($(".supplierSelect option"))
            $(".supplierSelect option").each(function (index,obj) {
                if(userOrg != $(obj).val()){
                    $(obj).remove()
                }
            })
            setTimeout(function(){
                form.render('select');
            },100)
        }

    }

    layui.use(['form', 'layedit', 'laydate','upload'], function() {
        form = layui.form
            , layedit = layui.layedit
            , laydate = layui.laydate;

        //自定义验证规则
        form.verify({

        });
        //监听提交
        form.on('submit(save)', function(data){
            console.log('提交form',data);
            let loading = layer.msg('保存中', {
                icon: 16
                ,anim: -1
                ,shade: 0.5    //遮罩
                ,fixed: true
                ,time: false    //手动关闭
            });
            $.post(data.form.action, data.field, function(res){
                console.log(res)
                layer.close(loading)
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
            })
            return false;
        });

        //上传图片监听
        let upload = layui.upload;
        upload.render({
            elem: '#uploadImage' //绑定元素
            ,url: 'media/uploadFile' //上传接口
            ,done: function(res){
                //上传完毕回调
                console.log("图片上传回调",res)
                if(res.state){
                    layer.msg("上传成功", {time: 1500, anim: 6});
                    $(".view-image").remove();
                    let html = "<button type=\"button\" class=\"layui-btn view-image\" onclick=\"viewImage('"+res.result+"')\">" +
                        "                        <i class=\"layui-icon\">&#xe655;</i>查看图片\n" +
                        "                    </button>";
                    $("#uploadImage").after(html);
                    $("input[name=tvScreen]").val(res.result);
                }else{
                    layer.msg("上传失败", {time: 1500, anim: 6});
                }
            }
            ,error: function(res){
                console.log("操作异常",res)
                layer.msg("服务异常", {time: 1500, anim: 6});
            }
        });

        //上传视频监听
        upload.render({
            elem: '#uploadVideo', //绑定元素
            url: "media/upload",//上传接口
            accept: 'video',
            before: function(obj){
                //预读本地文件示例，不支持ie8

            },
            done: function(res){
                console.log(res);

                if(res.state){
                    layer.msg("上传成功", {time: 1500, anim: 6});
                    $(".view-video").remove();
                    let html = "<button type=\"button\" class=\"layui-btn view-video\" onclick=\"viewImage('"+res.fileId+"')\">" +
                        "                        <i class=\"layui-icon\">&#xe655;</i>查看视频\n" +
                        "                    </button>";
                    $("#uploadVideo").after(html);
                    $("input[name=tvLocation]").val(res.fileId);
                    $("input[name=tvLength]").val(res.duration);
                }else{
                    layer.msg("上传失败", {time: 1500, anim: 6});
                }
            },
            error: function(res){
                console.log(res);
                //演示失败状态，并实现重传
                console.log("操作异常",res)
                layer.msg("服务异常", {time: 1500, anim: 6});
            }
        });
    })

    function viewImage(file){
        window.open("${filePath}/"+file,"查看文件")
    }
</script>
</body>
</html>
