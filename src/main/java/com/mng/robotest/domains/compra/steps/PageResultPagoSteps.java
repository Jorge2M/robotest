package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.PageResultPago;
import com.mng.robotest.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.domains.micuenta.pageobjects.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.domains.micuenta.steps.PageAccesoMisComprasSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps.ChecksResultWithNumberPoints;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageResultPagoSteps extends StepBase {

	private final PageResultPago pageResultPago = new PageResultPago();
	
	@Validation (
		description="Acaba apareciendo la página de la Shop de Mango de \"Ya has hecho tu compra\" (la esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean validaIsPageUntil(int seconds) {
		return (pageResultPago.isVisibleTextoConfirmacionPago(seconds));
	}
	
	public void validateIsPageOk(DataPago dataPago) {
		validateTextConfirmacionPago();
		validateDataPedido(dataPago);
		GenericChecks.checkDefault();
	}
	
	@Validation (
		description="Aparece la URL correspondiente a la página de resultado OK (la esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean checkUrl(int seconds) {
		return (pageResultPago.checkUrl(seconds));
	}
	
	@Validation
	public ChecksTM validateTextConfirmacionPago() {
		var checks = ChecksTM.getNew();
		int seconds1 = 10;
		boolean isVisibleTextConfirmacion = pageResultPago.isVisibleTextoConfirmacionPago(seconds1);
		checks.add(
			"Aparece un texto de confirmación del pago (lo esperamos hasta " + seconds1 + " segundos)",
			isVisibleTextConfirmacion, Warn);
		if (!isVisibleTextConfirmacion) {
			int seconds2 = 20;
			checks.add(
				"Si no aparece lo esperamos " + seconds2 + " segundos",
				pageResultPago.isVisibleTextoConfirmacionPago(seconds2), Defect);
		}
		return checks;
	}
	
	@Validation
	public ChecksTM validateDataPedido(DataPago dataPago) {
		var checks = ChecksTM.getNew();
		String importeTotal = "";
		if (dataTest.getDataBag()!=null && "".compareTo(dataTest.getDataBag().getImporteTotal())!=0) {
			importeTotal = dataTest.getDataBag().getImporteTotal();
		} else {
			importeTotal = dataPago.getDataPedido().getImporteTotal();
		}
	  	checks.add(
	  		"Aparece el importe " + importeTotal + " de la operación",
	  		ImporteScreen.isPresentImporteInScreen(importeTotal, dataTest.getCodigoPais(), driver), Warn);
		
		if (channel==Channel.desktop) {
			int seconds = 1;
			checks.add(
		  		"Aparece el link hacia las compras (lo esperamos " + seconds + " segundos)",
		  		pageResultPago.isButtonMisCompras(seconds), Warn);
		}
		
		int seconds = 5;
		String codigoPed = pageResultPago.getCodigoPedido(seconds);
		boolean isCodPedidoVisible = "".compareTo(codigoPed)!=0;
		checks.add(
	  		"Aparece el código de pedido (" + codigoPed + ") (lo esperamos hasta " + seconds + " segundos)",
	  		isCodPedidoVisible, Defect);
		
		DataPedido dataPedido = dataPago.getDataPedido();
		if (isCodPedidoVisible) {
			dataPedido.setResejecucion(State.Ok);
		}
		dataPedido.setCodpedido(codigoPed);
		
		return checks;
	}
	
	@Validation
	public ChecksResultWithNumberPoints checkLoyaltyPointsGenerated() {
		var checks = new ChecksResultWithNumberPoints();
	  	checks.add(
		  	"Aparece el bloque con los nuevos <b>Loyalty Points</b> generados",
		  	pageResultPago.isVisibleBlockNewLoyaltyPoints(), Defect);
		
	  	checks.setNumberPoints(pageResultPago.getLikesGenerated());
	  	checks.add(
	  		Check.make(
	  			"El número de likes es > 0",
	  			checks.getNumberPoints()>0, Defect)
		  	.info(String.format("Se generan <b>%s</b> Likes", checks.getNumberPoints())).build());
	  	
	  	return checks;
	}
	
	@Step(
		description="Seleccionar el link <b>descuentos y experiencias</b>", 
		expected="Aparece la página de Mango Likes You")	
	public void clickLinkDescuentosExperiencias() {
		pageResultPago.clickLinkDescuentosExperiencias();
		new PageMangoLikesYouSteps().checkIsPageOk();
	}
	
	@Step (
		description="Seleccionar el link \"Mis Compras\"",
		expected="Aparece la página de \"Mis compras\" o la de \"Acceso a Mis compras\" según si el usuario está o no loginado")
	public void selectMisCompras() {
		pageResultPago.clickMisCompras();	 
		if (dataTest.isUserRegistered()) {
			new PageMisComprasSteps().validateIsPage();
		} else {
			new PageAccesoMisComprasSteps().validateIsPage();
		}
	}	
	
	@Step (
		description="Seleccionar el botón \"Descubrir lo último\" o el icono de Mango", 
		expected="Volvemos a la portada")
	public void selectSeguirDeShopping(AppEcom app) {  
		if (pageResultPago.isVisibleDescubrirLoUltimo()) {
			pageResultPago.clickDescubrirLoUltimo();
		} else {
			new SecCabeceraMostFrequent().clickLogoMango();
		}
	}
	
	public void selectLinkMisComprasAndValidateCompra(DataPago dataPago) {		
		selectMisCompras();
		DataPedido dataPedido = dataPago.getDataPedido();
		if (dataTest.isUserRegistered()) {
			new PageMisComprasSteps().validateIsCompraOnline(
					dataPedido.getCodpedido(), dataPago.getFTCkout().chequeRegalo);
		} else {
			var pageAccesoMisComprasSteps = new PageAccesoMisComprasSteps();
			pageAccesoMisComprasSteps.clickBlock(TypeBlock.NO_REGISTRADO);
			pageAccesoMisComprasSteps.buscarPedidoForNoRegistrado(dataPago.getDataPedido());
		}
	}
}
