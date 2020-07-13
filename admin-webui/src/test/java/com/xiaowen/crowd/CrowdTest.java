package com.xiaowen.crowd;

import com.xiaowen.crowd.entity.Admin;
import com.xiaowen.crowd.mapper.AdminMapper;
import com.xiaowen.crowd.service.Adminservice;
import com.xiaowen.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author wen.he
 * @Date 2020/7/9 10:35
 */
//在类上标记必要的注解，Spring整合Junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private AdminMapper adminMapper;

  @Autowired
  private Adminservice adminservice;

  @Test
  public void testInsert() {
    Admin admin = new Admin(null, "xiaowen", "123", "xiaowen", "1030692558@qq.com", "2020-07-09");
    int insert = adminMapper.insert(admin);
    System.out.println("受影响的行数：" + insert);
  }

  /**
   * 测试事务
   */
  @Test
  public void testTx() {

    for (int i = 0; i < 200; i++) {
      Admin admin = new Admin(null, "xiaowen" + i, CrowdUtil.md5("321" + i), "ouou", "xiaowen" + i + "@qq.com", "2020-07-09");
      adminservice.saveAdmin(admin);
    }
  }

  @Test
  public void testLog() {
    //1、获取Logger对象，这里传入的CLASS对象就是当前打印日志的类
    Logger logger = LoggerFactory.getLogger(CrowdTest.class);

    //2、根据不同日志级别打印日志
    logger.debug("hello i am debug level!!!");
    logger.debug("hello i am debug level!!!");
    logger.debug("hello i am debug level!!!");

    logger.info("hello i am info level!!!");
    logger.info("hello i am info level!!!");
    logger.info("hello i am info level!!!");

    logger.warn("hello i am warn level!!!");
    logger.warn("hello i am warn level!!!");
    logger.warn("hello i am warn level!!!");

    logger.error("hello i am error level!!!");
    logger.error("hello i am error level!!!");
    logger.error("hello i am error level!!!");
  }

  @Test
  public void testQuery() {
    Admin admin = adminMapper.selectByPrimaryKey(2);
    System.out.println(admin);
  }
  @Test
  public void testConnection() {
    try {
      Connection connection = dataSource.getConnection();
      System.out.println(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
