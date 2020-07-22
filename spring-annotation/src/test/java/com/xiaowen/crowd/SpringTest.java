package com.xiaowen.crowd;

import com.xiaowen.crowd.entity.Employee;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>Title: SpringTest</p>
 * Description：
 * date：2020/5/13 20:08
 */
public class SpringTest {

	@Test
	public void springTest(){

		//以前使用new ClassPathXmlApplicationContext("");方式加载XML配置文件

		//现在基于注解配置类创建IOC容器对象
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyAnnotationConfiguration.class);
		Employee emp = applicationContext.getBean(Employee.class);
		System.out.println(emp);

		//关闭
		applicationContext.close();
	}
}
