package com.xiaowen.crowd.mvc.handler;

import com.xiaowen.crowd.constant.CrowdConstant;
import com.xiaowen.crowd.entity.admin.Admin;
import com.xiaowen.crowd.entity.ParamData;
import com.xiaowen.crowd.entity.Student;
import com.xiaowen.crowd.service.admin.Adminservice;
import com.xiaowen.crowd.util.CrowdUtil;
import com.xiaowen.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/9 23:03
 */
@Controller
public class TestHandler {

  @Autowired
  protected Adminservice adminservice;

  Logger logger = LoggerFactory.getLogger(TestHandler.class);

  /**
   * spring整合springMvc测试
   * @param modelMap
   * @return
   */
  @RequestMapping("/test/ssm.html")
  public String testSsm(ModelMap modelMap, HttpServletRequest request) {

    boolean judgeResult = CrowdUtil.judgeRequestType(request);
    logger.info("ajax_quest = " + judgeResult);
    List<Admin> adminList = adminservice.getAll();
    String str = null;
    int length = str.length();
    modelMap.addAttribute(CrowdConstant.ATTR_NAME_EXCEPTION, adminList);
    return "target";
  }

  /**
   * 数组传输一：参数名加"[]",jQuery处理数组时默认在参数名后添加"[]"
   * @param arrays
   * @return
   */
  @RequestMapping("/send/array.html")
  @ResponseBody
  public String testAjaxOne(@RequestParam("array[]") List<Integer> arrays) {

    arrays.stream().forEach(System.out::println);
    return "success";
  }

  /**
   * 数组传输二：通过类属性绑定数组
   * @param paramData
   * @return
   */
  @RequestMapping("/send/arrayTwo.html")
  @ResponseBody
  public String testAjaxTwo(ParamData paramData) {

    List<Integer> arrays = paramData.getArray();
    arrays.stream().forEach(System.out::println);
    return "success";
  }

  /**
   * 数组传输三：通过JSON绑定数组
   * @param arrays
   * @return
   */
  @RequestMapping("/send/arrayThree.html")
  @ResponseBody
  public String testAjaxThree(@RequestBody List<Integer> arrays) {

    arrays.stream().forEach(x -> logger.info("number = " + x));
    return "success";
  }

  /**
   * JSON复杂对象传输
   * @param students
   * @return
   */
  @RequestMapping("/send/compose/object.json")
  @ResponseBody
  public String testAjaxCompose(@RequestBody List<Student> students) {

    students.stream().forEach(x -> logger.info("student = " + x.toString()));

    return "success";
  }

  /**
   * 采用统一返回JSON格式
   * @param students
   * @return
   */
  @RequestMapping("/send/compose/tongyi.json")
  @ResponseBody
  public ResultEntity<List<Student>> testAjaxTyCompose(@RequestBody List<Student> students, HttpServletRequest request) {
    boolean judgeResult = CrowdUtil.judgeRequestType(request);
    logger.info("ajax_quest = " + judgeResult);
    students.stream().forEach(x -> logger.info("student = " + x.toString()));

    String str = null;
    int length = str.length();
    ResultEntity<List<Student>> listResultEntity = ResultEntity.successWithData(students);
    return listResultEntity;
  }
}
