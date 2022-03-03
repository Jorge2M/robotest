package com.mng.robotest.test.pageobject.shop.checkout.postfinance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PagePostfSelectChannel extends PageObjTM {

	public enum ChannelPF {
		App("opfc"),
		Card("classic");
		
		private final String id;
		private ChannelPF(String id) {
			this.id = id;
		}
		public String getXPath() {
			return "//button[@onclick='" + id + "()']";
		}
	}
	
	public PagePostfSelectChannel(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage(int maxSeconds) {
		return state(State.Visible, By.xpath(ChannelPF.Card.getXPath())).check();
	}
	
	public void selectChannel(ChannelPF channelPF) {
		click(By.xpath(channelPF.getXPath())).exec();
	}
}