package com.xiaowen.crowd.mvc.handler;

import com.xiaowen.crowd.entity.menu.Menu;
import com.xiaowen.crowd.service.menu.MenuService;
import com.xiaowen.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author wen.he
 * @Date 2020/7/15 11:32
 */
@Controller
public class MenuHandler {

  @Autowired
  protected MenuService menuService;

  @RequestMapping("/menu/get/whole/tree.json")
  @ResponseBody
  public ResultEntity<Menu> getWholeTree() {

    List<Menu> menuList = menuService.getAll();

    // 2.将List<Menu>转换为Map<Menu的id,Menu>
    Map<Integer,Menu> menuMap = menuList.stream().collect(Collectors.toMap(Menu::getId, Function.identity(), (k1, k2) -> k1));

    // 3.声明变量用于存储根节点对象
    Menu rootNode = null;

    // 4.遍历List<Menu>
    for (Menu menu : menuList) {

      // 5.获取当前Menu对象的pid属性
      Integer pid = menu.getPid();

      // 6.判断pid是否为null
      if(pid == null) {

        // 7.如果pid为null，说明当前节点是根节点，所以赋值
        rootNode = menu;

        // 8.根节点没有父节点，所以不必找父节点组装，本次for循环停止执行，继续执行下一次循环
        continue ;
      }

      // 9.既然pid不为null，那么我们根据这个pid查找当前节点的父节点。
      Menu father = menuMap.get(pid);

      // 10.组装：将menu添加到maybeFather的子节点集合中
      father.getChildren().add(menu);
    }

    return ResultEntity.successWithData(rootNode);

  }

  @RequestMapping("/menu/save.json")
  @ResponseBody
  public ResultEntity<String> saveMenu(Menu menu) {

    menuService.saveMenu(menu);

    return ResultEntity.success("添加成功");
  }

  /**
   * 执行更新
   * @param menu
   * @return
   */
  @RequestMapping("/menu/update.json")
  @ResponseBody
  public ResultEntity<String> updateMenu(Menu menu) {

    menuService.updateMenu(menu);

    return ResultEntity.success("更新成功");
  }

  /**
   * 执行删除
   * @param id
   * @return
   */
  @RequestMapping("/menu/remove.json")
  @ResponseBody
  public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {

    menuService.removeMenu(id);

    return ResultEntity.success("删除成功");
  }
}
