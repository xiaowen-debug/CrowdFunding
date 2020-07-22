package com.xiaowen.crowd.mapper;

import com.xiaowen.crowd.entity.auth.Auth;
import com.xiaowen.crowd.entity.auth.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    /**
     * 根据角色ID查询已分配的权限
     * @param roleId
     * @return
     */
    List<Auth> selectAssignedAuthIdByRoleId(@Param("roleId") Integer roleId);

    /**
     * 删除旧的关联关系
     * @param roleId
     */
    void deleteOldRelationship(@Param("roleId") Integer roleId);

    /**
     * 保存新的关联关系
     * @param roleId
     * @param authIdList
     */
    void insertNewRelationship(@Param("roleId") Integer roleId, @Param("authIdList") List<Integer> authIdList);
}