/**
 * 初始化树形结构
 */
function generateTree() {

    //1、创建JSON对象用于存储对zTree对象的设置
    var setting = {
        view: {
            addDiyDom: myAddDiyDom,
            addHoverDom: myAddHoverDom,
            removeHoverDom: myRemoveHoverDom
        },
        data: {
            key: {
                url: "xiaowen"
            }
        }
    };

    //2、准备生成树形结构的JSON数据
    $.ajax({
        url: "menu/get/whole/tree.json",
        type: "post",
        dataType: "json",
        success: function (res) {
            var result = res.result;
            if (result == "SUCCESS") {

                var zNodes = res.data;
                //3、初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result ="FAILED") {
                layer.msg(res.message);
            }
        }
    });
}

/**
 * 修改默认图标
 * @param treeId
 * @param treeNode
 */
function myAddDiyDom(treeId, treeNode) {

    /**
     * zTree生成ID的规则
     * eg: treeDemo_7_ico
     * 解析：ul标签的ID_当前节点的序号_功能
     * 提示：ul标签的ID_当前节点的序号  这部分可以通过访问treeNode的ID属性获得
     */

    //根据ID的生成规则拼接出span标签的ID
    var spanId = treeNode.tId + "_ico"

    //先删除样式，再添加样式
    $("#" + spanId)
        .removeClass()
        .addClass(treeNode.icon);
}

/**
 * 鼠标移入节点范围时 添加按钮组
 * @param treeId
 * @param treeNode
 */
function myAddHoverDom(treeId, treeNode) {

    /**
     * 按钮组的标签结构 <span><a><i>按钮一</i></a><a><i>按钮二</i></a></span>
     * 按钮组出现的位置：节点中 treeDemo_n_a 超链接的后面
     * @type {string}
     */

    //找到附着超链接的ID
    var anchorId = treeNode.tId + "_a";
    var btnGroupId = treeNode.tId + "_btnGrp";

    // 要是以前加过了就不再追加了
    if($("#" + btnGroupId).length > 0){
        return;
    }

    var btnHTML = "";
    var addBtn = "<a id = '" + treeNode.id + "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var editBtn = "<a id = '" + treeNode.id + "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var removeBtn = "<a id = '" + treeNode.id + "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    // 根节点
    var level = treeNode.level;
    if(level == 0){
        btnHTML = btnHTML + addBtn;
    }
    // 分支节点
    if(level == 1){
        btnHTML = addBtn + " " + editBtn;
        var length = treeNode.children.length;
        // 如果当前节点没有子节点 则它自己也可以移除
        if(length == 0){
            btnHTML = btnHTML + " " +  removeBtn;
        }
    }
    // 叶子节点
    if(level == 2){
        btnHTML = editBtn + " " + removeBtn;
    }

    // 在超链接后面追加元素
    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");
}

/**
 * 鼠标离开节点范围时 添加按钮组
 * @param treeId
 * @param treeNode
 */
function myRemoveHoverDom(treeId, treeNode) {

    // 移除该元素
    $("#" + treeNode.tId + "_btnGrp").remove();
}
