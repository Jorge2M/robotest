package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
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
    
	@Step (
		description="Clickamos el botón \"Confirmar Datos\"", 
        expected="La dirección de envío se establece a la de la tienda")
    public static void clickConfirmarDatosButton(Channel channel, DataPedido dataPedido, WebDriver driver) 
    throws Exception {
        ModalDroppoints.secConfirmDatos.clickConfirmarDatosButtonAndWait(5/*maxSecondsToWait*/, driver);
        WebdrvWrapp.waitForPageLoaded(driver, 2/*waitSeconds*/);       
        
        //Validaciones
        checkConfirmacionCambioDireccionEnvio(dataPedido, channel, driver);
    }
	
	@Validation
	private static ChecksResult checkConfirmacionCambioDireccionEnvio(DataPedido dataPedido, Channel channel, WebDriver driver) 
	throws Exception {
		ChecksResult validations = ChecksResult.getNew();
      	validations.add(
    		"Desaparece la capa de Droppoints<br>",
    		!ModalDroppoints.isVisible(channel, driver), State.Warn);
      	
	    DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
	    String textDireccionEnvioCompleta = PageCheckoutWrapper.getTextDireccionEnvioCompleta(channel, driver);
        dataPedido.setDireccionEnvio(textDireccionEnvioCompleta);
      	validations.add(
    		"Se modifica la dirección de envío por la del Delivery Point (" + dataDp.getDireccion() + ")<br>",
    		textDireccionEnvioCompleta.contains(dataDp.getDireccion()), State.Defect);
      	validations.add(
    		"Se modifica el código postal de envío por el del Delivery Point (" + dataDp.getCPandPoblacion() + ")",
    		textDireccionEnvioCompleta.contains(dataDp.getCPandPoblacion()), State.Defect);
		return validations;
	}
}
