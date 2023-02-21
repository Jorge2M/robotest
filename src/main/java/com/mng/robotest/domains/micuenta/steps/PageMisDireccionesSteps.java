package com.mng.robotest.domains.micuenta.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisDirecciones;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.transversal.StepBase;

public class PageMisDireccionesSteps extends StepBase {

	private final PageMisDirecciones pageMisDirecciones = new PageMisDirecciones();
	
	@Validation(
		description="Aparece la página de \"Mis direcciones\" (la esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage (int seconds) {
		return pageMisDirecciones.isPage(seconds);
	}

	public void checkData(DataNewRegister dataRegister) {
		clickLinkEditar();
		checkIsData(dataRegister);
		driver.navigate().back();
	}
	public void checkData(Map<String,String> dataRegister) {
		clickLinkEditar();
		checkIsData(dataRegister);
		driver.navigate().back();
	}

	@Step(
		description = "Seleccionar el link <b>Editar</b>",
		expected = "Aparece el formulario con los datos del usuario")
	private void clickLinkEditar() {
		pageMisDirecciones.clickLinkEditar();
		checkIsFormularioUsuario(5);
	}
	
	@Validation(
		description="Aparece el formulario con los datos del usuario (lo esperamos #{seconds} segundos)",
		level=State.Defect)
	public boolean checkIsFormularioUsuario(int seconds) {
		return pageMisDirecciones.isFormularioUsuario(seconds);
	}	
	
	@Validation
	private ChecksTM checkIsData(DataNewRegister dataNewRegister) {
		
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece el código postal <b>" + dataNewRegister.getPostalCode() + "</b>",
			isPostalCode(dataNewRegister.getPostalCode()), State.Defect);
		
		return checks;
	}
	
	@Validation
	private ChecksTM checkIsData(Map<String,String> dataOldRegister) {
		String codpostal = dataOldRegister.get("cfCp");
		String poblacion = dataOldRegister.get("cfCity");
		String direccion = dataOldRegister.get("cfDir1");

		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece el código postal <b>" + codpostal + "</b>",
			isPostalCode(codpostal), State.Defect);

		if (poblacion!=null) {
			checks.add(
				"Aparece la población <b>" + poblacion + "</b>",
				isPoblacion(poblacion), State.Defect);
		}
		
		checks.add(
			"Aparece la dirección <b>" + direccion + "</b>",
			isDireccion(direccion), State.Defect);
		
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
