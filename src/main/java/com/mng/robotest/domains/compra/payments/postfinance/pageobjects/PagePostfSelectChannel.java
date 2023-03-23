package com.mng.robotest.domains.compra.payments.postfinance.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePostfSelectChannel extends PageBase {

	public enum ChannelPF {
		APP("opfc"),
		CARD("classic");
		
		private final String id;
		private ChannelPF(String id) {
			this.id = id;
		}
		public String getXPath() {
			return "//button[@onclick='" + id + "()']";
		}
	}
	
	public boolean isPage(int seconds) {
		return state(Visible, ChannelPF.CARD.getXPath()).wait(seconds).check();
	}
	
	public void selectChannel(ChannelPF channelPF) {
		click(channelPF.getXPath()).exec();
	}
}