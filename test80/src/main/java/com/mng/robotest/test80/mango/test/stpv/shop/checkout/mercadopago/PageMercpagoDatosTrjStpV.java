package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
    
    public static void validaIsPage(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        switch (channel) {
        case movil_web:
            validaIsPageMobil(datosStep, dFTest);
            break;
        default:
        case desktop:
            validaIsPageDesktop(datosStep, dFTest);
        }
    }
    
    public static void validaIsPageMobil(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Estamos en la página de introducción de los datos de la tarjeta";   
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageMercpagoDatosTrjMobil.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void validaIsPageDesktop(DatosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Estamos en la página de introducción del CVC (la esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageMercpagoDatosTrjDesktop.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }    
    
    public static DatosStep inputNumTarjeta(String numTarjeta, Channel channel, DataFmwkTest dFTest) throws Exception {
        switch (channel) {
        case movil_web:
            return inputNumTarjetaMobil(numTarjeta, dFTest);
        default:
        case desktop:
            return inputNumTarjetaDesktop(numTarjeta, dFTest);
        }
    }
    
    public static DatosStep inputNumTarjetaMobil(String numTarjeta, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducir el número de tarjeta (" + numTarjeta + ")", 
            "El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa");
        try {
            PageMercpagoDatosTrjMobil.sendNumTarj(numTarjeta, dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
            
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa (lo esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageMercpagoDatosTrjMobil.isActiveWrapperVisaUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static DatosStep inputNumTarjetaDesktop(String numTarjeta, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducir el número de tarjeta (" + numTarjeta + ")", 
            "Aparece el icono de Visa a la derecha de la tarjeta introducida");
        try {
            PageMercpagoDatosTrjDesktop.sendNumTarj(numTarjeta, dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
            
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece el icono de Visa a la derecha de la tarjeta (lo esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageMercpagoDatosTrjDesktop.isVisibleVisaIconUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static DatosStep inputDataAndPay(InputData inputData, Channel channel, DataFmwkTest dFTest) 
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
