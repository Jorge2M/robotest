package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;

@SuppressWarnings({"static-access"})
public class SecConfirmDatosStpV {
    
	@Validation (
		description="Es visible la capa de confirmación de los datos (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    public static boolean validateIsVisible(int maxSecondsWait, Channel channel, WebDriver driver) {
        return (ModalDroppoints.secConfirmDatos.isVisibleUntil(maxSecondsWait, channel, driver));
    }
	
	public static void setDataIfNeeded(String codigoPais,WebDriver driver) throws Exception {
		if (PaisShop.Deutschland == PaisShop.getPais(codigoPais)) {
			if (ModalDroppoints.secConfirmDatos.isVisibleInputPostNumberIdDeutschland(driver)) {
				inputPostNumberId("0038594352", driver);
			}
		}
	}
	
	@Step (
		description="Introducir <b>#{postNumberId}</b> en el Post Number Id",
		expected="El valor queda correctamente introducido")
	public static void inputPostNumberId(String postNumberId, WebDriver driver) throws Exception {
		ModalDroppoints.secConfirmDatos.sendDataInputPostNumberIdDeutschland(postNumberId, driver);
	}
	
	@Step (
		description="Clickamos el botón \"Confirmar Datos\"", 
        expected="La dirección de envío se establece a la de la tienda")
    public static void clickConfirmarDatosButton(Channel channel, DataPedido dataPedido, WebDriver driver) 
    throws Exception {
        ModalDroppoints.secConfirmDatos.clickConfirmarDatosButtonAndWait(5, driver);
        WebdrvWrapp.waitForPageLoaded(driver, 2);       
        checkConfirmacionCambioDireccionEnvio(dataPedido, channel, driver);
    }
	
	@Validation
	private static ChecksResult checkConfirmacionCambioDireccionEnvio(DataPedido dataPedido, Channel channel, WebDriver driver) 
	throws Exception {
		ChecksResult validations = ChecksResult.getNew();
      	validations.add(
    		"Desaparece la capa de Droppoints",
    		!ModalDroppoints.isVisible(channel, driver), State.Warn);
      	
	    DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
	    String textDireccionEnvioCompleta = PageCheckoutWrapper.getTextDireccionEnvioCompleta(channel, driver);
        dataPedido.setDireccionEnvio(textDireccionEnvioCompleta);
      	validations.add(
    		"Se modifica la dirección de envío por la del Delivery Point (" + dataDp.getDireccion() + ")",
    		textDireccionEnvioCompleta.contains(dataDp.getDireccion()), State.Defect);
      	validations.add(
    		"Se modifica el código postal de envío por el del Delivery Point (" + dataDp.getCPandPoblacion() + ")",
    		textDireccionEnvioCompleta.contains(dataDp.getCPandPoblacion()), State.Defect);
		return validations;
	}
}
