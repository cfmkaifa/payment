package org.forbes.config;
import org.forbes.config.interceptor.ExtHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/***
 * ExtWebMvcConfigurer概要说明：自定义装在配置
 * @author Huanghy
 */
@Configuration
public class ExtWebMvcConfigurer implements WebMvcConfigurer {

	
	
	
	
	
	/**增加拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(extHandlerInterceptor());
	}
	
	
	/****
	 * extHandlerInterceptor方法慨述:
	 * @return HandlerInterceptor
	 * @创建人 huanghy
	 * @创建时间 2019年12月7日 上午9:20:41
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	private  HandlerInterceptor   extHandlerInterceptor(){
		return new ExtHandlerInterceptor();
	}
}
