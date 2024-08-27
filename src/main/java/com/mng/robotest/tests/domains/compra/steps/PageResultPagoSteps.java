package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageResultPago;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps.ChecksResultWithNumberPoints;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.tests.domains.micuenta.steps.PageAccesoMisComprasSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageResultPagoSteps extends StepBase {

	private final PageResultPago pgResultPago = new PageResultPago();
	
	@Validation (
		description="Acaba apareciendo la página de la Shop de Mango de \"Ya has hecho tu compra\" " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pgResultPago.isVisibleTextoConfirmacionPago(seconds);
	}
	
	public void checkIsPageOk() {
		checkTextConfirmationPayment();
		checkDataPedido();
		checksDefault();
	}
	
	@Validation (
		description="Aparece la URL correspondiente a la página de resultado OK " + SECONDS_WAIT)
	public boolean checkUrl(int seconds) {
		return (pgResultPago.checkUrl(seconds));
	}
	
	@Validation
	public ChecksTM checkTextConfirmationPayment() {
		var checks = ChecksTM.getNew();
		int seconds1 = 10;
		boolean isVisibleTextConfirmacion = pgResultPago.isVisibleTextoConfirmacionPago(seconds1);
		checks.add(
			"Aparece un texto de confirmación del pago " + getLitSecondsWait(seconds1),
			isVisibleTextConfirmacion, WARN);
		if (!isVisibleTextConfirmacion) {
			int seconds2 = 20;
			checks.add(
				"Si no aparece lo esperamos " + seconds2 + " segundos",
				pgResultPago.isVisibleTextoConfirmacionPago(seconds2));
		}
		return checks;
	}
	
	@Validation
	public ChecksTM checkDataPedido() {
		var checks = ChecksTM.getNew();
		String importeTotalExpected = getImporteTotalExpected();
		
	  	checks.add(
	  		"Aparece el importe " + importeTotalExpected + " de la operación",
	  		ImporteScreen.isPresentImporteInScreen(importeTotalExpected, dataTest.getCodigoPais(), driver), WARN);
		
		if (isDesktop()) {
			int seconds = 1;
			checks.add(
		  		"Aparece el link hacia las compras " + getLitSecondsWait(seconds),
		  		pgResultPago.isButtonMisCompras(seconds), WARN);
		}
		
		String codigoPed = pgResultPago.getCodigoPedido(5);
		boolean isCodPedidoVisible = "".compareTo(codigoPed)!=0;
		checks.add(
	  		"Aparece el código de pedido (" + codigoPed + ") " + getLitSecondsWait(5),
	  		isCodPedidoVisible);
		
		var dataPedido = dataTest.getDataPago().getDataPedido();
		if (isCodPedidoVisible) {
			dataPedido.setResejecucion(OK);
		}
		dataPedido.setCodpedido(codigoPed);
		
		return checks;
	}

	private String getImporteTotalExpected() {
		var dataBag = dataTest.getDataBag();
		if (dataBag!=null && "".compareTo(dataBag.getImporteTotal())!=0) {
			return dataBag.getImporteTotal();
		} else {
			var dataPago = dataTest.getDataPago();
			return dataPago.getDataPedido().getImporteTotal();
		}
	}
	
	@Validation
	public ChecksResultWithNumberPoints checkLoyaltyPointsGenerated() {
		var checks = new ChecksResultWithNumberPoints();
	  	checks.add(
		  	"Aparece el bloque con los nuevos <b>Loyalty Points</b> generados",
		  	pgResultPago.isVisibleBlockNewLoyaltyPoints());
		
	  	checks.setNumberPoints(pgResultPago.getLikesGenerated());
	  	checks.add(
	  		Check.make(
	  			"El número de likes es > 0",
	  			checks.getNumberPoints()>0)
		  	.info(String.format("Se generan <b>%s</b> Likes", checks.getNumberPoints())).build());
	  	
	  	return checks;
	}
	
	@Step(
		description="Seleccionar el link <b>descuentos y experiencias</b>", 
		expected="Aparece la página de Mango Likes You")	
	public void clickLinkDescubrirVentajas() {
		pgResultPago.clickLinkDescubrirVentajas();
		new MangoLikesYouSteps().checkIsPage();
	}
	
	@Step (
		description="Seleccionar el link \"Mis Compras\"",
		expected="Aparece la página de \"Mis compras\" o la de \"Acceso a Mis compras\" según si el usuario está o no loginado")
	public void selectMisCompras() {
		pgResultPago.clickMisCompras();	 
		if (dataTest.isUserRegistered()) {
			new PageMisComprasSteps().validateIsPage();
		} else {
			new PageAccesoMisComprasSteps().validateIsPage();
		}
	}	
	
	@Step (
		description="Seleccionar el botón \"Descubrir lo último\" o el icono de Mango", 
		expected="Volvemos a la portada")
	public void selectSeguirDeShopping() {  
		if (pgResultPago.isVisibleDescubrirLoUltimo()) {
			pgResultPago.clickDescubrirLoUltimo();
		} else {
			clickIconMango();
		}
	}
	
	public void selectLinkMisComprasAndValidateCompra() {		
		selectMisCompras();
		var dataPago = dataTest.getDataPago();
		var dataPedido = dataPago.getDataPedido();
		if (dataTest.isUserRegistered()) {
			new PageMisComprasSteps().checkIsCompraOnline(
					dataPedido.getCodpedido(), dataPago.getFTCkout().chequeRegalo);
		} else {
			var pageAccesoMisComprasSteps = new PageAccesoMisComprasSteps();
			pageAccesoMisComprasSteps.clickBlock(TypeBlock.NO_REGISTRADO);
			pageAccesoMisComprasSteps.buscarPedidoForNoRegistrado(dataPago.getDataPedido());
		}
	}
	
}
