package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraOnline;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageDetallePedido.DetallePedido;

public class PageDetallePedidoStpV {
	PageDetallePedido pageDetalle;
	
    public PageDetallePedidoStpV(WebDriver driver) {
		PageDetallePedido pageDetalle = DetallePedido.New.getPageObject();
		if (pageDetalle.isPage(driver)) {
			this.pageDetalle = pageDetalle;
		} else {
			this.pageDetalle = DetallePedido.Old.getPageObject();
		}
    }
    
    public PageDetallePedido getPageDetalle() {
    	return this.pageDetalle;
    }
    
    public void validateIsPageOk(CompraOnline compraOnline, String codPais, WebDriver driver) 
    throws Exception {
        String codPedido = compraOnline.numPedido;
        String importeTotal = compraOnline.importe.replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
        validateIsPageOk(codPedido, importeTotal, codPais, driver);
        areOkPrendasOnline(compraOnline.numPrendas, driver);
    }
    
    @Validation
    public ChecksResult areOkPrendasOnline(int numPrendasCompraOnline, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 2;
      	validations.add(
      		"Es visible alguna prenda (la esperamos hasta " + maxSecondsWait + " segundos)",
      		pageDetalle.isVisiblePrendaUntil(maxSecondsWait, driver), State.Info);	
      	validations.add(
      		"Aparecen " + numPrendasCompraOnline + " prendas",
      		pageDetalle.getNumPrendas(driver)==numPrendasCompraOnline, State.Warn);	
    	return validations;
    }
    
    public void validateIsPageOk(DataPedido dataPedido, WebDriver driver) {
        String codPedido = dataPedido.getCodpedido();
        String importeTotalManto = dataPedido.getImporteTotalManto();
        String codPais = dataPedido.getCodigoPais();
        
//    	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//    	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//        boolean isAdyenAndCI = (dataPedido.getPago().isAdyen() && UtilsMangoTest.isEntornoCI(app, dFTest));
        validateIsPageOk(codPedido, importeTotalManto, codPais, driver);
    }
    
    @Validation
    private ChecksResult validateIsPageOk(String codPedido, String importeTotalWithoutCurrency, String codPais, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
      	validations.add(
      		"Aparece la página de detalle del pedido",
      		pageDetalle.isPage(driver), State.Warn);	   
      	validations.add(
      		"En la página figura el Nº de pedido: " + codPedido,
      		driver.getPageSource().contains(codPedido), State.Info);	
      	validations.add(
      		"Como total figura el importe: " + importeTotalWithoutCurrency,
      		pageDetalle.isPresentImporteTotal(importeTotalWithoutCurrency, codPais, driver), State.Info);
      	return validations;
    }
    
    @Step (
    	description="Seleccionar el link necesario para volver a la página inicial de \"Mis Compras\"",
    	expected="Se vuelve a la página inicial de \"Mis Compras\"")
    public void clickBackButton(Channel channel, WebDriver driver) throws Exception {
        pageDetalle.clickBackButton(channel, driver);
    }
}
