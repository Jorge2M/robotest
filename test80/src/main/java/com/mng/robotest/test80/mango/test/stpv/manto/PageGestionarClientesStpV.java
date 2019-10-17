package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestionarClientes;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestionarClientes.TypeThirdButton;

public class PageGestionarClientesStpV {

	@Validation
	public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Estamos en la página " + PageGestionarClientes.titulo,
			PageGestionarClientes.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece el apartado de \"Buscar clientes\"",
			PageGestionarClientes.isVisibleFormBuscarClientes(driver), State.Defect);
	 	validations.add(
			"Aparece el apartado de \"Tratar clientes\"",
			PageGestionarClientes.isVisibleFormTratarClientes(driver), State.Defect);
		return validations;
	}

	@Step (
		description="Introducimos el DNI <b>#{dni}</b> y pulsamos el botón \"Buscar\"",
		expected="Aparece una lista de clientes válida",
		saveErrorData=SaveWhen.Never,
		saveImagePage=SaveWhen.Always)
	public static void inputDniAndClickBuscar(String dni, WebDriver driver) throws Exception {
		int waitSeconds = 20;
		PageGestionarClientes.inputDniAndClickBuscarButton(dni, waitSeconds, driver);     
		checkAfterSearchByDni(dni, driver);
	}
	
	@Validation
	private static ChecksResult checkAfterSearchByDni(String dni, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Se muestra la tabla de información",
			PageGestionarClientes.isVisibleTablaInformacion(driver), State.Defect);
	 	validations.add(
			"Aparece el DNI <b>" + dni + "</b> en la tabla",
			PageGestionarClientes.getDniTabla(dni, driver), State.Defect);
		int maxSecondsToWait = 1;
	 	validations.add(
			"Aparece el botón de Alta o Baja (los esperamos un máximo de " + maxSecondsToWait + " segundos)",
			PageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Baja, maxSecondsToWait, driver) ||
			PageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Alta, maxSecondsToWait, driver), 
			State.Defect);
	 	
	 	return validations;
	}

	final static String TagTypeButton = "@TagTypeButton";
	@Step (
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"" + TagTypeButton + "\"",
		expected="Aparece el mensaje correspondiente y el botón Alta",
		saveErrorData=SaveWhen.Never)
	public static void clickThirdButton(WebDriver driver) throws Exception {
		TypeThirdButton typeButton = PageGestionarClientes.getTypeThirdButton(driver);	
		TestMaker.getCurrentStep().replaceInDescription(TagTypeButton, typeButton.toString());
		
		int waitSeconds = 3;
		PageGestionarClientes.clickThirdButtonAndWaitSeconds(typeButton, waitSeconds, driver);   
		checkAfterClickButton(typeButton, driver);
	}
	
	@Validation
	private static ChecksResult checkAfterClickButton(TypeThirdButton typeButton, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece el mensaje <b>" + typeButton.getMensaje() + "</b>",
			PageGestionarClientes.isVisibleMensajeClickThirdButton(typeButton, driver), State.Defect);
	 	
		int maxSecondsWait = 2;
		TypeThirdButton buttonExpected = typeButton.buttonExpectedAfterClick();
	 	validations.add(
			"Aparece el botón \"Alta\" (lo esperamos hasta " + maxSecondsWait + " segundos)",
			PageGestionarClientes.isVisibleThirdButtonUntil(buttonExpected, maxSecondsWait, driver), State.Defect);
		
	 	return validations;
	}

	@Step(
		description="Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"Detalles\"",
		expected="Muestra los detalles del cliente correctamente",
		saveErrorData=SaveWhen.Never)
	public static void clickDetallesButton(String dni, WebDriver driver) throws Exception {
		String idCliente;
		int waitSeconds = 3;
		idCliente = PageGestionarClientes.getIdClienteTablaFromDni(dni, driver);
		PageGestionarClientes.clickDetallesButtonAndWaitSeconds(waitSeconds, driver);    
		checkAfterClickDetalles(dni, idCliente, driver);
	}
	
	@Validation
	private static ChecksResult checkAfterClickDetalles(String dni, String idCliente, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece el id del cliente <b>" + idCliente + "</b>",
			PageGestionarClientes.isVisibleIdClienteClickDetallesButton(idCliente, driver), State.Defect);
	 	validations.add(
			"Aparece el dni del cliente <b>" + dni + "</b>",
			PageGestionarClientes.isVisibleDniClickDetallesButton(dni, driver), State.Defect);
	 	
	 	return validations;
	}
}
