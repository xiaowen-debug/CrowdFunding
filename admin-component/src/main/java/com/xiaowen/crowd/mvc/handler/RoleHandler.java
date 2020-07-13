package com.xiaowen.crowd.mvc.handler;

import com.xiaowen.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Author wen.he
 * @Date 2020/7/13 23:46
 */
@Controller
public class RoleHandler {

  @Autowired
  protected RoleService roleService;
}
