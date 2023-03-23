package com.mng.robotest.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageGestionarClientes;
import com.mng.robotest.domains.manto.pageobjects.PageGestionarClientes.TypeThirdButton;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

import static com.mng.robotest.domains.manto.pageobjects.PageGestionarClientes.TypeThirdButton.*;
import static com.github.jorge2m.testmaker.conf.State.*;

public class PageGestionarClientesSteps extends StepMantoBase {

	private final PageGestionarClientes pageGestionarClientes = new PageGestionarClientes();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestionarClientes.TITULO,
			pageGestionarClientes.isPage(), Defect);
	 	
	 	checks.add(
			"Aparece el apartado de \"Buscar clientes\"",
			pageGestionarClientes.isVisibleFormBuscarClientes(), Defect);
	 	
	 	checks.add(
			"Aparece el apartado de \"Tratar clientes\"",
			pageGestionarClientes.isVisibleFormTratarClientes(), Defect);
	 	
		return checks;
	}

	@Step (
		description="Introducimos el DNI <b>#{dni}</b> y pulsamos el botón \"Buscar\"",
		expected="Aparece una lista de clientes válida",
		saveErrorData=SaveWhen.Never,
		saveImagePage=SaveWhen.Always)
	public void inputDniAndClickBuscar(String dni) {
		pageGestionarClientes.inputDniAndClickBuscarButton(dni, 20);	 
		checkAfterSearchByDni(dni);
	}
	
	@Validation
	private ChecksTM checkAfterSearchByDni(String dni) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Se muestra la tabla de información",
			pageGestionarClientes.isVisibleTablaInformacion(), Defect);
	 	
	 	checks.add(
			"Aparece el DNI <b>" + dni + "</b> en la tabla",
			pageGestionarClientes.getDniTabla(dni), Defect);
	 	
		int seconds = 1;
	 	checks.add(String.format(
			"Aparece el botón de Alta o Baja (los esperamos un máximo de %s segundos)", seconds),
			pageGestionarClientes.isVisibleThirdButton(BAJA, seconds) ||
			pageGestionarClientes.isVisibleThirdButton(ALTA, seconds), 
			Defect);
	 	
	 	return checks;
	}

	private static final String TAG_TYPE_BUTTON = "@TagTypeButton";
	@Step (
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"" + TAG_TYPE_BUTTON + "\"",
		expected="Aparece el mensaje correspondiente y el botón Alta",
		saveErrorData=SaveWhen.Never)
	public void clickThirdButton() {
		var typeButton = pageGestionarClientes.getTypeThirdButton();	
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_TYPE_BUTTON, typeButton.toString());
		pageGestionarClientes.clickThirdButtonAndWaitSeconds(typeButton, 3);   
		checkAfterClickButton(typeButton);
	}
	
	@Validation
	private ChecksTM checkAfterClickButton(TypeThirdButton typeButton) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(String.format(
			"Aparece el mensaje <b>" + typeButton.literal() + "</b> (lo esperamos %s segundos)", seconds),
			pageGestionarClientes.isVisibleMensajeClickThirdButton(typeButton, seconds), Defect);
	 	
		TypeThirdButton buttonExpected = typeButton.buttonExpectedAfterClick();
	 	checks.add(String.format(
			"Aparece el botón \"Alta\" (lo esperamos %s segundos)", seconds),
			pageGestionarClientes.isVisibleThirdButton(buttonExpected, seconds), Defect);
		
	 	return checks;
	}

	@Step(
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"Detalles\"",
		expected="Muestra los detalles del cliente correctamente",
		saveErrorData=SaveWhen.Never)
	public void clickDetallesButton(String dni) {
		String idCliente = pageGestionarClientes.getIdClienteTablaFromDni(dni);
		pageGestionarClientes.clickDetallesButtonAndWaitSeconds(3);	
		checkAfterClickDetalles(dni, idCliente);
	}
	
	@Validation
	private ChecksTM checkAfterClickDetalles(String dni, String idCliente) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el id del cliente <b>" + idCliente + "</b>",
			pageGestionarClientes.isVisibleIdClienteClickDetallesButton(idCliente), Defect);
	 	
	 	checks.add(
			"Aparece el dni del cliente <b>" + dni + "</b>",
			pageGestionarClientes.isVisibleDniClickDetallesButton(dni), Defect);
	 	
	 	return checks;
	}
}
