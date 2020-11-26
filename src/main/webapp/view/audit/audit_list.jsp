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

</head>

<body>

<div style="margin: 10px;">
    <table class="table table-condensed" style="margin-top:8px;">
        <thead>
            <td align="center">序号</td>
            <td align="center">节目名称</td>
            <td align="center">开始时间</td>
            <td align="center">结束时间</td>
            <td align="center">视频时长</td>
            <td align="center">视频类型</td>
            <td align="center">审核状态</td>
            <td align="center">操作</td>
        </thead>
        <c:forEach var="item" items="${pager.list}" varStatus="status">
            <tr>
                <td align="center">${status.index+1}</td>
                <td align="center">${item.tvName}</td>
                <td align="center"><fmt:formatDate value="${item.startTime}" type="both"></fmt:formatDate> </td>
                <td align="center"><fmt:formatDate value="${item.endTime}" type="both"></fmt:formatDate> </td>
                <td align="center">${item.importLength}</td>
                <td align="center">
                    <c:if test="${item.type==1}">节目</c:if>
                    <c:if test="${item.type==2}">广告</c:if>
                </td>
                <td align="center">
                    <c:if test="${item.auditState==1}">待审核</c:if>
                    <c:if test="${item.auditState==3}">已拒绝</c:if>
                </td>
                <td align="center">
                    <div class="layui-btn-group">
                        <button type="button" class="layui-btn layui-btn-sm " onclick="audit('${item.importId}')">
                            <i class="layui-icon">&#xe642;</i>
                        </button>
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" onclick="viewFile('${item.tvScreen}')">
                            <i class="layui-icon">&#xe64a;</i>
                        </button>
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-warm" onclick="viewFile('${item.tvLocation}')">
                            <i class="layui-icon">&#xe6ed;</i>
                        </button>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%--分页--%>
<div id="pager" align="center" style="position:static"></div>


<script>
    layui.use(['laypage', 'layer'], function(){
        var laypage = layui.laypage;
        var layer = layui.layer;
        var thisPage = '${pager.page}';

        //自定义排版
        laypage.render({
            elem: 'pager',
            count: ${pager.total},
            curr: '${pager.page}',
            limit: '${pager.limit}',
            group: [10,20,30,50,100],
            layout: ['limit', 'prev', 'page', 'next'],
            jump: function(obj,first){
                console.log(obj,first);
                let lastPage = sessionStorage.getItem("lastPage")
                let lastLimit = sessionStorage.getItem("lastLimit")
                if((!first && (lastPage==undefined ? 1 : lastPage != obj.curr || lastLimit==undefined ? 1 : lastLimit != obj.limit))){
                    sessionStorage.setItem('lastPage',obj.curr);
                    sessionStorage.setItem('lastLimit',obj.limit);
                    location.replace('${rootPath}/audit/list?type=${type}&page='+obj.curr+'&limit='+obj.limit);
                    console.log(232323)
                }
            }
        });
    });
    //审核节目申请
    function audit(id){
        layer.open({
            type: 2,
            title:'新增/修改用户信息',
            closeBtn: 1, //显示关闭按钮
            shade: [0.5],
            area: ['450px;', '300px'],
            offset: 't', //居中弹出
            time: 0, //0秒后自动关闭
            anim: 2,
            content: ['${rootPath}/view/audit/audit_detail.html?id='+id+'&path=${rootPath}', 'no'], //iframe的url，no代表不显示滚动条
            <%--content: ['${rootPath}/import/edit?id='+id+'&type=${type}', 'no'], //iframe的url，no代表不显示滚动条--%>
            yes: function(index, layero){
                console.log(index,layero);
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
        });
    }

    function viewFile(file){
        <%--window.open('${filePath}/'+file,"查看文件")--%>-
            layer.open({
                type: 2,
                title:'查看文件',
                closeBtn: 1, //显示关闭按钮
                shade: [0.5],
                area: ['900px;', '600px'],
                offset: 't', //居中弹出
                time: 0, //0秒后自动关闭
                anim: 2,
                content: ['${filePath}/'+file, 'no'], //iframe的url，no代表不显示滚动条
                yes: function(index, layero){
                    console.log(index,layero);
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            });
    }
</script>
</body>
</html>
