package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.Page1DktopCheckout;
import com.mng.robotest.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.test.data.Descuento;
import com.mng.robotest.test.data.Descuento.DiscountType;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.generic.beans.ValeDiscount;

import static com.github.jorge2m.testmaker.conf.State.*;

public class Page1DktopCheckoutSteps extends StepBase {
	
	private final Page1DktopCheckout page1DktopCheckout = new Page1DktopCheckout();
	
	@Validation
	public ChecksTM validateIsPageOK() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		boolean isPageInitCheckout = page1DktopCheckout.isPageUntil(seconds);
	 	checks.add(
	 		Check.make(
			    "Aparece la página inicial del Checkout " + getLitSecondsWait(seconds),
			    isPageInitCheckout, Warn)
	 		.store(StoreType.None).build());
	 	
	 	if (!isPageInitCheckout) {
		 	checks.add(
				"Si no ha aparecido la esperamos " + (seconds * 2) + " segundos más",
				page1DktopCheckout.isPageUntil(seconds*2));
	 	}
	 	checks.add(
			"Cuadran los artículos a nivel de la Referencia e Importe",
			page1DktopCheckout.validateArticlesAndImport(), Warn);
	 	
	 	return checks;
	}
	
	@Validation
	public ChecksTM validateIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la página inicial del Checkout " + getLitSecondsWait(seconds) + "<br>",
			page1DktopCheckout.isPageUntil(seconds));
		
		checks.add(
			"Aparecen los datos introducidos:<br>" + 
			"\"Nombre: <b>" + chequeRegalo.getNombre() + "</b><br>" + 
			"\"Apellidos: <b>" + chequeRegalo.getApellidos() + "</b><br>" + 
			"\"Email: <b>" + chequeRegalo.getEmail() + "</b><br>" + 
			"\"Importe: <b>" + chequeRegalo.getImporte() + "</b><br>" + 
			"\"Mensaje: <b>" + chequeRegalo.getMensaje() + "</b>",
			page1DktopCheckout.isDataChequeRegalo(chequeRegalo), Warn);
		
		return checks;
	}
	
	@Validation
	public ChecksTM validaResultImputPromoEmpl() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Aparece el descuento total aplicado al empleado " + getLitSecondsWait(seconds),
			page1DktopCheckout.isVisibleDescuentoEmpleadoUntil(seconds));
	 	
		var descuento = new Descuento(app, DiscountType.EMPLEADO);
	 	checks.add(
			"Para todos los artículos, el % de descuento final es como mínimo del " + 
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + ")",
			page1DktopCheckout.validateArticlesAndDiscount(descuento), Warn);
	 	
	 	return checks;
	}
	
	@Step (
		description="Introducir el vale <b style=\"color:blue;\">#{valePais.getCodigoVale()}</b> y pulsar el botón \"CONFIRMAR\"", 
		expected="Aparece la página de resumen de artículos con los descuentos correctamente aplicados",
		saveNettraffic=SaveWhen.Always)
	public void inputValeDescuento(ValeDiscount valePais) { 
		var pageCheckoutWrapper = new PageCheckoutWrapper();
		pageCheckoutWrapper.inputCodigoPromoAndAccept(valePais.getCodigoVale());
		dataTest.getDataBag().setImporteTotal(pageCheckoutWrapper.getPrecioTotalFromResumen(true));
		if (!isPRO()) {
			checkRedMessageInputValeInvisible(valePais);
			checkValeDiscountIsCorrect(valePais);
		} else {
			checkRedMessageInputValeVisible(valePais);
		}
		
		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
	}
	
	@Validation(
		description="<b>No</b> aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\"")
	private boolean checkRedMessageInputValeInvisible(ValeDiscount valePais) {
		return !page1DktopCheckout.isVisibleErrorRojoInputPromoUntil(1);
	}
	@Validation(
		description="<b>Sí</b> aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\"")
	private boolean checkRedMessageInputValeVisible(ValeDiscount valePais) {
		return page1DktopCheckout.isVisibleErrorRojoInputPromoUntil(1);
	}	
	
	@Validation
	private ChecksTM checkValeDiscountIsCorrect(ValeDiscount valePais) {
		var checks = ChecksTM.getNew();
		var descuento = new Descuento(valePais.getPorcDescuento(), app);
	 	checks.add(
			"En los artículos a los que aplica, el descuento es de " +  
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + "):" +
			dataTest.getDataBag().getListArtDescHTML(),
			page1DktopCheckout.validateArticlesAndDiscount(descuento));
	 	return checks;
	}
	
	@Step (
		description="Si existe -> seleccionar el link \"Eliminar\" asociado al vale", 
		expected="El vale desaparece")
	public void clearValeIfLinkExists() {
		new PageCheckoutWrapper().clickEliminarValeIfExists();
		checkIsVisibleInputVale(1);
	}
	
	@Validation (
		description="Aparece el input para la introducción del vale " + SECONDS_WAIT,
		level=Warn)
	private boolean checkIsVisibleInputVale(int seconds) {
		return (page1DktopCheckout.isVisibleInputCodigoPromoUntil(seconds));
	}
	
	@Step (
		description="Introducir un código de vendedor correcto #{codigoVendedor} y pulsar el botón \"Aceptar\"", 
		expected="El vendedor queda registrado")
	public void stepIntroduceCodigoVendedorVOTF(String codigoVendedor) {
		page1DktopCheckout.inputVendedorVOTF(codigoVendedor);
		page1DktopCheckout.acceptInputVendedorVOTF();
		checkAfterInputCodigoVendedor(codigoVendedor);				
	}
	
	@Validation
	private ChecksTM checkAfterInputCodigoVendedor(String codigoVendedor) {
		int seconds = 3;
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Desaparece el campo de Input del código de vendedor " + getLitSecondsWait(seconds),
			!page1DktopCheckout.isVisibleInputVendedorVOTF(seconds));
	 	
	 	checks.add(
			"En su lugar se pinta el código de vendedor " + codigoVendedor,
			page1DktopCheckout.isVisibleCodigoVendedorVOTF(codigoVendedor));
	 	
	 	return checks;
	}
}