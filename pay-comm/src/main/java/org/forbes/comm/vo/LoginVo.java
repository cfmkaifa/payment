package org.forbes.comm.vo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***登录返回信息
 * LoginVo概要说明：
 * @author Huanghy
 */
@ApiModel(description="登录返回对象")
@Data
@Component
public class LoginVo implements Serializable  {
	
	private static final long serialVersionUID = 7046487455365110153L;

	/**
	 * token值
	 */
	@ApiModelProperty(value="token值")
	private String token;
	
	/***当前用户信息
	 */
	@ApiModelProperty("当前用户信息")
	private SysUserVo userInfo;
}

