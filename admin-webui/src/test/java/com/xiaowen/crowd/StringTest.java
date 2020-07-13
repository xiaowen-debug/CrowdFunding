package com.xiaowen.crowd;

import com.xiaowen.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @Author wen.he
 * @Date 2020/7/11 15:23
 */
public class StringTest {

  @Test
  public void testMd5() {

    String password = "321";

    String encoded = CrowdUtil.md5(password);

    //202cb962ac59075b964b07152d234b70
    System.out.println(encoded);
  }
}
