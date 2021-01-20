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
    <div class="layui-btn-group">
        <button type="button" class="layui-btn" onclick="addOrEdit('')">
            <i class="layui-icon">&#xe608;</i> 添加
        </button>
    </div>

    <table class="table table-condensed" style="margin-top:8px;">
        <thead>
            <td align="center">序号</td>
            <td align="center">违规名称</td>
            <td align="center">违规详情</td>
            <td align="center">违规类型</td>
            <td align="center">违规供应商</td>
            <td align="center">添加时间</td>
            <td align="center">操作</td>
        </thead>
        <c:forEach var="item" items="${pager.list}" varStatus="status">
            <tr>
                <td align="center">${status.index+1}</td>
                <td align="center">${item.ruleName}</td>
                <td align="center">${item.ruleDesc}</td>
                <td align="center">
                    <c:if test="${item.ruleType==1}">违规</c:if>
                    <c:if test="${item.ruleType==2}"><span style="color: #e47214">告警</span></c:if>
                    <c:if test="${item.ruleType==3}"><span style="color: #e60000">黑名单</span></c:if>
                </td>
                <td align="center">
                    <c:forEach var="sup" items="${suppliers}" >
                        <c:if test="${sup.supplierId==item.orgId}">${sup.supplierName}</c:if>
                    </c:forEach>
                </td>
                </td>
                <td align="center"><fmt:formatDate value="${item.createTime}" type="both"></fmt:formatDate> </td>
                <td align="center">
                    <div class="layui-btn-group">
                        <button type="button" class="layui-btn layui-btn-sm " onclick="addOrEdit('${item.ruleId}')">
                            <i class="layui-icon">&#xe642;</i>
                        </button>
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-danger" onclick="deleted('${item.ruleId}')">
                            <i class="layui-icon">&#xe640;</i>
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
                    location.replace('${rootPath}/rule/list?page='+obj.curr+'&limit='+obj.limit);
                }
            }
        });
    });

    function addOrEdit(id){
        layer.open({
            type: 2,
            title:'新增/修改用户信息',
            closeBtn: 1, //显示关闭按钮
            shade: [0.5],
            area: ['900px;', '600px'],
            offset: 't', //居中弹出
            time: 0, //0秒后自动关闭
            anim: 2,
            content: ['${rootPath}/rule/edit?id='+id, 'no'], //iframe的url，no代表不显示滚动条
            yes: function(index, layero){
                console.log(index,layero);
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
            /*cancel: function(index, layero){
                console.log(index,layero);
                if(confirm('确定要关闭么')){ //只有当点击confirm框的确定时，该层才会关闭
                    layer.close(index)
                }
                return false;
            }*/
        });
    }
    function deleted(id){
        layer.confirm("确认要删除吗，删除后不能恢复", { title: "删除确认" }, function (index) {
            let loading = layer.msg('删除中', {
                icon: 16
                ,anim: -1
                ,shade: 0.5    //遮罩
                ,fixed: true
                ,time: false    //手动关闭
            });
            $.post('${rootPath}/rule/deleted', {id:id}, function(res){
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
                    layer.msg(res.msg, {time: 1500, anim: 6});
                    window.location.reload();
                }
            })
        })
    }
</script>
</body>
</html>
