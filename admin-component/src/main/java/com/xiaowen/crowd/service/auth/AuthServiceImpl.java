package com.xiaowen.crowd.service.auth;

import com.xiaowen.crowd.entity.auth.Auth;
import com.xiaowen.crowd.entity.auth.AuthExample;
import com.xiaowen.crowd.mapper.AdminMapper;
import com.xiaowen.crowd.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author wen.he
 * @Date 2020/7/20 11:43
 */
@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private AuthMapper authMapper;

  @Override
  public List<Auth> getAll() {
    return authMapper.selectByExample(new AuthExample());
  }

  @Override
  public List<Auth> getAssignedAuthIdByRoleId(Integer roleId) {
    return authMapper.selectAssignedAuthIdByRoleId(roleId);
  }

  @Transactional
  @Override
  public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {

    //1、获取roleId
    List<Integer> roleIdList = map.get("roleId");
    Integer roleId = roleIdList.get(0);

    //2、获取保存的权限ID
    List<Integer> authIdList = map.get("authIds");

    //3、先删除旧的关联关系
    authMapper.deleteOldRelationship(roleId);

    //4、保存新的关联关系
    if (!CollectionUtils.isEmpty(authIdList)) {

      authMapper.insertNewRelationship(roleId, authIdList);
    }
  }

  @Override
  public List<Auth> getAssignedAuthByAdminId(Integer adminId) {
    if (adminId == null) {
      return null;
    }
    return authMapper.selectAssignedAuthByAdminId(adminId);
  }
}
