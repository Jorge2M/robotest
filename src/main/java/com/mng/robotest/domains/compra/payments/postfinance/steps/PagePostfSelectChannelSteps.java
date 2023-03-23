package com.mng.robotest.domains.compra.payments.postfinance.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.compra.payments.postfinance.pageobjects.PagePostfSelectChannel;
import com.mng.robotest.domains.compra.payments.postfinance.pageobjects.PagePostfSelectChannel.ChannelPF;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePostfSelectChannelSteps {

	private final PagePostfSelectChannel pageObj = new PagePostfSelectChannel();
	
	@Validation (
		description="Aparece la página de selección del canal (lo esperamos un máximo de #{seconds})",
		level=Warn)
	public boolean checkIsPage(int seconds) {
		return (pageObj.isPage(seconds));
	}
	
	@Step(
		description="Seleccionar el canal <b>#{channelPF.name()}</b>", 
		expected="Aparece la página de introducción del código de seguidad")
	public void selectChannel(ChannelPF channelPF) {
		pageObj.selectChannel(channelPF);
	}
	
}
