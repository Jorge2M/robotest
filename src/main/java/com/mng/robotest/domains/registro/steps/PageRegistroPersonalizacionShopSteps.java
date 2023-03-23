package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroPersonalizacionShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.steps.shop.AccesoSteps;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRegistroPersonalizacionShopSteps extends StepBase {

	private final PageRegistroPersonalizacionShop pageRegistroPersonalizacion = new PageRegistroPersonalizacionShop();
	
	@Validation (
		description="Aparece la página de personalización del registro (la esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean checkIsPageUntil(int seconds) {
		return pageRegistroPersonalizacion.isPageUntil(seconds);
	}
	
	public void inputData(DataNewRegister data) {
		inputDataWithouBirthDate(data);
		if (PaisShop.getPais(dataTest.getPais())!=PaisShop.COREA_DEL_SUR) {
			inputBirthDate(data.getDateOfBirth());
		}
	}
	
	@Step (
		description=
			"Introducir los datos:<br>" + 
			"  - Nombre: <b>#{data.getName()}</b><br>" +
			"  - Código postal: <b>#{data.getPostalCode()}</br>" + 
			"  - Género: <b>#{data.getGender()}</b><br>" +
			"  - Líneas: <b>#{data.getLineas()}</b>",
		expected=
			"La introducción de datos es correcta")
	private void inputDataWithouBirthDate(DataNewRegister data) {
		pageRegistroPersonalizacion.inputPostalCode(data.getPostalCode());
		pageRegistroPersonalizacion.inputName(data.getName());
		pageRegistroPersonalizacion.selectGender(data.getGender());
		pageRegistroPersonalizacion.selectLineas(data.getLineas());
	}		
	
	@Step (
		description="Introducir la Fecha de nacimiento: <b>#{birthDate}</b><br>",
		expected="La introducción de datos es correcta")
	private void inputBirthDate(String birthDate) {
		pageRegistroPersonalizacion.inputDateOfBirth(birthDate);
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
//		SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
//		secMenusUserSteps.checkIsVisibleLinkCerrarSesionUntil(2);
		new AccesoSteps().checkIsLogged(7);
	}	
	
}
