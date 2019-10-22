package com.mng.robotest.test80.mango.test.stpv.shop.checkout.amazon;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.amazon.PageAmazonIdent;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageAmazonIdentStpV {
    
	@Validation
    public static ChecksResult validateIsPage(Pais pais, Channel channel, DataPedido dataPedido, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Aparece una página con el logo de Amazon",
    		PageAmazonIdent.isLogoAmazon(driver), State.Warn);
    	validations.add(
    		"Aparece los campos para la identificación (usuario/password)",
    		PageAmazonIdent.isPageIdent(driver), State.Defect);
    	if (channel==Channel.desktop) {
        	validations.add(
        		"En la página resultante figura el importe total de la compra (" + dataPedido.getImporteTotal() + ")",
        		ImporteScreen.isPresentImporteInScreen(dataPedido.getImporteTotal(), pais.getCodigo_pais(), driver), State.Warn);
    	}
    	
    	return validations;
    }
}
