package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjMobil;

public class PageMercpagoDatosTrjStpV {
	
	public static class InputData {
		public String numTarjeta;
		public String banco;
		public String nombreYApellido;
		public String mesVencimiento;
		public String anyVencimiento;
		public String codigoSeguridad;
	}
    
    public static void validaIsPage(Channel channel, WebDriver driver) {
        switch (channel) {
        case movil_web:
            validaIsPageMobil(driver);
            break;
        default:
        case desktop:
        	int maxSecondsWait = 5;
            validaIsPageDesktop(maxSecondsWait, driver);
        }
    }
    
    @Validation (
    	description="Estamos en la página de introducción de los datos de la tarjeta",
    	level=State.Defect)
    public static boolean validaIsPageMobil(WebDriver driver) {
    	return (PageMercpagoDatosTrjMobil.isPage(driver));
    }
    
    @Validation (
    	description="Estamos en la página de introducción del CVC (la esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Defect)
    public static boolean validaIsPageDesktop(int maxSecondsWait, WebDriver driver) {
    	return (PageMercpagoDatosTrjDesktop.isPageUntil(maxSecondsWait, driver));
    }    
    
    public static void inputNumTarjeta(String numTarjeta, Channel channel, WebDriver driver) throws Exception {
        switch (channel) {
        case movil_web:
            inputNumTarjetaMobil(numTarjeta, driver);
            break;
        default:
        case desktop:
            inputNumTarjetaDesktop(numTarjeta, driver);
            break;
        }
    }
    
    @Step (
    	description="Introducir el número de tarjeta #{numTarjeta}", 
        expected="El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa")
    public static void inputNumTarjetaMobil(String numTarjeta, WebDriver driver) throws Exception {
        PageMercpagoDatosTrjMobil.sendNumTarj(numTarjeta, driver);
            
        //Validaciones
        int maxSecondsWait = 2;
        isWrapperTarjetaVisibleVisaDataMobil(maxSecondsWait, driver);
    }
    
    @Validation (
    	description="El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean isWrapperTarjetaVisibleVisaDataMobil(int maxSecondsWait, WebDriver driver) {
	     return (PageMercpagoDatosTrjMobil.isActiveWrapperVisaUntil(maxSecondsWait, driver));
    }
    
    @Step (
    	description="Introducir el número de tarjeta #{numTarjeta})", 
        expected="Aparece el icono de Visa a la derecha de la tarjeta introducida")
    public static void inputNumTarjetaDesktop(String numTarjeta, WebDriver driver) throws Exception {
    	PageMercpagoDatosTrjDesktop.sendNumTarj(numTarjeta, driver);
            
        //Validaciones
    	int maxSecondsWait = 2;
    	isVisaIconAtRightTrjDesktop(maxSecondsWait, driver);
    }
    
    @Validation (
    	description="Aparece el icono de Visa a la derecha de la tarjeta (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean isVisaIconAtRightTrjDesktop(int maxSecondsWait, WebDriver driver) {
    	return (PageMercpagoDatosTrjDesktop.isVisibleVisaIconUntil(maxSecondsWait, driver));
    }
    
    public static DatosStep inputDataAndPay(InputData inputData, Channel channel, WebDriver driver) 
    throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        switch (channel) {
        case movil_web:
            inputDataMobil(inputData, dFTest);
            return clickButtonPayMobil(dFTest);
        default:
        case desktop:
            return inputDataAndPayDesktop(inputData, dFTest);
        }
    }
    
    public static DatosStep inputDataMobil(InputData inputData, DataFmwkTest dFTest) throws Exception {
        String fechaVencimiento = inputData.mesVencimiento + "/" + inputData.anyVencimiento;        
        DatosStep datosStep = new DatosStep (
            "Introducir la fecha de vencimiento (" + fechaVencimiento + "), " + 
            "el banco (" + inputData.banco + ") " + 
            "security code (" + inputData.codigoSeguridad + ") " + 
            "y confirmar", 
            "Aparece la página de resultado");
        try {
            PageMercpagoDatosTrjMobil.sendCaducidadTarj(fechaVencimiento, dFTest.driver);
            PageMercpagoDatosTrjMobil.sendCVC(inputData.codigoSeguridad, dFTest.driver);            
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
            
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece activado el botón \"Next\" para continuar con el pago (lo esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageMercpagoDatosTrjMobil.isClickableButtonNextPayUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static DatosStep clickButtonPayMobil(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Next\" para pagar", 
            "Aparece la página de resultado");
        try {
            PageMercpagoDatosTrjMobil.clickButtonNextPay(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
            
        //Validaciones
        PageMercpagoConfStpV.validaIsPage(Channel.movil_web, datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static DatosStep inputDataAndPayDesktop(InputData inputData, DataFmwkTest dFTest) 
    throws Exception {
        String fechaVencimiento = inputData.mesVencimiento + "/" + inputData.anyVencimiento;     
        DatosStep datosStep = new DatosStep (
            "Introducir la fecha de vencimiento (" + fechaVencimiento + "), " + 
            "security code (" + inputData.codigoSeguridad + "), " + 
            "Banco (" + inputData.banco  + ") " +
            "y confirmar", 
            "Aparece la página de resultado");
        try {
            PageMercpagoDatosTrjDesktop.sendCaducidadTarj(fechaVencimiento, dFTest.driver);
            PageMercpagoDatosTrjDesktop.sendCVC(inputData.codigoSeguridad, dFTest.driver);
            PageMercpagoDatosTrjDesktop.selectBanco(inputData.banco, dFTest.driver);
            PageMercpagoDatosTrjDesktop.clickBotonContinuar(dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
            
        //Validaciones
        PageMercpagoConfStpV.validaIsPage(Channel.desktop, datosStep, dFTest);
        
        return datosStep;
    }
}
