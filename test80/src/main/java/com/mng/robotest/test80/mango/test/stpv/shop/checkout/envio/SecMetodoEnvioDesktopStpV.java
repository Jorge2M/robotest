package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
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
        	if (ModalDroppoints.isErrorMessageVisibleUntil(driver)) {
        		ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), driver);
        	}
        }

        validaBlockSelectedDesktop(tipoTransporte, driver);
        if (tipoTransporte.isEntregaDomicilio()) {
            modalDroppoints.validaIsNotVisible(Channel.desktop, driver);
        } else {
            modalDroppoints.validaIsVisible(Channel.desktop, driver);
        }
    }
    
    @Validation
    public static ChecksResult validaBlockSelectedDesktop(TipoTransporte tipoTransporte, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
      	validations.add(
    		"Desaparece la capa de Loading  (lo esperamos hasta " + maxSecondsWait + " segundos)",
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
