package com.mng.robotest.test.steps.shop.checkout.postfinance;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel.ChannelPF;

public class PagePostfSelectChannelSteps {

	private final PagePostfSelectChannel pageObj;
	
	public PagePostfSelectChannelSteps(WebDriver driver) {
		pageObj = new PagePostfSelectChannel(driver);
	}
	
	@Validation (
		description="Aparece la página de selección del canal (lo esperamos un máximo de #{maxSeconds})",
		level=State.Warn)
	public boolean checkIsPage(int maxSeconds) {
		return (pageObj.isPage(maxSeconds));
	}
	
	@Step(
		description="Seleccionar el canal <b>#{channelPF.name()}</b>", 
		expected="Aparece la página de introducción del código de seguidad")
	public void selectChannel(ChannelPF channelPF) {
		pageObj.selectChannel(channelPF);
	}
	
}
