package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;

public class SecMetodoEnvioDesktopStpV {

    public static ModalDroppointsStpV modalDroppoints;
    
    @SuppressWarnings({ "static-access", "unused" })
    @Step (
    	description="<b style=\"color:blue;\">#{nombrePago}</b>:Seleccionamos el método de envío <b>#{tipoTransporte}</b>", 
        expected="Se selecciona el método de envío correctamente")
    public static void selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataCtxPago dCtxPago, WebDriver driver) 
    throws Exception {
        SecMetodoEnvioDesktop.selectMetodo(tipoTransporte, driver);
        if (!tipoTransporte.isEntregaDomicilio()) {
        	if (ModalDroppoints.isErrorMessageVisibleUntil(driver))
        		ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), driver);
        }

        //Validaciones
        validaBlockSelectedDesktop(tipoTransporte, driver);
        if (tipoTransporte.isEntregaDomicilio()) {
            modalDroppoints.validaIsNotVisible(Channel.desktop, driver);
        }
        else {
            modalDroppoints.validaIsVisible(Channel.desktop, driver);
        }
    }
    
    @Validation
    public static ListResultValidation validaBlockSelectedDesktop(TipoTransporte tipoTransporte, WebDriver driver) throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 5;
      	validations.add(
    		"Desaparece la capa de Loading  (lo esperamos hasta " + maxSecondsWait + " segundos) <br>",
    		PageCheckoutWrapper.waitUntilNoDivLoading(driver, maxSecondsWait), State.Warn);
      	validations.add(
    		"Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b>",
    		SecMetodoEnvioDesktop.isBlockSelectedUntil(tipoTransporte, maxSecondsWait, driver), State.Warn);
      	return validations;
    }
    
    @Step (
    	description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
        expected="La franja horaria se selecciona correctamente")
    public static void selectFranjaHorariaUrgente(int posicion, WebDriver driver) {
    	SecMetodoEnvioDesktop.selectFranjaHorariaUrgente(posicion, driver);
    }
}
