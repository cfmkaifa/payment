package org.forbes.comm.vo;

import java.io.Serializable;

import org.forbes.comm.constant.CommonConstant;

import lombok.Data;
/***
 * Result概要说明：统一返回错误
 * @author Huanghy
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";
	/******登录相关提示信息****/
	public static final  String LOGIN_MSG = "登录成功";
	public static final  String LOGIN_PASS_ERROR_MSG = "用户名或密码错误";
	public static final  String LOGIN_NOT_USER_ERROR_MSG = "该用户不存在";
	public static final  String LOGOUT_SUCCESS_MSG = "退出登录成功！";

	/******修改用户状态提示信息****/
	public static final  String UPDATE_STATUS_MSG = "状态修改成功";
	public static final  String UPDATE_STATUS_ERROR_MSG = "状态修改失败";

	/******添加用户提示信息****/
	public static final  String ADD_USER_MSG="添加用户成功";
	public static final  String ADD_USER_ERROR_MSG="添加用户失败";

	/******修改用户提示信息****/
	public static final  String UPDATE_USER_MSG="修改用户成功";
	public static final  String UPDATE_USER_ERROR_MSG="修改用户失败";

	/******用户详情****/
	public static final  String DETAIL_USER_MSG="查询用户详情成功";
	public static final  String DETAIL_USER_ERROR_MSG="查询用户详情失败";
	public static final  String DETAIL_USER_EMPTY_MSG="未查询到该用户";

	/******查询角色****/
	public static final  String ROLE_MSG="查询用户对应角色成功";
	public static final  String ROLE_ERROR_MSG="查询用户所缺角色失败";
	public static final  String NOT_ROLE_MSG="查询用户所缺角色成功";
	public static final  String NOT_ROLE_ERROR_MSG="查询用户所缺对应角色失败";
	public static final  String ROLE_EMPTY_MSG="未查询到该用户的角色";

	/******添加角色信息****/
	public static final  String ADD_ROLE_MSG="添加角色成功";
	public static final  String ADD_ROLE_ERROR_MSG="添加角色失败";

	/******修改角色信息****/
	public static final  String UPDATE_ROLE_MSG="角色修改成功";
	public static final  String UPDATE_ROLE_ERROR_MSG="角色修改失败";

	/******删除角色信息****/
	public static final  String DELETE_ROLE_MSG="角色删除成功";
	public static final  String DELETE_ROLE_ERROR_MSG="角色删除失败";

	/******查询所有角色信息****/
	public static final  String ROLE_LIST_MSG="查询所有角色成功";
	public static final  String ROLE_LIST_ERROR_MSG="查询所有角色失败";

	/******查询权限信息****/
	public static final  String PERMISSIONS_MSG = "查询所有权限成功";
	public static final  String PERMISSIONS_NOT_ERROR_MSG = "查询所有权限失败";

	public static final  String PERMISSION_BY_ID_MSG = "通过id查询权限内容成功";
	public static final  String PERMISSION_BY_ID_NOT_ERROR_MSG = "通过id查询权限内容失败";

	public static final  String PERMISSION_MSG = "通过角色id查询角色所有权限成功";
	public static final  String PERMISSION_NOT_ERROR_MSG = "通过角色id查询角色所有权限失败";

	public static final  String ALL_PERMISSION_MSG = "查询所有角色对应权限成功";
	public static final  String ALL_PERMISSION_NOT_ERROR_MSG = "查询所有角色对应权限失败";

	public static final  String IN_PERMISSION_MSG = "查询角色已拥有权限成功";
	public static final  String NOT_IN_PERMISSION_NOT_ERROR_MSG = "查询角色未拥有权限失败";
	/******校验权限信息****/
	public static final  String NULL_PERMISSION_MSG = "权限编码为空";
	public static final  String EXISTS_PERMISSION_MSG = "权限编码已存在";
	public static final  String AVAILABLE_PERMISSION_MSG = "权限编码可用";

	/******添加权限信息****/
	public static final  String ADD_PERMISSION_MSG = "添加权限成功";
	public static final  String ADD_PERMISSION_NOT_ERROR_MSG = "添加权限失败";
	public static final  String ADD_SAME_PERMISSION_NOT_ERROR_MSG = "权限编码已存在";
	/******给角色添加权限信息****/
	public static final  String ADD_ROLE_PERMISSION_MSG = "添加角色权限成功";
	public static final  String ADD_ROLE_PERMISSION_NOT_ERROR_MSG = "添加角色权限失败";
	/******修改权限内容信息****/
	public static final  String UPDATE_PERMISSION_MSG = "修改权限内容成功";
	public static final  String UPDATE_SAME_PERMISSION_MSG = "权限编码已存在";
	public static final  String UPDATE_PERMISSION_NOT_ERROR_MSG = "修改权限内容失败";
	/******删除权限内容信息****/
	public static final  String DELETE_PERMISSION_MSG = "删除权限成功";
	public static final  String DELETE_PERMISSION_NOT_ERROR_MSG = "删除权限失败";
	public static final  String DELETE_PERMISSION_NOT_NULL_ERROR_MSG = "该权限不存在";
	public static final  String DELETE_IF_PERMISSION_NOT_ERROR_MSG = "请先移除和该权限相关的角色权限信息";
	/******修改角色权限信息****/
	public static final  String UPDATE_ROLE_PERMISSION_MSG = "修改角色权限成功";
	public static final  String UPDATE_ROLE_PERMISSION_NOT_ERROR_MSG = "修改角色权限失败";
	/******删除角色权限信息****/
	public static final  String DELETE_ROLE_PERMISSION_MSG = "删除角色权限成功";
	public static final  String DELETE_ROLE_PERMISSION_NOT_ERROR_MSG = "删除角色权限失败";

	/******用户角色中间表添加****/
	public static final  String ADD_USER_AND_ROLE_MSG = "用户角色中间表添加成功";
	public static final  String ADD_USER_AND_ROLE_ERROR_MSG = "用户角色中间表添加失败";

	/******用户角色中间表删除****/
	public static final  String DELETE_USER_AND_ROLE_MSG = "用户角色中间表删除成功";
	public static final  String DELETE_USER_AND_ROLE_ERROR_MSG = "用户角色中间表删除失败";

	/*****用户角色中间表查询*****/
	public static final  String SELECT_USER_AND_ROLE_MSG = "用户角色中间表查询成功";
	public static final  String SELECT_USER_AND_ROLE_ERROR_MSG = "用户角色中间表查询失败";

	public static final  String SELECT_USER_NotRole_MSG = "用户所缺角色中间表查询成功";
	public static final  String SELECT_USER_NotRole_ERROR_MSG = "用户所缺角色中间表查询失败";

	/*****多条件查询用户集合*****/
	public static final  String SELECT_LIST_USER_AND_ROLE_MSG = "多条件查询用户成功";
	public static final  String SELECT_LIST_USER_AND_ROLE_ERROR_MSG = "多条件查询用户失败";


	/*****公共操作结果信息*****/
	public static final  String COMM_ACTION_MSG = "操作成功";
	public static final  String COMM_ACTION_ERROR_MSG = "操作失败";

	/*****编辑用户信息*****/
	public static final  String EDITOR_USER = "查询用户信息成功";
	public static final  String EDITOR_USER_ERROR = "查询用户信息失败";

	/*****判断用户是否唯一*****/
	public static final  String UNIQUE_USER = "用户账号已存在";
	public static final  String UNIQUE_ERROR_USER="用户账号不存在";

	/*****用户存在信息*****/
	public static final  String EXIST_USER = "用户已存在";
	public static final  String EXIST_ERROR_USER="用户不存在";

	/***修改***/
	public static final  String    UP_SUCCESS_MSG = "修改成功！";

	/**
	 * 返回代码
	 */
	private Integer code = CommonConstant.SC_OK_200;
	private String  bizCode = "0000";
	
	/**
	 * 返回数据对象 data
	 */
	private T result;

	public Result() {
		
	}
	
	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	public void error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
	}

	public void success(String message) {
		this.message = message;
		this.code = CommonConstant.SC_OK_200;
		this.success = true;
	}
	
	public static Result<Object> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}
	
	public static Result<Object> error(int code, String msg) {
		Result<Object> r = new Result<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}
	
	/***
	 * error方法慨述:
	 * @param bizCode
	 * @param msg
	 * @return Result<Object>
	 * @创建人 huanghy
	 * @创建时间 2019年12月7日 下午4:07:04
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public  void error(String bizCode, String msg) {
		this.bizCode = bizCode;
		this.message = msg;
		this.success = false;
	}
	
	public static Result<Object> ok(String msg) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(msg);
		return r;
	}
	
	public static Result<Object> ok(Object data) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}
}

