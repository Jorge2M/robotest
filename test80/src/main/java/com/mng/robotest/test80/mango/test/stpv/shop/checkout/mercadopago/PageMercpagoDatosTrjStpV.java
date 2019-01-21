package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjMobil;

@SuppressWarnings("javadoc")
public class PageMercpagoDatosTrjStpV {
	
	public static class InputData {
		public String numTarjeta;
		public String banco;
		public String nombreYApellido;
		public String mesVencimiento;
		public String anyVencimiento;
		public String codigoSeguridad;
	}
    
    public static void validaIsPage(Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        switch (channel) {
        case movil_web:
            validaIsPageMobil(datosStep, dFTest);
            break;
        default:
        case desktop:
            validaIsPageDesktop(datosStep, dFTest);
        }
    }
    
    public static void validaIsPageMobil(datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Estamos en la página de introducción de los datos de la tarjeta";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);   
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            if (!PageMercpagoDatosTrjMobil.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validaIsPageDesktop(datosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Estamos en la página de introducción del CVC (la esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);   
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            if (!PageMercpagoDatosTrjDesktop.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }    
    
    public static datosStep inputNumTarjeta(String numTarjeta, Channel channel, DataFmwkTest dFTest) throws Exception {
        switch (channel) {
        case movil_web:
            return inputNumTarjetaMobil(numTarjeta, dFTest);
        default:
        case desktop:
            return inputNumTarjetaDesktop(numTarjeta, dFTest);
        }
    }
    
    public static datosStep inputNumTarjetaMobil(String numTarjeta, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
            "Introducir el número de tarjeta (" + numTarjeta + ")", 
            "El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa");
        try {
            PageMercpagoDatosTrjMobil.sendNumTarj(numTarjeta, dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa (lo esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);   
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            if (!PageMercpagoDatosTrjMobil.isActiveWrapperVisaUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static datosStep inputNumTarjetaDesktop(String numTarjeta, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
            "Introducir el número de tarjeta (" + numTarjeta + ")", 
            "Aparece el icono de Visa a la derecha de la tarjeta introducida");
        try {
            PageMercpagoDatosTrjDesktop.sendNumTarj(numTarjeta, dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece el icono de Visa a la derecha de la tarjeta (lo esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);   
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            if (!PageMercpagoDatosTrjDesktop.isVisibleVisaIconUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static datosStep inputDataAndPay(InputData inputData, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        switch (channel) {
        case movil_web:
            inputDataMobil(inputData, dFTest);
            return clickButtonPayMobil(dFTest);
        default:
        case desktop:
            return inputDataAndPayDesktop(inputData, dFTest);
        }
    }
    
    public static datosStep inputDataMobil(InputData inputData, DataFmwkTest dFTest) throws Exception {
        String fechaVencimiento = inputData.mesVencimiento + "/" + inputData.anyVencimiento;        
        datosStep datosStep = new datosStep (
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece activado el botón \"Next\" para continuar con el pago (lo esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);   
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            if (!PageMercpagoDatosTrjMobil.isClickableButtonNextPayUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static datosStep clickButtonPayMobil(DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep (
            "Seleccionar el botón \"Next\" para pagar", 
            "Aparece la página de resultado");
        try {
            PageMercpagoDatosTrjMobil.clickButtonNextPay(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //Validaciones
        PageMercpagoConfStpV.validaIsPage(Channel.movil_web, datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static datosStep inputDataAndPayDesktop(InputData inputData, DataFmwkTest dFTest) 
    throws Exception {
        String fechaVencimiento = inputData.mesVencimiento + "/" + inputData.anyVencimiento;     
        datosStep datosStep = new datosStep (
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //Validaciones
        PageMercpagoConfStpV.validaIsPage(Channel.desktop, datosStep, dFTest);
        
        return datosStep;
    }
}
