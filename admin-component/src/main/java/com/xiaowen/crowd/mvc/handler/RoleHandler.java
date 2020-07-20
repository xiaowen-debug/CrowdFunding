package com.xiaowen.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.xiaowen.crowd.entity.role.Role;
import com.xiaowen.crowd.service.role.RoleService;
import com.xiaowen.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/13 23:46
 */
@Controller
public class RoleHandler {

  @Autowired
  protected RoleService roleService;


  /**
   * 初始数据
   * @param pageNum
   * @param pageSize
   * @param keyword
   * @return
   */
  @RequestMapping("/role/get/page/info.json")
  @ResponseBody
  public ResultEntity<PageInfo<Role>> getPageInfo(
                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                @RequestParam(value = "paeSize", defaultValue = "5") Integer pageSize,
                @RequestParam(value = "keyword", defaultValue = "") String keyword
            ) {

    //获取分页数据
    PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

    return  ResultEntity.successWithData(pageInfo);
  }

  /**
   * 执行保存
   * @param role
   * @return
   */
  @RequestMapping("/role/save.json")
  @ResponseBody
  public ResultEntity<String> saveRole(Role role) {

    roleService.execeSave(role);

    return  ResultEntity.success("角色新增成功");
  }

  /**
   * 执行更新
   * @param role
   * @return
   */
  @RequestMapping("/role/update.json")
  @ResponseBody
  public ResultEntity<String> updateRole(Role role) {

    roleService.execeUpdate(role);

    return  ResultEntity.success("角色更新成功");
  }

  /**
   * 执行删除
   * @param role
   * @return
   */
  @RequestMapping("/role/remove.json")
  @ResponseBody
  public ResultEntity<String> deleteRole(Role role) {

    roleService.execeDelete(role);

    return  ResultEntity.success("角色更新成功");
  }

  /**
   * 执行批量删除
   * @param roleIdList
   * @return
   */
  @RequestMapping("/role/remove/role/by/id/array.json")
  @ResponseBody
  public ResultEntity<String> batchDeleteRole(@RequestBody List<Integer> roleIdList) {

    roleService.execeBatchDelete(roleIdList);

    return  ResultEntity.success("角色删除成功");
  }
}
