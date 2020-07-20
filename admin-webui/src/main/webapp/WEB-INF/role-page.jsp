<%--
  Created by IntelliJ IDEA.
  User: xiaowen
  Date: 2020/7/19
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="UTF-8">

<%--<jsp:include page="include-head.jsp"></jsp:include>--%>
<%@include file="/WEB-INF/include-head.jsp" %>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">

        <%@include file="/WEB-INF/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;" disabled="disabled"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <%-- 所有信息都显示在这 --%>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <div id="Pagination" class="pagination"></div>
                                    </ul>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>
<%@include file="/WEB-INF/modal-role-edit.jsp" %>
<%@include file="/WEB-INF/modal-role-confirm.jsp" %>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp" %>
</body>
<script type="text/javascript" src="crowd/my-role.js"></script>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">

    $(function () {

        // 初始化数据,全局变量
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 进来默认刷新页面
        generatePage();

        //查询
        $("#searchBtn").click(function () {
            // 获取所有关键字赋值变量
            window.keyword = $.trim($("#keywordInput").val());
            // 按照用户再输入框中输入的值取SQL、ES查询
            generatePage();
        });

        // 新增按钮的id 点击打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        // 执行新增保存
        $("#saveRoleBtn").click(function () {
            // 找id为 addModal的对象中后代元素name为roleName的值
            var roleName = $.trim($("#addModal [name=roleName]").val());
            $.ajax({
                "url": "role/save.json",
                "type": "post",
                "data": {
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if(result == "SUCCESS"){
                        layer.msg(response.message)
                        // 重新加载分页
                        window.pageNum = 666666;
                        generatePage();
                    }
                    if(result == "FAILED"){
                        layer.msg("操作失败！请检查添加的角色是否已经存在")
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            // 关闭并清理模态框
            $("#addModal").modal("hide");
            $("#addModal [name=roleName]").val("");
        });

        // 先找到所有动态生成的元素所附着的静态元素
        // 第一个参数是事件类型 第二个参数是真正要绑定的事件类型
        $("#rolePageBody").on("click",".pencilBtn",function () {
            $("#editModal").modal("show");
            // 获取当前按钮所在的td的上一个td的值
            var roleName = $(this).parent().prev().text();
            // 这个id 在fillTableBody()中赋值了 将roleId的值放在全局变量来完成更新
            window.roleId = this.id;
            // 回显模态框中的文本值
            $("#editModal [name=roleName]").val(roleName);
        });

        // 发送更新请求
        $("#updateRoleBtn").click(function () {
            // 这里值获取错了会导致内存泄漏
            var roleName = $("#editModal [name=roleName]").val();
            $.ajax({
                url: "role/update.json",
                type: "post",
                data: {
                    "id": window.roleId,
                    "name": roleName
                },
                dataType: "json",
                success: function (response) {
                    var result = response.result;
                    if(result == "SUCCESS"){
                        layer.msg(response.message)
                        // 重新加载分页
                        generatePage();
                    }
                    if(result == "FAILED"){
                        layer.msg("更新失败！")
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            // 更新请求发完关闭模态框
            $("#editModal").modal("hide");
        });

        // 当用户点击删除按钮时
        $("#rolePageBody").on("click",".removeBtn",function () {
            // 获取当前按钮所在的td的上一个td的值
            var roleName = $(this).parent().prev().text();
            // 收集各个属性
            var roleArray = [{
                roleId: this.id,
                roleName: roleName,
            }]
            // 收集完各个属性之后弹出模态框
            showConfirmModal(roleArray);
        });

        // 当用户点击了确认模态框中删除的
        $("#removeRoleBtn").click(function () {
            // 将数组转成JSON
            var requestBody = JSON.stringify(window.roleIdArry);
            $.ajax({
                "url": "role/remove/role/by/id/array.json",
                "type": "post",
                "data": requestBody,
                "contentType" : "application/json;charset=UTF-8",
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if(result == "SUCCESS"){
                        layer.msg(response.message)
                        // 重新加载分页
                        generatePage();

                        // 删除后去掉选中
                        $("#summaryBox").removeAttr("checked");
                    }
                    if(result == "FAILED"){
                        layer.msg("删除失败！" + response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });

            $("#confirmModal").modal("hide");
        });

        //全选，全取消
        $("#summaryBox").click(function () {

            var currentStatus = this.checked;
            if(currentStatus == true){
                $("#batchRemoveBtn").prop("disabled","");
            }else{
                $("#batchRemoveBtn").prop("disabled","disabled");
            }
            $(".itemBox").prop("checked",currentStatus);
        })


        // 全选、全不选的反向操作
        $("#rolePageBody").on("click",".itemBox",function(){
            // 获取已经选中的 .itemBox 的数量
            var checkedBoxCount = $(".itemBox:checked").length;
            if(checkedBoxCount > 0){
                $("#batchRemoveBtn").prop("disabled","");
            } else {
                $("#batchRemoveBtn").prop("disabled","disabled");
            }
            // 获取全部.itemBox的数量
            var totalBoxCount = $(".itemBox").length;
            $("#summaryBox").prop("checked", checkedBoxCount >= totalBoxCount && checkedBoxCount > 0);
        });

        // 批量删除
        $("#batchRemoveBtn").click(function () {
            // 存放选中的角色属性 用于批量删除
            var roles = [];
            // 遍历已经选中的按钮
            $(".itemBox:checked").each(function () {
                // 循环获取之前设置的id
                var roleId = this.id;
                // 通过DOM获取角色名称
                var roleName = $(this).parent().next().text();
                roles.push({
                    "roleId": roleId,
                    "roleName": roleName
                });
            });
            if(roles.length < 1){
                layer.msg("技术不错呀,不过你没选中是不能删除的！");
                return;
            }
            // 用户选中 1 一个以上的用户并点击'删除'按钮
            showConfirmModal(roles);

        })

    });
</script>
</html>