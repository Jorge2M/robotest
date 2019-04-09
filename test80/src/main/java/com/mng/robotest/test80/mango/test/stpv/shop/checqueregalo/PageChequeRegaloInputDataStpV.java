package com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.*;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

public class PageChequeRegaloInputDataStpV{

	public static void paginaConsultarSaldo(String numTarjeta, WebDriver driver) throws Exception {
		clickConsultaSaldo(driver);
		inputDataTarjetaRegalo(numTarjeta, driver);
	}
	
	@Step (
		description="Accedemos a la página de consultar saldo de cheque regalo con sus respectivos campos",
        expected="Aparecen todos los campos y volvemos a la operativa normal")
    public static void clickConsultaSaldo(WebDriver driver) throws Exception {
        PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.ir, driver);
        checkInputDataTarjeta(driver);
	}

	@Validation
	private static ChecksResult checkInputDataTarjeta(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
    	validations.add(
    		"Aparece el cuadro de introduccion de datos determinado (lo esperamos hasta " + maxSecondsWait + " segundos)<br>",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.numeroTarjeta, StateElem.Present, maxSecondsWait, driver), 
    		State.Defect);
    	validations.add(
    		"Podemos validar la tarjeta<br>",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.validar, StateElem.Present, driver), 
    		State.Defect);
    	validations.add(
    		"Podemos volver atrás",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.volver, StateElem.Present, driver), 
    		State.Defect);
		return validations;
	}
	
	@Step (
		description="Introducimos en el campo de <b>tarjeta regalo</b>#{numTarjeta} para consultar el saldo",
        expected="Se carga la página donde salen nuevos campos visibles como el de <b>cvv</b>")
	public static void inputDataTarjetaRegalo(String numTarjeta, WebDriver driver) throws Exception {
        PageChequeRegaloInputData.introducirTarjetaConsultaSaldo(driver, numTarjeta);
        PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.validar, driver);
        checkInputOtherData(driver);
    }
	
	@Validation
	private static ChecksResult checkInputOtherData(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
    	validations.add(
    		"Es visible el campo de <b>cvv</b> (lo esperamos hasta " + maxSecondsWait + " segundos)</br>",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, StateElem.Present, maxSecondsWait, driver), 
    		State.Defect);
    	validations.add(
    		"Es visible el botón de <b>validar</b>",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.validar, StateElem.Present, driver), 
    		State.Defect);
    	return validations;
	}

	public static void insertCVVConsultaSaldo(String cvvNumber, WebDriver driver) throws Exception {
		consultarSaldoTarjeta(cvvNumber, driver);
		backToInitPageChequeRegalo(driver);
	}
	
    /*TODO A la espera del cvv para completar esta parte*/
	@Step (
		description="Introducimos el CVV <b>#{cvvNumber}</b> de la tarjeta",
        expected="Se vuelve a la página inicial del cheque regalo"/*Temporal*/)
    private static void consultarSaldoTarjeta(String cvvNumber, WebDriver driver) throws Exception {
        PageChequeRegaloInputData.inputDataInElement(ConsultaSaldo.cvvTarjeta, cvvNumber/*Temporal*/, driver);
        PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.validar, 3, driver);
        checkConsultaSaldoTarjeta(driver);
    }
	
	@Validation
	private static ChecksResult checkConsultaSaldoTarjeta(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
    	validations.add(
    		"Se pueden validar los datos (lo esperamos hasta " + maxSecondsWait + " segundos)</br>",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, StateElem.Present, maxSecondsWait, driver), 
    		State.Defect);
    	validations.add(
    		"Se puede volver atrás</br>",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.volver, StateElem.Present, driver), State.Defect);
    	validations.add(
    		"La tarjeta introducida no tiene saldo disponible",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.mensajeTarjetaSinSaldo, StateElem.Present, maxSecondsWait+2, driver), 
    		State.Warn);
    	return validations;
	}
	
	@Step (
		description="Usamos el boton de volver",
	    expected="Estamos en la página inicial de <b>Cheque Regalo</b>")
	private static void backToInitPageChequeRegalo(WebDriver driver) throws Exception {
		PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.volver, driver);
		int maxSecondsWait = 2;
		checkBackToInitPageChequeRegalo(maxSecondsWait, driver);
	}

	@Validation (
		description="Estamos en la página inicial (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private static boolean checkBackToInitPageChequeRegalo(int maxSecondsWait, WebDriver driver) {
	    return (PageChequeRegaloInputData.isElementInStateUntil(ElementCheque.paginaForm, StateElem.Present, maxSecondsWait, driver));
	}

	@Step (
		description="Seleccionamos la cantidad de <b>#{importeToClick}</b>",
        expected="Se comprueba que existen el resto de botones y está seleccionado el de <b>#{importeToClick}</b>")
    public static void seleccionarCantidades(Importe importeToClick, WebDriver driver) throws Exception {
        PageChequeRegaloInputData.clickImporteCheque(importeToClick, driver);
        checkImporteSelected(driver);
    }
	
	@Validation
	private static ChecksResult checkImporteSelected(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 2;
    	validations.add(
    		"Aparece el titulo de la página correctamente (lo esperamos hasta " + maxSecondsWait + " segundos)<br>",
    		PageChequeRegaloInputData.isElementInStateUntil(ElementCheque.titulo, StateElem.Present, maxSecondsWait, driver), 
    		State.Warn);
    	
	    String importesStr = java.util.Arrays.asList(PageChequeRegaloInputData.Importe.values()).toString();
    	validations.add(
    		"Es posible seleccionar cheques de " + importesStr + "<br>",
    		PageChequeRegaloInputData.isPresentInputImportes(driver), State.Defect);
    	validations.add(
    		"Es visible el link de <b>Consultar saldo</b>",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.ir, StateElem.Present, driver), State.Defect);
    	return validations;
	}

	@Step (
		description="Seleccionar link \"Comprar ahora\"",
        expected="Aparece la capa para introducir los datos de la operación")
    public static void clickQuieroComprarChequeRegalo(WebDriver driver) throws Exception {
        PageChequeRegaloInputData.clickAndWait(ElementCheque.compraAhora, driver);
        int maxSecondsWait = 3;
        checkIsVisibleCapaInputCheque(maxSecondsWait, driver);
    }
	
	@Validation (
		description="Aparece la capa para introducir los datos del cheque regalo (la esperamos hasta #{maxSecondsWait} segundos)<br>",
		level=State.Defect)
	private static boolean checkIsVisibleCapaInputCheque(int maxSecondsWait, WebDriver driver) {
		return (PageChequeRegaloInputData.isElementInStateUntil(InputCheque.dataProof, StateElem.Present, maxSecondsWait, driver));
	}
    
	@Step (
		description=
			"Introducir los datos del cheque y pulsar el botón <b>Comprar</b>:<br>" +
			"Nombre: <b>#{chequeRegalo.getNombre()}</b><br>" +
			"Apellidos: <b>#{chequeRegalo.getApellidos()}</b><br>" +
			"Email: <b>#{chequeRegalo.getEmail()}</b><br>" +
			"Importe: <b>#{chequeRegalo.getImporte()}</b><br>" +
	        "Mensaje: <b>#{chequeRegalo.getMensaje()}</b>",
	    expected="Aparece la página de identificación del usuario")
    public static void inputDataAndClickComprar(ChequeRegalo chequeRegalo, WebDriver driver) throws Exception {
        PageChequeRegaloInputData.inputDataCheque(chequeRegalo, driver);
        PageChequeRegaloInputData.clickButtonComprar(chequeRegalo, driver);           
        PageCheckoutWrapperStpV.validaIsVersionChequeRegalo(chequeRegalo, driver);
    }    
}
