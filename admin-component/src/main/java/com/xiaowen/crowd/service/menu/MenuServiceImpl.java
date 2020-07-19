package com.xiaowen.crowd.service.menu;

import com.xiaowen.crowd.entity.menu.Menu;
import com.xiaowen.crowd.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/15 11:30
 */
@Service
public class MenuServiceImpl implements MenuService {

  @Autowired
  protected MenuMapper menuMapper;

  @Override
  public List<Menu> getAll() {
    return menuMapper.selectAll();
  }

  @Override
  public void saveMenu(Menu menu) {

    menuMapper.insert(menu);
  }

  @Override
  public void updateMenu(Menu menu) {

    menuMapper.updateByPrimaryKeySelective(menu);
  }

  @Override
  public void removeMenu(Integer id) {

    menuMapper.deleteByPrimaryKey(id);
  }
}
