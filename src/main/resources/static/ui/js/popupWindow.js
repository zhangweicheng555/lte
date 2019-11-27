/**
 * @author sujiming
 * @date 2019-08-06 15:46
 */


layui.use(['jquery','table','layer','form','laydate','tree','carousel'],function () {
    $ = layui.$;
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var tree = layui.tree;
    var util = layui.util;
    var carousel = layui.carousel;

    //模拟数据
    var data = [
        {
        title: '一级1'
        ,id: 1
        ,checked: true
        ,spread: true
        ,children: [{
            title: '二级1-1 可允许跳转'
            ,id: 3
            ,href: 'https://www.layui.com/'
            ,children: [{
                title: '三级1-1-3'
                ,id: 23
                ,children: [{
                    title: '四级1-1-3-1'
                    ,id: 24
                    ,children: [{
                        title: '五级1-1-3-1-1'
                        ,id: 30
                    },{
                        title: '五级1-1-3-1-2'
                        ,id: 31
                    }]
                }]
            },{
                title: '三级1-1-1'
                ,id: 7
                ,children: [{
                    title: '四级1-1-1-1 可允许跳转'
                    ,id: 15
                    ,href: 'https://www.layui.com/doc/'
                }]
            },{
                title: '三级1-1-2'
                ,id: 8
                ,children: [{
                    title: '四级1-1-2-1'
                    ,id: 32
                }]
            }]
        },{
            title: '二级1-2'
            ,id: 4
            ,spread: true
            ,children: [{
                title: '三级1-2-1'
                ,id: 9
                ,disabled: true
            },{
                title: '三级1-2-2'
                ,id: 10
            }]
        },{
            title: '二级1-3'
            ,id: 20
            ,children: [{
                title: '三级1-3-1'
                ,id: 21
            },{
                title: '三级1-3-2'
                ,id: 22
            }]
        }]
    },
        {
        title: '一级2'
        ,id: 2
        ,spread: true
        ,children: [{
            title: '二级2-1'
            ,id: 5
            ,spread: true
            ,children: [{
                title: '三级2-1-1'
                ,id: 11
            },{
                title: '三级2-1-2'
                ,id: 12
            }]
        },{
            title: '二级2-2'
            ,id: 6
            ,children: [{
                title: '三级2-2-1'
                ,id: 13
            },{
                title: '三级2-2-2'
                ,id: 14
                ,disabled: true
            }]
        }]
    },
        {
        title: '一级3'
        ,id: 16
        ,children: [{
            title: '二级3-1'
            ,id: 17
            ,fixed: true
            ,children: [{
                title: '三级3-1-1'
                ,id: 18
            },{
                title: '三级3-1-2'
                ,id: 19
            }]
        },{
            title: '二级3-2'
            ,id: 27
            ,children: [{
                title: '三级3-2-1'
                ,id: 28
            },{
                title: '三级3-2-2'
                ,id: 29
            }]
        }]
    }
    ];

    //基本演示
    /*tree.render({
        elem: '#test12'
        ,data: data
        ,showCheckbox: true  //是否显示复选框
        ,id: 'demoId1'
        ,isJump: true //是否允许点击节点时弹出新窗口跳转
        ,click: function(obj){
            var data = obj.data;  //获取当前点击的节点数据
            layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(data));
        }
    });*/
    tree.render({
        elem: '#test12'
        ,data: data
        ,showCheckbox: true  //是否显示复选框
        ,id: 'demoId1'
        ,isJump: true //是否允许点击节点时弹出新窗口跳转
        ,click: function(obj){
            var data = obj.data;  //获取当前点击的节点数据
            layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(data));
        }
    });

    table.render({
        elem: '#tableData'
        //,url: projectName+'/autoCheckGcTwo/queryStation'
        ,method:'post'
        ,cols: [[
            {field:'id', width:25, title: 'ID',type:'checkbox', fixed: 'left' }
            ,{field:'enodebid', width:120, title: '基站号',align:'left' }
            ,{field:'baseStationName', title: '基站名',align:'left', minWidth:150 }
            ,{field:'city', title: '城市',align:'left',width:150}
            ,{field:'cellName', title: '小区名',align:'left', minWidth:200 }
            ,{field:'cellEnglishName', width:120, title: '英文小区名',align:'left'}
            ,{field:'cellType', width:120, title: 'Cell_Type', align:'left'}
            ,{field:'lac', width:120, title: 'LAC', align:'left'}
            ,{field:'ci', width:120, title: '小区ID', align:'left'}
            ,{field:'bandModel', width:120, title: 'BAND', align:'left'}
            ,{field:'company', minWidth:120, title: '厂商', align:'left'}
            ,{ title: '操作',toolbar: '#caozuo',align:'left', width:160,fixed: 'right'}
        ]]
        ,page: true
        ,toolbar: '#toolbarDemo'
        ,limit:14
        ,limits:[10,30,50,100,200,500]
        ,loading:true
        ,text: {
            none: '无数据'
        }
        ,done: function(res, curr, count){
        },
        data: [
            {"id":72,"enodebid":"SD13057","baseStationName":"泖塘","city":"上海","cellName":"泖塘-1","cellEnglishName":"MaoTang","cellType":"FLEXIMR10","lac":"2183","ci":"30575","bandModel":"1800M","company":"诺基亚","physicsStationNo":"501664","stationType":"宏站","configured":"S111","presetElectricDip":"6","azimuth":"30","mechanicalLowerInclination":"3","longitude":"121.19282","latitude":"30.95056","stationHeight":"45","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"661","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:09"},
            {"id":69,"enodebid":"SD23665","baseStationName":"姚虹","city":"上海","cellName":"姚虹-2","cellEnglishName":"YaoHong","cellType":"FLEXIMR10","lac":"4185","ci":"36658","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502501","stationType":"宏站","configured":"S111","presetElectricDip":"10","azimuth":"160","mechanicalLowerInclination":"5","longitude":"121.4002","latitude":"31.18735","stationHeight":"40","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"666","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:37"},
            {"id":70,"enodebid":"G10129","baseStationName":"莲花国际广场","city":"上海","cellName":"莲花国际广场7","cellEnglishName":"G10129LHGJGC7","cellType":"FLEXI EDGE","lac":"4185","ci":"1297","bandModel":"900M","company":"诺基亚","physicsStationNo":"","stationType":"微站","configured":"S21","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"0","longitude":"121.39812","latitude":"31.13512","stationHeight":"0","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"7","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"96","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:07"},
            {"id":1,"enodebid":"SD14359","baseStationName":"雅鹿新苑三区(1)","city":"上海","cellName":"雅鹿新苑三区(1)-2","cellEnglishName":"YaLuXinYuanSanQi(1)","cellType":"FLEXIMR10","lac":"2186","ci":"43592","bandModel":"1800M","company":"诺基亚","physicsStationNo":"531576","stationType":"宏站","configured":"S12","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"15","longitude":"121.3205912","latitude":"31.07413871","stationHeight":"24","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"677","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:55"},
            {"id":2,"enodebid":"SD24066","baseStationName":"名都新城(1)","city":"上海","cellName":"名都新城(1)-2","cellEnglishName":"F03509MingDouXinCheng(1)","cellType":"FLEXIMR10","lac":"4185","ci":"40667","bandModel":"1800M","company":"诺基亚","physicsStationNo":"503509","stationType":"宏站","configured":"S1111","presetElectricDip":"7","azimuth":"120","mechanicalLowerInclination":"0","longitude":"121.3872","latitude":"31.1094","stationHeight":"40","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"674","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:49"},
            {"id":3,"enodebid":"SD21311","baseStationName":"移动奉北闸","city":"上海","cellName":"移动奉北闸-3","cellEnglishName":"YiDongFengBeiZha","cellType":"FLEXIMR10","lac":"3182","ci":"13119","bandModel":"1800M","company":"诺基亚","physicsStationNo":"530223","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"240","mechanicalLowerInclination":"3","longitude":"121.509031","latitude":"30.961471","stationHeight":"38","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"2","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"675","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:51"},
            {"id":4,"enodebid":"SD15867","baseStationName":"移动联丰","city":"上海","cellName":"移动联丰-1","cellEnglishName":"YiDongLianFeng","cellType":"FLEXIMR10","lac":"2180","ci":"58677","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508597","stationType":"宏站","configured":"S111","presetElectricDip":"4","azimuth":"10","mechanicalLowerInclination":"5","longitude":"121.31571","latitude":"30.75327","stationHeight":"42","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"7","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"671","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:38"},
            {"id":5,"enodebid":"SD3217","baseStationName":"徐通风","city":"上海","cellName":"徐通风-3","cellEnglishName":"XUTONGFENG3","cellType":"FLEXIMR10","lac":"5195","ci":"32173","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502611","stationType":"宏站","configured":"S222","presetElectricDip":"2","azimuth":"200","mechanicalLowerInclination":"5","longitude":"121.45899","latitude":"31.16578","stationHeight":"33","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"666","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:46:51"},
            {"id":6,"enodebid":"SD1579","baseStationName":"移动奉收费","city":"上海","cellName":"移动奉收费-2","cellEnglishName":"YiDongFengShouFei","cellType":"FLEXIMR10","lac":"3182","ci":"5792","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508461","stationType":"宏站","configured":"S111","presetElectricDip":"0","azimuth":"120","mechanicalLowerInclination":"1","longitude":"121.490171","latitude":"30.857801","stationHeight":"42","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"1","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"662","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:38"},
            {"id":7,"enodebid":"SD23024","baseStationName":"闵新启德","city":"上海","cellName":"闵新启德-2","cellEnglishName":"MinXinQiDe","cellType":"FLEXIMR10","lac":"4185","ci":"30248","bandModel":"1800M","company":"诺基亚","physicsStationNo":"532317","stationType":"宏站","configured":"S212","presetElectricDip":"4","azimuth":"120","mechanicalLowerInclination":"3","longitude":"121.358921","latitude":"31.112398","stationHeight":"18","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"1","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"655","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:45"},
            {"id":8,"enodebid":"SD12084","baseStationName":"海边","city":"上海","cellName":"海边-1","cellEnglishName":"HaiBian","cellType":"FLEXIMR10","lac":"3182","ci":"20844","bandModel":"1800M","company":"诺基亚","physicsStationNo":"506088","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"30","mechanicalLowerInclination":"1","longitude":"121.51624","latitude":"30.86481","stationHeight":"50","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"683","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:06"},
            {"id":9,"enodebid":"SD1421","baseStationName":"电信松人南","city":"上海","cellName":"电信松人南-2","cellEnglishName":"DianXinSongRenNan","cellType":"FLEXIMR10","lac":"2183","ci":"4212","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508711","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"120","mechanicalLowerInclination":"3","longitude":"121.22501","latitude":"31.00442","stationHeight":"23.5","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"683","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:31"},
            {"id":10,"enodebid":"G10696","baseStationName":"地铁9号线松江南站（1）","city":"上海","cellName":"地铁9号线松江南站（1）9","cellEnglishName":"G10696DT9SJN3","cellType":"FLEXIMULTI","lac":"4185","ci":"6969","bandModel":"900M","company":"诺基亚","physicsStationNo":"","stationType":"微站","configured":"S111","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"0","longitude":"121.22626","latitude":"30.98679","stationHeight":"0","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"103","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:09"},
            {"id":11,"enodebid":"SD21953","baseStationName":"新飞","city":"上海","cellName":"新飞-3","cellEnglishName":"XinFei","cellType":"FLEXIMR10","lac":"2183","ci":"19539","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502119","stationType":"宏站","configured":"S111","presetElectricDip":"6","azimuth":"280","mechanicalLowerInclination":"3","longitude":"121.31281","latitude":"31.03622","stationHeight":"25","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"6","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"671","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:00"},
            {"id":12,"enodebid":"SD13669","baseStationName":"移动南金建","city":"上海","cellName":"移动南金建-1","cellEnglishName":"YiDongNanJinJian","cellType":"FLEXIMR10","lac":"3181","ci":"36696","bandModel":"1800M","company":"诺基亚","physicsStationNo":"509377","stationType":"宏站","configured":"S111","presetElectricDip":"3","azimuth":"0","mechanicalLowerInclination":"2","longitude":"121.67234","latitude":"31.04021","stationHeight":"34","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"682","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:22"},
            {"id":9,"enodebid":"SD1421","baseStationName":"电信松人南","city":"上海","cellName":"电信松人南-2","cellEnglishName":"DianXinSongRenNan","cellType":"FLEXIMR10","lac":"2183","ci":"4212","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508711","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"120","mechanicalLowerInclination":"3","longitude":"121.22501","latitude":"31.00442","stationHeight":"23.5","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"683","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:31"},
            {"id":10,"enodebid":"G10696","baseStationName":"地铁9号线松江南站（1）","city":"上海","cellName":"地铁9号线松江南站（1）9","cellEnglishName":"G10696DT9SJN3","cellType":"FLEXIMULTI","lac":"4185","ci":"6969","bandModel":"900M","company":"诺基亚","physicsStationNo":"","stationType":"微站","configured":"S111","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"0","longitude":"121.22626","latitude":"30.98679","stationHeight":"0","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"103","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:09"},
            {"id":11,"enodebid":"SD21953","baseStationName":"新飞","city":"上海","cellName":"新飞-3","cellEnglishName":"XinFei","cellType":"FLEXIMR10","lac":"2183","ci":"19539","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502119","stationType":"宏站","configured":"S111","presetElectricDip":"6","azimuth":"280","mechanicalLowerInclination":"3","longitude":"121.31281","latitude":"31.03622","stationHeight":"25","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"6","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"671","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:00"},
            {"id":12,"enodebid":"SD13669","baseStationName":"移动南金建","city":"上海","cellName":"移动南金建-1","cellEnglishName":"YiDongNanJinJian","cellType":"FLEXIMR10","lac":"3181","ci":"36696","bandModel":"1800M","company":"诺基亚","physicsStationNo":"509377","stationType":"宏站","configured":"S111","presetElectricDip":"3","azimuth":"0","mechanicalLowerInclination":"2","longitude":"121.67234","latitude":"31.04021","stationHeight":"34","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"682","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:22"}
        ]
    });
    table.render({
        elem: '#table1'
        //,url: projectName+'/autoCheckGcTwo/queryStation'
        ,method:'post'
        ,cols: [[
            {field:'id', width:25, title: 'ID',type:'checkbox', fixed: 'left' }
            ,{field:'enodebid', width:20, title: '基站号',align:'left' }
            ,{field:'baseStationName', title: '基站名',align:'left', width:50 }
            ,{field:'city', title: '城市',align:'left',width:80}
            ,{field:'cellName', title: '小区名',align:'left', width:80 }
            ,{field:'cellEnglishName', width:80, title: '英文小区名',align:'left'}
            ,{field:'cellType', width:80, title: 'Cell_Type', align:'left'}
            ,{field:'lac', width:80, title: 'LAC', align:'left'}
            ,{field:'ci', width:80, title: '小区ID', align:'left'}
            ,{field:'bandModel', width:80, title: 'BAND', align:'left'}
            ,{field:'company', width:60, title: '厂商', align:'left'}
            ,{ title: '操作',toolbar: '#caozuo',align:'left', width:100,fixed: 'right'}
        ]]
        ,page: false
        ,toolbar: '#toolbarDemo'
        /*,limit:10
        ,limits:[15,30,50,100,200,500]*/
        ,loading:true
        ,text: {
            none: '无数据'
        }
        ,done: function(res, curr, count){
        },
        data: [
            {"id":72,"enodebid":"SD13057","baseStationName":"泖塘","city":"上海","cellName":"泖塘-1","cellEnglishName":"MaoTang","cellType":"FLEXIMR10","lac":"2183","ci":"30575","bandModel":"1800M","company":"诺基亚","physicsStationNo":"501664","stationType":"宏站","configured":"S111","presetElectricDip":"6","azimuth":"30","mechanicalLowerInclination":"3","longitude":"121.19282","latitude":"30.95056","stationHeight":"45","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"661","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:09"},
            {"id":69,"enodebid":"SD23665","baseStationName":"姚虹","city":"上海","cellName":"姚虹-2","cellEnglishName":"YaoHong","cellType":"FLEXIMR10","lac":"4185","ci":"36658","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502501","stationType":"宏站","configured":"S111","presetElectricDip":"10","azimuth":"160","mechanicalLowerInclination":"5","longitude":"121.4002","latitude":"31.18735","stationHeight":"40","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"666","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:37"},
            {"id":70,"enodebid":"G10129","baseStationName":"莲花国际广场","city":"上海","cellName":"莲花国际广场7","cellEnglishName":"G10129LHGJGC7","cellType":"FLEXI EDGE","lac":"4185","ci":"1297","bandModel":"900M","company":"诺基亚","physicsStationNo":"","stationType":"微站","configured":"S21","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"0","longitude":"121.39812","latitude":"31.13512","stationHeight":"0","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"7","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"96","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:07"},
            {"id":1,"enodebid":"SD14359","baseStationName":"雅鹿新苑三区(1)","city":"上海","cellName":"雅鹿新苑三区(1)-2","cellEnglishName":"YaLuXinYuanSanQi(1)","cellType":"FLEXIMR10","lac":"2186","ci":"43592","bandModel":"1800M","company":"诺基亚","physicsStationNo":"531576","stationType":"宏站","configured":"S12","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"15","longitude":"121.3205912","latitude":"31.07413871","stationHeight":"24","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"677","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:55"},
            {"id":2,"enodebid":"SD24066","baseStationName":"名都新城(1)","city":"上海","cellName":"名都新城(1)-2","cellEnglishName":"F03509MingDouXinCheng(1)","cellType":"FLEXIMR10","lac":"4185","ci":"40667","bandModel":"1800M","company":"诺基亚","physicsStationNo":"503509","stationType":"宏站","configured":"S1111","presetElectricDip":"7","azimuth":"120","mechanicalLowerInclination":"0","longitude":"121.3872","latitude":"31.1094","stationHeight":"40","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"674","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:49"},
            {"id":3,"enodebid":"SD21311","baseStationName":"移动奉北闸","city":"上海","cellName":"移动奉北闸-3","cellEnglishName":"YiDongFengBeiZha","cellType":"FLEXIMR10","lac":"3182","ci":"13119","bandModel":"1800M","company":"诺基亚","physicsStationNo":"530223","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"240","mechanicalLowerInclination":"3","longitude":"121.509031","latitude":"30.961471","stationHeight":"38","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"2","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"675","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:51"},
            {"id":4,"enodebid":"SD15867","baseStationName":"移动联丰","city":"上海","cellName":"移动联丰-1","cellEnglishName":"YiDongLianFeng","cellType":"FLEXIMR10","lac":"2180","ci":"58677","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508597","stationType":"宏站","configured":"S111","presetElectricDip":"4","azimuth":"10","mechanicalLowerInclination":"5","longitude":"121.31571","latitude":"30.75327","stationHeight":"42","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"7","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"671","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:38"},
            {"id":5,"enodebid":"SD3217","baseStationName":"徐通风","city":"上海","cellName":"徐通风-3","cellEnglishName":"XUTONGFENG3","cellType":"FLEXIMR10","lac":"5195","ci":"32173","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502611","stationType":"宏站","configured":"S222","presetElectricDip":"2","azimuth":"200","mechanicalLowerInclination":"5","longitude":"121.45899","latitude":"31.16578","stationHeight":"33","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"666","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:46:51"},
            {"id":6,"enodebid":"SD1579","baseStationName":"移动奉收费","city":"上海","cellName":"移动奉收费-2","cellEnglishName":"YiDongFengShouFei","cellType":"FLEXIMR10","lac":"3182","ci":"5792","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508461","stationType":"宏站","configured":"S111","presetElectricDip":"0","azimuth":"120","mechanicalLowerInclination":"1","longitude":"121.490171","latitude":"30.857801","stationHeight":"42","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"1","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"662","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:38"},
            {"id":7,"enodebid":"SD23024","baseStationName":"闵新启德","city":"上海","cellName":"闵新启德-2","cellEnglishName":"MinXinQiDe","cellType":"FLEXIMR10","lac":"4185","ci":"30248","bandModel":"1800M","company":"诺基亚","physicsStationNo":"532317","stationType":"宏站","configured":"S212","presetElectricDip":"4","azimuth":"120","mechanicalLowerInclination":"3","longitude":"121.358921","latitude":"31.112398","stationHeight":"18","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"1","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"655","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:45"},
            {"id":8,"enodebid":"SD12084","baseStationName":"海边","city":"上海","cellName":"海边-1","cellEnglishName":"HaiBian","cellType":"FLEXIMR10","lac":"3182","ci":"20844","bandModel":"1800M","company":"诺基亚","physicsStationNo":"506088","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"30","mechanicalLowerInclination":"1","longitude":"121.51624","latitude":"30.86481","stationHeight":"50","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"5","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"683","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:06"},
            {"id":9,"enodebid":"SD1421","baseStationName":"电信松人南","city":"上海","cellName":"电信松人南-2","cellEnglishName":"DianXinSongRenNan","cellType":"FLEXIMR10","lac":"2183","ci":"4212","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508711","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"120","mechanicalLowerInclination":"3","longitude":"121.22501","latitude":"31.00442","stationHeight":"23.5","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"683","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:31"},
            {"id":10,"enodebid":"G10696","baseStationName":"地铁9号线松江南站（1）","city":"上海","cellName":"地铁9号线松江南站（1）9","cellEnglishName":"G10696DT9SJN3","cellType":"FLEXIMULTI","lac":"4185","ci":"6969","bandModel":"900M","company":"诺基亚","physicsStationNo":"","stationType":"微站","configured":"S111","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"0","longitude":"121.22626","latitude":"30.98679","stationHeight":"0","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"103","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:09"},
            {"id":11,"enodebid":"SD21953","baseStationName":"新飞","city":"上海","cellName":"新飞-3","cellEnglishName":"XinFei","cellType":"FLEXIMR10","lac":"2183","ci":"19539","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502119","stationType":"宏站","configured":"S111","presetElectricDip":"6","azimuth":"280","mechanicalLowerInclination":"3","longitude":"121.31281","latitude":"31.03622","stationHeight":"25","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"6","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"671","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:00"},
            {"id":12,"enodebid":"SD13669","baseStationName":"移动南金建","city":"上海","cellName":"移动南金建-1","cellEnglishName":"YiDongNanJinJian","cellType":"FLEXIMR10","lac":"3181","ci":"36696","bandModel":"1800M","company":"诺基亚","physicsStationNo":"509377","stationType":"宏站","configured":"S111","presetElectricDip":"3","azimuth":"0","mechanicalLowerInclination":"2","longitude":"121.67234","latitude":"31.04021","stationHeight":"34","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"682","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:22"},
            {"id":9,"enodebid":"SD1421","baseStationName":"电信松人南","city":"上海","cellName":"电信松人南-2","cellEnglishName":"DianXinSongRenNan","cellType":"FLEXIMR10","lac":"2183","ci":"4212","bandModel":"1800M","company":"诺基亚","physicsStationNo":"508711","stationType":"宏站","configured":"S111","presetElectricDip":"2","azimuth":"120","mechanicalLowerInclination":"3","longitude":"121.22501","latitude":"31.00442","stationHeight":"23.5","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"0","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"683","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:31"},
            {"id":10,"enodebid":"G10696","baseStationName":"地铁9号线松江南站（1）","city":"上海","cellName":"地铁9号线松江南站（1）9","cellEnglishName":"G10696DT9SJN3","cellType":"FLEXIMULTI","lac":"4185","ci":"6969","bandModel":"900M","company":"诺基亚","physicsStationNo":"","stationType":"微站","configured":"S111","presetElectricDip":"0","azimuth":"0","mechanicalLowerInclination":"0","longitude":"121.22626","latitude":"30.98679","stationHeight":"0","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"103","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:09"},
            {"id":11,"enodebid":"SD21953","baseStationName":"新飞","city":"上海","cellName":"新飞-3","cellEnglishName":"XinFei","cellType":"FLEXIMR10","lac":"2183","ci":"19539","bandModel":"1800M","company":"诺基亚","physicsStationNo":"502119","stationType":"宏站","configured":"S111","presetElectricDip":"6","azimuth":"280","mechanicalLowerInclination":"3","longitude":"121.31281","latitude":"31.03622","stationHeight":"25","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"6","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"671","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:48:00"},
            {"id":12,"enodebid":"SD13669","baseStationName":"移动南金建","city":"上海","cellName":"移动南金建-1","cellEnglishName":"YiDongNanJinJian","cellType":"FLEXIMR10","lac":"3181","ci":"36696","bandModel":"1800M","company":"诺基亚","physicsStationNo":"509377","stationType":"宏站","configured":"S111","presetElectricDip":"3","azimuth":"0","mechanicalLowerInclination":"2","longitude":"121.67234","latitude":"31.04021","stationHeight":"34","presetElectricDipOpe":"","commonStationNo":"","coverScene":"","loopLine":"","stationStatus":"","beautifyType":"","beautifyingStop":"","lifeRoundStatus":"","useCombiner":"","lowerType":"","tkFlag":"","lineCompany":"","lineElectricUp":"","noWirePortNum":"","antennaHangUp":"","lineCombiner":"","lineType":"","lineStationNo":"","lineTypeNo":"","netBlong":"","conpanyRegion":"","bcc":"3","btsId":"","hop":"","msc":"","ncc":"","tch0":"","tch1":"","tch2":"","tch3":"","tch4":"","tch5":"","trxNu":"","egena":"","et":"","gena":"","bcfId":"","bscId":"","gsm":"","hys":"","nsei":"","rruStop":"","sdNum":"","bcch":"682","makeType":"2G","tempMark":null,"templateId":21,"projId":4,"createTime":"2019-04-12 09:47:22"}
        ]
    });

    //监听行工具条
    table.on('tool(tableData)', function(obj){
        var data = obj.data;
        console.log(JSON.stringify(data));
        //console.log(obj)
        if(obj.event === 'delete'){
            var index = parent.layer.confirm(
                '真的删除吗',
                {
                    btn: ['重要','奇葩'] //按钮
                },
                function(){
                    parent.layer.close(index);
                },
                function() {
                    parent.layer.msg('也可以这样');
            });
        } else if(obj.event === 'edit'){
            parent.layer.prompt({
                formType: 2
                ,value: data.email
            }, function(value, index){
                obj.update({
                    email: value
                });
                layer.close(index);
            });
        }
    });
    /*重置密码*/
    $("#updateUserPasswordBtn").click(function () {
        layer.open({
            type: 1,
            title: "修改用户密码",
            area: ['450px', '230px'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                parent.layer.close(index)
            },
            content: $('#updateUserPassword')
        });
    });

    /*添加用户*/
    $("#addOneBtn").click(function () {
        layer.open({
            type: 1,
            title: "添加用户",
            area: ['650px', '400px'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                parent.layer.close(index)
            },
            content: $('#addOneUser')
        });
    });

    /*添加项目角色*/
    $("#addProjectRoleBtn").click(function () {
        layer.open({
            type: 1,
            title: "添加用户",
            area: ['650px', '400px'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                parent.layer.close(index)
            },
            content: $('#addProjectRole')
        });
    });

    /*表格弹窗*/
    $("#addTableBtn").click(function () {
        layer.open({
            type: 1,
            title: "添加用户",
            area: ['180', '500'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                parent.layer.close(index)
            },
            content: $('#addTable')
        });
    });
    //常规轮播
    carousel.render({
        elem: '#test1'
        ,arrow: 'always'
        ,width: '750px'
        ,height: '450px'
        ,interval: 2000
        ,anim: 'default'
    });
    /*表格弹窗*/
    /*$("#addTableBtn").click(function () {
        layer.open({
            type: 2,
            title: "添加用户",
            area: ['180', '500'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                layer.close(index)
            },
            content: $('#addTable')
        });
    });*/

    $("#carouselWindow").click(function () {
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

    $("#testItemBtn").click(function () {
        layer.open({
            type: 1,
            title: "图片预览",
            area: ['450', '550'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                layer.close(index)
            },
            content: $('#mainArea_right')
        });
    });

    $("#thresholdTestBtn").click(function () {
        layer.open({
            type: 1,
            title: "阈值配置",
            area: ['450', '550'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                layer.close(index)
            },
            content: $('#updateThreshold')
        });
    });

    $("#appVersionManageBtn").click(function () {
        layer.open({
            type: 1,
            title: "app管理",
            area: ['550', '950'],
            offset: ['100px'],
            shadeClose:true,
            cancel: function(index, layero){
                layer.close(index)
            },
            content: $('#appVersionManage')
        });
    });




})





