package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.checkout.PageResultPago;
import com.mng.robotest.test.pageobject.shop.micuenta.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.test.steps.shop.micuenta.PageAccesoMisComprasSteps;
import com.mng.robotest.test.steps.shop.miscompras.PageMisComprasSteps;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageResultPagoSteps {

	private final PageResultPago pageResultPago;
	private final WebDriver driver;
	private final Channel channel;
	
	public PageResultPagoSteps(TypePago typePago, Channel channel, WebDriver driver) {
		this.pageResultPago = new PageResultPago(typePago, channel, driver);
		this.driver = driver;
		this.channel = channel;
	}
	
	@Validation (
		description="Acaba apareciendo la página de la Shop de Mango de \"Ya has hecho tu compra\" (la esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
	public boolean validaIsPageUntil(int maxSecondsToWait) {
		return (pageResultPago.isVisibleTextoConfirmacionPago(maxSecondsToWait));
	}
	
	public void validateIsPageOk(DataCtxPago dCtxPago, DataCtxShop dCtxSh) throws Exception {
		validateTextConfirmacionPago();
		validateDataPedido(dCtxPago, dCtxSh);
		if (dCtxPago.getFTCkout().loyaltyPoints) {
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
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds1 = 10;
		boolean isVisibleTextConfirmacion = pageResultPago.isVisibleTextoConfirmacionPago(maxSeconds1);
		validations.add(
			"Aparece un texto de confirmación del pago (lo esperamos hasta " + maxSeconds1 + " segundos)",
			isVisibleTextConfirmacion, State.Warn);
		if (!isVisibleTextConfirmacion) {
			int maxSeconds2 = 20;
			validations.add(
				"Si no aparece lo esperamos " + maxSeconds2 + " segundos",
				pageResultPago.isVisibleTextoConfirmacionPago(maxSeconds2), State.Defect);
		}
		return validations;
	}
	
	@Validation
	public ChecksTM validateDataPedido(DataCtxPago dCtxPago, DataCtxShop dCtxSh) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		String importeTotal = "";
		DataBag dataBag = dCtxPago.getDataPedido().getDataBag(); 
		if (dataBag!=null && "".compareTo(dataBag.getImporteTotal())!=0) {
			importeTotal = dataBag.getImporteTotal();
		} else {
			importeTotal = dCtxPago.getDataPedido().getImporteTotal();
		}
	  	validations.add(
	  		"Aparece el importe " + importeTotal + " de la operación",
	  		ImporteScreen.isPresentImporteInScreen(importeTotal, dCtxSh.pais.getCodigo_pais(), driver), State.Warn);
		
		if (dCtxSh.channel==Channel.desktop) {
//			if (dCtxSh.appE==AppEcom.shop) {
				validations.add(
			  		"Aparece el link hacia las compras",
			  		pageResultPago.isButtonMisCompras(), State.Warn);
//			} else {
//				validations.add(
//			  		"Aparece el link hacia los pedidos",
//			  		pageResultPago.isLinkPedidos(), State.Warn);
//			}
		}
		
		int maxSeconds = 5;
		String codigoPed = pageResultPago.getCodigoPedido(maxSeconds);
		boolean isCodPedidoVisible = "".compareTo(codigoPed)!=0;
		validations.add(
	  		"Aparece el código de pedido (" + codigoPed + ") (lo esperamos hasta " + maxSeconds + " segundos)",
	  		isCodPedidoVisible, State.Defect);
		
		DataPedido dataPedido = dCtxPago.getDataPedido();
		if (isCodPedidoVisible) {
			dataPedido.setResejecucion(State.Ok);
		}
		dataPedido.setCodpedido(codigoPed);
		
		return validations;
	}
	
	@Validation (
		description="Aparece el bloque informando que se han generado nuevos Loyalty Points",
		level=State.Defect)
	public boolean validateBlockNewLoyaltyPoints() {
		return (pageResultPago.isVisibleBlockNewLoyaltyPoints());
	}
	
//	@Step (
//		description="Seleccionar el link \"Mis pedidos\"", 
//		expected="Apareca la página de identificación del pedido")
//	public void selectMisPedidos(DataPedido dataPedido) throws Exception {
//		pageResultPago.clickMisPedidos();	  
//								
//		//Validations. Puede aparecer la página con la lista de pedidos o la de introducción de los datos del pedido
//		if (PageListPedidosOld.isPage(driver)) {
//			PageListPedidosSteps.validateIsPage(dataPedido.getCodpedido(), driver);
//		} else {
//			PageInputPedidoSteps.getNew(channel, driver).validateIsPage();
//		}
//	}	
	
	@Step (
		description="Seleccionar el link \"Mis Compras\"",
		expected="Aparece la página de \"Mis compras\" o la de \"Acceso a Mis compras\" según si el usuario está o no loginado")
	public void selectMisCompras(boolean userRegistered, AppEcom app, Pais pais) 
	throws Exception {
		pageResultPago.clickMisCompras();	 
		if (userRegistered) {
			PageMisComprasSteps pageMisComprasSteps = PageMisComprasSteps.getNew(channel, app, driver);
			pageMisComprasSteps.validateIsPage(pais);
		} else {
			PageAccesoMisComprasSteps.getNew(driver).validateIsPage();
		}
	}	
	
	@Step (
		description="Seleccionar el botón \"Descubrir lo último\" o el icono de Mango", 
		expected="Volvemos a la portada")
	public void selectSeguirDeShopping(AppEcom app) throws Exception {  
		if (pageResultPago.isVisibleDescubrirLoUltimo()) {
			pageResultPago.clickDescubrirLoUltimo();
		} else {
			SecCabecera.getNew(channel, app, driver).clickLogoMango();
		}
	}
	
//	public void selectLinkPedidoAndValidatePedido(DataPedido dataPedido) 
//	throws Exception {
//		selectMisPedidos(dataPedido);
//		StepTM StepTestMaker = TestMaker.getLastStep();
//		if (StepTestMaker.getResultSteps()==State.Ok) {
//			if (PageListPedidosOld.isPage(driver)) {
//				PageListPedidosSteps.selectPedido(dataPedido.getCodpedido(), driver);
//			} else {
//				PageInputPedidoSteps.getNew(channel, driver).inputPedidoAndSubmit(dataPedido);
//			}
//		}
//	}
	
	public void selectLinkMisComprasAndValidateCompra(DataCtxPago dCtxPago, DataCtxShop dCtxSh) 
	throws Exception {		
		selectMisCompras(dCtxSh.userRegistered, dCtxSh.appE, dCtxSh.pais);
		DataPedido dataPedido = dCtxPago.getDataPedido();
		if (dCtxSh.userRegistered) {
			PageMisComprasSteps pageMisComprasSteps = PageMisComprasSteps.getNew(dCtxSh.channel, dCtxSh.appE, driver);
			pageMisComprasSteps.validateIsCompraOnline(dataPedido.getCodpedido(), dCtxPago.getFTCkout().isChequeRegalo);
		} else {
			PageAccesoMisComprasSteps pageAccesoMisComprasSteps = PageAccesoMisComprasSteps.getNew(driver);
			pageAccesoMisComprasSteps.clickBlock(TypeBlock.NoRegistrado);
			pageAccesoMisComprasSteps.buscarPedidoForNoRegistrado(dCtxPago.getDataPedido(), dCtxSh.channel, dCtxSh.appE);
		}
	}
}
