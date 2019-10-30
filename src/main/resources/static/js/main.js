initMenu();
function initMenu(){
	
	 $.ajax({  
	     url:"/permissions/current",  
	     type:"get",  
	     async:false,
	     success:function(data){
	    	 console.info(data);
	    	 if(!$.isArray(data)){
	    		 location.href='/login.html';
	    		 return;
	    	 }
	    	 var menu = $("#menu");
	    	 var html="";
	    	 $.each(data, function(i,item){
	    			 html=html+'<li class="layui-nav-item"><a href="javascript:;" lay-id="'+item.id+'"><i style="padding-right: 2px;" aria-hidden="true" title="'+item.name+'" unreadNotice></i>'+item.name+'</a><dl class="layui-nav-child">';
	    			 //item  每一项
	    			 var arrayItem=item.child;
	    			 if(arrayItem && arrayItem.length>0){//有子项
	    				 for(var j=0;j<arrayItem.length;j++){
	    					 html = html +'<dd><a href="javascript:;"  lay-id="'+arrayItem[j].id+'"  data-url="'+arrayItem[j].href+'"><i style="padding-right: 2px;"  aria-hidden="true" title="'+arrayItem[j].name+'" unreadNotice></i>'+arrayItem[j].name+'</a></dd>';
	    				 }
	    			 }
	    			 html = html+'</dl></li>';
	          
	        });
	    	 html =html+'<li class="layui-nav-item" pc><a href="javascript:;" class="admin-header-user"><img /><span></span></a><dl class="layui-nav-child"><dd><a href="javascript:;" onclick="logout()"><i class="fa fa-sign-out" aria-hidden="true"></i>退出</a></dd></dl></li>';
	    	 $("#topDaoHangUl").append(html);
	     }
	 });
}


var username;
// 登陆用户头像昵称
showLoginInfo();
function showLoginInfo(){
	$.ajax({
		type : 'get',
		url : '/users/current',
		async : false,
		success : function(data) {
			$(".admin-header-user span").text(data.nickname);
			username=data.username;
			var pro = window.location.protocol;
			var host = window.location.host;
			var domain = pro + "//" + host;
			
			var sex = data.sex;
			var url = data.headImgUrl;
			if(url == null || url == ""){
				if(sex == 1){
					url = "/img/avatars/sunny.png";
				} else {
					url = "/img/avatars/1.png";
				}
				
				url = domain + url;
			} else {
				url = domain + "/statics" + url;
			}
			var img = $(".admin-header-user img");
			img.attr("src", url);
		}
	});
}


function logout(){
	$.ajax({
		type : 'get',
		url : '/logout',
		success : function(data) {
			localStorage.removeItem("token");
			location.href='/login.html';
		}
	});
}

var active;

layui.use(['layer', 'element'], function() {
	var $ = layui.jquery,
	layer = layui.layer;
	var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    element.on('nav(demo)', function(elem){
      //layer.msg(elem.text());
    });
    
    
	  //触发事件  
	   active = {  
	       tabAdd: function(obj){
	    	 var lay_id = $(this).attr("lay-id");
	    	 var title = $(this).html() + '<i class="layui-icon" data-id="' + lay_id + '"></i>';
	         //新增一个Tab项  
	         element.tabAdd('admin-tab', {  
	           title: title,
	           content: '<iframe   src="' + $(this).attr('data-url') + '"></iframe>',
	           id: lay_id
	         });  
	         element.tabChange("admin-tab", lay_id);    
	       }, 
	       tabDelete: function(lay_id){
    	      element.tabDelete("admin-tab", lay_id);
    	   },
	       tabChange: function(lay_id){
	         element.tabChange('admin-tab', lay_id);
	       }  
	   };  
	   //添加tab  
	   $(document).on('click','a',function(){  
	       if(!$(this)[0].hasAttribute('data-url') || $(this).attr('data-url')===''){
	    	   return;  
	       }
	       var tabs = $(".layui-tab-title").children();
	       var lay_id = $(this).attr("lay-id");

	       for(var i = 0; i < tabs.length; i++) {
	           if($(tabs).eq(i).attr("lay-id") == lay_id) { 
	        	   active.tabChange(lay_id);
	               return;  
	           }    
	       }  
	       active["tabAdd"].call(this);  
	       resize();  
	   });  
	     
	   //iframe自适应  
	   function resize(){  
	       var $content = $('.admin-nav-card .layui-tab-content');  
	       $content.height($(this).height() - 147);  
	       $content.find('iframe').each(function() {  
	           $(this).height($content.height());  
	       });  
	   }  
	   $(window).on('resize', function() {  
	       var $content = $('.admin-nav-card .layui-tab-content');  
	       $content.height($(this).height() - 147);  
	       $content.find('iframe').each(function() {  
	           $(this).height($content.height());  
	       });  
	   }).resize();  
	   
	   //toggle左侧菜单  
	   $('.admin-side-toggle').on('click', function() {
	       var sideWidth = $('#admin-side').width();  
	       if(sideWidth === 200) {    //将这三个css设置为0之后  就会出现没有左侧导航的情况
	           $('#admin-body').animate({  
	               left: '0'  
	           });
	           $('#admin-footer').animate({  
	               left: '0'  
	           });  
	           $('#admin-side').animate({  
	               width: '0'  
	           });  
	       } else {  
	           $('#admin-body').animate({  
	               left: '200px'  
	           });  
	           $('#admin-footer').animate({  
	               left: '200px'  
	           });  
	           $('#admin-side').animate({  
	               width: '200px'  
	               });  
	           }  
	       });
	   
	    //手机设备的简单适配
	    var treeMobile = $('.site-tree-mobile'),
	    shadeMobile = $('.site-mobile-shade');
	    treeMobile.on('click', function () {
	        $('body').addClass('site-mobile');
	    });
	    shadeMobile.on('click', function () {
	        $('body').removeClass('site-mobile');
	    });
	    
	    
	    //开始登陆处理项目
	    $.ajax({
			type : 'post',
			url : '/project/dealIndexProject',
			async : false,
			dataType: "json",
			success : function(data) {
				if(data.success){
					$("#nowProject").attr("lang",data.object.nowProject.id);
					$("#nowProject").text(data.object.nowProject.projName);
					var listProjects=data.object.listProject;
					if(listProjects != null && listProjects.length >0){
						$("#childDl").empty();
						for(var i=0;i<listProjects.length;i++){
							var project=listProjects[i];
							$("#childDl").append("<dd><a href='javascript:;' class='clsProj'  id='"+project.id+"'>"+project.projName+"</a></dd>");
						}
					}
				}else{
					layer.msg("系统异常！");
				}
			}
		});
	  //监听下拉菜单
	  $(".clsProj").click(function(){
		  var proj=$(this).attr("id");
		  $.ajax({
				type : 'post',
				url : '/project/changeProject',
				async : false,
				dataType: "json",
				data:{
					"proj":proj,
					"username":username
				},
				success : function(data) {
					if(data.success){
						 location.href = "/index.html";
					}else{
						layer.msg("系统异常！");
					}
				}
			});
		 
	  });
});