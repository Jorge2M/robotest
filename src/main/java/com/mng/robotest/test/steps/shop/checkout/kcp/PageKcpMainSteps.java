package com.mng.robotest.test.steps.shop.checkout.kcp;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.kcp.PageKcpMain;


public class PageKcpMainSteps extends StepBase {

	private final PageKcpMain pageKcpMain = new PageKcpMain();
	
	@Validation (
		description="Aparece la página inicial de KCP (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean isPage(int maxSeconds) { 
		return (pageKcpMain.isPage(maxSeconds));
	}
	
	@Validation (
		description="Aparece el radiobutton para aceptar los términos y condiciones (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean isPresentTermAndConditions(int maxSeconds) { 
		return (pageKcpMain.isVisibleTermAndConditions(maxSeconds));
	}	
	
}
