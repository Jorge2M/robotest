package com.mng.robotest.tests.domains.compra.payments.kcp.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.kcp.pageobjects.PageKcpMain;

public class PageKcpMainSteps extends StepBase {

	private final PageKcpMain pgKcpMain = new PageKcpMain();
	
	@Validation (
		description="Aparece la página inicial de KCP " + SECONDS_WAIT)
	public boolean isPage(int seconds) { 
		return pgKcpMain.isPage(seconds);
	}
	
	@Validation (
		description="Aparece el radiobutton para aceptar los términos y condiciones " + SECONDS_WAIT)
	public boolean isPresentTermAndConditions(int seconds) { 
		return pgKcpMain.isVisibleTermAndConditions(seconds);
	}	
	
}
