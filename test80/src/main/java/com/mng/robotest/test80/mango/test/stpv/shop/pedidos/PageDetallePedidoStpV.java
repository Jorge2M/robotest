package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageDetallePedido.DetallePedido;

public class PageDetallePedidoStpV {
	
	private final WebDriver driver;
	private final PageDetallePedido pageDetalle;
	
    public PageDetallePedidoStpV(WebDriver driver) {
    	this.driver = driver;
		PageDetallePedido pageDetalle = DetallePedido.New.getPageObject(driver);
		if (pageDetalle.isPage()) {
			this.pageDetalle = pageDetalle;
		} else {
			this.pageDetalle = DetallePedido.Old.getPageObject(driver);
		}
    }
    
    public PageDetallePedido getPageDetalle() {
    	return this.pageDetalle;
    }
    
    public void validateIsPageOk(Ticket compra, String codPais, WebDriver driver) {
        String importeTotal = compra.getPrecio().replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
        validateIsPageOk(compra.getId(), importeTotal, codPais);
        areOkPrendasOnline(compra.getNumItems());
    }
    
    @Validation
    public ChecksTM areOkPrendasOnline(int numPrendasCompraOnline) {
    	ChecksTM validations = ChecksTM.getNew();
	    int maxSeconds = 2;
      	validations.add(
      		"Es visible alguna prenda (la esperamos hasta " + maxSeconds + " segundos)",
      		pageDetalle.isVisiblePrendaUntil(maxSeconds), State.Info);	
      	validations.add(
      		"Aparecen " + numPrendasCompraOnline + " prendas",
      		pageDetalle.getNumPrendas()==numPrendasCompraOnline, State.Warn);	
    	return validations;
    }
    
    public void validateIsPageOk(DataPedido dataPedido) {
        String codPedido = dataPedido.getCodpedido();
        String importeTotalManto = dataPedido.getImporteTotalManto();
        String codPais = dataPedido.getCodigoPais();
        
//    	//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//    	//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//        boolean isAdyenAndCI = (dataPedido.getPago().isAdyen() && UtilsMangoTest.isEntornoCI(app, dFTest));
        validateIsPageOk(codPedido, importeTotalManto, codPais);
    }
    
    @Validation
    private ChecksTM validateIsPageOk(String codPedido, String importeTotalWithoutCurrency, String codPais) {
    	ChecksTM validations = ChecksTM.getNew();
      	validations.add(
      		"Aparece la página de detalle del pedido",
      		pageDetalle.isPage(), State.Warn);	   
      	validations.add(
      		"En la página figura el Nº de pedido: " + codPedido,
      		driver.getPageSource().contains(codPedido), State.Info);	
      	validations.add(
      		"Como total figura el importe: " + importeTotalWithoutCurrency,
      		pageDetalle.isPresentImporteTotal(importeTotalWithoutCurrency, codPais), State.Info);
      	return validations;
    }
    
    @Step (
    	description="Seleccionar el link necesario para volver a la página inicial de \"Mis Compras\"",
    	expected="Se vuelve a la página inicial de \"Mis Compras\"")
    public void clickBackButton(Channel channel) {
        pageDetalle.clickBackButton(channel);
    }
}
