/**
 * @author sujiming
 * @date 2019-08-12 10:36
 */



var $;
layui.use(['form','carousel','jquery'], function(){
    $ = layui.$;
    var form = layui.form;
    var carousel = layui.carousel;

    /*背景地图*/
    var map = new BMap.Map("baiduMap");
    // 创建地图实例
    map.centerAndZoom(new BMap.Point(121.493, 31.250), 14);
    map.addControl(new BMap.MapTypeControl({
        mapTypes: []
    }));
    // 创建点坐标
    map.setCurrentCity("上海");
    map.enableScrollWheelZoom(true);
    /*设置地图样式*/
    map.setMapStyleV2({
        styleId: '275878cbd1a7480eb8494faf09d7bc7a'
    });



    /*绘制共站信息封装方法*/
    function ComplexCustomOverlay(point, text){
        this._point = point;
        this._text = text;
    }
    ComplexCustomOverlay.prototype = new BMap.Overlay();
    ComplexCustomOverlay.prototype.initialize = function(map){
        this._map = map;
        var text = this._text ;
        var div = this._div = document.createElement("div");
        div.style.position = "absolute";
        div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
        /*设置父div容器宽度*/
        div.style.width = "400px";
        div.style.height = "26px";
        div.style.padding = "2px";
        div.style.lineHeight = "26px";
        div.style.whiteSpace = "nowrap";
        /*div.style.MozUserSelect = "none";*/
        div.style.fontSize = "14px";
        for(var i=0; i<text.length;i++){
            if(text[i].sign == "1"){
                var divChild = document.createElement("div");
                divChild.style.position = "relative";
                divChild.style.cssFloat = "left";
                divChild.style.border = "1px solid #C4C6CF";
                divChild.style.color = "#666666";
                divChild.style.height = "26px";
                divChild.style.width = "50px";
                divChild.style.textAlign = "center";
                /*divChild.style.paddingLeft = "3px";
                divChild.style.paddingRight = "3px";*/
                divChild.style.lineHeight = "26px";
                divChild.style.whiteSpace = "nowrap";
                divChild.style.MozUserSelect = "none";
                divChild.style.fontSize = "14px";
                divChild.style.backgroundColor = "#FFFFB8";
                divChild.appendChild(document.createTextNode(text[i].msg));
                div.appendChild(divChild);
            }else {
                var divChild = document.createElement("div");
                divChild.style.position = "relative";
                divChild.style.cssFloat = "left";
                divChild.style.border = "1px solid #C4C6CF";
                divChild.style.color = "#666666";
                divChild.style.width = "50px";
                divChild.style.textAlign = "center";
                divChild.style.height = "26px";
                /*divChild.style.paddingLeft = "3px";
                divChild.style.paddingRight = "3px";*/
                divChild.style.lineHeight = "26px";
                divChild.style.whiteSpace = "nowrap";
                divChild.style.MozUserSelect = "none";
                divChild.style.fontSize = "14px";
                divChild.style.backgroundColor = "#FFFFFF";
                divChild.appendChild(document.createTextNode(text[i].msg));
                div.appendChild(divChild);
            }
        }
        var that = this;
        map.getPanes().labelPane.appendChild(div);
        return div;
    }
    ComplexCustomOverlay.prototype.draw = function(){
        var map = this._map;
        var pixel = map.pointToOverlayPixel(this._point);
        this._div.style.left = pixel.x-65+ "px";
        this._div.style.top  = pixel.y-50+ "px";
    }
    /*绘制共站信息封装方法结束*/


    //创建图标i标注

    var pt1 = new BMap.Point(121.480, 31.261);
    var myIcon1 = new BMap.Icon("../img/bsNotSurveySign.png", new BMap.Size(100,157));
    var marker1 = new BMap.Marker(pt1,{icon:myIcon1});
    map.addOverlay(marker1);
    var obj1 = [

    ];
    var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(121.480, 31.261) , obj1);
    map.addOverlay(myCompOverlay);


    var pt2 = new BMap.Point(121.493, 31.250);
    var myIcon2 = new BMap.Icon("../img/bsAlreadySurveySign.png", new BMap.Size(100,157));
    var marker2 = new BMap.Marker(pt2,{icon:myIcon2});
    map.addOverlay(marker2);
    var obj2 = [
        {
            sign:"1",
            msg:"2g"
        },{
            sign:"0",
            msg:"3g"
        },{
            sign:"1",
            msg:"L900"
        },{
            sign:"1",
            msg:"L1800"
        }
    ];
    var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(121.493, 31.250) , obj2);
    map.addOverlay(myCompOverlay);


    var pt3 = new BMap.Point(121.473, 31.240);
    var myIcon2 = new BMap.Icon("../img/bsAlreadySurveySign.png", new BMap.Size(100,157));
    var marker2 = new BMap.Marker(pt3,{icon:myIcon2});
    map.addOverlay(marker2);
    var obj2 = [
        {
            sign:"1",
            msg:"2g"
        },{
            sign:"0",
            msg:"L900"
        },{
            sign:"1",
            msg:"L1800"
        }
    ];
    var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(121.473, 31.240) , obj2);
    map.addOverlay(myCompOverlay);










    //常规轮播
    carousel.render({
        elem: '#test1'
        ,arrow: 'always'
        ,width: '750px'
        ,height: '450px'
        ,interval: 2000
        ,anim: 'default'
    });


    $("#carouselWindow1").click(function () {
        layer.open({
            type: 1,
            title: "图片预览",
            area: ['850', '650'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                layer.close(index)
            },
            content: $('#pictureRotation')
        });
    });
    $("#carouselWindow2").click(function () {
        layer.open({
            type: 1,
            title: "图片预览",
            area: ['850', '650'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                layer.close(index)
            },
            content: $('#pictureRotation')
        });
    });
    $("#carouselWindow3").click(function () {
        layer.open({
            type: 1,
            title: "图片预览",
            area: ['850', '650'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                layer.close(index)
            },
            content: $('#pictureRotation')
        });
    });

    
    


});

/*统计栏信息联动*/
function statisticsInfoShow(obj , indexTemp) {
    $(".statisticsTitle").each(function () {
        $(this).removeClass("active")
    });
    $(obj).addClass("active");
    $(".statisticsContent").each(function () {
        $(this).removeClass("statisticsContentActive")
    });
    $(".statisticsContent").each(function (index) {
        console.log("index==="+index)
        if(index == indexTemp){
            console.log("index=jjjjj=="+index)
            $(this).addClass("statisticsContentActive");
        }
    });
}

function statisticsShow() {
        if($(".statisticsContainer").css("display")=="none" ){
            $(".statisticsContainer").show();
            $(".statisticsShowBtnContainer").hide()
        }else{
            $(".statisticsContainer").hide();
            $(".statisticsShowBtnContainer").show()
        }
}