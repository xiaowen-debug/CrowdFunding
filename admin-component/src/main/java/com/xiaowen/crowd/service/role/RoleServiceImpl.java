package com.xiaowen.crowd.service.role;

import com.xiaowen.crowd.mapper.RoleMapper;
import com.xiaowen.crowd.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wen.he
 * @Date 2020/7/13 23:44
 */

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  protected RoleMapper roleMapper;
}
