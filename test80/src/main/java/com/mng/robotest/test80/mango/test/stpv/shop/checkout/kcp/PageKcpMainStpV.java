package com.mng.robotest.test80.mango.test.stpv.shop.checkout.kcp;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp.PageKcpMain;

public class PageKcpMainStpV {

	private final PageKcpMain pageKcpMain;
	
	public PageKcpMainStpV(WebDriver driver) {
		pageKcpMain = new PageKcpMain(driver);
	}
	
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
