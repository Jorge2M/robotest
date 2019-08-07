package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.EnumSet;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Descuento;
import com.mng.robotest.test80.mango.test.data.Descuento.DiscountType;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class Page1DktopCheckoutStpV {
    
	@Validation
    public static ChecksResult validateIsPageOK(DataBag dataBag, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
        boolean isPageInitCheckout = Page1DktopCheckout.isPageUntil(maxSecondsWait, driver);
	 	validations.add(
			"Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSecondsWait + " segundos)",
			isPageInitCheckout, State.Warn, true);
	 	if (!isPageInitCheckout) {
		 	validations.add(
				"Si no ha aparecido la esperamos " + (maxSecondsWait * 2) + " segundos más",
				Page1DktopCheckout.isPageUntil(maxSecondsWait*2, driver), State.Defect);
	 	}
	 	validations.add(
			"Cuadran los artículos a nivel de la Referencia e Importe",
			Page1DktopCheckout.validateArticlesAndImport(dataBag, driver), State.Warn);
	 	
	 	return validations;
    }
    
	@Validation
    public static ChecksResult validateIsVersionChequeRegalo(ChequeRegalo chequeRegalo, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
    	validations.add(
    		"Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSecondsWait + " segundos)<br>",
    		Page1DktopCheckout.isPageUntil(maxSecondsWait, driver), State.Defect);
    	validations.add(
    		"Aparecen los datos introducidos:<br>" + 
    		"\"Nombre: <b>" + chequeRegalo.getNombre() + "</b><br>" + 
    		"\"Apellidos: <b>" + chequeRegalo.getApellidos() + "</b><br>" + 
    		"\"Email: <b>" + chequeRegalo.getEmail() + "</b><br>" + 
    		"\"Importe: <b>" + chequeRegalo.getImporte() + "</b><br>" + 
    		"\"Mensaje: <b>" + chequeRegalo.getMensaje() + "</b>",
    		Page1DktopCheckout.isDataChequeRegalo(chequeRegalo, driver), State.Warn);
    	return validations;
    }
    
	@Validation
    public static ChecksResult validaResultImputPromoEmpl(DataBag dataBag, AppEcom app, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"Aparece el descuento total aplicado al empleado (lo experamos hasta " + maxSecondsWait + " segundos)",
			Page1DktopCheckout.isVisibleDescuentoEmpleadoUntil(driver, maxSecondsWait), State.Defect);
	 	
    	Descuento descuento = new Descuento(app, DiscountType.Empleado);
	 	validations.add(
			"Para todos los artículos, el % de descuento final es como mínimo del " + 
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + ")",
			Page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento, driver), State.Warn);
	 	return validations;
    }
    
	@Validation (
		description="<b>Sí</b> aparece el texto del vale/campaña <b>#{valePais.getCampanya()}</b> (\"#{valePais.getTextoCheckout()}\")",
		level=State.Defect)
	public static boolean checkIsVisibleTextVale(ValePais valePais, WebDriver driver) {
		return (Page1DktopCheckout.checkTextValeCampaingIs(valePais.getTextoCheckout(), driver));
	}
	
	@Validation (
		description="<b>No</b> aparece el texto del vale/campaña <b>#{valePais.getCampanya()}</b> (\"#{valePais.getTextoCheckout()}\")",
		level=State.Defect)
	public static boolean checkIsNotVisibleTextVale(ValePais valePais, WebDriver driver) {
		return (!Page1DktopCheckout.checkTextValeCampaingIs(valePais.getTextoCheckout(), driver));
	}
	
	@Step (
		description="Introducir el vale <b style=\"color:blue;\">#{valePais.getCodigoVale()}</b> y pulsar el botón \"CONFIRMAR\"", 
        expected="Aparece la página de resumen de artículos con los descuentos correctamente aplicados",
        saveNettraffic=SaveWhen.Always)
    public static void inputValeDescuento(ValePais valePais, DataBag dataBag, AppEcom app, WebDriver driver) 
    throws Exception { 
        PageCheckoutWrapper.inputCodigoPromoAndAccept(valePais.getCodigoVale(), Channel.desktop, driver);
        dataBag.setImporteTotal(PageCheckoutWrapper.getPrecioTotalFromResumen(Channel.desktop, driver));    
        checkAfterInputDiscountVale(valePais, driver);
        if (valePais.isValid()) {
        	checkValeDiscountIsCorrect(valePais, dataBag, app, driver);
        }
        
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.Criteo,
            Constantes.AnalyticsVal.NetTraffic, 
            Constantes.AnalyticsVal.DataLayer);
        PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, analyticSet, driver);
    }
	
	@Validation
	private static ChecksResult checkAfterInputDiscountVale(ValePais valePais, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 1;
        boolean isVisibleError = Page1DktopCheckout.isVisibleErrorRojoInputPromoUntil(maxSecondsWait, driver);
        if (valePais.isValid()) {
		 	validations.add(
				"<b>No</b> aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\"",
				!isVisibleError, State.Defect);
        } else {
		 	validations.add(
				"<b>Sí</b> aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\"",
				isVisibleError, State.Defect);
        }
        
		return validations;
	}
	
	@Validation
	private static ChecksResult checkValeDiscountIsCorrect(ValePais valePais, DataBag dataBag, AppEcom app, WebDriver driver) 
	throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	Descuento descuento = new Descuento(valePais.getPorcDescuento(), app);
	 	validations.add(
			"En los artículos a los que aplica, el descuento es de " +  
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + "):" +
			dataBag.getListArtDescHTML(),
			Page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento, driver), State.Defect);
	 	return validations;
	}
    
	@Step (
		description="Si existe -> seleccionar el link \"Eliminar\" asociado al vale", 
        expected="El vale desaparece")
    public static void clearValeIfLinkExists(WebDriver driver) throws Exception {
        PageCheckoutWrapper.clickEliminarValeIfExists(Channel.desktop, driver);
        checkIsVisibleInputVale(1, driver);
    }
	
	@Validation (
		description="Aparece el input para la introducción del vale (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private static boolean checkIsVisibleInputVale(int maxSecondsWait, WebDriver driver) throws Exception {
		return (Page1DktopCheckout.isVisibleInputCodigoPromoUntil(maxSecondsWait, driver));
	}
    
    @Step (
    	description="Introducir un código de vendedor correcto #{codigoVendedor} y pulsar el botón \"Aceptar\"", 
        expected="El vendedor queda registrado")
    public static void stepIntroduceCodigoVendedorVOTF(String codigoVendedor, WebDriver driver) throws Exception {
        Page1DktopCheckout.inputVendedorVOTF(codigoVendedor, driver);
        Page1DktopCheckout.acceptInputVendedorVOTF(driver);
        checkAfterInputCodigoVendedor(codigoVendedor, driver);                
    }
    
    @Validation
    private static ChecksResult checkAfterInputCodigoVendedor(String codigoVendedor, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Desaparece el campo de Input del código de vendedor",
			!Page1DktopCheckout.isVisibleInputVendedorVOTF(driver), State.Defect);
	 	validations.add(
			"En su lugar se pinta el código de vendedor " + codigoVendedor,
			Page1DktopCheckout.isVisibleCodigoVendedorVOTF(codigoVendedor, driver), State.Defect);
	 	return validations;
    }
}