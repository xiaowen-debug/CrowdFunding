package org.springframework.web.filter;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author wen.he
 * @Date 2020/7/22 23:23
 *
 *
 */
public class DelegatingFilterProxy extends GenericFilterBean {
  private String contextAttribute;
  private WebApplicationContext webApplicationContext;
  private String targetBeanName;
  private boolean targetFilterLifecycle;
  private volatile Filter delegate;
  private final Object delegateMonitor;

  public DelegatingFilterProxy() {
    this.targetFilterLifecycle = false;
    this.delegateMonitor = new Object();
  }

  public DelegatingFilterProxy(Filter delegate) {
    this.targetFilterLifecycle = false;
    this.delegateMonitor = new Object();
    Assert.notNull(delegate, "Delegate Filter must not be null");
    this.delegate = delegate;
  }

  public DelegatingFilterProxy(String targetBeanName) {
    this(targetBeanName, (WebApplicationContext)null);
  }

  public DelegatingFilterProxy(String targetBeanName, WebApplicationContext wac) {
    this.targetFilterLifecycle = false;
    this.delegateMonitor = new Object();
    Assert.hasText(targetBeanName, "Target Filter bean name must not be null or empty");
    this.setTargetBeanName(targetBeanName);
    this.webApplicationContext = wac;
    if (wac != null) {
      this.setEnvironment(wac.getEnvironment());
    }

  }

  public void setContextAttribute(String contextAttribute) {
    this.contextAttribute = contextAttribute;
  }

  public String getContextAttribute() {
    return this.contextAttribute;
  }

  public void setTargetBeanName(String targetBeanName) {
    this.targetBeanName = targetBeanName;
  }

  protected String getTargetBeanName() {
    return this.targetBeanName;
  }

  public void setTargetFilterLifecycle(boolean targetFilterLifecycle) {
    this.targetFilterLifecycle = targetFilterLifecycle;
  }

  protected boolean isTargetFilterLifecycle() {
    return this.targetFilterLifecycle;
  }

  protected void initFilterBean() throws ServletException {
    synchronized(this.delegateMonitor) {
      if (this.delegate == null) {
        if (this.targetBeanName == null) {
          this.targetBeanName = this.getFilterName();
        }

        /**
         * 加载两次，第一次在项目初始化时加载springIOC容器
         * 第二次在第一次请求时加载springMVCIOC容器，
         * 此时注销第一次加载spring容器
         *
         * 三大容器加载顺序
         * ContextLoaderListener > DelegatingFilterProxy > DispatcherServlet
         * Listener > Filter > Servlet
        */
        /*
        WebApplicationContext wac = this.findWebApplicationContext();
        if (wac != null) {
          this.delegate = this.initDelegate(wac);
        }*/

      }

    }
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Filter delegateToUse = this.delegate;
    if (delegateToUse == null) {
      synchronized(this.delegateMonitor) {
        delegateToUse = this.delegate;
        if (delegateToUse == null) {
          //把原来的查找IOC容器的代码注释掉
          //WebApplicationContext wac = this.findWebApplicationContext();

          //按我们自己的需要重新编写
          //1、获取ServletContext对象
          ServletContext sc = this.getServletContext();

          //2、拼接SpringMVC将IOC容器存入ServletContext域的时候使用的属性名
          String servletName = "springDispatcherServlet";

          String attrName = FrameworkServlet.SERVLET_CONTEXT_PREFIX + servletName;

          //3、根据attrName从ServletContext域中获取IOC容器对象
          WebApplicationContext wac = (WebApplicationContext) sc.getAttribute(attrName);

          if (wac == null) {
            throw new IllegalStateException("No WebApplicationContext found: no ContextLoaderListener or DispatcherServlet registered?");
          }

          delegateToUse = this.initDelegate(wac);
        }

        this.delegate = delegateToUse;
      }
    }

    this.invokeDelegate(delegateToUse, request, response, filterChain);
  }

  public void destroy() {
    Filter delegateToUse = this.delegate;
    if (delegateToUse != null) {
      this.destroyDelegate(delegateToUse);
    }

  }

  protected WebApplicationContext findWebApplicationContext() {
    if (this.webApplicationContext != null) {
      if (this.webApplicationContext instanceof ConfigurableApplicationContext) {
        ConfigurableApplicationContext cac = (ConfigurableApplicationContext)this.webApplicationContext;
        if (!cac.isActive()) {
          cac.refresh();
        }
      }

      return this.webApplicationContext;
    } else {
      String attrName = this.getContextAttribute();
      return attrName != null ? WebApplicationContextUtils.getWebApplicationContext(this.getServletContext(), attrName) : WebApplicationContextUtils.findWebApplicationContext(this.getServletContext());
    }
  }

  protected Filter initDelegate(WebApplicationContext wac) throws ServletException {
    Filter delegate = (Filter)wac.getBean(this.getTargetBeanName(), Filter.class);
    if (this.isTargetFilterLifecycle()) {
      delegate.init(this.getFilterConfig());
    }

    return delegate;
  }

  protected void invokeDelegate(Filter delegate, ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    delegate.doFilter(request, response, filterChain);
  }

  protected void destroyDelegate(Filter delegate) {
    if (this.isTargetFilterLifecycle()) {
      delegate.destroy();
    }

  }
}

