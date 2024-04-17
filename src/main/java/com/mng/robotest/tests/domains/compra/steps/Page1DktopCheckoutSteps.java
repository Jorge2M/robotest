package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.pageobjects.desktop.Page1DktopCheckout;
import com.mng.robotest.testslegacy.data.Descuento;
import com.mng.robotest.testslegacy.data.Descuento.DiscountType;
import com.mng.robotest.testslegacy.generic.beans.ValeDiscount;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class Page1DktopCheckoutSteps extends StepBase {
	
	private final Page1DktopCheckout pg1DktopCheckout = new Page1DktopCheckout();
	
	@Validation
	public ChecksTM checkIsPageOK() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		boolean isPageInitCheckout = isPage(seconds);
	 	checks.add(
	 		Check.make(
			    "Aparece la página inicial del Checkout " + getLitSecondsWait(seconds),
			    isPageInitCheckout, WARN)
	 		.store(NONE).build());
	 	
	 	if (!isPageInitCheckout) {
		 	checks.add(
				"Si no ha aparecido la esperamos " + (seconds * 2) + " segundos más",
				pg1DktopCheckout.isPage(seconds*2));
	 	}
	 	checks.add(
			"Cuadran los artículos a nivel de la Referencia e Importe",
			pg1DktopCheckout.validateArticlesAndImport(), WARN);
	 	
	 	return checks;
	}
	
	public boolean isPage(int seconds) {
		return pg1DktopCheckout.isPage(seconds);
	}
	
	@Validation
	public ChecksTM validateIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la página inicial del Checkout " + getLitSecondsWait(seconds) + "<br>",
			pg1DktopCheckout.isPage(seconds));
		
		checks.add(
			"Aparecen los datos introducidos:<br>" + 
			"\"Nombre: <b>" + chequeRegalo.getNombre() + "</b><br>" + 
			"\"Apellidos: <b>" + chequeRegalo.getApellidos() + "</b><br>" + 
			"\"Email: <b>" + chequeRegalo.getEmail() + "</b><br>" + 
			"\"Importe: <b>" + chequeRegalo.getImporte() + "</b><br>" + 
			"\"Mensaje: <b>" + chequeRegalo.getMensaje() + "</b>",
			pg1DktopCheckout.isDataChequeRegalo(chequeRegalo), WARN);
		
		return checks;
	}
	
	@Validation
	public ChecksTM validaResultImputPromoEmpl() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Aparece el descuento total aplicado al empleado " + getLitSecondsWait(seconds),
			pg1DktopCheckout.isVisibleDescuentoEmpleadoUntil(seconds));
	 	
		var descuento = new Descuento(app, DiscountType.EMPLEADO);
	 	checks.add(
			"Para todos los artículos, el % de descuento final es como mínimo del " + 
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + ")",
			pg1DktopCheckout.validateArticlesAndDiscount(descuento), WARN);
	 	
	 	return checks;
	}
	
	@Step (
		description="Introducir el vale <b style=\"color:blue;\">#{valePais.getCodigoVale()}</b> y pulsar el botón \"CONFIRMAR\"", 
		expected="Aparece la página de resumen de artículos con los descuentos correctamente aplicados",
		saveNettraffic=ALWAYS)
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
		return !pg1DktopCheckout.isVisibleErrorRojoInputPromoUntil(1);
	}
	@Validation(
		description="<b>Sí</b> aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\"")
	private boolean checkRedMessageInputValeVisible(ValeDiscount valePais) {
		return pg1DktopCheckout.isVisibleErrorRojoInputPromoUntil(1);
	}	
	
	@Validation
	private ChecksTM checkValeDiscountIsCorrect(ValeDiscount valePais) {
		var checks = ChecksTM.getNew();
		var descuento = new Descuento(valePais.getPorcDescuento(), app);
	 	checks.add(
			"En los artículos a los que aplica, el descuento es de " +  
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + "):" +
			dataTest.getDataBag().getListArtDescHTML(),
			pg1DktopCheckout.validateArticlesAndDiscount(descuento));
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
		level=WARN)
	private boolean checkIsVisibleInputVale(int seconds) {
		return pg1DktopCheckout.isVisibleInputCodigoPromoUntil(seconds);
	}

}