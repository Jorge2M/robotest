package com.mng.robotest.test.steps.votf;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class PageSelectLineaVOTFSteps {

	private final PageSelectLineaVOTF pageSelectLineaVOTF;
	
	public PageSelectLineaVOTFSteps(WebDriver driver) {
		pageSelectLineaVOTF = new PageSelectLineaVOTF(driver);
	}
	
	@Validation
	public ChecksTM validateIsPage() { 
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece el banner correspondiente a SHE",
			pageSelectLineaVOTF.isBannerPresent(LineaType.she), State.Warn);
		validations.add(
			"Aparece el banner correspondiente a MAN",
			pageSelectLineaVOTF.isBannerPresent(LineaType.he), State.Warn);
		validations.add(
			"Aparece el banner correspondiente a NIÑAS",
			pageSelectLineaVOTF.isBannerPresent(LineaType.nina), State.Warn);
		validations.add(
			"Aparece el banner correspondiente a NIÑOS",
			pageSelectLineaVOTF.isBannerPresent(LineaType.nino), State.Warn);
		return validations;
	}
	
	@Step (
		description="Seleccionar el #{umMenu}o menu de Mujer y finalmente seleccionar el logo de Mango",
		expected="Aparece la página inicial de SHE")
	public void selectMenuAndLogoMango(int numMenu, DataCtxShop dCtxSh) {
		pageSelectLineaVOTF.clickBanner(LineaType.she);
		pageSelectLineaVOTF.clickMenu(LineaType.she, numMenu);
		
		WebDriver driver = pageSelectLineaVOTF.driver;
		SecCabecera.getNew(Channel.desktop, AppEcom.votf, driver).clickLogoMango();
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dCtxSh.pais));
		new SectionBarraSupVOTFSteps(driver).validate(accesoVOTF.getUsuario());
		
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}
}
