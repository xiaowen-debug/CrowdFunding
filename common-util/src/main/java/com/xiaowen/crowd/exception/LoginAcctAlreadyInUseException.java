package com.xiaowen.crowd.exception;

/**
 * @Author wen.he
 * @Date 2020/7/12 23:58
 *
 * 账号唯一性约束异常
 */
public class LoginAcctAlreadyInUseException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public LoginAcctAlreadyInUseException() {
  }

  public LoginAcctAlreadyInUseException(String message) {
    super(message);
  }

  public LoginAcctAlreadyInUseException(String message, Throwable cause) {
    super(message, cause);
  }

  public LoginAcctAlreadyInUseException(Throwable cause) {
    super(cause);
  }

  public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
