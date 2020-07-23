package com.xiaowen.crowd.service.auth;

import com.xiaowen.crowd.entity.auth.Auth;

import java.util.List;
import java.util.Map;

/**
 * @Author wen.he
 * @Date 2020/7/20 11:43
 */
public interface AuthService {

  /**
   * 查询所有权限
   * @return
   */
  List<Auth> getAll();

  /**
   * 根据角色ID查询已分配的权限
   * @param roleId
   * @return
   */
  List<Auth> getAssignedAuthIdByRoleId(Integer roleId);

  /**
   * 为角色赋值权限
   * @param map
   */
  void saveRoleAuthRelationship(Map<String, List<Integer>> map);

  /**
   * 根据adminId查询其权限
   * @param adminId
   * @return
   */
  List<Auth> getAssignedAuthByAdminId(Integer adminId);
}
