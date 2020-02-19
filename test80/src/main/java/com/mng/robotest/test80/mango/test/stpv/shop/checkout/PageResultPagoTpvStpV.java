package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageResultPagoTpv;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageResultPagoTpvStpV {
    
	@Validation
    public static ChecksTM validateIsPageOk(DataPedido dataPedido, String codPais, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece un texto de confirmación de la compra",
			PageResultPagoTpv.isPresentCabeceraConfCompra(driver), State.Warn);
	 	
        String importeTotal = dataPedido.getImporteTotal();
	 	validations.add(
			"Aparece el importe " + importeTotal + " de la operación",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	
	 	boolean isVisibleCodPedido = PageResultPagoTpv.isVisibleCodPedido(driver);
	 	String codPedido = "";
	 	if (isVisibleCodPedido) {
	 		codPedido = PageResultPagoTpv.getCodigoPedido(driver);
	 	}
	 	validations.add(
			"Aparece el código de pedido <b>" + codPedido + "</b>",
			isVisibleCodPedido, State.Defect);
	 	
        dataPedido.setCodpedido(codPedido); 
        dataPedido.setResejecucion(State.Ok);
        
        return validations;
    }
}
