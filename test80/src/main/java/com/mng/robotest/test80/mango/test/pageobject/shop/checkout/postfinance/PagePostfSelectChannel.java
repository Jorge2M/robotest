package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PagePostfSelectChannel extends PageObjTM {

    public enum ChannelPF {
    	App("link-pfc"),
    	Card("link-classic");
    	
    	private final String id;
    	private ChannelPF(String id) {
    		this.id = id;
    	}
    	public String getId() {
    		return id;
    	}
    }
    
    public PagePostfSelectChannel(WebDriver driver) {
    	super(driver);
    }
    
    public boolean isPage(int maxSeconds) {
    	return state(State.Visible, By.id(ChannelPF.Card.getId())).check();
    }
    
    public void selectChannel(ChannelPF channelPF) {
    	click(By.id(channelPF.getId())).exec();
    }
}