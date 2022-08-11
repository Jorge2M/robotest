package com.mng.robotest.test.steps.shop.checkout.pasarelaotras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.utils.ImporteScreen;


public class PagePasarelaOtrasSteps {
	
	@Validation
	public static ChecksTM validateIsPage(String importeTotal, Pais pais, Channel channel, AppEcom app, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		if (channel==Channel.desktop) {
		   	checks.add(
				"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
				ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), driver), State.Warn);
		}
	   	checks.add(
			"No se trata de la página de precompra (no aparece los logos de formas de pago)",
			new PageCheckoutWrapper(channel, app).isPresentMetodosPago(), State.Defect);
	   	
	   	return checks;
	}
}
