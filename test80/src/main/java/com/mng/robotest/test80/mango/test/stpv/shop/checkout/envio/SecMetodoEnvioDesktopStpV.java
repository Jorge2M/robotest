package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
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
    public static void selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataCtxPago dCtxPago, WebDriver driver) {
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
    public static ChecksTM validaBlockSelectedDesktop(TipoTransporte tipoTransporte, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
      	validations.add(
    		"Desaparece la capa de Loading  (lo esperamos hasta " + maxSeconds + " segundos)",
    		PageCheckoutWrapper.waitUntilNoDivLoading(driver, maxSeconds), State.Warn);
      	validations.add(
    		"Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b>",
    		SecMetodoEnvioDesktop.isBlockSelectedUntil(tipoTransporte, maxSeconds, driver), State.Warn);
      	return validations;
    }
    
    @Step (
    	description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
        expected="La franja horaria se selecciona correctamente")
    public static void selectFranjaHorariaUrgente(int posicion, WebDriver driver) {
    	SecMetodoEnvioDesktop.selectFranjaHorariaUrgente(posicion, driver);
    }
}
