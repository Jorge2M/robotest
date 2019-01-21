package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageReembolsos;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageReembolsos.TypeReembolso;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;

/**
 * Clase que engloba los pasos/validaciones relacionados con la página de configuración de los Reembolsos
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"javadoc", "static-access"})
public class PageReembolsosStpV {

    /**
     * Step (+validación) correspondiente a la selección del menú superior "Mi cuenta" + "Reembolsos"
     * @param paisConSaldoCta indica si el país tiene configurado el saldo en cuenta
     */
    public static datosStep gotoRefundsFromMenu(boolean paisConSaldoCta, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
    	//Step
    	SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest);
    	
        //Step
        datosStep datosStep = new datosStep   (
            "Seleccionar la opción \"Reembolsos\"", 
            "Aparece la página de reembolsos");
        try {
            PageMiCuenta.clickReembolsos(dFTest.driver);
                                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
    
        //Validaciones
        String validacion2 = "";
        int maxSecondsToWait = 5;
        if (paisConSaldoCta)
            validacion2 = 
            "2) El país SÍ tiene asociado Saldo en Cuenta -> Aparecen las secciones de \"Saldo en cuenta\" y \"Transferencia bancaria\"";
        else
            validacion2 = 
            "2) El país NO tiene asociado Saldo en Cuenta -> Aparece la sección de \"Transferencia bancaria\" y no la de \"Saldo en cuenta\"";
    
        String descripValidac = 
            "1) Aparece la página de reembolsos<br>" + 
            validacion2;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageReembolsos.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (paisConSaldoCta) {
                if (!PageReembolsos.isVisibleTransferenciaSectionUntil(maxSecondsToWait, dFTest.driver) ||
                    !PageReembolsos.isVisibleStorecreditSection(dFTest.driver)) 
                    fmwkTest.addValidation(2, State.Defect, listVals);
            }
            else {
                if (!PageReembolsos.isVisibleTransferenciaSectionUntil(maxSecondsToWait, dFTest.driver) ||
                    PageReembolsos.isVisibleStorecreditSection(dFTest.driver)) 
                    fmwkTest.addValidation(2, State.Defect, listVals);
            }
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    /**
    /* Step (+validación) correspondiente a la selección del menú superior "Mi cuenta" + "Reembolsos" y finalmente valida que el saldo que aparece es el que se espera 
     * @param webdriver
     * @param saldoEsperado saldo que validaremos exista en el apartado de "Saldo en cuenta" de la página de configuración del reembolso
     */
    public static datosStep gotoRefundsFromMenuAndValidaSalCta(boolean paisConSaldoCta, float saldoCtaEsperado, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step (+validación) correspondiente a la selección del menú superior "Mi cuenta" + "Reembolsos"
        datosStep datosStep = PageReembolsosStpV.gotoRefundsFromMenu(paisConSaldoCta, app, channel, dFTest);
        
        //Validations
        String descripValidac = "1) Aparece el saldo en cuenta que esperamos <b>" + saldoCtaEsperado + "</b>"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1) Capturamos el importe de "Saldo en Cuenta"
            float saldoCtaPage = PageReembolsos.getImporteStoreCredit(dFTest.driver);
            if (saldoCtaEsperado!=saldoCtaPage)
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }

    /**
     * Ejecuta los pasos necesarios para validar la configuración de los reembolsos mediante transferencia
     */
    public static void testConfTransferencia(DataFmwkTest dFTest) throws Exception {
        //Step (+validaciones) Selección del radiobutton correspondiente a la opción de Transferencias
        PageReembolsosStpV.selectRadioTransferencia(dFTest);        
        
        //Step (+validaciones) Informa los datos de configuración del reembolso por transferencia y selecciona el botón guardar 
        PageReembolsosStpV.informaDatosTransAndSave(dFTest);
    }
    
    /**
     * Selección del radio correspondiente a la opción de Transferencias
     */
    public static datosStep selectRadioTransferencia(DataFmwkTest dFTest) throws Exception {    
        //Step
        datosStep datosStep = new datosStep   (
            "<b>Transferencias:</b> seleccionamos el radio asociado", 
            "Los campos de input se hacen visibles");
        try {
            //Seleccionamos el radio de "Transferencias"
            PageReembolsos.clickRadio(TypeReembolso.Transferencia, dFTest.driver); 
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validations
        String descripValidac = 
            "1) Los campos de input Banco, Titular e IBAN se hacen visibles"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageReembolsos.isVisibleInputsTransf(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    /**
     * Informa los datos de configuración del reembolso por transferencia y selecciona el botón guardar
     */
    public static datosStep informaDatosTransAndSave(DataFmwkTest dFTest) throws Exception {
        //Step
        String banco = "Banco de crédito Balear";
        String titular = "Jorge Muñoz";
        String IBAN = "ES8023100001180000012345";
        datosStep datosStep = new datosStep   (
            "Informar el banco: " + banco + "<br>titular: " + titular + "<br>IBAN: " + IBAN + "<br>y pulsar el botón \"Save\"", 
            "La modificación de datos se realiza correctamente");
        try {
            PageReembolsos.typeInputsTransf(dFTest.driver, banco, titular, IBAN);
            PageReembolsos.clickButtonSaveTransfForce(dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
    
        //Validaciones
        int maxSecondsToWait = 10;
        String descripValidac = 
            "1) Aparecen establecidos los datos de banco, titular e IBAN (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece seleccionado el radiobutton de \"Transferencia bancaria\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageReembolsos.isVisibleTextBancoUntil(maxSecondsToWait, dFTest.driver) ||
                !PageReembolsos.isVisibleTextTitular(dFTest.driver) ||
                !PageReembolsos.isVisibleTextIBAN(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageReembolsos.isCheckedRadio(TypeReembolso.Transferencia, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    /**
     * Selección del radio de Saldo en Cuenta + refresh de la página
     */
    public static datosStep selectRadioSalCtaAndRefresh(DataFmwkTest dFTest) {
        //Step
        datosStep datosStep = new datosStep   (
            "<b>Store Credit:</b> seleccionamos el radio asociado y ejecutamos un refresh de la página", 
            "El checkbox de \"Store Credit\" acaba marcado");
        try {
            PageReembolsos.clickRadio(TypeReembolso.StoreCredit, dFTest.driver); 
            
            //Realizamos un refresh de la página
            //webdriver.navigate().refresh();
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        String descripValidac = 
            "1) Aparece seleccionado el radiobutton de \"Store Credit\"<br>" + 
            "2) Aparece un saldo >= 0";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!PageReembolsos.isCheckedRadio(TypeReembolso.StoreCredit, dFTest.driver))
               fmwkTest.addValidation(1,State.Warn, listVals);
           //2)
           if (PageReembolsos.getImporteStoreCredit(dFTest.driver) < 0)
               fmwkTest.addValidation(2, State.Defect, listVals);
           
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    /**
     * En ocasiones (principalmente en el casdo del mock) existe un botón "Guardar" que hay que seleccionar para que se active el saldo en cuenta
     */
    public static void clickSaveButtonStoreCreditIfExists(DataFmwkTest dFTest) throws Exception {
        if (PageReembolsos.isVisibleSaveButtonStoreCredit(dFTest.driver)) {
            //Step
            datosStep datosStep = new datosStep   (
                "<b>Store Credit:</b> Seleccionamos el botón \"Save\"", 
                "Desaparece el botón \"Save\"");
            try {
                PageReembolsos.clickSaveButtonStoreCredit(dFTest.driver); 
                
                datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
            }
            finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
            //Validaciones
            int maxSecondsToWait = 2;
            String descripValidac = 
                "1) Desaparece el botón \"Save\" de Store Credit (lo esperamos hasta " + maxSecondsToWait + " segundos)";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
            try {
               List<SimpleValidation> listVals = new ArrayList<>();
               //1)
               if (PageReembolsos.isVisibleSaveButtonStoreCreditUntil(maxSecondsToWait, dFTest.driver))
                   fmwkTest.addValidation(1,State.Warn, listVals);
               
               datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
    }
}
