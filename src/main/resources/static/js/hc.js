
//针对与折现跟柱形图
function initChart(container,isOpenLegend,isOpenCredits,chartTitle,secondTitle,categories,xRevealNum,yTitle,data){
	console.info(data);
	 // 图表配置
    var options = {
          chart: {
        	  borderWidth: 0.5//外边框宽度0.5
        }, 
        lang:{
	    	  noData:'无数据....'
	         ,showLoading:'数据加载中....'   //chart.showLoading()  时会显示这个状态值
	    },
        legend: {
        	enabled:isOpenLegend//关掉图例
		},
		credits: {
        	enabled:isOpenCredits//版权信息
		},
        title: {
            text: chartTitle,                // 标题
            useHTML:true                           // 是否解析html标签，设置解析后，可以使用例如a等html标签   通过这个来使用css控制标题的所有属性;超链接之类的  设置为null即不显示
        },
       
        subtitle: {
            text: secondTitle,
            align:'right',
            useHTML:true
        },
        xAxis: {// x 轴分类
            categories: categories,
            labels:{
            	staggerLines: xRevealNum,//水平lable显示的行数  只针对x轴有作用
            	enabled:true    //是否显示x轴，y轴lable信息  也就是x y轴的刻度值
            }
        },
        yAxis: {// y 轴标题
            title: {
                text: yTitle    //设置为null即不现实              
            },
            labels:{
            	enabled:true   //是否显示x轴，y轴lable信息
            }
        },
        plotOptions: {
			series: {
				stacking: 'normal'  
			}
		},
        tooltip:{
			shared: true   //共享提示框
		},
        series: data
    };
   // 图表初始化函数
   var chart = Highcharts.chart(container, options);
   return chart;
}
