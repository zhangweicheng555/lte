
/**
 * 公共抽取的js   weichengz
 * 对于自己扩展的js  这个base  是很重要的  务必填写
 */
layui.config({
  base: '/js/' 
});
layui.define(['layer','laydate','jquery'], function(exports) {
	"use strict";

	var $ = layui.jquery,
	laydate = layui.laydate,
	layer = layui.layer;
	
	var common = {
			/**
			 * iframe弹框 open
			 * title：弹框标题
			 * openUrl:页面打开链接
			 * width:宽度   1100px
			 * height:高度  560px
			 * isMax:是否允许最大化功能
			 * 使用场景： 只打开这个页面 不在做其他的操作
			 * 预览
			 */
			openEditDialogView:function(title,openUrl,width,height,isMax){
				var indexModel=parent.layer.open({
					type: 2, 
					title:title,
					content: openUrl,
					skin:"layui-layer-molv",
					icon: 2,
					closeBtn :1,
					area: [width,height],/**宽高*/
					shade: [0.5, '#393D49'],
					// shadeClose :true,
					id:'checkCar',
					maxmin :isMax,
					fixed :true,
					success: function(layero, index){},
					btn: ['关闭'],
					yes: function(index, layero){
						parent.layer.close(indexModel);
						return false;
					}
				});
			},
			
		/**
		 * iframe弹框 open
		 * fun:执行成功回调函数
		 * title：弹框标题
		 * openUrl:页面打开链接
		 * width:宽度   1100px
		 * height:高度  560px
		 * isMax:是否允许最大化功能
		 * submitUrl:页面提交的url
		 * 使用场景： 添加修改
		 * 注意事项：
		 * 		ajax返回的为map  success回调标记(true  false)  msg回调[成功]信息(操作成功)只有回调成功需要msg
		 * 		form的名字为：formDemo
		 * 		子页面提交的函数为：submitForm()
		 */
		openEditDialog:function(fun,title,openUrl,width,height,isMax,btn1,btn2,submitUrl){
			var indexModel=parent.layer.open({
				  type: 2, 
				  title:title,
				  content: openUrl,
				  skin:"layui-layer-molv",
				  icon: 2,
				  closeBtn :1,
				  area: [width,height],/**宽高*/
				  shade: [0.5, '#393D49'],
				 // shadeClose :true,
				  id:'checkCar',
				  maxmin :isMax,
				  fixed :true,
				  success: function(layero, index){},
				  btn: [btn1,btn2],
				  yes: function(index, layero){
					  var iframeWin = parent.window[layero.find('iframe')[0]['name']]; /**获得子的ifram对象*/
					  if(iframeWin.submitForm()){
						  parent.layer.confirm('您确定要提交吗?', {icon: 3, title:'再次确认'}, function(index){
							  parent.layer.close(index);
							  common.showLoadingDialogZero();
							  //这个就是获取子页面元素的方式
						      var checkfirstintroduce=iframeWin.document.getElementById("formDemo");
							  /**发送ajax*/
							  $.ajax({
								   type: "POST",url: submitUrl,data:$(checkfirstintroduce).serialize() ,dataType: "json",
								   success: function(msg){
									   if(msg.success){
										   parent.layer.close(indexModel);
										   common.getMsgBlackDialog(msg.message,2000);
										   fun();
								       }else{
								    	   if(msg.message){
								    		   common.getMsgDialog(msg.message,5,2000);
								    	   }else{
								    		   common.getMsgDialog("操作失败...",5,2000);
								    	   }
								       }
									   common.closeLoadingDialog();
								   }
							  });
					   });
					  }
					  return false;
				  },
				  btn2: function(index, layero){
					  parent.layer.close(indexModel);
				  }
			});
		},
		
		/**
		 * 展示加载层  不带自动消失的时间  1透明 不能用
		 */
		showLoadingDialogZero:function(){
			parent.layer.load(0,{
				shade: [0.2, '#393D49']
			});
		},
		/**
		 * 展示加载层  不带自动消失的时间
		 */
		showLoadingDialog:function(){
			parent.layer.load(0,{
				shade: [0.2, '#393D49']
			});
		},
		/**
		 * 展示加载层  指定自动消失的时间
		 */
		showLoadingTimeDialog:function(time){
			parent.layer.load(0,{
				shade: [0.2, '#393D49'],
				time:time
			});
		},
		
		/**
		 * 清除缓存   有success  成功的时候返回msg  
		 */
		
		
		/**
		 * 关闭所有的层
		 */
		closeAllDialog: function(){
			parent.layer.closeAll(); 
		},
		/**
		 * 关闭所有的加载层
		 */
		closeLoadingDialog:function(){
			parent.layer.closeAll("loading"); 
		},
		/**
		 * 确认框 confirm  
		 * title:标题
		 * content：提示的内容
		 * icon：//1对号，2错号，3问号 4.锁 5.哭脸
		 * submitUrl：确认之后执行的url
		 * data:传到url里面的参数  {}格式
		 * funModel:刷新的函数
		 * ajax回调的函数  success(回调的标记)  msg(回调的信息  失败也可以在后台指定回调的信息)
		 * 使用场景：一般就是传入3  删除确认常用
		 */
		getConfirmDelDialog:function(title,content,icon,submitUrl,data,funModel){
			parent.layer.confirm(content,{title:title,icon :icon},
				function(index){
				common.showLoadingDialog();
					parent.layer.close(index);
					$.ajax({
						   type: "POST",url: submitUrl,data:data,dataType: "json",
						   success: function(msg){
							   common.closeLoadingDialog();
							   if(msg.success){
								   common.getMsgBlackDialog(msg.message,2000);
								   if(typeof funModel == 'function'){
									   funModel();
								   }
						       }else{
						    	   if(msg.message){
						    		   common.getMsgDialog(msg.message,5,2000);
						    	   }else{
						    		   common.getMsgDialog("操作失败...",5,2000);
						    	   }
						       }
						   }
					  });
				}
			);
		},
		/**
		 * 提示框  msg   专用来提示的
		 * content：提示的内容
		 * icon:  1对号，2错号，3问号 4.锁 5.哭脸
		 * time:控制提示的时间   2000两秒  不配置就是3秒
		 */
		getMsgDialog:function(contentM,iconM,timeM){
			parent.layer.msg(contentM,{
				icon:iconM,
				time:timeM
			});
		},
		/**
		 * 提示框  msg   专用来提示的   背景黑色提示
		 */
		getMsgBlackDialog:function(contentM,timeM){
			parent.layer.msg(contentM,{
				time:timeM
			});
		
		},
		/**
		 * 提示框
		 * title:提示的标题
		 * content：提示的内容
		 */
		getInfoDialog:function(title,content){
			parent.layer.open({
				type:1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
				title:title,
				content:content,
				skin:"layui-layer-molv",
				//icon - 图标。信息框和加载层的私有参数
				shade: [0.5, '#393D49'],
				shadeClose :true
			});
		},
		
		/**
		 * 传入dom,返回时间的值
		 * "#id"   或者  common.getDataTimeFun(this);
		 */
		getDataTimeFun : function(dom){
			var dateModel="";
			laydate({
				elem: dom, //需显示日期的元素选择器   直接用this吧 当前要显示的日期的地方
				istime: true, //是否开启时间选择
				format: 'YYYY-MM-DD',//日期格式
				isclear: true, //是否显示清空
				istoday: true, //是否显示今天
				issure: true, //是否显示确认
				festival: true, //是否显示节日
				fixed: false, //是否固定在可视区域
				zIndex: 99999999, //css z-index
				min: '1900-01-01 00:00:00', //最小日期
				max: '2099-12-31 23:59:59', //最大日期
				start: '2014-6-15 23:00:00',  //开始日期
				choose: function(dates){ //选择好日期的回调
					dateModel=dates;
					//layer.msg($("#birthday").val());   //dates就是我们选中的日期
				}
			});
			return dateModel;
		},
	
	
		/**
		 * 抛出一个异常错误信息
		 */
		throwError: function(msg) {
			throw new Error(msg);
			return;
		},
		/**
		 * 弹出一个错误提示
		 */
		msgError: function(msg) {
			layer.msg(msg, {
				icon: 5
			});
			return ;
		}
		
		
	};

	exports('common', common);
});