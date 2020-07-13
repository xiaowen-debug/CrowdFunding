package com.xiaowen.crowd.exception;

/**
 * @Author wen.he
 * @Date 2020/7/11 20:27
 *
 * 未登录异常
 */
public class AccessForbiddenException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AccessForbiddenException() {
  }

  public AccessForbiddenException(String message) {
    super(message);
  }

  public AccessForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccessForbiddenException(Throwable cause) {
    super(cause);
  }

  public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
