package com.mng.robotest.test.steps.manto;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;

public class DataMantoAccess {
	private String urlManto;
	private String userManto;
	private String passManto; 
	private Channel channel;
	private AppEcom appE;
	
	public String getUrlManto() {
		return urlManto;
	}
	public void setUrlManto(String urlManto) {
		this.urlManto = urlManto;
	}
	public String getUserManto() {
		return userManto;
	}
	public void setUserManto(String userManto) {
		this.userManto = userManto;
	}
	public String getPassManto() {
		return passManto;
	}
	public void setPassManto(String passManto) {
		this.passManto = passManto;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public AppEcom getAppE() {
		return appE;
	}
	public void setAppE(AppEcom appE) {
		this.appE = appE;
	} 
}
