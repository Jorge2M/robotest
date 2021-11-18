package com.mng.robotest.test80.mango.test.stpv.votf;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.AccesoVOTF;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class PageSelectLineaVOTFStpV {

	@Validation
	public static ChecksTM validateIsPage(WebDriver driver) { 
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece el banner correspondiente a SHE",
			PageSelectLineaVOTF.isBannerPresent(LineaType.she, driver), State.Warn);
		validations.add(
			"Aparece el banner correspondiente a MAN",
			PageSelectLineaVOTF.isBannerPresent(LineaType.he, driver), State.Warn);
		validations.add(
			"Aparece el banner correspondiente a NIÑAS",
			PageSelectLineaVOTF.isBannerPresent(LineaType.nina, driver), State.Warn);
		validations.add(
			"Aparece el banner correspondiente a NIÑOS",
			PageSelectLineaVOTF.isBannerPresent(LineaType.nino, driver), State.Warn);
		return validations;
	}
	
	@Step (
		description="Seleccionar el #{umMenu}o menu de Mujer y finalmente seleccionar el logo de Mango",
		expected="Aparece la página inicial de SHE")
	public static void selectMenuAndLogoMango(int numMenu, DataCtxShop dCtxSh, WebDriver driver) {
		PageSelectLineaVOTF.clickBanner(LineaType.she, driver);
		PageSelectLineaVOTF.clickMenu(LineaType.she, numMenu, driver);
		SecCabecera.getNew(Channel.desktop, AppEcom.votf, driver).clickLogoMango();
		
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dCtxSh.pais));
		SectionBarraSupVOTFStpV.validate(accesoVOTF.getUsuario(), driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.Analitica)).checks(driver);
	}
}
