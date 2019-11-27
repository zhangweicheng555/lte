/**
 * @author nokia-ihandle
 * @date 2019-08-02 09:18
 */


/**
 * 默认收缩左侧导航
 */
var username;
var projIdNowLtem;/**当前选中的项目ID*/
$(function(){
	 
	 //开始登陆处理项目
	    $.ajax({
			type : 'post',
			url : '/project/dealIndexProject',
			async : false,
			dataType: "json",
			success : function(data) {
				if(data.success){
					projIdNowLtem=data.object.nowProject.id;
					if(projIdNowLtem==505 || projIdNowLtem==478 || projIdNowLtem==503){//针对上海联通
						$("#containerCommonHtml").append('<iframe id="iframe" style="width:100%;"  src="ui/page/indexBody.html"></iframe>');
					}else{
						$("#containerCommonHtml").append('<iframe id="iframe" style="width:100%;"  src="pages/dashboard.html"></iframe>');
					}
					
					$("#nowProject").attr("lang",data.object.nowProject.id);
					$("#nowProject").text(data.object.nowProject.projName);
					var listProjects=data.object.listProject;
					if(listProjects != null && listProjects.length >0){
						$("#childDl").empty();
						for(var i=0;i<listProjects.length;i++){
							var project=listProjects[i];
							$("#childDl").append('<li class="sites-header-link-nav-item clsProj" id="'+project.id+'"><a style="text-align: left;margin-left: 25px;">'+project.projName+'</a></li>');
						}
						
					}
				}else{
					layer.msg("系统异常！");
				}
			}
		});
	    
	    //只有上海联通的时候  会默认折叠左侧
	   /* if(projIdNowLtem==505){
	    	if($("div.asideContainer").css("display") ==='none'){
	    		$("div.asideContainer").show();
	    		$("body div.mainContentArea").css("left","200px");
	    	}else{
	    		$("div.asideContainer").hide();
	    		$("body div.mainContentArea").css("left","0");
	    	}
	    }*/
	 
	 //初始化菜单
	 initMenu();
	 
	 //显示当前在线人数
	 $.ajax({  
		 url:"/redis/queryLoginPersonNum",  
		 type:"POST",  
		 async:false,
		 success:function(data){
			 if(data.success){
				 $("#nowLoginPerson").html("系统在线人数: "+data.object);
				 $('#nowLoginPersonDiv').show();
			 }
		 }
	 });
	 
	 
	  
	    
	    //获取登陆的用户名字
	    $.ajax({
			type : 'get',
			url : '/users/current',
			async : false,
			success : function(data) {
				$("#loginUserName").text(data.nickname);
				username=data.username;
			}
		});
	    
	    
	    //监听项目切换菜单
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



/*左侧菜浏览器 单自适应*/
$(window).resize(function () {
    $("div.sites-header-nav").height();
    $("#iframe").height($(document).height()-$("div.sites-header-nav").height()-5);
});

/*左侧菜单  跳转*/
function toNewHtml(ele) {
    /*防止跳转时没有找到应有的url*/
    if($(ele).hasClass("disable")){
        return;
    }
    if($(ele).find("a").attr("name")){
        $(".one-level").each(function (i,n) {
            $(n).removeClass("active")
        });
        $(".two-level").each(function (i,n) {
            $(n).removeClass("active")
        });
        $(".three-level").each(function (i,n) {
            $(n).removeClass("active")
        });
        $(ele).addClass("active");
        $("#iframe").attr("src",$(ele).find("a").attr("name"));
    }
    if($(ele).hasClass("enable")){
        $("#iframe").attr("src",$(ele).find("a").attr("name"));
    }
}


/*左侧菜单项展开收缩*/
function menuClick(ele) {
    $(ele).find("ul").show();
    $(ele).parent().next().find("ul").show();
    $(ele).parent().next().css("display") ==='none' ? $(ele).parent().next().show() : $(ele).parent().next().hide();
}

/*导航条控制左侧菜单动作  展开与收缩*/
function menuNavClick() {
    if($("div.asideContainer").css("display") ==='none'){
        $("div.asideContainer").show();
        $("body div.mainContentArea").css("left","200px");
    }else{
        $("div.asideContainer").hide();
        $("body div.mainContentArea").css("left","0");
    }
}





/**
 * 作用待定
 */
/*子iframe页面跳转链接触发父页面左菜单联动*/
function parentToNewHtmlResponse(ele) {
    $("#iframe").attr("src",ele);
    $("div.two-level").each(function () {
        $(this).hide();
    })
    var links =$("#asideContainer a");
    links.each(function () {
        if($(this).attr("name") == $("#iframe").attr("src")){
            $(".one-level").each(function (i,n) {
                $(n).removeClass("active")
            });
            $(".two-level").each(function (i,n) {
                $(n).removeClass("active")
            });
            $(".three-level").each(function (i,n) {
                $(n).removeClass("active")
            });
            $(this).parent().addClass("active");
            $(this).parent().parent().show().parent().show();
            if($(this).parent().hasClass("three-level")){
                $(this).parent().parent().show().parent().show().parent().show().parent().show();
            }
        }
    })
    
}   
    
    /**
     * 左侧菜单初始化
     */
   
    function initMenu(){
    	 $.ajax({  
    	     url:"/permissions/current",  
    	     type:"get",  
    	     async:false,
    	     success:function(data){
    	    	 if(!$.isArray(data)){
    	    		 location.href='/login.html';
    	    		 return;
    	    	 }
    	    	 
    	    	 if(data==null || data.length==0){
    	    		 location.href='/login.html';
    	    		 return;
    	    	 }
    	    	 
    	    	 var menu = $("#appendLeftItems");
    	    	 //初始化首页的内容     
    	    	 var indexShowHtml='';
    	    	 if(projIdNowLtem==505 || projIdNowLtem==478 || projIdNowLtem==503){//只有上海联通会显示这个页面
    	    		 indexShowHtml +=singleLeftItem('首页','ui/page/indexBody.html',false,true);
    	    	 }
    	    	
    	    	 $.each(data, function(i,item){
    	    		 //添加父菜单
    	    		 var childs=item.child;
    	    		 if(childs !=null && childs.length > 0){//这个是多级目录的情况下的处理操作
    	    			 indexShowHtml += singleLeftItem(item.name,item.href,true,false,item.css);
    	    			 indexShowHtml += setChild(item.child)
    	    		 }else{//单级目录的处理操作
    	    			 indexShowHtml +=singleLeftItem(item.name,item.href,null,false,item.css);
    	    		 }
    	        });
    	    	 menu.append(indexShowHtml);
    	     }
    	 });
    }
    
    /**
     * 单个的导航  有名字，带不带URL都是可以的
     * @param name   导航的名字
     * @param url    导航的url
     * @param flag   是否待展开的箭头
     * @returns
     */
    function singleLeftItem(name,url,flag,active,itemType){
    	var html="";
    	if(active){
//    		html +='<li class="one-level active"><div onclick="toNewHtml(this)"><div class="menuImg"></div><a name="'+url+'">'+name+'</a>';
    		html +='<li class="one-level active"><div onclick="toNewHtml(this)"><a name="'+url+'"><i class="fa fa-bar-chart" style="margin-right: 5px;font-size: 12px;"></i>'+name+'</a>';
    	}else{
    		html +='<li class="one-level"><div onclick="menuClick(this)"><a name="'+url+'"><i class="fa '+itemType+'" style="margin-right: 5px;font-size: 12px;"></i>'+name+'</a>';
    	}
    	if(flag){
    		html +='<img src="ui/img/slideRectangle.png" alt="" class="fr">';
    	}
    	html +='</div></li>';
    	return html;
    }
    
    /**
     * 处理二级目录    二级里面处理三级
     * @param indexShowHtml
     * @param item
     * @param flag   是否需要增加展开箭头  就是是否有子节点
     * @returns
     */
    function appendLevelItem(item){
    	var childs=item.child;
    	if(item !=null && childs !=null && childs.length==0){//只有二级
    		return appendSecondLevelItem(item);
    	}
    	if(item !=null && childs !=null && childs.length > 0){//存在二级 三级的目录
    		return appendThressLevelItem(item);
    	}
    }

    /**
     * 添加二级标签
     * @returns
     */
    function appendSecondLevelItem(item){
    	var html='<li class="two-level"  onclick="toNewHtml(this)"><a name="'+item.href+'"><i class="fa '+item.css+'" style="margin-right: 5px;font-size: 12px;"></i>'+item.name+'</a></li>';
    	return html;
    }
    /**
     * 添加三级标签
     * @returns
     */
    function appendThressLevelItem(item){
    	var html='<li class="two-level"><div onclick="menuClick(this)"><a name="'+item.href+'"><i class="fa '+item.css+'" style="margin-right: 5px;font-size: 12px;"></i>'+item.name+'</a><img src="ui/img/slideRectangle.png" alt="" class="fr"></div></li>';
    	html +='<div class="two-level"><ul>';
    	
    	var childs=item.child;
    	for(var i=0;i<childs.length;i++){
    		var threeTerm=childs[i];
    		html +=' <li class="three-level"  onclick="toNewHtml(this)"><a name="'+threeTerm.href+'"><i class="fa '+item.css+'" style="margin-right: 5px;font-size: 12px;"></i>'+threeTerm.name+'</a></li>';
    	}
    	html +='</ul></div>';
    	return html;
    }
    
    
    /**
     * 递归迭代处理父子html
     * @param indexShowHtml   要拼接的html
     * @param child
     * @returns
     */
    function setChild(child){
    	var htmlFirst='';
    	var htmlFinal='';
        if(child != null && child.length > 0){
        	htmlFirst +='<div class="two-level"><ul>';
        	var htmlLi="";
            $.each(child, function(j,item2){
                // 递归
            	htmlLi +=appendLevelItem(item2);
            });
            htmlFinal +='</ul></div>';
            return htmlFirst+htmlLi+htmlFinal;
        }
    }
    
    
    /**
     * 退出
     */
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

