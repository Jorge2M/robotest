package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageResultPagoTpv;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageResultPagoTpvStpV {
    
	@Validation
    public static ChecksResult validateIsPageOk(DataPedido dataPedido, String codPais, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
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
