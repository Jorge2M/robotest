package com.mng.robotest.test80.mango.test.stpv.manto.pedido;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido;
import static com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.GestionPostCompra.*;
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
        expected="Aparece una página de la pasarela de resultado OK")
	public static void changePedidoToEstado(EstadoPedido newState, WebDriver driver) throws Exception {
        PageGenerarPedido.selectEstado(newState, driver);
        PageGenerarPedido.clickAndWait(GenerarFicheroButton, driver);
        
        //Validations
        checkMsgFileCreatedCorrectly(driver);
	}
	
	@Validation (
		description="Aparece el mensaje de <b>Fichero creado correctamente</b>",
		level=State.Defect)
	private static boolean checkMsgFileCreatedCorrectly(WebDriver driver) {
		return (PageGenerarPedido.isElementInState(MessageOkFicheroCreado, StateElem.Visible, driver));
	}
}