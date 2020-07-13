package com.xiaowen.crowd.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaowen.crowd.constant.CrowdConstant;
import com.xiaowen.crowd.entity.Admin;
import com.xiaowen.crowd.entity.AdminExample;
import com.xiaowen.crowd.exception.LoginFailedException;
import com.xiaowen.crowd.mapper.AdminMapper;
import com.xiaowen.crowd.service.Adminservice;
import com.xiaowen.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author wen.he
 * @Date 2020/7/9 21:42
 */
@Service
public class AdminserviceImpl implements Adminservice {

  @Autowired
  protected AdminMapper adminMapper;

  @Override
  public void saveAdmin(Admin admin) {

    this.adminMapper.insert(admin);
  }

  @Override
  public List<Admin> getAll() {
    List<Admin> adminList = adminMapper.selectAll();
    return adminMapper.selectAll();
  }

  @Override
  public Admin getAdminByUserName(String loginAcct, String password) {

    if (loginAcct == null) {
      throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
    }

    AdminExample adminExample = new AdminExample();
    AdminExample.Criteria criteria = adminExample.createCriteria();
    criteria.andLoginAcctEqualTo(loginAcct);

    List<Admin> adminList = adminMapper.selectByExample(adminExample);
    if (CollectionUtils.isEmpty(adminList)) {
      throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
    }

    // 数据库加唯一性约束可解决
    if (adminList.size() > 1) {
      throw  new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
    }

    Admin admin = adminList.get(0);
    if (admin == null) {
      throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
    }

    // 获取根据账号查询出来的密码
    String userPswdDb = admin.getUserPswd();
    // 将表单中的明文密码进行加密
    String userPswdForm = CrowdUtil.md5(password);
    // 比对 Objects.equals() 避免空指针异常
    if (!Objects.equals(userPswdDb, userPswdForm)) {
      // 密码不相等
      throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
    }
    return admin;
  }

  @Override
  public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

    //1、调用PageHelper的静态方法开启分页功能
    //这里充分体现了PageHelper的‘非侵入式’设计，原本要做的查询不必有任何修改
    PageHelper.startPage(pageNum, pageSize);

    //2、执行查询
    List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
    //3、封装到PageInfo对象中
    return new PageInfo<>(adminList);
  }

  @Override
  public void removeAdminById(Integer adminId) {

    this.adminMapper.deleteByPrimaryKey(adminId);
  }
}
