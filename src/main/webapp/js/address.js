layui.define(["form","jquery"],function(exports){
    var form = layui.form,
    $ = layui.jquery,
    Address = {
        provinces : function() {
            let province = $("select[name=province]").val();
            //加载省数据
            let proHtml = ''
            let that = this;
            $.get("json/address.json", function (data) {
                let citys = []
                for (var i = 0; i < data.length; i++) {
                    if(province === data[i].name){
                        proHtml += '<option value="' + data[i].code + '" selected>' + data[i].name + '</option>';
                        citys = data[i].childs
                        continue;
                    }
                    proHtml += '<option value="' + data[i].code + '">' + data[i].name + '</option>';
                }

                //初始化城市数据
                let areas = []
                let city = $("select[name=city]").val();
                let cityHtml = '<option value="">请选择市</option>';
                for (var i = 0; i < citys.length; i++) {
                    if(citys[i].name === city){
                        cityHtml += '<option value="' + citys[i].code + '" selected>' + citys[i].name + '</option>';
                        areas = citys[i].childs;
                        continue;
                    }
                    cityHtml += '<option value="' + citys[i].code + '">' + citys[i].name + '</option>';
                }
                $("select[name=city]").html(cityHtml).removeAttr("disabled");
                //初始化区县数据
                let area = $("select[name=area]").val();
                let areaHtml = '<option value="">请选择县/区</option>';
                for (var i = 0; i < areas.length; i++) {
                    if(areas[i].name === area){
                        areaHtml += '<option value="' + areas[i].code + '" selected>' + areas[i].name + '</option>';
                        continue;
                    }
                    areaHtml += '<option value="' + areas[i].code + '">' + areas[i].name + '</option>';
                }
                $("select[name=area]").html(areaHtml).removeAttr("disabled");
                $("select[name=province]").append(proHtml);
                form.render();


                form.on('select(province)', function (proData) {
                    $("select[name=area]").html('<option value="">请选择县/区</option>');
                    var value = proData.value;
                    if (value > 0) {
                        that.citys(data[$(this).index() - 1].childs);
                    } else {
                        $("select[name=city]").attr("disabled", "disabled");
                    }
                });
                form.on('select(city)', function (cityData) {
                    var value = cityData.value;
                    if (value > 0) {
                        that.areas(citys[$(this).index() - 1].childs);
                    } else {
                        $("select[name=area]").attr("disabled", "disabled");
                    }
                });
            })
        },
        //加载市数据
        citys : function(citys) {
            var cityHtml = '<option value="">请选择市</option>',that = this;
            for (var i = 0; i < citys.length; i++) {
                cityHtml += '<option value="' + citys[i].code + '">' + citys[i].name + '</option>';
            }
            $("select[name=city]").html(cityHtml).removeAttr("disabled");
            form.render();
            form.on('select(city)', function (cityData) {
                var value = cityData.value;
                if (value > 0) {
                    that.areas(citys[$(this).index() - 1].childs);
                } else {
                    $("select[name=area]").attr("disabled", "disabled");
                }
            });
        },
        //加载县/区数据
        areas : function(areas) {
            var areaHtml = '<option value="">请选择县/区</option>';
            for (var i = 0; i < areas.length; i++) {
                areaHtml += '<option value="' + areas[i].code + '">' + areas[i].name + '</option>';
            }
            $("select[name=area]").html(areaHtml).removeAttr("disabled");
            form.render();
        }
    };
    exports("address",Address);
})