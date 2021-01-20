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
        .form0{
            flex:0 0 150px;background-color: #D3D4D3;border: 1px solid black;color: white;text-align: center;line-height: 48px;
        }
        .form1{
            flex:0 0 150px;background-color: #555555;border: 1px solid black;color: white;text-align: center;line-height: 48px;
        }
        .form2{
            flex:0 0 150px;background-color: #009E94;border: 1px solid black;color: white;text-align: center;line-height: 48px;
        }
        .form3{
            flex:0 0 150px;background-color: #e60000;border: 1px solid black;color: white;text-align: center;line-height: 48px;
        }
    </style>
</head>

<body>
<div id="form-unit" style="display: none;width: 880px;height: 70px;margin-left:10px;">
    <div id="form-item" style="width:880px;height:50px;font-size: 15px;overflow-x: scroll;display: flex;flex-direction: row;align-items: center;">
        <%--<div class="form0">00:00:22--12:12:22</div>--%>
    </div>
    <div style="height: 20px;display: flex;flex-direction: row;justify-content: flex-end;align-items: center;margin-right: 20px;font-size: 13px;">
        <%--白色未拍期0；灰色已排期1；绿色可申请2；红色已占用3--%>
        <div style="width: 80px;height: 20px;display: flex;flex-direction: row;justify-content: space-evenly;align-items: center;">
            <div style="background-color: #D3D4D3;width: 10px;height: 10px;">&nbsp;</div>
            <div style="line-height: 20px;">未排期</div>
        </div>
        <div style="width: 80px;height: 20px;display: flex;flex-direction: row;justify-content: space-evenly;align-items: center;">
            <div style="background-color: #555555;width: 10px;height: 10px;">&nbsp;</div>
            <div style="line-height: 20px;">已排期</div>
        </div>
        <div style="width: 80px;height: 20px;display: flex;flex-direction: row;justify-content: space-evenly;align-items: center;">
            <div style="background-color: #009E94;width: 10px;height: 10px;">&nbsp;</div>
            <div style="line-height: 20px;">可申请</div>
        </div>
        <div style="width: 80px;height: 20px;display: flex;flex-direction: row;justify-content: space-evenly;align-items: center;">
            <div style="background-color:#e60000;width: 10px;height: 10px;">&nbsp;</div>
            <div style="line-height: 20px;">已占用</div>
        </div>
    </div>
</div>
<div style="margin: 10px;display: flex;flex-direction: row">
    <div style="width: 500px;height: 540px;">
        <div style="display: flex;flex-direction: row">
            <select name="orgId" class="layui-select layui-input-inline">
                <option value="" >请选择供应商</option>
                <c:forEach var="item" items="${suppliers}">
                    <option value="${item.supplierId}" <c:if test="${item.supplierId==orgId}">selected</c:if> >${item.supplierName}</option>
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
        <table style="border-spacing:0px 10px;border-collapse:separate;" id="tableForm">
            <colgroup>
                <col width="100px">
                <col width="250px">
            </colgroup>
            <tr>
                <td style="text-align: center">
                    <label >选择频道：</label>
                </td>
                <td >
                    <select name="channelId" class="layui-select layui-input-inline" lay-verify="required" lay-filter="channel">
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
            <tr id="endTime" style="display: none;background: white">
                <td style="text-align: center">
                    <label >结束时间：</label>
                </td>
                <td >
                    <input type="text" name="endTime" value="" class="layui-input" readonly>
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
    var formList = null;
    var list = null;

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

        let startTime = $("input[name=startTime]").val();
        $("#endTime").attr("style","display:none");
        console.log(startTime);
        if(startTime != null && startTime != ''){
            $("input[name=endTime]").val(dateAddSeconds2Str(startTime,Number(list[index].tvLength)));
            $("#endTime").attr("style","background: white");
        }

        viewDate();
    }

    layui.use(['form', 'layedit', 'laydate','upload'], function() {
        let form = layui.form
            , layedit = layui.layedit
            , laydate = layui.laydate;
        let $ = layui.$;

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
            let startTime = $("input[name=startTime]").val();
            let importLength = $("input[name=importLength]").val();
            let endTime = dateAddSeconds2Str(startTime,Number(importLength));
            let canSubmit = false;
            debugger;
            for(let i in formList){
                //  白色未拍期0；灰色已排期1；绿色可申请2；红色已占用3
                if(compareDateStr(formList[i].startTime,startTime)<=0 && compareDateStr(endTime,formList[i].endTime)<=0){
                    if(formList[i].used==1 || formList[i].used==3){
                        canSubmit = false;
                        break;
                    }else if(formList[i].used==0 || formList[i].used==2){
                        canSubmit = true;
                        break;
                    }
                }
            }
            if(!canSubmit){
                layer.msg("该时间段不能申请", {time: 1500, anim: 6});
                return false;
            }


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
            // min: 'new Date()',
            /*change: function(value, date, endDate){
                $("input[name=startTime]").val(value);
                viewDate();
            },*/
            done: function(value, date, endDate){
                $("input[name=startTime]").val(value);
                viewDate();
                let importLength = $("input[name=importLength]").val();
                if(importLength != null && importLength != "") {
                    $("input[name=endTime]").val(dateAddSeconds2Str(value, Number(importLength)));
                    $("#endTime").attr("style", "background:white;");
                }
            }
        });

        form.on('select(channel)',function(e){
            viewDate()
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
    function viewDate(){
        let startTime = $("input[name=startTime]").val();
        let importLength = $("input[name=importLength]").val();
        let type = '${type}';
        let channelId = $("select[name=channelId] option:selected").val();

        $.post('${rootPath}/import/formList',{date:startTime,type:Number(type),channelId:channelId,length:Number(importLength)},function (res){
            if(!res.state || res.result.length==0)
                return;

            let result = res.result;
            formList = res.result;
            console.log(result);
            let html = "";
            for(let i in result){
                html += "<div class='form"+result[i].used+"'>"+result[i].dateDis+"</div>";
            }
            // $("#form-unit").removeAttr("display");
            $("#form-unit").attr("style","width: 880px;height: 70px;margin-left:10px;");
            $("#form-item div").remove();
            $("#form-item").append(html);
        });
    }
    function changeChannel(obj){
        console.log(obj);
        viewDate();
    }
</script>
</body>
</html>
