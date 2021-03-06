package org.forbes.config.channel.wechat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
/***
 * WxPayProperties概要说明：微信配置相关信息
 * @author Huanghy
 */
@RefreshScope
@Component
public class WxPayProperties {

	@Value("${cert.root.path}")
	private String certRootPath;

	@Value("${wx.notify_url}")
	private String notifyUrl;

	public String getCertRootPath() {
		return certRootPath;
	}

	public void setCertRootPath(String certRootPath) {
		this.certRootPath = certRootPath;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}
