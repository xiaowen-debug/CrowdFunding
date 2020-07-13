package com.xiaowen.crowd.util;

import com.xiaowen.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author wen.he
 * @Date 2020/7/10 22:28
 *
 * 工具类
 * 1、请求类型方式判断  普通请求还是Ajax请求
 * 2、MD5密码算法加密
 */
public class CrowdUtil {

  /**
   * 对明文密码进行md5算法加密
   * @param source
   * @return
   */
  public static String md5(String source) {

    //1、判断source是否有效
    if (source == null || source.length() == 0) {
      //2、如果不是有效的字符串抛出异常
      throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
    }

    try {

      //3、获取MessageDige对象
      String algorithm = "md5";
      MessageDigest messageDige = MessageDigest.getInstance(algorithm);

      //4、获取明文字符串对应的字节数组
      byte[] input = source.getBytes();

      //5、执行加密
      byte[] outPut = messageDige.digest(input);

      //6、创建BigInteger对象
      int signum = 1; // 1为正数  -1为负数  0为0
      BigInteger bigInteger = new BigInteger(signum, outPut);

      //7、按照16进制将bigInteger的值转为字符串
      int radix = 16;
      String encoded = bigInteger.toString(radix);

      return encoded;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * 判断当前请求是否为 Ajax 请求
   * @param request
   * @return
   */
  public static  boolean judgeRequestType(HttpServletRequest request) {

    //获取请求头
    String acceptHeader = request.getHeader("Accept");
    String xRequestHeader = request.getHeader("X-Requested-With");

    return (acceptHeader != null && acceptHeader.contains("application/json"))
        || (xRequestHeader != null && xRequestHeader.contains("XMLHttpRequest"));
  }
}
