package com.xiaowen.crowd.mvc.config;

import com.xiaowen.crowd.entity.admin.Admin;
import com.xiaowen.crowd.entity.auth.Auth;
import com.xiaowen.crowd.entity.role.Role;
import com.xiaowen.crowd.service.admin.Adminservice;
import com.xiaowen.crowd.service.auth.AuthService;
import com.xiaowen.crowd.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/23 10:44
 *
 * 登录判断实现
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService {

  @Autowired
  private Adminservice adminservice;

  @Autowired
  private AuthService authService;

  @Autowired
  private RoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {

    Admin admin = adminservice.getAdminByLoginAcct(loginAcct);

    //获取角色
    List<Role> assingedRole = roleService.getAssingedRole(admin.getId());

    //获取权限
    List<Auth> assignedAuth = authService.getAssignedAuthByAdminId(admin.getId());

    //创建集合对象用来存储GrantedAuthority
    List<GrantedAuthority> authorities = new ArrayList<>();

    //角色与权限都封装在GrantedAuthority集合中，通过"ROLE_"前缀来进行区分权限和角色
    //存入角色信息
    for (Role role : assingedRole) {
      String roleName = "ROLE_" + role.getName();
      SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
      authorities.add(simpleGrantedAuthority);
    }
    //存入权限信息
    for (Auth auth : assignedAuth) {
      SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth.getName());
      authorities.add(simpleGrantedAuthority);
    }

    return new SecurityAdmin(admin, authorities);
  }
}
