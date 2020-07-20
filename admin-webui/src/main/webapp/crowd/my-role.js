/**
 * 执行分页，生成页面效果，任何时候调用这个函数都会重新加载这个页面
 */
function generatePage() {

    //1、获取分页shu数据
    var pageInfo = getPageInfoRemote();

    console.log(pageInfo);
    //2、填充表格
    fillTableBody(pageInfo);
}

/**
 * 远程访问服务器，获取pageInfo数据
 */
function getPageInfoRemote() {

    var ajaxResult = $.ajax({
        url: "role/get/page/info.json",
        type: "post",
        data: {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        dataType: "json",
        async: false,
    });
    // 判断当前请求响应码是否为200
    var status = ajaxResult.status;
    if (status != 200) {
        layer.msg("Server-side program call failed! Response code: " + status)
        return null;
    }
    // 如果响应状态码是 200, 说明请求处理成功 获取pageInfo
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    var pageInfo = resultEntity.data;
    return pageInfo;
}

/**
 * 填充表格
 * @param pageInfo
 */
function fillTableBody(pageInfo) {

    $("#rolePageBody").empty();
    // 这里清空是为了让没有搜索结果时不显示页面
    $("#Pagination").empty();

    if (pageInfo == null || pageInfo == undefined || pageInfo.list.length == 0) {
        // 向role-page.jsp 中的 rolePageBody ID中追加数据
        $("#rolePageBody").append("<tr><td colspan='4'>Sorry! you do not have permission or the data you are looking for</td></tr>")
        return;
    }

    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;

        var numberTd = "<td>" + (i + 1) + "</td>";
        // 这里的id 后面遍历循环删除时用到
        var checkboxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        // Role页面的第一个按钮 [权限分配]
        var checkBtn = "<button id= '" + roleId + "' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        // 把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";

        // 组装表格
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);
    }

    generateNavigator(pageInfo)
}

/**
 * 生成分页页码及导航条
 * @param pageInfo
 */
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 4,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }

    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

/**
 * 分页时的回调函数
 * @param pageIndx
 * @param jQuery
 */
function paginationCallBack(pageIndex, jQuery) {
    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();
    return false;
}


/**
 * 声明专门的函数 显示确认的模态框 [删除]
 * @param roles
 */
function showConfirmModal(roles) {
    // 清除模态框中的数据
    $("#roleNameDiv").empty();
    $("#confirmModal").modal("show");
    window.roleIdArry = [];
    for(var i = 0;i < roles.length; i++){
        var role = roles[i];
        var roleName = roles[i].roleName;
        $("#roleNameDiv").append(roleName + "<br/>");

        // 往全局变量里面存放要删除的id
        var roleId = role.roleId;
        window.roleIdArry.push(roleId);
    }
}