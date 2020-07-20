package com.xiaowen.crowd.mvc.handler;

import com.xiaowen.crowd.constant.CrowdConstant;
import com.xiaowen.crowd.entity.admin.Admin;
import com.xiaowen.crowd.service.admin.Adminservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @Author wen.he
 * @Date 2020/7/19 11:33
 */
@Controller
public class LoginHandler {

  @Autowired
  private Adminservice adminservice;

  /**
   * 登录处理
   * @param userAcct
   * @param password
   * @param session
   * @return
   */
  @RequestMapping("admin/do/login.html")
  public String doLogin(@RequestParam("userAcct") String userAcct,
                        @RequestParam("password") String password,
                        HttpSession session) {


    Admin admin = adminservice.getAdminByUserName(userAcct, password);

    //将登录成功返回admin对象存入Session域
    session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

    return "redirect:/admin/to/main/page.html";
  }

  /**
   * 为了避免跳转到后台主页面再刷新浏览器导致重复提交登录表单，重定向到目标页面
   * 需单独写一个跳转方法
   * 重定向浏览器地址会改变
   * @return
   */
  @RequestMapping("admin/to/main/page.html")
  public String toLoginPage() {

    return "admin-main";
  }

  /**
   * 退出登录
   * @param session
   * @return
   */
  @RequestMapping("admin/do/logout.html")
  public String dologout(HttpSession session) {

    //强制Session失效
    session.invalidate();

    return "redirect:/admin/to/login/page.html";
  }

}
