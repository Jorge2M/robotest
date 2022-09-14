package com.mng.robotest.test.steps.manto;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.pageobject.manto.PageGestionarClientes;
import com.mng.robotest.test.pageobject.manto.PageGestionarClientes.TypeThirdButton;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;


public class PageGestionarClientesSteps {

	private final PageGestionarClientes pageGestionarClientes = new PageGestionarClientes();
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestionarClientes.TITULO,
			pageGestionarClientes.isPage(), State.Defect);
	 	
	 	checks.add(
			"Aparece el apartado de \"Buscar clientes\"",
			pageGestionarClientes.isVisibleFormBuscarClientes(), State.Defect);
	 	
	 	checks.add(
			"Aparece el apartado de \"Tratar clientes\"",
			pageGestionarClientes.isVisibleFormTratarClientes(), State.Defect);
	 	
		return checks;
	}

	@Step (
		description="Introducimos el DNI <b>#{dni}</b> y pulsamos el botón \"Buscar\"",
		expected="Aparece una lista de clientes válida",
		saveErrorData=SaveWhen.Never,
		saveImagePage=SaveWhen.Always)
	public void inputDniAndClickBuscar(String dni) throws Exception {
		pageGestionarClientes.inputDniAndClickBuscarButton(dni, 20);	 
		checkAfterSearchByDni(dni);
	}
	
	@Validation
	private ChecksTM checkAfterSearchByDni(String dni) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Se muestra la tabla de información",
			pageGestionarClientes.isVisibleTablaInformacion(), State.Defect);
	 	
	 	checks.add(
			"Aparece el DNI <b>" + dni + "</b> en la tabla",
			pageGestionarClientes.getDniTabla(dni), State.Defect);
	 	
		int secondsToWait = 1;
	 	checks.add(
			"Aparece el botón de Alta o Baja (los esperamos un máximo de " + secondsToWait + " segundos)",
			pageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Baja, secondsToWait) ||
			pageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Alta, secondsToWait), 
			State.Defect);
	 	
	 	return checks;
	}

	static final String TagTypeButton = "@TagTypeButton";
	@Step (
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"" + TagTypeButton + "\"",
		expected="Aparece el mensaje correspondiente y el botón Alta",
		saveErrorData=SaveWhen.Never)
	public void clickThirdButton() throws Exception {
		TypeThirdButton typeButton = pageGestionarClientes.getTypeThirdButton();	
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagTypeButton, typeButton.toString());
		pageGestionarClientes.clickThirdButtonAndWaitSeconds(typeButton, 3);   
		checkAfterClickButton(typeButton);
	}
	
	@Validation
	private ChecksTM checkAfterClickButton(TypeThirdButton typeButton) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el mensaje <b>" + typeButton.getMensaje() + "</b>",
			pageGestionarClientes.isVisibleMensajeClickThirdButton(typeButton), State.Defect);
	 	
		int seconds = 2;
		TypeThirdButton buttonExpected = typeButton.buttonExpectedAfterClick();
	 	checks.add(
			"Aparece el botón \"Alta\" (lo esperamos hasta " + seconds + " segundos)",
			pageGestionarClientes.isVisibleThirdButtonUntil(buttonExpected, seconds), State.Defect);
		
	 	return checks;
	}

	@Step(
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"Detalles\"",
		expected="Muestra los detalles del cliente correctamente",
		saveErrorData=SaveWhen.Never)
	public void clickDetallesButton(String dni) throws Exception {
		String idCliente;
		int waitSeconds = 3;
		idCliente = pageGestionarClientes.getIdClienteTablaFromDni(dni);
		pageGestionarClientes.clickDetallesButtonAndWaitSeconds(waitSeconds);	
		checkAfterClickDetalles(dni, idCliente);
	}
	
	@Validation
	private ChecksTM checkAfterClickDetalles(String dni, String idCliente) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el id del cliente <b>" + idCliente + "</b>",
			pageGestionarClientes.isVisibleIdClienteClickDetallesButton(idCliente), State.Defect);
	 	
	 	checks.add(
			"Aparece el dni del cliente <b>" + dni + "</b>",
			pageGestionarClientes.isVisibleDniClickDetallesButton(dni), State.Defect);
	 	
	 	return checks;
	}
}
