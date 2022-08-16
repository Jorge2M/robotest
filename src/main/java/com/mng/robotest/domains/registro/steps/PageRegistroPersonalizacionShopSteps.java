package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroPersonalizacionShop;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;

public class PageRegistroPersonalizacionShopSteps extends StepBase {

	private final PageRegistroPersonalizacionShop pageRegistroPersonalizacion = new PageRegistroPersonalizacionShop();
	
	@Validation (
		description="Aparece la página de personalización del registro (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPageUntil(int maxSeconds) {
		return pageRegistroPersonalizacion.isPageUntil(maxSeconds);
	}
	
	@Step (
		description=
			"Introducir los datos:<br>" + 
			"  - Nombre: <b>#{data.getName()}</b><br>" +
			"  - Código postal: <b>#{data.getPostalCode()}</br>" + 
			"  - Fecha de nacimiento: <b>#{data.getDateOfBirth()}</b><br>" +
			"  - Género: <b>#{data.getGender()}</b><br>" +
			"  - Líneas: <b>#{data.getLineas()}</b>",
		expected=
			"La introducción de datos es correcta")
	public void inputData(DataNewRegister data) {
		pageRegistroPersonalizacion.inputPostalCode(data.getPostalCode());
		pageRegistroPersonalizacion.inputDateOfBirth(data.getDateOfBirth());
		pageRegistroPersonalizacion.inputName(data.getName());
		pageRegistroPersonalizacion.selectGender(data.getGender());
		pageRegistroPersonalizacion.selectLineas(data.getLineas());
	}	
	
	@Step (
		description="Pulsar el botón <b>Guardar</b>",
		expected="Accedemos a la shop como conectados",
		saveImagePage=SaveWhen.Always)
	public void clickGuardar() {
		pageRegistroPersonalizacion.clickGuardar();
		checkWeAreLogged();
	}		
	
	public void checkWeAreLogged() {
		SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
		secMenusUserSteps.checkIsVisibleLinkCerrarSesionUntil(2);
	}	
	
}
