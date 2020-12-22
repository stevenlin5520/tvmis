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
    <style type="text/css">
        .layui-form-select,.layui-select{
            height:30px;
        }
    </style>
</head>
<body>

<div style="margin: 10px;display: flex;flex-direction: row">
    <dvi style="width: 400px;height: 300px;">
        <div style="color: #e60000;text-align: center">
            选中频道和日期，进行自动编排节目
        </div>
        <form class="layui-form" action="${rootPath}/form/autoBuildForm" style="margin-top:10px;" method="post">
            <table style="border-spacing:0px 10px;border-collapse:separate;">
                <colgroup>
                    <col width="100px">
                    <col width="250px">
                </colgroup>
                <tr>
                    <td style="text-align: center">
                        <label >选择频道：</label>
                    </td>
                    <td >
                        <select name="channelId" class="layui-select layui-input-inline" lay-verify="required" >
                            <option value="" >请选择频道</option>
                            <c:forEach var="item" items="${channelList}">
                                <option value="${item.channelId}" <c:if test="${item.channelId==bean.channelId}">selected</c:if> >${item.channelName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center">
                        <label >播放时间：</label>
                    </td>
                    <td >
                        <input type="text" id="date" name="date" value="${date}" lay-verify="required" autocomplete="off" placeholder="请输入更新时间" class="layui-input">
                    </td>
                </tr>
            </table>
            <div class="layui-form-item" align="right" style="margin-top: 30px;margin-right: 20px;">
                <div class="layui-input-block">
                    <button type="submit" class="layui-btn" lay-submit="" lay-filter="save" >提交保存</button>
                </div>
            </div>
        </form>
    </dvi>
</div>
<script type="text/javascript">
    layui.use(['form', 'laydate'], function() {
        let form = layui.form
            , laydate = layui.laydate;

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
            console.log('提交form',data);
            let loading = layer.msg('保存中', {
                icon: 16
                ,anim: -1
                ,shade: 0.5    //遮罩
                ,fixed: true
                ,time: false    //手动关闭
            });
            /*$.ajax({
                url: data.form.action,
                data: JSON.stringify(data.field),
                dataType: 'json',
                // contentType: 'application/json;charset=UTF-8',
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
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
            })*/
            $.post(data.form.action, data.field, function(res){
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

        //日期监听
        laydate.render({
            elem: '#date', //指定元素
            type: 'date',
            format: 'yyyy-MM-dd',
            value: '${date}',
            change: function(value, date, endDate){
                endDate.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date
                }
                $("input[name=date]").val(value);
            }
        });

    })


</script>
</body>
</html>
