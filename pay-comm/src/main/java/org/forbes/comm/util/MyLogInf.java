package org.forbes.comm.util;

/***
 * MyLogInf概要说明：
 * @author Huanghy
 */
public abstract interface MyLogInf {

    public abstract void debug(String paramString, Object[] paramArrayOfObject);

    public abstract void info(String paramString, Object[] paramArrayOfObject);

    public abstract void warn(String paramString, Object[] paramArrayOfObject);

    public abstract void error(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject);
}
