<%--
  Created by IntelliJ IDEA.
  User: xiaowen
  Date: 2020/7/15
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="UTF-8">

<%--<jsp:include page="include-head.jsp"></jsp:include>--%>
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">

        <%@include file="/WEB-INF/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="glyphicon glyphicon-th-list"></i>
                        权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal">
                        <i class="glyphicon glyphicon-question-sign"></i>
                    </div>
                </div>

                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp" %>
<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>
</body>

<script type="text/javascript">

    $(function () {

        //调用初始化树形结构函数
        generateTree();

        //给添加节点按钮绑定单击响应函数-打开模态框
        $("#treeDemo").on("click", ".addBtn", function () {
            //打开模态框
            $("#menuAddModal").modal("show");
            //将当前节点的ID，作为新节点的pid保存到全局变量
            window.pid = this.id;
            return false;
        });

        //添加保存
        $("#menuSaveBtn").click(function () {

            //收集表单项中用户输入的数据
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $.trim($("#menuAddModal [name=icon]:checked").val());
            
            $.ajax({
                url: "menu/save.json",
                type: "post",
                data: {
                    pid : window.pid,
                    name: name,
                    url: url,
                    icon: icon
                },
                dataType: "json",
                success: function (res) {
                    var result = res.result;
                    if (result == "SUCCESS") {
                        layer.msg(res.message);
                        setTimeout(function(){
                            //重新加载树形结构  异步的放在成功后 睡眠2S
                            generateTree();
                            }, 1000);
                    }
                    if (result == "FAILED") {
                        layer.msg("添加失败");
                    }
                },
                error: function (res) {
                    layer.msg(res.status + "  " + res.statusText);
                }
            });
            $("#menuAddModal").modal("hide");
            // 不传任何参数 就相当于用户点击了一下
            $("#menuResetBtn").click();
        });

        //给更新节点按钮绑定单击响应函数-打开模态框
        $("#treeDemo").on("click", ".editBtn", function () {
            //打开模态框
            $("#menuEditModal").modal("show");
            //将当前节点的ID，作为新节点的pid保存到全局变量
            window.id = this.id;

            // 获取zTreeObj
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            //数据回显
            //利用zTree中的ID属性及属性值 获取数据
            var key = "id";
            var value = window.id;

            //利用zTreeAPI获取用户当前点击的节点值然后回显 [注意这里的 currentNode 值来自数据库]
            var currentNode = zTreeObj.getNodeByParam(key, value);

            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);

            // 找到icon这个name 根据currentNode的值就能进行回显 单选框及多选框一样
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });

        //更新保存
        $("#menuEditBtn").click(function () {

            //收集表单项中用户输入的数据
            var name = $.trim($("#menuEditModal [name=name]").val());
            var url = $.trim($("#menuEditModal [name=url]").val());
            var icon = $.trim($("#menuEditModal [name=icon]:checked").val());

            $.ajax({
                url: "menu/update.json",
                type: "post",
                data: {
                    id : window.id,
                    name: name,
                    url: url,
                    icon: icon
                },
                dataType: "json",
                success: function (res) {
                    var result = res.result;
                    if (result == "SUCCESS") {

                        layer.msg(res.message);
                        setTimeout(function(){
                            //重新加载树形结构  异步的放在成功后 睡眠2S
                            generateTree();
                        }, 1000);
                    }
                    if (result == "FAILED") {
                        layer.msg(res.message);
                    }
                },
                error: function (res) {
                    layer.msg(res.status + "  " + res.statusText);
                }
            });
            $("#menuEditModal").modal("hide");
        });

        //给删除绑定单机响应函数-打开模态框
        $("#treeDemo").on("click", ".removeBtn", function () {
            //打开模态框
            $("#menuConfirmModal").modal("show");
            //将当前节点的ID，作为新节点的pid保存到全局变量
            window.id = this.id;

            // 获取zTreeObj
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            //数据回显
            //利用zTree中的ID属性及属性值 获取数据
            var key = "id";
            var value = window.id;

            //利用zTreeAPI获取用户当前点击的节点值然后回显 [注意这里的 currentNode 值来自数据库]
            var currentNode = zTreeObj.getNodeByParam(key, value);

            $("#removeNodeSpan").html("【<i class='" + currentNode.icon + "'></i>" + currentNode.name + "】");

            return false;
        });


        //确定删除按钮的响应函数
        $("#confirmBtn").click(function () {
            $.ajax({
                "url": "menu/remove.json",
                "type": "post",
                "data": {
                    "id": window.id
                },
                "success": function (response) {
                    var result = response.result;
                    if(result == "SUCCESS"){
                        layer.msg(response.message);
                        setTimeout(function(){
                            //重新加载树形结构  异步的放在成功后 睡眠2S
                            generateTree();
                        }, 1000);
                    }else{
                        layer.msg("对不起！您无法对这个菜单进行更改！");
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            })
            $("#menuConfirmModal").modal("hide");
        })
    });
</script>
</html>