package com.mng.robotest.test.steps.shop.micuenta;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.micuenta.PageMisDatos;

public class PageMisDatosSteps extends StepBase {

	private final PageMisDatos pageMisDatos = new PageMisDatos();
	
	@Validation
	public ChecksTM validaIsPage (String usuarioReg) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Aparece una página con el la cabecera \"Mis datos\" (esperamos hasta " + maxSeconds + " segundos)",
			pageMisDatos.isPage(maxSeconds), State.Warn);
		
		checks.add(
			"El campo de email contiene " + usuarioReg,
			pageMisDatos.getValueEmailInput().compareTo(usuarioReg.toUpperCase())==0, State.Warn);

		return checks;
	}

	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(Map<String,String> datosRegOk, String codpais) {
		String nombre = datosRegOk.get("cfName");
		String apellidos = datosRegOk.get("cfSname");
		String email = datosRegOk.get("cfEmail");
		String direccion = datosRegOk.get("cfDir1");
		String codpostal = datosRegOk.get("cfCp");
		String poblacion = datosRegOk.get("cfCity");
		String provincia = datosRegOk.get("estadosPais");

		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece un campo de contraseña de tipo password",
			pageMisDatos.isVisiblePasswordTypePassword(), State.Defect);
		
		checks.add(
			"El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
			!(pageMisDatos.getNumInputContentVoid() > 1), State.Defect);
		
		checks.add(
			"El Apellidos contiene el definido durante el registro: <b>" + apellidos + "</b>",
			(pageMisDatos.getTextInputNombre().compareTo(nombre)==0), State.Defect);
		
		checks.add(
			"El Email contiene el definido durante el registro: <b>" + email + "</b>",
			(pageMisDatos.getTextInputEmail().toLowerCase().compareTo(email.toLowerCase())==0), State.Defect);
		
		checks.add(
			"La Dirección contiene la definida durante el registro: <b>" + direccion + "</b>",
			(pageMisDatos.getTextInputDireccion().compareTo(direccion)==0), State.Defect);
		
		checks.add(
			"El Código postal contiene el definido durante el registro: <b>" + codpostal + "</b>",
			(pageMisDatos.getTextInputCodPostal().compareTo(codpostal)==0), State.Defect);
		
		checks.add(
			"La población contiene la definida durante el registro: <b>" + poblacion + "</b>",
			(pageMisDatos.getTextInputPoblacion().compareTo(poblacion)==0), State.Defect);
		
		checks.add(
			"Está seleccionado el país definido durante el registro: <b>" + codpais + "</b>",
			(pageMisDatos.getCodPaisSelected().compareTo(codpais)==0), State.Defect);
		
		if (provincia != null) {
			checks.add(
				"Está seleccionada la provincia definida durante el registro: <b>" + provincia + "</b>",
				(pageMisDatos.getProvinciaSelected().compareTo(provincia)==0), State.Defect);
		}
		return checks;
	}
	
	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(DataNewRegister dataNewRegister) {
		String nombre = dataNewRegister.getName();
		String email = dataNewRegister.getEmail();
		String codpostal = dataNewRegister.getPostalCode();

		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece un campo de contraseña de tipo password",
			pageMisDatos.isVisiblePasswordTypePassword(), State.Defect);
		
		checks.add(
			"El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
			(pageMisDatos.getTextInputNombre().compareTo(nombre)==0), State.Defect);
		
		checks.add(
			"El Email contiene el definido durante el registro: <b>" + email + "</b>",
			(pageMisDatos.getTextInputEmail().toLowerCase().compareTo(email.toLowerCase())==0), State.Defect);
		
		checks.add(
			"El Código postal contiene el definido durante el registro: <b>" + codpostal + "</b>",
			(pageMisDatos.getTextInputCodPostal().compareTo(codpostal)==0), State.Defect);
		
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

	@Validation(
		description = "1) Aparece una pantalla de resultado Ok de la suscripción",
		level = State.Defect)
	private boolean validateModificationOfData() {
		return (pageMisDatos.pageResOK());
	}

	@Validation (
		description="En el campo del nombre figura<b>: #{nombre}<b>",
		level=State.Warn)
	public boolean validaContenidoNombre(String nombre) {
		return (pageMisDatos.getValueNombreInput().contains(nombre));
	}
}
