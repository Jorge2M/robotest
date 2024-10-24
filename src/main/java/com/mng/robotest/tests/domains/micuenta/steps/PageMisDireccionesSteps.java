package com.mng.robotest.tests.domains.micuenta.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMisDirecciones;

public class PageMisDireccionesSteps extends StepBase {

	private final PageMisDirecciones pageMisDirecciones = new PageMisDirecciones();
	
	@Validation(
		description="Aparece la página de \"Mis direcciones\" " + SECONDS_WAIT)
	public boolean checkIsPage (int seconds) {
		return pageMisDirecciones.isPage(seconds);
	}

	public void checkData() {
		clickLinkEditarIfExists();
		checkPostalCodeVoid();
		driver.navigate().back();
	}
	public void checkData(Map<String,String> dataRegister) {
		clickLinkEditarIfExists();
		checkIsData(dataRegister);
		back();
	}

	private void clickLinkEditarIfExists() {
		if (pageMisDirecciones.isLinkEditarVisible(1)) {
			clickLinkEditar();
		}
	}
	
	@Step(
		description = "Seleccionar el link <b>Editar</b>",
		expected = "Aparece el formulario con los datos del usuario")
	private void clickLinkEditar() {
		pageMisDirecciones.clickLinkEditar();
		checkIsFormularioUsuario(5);
	}
	
	@Validation(
		description="Aparece el formulario con los datos del usuario " + SECONDS_WAIT)
	public boolean checkIsFormularioUsuario(int seconds) {
		return pageMisDirecciones.isFormularioUsuario(seconds);
	}	
	
	@Validation(description="El código postal está vacío")
	private boolean checkPostalCodeVoid() {
		return isPostalCode("");
	}
	
	@Validation
	private ChecksTM checkIsData(Map<String,String> dataOldRegister) {
		String codpostal = dataOldRegister.get("cfCp");
		String poblacion = dataOldRegister.get("cfCity");
		String direccion = dataOldRegister.get("cfDir1");

		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el código postal <b>" + codpostal + "</b>",
			isPostalCode(codpostal));

		if (poblacion!=null && "0".compareTo(poblacion)!=0) {
			checks.add(
				"Aparece la población <b>" + poblacion + "</b>",
				isPoblacion(poblacion));
		}
		
		checks.add(
			"Aparece la dirección <b>" + direccion + "</b>",
			isDireccion(direccion));
		
		return checks;
	}	
	
	private boolean isPostalCode(String postalCode) {
		return postalCode.compareTo(pageMisDirecciones.getCodigoPostal())==0;
	}
	private boolean isPoblacion(String poblacion) {
		return poblacion.compareTo(pageMisDirecciones.getPoblacion())==0;
	}
	private boolean isDireccion(String direccion) {
		return direccion.compareTo(pageMisDirecciones.getDireccion())==0;
	}
	
}
