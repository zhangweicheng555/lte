function getMenuTree() {
	var root = {
		id : 0,
		name : "root",
		open : true,
	};

	$.ajax({
		type : 'get',
		url : '/permissions/all',
		contentType : "application/json; charset=utf-8",
		async : false,
		success : function(data) {
			var length = data.length;
			var children = [];
			for (var i = 0; i < length; i++) {
				var d = data[i];
				var node = createNode(d);
				children[i] = node;
			}

			root.children = children;
		}
	});

	return root;
}

function initMenuDatas(roleId){
	$.ajax({
		type : 'get',
		url : '/permissions?roleId=' + roleId,
		success : function(data) {
			var length = data.length;
			var ids = [];
			for(var i=0; i<length; i++){
				ids.push(data[i]['id']);
			}
			
			initMenuCheck(ids);
		}
	});
}

function initMenuCheck(ids) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var length = ids.length;
	if(length > 0){
		var node = treeObj.getNodeByParam("id", 0, null);
		treeObj.checkNode(node, true, false);
	}
	
	for(var i=0; i<length; i++){
		var node = treeObj.getNodeByParam("id", ids[i], null);
		treeObj.checkNode(node, true, false);
	}
	
}

function getCheckedMenuIds(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	
	var length = nodes.length;
	var ids = [];
	for(var i=0; i<length; i++){
		var n = nodes[i];
		var id = n['id'];
		ids.push(id);
	}
	
	return ids;
}

function createNode(d) {
	var id = d['id'];
	var pId = d['parentId'];
	var name = d['name'];
	var child = d['child'];

	var node = {
		open : true,
		id : id,
		name : name,
		pId : pId,
	};

	if (child != null) {
		var length = child.length;
		if (length > 0) {
			var children = [];
			for (var i = 0; i < length; i++) {
				children[i] = createNode(child[i]);
			}

			node.children = children;
		}

	}
	return node;
}



function initDicSelect(type,selectId){
	$.ajax({
        type : 'post',
        url : '/dic/findByType',
        data:{"type":type},
        async : false,
        dataType: "json",
        success : function(data) {
        	var select = $("#"+selectId);
            select.append("<option value='0'>''</option>");
            if(data != null && data.length >0){
            	select.empty();
            	for(var i=0;i<data.length;i++){
            		var row=data[i];
            		select.append("<option value='"+row.k+"'>"+row.val+"</option>");
            	}
            }
        }
    });
}

function initProjSelect(selectId,projId){
	var data={};
	if(projId){
		data={"flag":projId};
	}
	$.ajax({
		type : 'post',
		url : '/role/projSelect',
		data:data,
		async : false,
		dataType: "json",
		success : function(data) {
			var select = $("#"+selectId);
			select.append("<option value=''>''</option>");
			if(data != null && data.length >0){
				select.empty();
				for(var i=0;i<data.length;i++){
					var row=data[i];
					if(projId && projId==row.id){
						select.append("<option selected value='"+row.id+"'>"+row.projName+"</option>");
					}else{
						select.append("<option value='"+row.id+"'>"+row.projName+"</option>");
					}
				}
			}
		}
	});
}

function initParentMenuSelect(){
	$.ajax({
        type : 'get',
        url : '/permissions/queryChlidTree',
        async : false,
        dataType: "json",
        success : function(data) {
            var select = $("#parentId");
            select.append("<option value='0'>root</option>");
            if(data != null && data.length >0){
            	for(var i=0; i<data.length; i++){
            		var d = data[i];
            		var id = d.id;
            		var name = d.name;
            		select.append("<option value='"+ id +"'>" +name+"</option>");
            	}
            }
        }
    });
}


//初始化ztree
function initZtree(treeId,flag){
	var data={};
	if(flag){
		 data = {"flag":flag};
	}
	
	var setting = {
			check: {
				enable: true,
				nocheckInherit: true,
				chkboxType: { "Y": "ps", "N": "s" }
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: 0
				}
			}
		};

	$.ajax({
        type : 'post',
        url : '/role/find',
        async : false,
        data:data,
        dataType: "json",
        success : function(data) {
	       return $.fn.zTree.init($("#"+treeId), setting, data);
        }
    });
}

//初始化某个项目的  角色  人员树
function initPersonZtree(treeId,flag){
	var data={};
	if(flag){
		data = {"flag":flag};
	}
	
	var setting = {
			check: {
				enable: true,
				nocheckInherit: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: 0
				}
			},
			 callback: {//设置父节点不让选择
		            beforeCheck: zTreeBeforeCheck
		     }
	};
	
	$.ajax({
		type : 'post',
		url : '/role/findRolePersion',
		async : false,
		data:data,
		dataType: "json",
		success : function(data) {
			return $.fn.zTree.init($("#"+treeId), setting, data);
		}
	});
}


function zTreeBeforeCheck(treeId, treeNode) {
    return !treeNode.isParent;
}