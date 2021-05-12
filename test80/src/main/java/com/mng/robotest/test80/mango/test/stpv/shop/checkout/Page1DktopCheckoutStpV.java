package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.Arrays;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Descuento;
import com.mng.robotest.test80.mango.test.data.Descuento.DiscountType;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class Page1DktopCheckoutStpV {
    
	private final Page1DktopCheckout page1DktopCheckout;
	private final Channel channel;
	private final AppEcom app;
	private final WebDriver driver;
	
	public Page1DktopCheckoutStpV(Channel channel, AppEcom app, WebDriver driver) {
		this.page1DktopCheckout = new Page1DktopCheckout(channel, app, driver);
		this.channel = channel;
		this.app = app;
		this.driver = driver;
	}
	
	@Validation
    public ChecksTM validateIsPageOK(DataBag dataBag) throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
        boolean isPageInitCheckout = page1DktopCheckout.isPageUntil(maxSeconds);
	 	validations.add(
			"Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSeconds + " segundos)",
			isPageInitCheckout, State.Warn, true);
	 	if (!isPageInitCheckout) {
		 	validations.add(
				"Si no ha aparecido la esperamos " + (maxSeconds * 2) + " segundos más",
				page1DktopCheckout.isPageUntil(maxSeconds*2), State.Defect);
	 	}
	 	validations.add(
			"Cuadran los artículos a nivel de la Referencia e Importe",
			page1DktopCheckout.validateArticlesAndImport(dataBag), State.Warn);
	 	
	 	return validations;
    }
    
	@Validation
    public ChecksTM validateIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
		ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
    	validations.add(
    		"Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSeconds + " segundos)<br>",
    		page1DktopCheckout.isPageUntil(maxSeconds), State.Defect);
    	validations.add(
    		"Aparecen los datos introducidos:<br>" + 
    		"\"Nombre: <b>" + chequeRegalo.getNombre() + "</b><br>" + 
    		"\"Apellidos: <b>" + chequeRegalo.getApellidos() + "</b><br>" + 
    		"\"Email: <b>" + chequeRegalo.getEmail() + "</b><br>" + 
    		"\"Importe: <b>" + chequeRegalo.getImporte() + "</b><br>" + 
    		"\"Mensaje: <b>" + chequeRegalo.getMensaje() + "</b>",
    		page1DktopCheckout.isDataChequeRegalo(chequeRegalo), State.Warn);
    	return validations;
    }
    
	@Validation
    public ChecksTM validaResultImputPromoEmpl(DataBag dataBag) throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
	 	validations.add(
			"Aparece el descuento total aplicado al empleado (lo experamos hasta " + maxSeconds + " segundos)",
			page1DktopCheckout.isVisibleDescuentoEmpleadoUntil(maxSeconds), State.Defect);
	 	
    	Descuento descuento = new Descuento(app, DiscountType.Empleado);
	 	validations.add(
			"Para todos los artículos, el % de descuento final es como mínimo del " + 
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + ")",
			page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento), State.Warn);
	 	return validations;
    }
    
	@Validation (
		description="<b>Sí</b> aparece el texto del vale/campaña <b>#{valePais.getCampanya()}</b> (\"#{valePais.getTextoCheckout()}\")",
		level=State.Defect)
	public boolean checkIsVisibleTextVale(ValePais valePais) {
		return (page1DktopCheckout.checkTextValeCampaingIs(valePais.getTextoCheckout()));
	}
	
	@Validation (
		description="<b>No</b> aparece el texto del vale/campaña <b>#{valePais.getCampanya()}</b> (\"#{valePais.getTextoCheckout()}\")",
		level=State.Defect)
	public boolean checkIsNotVisibleTextVale(ValePais valePais) {
		return (!page1DktopCheckout.checkTextValeCampaingIs(valePais.getTextoCheckout()));
	}
	
	@Step (
		description="Introducir el vale <b style=\"color:blue;\">#{valePais.getCodigoVale()}</b> y pulsar el botón \"CONFIRMAR\"", 
        expected="Aparece la página de resumen de artículos con los descuentos correctamente aplicados",
        saveNettraffic=SaveWhen.Always)
    public void inputValeDescuento(ValePais valePais, DataBag dataBag) throws Exception { 
		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
        pageCheckoutWrapper.inputCodigoPromoAndAccept(valePais.getCodigoVale());
        dataBag.setImporteTotal(pageCheckoutWrapper.getPrecioTotalFromResumen());    
        checkAfterInputDiscountVale(valePais);
        if (valePais.isValid()) {
        	checkValeDiscountIsCorrect(valePais, dataBag);
        }
        
		GenericChecks.from(Arrays.asList(
				GenericCheck.GoogleAnalytics,
				GenericCheck.Analitica,
				GenericCheck.NetTraffic)).checks(driver);
    }
	
	@Validation
	private ChecksTM checkAfterInputDiscountVale(ValePais valePais) {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 1;
        boolean isVisibleError = page1DktopCheckout.isVisibleErrorRojoInputPromoUntil(maxSeconds);
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
	private ChecksTM checkValeDiscountIsCorrect(ValePais valePais, DataBag dataBag) throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
    	Descuento descuento = new Descuento(valePais.getPorcDescuento(), app);
	 	validations.add(
			"En los artículos a los que aplica, el descuento es de " +  
			descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + "):" +
			dataBag.getListArtDescHTML(),
			page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento), State.Defect);
	 	return validations;
	}
    
	@Step (
		description="Si existe -> seleccionar el link \"Eliminar\" asociado al vale", 
        expected="El vale desaparece")
    public void clearValeIfLinkExists() throws Exception {
		new PageCheckoutWrapper(channel, app, driver).clickEliminarValeIfExists();
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
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Desaparece el campo de Input del código de vendedor",
			!page1DktopCheckout.isVisibleInputVendedorVOTF(), State.Defect);
	 	validations.add(
			"En su lugar se pinta el código de vendedor " + codigoVendedor,
			page1DktopCheckout.isVisibleCodigoVendedorVOTF(codigoVendedor), State.Defect);
	 	return validations;
    }
}