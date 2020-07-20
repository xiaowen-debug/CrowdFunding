package com.xiaowen.crowd.service.role;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaowen.crowd.entity.role.Role;
import com.xiaowen.crowd.entity.role.RoleExample;
import com.xiaowen.crowd.mapper.AdminMapper;
import com.xiaowen.crowd.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/13 23:44
 */

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  protected RoleMapper roleMapper;

  @Autowired
  protected AdminMapper adminMapper;

  @Override
  public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

    //1、开启分页功能
    PageHelper.startPage(pageNum, pageSize);

    //2、执行查询
    List<Role> roleList = roleMapper.selectRoleBykeyWord(keyword);

    //2、封装为PageInfo对象返回
    return new PageInfo<>(roleList);
  }

  @Override
  public void execeSave(Role role) {

    if (role != null) {
      roleMapper.insert(role);
    }
  }

  @Override
  public void execeUpdate(Role role) {

    if (role != null) {
      roleMapper.updateByPrimaryKey(role);
    }
  }

  @Override
  public void execeDelete(Role role) {

    if (role != null) {
      roleMapper.updateByPrimaryKey(role);
    }
  }

  @Override
  public void execeBatchDelete(List<Integer> roleIdList) {

    if (!CollectionUtils.isEmpty(roleIdList)) {

      RoleExample roleExample = new RoleExample();

      RoleExample.Criteria criteria = roleExample.createCriteria();
      criteria.andIdIn(roleIdList);

      roleMapper.deleteByExample(roleExample);
    }
  }

  @Override
  public List<Role> getAssingedRole(Integer adminId) {

    if(adminId == null) {
      return null;
    }
    return roleMapper.selectAssignedRole(adminId);
  }

  @Override
  public List<Role> getUnAssingedRole(Integer adminId) {
    if(adminId == null) {
      return null;
    }
    return roleMapper.selectUnAssignedRole(adminId);
  }

  @Transactional
  @Override
  public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {

    //1、先删除
    adminMapper.deleteOldRelationship(adminId);

    //2、再添加
    if (roleIdList != null && roleIdList.size() > 0) {
      adminMapper.insertNewRelationship(adminId, roleIdList);
    }
  }
}
