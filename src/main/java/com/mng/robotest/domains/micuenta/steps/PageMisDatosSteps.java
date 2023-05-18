package com.mng.robotest.domains.micuenta.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisDatos;
import com.mng.robotest.domains.registro.beans.DataNewRegister;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageMisDatosSteps extends StepBase {

	private final PageMisDatos pageMisDatos = new PageMisDatos();
	
	@Validation
	public ChecksTM validaIsPage (String usuarioReg) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Aparece una página con el la cabecera \"Mis datos\" (esperamos hasta " + seconds + " segundos)",
			pageMisDatos.isPage(seconds), Warn);
		
		checks.add(
			"El campo de email contiene " + usuarioReg,
			pageMisDatos.getValueEmailInput().toUpperCase().compareTo(usuarioReg.toUpperCase())==0, Warn);

		return checks;
	}
	
	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(Map<String,String> datosRegOk, String codpais) {
		String nombre = datosRegOk.get("cfName");
		String apellidos = datosRegOk.get("cfSname");
		String email = datosRegOk.get("cfEmail");

		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece un campo de contraseña de tipo password",
			pageMisDatos.isVisiblePasswordTypePassword());
		
		checks.add(
			"El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
			(pageMisDatos.getNumInputContentVoid() <= 1));
		
		checks.add(
			"El Apellidos contiene el definido durante el registro: <b>" + apellidos + "</b>",
			(pageMisDatos.getTextInputNombre().compareTo(nombre)==0));
		
		checks.add(
			"El Email contiene el definido durante el registro: <b>" + email + "</b>",
			(pageMisDatos.getTextInputEmail().toLowerCase().compareTo(email.toLowerCase())==0));

		return checks;
	}
	
	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(DataNewRegister dataNewRegister) {
		String nombre = dataNewRegister.getName();
		String email = dataNewRegister.getEmail();

		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece un campo de contraseña de tipo password",
			pageMisDatos.isVisiblePasswordTypePassword());
		
		checks.add(
			"El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
			(pageMisDatos.getTextInputNombre().compareTo(nombre)==0));
		
		checks.add(
			"El Email contiene el definido durante el registro: <b>" + email + "</b>",
			(pageMisDatos.getTextInputEmail().toLowerCase().compareTo(email.toLowerCase())==0));
		
		return checks;
	}	

	@Step(
		description = "Modificar el nombre (cambio entre #{name1}<->#{name2}) + Botón \"Modificar Datos\"",
		expected = "Aparece la confirmación que los datos se han modificados")
	public String modificaNombreYGuarda(String name1, String name2) {
		String currentName = pageMisDatos.getValueNombreInput();
		String newName = (currentName.compareTo(name2)==0) ? name1 : name2;
		
		pageMisDatos.setNombreInput(newName);
		pageMisDatos.clickGuardarCambios();
		validateModificationOfData();

		return newName;
	}

	@Validation(description = "Aparece una pantalla de resultado Ok de la suscripción")
	private boolean validateModificationOfData() {
		return pageMisDatos.pageResOK();
	}

	@Validation (
		description="En el campo del nombre figura<b>: #{nombre}<b>",
		level=Warn)
	public boolean validaContenidoNombre(String nombre) {
		return pageMisDatos.getValueNombreInput().contains(nombre);
	}

	@Step(
		description = "Seleccionar el link Cancelar cuenta",
		expected = "Aparece la página de resultado de cancelación de la cuenta ok")
	public void cancelarCuenta() {
		pageMisDatos.cancelarCuenta();
		checkCuentaCanceladaOk(3);
	}
	
	@Validation (
		description="Aparece un mensaje de cuenta cancelada correctamente (lo esperamos #{seconds} segundos)")
	private boolean checkCuentaCanceladaOk(int seconds) {
		return pageMisDatos.isMessageCuentaCanceladaOkVisible(seconds);
	}
	
}
