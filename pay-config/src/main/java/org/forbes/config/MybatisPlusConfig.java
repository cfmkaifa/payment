package org.forbes.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/***
 * MybatisPlusConfig概要说明：
 * @author Huanghy
 */
@Configuration
@MapperScan(value={"org.forbes.**.mapper*"})
public class MybatisPlusConfig {

	
	/***
	 * paginationInterceptor方法慨述:分页插件
	 * @return PaginationInterceptor
	 * @创建人 huanghy
	 * @创建时间 2019年11月26日 上午8:09:15
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	@Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
