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

<div style="margin: 10px;display: flex;flex-direction: row">
    <div style="width: 500px;height: 540px;">
        <div style="display: flex;flex-direction: row">
            <select name="orgId" class="layui-select layui-input-inline">
                <option value="" >请选择供应商</option>
                <c:forEach var="item" items="${suppliers}">
                    <option value="${item.supplierId}">${item.supplierName}</option>
                </c:forEach>
            </select>
            <input type="text" class="layui-input-inline" placeholder="请输入节目名称" name="tvName">
            <button type="button" class="layui-btn" onclick="search()">
                <i class="layui-icon layui-icon-search"></i> 查询
            </button>
        </div>
        <table style="border-spacing:0px 10px;border-collapse:separate;text-align: center" id="tvTable">
            <colgroup>
                <col width="50px">
                <col width="150px">
                <col width="100px">
                <col width="150px">
                <col width="100px">
            </colgroup>
        </table>
    </div>
    <dvi style="width: 400px;height: 540px;">
        <form class="layui-form" action="${rootPath}/import/save" style="margin-top:10px;" method="post">
        <input type="text" style="display: none;" name="importId" value="${bean.importId}">
        <input type="text" style="display: none;" name="televisionId" value="${bean.televisionId}">
        <input type="text" style="display: none;" name="type" value="${type}">
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
                    <select name="channelId" class="layui-select layui-input-inline" lay-verify="required">
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
                    <input type="text" id="startTime" name="startTime" value="${startTime}" lay-verify="required" autocomplete="off" placeholder="请输入播放时间" class="layui-input">
                </td>
            </tr>
            <tr >
                <td style="text-align: center">
                    <label >视频名称：</label>
                </td>
                <td >
                    <input type="text" id="tvName" value="${tvName}" autocomplete="off"  class="layui-input" readonly>
                </td>
            </tr>
            <tr>
                <td style="text-align: center">
                    <label >视频时长：</label>
                </td>
                <td >
                    <input type="text" name="importLength" value="${bean.importLength}" autocomplete="off"  class="layui-input" readonly>
                </td>
            </tr>
            <%--<tr>
                <td style="text-align: center">
                    <label >结束时间：</label>
                </td>
                <td >
                    <input type="text" name="endTime" value="${endTime}" autocomplete="off" class="layui-input" readonly>
                </td>
            </tr>--%>
        </table>
       <div class="layui-form-item" align="right" style="margin-top: 20px;margin-right: 20px;">
           <div class="layui-input-block">
               <button type="submit" class="layui-btn" lay-submit="" lay-filter="save" >提交保存</button>
           </div>
       </div>
    </form>
    </dvi>
</div>
<script type="text/javascript">
    var list = null
    function search(){
        let tvName = $("input[name=tvName]").val();
        let orgId = $("select[name=orgId]").val();
        $.post('${rootPath}/television/search',{type:${type},orgId:orgId,tvName:tvName},function(res){
            if(res.state){
                list = res.result
                console.log(res)
               $("#tvTable tr").remove()
                let html = "\n" +
                    "                <tr style=\"text-align: center\">\n" +
                    "                    <th>序号</th>\n" +
                    "                    <th>名称</th>\n" +
                    "                    <th>时长</th>\n" +
                    "                    <th>查看</th>\n" +
                    "                    <th>操作</th>\n" +
                    "                </tr>\n" +
                    "            ";

               for(let index in list){
                   html += "<tr>\n" +
                       "                <td>"+(Number(index)+1)+"</td>\n" +
                       "                <td>"+list[index].tvName+"</td>\n" +
                       "                <td>"+list[index].tvLength+"</td>\n" +
                       "                <td>\n" +
                       "                    <button type=\"button\" class=\"layui-btn layui-btn-sm layui-btn-normal\" onclick=\"viewFile('"+list[index].tvScreen+"')\">\n" +
                       "                        <i class=\"layui-icon\">&#xe64a;</i>\n" +
                       "                    </button>\n" +
                       "                    <button type=\"button\" class=\"layui-btn layui-btn-sm layui-btn-warm\" onclick=\"viewFile('"+list[index].tvLocation+"')\">\n" +
                       "                        <i class=\"layui-icon\">&#xe6ed;</i>\n" +
                       "                    </button>\n" +
                       "                </td>\n" +
                       "                <td>\n" +
                       "                    <button type=\"button\" class=\"layui-btn layui-btn-sm \" onclick=\"selectThis("+index+",this)\">\n" +
                       "                        <i class=\"layui-icon \"><!--&#xe643;-->&#xe63f;</i>\n" +
                       "                    </button>\n" +
                       "                </td>\n" +
                       "            </tr>";
               }
                $("#tvTable").append(html)
            }else{
                layer.msg("查询失败", {time: 1500, anim: 6});
            }
        })
    }

    function selectThis(index,_this){
        $("tr").attr("style","background:white")
        $(_this).parent().parent().attr("style","background:grey")
        console.log(list[index])
        $("#tvName").val(list[index].tvName)
        $("input[name=importLength]").val(list[index].tvLength)
        $("input[name=televisionId]").val(list[index].tvId)
        $("input[name=endTime]").val(list[index].tvId)
    }

    layui.use(['form', 'layedit', 'laydate','upload'], function() {
        let form = layui.form
            , layedit = layui.layedit
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
            $.ajax({
                url: data.form.action,
                data: JSON.stringify(data.field),
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
                        let index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);//关闭当前页
                        window.parent.location.reload();
                    }
                },
                complete: function(){
                    layer.close(loading)
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
            elem: '#startTime', //指定元素
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss',
            value: '${startTime}',
            change: function(value, date, endDate){
                console.log(value); //得到日期生成的值，如：2017-08-18
                console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                $("input[name=startTime]").val(value);
            }
        });

    })

    function viewImage(image){
        window.open("${filePath}/"+image,"查看头像")
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

</script>
</body>
</html>
