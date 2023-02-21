package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisDirecciones;
import com.mng.robotest.domains.transversal.StepBase;

public class PageMisDireccionesSteps extends StepBase {

	private final PageMisDirecciones pageMisDirecciones = new PageMisDirecciones();
	
	@Validation(
		description="Aparece la página de \"Mis direcciones\" (la esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage (int seconds) {
		return pageMisDirecciones.isPage(seconds);
	}

	@Validation(
		description="Figura el código postal <b>#{postalCode}</b>",
		level=State.Defect)
	public boolean checkPostalCode(String postalCode) {
		pageMisDirecciones.clickLinkEditar();
		boolean check = postalCode.compareTo(pageMisDirecciones.getCodigoPostal())==0;
		driver.navigate().back();
		return check;
	}
	
}
