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
    <form action="form/list?page=1&limit=10" method="get" id="formList">
<%--        <div class="layui-btn-group">--%>
        <input value="1" name="page" style="display: none;">
        <input value="10" name="limit" style="display: none;">
        <div style="width: 1000px;height: 40px;display: flex;flex-direction: row;justify-content: flex-start;align-items: center">
            <button type="button" class="layui-btn layui-btn-normal" onclick="updateForm()">
                <i class="layui-icon layui-icon-refresh"></i> 更新节目单
            </button>
            <select name="channelId" class="layui-select layui-input-inline" style="width: 200px;margin-left: 10px">
                <option value="" >请选择频道</option>d
                <c:forEach var="item" items="${channelList}">
                    <option value="${item.channelId}" <c:if test="${item.channelId==channelId}">selected</c:if> >${item.channelName}</option>
                </c:forEach>
            </select>
            <input type="text" id="startTime" name="startTime" value="${startTime}" autocomplete="off" placeholder="请输入播放时间" class="layui-input layui-input-block" style="width: 200px;margin-left: 20px;">
            <button type="submit" class="layui-btn " <%--onclick="search()"--%> style="margin-left: 20px" lay-filter="search">
                <i class="layui-icon layui-icon-search"></i>查询
            </button>
        </div>
    </form>
    <table class="table table-condensed" style="margin-top:8px;">
        <thead>
            <%--<td align="center">序号</td>
            <td align="center">播放时间</td>
            <td align="center">播放频道</td>
            <td align="center">观看次数</td>
            <td align="center">添加时间</td>
            <td align="center">操作</td>--%>
            <td align="center">序号</td>
            <td align="center">播放频道</td>
            <td align="center">视频类型</td>
            <td align="center">播放时间</td>
            <td align="center">开始时间</td>
            <td align="center">结束时间</td>
            <td align="center">播放时长</td>
            <td align="center">节目名称</td>
            <td align="center">操作</td>
        </thead>
        <c:forEach var="item" items="${pager.list}" varStatus="status">
            <tr>
                <%--<td align="center">${status.index+1}</td>
                <td align="center"><fmt:formatDate value="${item.formDate}" type="date"></fmt:formatDate> </td>
                <td align="center">${item.watchNum}</td>
                <td align="center">
                    <c:forEach var="item2" items="${channelList}">
                        <c:if test="${item.formContent == item2.channelId}">${item2.channelName}</c:if>
                    </c:forEach>
                </td>
                <td align="center"><fmt:formatDate value="${item.createTime}" type="both"></fmt:formatDate> </td>
                <td align="center">
                    <div class="layui-btn-group">
                        <button type="button" class="layui-btn layui-btn-sm " onclick="view('${item.formId}')">
                            <i class="layui-icon">&#xe642;</i>
                        </button>
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-danger" onclick="deleted('${item.formId}')">
                            <i class="layui-icon">&#xe640;</i>
                        </button>
                    </div>
                </td>--%>
                    <td align="center">${status.index+1}</td>
                    <td align="center">${item.channelName}</td>
                    <td align="center">${item.tvType==1 ? '节目' : '广告'}</td>
                    <td align="center"><fmt:formatDate value="${item.formDate}" type="date"></fmt:formatDate> </td>
                    <td align="center"><fmt:formatDate value="${item.playStart}" type="both"></fmt:formatDate> </td>
                    <td align="center"><fmt:formatDate value="${item.playEnd}" type="both"></fmt:formatDate> </td>
                    <td align="center">${item.playLength}</td>
                    <td align="center">${item.playName}</td>
                    <td align="center">
                        <div class="layui-btn-group">
                            <%--<button type="button" class="layui-btn layui-btn-sm " onclick="view('${item.formId}')">
                                <i class="layui-icon">&#xe642;</i>
                            </button>
                            <button type="button" class="layui-btn layui-btn-sm layui-btn-danger" onclick="deleted('${item.formId}')">
                                <i class="layui-icon">&#xe640;</i>
                            </button>--%>
                            <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" onclick="viewFile('${item.playScreen}')">
                                <i class="layui-icon">&#xe64a;</i>
                            </button>
                            <button type="button" class="layui-btn layui-btn-sm layui-btn-warm" onclick="viewFile('${item.playVideo}')">
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
    layui.use(['laypage', 'layer','laydate','form'], function(){
        var laypage = layui.laypage,
            form = layui.form,
            layer = layui.layer,
            laydate = layui.laydate;
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
                    location.replace('${rootPath}/form/list?page='+obj.curr+'&limit='+obj.limit+'&channelId=${channelId}&startTime=${startTime}');
                }
            }
        });
        //日期监听
        laydate.render({
            elem: '#startTime', //指定元素
            type: 'date',
            format: 'yyyy-MM-dd',
            value: '${startTime}',
            change: function(value, date, endDate){
                $("input[name=startTime]").val(value);
            }
        });
        //监听提交
       /* form.on('submit(search)', function(data){
            console.log('提交form',data);
            let field = data.field;
            console.log(field)
            field.page=1;
            field.limit=10;
            console.log(field)
            $.post(data.form.action, data.field, function(res){

            })
            return true;
        });*/
    });

    function view(id){
        layer.open({
            type: 2,
            title:'查看节目列表',
            closeBtn: 1, //显示关闭按钮
            shade: [0.5],
            // area: ['1600px', '700px'],
            area: ['700px', '700px'],
            offset: 't', //居中弹出
            // offset: 'auto', //居中弹出
            time: 0, //0秒后自动关闭
            anim: 2,
            content: ['${rootPath}/view/form/form_view.html?id='+id, 'no'], //iframe的url，no代表不显示滚动条
            <%--content: ['${rootPath}/form/view?id='+id, 'no'], //iframe的url，no代表不显示滚动条--%>
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
            $.post('${rootPath}/form/deleted', {id:id}, function(res){
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
    function viewFile(file){
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
    function updateForm(){
        layer.open({
            type: 2,
            title:'更新节目单',
            closeBtn: 1, //显示关闭按钮
            shade: [0.5],
            area: ['400px', '530px'],
            offset: 't', //居中弹出
            time: 0, //0秒后自动关闭
            anim: 2,
            content: ['form/toBuild', 'no'], //iframe的url，no代表不显示滚动条
            yes: function(index, layero){
                console.log(index,layero);
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
        });
    }

</script>
</body>
</html>
