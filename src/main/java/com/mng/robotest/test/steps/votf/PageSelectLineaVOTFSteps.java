package com.mng.robotest.test.steps.votf;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class PageSelectLineaVOTFSteps extends StepBase {

	private final PageSelectLineaVOTF pageSelectLineaVOTF = new PageSelectLineaVOTF();
	
	@Validation
	public ChecksTM validateIsPage() { 
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece el banner correspondiente a SHE",
			pageSelectLineaVOTF.isBannerPresent(LineaType.she), State.Warn);
		checks.add(
			"Aparece el banner correspondiente a MAN",
			pageSelectLineaVOTF.isBannerPresent(LineaType.he), State.Warn);
		checks.add(
			"Aparece el banner correspondiente a NIÑAS",
			pageSelectLineaVOTF.isBannerPresent(LineaType.nina), State.Warn);
		checks.add(
			"Aparece el banner correspondiente a NIÑOS",
			pageSelectLineaVOTF.isBannerPresent(LineaType.nino), State.Warn);
		return checks;
	}
	
	@Step (
		description="Seleccionar el #{umMenu}o menu de Mujer y finalmente seleccionar el logo de Mango",
		expected="Aparece la página inicial de SHE")
	public void selectMenuAndLogoMango(int numMenu) {
		pageSelectLineaVOTF.clickBanner(LineaType.she);
		pageSelectLineaVOTF.clickMenu(LineaType.she, numMenu);
		
		SecCabecera.getNew(channel, AppEcom.votf).clickLogoMango();
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dataTest.getPais()));
		new SectionBarraSupVOTFSteps().validate(accesoVOTF.getUsuario());
		
		GenericChecks.checkDefault();
	}
}
