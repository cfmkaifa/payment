package org.forbes.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.forbes.comm.constant.CommonConstant;
import org.forbes.comm.constant.UserContext;
import org.forbes.comm.utils.ConvertUtils;
import org.forbes.comm.utils.JwtUtil;
import org.forbes.comm.vo.SysUserVo;
import org.forbes.config.cache.UserCache;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;
/***
 * ExtHandlerInterceptor概要说明：扩展拦截
 * @author Huanghy
 */
@Slf4j
public class ExtHandlerInterceptor implements HandlerInterceptor {

	
	/***设置当前登录信息
	 */
	public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse response, Object handler)
			throws Exception {
		String token = httpRequest.getHeader(CommonConstant.X_ACCESS_TOKEN);
		if(ConvertUtils.isNotEmpty(token)){
			String userName = JwtUtil.getUsername(token);
			SysUserVo sysUserVo = UserCache.getSysUser(userName);
			if(ConvertUtils.isNotEmpty(sysUserVo)){				
				UserContext.setSysUser(sysUserVo);
			}
		}
		return true;
	}
	
	
	
	/***执行完成
	 */
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
    		throws Exception {
        UserContext.clear();
    }
}
