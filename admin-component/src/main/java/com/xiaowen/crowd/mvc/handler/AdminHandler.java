package com.xiaowen.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.xiaowen.crowd.constant.CrowdConstant;
import com.xiaowen.crowd.entity.admin.Admin;
import com.xiaowen.crowd.exception.LoginAcctAlreadyInUseException;
import com.xiaowen.crowd.service.admin.Adminservice;
import com.xiaowen.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author wen.he
 * @Date 2020/7/11 15:44
 */
@Controller
public class AdminHandler {

  @Autowired
  private Adminservice adminservice;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * 分页查询数据
   * @param keyword
   * @param pageNum
   * @param pageSize
   * @param modelMap
   * @return
   */
  @RequestMapping("admin/get/page.html")
  public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                            ModelMap modelMap) {

    PageInfo<Admin> pageInfo = adminservice.getPageInfo(keyword, pageNum, pageSize);
    //将pageInfo对象放入模型
    modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
    return "admin-page";

  }


  /**
   * 根据ID删除Admin
   * @param adminId
   * @return
   */
  @RequestMapping("admin/remove/{adminId}/{pageNum}/{keyword}.html")
  public String deleteAdmin(@PathVariable("adminId") Integer adminId,
                            @PathVariable("pageNum") Integer pageNum,
                            @PathVariable("keyword") String keyword) {

    adminservice.removeAdminById(adminId);

    return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
  }


  /**
   * 保存
   * @param admin
   * @return
   */
  @RequestMapping("admin/save.html")
  public String saveAdmin(Admin admin) {

    //明文加密
    String userPswd = admin.getUserPswd();
    //String userEncodePswd = CrowdUtil.md5(userPswd);
    String userEncodePswd = bCryptPasswordEncoder.encode(userPswd);

    admin.setUserPswd(userEncodePswd);
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createTime = simpleDateFormat.format(date);
    admin.setCreateTime(createTime);

    try {
      adminservice.saveAdmin(admin);
    } catch (Exception e) {
      e.printStackTrace();
      if (e instanceof DuplicateKeyException) {

        throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
      }
    }

    return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
  }

  /**
   * 编辑页面数据
   * @param adminId
   * @param pageNum
   * @param keyword
   * @return
   */
  @RequestMapping("admin/to/edit/{adminId}/{pageNum}/{keyword}.html")
  public String getAdminById(@PathVariable("adminId") Integer adminId,
                             @PathVariable("pageNum") Integer pageNum,
                             @PathVariable("keyword") String keyword,
                             ModelMap modelMap) {

    Admin admin = adminservice.getAdminById(adminId);
    modelMap.addAttribute("admin", admin);
    modelMap.addAttribute("pageNum", pageNum);
    modelMap.addAttribute("keyword", keyword);
    return "admin-edit";
  }

  /**
   * 执行更新
   * @param pageNum
   * @param keyword
   * @param admin
   * @return
   */
  @RequestMapping("admin/update.html")
  public String update(@RequestParam("pageNum") String pageNum,
                       @RequestParam("keyword") String keyword,
                       Admin admin) {

    adminservice.update(admin);
    return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
  }

  @RequestMapping("admin/check/existUserInDb.html")
  @ResponseBody
  public String checkUserInDb(@RequestParam("loginAcct") String loginAcct) {

    Admin admin = adminservice.getAdminByLoginAcct(loginAcct);

    if (admin != null) {
      return "true";
    }
    return "false";
  }
}
