package com.mng.robotest.domains.buscador.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;

public interface SecSearch {
	
	public void search(String text);
	public void close();
	
	public static SecSearch getNew(Channel channel, AppEcom app) {
		if (channel.isDevice()) {
			if (channel==Channel.tablet) {
				return new SecSearchDeviceShop();
			}
			if (app==AppEcom.outlet) {
				return new SecSearchMobilOutlet();
			}
			return new SecSearchDeviceShop();
		}
		return new SecSearchDesktop();
	}
}
