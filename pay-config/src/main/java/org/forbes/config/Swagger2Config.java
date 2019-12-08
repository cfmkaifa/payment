package org.forbes.config;

import java.util.ArrayList;
import java.util.List;

import org.forbes.comm.constant.CommonConstant;
import org.forbes.comm.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
@Configuration
@Order(0)
public class Swagger2Config implements WebMvcConfigurer {

	@Value("${spring.application.name}")
	private String applicationName;
	@Value("${agency_port}")
	private String AGENCY_PORT;
	private static final String APPLICATIO_NNAME_CODE = "applicationName";
	private static final String AGENCY_PORT_CODE = "agencyPort";
	
	/**
	 *
	 * 显示swagger-ui.html文档展示页，还必须注入swagger资源：
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("docs.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 *
	 * @return Docket
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(false)
				.pathMapping("/")
				.apiInfo(apiInfo())
				.select()
				//加了ApiOperation注解的类，才生成接口文档
	            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(setHeaderToken());
	}

	/**
	 * JWT token
	 * @return
	 */
	private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(CommonConstant.X_ACCESS_TOKEN).description("token")
        .modelRef(new ModelRef("string")).parameterType("header")
        .required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

	/**
	 * api文档的详细信息函数,注意这里的注解引用的是哪个
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private ApiInfo apiInfo() {
		List<VendorExtension> extensions = Lists.newArrayList();
		if(ConvertUtils.isNotEmpty(AGENCY_PORT)){
			VendorExtension<String> extension = new StringVendorExtension(AGENCY_PORT_CODE,AGENCY_PORT);
			extensions.add(extension);
		}
		VendorExtension<String> extension = new StringVendorExtension(APPLICATIO_NNAME_CODE,applicationName);
		extensions.add(extension);
		return new ApiInfoBuilder()
				// //大标题
				.title("福布斯后台服务API接口文档")
				// 版本号
				.version("1.0")
				.extensions(extensions)
				// 描述
				.description("restful 风格接口")
				.build();
	}

}
