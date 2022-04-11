package com.mng.robotest.test.stpv.shop.checkout.eps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.pageobject.shop.checkout.eps.PageEpsSelBanco;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageEpsSelBancoStpV {

	@Validation
	public static ChecksTM validateIsPage(String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Figura el icono correspondiente al pago <b>EPS</b>",
			PageEpsSelBanco.isPresentIconoEps(driver), State.Warn);
		
		State stateVal = State.Warn;
		if (channel.isDevice()) {
			stateVal=State.Info;
		}
		validations.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
		validations.add(
			"Aparece el logo del banco seleccionado",
			PageEpsSelBanco.isVisibleIconoBanco(driver), State.Warn);
		return validations;
	}
}
	