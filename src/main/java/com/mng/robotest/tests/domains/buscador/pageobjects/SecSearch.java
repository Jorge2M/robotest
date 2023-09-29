package com.mng.robotest.tests.domains.buscador.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public interface SecSearch {
	
	public void search(String text);
	public void close();
	
	public static SecSearch getNew(Channel channel) {
		if (channel.isDevice()) {
			if (channel==Channel.tablet) {
				return new SecSearchDevice();
			}
			return new SecSearchDevice();
		}
		return new SecSearchDesktop();
	}
}
