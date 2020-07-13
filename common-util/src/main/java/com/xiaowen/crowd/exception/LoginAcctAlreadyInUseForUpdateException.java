package com.xiaowen.crowd.exception;

/**
 * @Author wen.he
 * @Date 2020/7/12 23:58
 *
 * 账号唯一性约束异常
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public LoginAcctAlreadyInUseForUpdateException() {
  }

  public LoginAcctAlreadyInUseForUpdateException(String message) {
    super(message);
  }

  public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
    super(message, cause);
  }

  public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
    super(cause);
  }

  public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
