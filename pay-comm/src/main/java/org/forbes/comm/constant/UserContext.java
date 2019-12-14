package org.forbes.comm.constant;

import org.forbes.comm.vo.SysUserVo;

/***
 * UserContext概要说明：用户上下文
 * @author Huanghy
 */
public final class UserContext {

	/***线程独享用户信息
	 */
    private static final ThreadLocal<SysUserVo> THREAD_LOCAL_USER_NAME = new ThreadLocal<SysUserVo>();
    
    
    /***
     * getUserName方法慨述:当前用户信息
     * @return SysUserVo
     * @创建人 huanghy
     * @创建时间 2019年12月6日 上午11:36:43
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public  static SysUserVo getSysUser(){
    	return THREAD_LOCAL_USER_NAME.get();
    }
    
    /***
     * setUserName方法慨述:设置用户名
     * @param SysUserVo void
     * @创建人 huanghy
     * @创建时间 2019年12月6日 上午11:37:26
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public  static void setSysUser(SysUserVo sysUserVo){
    	THREAD_LOCAL_USER_NAME.set(sysUserVo);
    }
    
    
    /***
     * clear方法慨述:清除当前登录信息
     *  void
     * @创建人 huanghy
     * @创建时间 2019年12月6日 上午11:38:08
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public  static void clear(){
    	THREAD_LOCAL_USER_NAME.remove();
    }
}
