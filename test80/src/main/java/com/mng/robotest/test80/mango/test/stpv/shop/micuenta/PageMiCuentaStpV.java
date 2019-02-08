package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.HashMap;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;

@SuppressWarnings({"javadoc", "static-access"})
public class PageMiCuentaStpV {
    
	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		int maxSecondsToWait = 2;
	    String descripValidac = 
	        "1) Aparece la página de \"Mi Cuenta\" (la esperamos hasta " + maxSecondsToWait + " segundos)";
	    datosStep.setStateIniValidations();     
	    ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try {
	        if (!PageMiCuenta.isPageUntil(maxSecondsToWait, dFTest.driver)) {
	            listVals.add(1, State.Defect);
	        }
	
	        datosStep.setListResultValidations(listVals);
	    }
	    finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
    public static DatosStep goToMisDatos(String usuarioReg, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
    	//Step
    	SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest);
    	
        //Step.
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el link \"Mis datos\"", 
            "Aparece la página de \"Mis datos\"");
        try {
            PageMiCuenta.clickMisDatos(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }             
        
        //Validaciones
        PageMisDatosStpV.validaIsPage(usuarioReg, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep goToMisComprasFromMenu(DataCtxShop dataCtxShop, Channel channel, DataFmwkTest dFTest) throws Exception {
    	//Step
    	SecMenusUserStpV.clickMenuMiCuenta(channel, dataCtxShop.appE, dFTest);
    	
        //Step.
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el link \"Mis Compras\"", 
            "Aparece la página de \"Mis Compras\"");
        try {
            PageMiCuenta.clickMisCompras(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }             
        
        if (channel==Channel.movil_web) {
            //En el caso de móvil_web aparece una página intermedia "New!"
            PageInfoNewMisComprasMovilStpV.validateIsPage(datosStep, dFTest);
            datosStep = PageInfoNewMisComprasMovilStpV.clickButtonToMisComprasAndNoValidate(dFTest);
        }
        
        //Validaciones
        PageMisComprasStpV.validateIsPage(dataCtxShop, datosStep, dFTest);
        
        return datosStep;
    }    
 
    public static DatosStep goToMisDatosAndValidateData(HashMap<String,String> dataRegistro, String codPais, AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = goToMisDatos(dataRegistro.get("cfEmail"), app, channel, dFTest);
        
        //Validaciones.
        PageMisDatosStpV.validaIsDataAssociatedToRegister(dataRegistro, codPais, datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static DatosStep goToSuscripciones(AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
    	//Step
    	SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest);
    	
    	//Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el link \"Suscripciones\"", 
            "Aparece la página de \"Suscripciones\"" /*Resultado esperado*/);
        try {
            PageMiCuenta.clickSuscripciones(dFTest.driver); 
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageSuscripcionesStpV.validaIsPage(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void goToSuscripcionesAndValidateData(HashMap<String,String> datosRegOk, AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = goToSuscripciones(app, channel, dFTest);
        
        //Validaciones
        PageSuscripcionesStpV.validaIsDataAssociatedToRegister(datosRegOk, datosStep, dFTest);
    }    
    
    public static DatosStep goToMisPedidos(String usrRegistrado, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
    	//Step
    	SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest);
    	
    	//Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el link \"Mis pedidos\"", 
            "Aparece la página de \"Mis pedidos\" sin pedidos");
        try {
            PageMiCuenta.clickMisPedidos(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones.
        PagePedidosStpV.validaIsPageSinPedidos(usrRegistrado, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep goToDevoluciones(AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
    	//Step
    	SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest);
    	
    	//Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el link \"Devoluciones\"", 
            "Aparece la página de \"Devoluciones\"");
        try {
            PageMiCuenta.clickDevoluciones(dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
                
        //Validaciones
        PageDevolucionesStpV.validaIsPage(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep goToReembolsos(AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
    	//Step
    	SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest);
    	
    	//Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el link \"Reembolsos\"", 
            "Aparece la página de \"Reembolsos\"");
        try {
            PageMiCuenta.clickReembolsos(dFTest.driver);
                                                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
                
        //Validaciones
        PageReembolsosStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
