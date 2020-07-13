package com.xiaowen.crowd.mvc.config;

import com.google.gson.Gson;
import com.xiaowen.crowd.exception.AccessForbiddenException;
import com.xiaowen.crowd.exception.LoginAcctAlreadyInUseException;
import com.xiaowen.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.xiaowen.crowd.exception.LoginFailedException;
import com.xiaowen.crowd.util.CrowdUtil;
import com.xiaowen.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author wen.he
 * @Date 2020/7/10 22:51
 *
 * 基于注解的异常处理器类  @ControllerAdvice
 * 一个异常对应一个方法
 */
@ControllerAdvice
public class CrowdExceptionResolver {

  //可对应多个类型
  @ExceptionHandler(value = NullPointerException.class)
  public ModelAndView resolveNullPointerException(NullPointerException exception,
                                                  HttpServletRequest request, HttpServletResponse response) throws IOException {

    String viewName = "system-error";
    return this.commonResolveException(viewName, exception, request, response);
  }

  @ExceptionHandler(value = ArithmeticException.class)
  public ModelAndView resolveArithmeticException(ArithmeticException exception,
                                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 只是指定当前异常对应的页面即可
    String viewName = "system-error";
    return this.commonResolveException(viewName, exception, request, response);
  }


  @ExceptionHandler(value = AccessForbiddenException.class)
  public ModelAndView resolveAccessForbiddenExceptionException(AccessForbiddenException exception,
                                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 只是指定当前异常对应的页面即可
    String viewName = "admin-login";
    return this.commonResolveException(viewName, exception, request, response);
  }

  @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
  public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception,
                                                               HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 只是指定当前异常对应的页面即可
    String viewName = "admin-add";
    return this.commonResolveException(viewName, exception, request, response);
  }

  @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
  public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception,
                                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 只是指定当前异常对应的页面即可
    String viewName = "system-error";
    return this.commonResolveException(viewName, exception, request, response);
  }


  /**
   * 登录失败异常
   * @param exception
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @ExceptionHandler(value = LoginFailedException.class)
  public ModelAndView resolveLoginFailedException(ArithmeticException exception,
                                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 只是指定当前异常对应的页面即可
    String viewName = "admin-login";
    return this.commonResolveException(viewName, exception, request, response);
  }

  /**
   *  核心异常处理方法    利用多态机制，适配多种异常
   ** @param exception SpringMVC 捕获到的异常对象
   * @param request 为了判断当前请求是“普通请求”还是“Ajax 请求” 需要传入原生 request 对象
   * @param response 为了能够将 JSON 字符串作为当前请求的响应数 据返回给浏览器
   * @param viewName  指定要前往的视图名称
   * @return ModelAndView
   * @throws IOException
   */
  private ModelAndView commonResolveException(String viewName, Exception exception,
                                     HttpServletRequest request, HttpServletResponse response) throws IOException {

    //1、判断当前请求类型
    boolean judgeResult = CrowdUtil.judgeRequestType(request);

    //2、处理ajax请求
    if (judgeResult) {

      //3、创建ResultEntity对象
      ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

      //4、创建Gson对象
      Gson gson = new Gson();

      //5、将ResultEntity对象转换为JSON字符串
      String json = gson.toJson(resultEntity);

      //6、将JSON字符串作为响应体返回给浏览器
      response.getWriter().write(json);

      //7、由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
      return null;
    }

    //8、一般请求，不是Ajax请求
    ModelAndView modelAndView = new ModelAndView();

    //9、将Exception对象cun'存入模型
    modelAndView.addObject("exception", exception);

    //10、设置对应的视图名称
    modelAndView.setViewName(viewName);

    //11、返回
    return modelAndView;
  }
}
