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
</head>

<body>

<div style="margin: 10px;">
    <form class="layui-form" action="${rootPath}/channel/save" style="margin-top:10px;" method="post">
        <div class="layui-form-item" style="display: none">
            <input type="text" name="channelId" value="${bean.channelId}">
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label2" >频道名称：</label>
            <div class="layui-input-block" >
                <input type="text" name="channelName" value="${bean.channelName}" lay-verify="required" autocomplete="off" placeholder="请输入频道名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label2" >频道排序：</label>
            <div class="layui-input-block" >
                <input type="text" name="channelSort" value="${bean.channelSort}" lay-verify="required" autocomplete="off" placeholder="请输入频道排序" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label2" >频道描述：</label>
            <div class="layui-input-block">
                <input type="text" name="remark" value="${bean.remark}" lay-verify="required" autocomplete="off" placeholder="请输入频道描述" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" align="right" style="margin-top: 20px;margin-right: 20px;">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="save" >提交保存</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form
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
    })
</script>
</body>
</html>
