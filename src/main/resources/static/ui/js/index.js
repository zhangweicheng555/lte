/**
 * @author sujiming
 * @date 2019-07-25 09:57
 */

layui.use(['jquery','table','layer','form','laydate'],function () {
    $ = layui.$;
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    $('#booNavigation').booNavigation({
        slideSpeed: 200
    });
    //设置iframe窗口大小
    $("#iframe").height($(document).height()-$("div.sites-header-nav").height()-5);


})
 $("#deleteAlertWindow").click(function () {
        parent.layer.confirm('真的删除行么', function(index){
            obj.del();
            layer.close(index);
        });
 });

