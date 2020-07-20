package com.xiaowen.crowd.mapper;

import com.xiaowen.crowd.entity.role.Role;
import com.xiaowen.crowd.entity.role.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleBykeyWord(@Param("keyword") String keyword);

    List<Role> selectAssignedRole(@Param("adminId") Integer adminId);

    List<Role> selectUnAssignedRole(@Param("adminId") Integer adminId);
}