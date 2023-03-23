package com.mng.robotest.domains.compra.payments.kcp.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.kcp.pageobjects.PageKcpMain;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageKcpMainSteps extends StepBase {

	private final PageKcpMain pageKcpMain = new PageKcpMain();
	
	@Validation (
		description="Aparece la página inicial de KCP (la esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean isPage(int seconds) { 
		return (pageKcpMain.isPage(seconds));
	}
	
	@Validation (
		description="Aparece el radiobutton para aceptar los términos y condiciones (lo esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean isPresentTermAndConditions(int seconds) { 
		return (pageKcpMain.isVisibleTermAndConditions(seconds));
	}	
	
}
