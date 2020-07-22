package com.xiaowen.crowd;

import com.xiaowen.crowd.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: MyAnnotationConfiguration</p>
 * Description：
 * date：2020/5/13 20:04
 */
@Configuration
public class MyAnnotationConfiguration {

	@Bean
	public Employee getEmployee(){
		return new Employee();
	}
}
