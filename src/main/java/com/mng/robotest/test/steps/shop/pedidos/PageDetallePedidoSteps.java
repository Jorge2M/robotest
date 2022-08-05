package com.mng.robotest.test.steps.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.micuenta.Ticket;
import com.mng.robotest.test.pageobject.shop.miscompras.PageDetallePedido;
import com.mng.robotest.test.pageobject.shop.miscompras.PageDetallePedido.DetallePedido;

public class PageDetallePedidoSteps {
	
	private final PageDetallePedido pageDetalle;
	private final WebDriver driver = TestMaker.getDriverTestCase();
	
	public PageDetallePedidoSteps(Channel channel, AppEcom app) {
		PageDetallePedido pageDetalle = DetallePedido.New.getPageObject(channel, app);
		if (pageDetalle.isPage()) {
			this.pageDetalle = pageDetalle;
		} else {
			pageDetalle = DetallePedido.Old.getPageObject(channel, app);
			if (pageDetalle.isPage()) {
				this.pageDetalle = pageDetalle;
			} else {
				this.pageDetalle = DetallePedido.OldOld.getPageObject(channel, app);
			}
		}
	}
	
	public PageDetallePedido getPageDetalle() {
		return this.pageDetalle;
	}
	
	public void validateIsPageOk(Ticket compra, String codPais) {
		String importeTotal = compra.getPrecio().replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
		validateIsPageOk(compra.getId(), importeTotal, codPais);
		areOkPrendasOnline(compra.getNumItems());
	}
	
	@Validation
	public ChecksTM areOkPrendasOnline(int numPrendasCompraOnline) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
	  	checks.add(
	  		"Es visible alguna prenda (la esperamos hasta " + maxSeconds + " segundos)",
	  		pageDetalle.isVisiblePrendaUntil(maxSeconds), State.Info);	
	  	checks.add(
	  		"Aparecen " + numPrendasCompraOnline + " prendas",
	  		pageDetalle.getNumPrendas()==numPrendasCompraOnline, State.Warn);	
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
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
	  		"Aparece la página de detalle del pedido",
	  		pageDetalle.isPage(), State.Warn);	   
	  	checks.add(
	  		"En la página figura el Nº de pedido: " + codPedido,
	  		driver.getPageSource().contains(codPedido), State.Info);	
	  	checks.add(
	  		"Como total figura el importe: " + importeTotalWithoutCurrency,
	  		pageDetalle.isPresentImporteTotal(importeTotalWithoutCurrency, codPais), State.Info);
	  	return checks;
	}
	
	@Step (
		description="Seleccionar el link necesario para volver a la página inicial de \"Mis Compras\"",
		expected="Se vuelve a la página inicial de \"Mis Compras\"")
	public void clickBackButton(Channel channel) {
		pageDetalle.clickBackButton(channel);
	}
}
