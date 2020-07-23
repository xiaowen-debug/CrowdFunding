package com.xiaowen.crowd.mvc.config;

import com.xiaowen.crowd.entity.admin.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/23 10:33
 *
 * springsecurity用户实体
 * 考虑到User对象中仅仅包含账号和密码，为了能够获取到原始的Admin对象
 * 专门创建这个类对User类进行扩展
 */
public class SecurityAdmin extends User {

  private static final long seriaVersionUID = 1L;

  //原始的Admin对象,包含Admin对象的全部属性
  private Admin originalAdmin;

  public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {

    //调用父类构造器
    super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
    this.originalAdmin = originalAdmin;

    //擦除原始Admin对象中的密码
    originalAdmin.setUserPswd(null);
  }

  public Admin getOriginalAdmin() {
    return originalAdmin;
  }

}
