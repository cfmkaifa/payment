package org.forbes.config.interceptor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.forbes.comm.constant.UserContext;
import org.forbes.comm.utils.ConvertUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisInterceptor implements Interceptor {


	public static final String CREATE_BY = "createBy";
	public static final String CREATE_BY_TABLE = "create_by";
	public static final String CREATE_TIME_TABLE = "create_time";
	public static final String CREATE_TIME = "createTime";
	/**
	 * 更新用户登录名称
	 */
	public static final String UPDATE_BY = "updateBy";
	/**
	 * 更新日期时间
	 */
	public static final String UPDATE_TIME = "updateTime";
	/***默认用户
	 */
	private static final String DEFAULT_SYS_USER = "system";

	/***
	 *
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		String sqlId = mappedStatement.getId();
		log.debug("------sqlId------" + sqlId);
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		Object parameter = invocation.getArgs()[1];
		log.debug("------sqlCommandType------" + sqlCommandType);
		if (parameter == null) {
			return invocation.proceed();
		}
		List<Field> fields = this.receFields(parameter);
		if (SqlCommandType.INSERT == sqlCommandType) {
			this.insertInvocation(parameter, fields);
		}
		if (SqlCommandType.UPDATE == sqlCommandType) {
			this.upInvocation(parameter, fields);
		}
		return invocation.proceed();
	}


	/***
	 * receFields方法慨述:
	 * @param obj
	 * @return List<Field>
	 * @创建人 huanghy
	 * @创建时间 2019年11月26日 上午8:45:10
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	private List<Field> receFields(Object obj){
		Field[] fields = (Field[]) ArrayUtils.addAll(obj.getClass().getDeclaredFields(), obj.getClass().getSuperclass().getDeclaredFields());
		return Arrays.asList(fields);
	}


	/***
	 * insertInvocation方法慨述:
	 * @param parameter
	 * @param fields void
	 * @创建人 huanghy
	 * @创建时间 2019年11月26日 下午3:53:18
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	private void insertInvocation(Object parameter,List<Field> fields){
		fields.stream().filter(fieldt -> CREATE_BY.equals(fieldt.getName())
				|| CREATE_TIME.equals(fieldt.getName())).forEach(tfieldt -> {
			try {
				tfieldt.setAccessible(true);
				Object fieldVal = tfieldt.get(parameter);
				tfieldt.setAccessible(false);
				if (CREATE_BY.equals(tfieldt.getName())) {
					if(ConvertUtils.isEmpty(fieldVal)){
						tfieldt.setAccessible(true);
						if(ConvertUtils.isNotEmpty(UserContext.getSysUser())){
							tfieldt.set(parameter, UserContext.getSysUser().getRealname());
						} else {
							tfieldt.set(parameter, DEFAULT_SYS_USER);
						}
						tfieldt.setAccessible(false);
					}
				}
				if (CREATE_TIME.equals(tfieldt.getName())) {
					if(ConvertUtils.isEmpty(fieldVal)){
						tfieldt.setAccessible(true);
						tfieldt.set(parameter, new Date());
						tfieldt.setAccessible(false);
					}
				}
			}catch(Exception e){
				log.trace("设置当前人异常", e);
			}
		});
	}

	/***
	 * upInvocation方法慨述:
	 * @param parameter
	 * @param fields void
	 * @创建人 huanghy
	 * @创建时间 2019年11月26日 下午3:52:10
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	private void upInvocation(Object parameter,List<Field> fields){
		fields.stream().filter(fieldt -> UPDATE_BY.equals(fieldt.getName())
				|| UPDATE_TIME.equals(fieldt.getName())).forEach(tfieldt -> {
			try{
				tfieldt.setAccessible(true);
				Object fieldVal = tfieldt.get(parameter);
				tfieldt.setAccessible(false);
				if (UPDATE_BY.equals(tfieldt.getName())) {
					if(ConvertUtils.isEmpty(fieldVal)){
						tfieldt.setAccessible(true);
						if(ConvertUtils.isNotEmpty(UserContext.getSysUser())){
							tfieldt.set(parameter, UserContext.getSysUser().getRealname());
						} else {
							tfieldt.set(parameter, DEFAULT_SYS_USER);
						}
						tfieldt.setAccessible(false);
					}
				}
				if (UPDATE_TIME.equals(tfieldt.getName())) {
					if(ConvertUtils.isEmpty(fieldVal)){
						tfieldt.setAccessible(true);
						tfieldt.set(parameter, new Date());
						tfieldt.setAccessible(false);
					}
				}
			}catch(Exception e){
				log.trace("设置当前人异常", e);
			}
		});
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
	}

}
