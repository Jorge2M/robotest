package com.mng.robotest.test.steps.shop.checkout.amazon;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.amazon.PageAmazonIdent;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageAmazonIdentSteps {
	
	@Validation
	public static ChecksTM validateIsPage(Pais pais, Channel channel, DataPedido dataPedido, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece una página con el logo de Amazon",
			PageAmazonIdent.isLogoAmazon(driver), State.Warn);
		checks.add(
			"Aparece los campos para la identificación (usuario/password)",
			PageAmazonIdent.isPageIdent(driver), State.Defect);
		if (channel==Channel.desktop) {
			checks.add(
				"En la página resultante figura el importe total de la compra (" + dataPedido.getImporteTotal() + ")",
				ImporteScreen.isPresentImporteInScreen(dataPedido.getImporteTotal(), pais.getCodigo_pais(), driver), State.Warn);
		}
		
		return checks;
	}
}
