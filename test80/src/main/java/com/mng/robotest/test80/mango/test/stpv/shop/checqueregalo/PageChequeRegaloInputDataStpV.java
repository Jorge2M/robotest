package com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.*;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;


public class PageChequeRegaloInputDataStpV{

    public static void paginaConsultarSaldo(DataFmwkTest dFTest, String numTarjeta) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
        	"Accedemos a la página de consultar saldo de cheque regalo con sus respectivos campos",
            "Aparecen todos los campos y volvemos a la operativa normal");
        try{
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.ir, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        int maxSecondsWait = 2;
        String descripValidac =
        	"1) Aparece el cuadro de introduccion de datos determinado (lo esperamos hasta " + maxSecondsWait + " segundos)<br>" +
            "2) Podemos validar la tarjeta<br>" +
            "3) Podemos volver atrás";
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if(!PageChequeRegaloInputData.
            	isElementInStateUntil(ConsultaSaldo.numeroTarjeta, StateElem.Present, maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if(!PageChequeRegaloInputData.
            	isElementInState(ConsultaSaldo.validar, StateElem.Present, dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if(!PageChequeRegaloInputData.
            	isElementInState(ConsultaSaldo.volver, StateElem.Present, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }

        //Step
        datosStep = new DatosStep    (
        	"Introducimos en el campo de <b>tarjeta regalo</b> " + numTarjeta + " para consultar el saldo",
            "Se carga la página donde salen nuevos campos visibles como el de <b>cvv</b>");
        try {
            PageChequeRegaloInputData.introducirTarjetaConsultaSaldo(dFTest.driver, numTarjeta);
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.validar, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validar
        maxSecondsWait = 2;
        descripValidac =
            "1) Es visible el campo de <b>cvv</b> (lo esperamos hasta " + maxSecondsWait + " segundos)</br>" +
            "2) Es visible el botón de <b>validar</b>";
        listVals = ListResultValidation.getNew(datosStep);
        try {
            if(!PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, StateElem.Present, maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if(!PageChequeRegaloInputData.isElementInState(ConsultaSaldo.validar, StateElem.Present, dFTest.driver)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    /*TODO A la espera del cvv para completar esta parte*/
    public static void insertCVVConsultaSaldo(DataFmwkTest dFTest, String cvvNumber) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep   (
            "Introducimos el CVV <b>" + cvvNumber + "</b> de la tarjeta",
            "Se vuelve a la página inicial del cheque regalo"/*Temporal*/);
        try {
            PageChequeRegaloInputData.inputDataInElement(ConsultaSaldo.cvvTarjeta, cvvNumber/*Temporal*/, dFTest.driver);
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.validar, 3, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validations
        int maxSecondsWait = 2;
        String descripValidac =
        	"1) Se pueden validar los datos (lo esperamos hasta " + maxSecondsWait + " segundos)</br>" +
            "2) Se puede volver atrás</br>" +
            "3) La tarjeta introducida no tiene saldo disponible";
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if(!PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, StateElem.Present, maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if(!PageChequeRegaloInputData.isElementInState(ConsultaSaldo.volver, StateElem.Present, dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (!PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.mensajeTarjetaSinSaldo, StateElem.Present, maxSecondsWait + 2, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
            datosStep.setListResultValidations(listVals);
        } finally { listVals.checkAndStoreValidations(descripValidac); }

        //Step
        datosStep = new DatosStep(
            "Usamos el boton de volver",
            "Estamos en la página inicial de <b>Cheque Regalo</b>");
        try {
            PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.volver, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        maxSecondsWait = 2;
        descripValidac =
        	"1) Estamos en la página inicial (la esperamos hasta " + maxSecondsWait + " segundos)";
        listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageChequeRegaloInputData.
            	isElementInStateUntil(ElementCheque.paginaForm, StateElem.Present, maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    public static void seleccionarCantidades(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep   (
            "Seleccionamos la cantidad de <b>50€</b>",
            "Se comprueba que existen el resto de botones y está seleccionado el de <b>50€</b>");
        try {
            PageChequeRegaloInputData.clickImporteCheque(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        String importesStr = java.util.Arrays.asList(PageChequeRegaloInputData.Importe.values()).toString();
        int maxSecondsWait = 2;
        String descripValidac =
            "1) Aparece el titulo de la página correctamente (lo esperamos hasta XX segundos)<br>" +
            "2) Es posible seleccionar cheques de " + importesStr + "<br>" +
            "3) Es visible el link de <b>Consultar saldo</b>";
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try{
            if (!PageChequeRegaloInputData.isElementInStateUntil(ElementCheque.paginaForm, StateElem.Present, maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageChequeRegaloInputData.isPresentInputImportes(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (!PageChequeRegaloInputData.isElementInState(ConsultaSaldo.ir, StateElem.Present, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    public static void clickQuieroComprarChequeRegalo(DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep   (
            "Seleccionar link \"Comprar ahora\"",
            "Aparece la capa para introducir los datos de la operación");
        try {
            PageChequeRegaloInputData.clickAndWait(ElementCheque.compraAhora, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones.
        int maxSecondsWait = 3;
        String descripValidac =
            "1) Aparece la capa para introducir los datos del cheque regalo (la esperamos hasta " + maxSecondsWait + " segundos)<br>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageChequeRegaloInputData.
            	isElementInStateUntil(InputCheque.dataProof, StateElem.Present, maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void inputDataAndClickComprar(ChequeRegalo chequeRegalo, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep   (
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
        finally { StepAspect.storeDataAfterStep(datosStep); }               
            
        //Validaciones.
        PageCheckoutWrapperStpV.validaIsVersionChequeRegalo(chequeRegalo, datosStep, dFTest);
    }    
}
