package org.forbes.config.cache;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.forbes.comm.constant.CommonConstant;
import org.forbes.comm.utils.SpringContextUtils;
import org.forbes.comm.vo.SysUserVo;
import org.forbes.config.RedisUtil;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
/***
 * UserCache概要说明：用户缓存
 * @author Huanghy
 */
@Slf4j
public class UserCache {

	/***用户缓存
	 */
	private static final Map<String,SysUserVo> USER_CACHE_MAP = Maps.newConcurrentMap();
	private static final ReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
	private static final long LOCK_TIME = 3;
	
	
	/***
	 * existsUser方法慨述:判断用户是否存在
	 * @param userName
	 * @return boolean
	 * @创建人 huanghy
	 * @创建时间 2019年12月7日 上午10:16:55
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public static boolean existsUser(String userName){
		return USER_CACHE_MAP.containsKey(userName);
	}
	
	
	/***
	 * setUserCashe方法慨述:
	 * @param userName
	 * @param SysUser void
	 * @创建人 huanghy
	 * @创建时间 2019年12月7日 上午9:57:52
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public static void setUserCashe(String userName,SysUserVo sysUser){
		if(!USER_CACHE_MAP.containsKey(userName)){
			boolean isLock = false;
			try {
				READ_WRITE_LOCK.writeLock().tryLock(LOCK_TIME, TimeUnit.SECONDS);
				USER_CACHE_MAP.put(userName, sysUser);
			} catch(InterruptedException e){
				log.trace("缓存获取用户异常", e);
			} finally {
				if(isLock){
					READ_WRITE_LOCK.writeLock().unlock();
				}
			}
		}
	}
	
	/***
	 * getSysUser方法慨述:获取用户
	 * @param userName
	 * @return SysUserVo
	 * @创建人 huanghy
	 * @创建时间 2019年12月7日 上午10:04:54
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	public static SysUserVo getSysUser(String userName){
		if(USER_CACHE_MAP.containsKey(userName)){
			return USER_CACHE_MAP.get(userName);
		}/**从redis获取**/ 
		else {
			RedisUtil redisUtil = SpringContextUtils.getBean(RedisUtil.class);
			String key = String.format(CommonConstant.PREFIX_USER, userName);
			SysUserVo sysUserVo = (SysUserVo) redisUtil.get(key);
			setUserCashe(userName,sysUserVo);
			return sysUserVo;
		}
	}
}
