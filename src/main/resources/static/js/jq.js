//ajax通用配置     跑异常的时候会走这里面
$.ajaxSetup({
	cache : false,
	headers : {
		"token" : localStorage.getItem("token")
	},
	error : function(xhr, textStatus, errorThrown) {
		var msg = xhr.responseText;
		var response = JSON.parse(msg);
		var code = response.code;
		var message = response.message;
		if (code == 400) {
			parent.layer.msg(message);
		} else if (code == 401) {
			localStorage.removeItem("token");
			location.href = '/login.html';
		} else if (code == 403) {
			console.log("未授权:" + message);
			parent.layer.msg('未授权');
		} else if (code == 500) {
			parent.layer.msg('系统错误：' + message);
		}else{
			parent.layer.msg(message);
		}
		parent.layer.closeAll("loading"); ;
	}
});

// 删除按钮权限
function buttonDel(data, permission, pers) {
	if (permission != "") {
		if ($.inArray(permission, pers) < 0) {
			return "";
		}
	}

	var btn = $("<button class='layui-btn layui-btn-normal layui-btn-xs' title='删除' onclick='del(\""
			+ data + "\")'><i class='layui-icon'>&#xe640;</i></button>");
	return btn.prop("outerHTML");
}

// 编辑按钮权限
function buttonEdit(href, permission, pers) {
	if (permission != "") {
		if ($.inArray(permission, pers) < 0) {
			return "";
		}
	}

	var btn = $("<button class='layui-btn layui-btn-normal layui-btn-xs' title='编辑' onclick='update(\""
			+ href + "\")'><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}

// 编辑按钮权限  无Url
function buttonEditOpen(flag, permission, pers) {
	if (permission != "") {
		if ($.inArray(permission, pers) < 0) {
			return "";
		}
	}
	var array=flag.split(",");
	var btn = $("<button class='layui-btn layui-btn-normal layui-btn-xs myB' lang='"+array[0]+"'  title='编辑' '><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}


// 根据参数的名字获取url后面传递的值
function getUrlParam(key) {
	var href = window.location.href;
	var url = href.split("?");
	if (url.length <= 1) {
		return "";
	}
	var params=url[1].split("&");
	for(var i=0;i<params.length;i++){
		var param=params[i].split("=");
		if(key == param[0]){
			return param[1];
		}
	}
}
// 删除当前tab
function deleteCurrentTab() {
	var lay_id = $(parent.document).find("ul.layui-tab-title").children(
			"li.layui-this").attr("lay-id");
	parent.active.tabDelete(lay_id);
}
//核对是否有权限
function checkPermission() {
	var pers = [];
	$.ajax({
		type : 'get',
		url : '/permissions/owns',
		contentType : "application/json; charset=utf-8",
		async : false,
		success : function(data) {
			pers = data;
			console.info(pers);
			$("[permission]").each(function() {
				var per = $(this).attr("permission");
				if ($.inArray(per, data) < 0) {
					$(this).hide();
				}
			});
		}
	});
	
	return pers;
}

/**
 * 查询该项目下的用户的列表信息    selectName  是当前用户的id  用于下拉框初始化选中当前id
 * @param item
 * @param selectName
 * @returns
 */
function queryUserByProjId(item,selectName) {
	$.ajax({
		url : '/plan/queryUserByProjId',
		data: {},
		type:"post",
		dataType:"json",
		async : false,
		success : function(data) {
			if(data.success){
				$("#"+item).empty();
				var rows=data.object;
				var html="";
				for(var i=0;i<rows.length;i++){
					var obj=rows[i];
					if(rows[i].id == selectName){
						html =html+'<option selected value="'+obj.id+'">'+obj.username+'</option>';
					}else{
						html =html+'<option value="'+obj.id+'">'+obj.username+'</option>';
					}
				}
				$("#"+item).append(html);
			}else{
				parent.layer.msg("查询出错误了...");
			}
		}
	});
}

/**
 * 查询指定角色下的用户的列表信息    prijId  是当前用户的id  
 * @param item   select 的html  id值
 * @param selectName
 * @returns
 */
function queryUerByRoleIdForSelect(item,roleId,url) {
	$.ajax({
		url : url,
		data: {"roleId":roleId},
		type:"post",
		dataType:"json",
		async : false,
		success : function(data) {
			if(data.success){
				$("#"+item).empty();
				var rows=data.object;
				console.info(rows);
				var html="";
				for(var i=0;i<rows.length;i++){
					var obj=rows[i];
					html =html+'<option value="'+obj.id+'">'+obj.username+'</option>';
				}
				console.info(html);
				$("#"+item).append(html);
			}else{
				parent.layer.msg("查询出错误了...");
			}
		}
	});
}


/**
 * 下拉框处理方式
 * @param item  下拉框的id
 * @param selectName  初始化选中
 * @param selectArray  下拉框所有选项的数组
 * @returns
 */
function initSelect(item,selectName,selectArray) {
	$("#"+item).empty();
	var html='<option selected value=""></option>';
	if(selectArray && selectArray.length>0){
		for(var i=0;i<selectArray.length;i++){
			var obj=selectArray[i];
			if(selectName){
				if(obj == selectName){
					html =html+'<option selected value="'+obj+'">'+obj+'</option>';
				}else{
					html =html+'<option value="'+obj+'">'+obj+'</option>';
				}
			}else{
				html =html+'<option value="'+obj+'">'+obj+'</option>';
			}
		}
	}
	console.info(html);
	$("#"+item).append(html);
}


/**
 * 查询该项目下的角色列表信息    selectIds  是当前用户的角色ids  用于checkbox初始化选中
 * 返回的信息object是角色列表信息list<ROle>
 */
function queryRoleByProjId(item,selectIds,url) {
	$.ajax({
		url : url,
		data: {},
		type:"post",
		dataType:"json",
		async : false,
		success : function(data) {
			if(data.success){
				$("#"+item).empty();
				var rows=data.object;
				var html="";
				var idsArray=[];
				if(selectIds){
					idsArray=selectIds.split(",");
				}
				console.info(idsArray);
				for(var i=0;i<rows.length;i++){
					var obj=rows[i];
					if(idsArray.indexOf(obj.id+"")>-1){//包含
						html =html+'<input type="checkbox" value="'+obj.id+'" name="choose" title="'+obj.name+'" lay-skin="primary" checked>';
					}else{
						html =html+'<input type="checkbox" value="'+obj.id+'" name="choose" title="'+obj.name+'" lay-skin="primary" >';
					}
				}
				
				$("#"+item).append(html);
			}else{
				parent.layer.msg("查询出错误了...");
			}
		}
	});
}
