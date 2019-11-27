/**
 * @author sujiming
 * @date 2019-07-25 09:57
 */

layui.use(['jquery','table','layer','form','laydate','common'],function () {
    $ = layui.$;
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var common = layui.common;
    
    /**勘站数量统计*/
    $.ajax({
		type : 'post',
		url : '/analyze/analyzeAutoCheckNowCloplete',
		async : false,
		dataType:"json",
		success : function(data) {
			if(data !=null && data.length>0){
				var nowArray=new Array();
			    var allArray=new Array();
			    var monthName=new Array();
			    var minNum=0;
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					var name=obj.date;
					var num=obj.num;
					if(i==0){/**查询最小值*/
						minNum=obj.num;
					}else{
						minNum=num+minNum;
						nowArray[i-1]=num;
						allArray[i-1]=minNum;
						monthName[i-1]=name;
					}
				}
				
			    initMyDateChart(nowArray,allArray,monthName);
			}
		}
	});
    
    
    
    /**5G天线数、天线型号、天线类型*/
    $.ajax({
		type : 'post',
		url : '/analyze/analyzeAutoCheckTx',
		async : false,
		dataType:"json",
		success : function(data) {
			if(data !=null && data.length>0){
				
				
				var ODV2065R18KGnum=0;
				var AEQNnum=0;
				
				var AEQAnum=0;
				var AEQBnum=0;
				
				var banTx=0;
				var meihuaTx=0;
				var jishuTx=0;
				var xidingTx=0;
				var otherNum=0;
				
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					console.info(obj);
					var name=obj.name;
					var num=obj.num;
					if('5G天线数'==name){
						$("#5gtxNum").html(num);
					}
					
					if('AEQA'==name){
						AEQAnum=num;
					}
					if('AEQB'==name){
						AEQBnum=num;
					}
					if('AEQN'==name){
						AEQNnum=num;
					}
					//AEQB \ AEQN
					if('ODV2-065R18K-G'==name){
						ODV2065R18KGnum=num;
					}
					
					if('其他'==name){
						otherNum=num;
					}
					if('板状天线'==name){
						banTx=num;
					}
					if('美化天线'==name){
						meihuaTx=num;
					}
					if('吸顶天线'==name){
						xidingTx=num;
					}
					if('集束天线'==name){
						jishuTx=num;
					}
				}
				
				var xhNum=AEQAnum+ODV2065R18KGnum+AEQBnum+AEQNnum;
				//设置天线型号图形问题
				tianxianLeixingInit(ODV2065R18KGnum,AEQAnum,AEQBnum,AEQNnum);
				//设置天线类型图形问题
				tianxianxinggaoInit(banTx,meihuaTx,jishuTx,xidingTx,otherNum);
				
				var allNumTx=otherNum+banTx+meihuaTx+jishuTx+xidingTx;
				
				if(xhNum >0){
					$("#AEQA").html(Number((AEQAnum/xhNum)*100).toFixed(0)+"%"+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+Number((AEQBnum/xhNum)*100).toFixed(0)+"%");
					$("#ODV2065R18KG").html(Number((ODV2065R18KGnum/xhNum)*100).toFixed(0)+"%"+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+Number((AEQNnum/xhNum)*100).toFixed(0)+"%");
					
				}
				if(allNumTx >0){
					$("#otherNum").html(Number((otherNum/allNumTx)*100).toFixed(0)+"%");
					$("#banTx").html(Number((banTx/allNumTx)*100).toFixed(0)+"%");
					$("#meihuaTx").html(Number((meihuaTx/allNumTx)*100).toFixed(0)+"%");
					$("#jishuTx").html(Number((jishuTx/allNumTx)*100).toFixed(0)+"%");
					$("#xidingTx").html(Number((xidingTx/allNumTx)*100).toFixed(0)+"%");
				}
			}
		}
	});
    
    
    function tianxianxinggaoInit(banTx,meihuaTx,jishuTx,xidingTx,otherNum){
    	 //天线型号、天线类型处理
        var myChart3 = echarts.init(document.getElementById('echartsPic3'), 'walden');
        var option3 =  {
                series: [
                    {
                        name:'访问来源',
                        type:'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: false,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        
                        data:[
                            {value:banTx, name:'直达'},
                            {value:meihuaTx, name:'邮件营销'},
                            {value:jishuTx, name:'联盟广告'},
                            {value:xidingTx, name:'视频广告'},
                            {value:otherNum, name:'百度'},
                        ]
                    }
                ]
            };
        myChart3.setOption(option3);
    }
    
    function tianxianLeixingInit(ODV2065R18KGnum,AEQAnum,AEQBnum,AEQNnum){
    	 var myChart2 = echarts.init(document.getElementById('echartsPic2'), 'walden');
    	 var option2 =  {
    		        series: [
    		            {
    		                name:'访问来源',
    		                type:'pie',
    		                radius: ['50%', '70%'],
    		                avoidLabelOverlap: false,
    		                label: {
    		                    normal: {
    		                        show: false
    		                    },
    		                    emphasis: {
    		                        show: false,
    		                        textStyle: {
    		                            fontSize: '30',
    		                            fontWeight: 'bold'
    		                        }
    		                    }
    		                },
    		                labelLine: {
    		                    normal: {
    		                        show: false
    		                    }
    		                },
    		                data:[
    		                    {value:ODV2065R18KGnum, name:'ODV2065R18KGnum'},
    		                    {value:AEQAnum, name:'AEQA'},
    		                    {value:AEQNnum, name:'AEQN'},
    		                    {value:AEQBnum, name:'AEQB'},
    		                ]
    		            }
    		        ]
    		    };
    	 myChart2.setOption(option2);
    }
    
    
    function initMyDateChart(nowArray,allArray,monthName){
    	 //显示
		var myChart = echarts.init(document.getElementById('lineChart'));
		option = {
	    	    tooltip: {
	    	        trigger: 'axis',
	    	        axisPointer: {
	    	            type: 'cross',
	    	            crossStyle: {
	    	                color: '#999'
	    	            }
	    	        }
	    	    },
	    	   
	    	    legend: {
	    	        data:['当天完成量','累计完成量']
	    	    },
	    	    xAxis: [
	    	        {
	    	            type: 'category',
	    	            data: monthName,
	    	            axisPointer: {
	    	                type: 'shadow'
	    	            }
	    	        }
	    	    ],
	    	    yAxis: [
	    	        {
	    	            type: 'value',
	    	            name: '当天完成量',
	    	            min: 0,
	    	            max: 100,
	    	            interval: 10
	    	        },
	    	        {
	    	            type: 'value',
	    	            name: '累计完成量',
	    	            min: 0,
	    	            max: 700,
	    	            interval: 100
	    	            
	    	        }
	    	    ],
	    	    series: [
	    	        {
	    	            name:'当天完成量',
	    	            type:'bar',
	    	            data:nowArray
	    	        },
	    	        {
	    	            name:'累计完成量',
	    	            type:'line',
	    	            yAxisIndex: 1,
	    	            data:allArray
	    	        }
	    	    ]
	    	};
	    myChart.setOption(option);
    }
    
    
    
    
    /***我的任务展示区域*/
    
  
    
    
    var option1 = {
            toolbox: {
                show:false,
                feature: {
                    saveAsImage: {}
                }
            },
            series: [
                {
                    name:'访问来源',
                    type:'pie',
                    selectedMode: 'single',
                    radius: [0, '30%'],
                    label: {
                        normal: {
                            position: 'inner',
                            show: false
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:[
                        {value:300, name:'直达'},
                        {value:300, name:'邮件营销'},
                        {value:300, name:'联盟广告'},
                        {value:300, name:'视频广告'},
                        {value:300, name:'百度'},
                        {value:100, name:'jingdong'},
                    ]
                },
                {
                    name:'访问来源',
                    type:'pie',
                    radius: ['40%', '55%'],
                    label: {

                        normal: {
                            show: false,
                            formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                            backgroundColor: '#eee',
                            borderColor: '#aaa',
                            borderWidth: 1,
                            borderRadius: 4,
                            // shadowBlur:3,
                            // shadowOffsetX: 2,
                            // shadowOffsetY: 2,
                            // shadowColor: '#999',
                            // padding: [0, 7],
                            rich: {
                                a: {
                                    color: '#999',
                                    lineHeight: 22,
                                    align: 'center'
                                },
                                hr: {
                                    borderColor: '#aaa',
                                    width: '100%',
                                    borderWidth: 0.5,
                                    height: 0
                                },
                                b: {
                                    fontSize: 16,
                                    lineHeight: 33
                                },
                                per: {
                                    color: '#eee',
                                    backgroundColor: '#334455',
                                    padding: [2, 4],
                                    borderRadius: 2
                                }
                            }
                        }
                    },
                    data:[
                        {value:300, name:'直达'},
                        {value:300, name:'邮件营销'},
                        {value:300, name:'联盟广告'},
                        {value:300, name:'视频广告'},
                        {value:300, name:'百度'},
                        {value:100, name:'jingdong'},

                    ]
                }
            ]
        };
   
    
   
   

    var myChart1 = echarts.init(document.getElementById('echartsPic1'), 'walden');
    myChart1.setOption(option1);
    
   
   /**天馈问题数据表格  开始*/
   
    $.ajax({
		type : 'post',
		url : '/analyze/analyzeAutoCheckTk',
		async : false,
		dataType:"json",
		success : function(data) {
			var a1 = new Array();
			var a2 = new Array();
			if(data !=null && data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					a1[i]=obj.name;
					a2[i]=obj.allNum;
				}
			}
			
			var seriesLabel = {
	    		    normal: {
	    		        show: true
	    		    }
	    		};
			//初始化数据
			 var option4 =  {
			            color: ['#709DFF'],
			            xAxis: {
			                type: 'category',
			                data: a1
			            },
			            yAxis: {
			                type: 'value'
			            },
			            series: [{
			                data: a2,
			                label: seriesLabel,
			                type: 'bar'
			            }]
			    };
			 //显示
			 var myChart4 = echarts.init(document.getElementById('antennaFeedbackProblemChart'), 'walden');
			 myChart4.setOption(option4);
		}
	});
   
    /**天馈问题数据表格结束*/
    
    /**工作进度*/
    $.ajax({
		type : 'post',
		url : '/analyze/analyzeAutoCheckKzPlan',
		async : false,
		dataType:"json",
		success : function(data) {
			if(data !=null && data.length>0){
				var completeNum=1;
				var AllNum=0;
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					var name=obj.name;
					var num=obj.num;
					if('avgTime'==name){
						$("#avgTime").html(num+'min');
					}
					if('completeTask'==name){
						completeNum=num;
						$("#completeTask").html(num);
					}
					if('planTask'==name){
						AllNum=num;
						$("#planTask").html(num);
					}
				}
				if(AllNum >0){
					$("#completeTaskLv").html(Number((completeNum/AllNum)*100).toFixed(0)+"%");
				}
			}
			
		}
	});
    /**流程管控*/
    $.ajax({
    	type : 'post',
    	url : '/analyze/analyzeAutoCheckRunTask',
    	async : false,
    	dataType:"json",
    	success : function(data) {
    		if(data !=null && data.length>0){
    			
    			var nameArray = new Array();
    			var dataArray = new Array();
    			
    			for(var i=0;i<data.length;i++){
    				var obj=data[i];
    				var name=obj.name;
    				var num=obj.num;
    				
    				nameArray[i]=name;
    				dataArray[i]=num;
    				
    				if('区域任务分配'==name){
    					$("#quyurenwufenpei").html(num);
    				}
    				if('厂家任务确认'==name){
    					$("#changjiarenwuqueren").html(num);
    				}
    				if('厂家区域审核'==name){
    					$("#changjiaquyushenhe").html(num);
    				}
    				if('厂家审核'==name){
    					$("#changjiashenhe").html(num);
    				}
    				if('性能测试'==name){
    					$("#xingnengceshi").html(num);
    				}
    				if('报告审核'==name){
    					$("#baogaoshenhe").html(num);
    				}
    				if('现场勘站'==name){
    					$("#xianchangkanzhan").html(num);
    				}
    			}
    			initRunLineChartChart(nameArray,dataArray);
    		}
    	}
    });
    
    
    //正在进行任务的统计
    function initRunLineChartChart(nameArray,dataArray){
    	nameArray.push('任务类型分析');
    	dataArray.push(0);
    	
    	var seriesLabel = {
    		    normal: {
    		        show: true
    		    }
    		};
    	
   	 //显示
		var myRunChart = echarts.init(document.getElementById('lineRunChart'));
		optionRun = {
			    xAxis: {
			        type: 'category',
			        data: nameArray
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: [{
			        data: dataArray,
			        label: seriesLabel,
			        type: 'bar'
			    }]
			};
		myRunChart.setOption(optionRun);
   }
    
    
    /***问题天线表数据   开始*/
    
    
   table.render({
	    elem: '#antennaFeedbackProblemListTable'    
	    ,id:'antennaFeedbackProblemListTable'
	    ,url:'/auto/check/record/query'  
	    ,method:'post'    
	    ,where:{
			   "map[flag]":"1"
			  // ,"map[enodebidMain]":$("#enodebidMain").val()
			  // ,"map[baseStationName]":$("#baseStationName").val()
			   }
	    ,cols: [[        
	      //{field:'id', width:30, title: 'ID', sort: true,type:'checkbox'}
	      //,{title: '序号',width:70,templet: '#indexTpl',align:'center'}
	      {field:'enodebid',   width:100, title: '站号',align:'center'}
	      ,{field:'cellName', width:100, title: '小区名称',align:'center'}
	      ,{field:'rruStop', width:100, title: 'RRU卡死',align:'center'}
	      ,{field:'tkFlag', width:100, title: '天馈标签',align:'center'}
	      ,{field:'lineElectricUp', width:150, title: '天线电调线（损坏）',align:'center'}
	      ,{field:'beautifyingStop', width:120, title: '美化罩（卡死）',align:'center'}
	      ,{field:'presetElectricDipOpe', width:150, title: '电下倾角是否可调',align:'center'}
	    ]]
	    ,page: true
	    ,limit:7
	    ,limits:[7] 
        ,loading:true
        ,text: {
            none: '无数据' 
        }
	    ,done: function(res, curr, count){
	    }
        ,height:'full'
        ,cellMinWidth: 80
	  });
   
    /***问题天线表数据  结束*/
    


    table.render({
        elem: '#dataList'
        //,url: projectName+'/autoCheckGcTwo/queryStation'
        ,method:'post'
        ,cols: [[
            {field:'id', width:25, title: 'ID',type:'checkbox', fixed: 'left' }
            ,{field:'enodebid', width:120, title: '基站号', align:'center'}
            ,{field:'baseStationName', title: '基站名',align:'center', width:150 }
            ,{field:'city', title: '城市',align:'center',width:150}
            ,{field:'cellName', title: '小区名',align:'center', width:200 }
            ,{field:'cellEnglishName', width:120, title: '英文小区名',align:'center'}
            ,{field:'cellType', width:120, title: 'Cell_Type', align:'center'}
            ,{field:'lac', width:120, title: 'LAC', align:'center'}
            ,{field:'ci', width:120, title: '小区ID', align:'center'}
            ,{field:'bandModel', width:120, title: 'BAND', align:'center'}
            ,{field:'company', width:120, title: '厂商', align:'center'}
            /* ,{field:'secondConpany', width:120, title: '分公司名称', align:'center'}
             ,{field:'comapnyBelong', width:120, title: '分区归属', align:'center'}*/
            ,{field:'physicsStationNo', width:120, title: '物理站点编号', align:'center'}
            ,{field:'stationType', width:120, title: '站型', align:'center'}
            ,{field:'configured', width:120, title: '配置', align:'center'}
            ,{field:'presetElectricDip', width:120, title: '电下倾角', align:'center'}
            ,{field:'azimuth', width:120, title: '方位角', align:'center'}
            ,{field:'mechanicalLowerInclination', width:120, title: '物理倾角', align:'center'}
            ,{field:'longitude', width:120, title: '经度',align:'center'}
            ,{field:'latitude', width:120, title: '纬度',align:'center'}
            ,{field:'stationHeight', width:120, title: '站高', align:'center'}
            ,{field:'presetElectricDipOpe', width:120, title: '电下倾（可调）', align:'center'}
            ,{field:'commonStationNo', width:120, title: 'LTE共站站点', align:'center'}
            ,{field:'coverScene', width:120, title: '覆盖场景号', align:'center'}
            ,{field:'loopLine', width:120, title: '环线信息', align:'center'}
            ,{field:'stationStatus', width:120, title: '基站状态', align:'center'}
            ,{field:'beautifyType', width:120, title: '美化方式',align:'center'}
            ,{field:'beautifyingStop', width:120, title: '美化罩（卡死）', align:'center'}
            ,{field:'lifeRoundStatus', width:120, title: '生命周期状态', align:'center'}
            ,{field:'useCombiner', width:120, title: '是否使用合路器', align:'center'}
            ,{field:'lowerType', width:120, title: '塔桅类型', align:'center'}
            ,{field:'tkFlag', width:120, title: '天馈标签', align:'center'}
            ,{field:'lineCompany', width:120, title: '天线厂家',align:'center'}
            ,{field:'lineElectricUp', width:120, title: '天线电调（损坏）', align:'center'}
            ,{field:'noWirePortNum', width:120, title: '天线端口', align:'center'}
            ,{field:'antennaHangUp', width:120, title: '天线挂高', align:'center'}
            ,{field:'lineCombiner', width:120, title: '天线合路情况', align:'center'}
            ,{field:'lineType', width:120, title: '天线类型', align:'center'}
            ,{field:'lineStationNo', width:120, title: '天线平台号', align:'center'}
            ,{field:'lineTypeNo', width:120, title: '天线型号', align:'center'}
            ,{field:'netBlong', width:120, title: '网格归属', align:'center'}
            ,{field:'conpanyRegion', width:120, title: '行政区域', align:'center'}
            ,{field:'bcc', width:120, title: 'BCC', align:'center'}
            ,{field:'btsId', width:120, title: 'BTS_ID', align:'center'}
            ,{field:'hop', width:120, title: 'HOP', align:'center'}
            ,{field:'msc', width:120, title: 'MSC', align:'center'}
            ,{field:'ncc', width:120, title: 'NCC', align:'center'}
            ,{field:'tch0', width:120, title: 'TCH0', align:'center'}
            ,{field:'tch1', width:120, title: 'TCH1', align:'center'}
            ,{field:'tch2', width:120, title: 'TCH2', align:'center'}
            ,{field:'tch3', width:120, title: 'TCH3', align:'center'}
            ,{field:'tch4', width:120, title: 'TCH4', align:'center'}
            ,{field:'tch5', width:120, title: 'TCH5', align:'center'}
            ,{field:'trxNu', width:120, title: 'TRX_NU', align:'center'}
            ,{field:'egena', width:120, title: 'EGENA', align:'center'}
            ,{field:'et', width:120, title: 'ET', align:'center'}
            ,{field:'gena', width:120, title: 'GENA', align:'center'}
            ,{field:'bcfId', width:120, title: 'BCF_ID', align:'center'}
            ,{field:'bscId', width:120, title: 'BSC_ID', align:'center'}
            ,{field:'gsm', width:120, title: 'GSM', align:'center'}
            ,{field:'hys', width:120, title: 'HYS', align:'center'}
            ,{field:'nsei', width:120, title: 'NSEI', align:'center'}
            ,{field:'rruStop', width:120, title: 'RRU卡死', align:'center'}
            ,{field:'sdNum', width:120, title: 'SDnum', align:'center'}
            ,{field:'makeType', width:120, title: '制式', align:'center'}
            ,{ title: '操作',toolbar: '#caozuo',align:'center', width:160,fixed: 'right'}
        ]]
        ,page: true
        ,toolbar: '#toolbarDemo'
        ,where:{
            /*"map[projectTeamId]":$("#projectTeam option:selected").attr("id"),*/
            "map[stationName]":$("#stationName").val()
            ,"map[stationNum]":$("#stationNumber").val()
        }
        ,limit:15
        ,limits:[15,30,50,100,200,500]
        ,loading:true
        ,toolbar: '#toolbarDemo'
        ,text: {
            none: '无数据'
        }
        ,done: function(res, curr, count){
            /*$("[data-field = 'appMessage']").children().each(function(){
                var str = $(this).text();
                while (str.indexOf("///")!="-1"){
                    str = str.replace("///",",");
                }
                $(this).text(str);
            });*/
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

    /*日期渲染*/
    laydate.render({
        elem: '#date'
    });


})

function toUserManageHtml() {
    window.location.href="userManage.html";
}
 $("#deleteAlertWindow").click(function () {
        parent.layer.confirm('真的删除行么', function(index){
            obj.del();
            layer.close(index);
        });
 })





