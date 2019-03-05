package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.EnumSet;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.Descuento;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.Descuento.DiscountType;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class Page1DktopCheckoutStpV {
    
	@Validation
    public static ListResultValidation validateIsPageOK(DataBag dataBag, WebDriver driver) throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 5;
        boolean isPageInitCheckout = Page1DktopCheckout.isPageUntil(maxSecondsWait, driver);
	 	validations.add(
			"Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSecondsWait + " segundos)<br>",
			isPageInitCheckout, State.Warn_NoHardcopy);
	 	if (!isPageInitCheckout) {
		 	validations.add(
				"Si no ha aparecido la esperamos " + (maxSecondsWait * 2) + " segundos más<br>",
				Page1DktopCheckout.isPageUntil(maxSecondsWait*2, driver), State.Defect);
	 	}
	 	validations.add(
			"Cuadran los artículos a nivel de la Referencia e Importe",
			Page1DktopCheckout.validateArticlesAndImport(dataBag, driver), State.Warn);
	 	
	 	return validations;
    }
    
    public static void validateIsVersionChequeRegalo(ChequeRegalo chequeRegalo, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página inicial del Checkout (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparecen los datos introducidos:<br>" +
              "Nombre: <b>" + chequeRegalo.getNombre() + "</b><br>" +
              "Apellidos: <b>" + chequeRegalo.getApellidos() + "</b><br>" +
              "Email: <b>" + chequeRegalo.getEmail() + "</b><br>" +
              "Importe: <b>" + chequeRegalo.getImporte() + "</b><br>" +
              "Mensaje: <b>" + chequeRegalo.getMensaje() + "</b>"; 
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!Page1DktopCheckout.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!Page1DktopCheckout.isDataChequeRegalo(chequeRegalo, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void validaResultImputPromoEmpl(DataBag dataBag, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
    	Descuento descuento = new Descuento(app, DiscountType.Empleado);
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece el descuento total aplicado al empleado (lo experamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Para todos los artículos, el % de descuento final es como mínimo del " + 
            	descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + ")";
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!Page1DktopCheckout.isVisibleDescuentoEmpleadoUntil(dFTest.driver, maxSecondsToWait)) {
                listVals.add(1, State.Defect);
            }
            if (!Page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
    }
    
    /**
     * Introduce un vale de descuento activo y comprueba el resultado
     */
    public static void inputValeDescuento(ValePais valePais, DataBag dataBag, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Introducir el vale <b style=\"color:blue;\">" + valePais.getCodigoVale() + "</b> y pulsar el botón \"CONFIRMAR\"", 
            "Aparece la página de resumen de artículos con los descuentos correctamente aplicados" /*Resultado esperado*/);
        datosStep.setSaveNettrafic(SaveWhen.Always, dFTest.ctx);
        datosStep.setSaveImagePage(SaveWhen.Never);
        try {   
            PageCheckoutWrapper.inputCodigoPromoAndAccept(valePais.getCodigoVale(), Channel.desktop, dFTest.driver);
            dataBag.setImporteTotal(PageCheckoutWrapper.getPrecioTotalFromResumen(Channel.desktop, dFTest.driver));
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }         

        //Validaciones
        String validacion2 = "";
        int maxSecondsToWait1 = 1;
        int maxSecondsToWait2 = 2;
        if (valePais.isValid())
            validacion2 = 
            	"NO aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\" " +
            	"(lo esperamos hasta " + maxSecondsToWait1 + " segundos)";
        else
            validacion2 = 
            	"SÍ aparece mensaje de error en rojo (rgba(255, 0, 0, 1) en el bloque correspondiente al \"Código promocional\" " +
            	"(lo esperamos hasta " + maxSecondsToWait2 + " segundos)";
        
        String descripValidac = "1) " + validacion2;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (valePais.isValid()) {
                if (Page1DktopCheckout.isVisibleErrorRojoInputPromoUntil(maxSecondsToWait1, dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
            }
            else {
                if (!Page1DktopCheckout.isVisibleErrorRojoInputPromoUntil(maxSecondsToWait2, dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }                

        if (valePais.isValid()) {
            //Validaciones
        	Descuento descuento = new Descuento(valePais.getPorcDescuento(), app);
            descripValidac = 
                "1) En los artículos a los que aplica, el descuento es de " + 
                    descuento.getPercentageDesc() + "% (" + descuento.getDiscountOver().getDescription() + "): <br>" +
                	dataBag.getListArtDescHTML();
            datosStep.setNOKstateByDefault();
            listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!Page1DktopCheckout.validateArticlesAndDiscount(dataBag, descuento, dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }

                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
            
            //Validaciones estándar. 
            //AllPagesStpV.validacionesEstandar(false/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);            
        }
        
        //VALIDACIONES - PARA ANALYTICS (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.Criteo,
            Constantes.AnalyticsVal.NetTraffic, 
            Constantes.AnalyticsVal.DataLayer);
        PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, analyticSet, dFTest.driver);
    }
    
    public static void clearValeIfLinkExists(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Si existe -> seleccionar el link \"Eliminar\" asociado al vale", 
            "El vale desaparece");
        try {   
        	PageCheckoutWrapper.clickEliminarValeIfExists(Channel.desktop, dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
                        
        //Validaciones
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece el input para la introducción del vale (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!Page1DktopCheckout.isVisibleInputCodigoPromoUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * Pasos/Validaciones para la introducción de un código de vendedor en VOTF
     */
    public static void stepIntroduceCodigoVendedorVOTF(String codigoVendedor, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducir un código de vendedor correcto " + codigoVendedor + " y pulsar el botón \"Aceptar\"", 
            "El vendedor queda registrado");
        try {   
            Page1DktopCheckout.inputVendedorVOTF(codigoVendedor, dFTest.driver);
            Page1DktopCheckout.acceptInputVendedorVOTF(dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
                        
        //Validaciones.
        String descripValidac = 
            "1) Desaparece el campo de Input del código de vendedor<br>" +
            "2) En su lugar se pinta el código de vendedor " + codigoVendedor;
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (Page1DktopCheckout.isVisibleInputVendedorVOTF(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!Page1DktopCheckout.isVisibleCodigoVendedorVOTF(codigoVendedor, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}