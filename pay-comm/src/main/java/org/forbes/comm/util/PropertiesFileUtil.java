package org.forbes.comm.util;

import java.util.ResourceBundle;
/***
 * PropertiesFileUtil概要说明：属性文件工具类
 * @author Huanghy
 */
public class PropertiesFileUtil {

	private ResourceBundle rb = null;

	public PropertiesFileUtil(String bundleFile) {
		rb = ResourceBundle.getBundle(bundleFile);
	}

	public String getValue(String key) {
		return rb.getString(key);
	}

	public static void main(String[] args) {


	}
}
