package com.mng.robotest.test.steps.shop.checkout;

import java.util.Arrays;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.data.Descuento;
import com.mng.robotest.test.data.Descuento.DiscountType;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.generic.beans.ValeDiscount;
import com.mng.robotest.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class Page1DktopCheckoutSteps extends StepBase {
	
	private final Page1DktopCheckout page1DktopCheckout = new Page1DktopCheckout();
	
	@Validation
	public ChecksTM validateIsPageOK(DataBag dataBag) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		boolean isPageInitCheckout = page1DktopCheckout.isPageUntil(maxSeconds);
	 	checks.add(
	 		Check.make(
			    "Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSeconds + " segundos)",
			    isPageInitCheckout, State.Warn)
	 		.store(StoreType.None).build());
	 	
	 	if (!isPageInitCheckout) {
		 	checks.add(
				"Si no ha aparecido la esperamos " + (maxSeconds * 2) + " segundos más",
				page1DktopCheckout.isPageUntil(maxSeconds*2), State.Defect);
	 	}
	 	checks.add(
			"Cuadran los artículos a nivel de la Referencia e Importe",
			page1DktopCheckout.validateArticlesAndImport(dataBag), State.Warn);
	 	
	 	return checks;
	}
	
	@Validation
	public ChecksTM validateIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		checks.add(
			"Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSeconds + " segundos)<br>",
			page1DktopCheckout.isPageUntil(maxSeconds), State.Defect);
		
		checks.add(
			"Aparecen los datos introducidos:<br>" + 
			"\"Nombre: <b>" + chequeRegalo.getNombre() + "</b><br>" + 
			"\"Apellidos: <b>" + chequeRegalo.getApellidos() + "</b><br>" + 
			"\"Email: <b>" + chequeRegalo.getEmail() + "</b><br>" + 
			"\"Importe: <b>" + chequeRegalo.getImporte() + "</b><br>" + 
			"\"Mensaje: <b>" + chequeRegalo.getMensaje() + "</b>",
			page1DktopCheckout.isDataChequeRegalo(chequeRegalo), State.Warn);
		
		return checks;
	}
	
	@Validation
	public ChecksTM validaResultImputPromoEmpl(DataBag dataBag) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
	 	checks.add(
			"Aparece el descuento total aplicado al empleado (lo experamos hasta " + maxSeconds + " segundos)",
			page1DktopCheckout.isVisibleDescuentoEmpleadoUntil(maxSeconds), State.Defect);
	 	
		Descuento descuento = new Descuento(app, DiscountType.Empleado);
	 	checks.add(
			"Para todos los artículos, el % de descuento final es como mínimo del " + 
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + ")",
			page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento), State.Warn);
	 	
	 	return checks;
	}
	
	@Validation (
		description="<b>Sí</b> aparece el texto del vale <b>#{valePais.getCodigoVale()}</b> (\"#{valePais.getTextoCheckout()}\")",
		level=State.Defect)
	public boolean checkIsVisibleTextVale(ValeDiscount valePais) {
		return (page1DktopCheckout.checkTextValeCampaingIs(valePais.getTextoCheckout()));
	}
	
	@Validation (
		description="<b>No</b> aparece el texto del vale <b>#{valePais.getCodigoVale()}</b> (\"#{valePais.getTextoCheckout()}\")",
		level=State.Defect)
	public boolean checkIsNotVisibleTextVale(ValeDiscount valePais) {
		return (!page1DktopCheckout.checkTextValeCampaingIs(valePais.getTextoCheckout()));
	}
	
	@Step (
		description="Introducir el vale <b style=\"color:blue;\">#{valePais.getCodigoVale()}</b> y pulsar el botón \"CONFIRMAR\"", 
		expected="Aparece la página de resumen de artículos con los descuentos correctamente aplicados",
		saveNettraffic=SaveWhen.Always)
	public void inputValeDescuento(ValeDiscount valePais, DataBag dataBag) throws Exception { 
		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper();
		pageCheckoutWrapper.inputCodigoPromoAndAccept(valePais.getCodigoVale());
		dataBag.setImporteTotal(pageCheckoutWrapper.getPrecioTotalFromResumen());	
		checkAfterInputDiscountVale(valePais);
		checkValeDiscountIsCorrect(valePais, dataBag);
		
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(
				GenericCheck.GoogleAnalytics,
				GenericCheck.NetTraffic)).checks();
	}
	
	@Validation
	private ChecksTM checkAfterInputDiscountVale(ValeDiscount valePais) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 1;
		boolean isVisibleError = page1DktopCheckout.isVisibleErrorRojoInputPromoUntil(maxSeconds);
	 	checks.add(
			"<b>No</b> aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\"",
			!isVisibleError, State.Defect);
		
		return checks;
	}
	
	@Validation
	private ChecksTM checkValeDiscountIsCorrect(ValeDiscount valePais, DataBag dataBag) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		Descuento descuento = new Descuento(valePais.getPorcDescuento(), app);
	 	checks.add(
			"En los artículos a los que aplica, el descuento es de " +  
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + "):" +
			dataBag.getListArtDescHTML(),
			page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento), State.Defect);
	 	return checks;
	}
	
	@Step (
		description="Si existe -> seleccionar el link \"Eliminar\" asociado al vale", 
		expected="El vale desaparece")
	public void clearValeIfLinkExists() throws Exception {
		new PageCheckoutWrapper().clickEliminarValeIfExists();
		checkIsVisibleInputVale(1);
	}
	
	@Validation (
		description="Aparece el input para la introducción del vale (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkIsVisibleInputVale(int maxSeconds) throws Exception {
		return (page1DktopCheckout.isVisibleInputCodigoPromoUntil(maxSeconds));
	}
	
	@Step (
		description="Introducir un código de vendedor correcto #{codigoVendedor} y pulsar el botón \"Aceptar\"", 
		expected="El vendedor queda registrado")
	public void stepIntroduceCodigoVendedorVOTF(String codigoVendedor) throws Exception {
		page1DktopCheckout.inputVendedorVOTF(codigoVendedor);
		page1DktopCheckout.acceptInputVendedorVOTF();
		checkAfterInputCodigoVendedor(codigoVendedor);				
	}
	
	@Validation
	private ChecksTM checkAfterInputCodigoVendedor(String codigoVendedor) {
		int maxSeconds = 3;
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Desaparece el campo de Input del código de vendedor (lo esperamos hasta " + maxSeconds + " segundos)",
			!page1DktopCheckout.isVisibleInputVendedorVOTF(maxSeconds), State.Defect);
	 	
	 	checks.add(
			"En su lugar se pinta el código de vendedor " + codigoVendedor,
			page1DktopCheckout.isVisibleCodigoVendedorVOTF(codigoVendedor), State.Defect);
	 	
	 	return checks;
	}
}