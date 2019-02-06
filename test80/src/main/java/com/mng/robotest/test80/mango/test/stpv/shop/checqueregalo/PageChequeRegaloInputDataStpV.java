package com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.*;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

@SuppressWarnings("javadoc")
public class PageChequeRegaloInputDataStpV{

    public static void paginaConsultarSaldo(DataFmwkTest dFTest, String numTarjeta) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
        	"Accedemos a la página de consultar saldo de cheque regalo con sus respectivos campos",
            "Aparecen todos los campos y volvemos a la operativa normal");
        try{
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.ir, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep,dFTest)); }

        //Validaciones
        int maxSecondsWait = 2;
        String descripValidac =
        	"1) Aparece el cuadro de introduccion de datos determinado (lo esperamos hasta " + maxSecondsWait + " segundos)<br>" +
            "2) Podemos validar la tarjeta<br>" +
            "3) Podemos volver atrás";
        try {
            List<SimpleValidation> listVals = new ArrayList<>();

            //1
            if(!PageChequeRegaloInputData.
            	isElementInStateUntil(ConsultaSaldo.numeroTarjeta, StateElem.Present, maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2
            if(!PageChequeRegaloInputData.
            	isElementInState(ConsultaSaldo.validar, StateElem.Present, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3
            if(!PageChequeRegaloInputData.
            	isElementInState(ConsultaSaldo.volver, StateElem.Present, dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

        //Step
        datosStep datosStep2 = new datosStep    (
        	"Introducimos en el campo de <b>tarjeta regalo</b> " + numTarjeta + " para consultar el saldo",
            "Se carga la página donde salen nuevos campos visibles como el de <b>cvv</b>");
        try {
            PageChequeRegaloInputData.introducirTarjetaConsultaSaldo(dFTest.driver, numTarjeta);
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.validar, dFTest.driver);

            datosStep2.setExcepExists(false); datosStep2.setResultSteps(State.Ok);
        } finally { datosStep2.setStepNumber(fmwkTest.grabStep(datosStep2,dFTest)); }

        //Validar
        maxSecondsWait = 2;
        descripValidac =
            "1) Es visible el campo de <b>cvv</b> (lo esperamos hasta " + maxSecondsWait + " segundos)</br>" +
            "2) Es visible el botón de <b>validar</b>";
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1
            if(!PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, StateElem.Present, maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2
            if(!PageChequeRegaloInputData.isElementInState(ConsultaSaldo.validar, StateElem.Present, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep2.setExcepExists(false); datosStep2.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep2, descripValidac, dFTest); }
    }

    /*TODO A la espera del cvv para completar esta parte*/
    public static void insertCVVConsultaSaldo(DataFmwkTest dFTest, String cvvNumber) throws Exception {
        //Step
        datosStep datosStep = new datosStep   (
            "Introducimos el CVV <b>" + cvvNumber + "</b> de la tarjeta",
            "Se vuelve a la página inicial del cheque regalo"/*Temporal*/);
        try {
            PageChequeRegaloInputData.inputDataInElement(ConsultaSaldo.cvvTarjeta, cvvNumber/*Temporal*/, dFTest.driver);
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.validar, 3, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validations
        int maxSecondsWait = 2;
        String descripValidac =
                "1) Se pueden validar los datos (lo esperamos hasta " + maxSecondsWait + " segundos)</br>" +
                "2) Se puede volver atrás</br>" +
                "3) La tarjeta introducida no tiene saldo disponible";
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1
            if(!PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, StateElem.Present, maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            //2
            if(!PageChequeRegaloInputData.isElementInState(ConsultaSaldo.volver, StateElem.Present, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);

            //3
            if (!PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.mensajeTarjetaSinSaldo, StateElem.Present, maxSecondsWait + 2, dFTest.driver)) {
                fmwkTest.addValidation(3, State.Defect, listVals);
            }
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

        //Step
        datosStep = new datosStep(
            "Usamos el boton de volver",
            "Estamos en la página inicial de <b>Cheque Regalo</b>");
        try {
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.volver, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep,dFTest)); }

        //Validaciones
        maxSecondsWait = 2;
        descripValidac =
                "1) Estamos en la página inicial (la esperamos hasta " + maxSecondsWait + " segundos)";
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1
            if (!PageChequeRegaloInputData.
            	isElementInStateUntil(ElementCheque.paginaForm, StateElem.Present, maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void seleccionarCantidades(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep   (
            "Seleccionamos la cantidad de <b>50€</b>",
            "Se comprueba que existen el resto de botones y está seleccionado el de <b>50€</b>");
        try {
            PageChequeRegaloInputData.clickImporteCheque(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep,dFTest)); }

        //Validaciones
        String importesStr = java.util.Arrays.asList(PageChequeRegaloInputData.Importe.values()).toString();
        int maxSecondsWait = 2;
        String descripValidac =
            "1) Aparece el titulo de la página correctamente (lo esperamos hasta XX segundos)<br>" +
            "2) Es posible seleccionar cheques de " + importesStr + "<br>" +
            "3) Es visible el link de <b>Consultar saldo</b>";
        try{
            List<SimpleValidation> listVals = new ArrayList<>();
            //1
            if (!PageChequeRegaloInputData.isElementInStateUntil(ElementCheque.paginaForm, StateElem.Present, maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2
            if (!PageChequeRegaloInputData.isPresentInputImportes(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3
            if (!PageChequeRegaloInputData.isElementInState(ConsultaSaldo.ir, StateElem.Present, dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void clickQuieroComprarChequeRegalo(DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep   (
            "Seleccionar link \"Comprar ahora\"",
            "Aparece la capa para introducir los datos de la operación");
        try {
            PageChequeRegaloInputData.clickAndWait(ElementCheque.compraAhora, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones.
        int maxSecondsWait = 3;
        String descripValidac =
            "1) Aparece la capa para introducir los datos del cheque regalo (la esperamos hasta " + maxSecondsWait + " segundos)<br>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageChequeRegaloInputData.
            	isElementInStateUntil(InputCheque.dataProof, StateElem.Present, maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void inputDataAndClickComprar(ChequeRegalo chequeRegalo, DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep   (
            "Introducir los datos del cheque y pulsar el botón <b>Comprar</b>:<br>" +
            "Nombre: <b>" + chequeRegalo.getNombre() + "</b><br>" +
            "Apellidos: <b>" + chequeRegalo.getApellidos() + "</b><br>" +
            "Email: <b>" + chequeRegalo.getEmail() + "</b><br>" +
            "Importe: <b>" + chequeRegalo.getImporte() + "</b><br>" +
            "Mensaje: <b>" + chequeRegalo.getMensaje() + "</b>", 
            "Aparece la página de identificación del usuario");
        try {
            PageChequeRegaloInputData.inputDataCheque(chequeRegalo, dFTest.driver);
            PageChequeRegaloInputData.clickButtonComprar(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }               
            
        //Validaciones.
        PageCheckoutWrapperStpV.validaIsVersionChequeRegalo(chequeRegalo, datosStep, dFTest);
    }    
}
