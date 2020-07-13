package com.xiaowen.crowd.mvc.interceptor;

import com.xiaowen.crowd.constant.CrowdConstant;
import com.xiaowen.crowd.entity.Admin;
import com.xiaowen.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author wen.he
 * @Date 2020/7/11 20:22
 *
 * 登录拦截器，判断用户是否登录
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    HttpSession session = request.getSession();

    // 获取Session域中的Admin对象
    Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

    // 未登录
    if (admin == null) {

      throw new AccessForbiddenException(CrowdConstant.MESSAGE_LOGIN_FORBIDEN);
    }
    return true;
  }
}
