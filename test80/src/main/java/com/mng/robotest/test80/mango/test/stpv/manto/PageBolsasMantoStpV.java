package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageBolsas;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Bolsas en Manto
 * @author jorge.munoz
 *
 */
public class PageBolsasMantoStpV {

	@Validation
    public static ChecksResultWithFlagLinkCodPed validaLineaBolsa(DataPedido dataPedido, AppEcom appE, WebDriver driver) {
//    	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//    	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//        State levelByCIAdyen = State.Warn;
//    	if (dataPedido.getPago().isAdyen() &&
//        	UtilsMangoTest.isEntornoCI(appE, dFTest))
//    		levelByCIAdyen = State.Info;
		ChecksResultWithFlagLinkCodPed validations = ChecksResultWithFlagLinkCodPed.getNew();
		
        int maxSecondsWait = 1;
        boolean isPresentLinkPedido = PageBolsas.presentLinkPedidoInBolsaUntil(dataPedido.getCodigoPedidoManto(), maxSecondsWait, driver);
	 	if (isPresentLinkPedido) {
	 		dataPedido.setIdCompra(PageBolsas.getIdCompra(dataPedido.getCodigoPedidoManto(), driver));
	 	}
        validations.setExistsLinkCodPed(isPresentLinkPedido);
	 	validations.add(
			"En la columna 1 aparece el código de pedido: " + dataPedido.getCodigoPedidoManto() + " (lo esperamos hasta " + maxSecondsWait + " segundos) <br>",
			isPresentLinkPedido, State.Warn);
	 	
	 	validations.add(
			"Aparece una sola bolsa <br>",
			PageBolsas.getNumLineas(driver)==1, State.Warn);
	 	
	 	//En el caso de Outlet no tenemos la información del TPV que toca
	 	if (appE!=AppEcom.outlet) {
	 		String idTpv = dataPedido.getPago().getTpv().getId();
		 	validations.add(
				"En la columna 8 Aparece el Tpv asociado: " + idTpv + "<br>",
				PageBolsas.presentIdTpvInBolsa(driver, idTpv), State.Warn);
	 	}
	 	
	 	validations.add(
			"En la columna 7 aparece el email asociado: " + dataPedido.getEmailCheckout(),
			PageBolsas.presentCorreoInBolsa(driver, dataPedido.getEmailCheckout()), State.Warn);
        
        return validations;
    }
	
    public static class ChecksResultWithFlagLinkCodPed extends ChecksResult {
    	boolean existsLinkCodPed;
    	private ChecksResultWithFlagLinkCodPed() {
    		super();
    	}
    	public static ChecksResultWithFlagLinkCodPed getNew() {
    		return (new ChecksResultWithFlagLinkCodPed());
    	}
    	
    	public boolean getExistsLinkCodPed() {
    		return this.existsLinkCodPed;
    	}
    	
    	public void setExistsLinkCodPed(boolean existsLinkCodPed) {
    		this.existsLinkCodPed = existsLinkCodPed;
    	}
    }
}
