package com.xiaowen.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author wen.he
 * @Date 2020/7/22 22:48
 */
@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CrowdUserDetailsService userDetailsService;

  /**
   * 在此处装配在springMVC的IOC容器，
   * 从而在serviceImpl的账号保存时，无法获取该Bean。serviceImpl从springIOC容器中获取。
   * 所以应该装配在spring的IOC容器中，都可以使用。
   * @return
   */
  /*
  @Bean
  public BCryptPasswordEncoder getBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
  */

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    // 在内存完成用户名密码的校验 指定当前用户拥有什么样的角色
    /*
    auth.inMemoryAuthentication()
        .withUser("tom").password("123")
        .roles("ADMIN", "大师")
        .and()
        .withUser("jerry").password("123")
        .roles("UPDATE");
    */


    //数据库获取
    auth.userDetailsService(userDetailsService)
        //进行加密判断
        .passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // 将记住我的信息存入
    //JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    //tokenRepository.setDataSource(dataSource);

    http
        // 对请求进行授权
        .authorizeRequests()
        .antMatchers("/admin/to/login/page.html").permitAll() // 针对登录页进行设置
        /**
         * SpringSecurity默认的是登陆成功后继续跳转到你之前的页面，意思就是，比如现在访问一个需要登陆认证的页面，如果你没有登陆，SpringSecurity会先让你去登陆，
         * 如果你没有配置就进入的是SpringSecurity默认的那个登陆页，如果配置了就会访问你配置的那个路径：
         * 在认证成功后，会继续跳转至刚刚你需要访问的（需要授权）路径，而我之前访问的路径就是index.html，
         * 在我在index.html页面上认证成功后，SpringSecurity继续访问index.html页面，所以会出现302。
         * 所以对index.jsp进行放行
         */
        .antMatchers("/index.jsp").permitAll()
        .antMatchers("/admin/to/main/page.html").permitAll() // 针对后台主页面进行设置

        // 无条件访问  静态资源放行
        .antMatchers("/bootstrap/**").permitAll()
        .antMatchers("/crowd/**").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/fonts/**").permitAll()
        .antMatchers("/img/**").permitAll()
        .antMatchers("/jquery/**").permitAll()
        .antMatchers("/layer/**").permitAll()
        .antMatchers("/layui/**").permitAll()
        .antMatchers("/script/**").permitAll()
        .antMatchers("/ztree/**").permitAll()
        .and().authorizeRequests().anyRequest()
        //需要登录才能访问
        .authenticated()
        // 指定用自己的登录页面 表单登录
        .and().formLogin()
        /**
         * 关于loginPage()方法的特殊说明
         * 指定登录页的同时会影响到：“提交登录表单的地址”，“退出登录地址”，“登录失败地址”
         * index.jsp GET   去登录页面
         * index.jsp POST  提交表单登录地址
         * index.jsp GET   登录失败
         * index.jsp GET   退出登录
         */
        .loginPage("/admin/to/login/page.html") //指定登录界面，后台转发
        /**
         * loginProcessingUrl()方法指定了登录地址，会覆盖loginPage()方法中设置的默认值/index.jsp  POST
         */
        .loginProcessingUrl("/security/do/login.html")
        //自定义设置登录用户名和密码参数名称
        .usernameParameter("loginAcct").passwordParameter("userPw")
        //指定登录成功之后去的页面
        .defaultSuccessUrl("/admin/to/main/page.html")
        // 指定退出的请求 退出成功去的页面
        .and()
        .logout().logoutUrl("/security/do/logout.html").logoutSuccessUrl("/admin/to/login/page.html")
        //指定异常处理器
        .and().exceptionHandling()
        // 访问被拒绝以后前忘的页面  accessDeniedHandler：不仅去no_auth.jsp这个页面还可以带消息过去
        //.accessDeniedPage("/to/no/auth/page.html")
        //可自定义消息，路径
        .accessDeniedHandler(new AccessDeniedHandler() {
          @Override
          public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            request.setAttribute("《《《message", "抱歉您无法访问这个资源》》》");
            request.getRequestDispatcher("/WEB-INF/no_auth.jsp").forward(request, response);
          }
        })
        // 开启记住我的功能  在表单中 提供名 remember-me 的请求参数
        // <input type="checkbox" name="remember-me" lay-skin="primary" title="记住我">
        //.and().rememberMe()
        // 将登录信息保存到数据库
        //.tokenRepository(tokenRepository)
        /**
         * 如果CSRF功能没有禁用，那么退出请求必须是POST方式，
         * 如果禁用了CSRF功能则任何请求方式都可以
         */
        .and().csrf().disable();
  }

}
