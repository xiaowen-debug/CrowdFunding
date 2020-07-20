package com.xiaowen.crowd.service.role;

import com.github.pagehelper.PageInfo;
import com.xiaowen.crowd.entity.role.Role;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/13 23:44
 */
public interface RoleService {

  PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

  void execeSave(Role role);

  void execeUpdate(Role role);

  void execeDelete(Role role);

  void execeBatchDelete(List<Integer> roleIdList);

  List<Role> getAssingedRole(Integer adminId);

  List<Role> getUnAssingedRole(Integer adminId);

  void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
