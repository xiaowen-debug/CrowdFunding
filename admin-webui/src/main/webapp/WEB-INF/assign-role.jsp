<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="text/javascript">

    $(function () {
        $("#toRightBtn").click(function () {
            // 找第一个select选择器; '>':表示找它的子元素 意思就是把第一个select中所有选中的元素追加到第二个select中
            //                                  追加到末尾
            $("select:eq(0) > option:selected").appendTo("select:eq(1)");
        });
        $("#toLeftBtn").click(function () {
            // 同理                             追加到最前面
            $("select:eq(1) > option:selected").prependTo("select:eq(0)");
        });
        // 提交的时候先全部选中
        $("#submitBtn").click(function () {
            $("select:eq(1) > option").prop("selected", "selected");
        });
    })
</script>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/role/assign.html" method="post" role="form" class="form-inline">
                        <%--  这三个参数的值一直跟随 admin-page.jsp 页面的请求--%>
                        <input type="hidden" name="adminId" value="${requestScope.adminId }">
                        <input type="hidden" name="pageNum" value="${requestScope.pageNum }">
                        <input type="hidden" name="keyword" value="${requestScope.keyword }">

                        <div class="form-group">
                            <label>未分配角色列表</label><br>
                            <select class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label>已分配角色列表</label><br>
                            <select class="form-control" name="roleIdList" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role">
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="submitBtn" style="width: 330px;" type="submit" class="btn btn-lg btn-success btn-block">修改</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
