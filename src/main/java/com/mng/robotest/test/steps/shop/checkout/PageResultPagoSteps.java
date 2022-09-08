package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.micuenta.pageobjects.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.domains.micuenta.steps.PageAccesoMisComprasSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.checkout.PageResultPago;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageResultPagoSteps extends StepBase {

	private final PageResultPago pageResultPago = new PageResultPago();
	private final WebDriver driver = TestMaker.getDriverTestCase();
	
	@Validation (
		description="Acaba apareciendo la página de la Shop de Mango de \"Ya has hecho tu compra\" (la esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
	public boolean validaIsPageUntil(int maxSecondsToWait) {
		return (pageResultPago.isVisibleTextoConfirmacionPago(maxSecondsToWait));
	}
	
	public void validateIsPageOk(DataPago dataPago) throws Exception {
		validateTextConfirmacionPago();
		validateDataPedido(dataPago);
		if (dataPago.getFTCkout().checkLoyaltyPoints) {
			validateBlockNewLoyaltyPoints();
		}
	}
	
	@Validation (
		description="Aparece la URL correspondiente a la página de resultado OK (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkUrl(int maxSeconds) {
		return (pageResultPago.checkUrl(maxSeconds));
	}
	
	@Validation
	public ChecksTM validateTextConfirmacionPago() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds1 = 10;
		boolean isVisibleTextConfirmacion = pageResultPago.isVisibleTextoConfirmacionPago(maxSeconds1);
		checks.add(
			"Aparece un texto de confirmación del pago (lo esperamos hasta " + maxSeconds1 + " segundos)",
			isVisibleTextConfirmacion, State.Warn);
		if (!isVisibleTextConfirmacion) {
			int maxSeconds2 = 20;
			checks.add(
				"Si no aparece lo esperamos " + maxSeconds2 + " segundos",
				pageResultPago.isVisibleTextoConfirmacionPago(maxSeconds2), State.Defect);
		}
		return checks;
	}
	
	@Validation
	public ChecksTM validateDataPedido(DataPago dataPago) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		String importeTotal = "";
		DataBag dataBag = dataPago.getDataPedido().getDataBag(); 
		if (dataBag!=null && "".compareTo(dataBag.getImporteTotal())!=0) {
			importeTotal = dataBag.getImporteTotal();
		} else {
			importeTotal = dataPago.getDataPedido().getImporteTotal();
		}
	  	checks.add(
	  		"Aparece el importe " + importeTotal + " de la operación",
	  		ImporteScreen.isPresentImporteInScreen(importeTotal, dataTest.pais.getCodigo_pais(), driver), State.Warn);
		
		if (channel==Channel.desktop) {
//			if (dataTest.appE==AppEcom.shop) {
				checks.add(
			  		"Aparece el link hacia las compras",
			  		pageResultPago.isButtonMisCompras(), State.Warn);
//			} else {
//				checks.add(
//			  		"Aparece el link hacia los pedidos",
//			  		pageResultPago.isLinkPedidos(), State.Warn);
//			}
		}
		
		int maxSeconds = 5;
		String codigoPed = pageResultPago.getCodigoPedido(maxSeconds);
		boolean isCodPedidoVisible = "".compareTo(codigoPed)!=0;
		checks.add(
	  		"Aparece el código de pedido (" + codigoPed + ") (lo esperamos hasta " + maxSeconds + " segundos)",
	  		isCodPedidoVisible, State.Defect);
		
		DataPedido dataPedido = dataPago.getDataPedido();
		if (isCodPedidoVisible) {
			dataPedido.setResejecucion(State.Ok);
		}
		dataPedido.setCodpedido(codigoPed);
		
		return checks;
	}
	
	@Validation (
		description="Aparece el bloque informando que se han generado nuevos Loyalty Points",
		level=State.Defect)
	public boolean validateBlockNewLoyaltyPoints() {
		return (pageResultPago.isVisibleBlockNewLoyaltyPoints());
	}
	
	@Step (
		description="Seleccionar el link \"Mis Compras\"",
		expected="Aparece la página de \"Mis compras\" o la de \"Acceso a Mis compras\" según si el usuario está o no loginado")
	public void selectMisCompras(boolean userRegistered) throws Exception {
		pageResultPago.clickMisCompras();	 
		if (userRegistered) {
			new PageMisComprasSteps().validateIsPage();
		} else {
			new PageAccesoMisComprasSteps().validateIsPage();
		}
	}	
	
	@Step (
		description="Seleccionar el botón \"Descubrir lo último\" o el icono de Mango", 
		expected="Volvemos a la portada")
	public void selectSeguirDeShopping(AppEcom app) throws Exception {  
		if (pageResultPago.isVisibleDescubrirLoUltimo()) {
			pageResultPago.clickDescubrirLoUltimo();
		} else {
			SecCabecera.getNew(channel, app).clickLogoMango();
		}
	}
	
	public void selectLinkMisComprasAndValidateCompra(DataPago dataPago) throws Exception {		
		selectMisCompras(dataTest.userRegistered);
		DataPedido dataPedido = dataPago.getDataPedido();
		if (dataTest.userRegistered) {
			new PageMisComprasSteps().validateIsCompraOnline(
					dataPedido.getCodpedido(), dataPago.getFTCkout().chequeRegalo);
		} else {
			PageAccesoMisComprasSteps pageAccesoMisComprasSteps = new PageAccesoMisComprasSteps();
			pageAccesoMisComprasSteps.clickBlock(TypeBlock.NO_REGISTRADO);
			pageAccesoMisComprasSteps.buscarPedidoForNoRegistrado(dataPago.getDataPedido());
		}
	}
}