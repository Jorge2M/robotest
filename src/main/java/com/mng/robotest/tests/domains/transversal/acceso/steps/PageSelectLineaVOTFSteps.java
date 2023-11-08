package com.mng.robotest.tests.domains.transversal.acceso.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.acceso.pageobjects.PageSelectLineaVOTF;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.testslegacy.beans.AccesoVOTF;
import com.mng.robotest.testslegacy.data.PaisShop;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageSelectLineaVOTFSteps extends StepBase {

	private final PageSelectLineaVOTF pageSelectLineaVOTF = new PageSelectLineaVOTF();
	
	@Validation
	public ChecksTM validateIsPage() { 
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el banner correspondiente a SHE",
			pageSelectLineaVOTF.isBannerPresent(LineaType.SHE), Warn);
		
		checks.add(
			"Aparece el banner correspondiente a MAN",
			pageSelectLineaVOTF.isBannerPresent(LineaType.HE), Warn);
		
		checks.add(
			"Aparece el banner correspondiente a NIÑAS",
			pageSelectLineaVOTF.isBannerPresent(LineaType.NINA), Warn);
		
		checks.add(
			"Aparece el banner correspondiente a NIÑOS",
			pageSelectLineaVOTF.isBannerPresent(LineaType.NINO), Warn);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el #{umMenu}o menu de Mujer y finalmente seleccionar el logo de Mango",
		expected="Aparece la página inicial de SHE")
	public void selectMenuAndLogoMango(int numMenu) {
		pageSelectLineaVOTF.clickBanner(LineaType.SHE);
		pageSelectLineaVOTF.clickMenu(LineaType.SHE, numMenu);
		
		SecCabecera.make().clickLogoMango();
		var accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dataTest.getPais()));
		new SectionBarraSupVOTFSteps().validate(accesoVOTF.getUsuario());
		
		checksDefault();
	}
}
