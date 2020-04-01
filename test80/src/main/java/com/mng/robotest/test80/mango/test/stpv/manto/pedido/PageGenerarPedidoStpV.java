package com.mng.robotest.test80.mango.test.stpv.manto.pedido;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;

import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido;
import static com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.GestionPostCompra.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import org.openqa.selenium.WebDriver;

public class PageGenerarPedidoStpV {

	@Validation (
		description="Aparece la página de generación asociada al pedido <b>#{idPedido}</b>",
		level=State.Defect)
	public static boolean validateIsPage(String idPedido, WebDriver driver) {
		return (PageGenerarPedido.isPage(idPedido, driver));
	}
	
	@Step (
		description="Seleccionamos el estado <b>#{newState}</b> y pulsamos el botón <b>Generar Fichero</b>", 
		expected="Aparece una página de la pasarela de resultado OK",
		saveErrorData=SaveWhen.Never)
	public static void changePedidoToEstado(EstadoPedido newState, WebDriver driver) {
		PageGenerarPedido.selectEstado(newState, driver);
		click(GenerarFicheroButton.getBy(), driver).exec();
		checkMsgFileCreatedCorrectly(driver);
	}
	
	@Validation (
		description="Aparece el mensaje de <b>Fichero creado correctamente</b>",
		level=State.Warn)
	private static boolean checkMsgFileCreatedCorrectly(WebDriver driver) {
		return (state(Visible, MessageOkFicheroCreado.getBy(), driver).check());
	}
}
