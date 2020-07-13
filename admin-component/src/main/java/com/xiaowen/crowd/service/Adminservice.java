package com.xiaowen.crowd.service;

import com.github.pagehelper.PageInfo;
import com.xiaowen.crowd.entity.Admin;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/9 21:42
 */
public interface Adminservice {

  void saveAdmin(Admin admin);

  List<Admin> getAll();

  /**
   * 登录验证
   * @param username
   * @param password
   * @return
   */
  Admin getAdminByUserName(String username, String password);

  /**
   * 分页查询
   * @param keyword
   * @param pageNum
   * @param pageSize
   * @return
   */
  PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

  /**
   * 根据ID删除Admin
   * @param adminId
   */
  void removeAdminById(Integer adminId);

  /**
   * 根据ID查询Admin
   * @param adminId
   * @return
   */
  Admin getAdminById(Integer adminId);

  /**
   * 更新
   * @param admin
   */
  void update(Admin admin);

  /**
   * 账号查询Admin
   * @param loginAcct
   * @return
   */
  Admin getAdminByLoginAcct(String loginAcct);
}
