Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth() + 1, // 月份
        "d+" : this.getDate(), // 日
        "h+" : this.getHours(), // 小时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
        "S" : this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

};

var Common = function () {

    return {
        // 初始化各个函数及对象
        init: function () {

        },

        strIsNotEmpty: function(str) {
            if (str != null && str != undefined && str != '') {
                return true;
            }
            return false;
        },

        // 时间戳转换成指定日期格式
        formatTime: function(time, format) {
            var t = new Date(time);
            var tf = function(i){return (i < 10 ? '0' : '') + i};
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
                switch(a){
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        }


    };

}();

jQuery(document).ready(function() {
    Common.init();
});

/**
 * 通过字符串日期加上秒数的时间
 * @param dateStr
 * @param seconds
 * @returns {null|Date}
 */
function dateAddSeconds(dateStr,seconds){
    let date = initDate(dateStr);
    if(date == null)
        return null;
    return new Date(date.getTime()+seconds*1000);
}

/**
 * 通过字符串日期加上秒数的时间(获取字符串)
 * @param dateStr
 * @param seconds
 * @returns {null|Date}
 */
function dateAddSeconds2Str(dateStr,seconds){
    let date = dateAddSeconds(dateStr,seconds);
    return dateToDateTimeStr(date);
}
/**
 * 初始化日期
 * @param dateStr
 * @returns {null|Date}
 */
function initDate(dateStr){
    if(dateStr == null || dateStr == ''){
        return null;
    }
    let split = dateStr.split(" ");
    let date = split[0].split("-");
    let time = split[1].split(":");
    let year = Number(date[0]);
    let month = Number(date[1].startsWith("0",0) ? date[1].replace("0","") : date[1]);
    let day = Number(date[2].startsWith("0",0) ? date[2].replace("0","") : date[2]);
    let hour = Number(time[0].startsWith("0",0) ? time[0].replace("0","") : time[0]);
    let minute = Number(time[1].startsWith("0",0) ? time[1].replace("0","") : time[1]);
    let second = Number(time[2].startsWith("0",0) ? time[2].replace("0","") : time[2]);

    return new Date(year,month-1,day,hour,minute,second,0);
}

/**
 * 将日期装换成字符串
 * @param date
 * @returns {*}
 */
function dateToDateTimeStr(date){
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let day = date.getDate();
    let hours = date.getHours();
    let minutes = date.getMinutes();
    let seconds = date.getSeconds();
    return year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hours<10?"0"+hours:hours)+":"+(minutes<10?"0"+minutes:minutes)+":"+(seconds<10?"0"+seconds:seconds);
}
/**
 * 将日期时间装换成字符串
 * @param date
 * @returns {*}
 */
function dateToDateStr(date){
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let day = date.getDate();
    return year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day);
}

/**
 * 比较两日期的大小：1为前者大；0为相等；-1为后者大
 * @param date1
 * @param date2
 * @returns {number}
 */
function compareDate(date1,date2){
    let diff = date1.getTime()-date2.getTime();
    return diff>0 ? 1 : diff==0 ? 0 : -1;
}
/**
 * 比较两日期的大小：1为前者大；0为相等；-1为后者大
 * @param date1
 * @param date2
 * @returns {number}
 */
function compareDateStr(dateStr1,dateStr2){
    let split = dateStr1.split(" ");
    let date = split[0].split("-");
    let time = split[1].split(":");
    let year = Number(date[0]);
    let month = Number(date[1].startsWith("0",0) ? date[1].replace("0","") : date[1]);
    let day = Number(date[2].startsWith("0",0) ? date[2].replace("0","") : date[2]);
    let hour = Number(time[0].startsWith("0",0) ? time[0].replace("0","") : time[0]);
    let minute = Number(time[1].startsWith("0",0) ? time[1].replace("0","") : time[1]);
    let second = Number(time[2].startsWith("0",0) ? time[2].replace("0","") : time[2]);

    let split2 = dateStr2.split(" ");
    let date2 = split2[0].split("-");
    let time2 = split2[1].split(":");
    let year2 = Number(date2[0]);

    let month2 = Number(date2[1].startsWith("0",0) ? date2[1].replace("0","") : date2[1]);
    let day2 = Number(date2[2].startsWith("0",0) ? date2[2].replace("0","") : date2[2]);
    let hour2 = Number(time2[0].startsWith("0",0) ? time2[0].replace("0","") : time2[0]);
    let minute2 = Number(time2[1].startsWith("0",0) ? time2[1].replace("0","") : time2[1]);
    let second2 = Number(time2[2].startsWith("0",0) ? time2[2].replace("0","") : time2[2]);

    if(year>year2)
        return 1;
    else if(year<year2)
        return -1;
    else if(month>month2)
        return 1;
    else if(month<month2)
        return -1;
    else if(day>day2)
        return 1;
    else if(day<day2)
        return -1;
    else if(hour>hour2)
        return 1;
    else if(hour<hour2)
        return -1;
    else if(minute>minute2)
        return 1;
    else if(minute<minute2)
        return -1;
    else if(second>second2)
        return 1;
    else if(second<second2)
        return -1;
    else
        return 0;
}