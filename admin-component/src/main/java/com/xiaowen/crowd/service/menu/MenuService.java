package com.xiaowen.crowd.service.menu;

import com.xiaowen.crowd.entity.menu.Menu;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/15 11:30
 */
public interface MenuService {

  List<Menu> getAll();

  void saveMenu(Menu menu);

  void updateMenu(Menu menu);

  void removeMenu(Integer id);
}
