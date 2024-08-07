package com.mng.robotest.tests.domains.micuenta.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMisDatos;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;

import static com.github.jorge2m.testmaker.conf.State.*;

public class MisDatosSteps extends StepBase {

	private final PageMisDatos pgMisDatos = new PageMisDatos();
	
	@Validation
	public ChecksTM checkIsPage (String usuarioReg) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		boolean isPage = pgMisDatos.isPage(seconds);
		checks.add(
			"Aparece una página con el la cabecera \"Mis datos\" " + getLitSecondsWait(seconds),
			isPage, WARN);
		
		if (isPage) {
			checks.add(
				"El campo de email contiene " + usuarioReg,
				pgMisDatos.getValueEmailInput().toUpperCase().compareTo(usuarioReg.toUpperCase())==0, 
				WARN);
		}

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
			pgMisDatos.isVisiblePasswordTypePassword());
		
		checks.add(
			"El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
			(pgMisDatos.getNumInputContentVoid() <= 1));
		
		checks.add(
			"El Apellidos contiene el definido durante el registro: <b>" + apellidos + "</b>",
			(pgMisDatos.getTextInputNombre().compareTo(nombre)==0));
		
		checks.add(
			"El Email contiene el definido durante el registro: <b>" + email + "</b>",
			(pgMisDatos.getTextInputEmail().toLowerCase().compareTo(email.toLowerCase())==0));

		return checks;
	}
	
	@Validation
	public ChecksTM checkIsDataAssociatedToRegister(DataNewRegister dataNewRegister) {
		String nombre = dataNewRegister.getName();
		String email = dataNewRegister.getEmail();

		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece un campo de contraseña de tipo password",
			pgMisDatos.isVisiblePasswordTypePassword());
		
		checks.add(
			"El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
			(pgMisDatos.getTextInputNombre().compareTo(nombre)==0));
		
		checks.add(
			"El Email contiene el definido durante el registro: <b>" + email + "</b>",
			(pgMisDatos.getTextInputEmail().toLowerCase().compareTo(email.toLowerCase())==0));
		
		return checks;
	}	

	@Step(
		description = "Modificar el nombre (cambio entre #{name1}<->#{name2}) + Botón \"Modificar Datos\"",
		expected = "Aparece la confirmación que los datos se han modificados")
	public String modificaNombreYGuarda(String name1, String name2) {
		String currentName = pgMisDatos.getValueNombreInput();
		String newName = (currentName.compareTo(name2)==0) ? name1 : name2;
		
		pgMisDatos.setNombreInput(newName);
		pgMisDatos.clickGuardarCambios();
		validateModificationOfData();

		return newName;
	}

	@Validation (description = "Aparece una pantalla de resultado Ok de la suscripción")
	private boolean validateModificationOfData() {
		return pgMisDatos.pageResOK();
	}

	@Validation (
		description="En el campo del nombre figura<b>: #{nombre}<b>",
		level=WARN)
	public boolean validaContenidoNombre(String nombre) {
		return pgMisDatos.getValueNombreInput().contains(nombre);
	}

	@Step(
		description = "Seleccionar el link Cancelar cuenta + confirmar cancelación",
		expected = "Aparece la página de resultado de cancelación de la cuenta ok")
	public void cancelarCuenta() {
		pgMisDatos.cancelarCuenta();
		checkCuentaCanceladaOk(3);
	}
	
	@Step(
		description = "Confirmar la cancelación de la cuenta",
		expected = "Aparece la página de resultado de cancelación de la cuenta ok")
	public void confirmCancelarCuenta() {
		pgMisDatos.confirmEliminarCuenta();
		checkCuentaCanceladaOk(3);
	}	
	
	
	@Validation (
		description="Aparece un mensaje de cuenta cancelada correctamente " + SECONDS_WAIT)
	private boolean checkCuentaCanceladaOk(int seconds) {
		return pgMisDatos.isMessageCuentaCanceladaOkVisible(seconds);
	}
	
}
