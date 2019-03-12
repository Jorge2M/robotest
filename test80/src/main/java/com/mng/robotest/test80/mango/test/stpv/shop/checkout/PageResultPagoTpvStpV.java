package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageResultPagoTpv;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageResultPagoTpvStpV {
    
	@Validation
    public static ListResultValidation validateIsPageOk(DataPedido dataPedido, String codPais, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
	 	validations.add(
			"Aparece un texto de confirmación de la compra<br>",
			PageResultPagoTpv.isPresentCabeceraConfCompra(driver), State.Warn);
	 	
        String importeTotal = dataPedido.getImporteTotal();
	 	validations.add(
			"Aparece el importe " + importeTotal + " de la operación<br>",
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
