package com.mng.robotest.test.stpv.shop.checkout.paymaya;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.paymaya.PageInitPaymaya;

public class PageInitPaymayaStpV {

	private PageInitPaymaya pageInitPaymaya;
	private WebDriver driver;
	
	public PageInitPaymayaStpV(WebDriver driver) {
		pageInitPaymaya = new PageInitPaymaya(driver);
		this.driver = driver;
	}
	
	@Validation
	public ChecksTM checkPage() {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la página inicial de PayMaya",
			pageInitPaymaya.isPage(), State.Warn); 
	 	validations.add(
			"Aparece la imagen del QR",
			pageInitPaymaya.isQrVisible(), State.Defect);
	 	return validations;
	}
	
	@Step(
		description="Seleccionamos el botón PayMaya para el checkout express",
		expected="Aparece la página de identificación de PayMaya")
	public PageIdentPaymayaStpV clickPaymayaButton() throws Exception {
		pageInitPaymaya.clickButtonPayMaya();
		PageIdentPaymayaStpV pageIdentPaymayaStpV = new PageIdentPaymayaStpV(driver);
		pageIdentPaymayaStpV.checkPage();
		return pageIdentPaymayaStpV;
	}
	
}
