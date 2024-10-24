package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.beans.Ticket;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageDetalleCompra;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageDetallePedidoSteps extends StepBase {
	
	private final PageDetalleCompra pgDetalleCompra = PageDetalleCompra.make(channel);
	
	public void validateIsPageOk(Ticket compra) {
		String importeTotal = compra.getPrecio().replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
		validateIsPageOk(compra.getId(), importeTotal, dataTest.getCodigoPais());
		areOkPrendasOnline(compra.getNumItems());
	}
	
	@Validation
	public ChecksTM areOkPrendasOnline(int numPrendasCompraOnline) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	  	checks.add(
	  		"Es visible alguna prenda " + getLitSecondsWait(seconds),
	  		pgDetalleCompra.isVisiblePrendaUntil(seconds), INFO);
	  	
	  	checks.add(
	  		"Aparecen " + numPrendasCompraOnline + " prendas",
	  		pgDetalleCompra.getNumPrendas()==numPrendasCompraOnline, WARN);
	  	
		return checks;
	}
	
	public void validateIsPageOk(DataPedido dataPedido) {
		String codPedido = dataPedido.getCodpedido();
		String importeTotalManto = dataPedido.getImporteTotalManto();
		String codPais = dataPedido.getCodigoPais();
		
//		//TODO tratamiento específico temporal para el entorno de CI con Adyen -> Level.Info 
//		//(hasta que dispongamos de la CI que despliega Adyen y el resto de artefactos satelitales)
//		boolean isAdyenAndCI = (dataPedido.getPago().isAdyen() && UtilsMangoTest.isEntornoCI(app, dFTest));
		validateIsPageOk(codPedido, importeTotalManto, codPais);
	}
	
	@Validation
	private ChecksTM validateIsPageOk(String codPedido, String importeTotalWithoutCurrency, String codPais) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	  	checks.add(
	  		"Aparece la página de detalle del pedido " + getLitSecondsWait(seconds),
	  		pgDetalleCompra.isPage(seconds), WARN);	   
	  	
	  	checks.add(
	  		"En la página figura el Nº de pedido: " + codPedido,
	  		driver.getPageSource().contains(codPedido), INFO);	
	  	
	  	checks.add(
	  		"Como total figura el importe: " + importeTotalWithoutCurrency,
	  		pgDetalleCompra.isPresentImporteTotal(importeTotalWithoutCurrency, codPais), INFO);
	  	
	  	return checks;
	}
	
}
