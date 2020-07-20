package com.xiaowen.crowd.mvc.handler;

import com.xiaowen.crowd.entity.role.Role;
import com.xiaowen.crowd.service.admin.Adminservice;
import com.xiaowen.crowd.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/19 20:43
 */
@Controller
public class AssignHandler {

  @Autowired
  protected Adminservice adminservice;

  @Autowired
  protected RoleService roleService;

  /**
   * 初始数据
   * @param adminId
   * @param pageNum
   * @param keyword
   * @param modelMap
   * @return
   */
  @RequestMapping("/assign/to/assign/role/page/{adminId}/{pageNum}/{keyword}.html")
  public String toAssingRolePage(@PathVariable("adminId") Integer adminId,
                                 @PathVariable("pageNum") Integer pageNum,
                                 @PathVariable("keyword") String keyword,
                                 ModelMap modelMap) {

    //1、查询已分配角色
    List<Role> assignedRoleList =  roleService.getAssingedRole(adminId);

    //2、查询未分配角色
    List<Role> unAssignedRoleList =  roleService.getUnAssingedRole(adminId);

    //3、存入模型
    modelMap.addAttribute("assignedRoleList", assignedRoleList);
    modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

    modelMap.addAttribute("admin", adminId);
    modelMap.addAttribute("pageNum", pageNum);
    modelMap.addAttribute("keyword", keyword);

    return "assign-role";
  }

  /**
   * 执行保存
   * @param adminId
   * @param pageNum
   * @param keyword
   * @param roleIdList
   * @return
   */
  @RequestMapping("/assign/do/role/assign.html")
  public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                 @RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("keyword") String keyword,
                                 @RequestParam("roleIdList") List<Integer> roleIdList) {

    roleService.saveAdminRoleRelationship(adminId, roleIdList);

    return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
  }
}
