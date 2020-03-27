package com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.*;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageChequeRegaloInputDataStpV {

	private final WebDriver driver;
	private final PageChequeRegaloInputData pageChequeRegaloInputData;
	
	public PageChequeRegaloInputDataStpV(WebDriver driver) {
		this.driver = driver;
		this.pageChequeRegaloInputData = new PageChequeRegaloInputData(driver);
	}
	
	public void paginaConsultarSaldo(String numTarjeta) throws Exception {
		clickConsultaSaldo();
		inputDataTarjetaRegalo(numTarjeta);
	}
	
	@Step (
		description="Accedemos a la página de consultar saldo de cheque regalo con sus respectivos campos",
        expected="Aparecen todos los campos y volvemos a la operativa normal")
    public void clickConsultaSaldo() throws Exception {
        PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.ir, driver);
        checkInputDataTarjeta();
	}

	@Validation
	private ChecksTM checkInputDataTarjeta() {
		ChecksTM validations = ChecksTM.getNew();
    	validations.add(
    		"Aparece el cuadro de introduccion de datos determinado (lo esperamos hasta " + 2 + " segundos)",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.numeroTarjeta, Present, 2, driver), 
    		State.Defect);
    	validations.add(
    		"Podemos validar la tarjeta",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.validar, Present, driver), 
    		State.Defect);
    	validations.add(
    		"Podemos volver atrás",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.volver, Present, driver), 
    		State.Defect);
		return validations;
	}
	
	@Step (
		description="Introducimos en el campo de <b>tarjeta regalo</b>#{numTarjeta} para consultar el saldo",
        expected="Se carga la página donde salen nuevos campos visibles como el de <b>cvv</b>")
	public void inputDataTarjetaRegalo(String numTarjeta) throws Exception {
        pageChequeRegaloInputData.introducirTarjetaConsultaSaldo(numTarjeta);
        PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.validar, driver);
        checkInputOtherData();
    }
	
	@Validation
	private ChecksTM checkInputOtherData() {
		ChecksTM validations = ChecksTM.getNew();
    	int maxSecondsWait = 2;
    	validations.add(
    		"Es visible el campo de <b>cvv</b> (lo esperamos hasta " + maxSecondsWait + " segundos)</br>",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, Present, maxSecondsWait, driver), 
    		State.Defect);
    	validations.add(
    		"Es visible el botón de <b>validar</b>",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.validar, Present, driver), 
    		State.Defect);
    	return validations;
	}

	public void insertCVVConsultaSaldo(String cvvNumber) throws Exception {
		consultarSaldoTarjeta(cvvNumber);
		backToInitPageChequeRegalo();
	}
	
	/*TODO A la espera del cvv para completar esta parte*/
	@Step (
		description="Introducimos el CVV <b>#{cvvNumber}</b> de la tarjeta",
		expected="Se vuelve a la página inicial del cheque regalo"/*Temporal*/)
	private void consultarSaldoTarjeta(String cvvNumber) throws Exception {
		pageChequeRegaloInputData.introducirCvc(cvvNumber);
		checkConsultaSaldoTarjeta();
	}
	
	@Validation
	private ChecksTM checkConsultaSaldoTarjeta() {
		ChecksTM validations = ChecksTM.getNew();
    	int maxSecondsWait = 2;
    	validations.add(
    		"Se pueden validar los datos (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.validar, Present, maxSecondsWait, driver), 
    		State.Defect);
    	validations.add(
    		"Se puede volver atrás",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.volver, Present, driver), State.Defect);
    	validations.add(
    		"La tarjeta introducida no tiene saldo disponible",
    		PageChequeRegaloInputData.isElementInStateUntil(ConsultaSaldo.mensajeTarjetaSinSaldo, Present, maxSecondsWait+2, driver), 
    		State.Warn);
    	return validations;
	}
	
	@Step (
		description="Usamos el boton de volver",
	    expected="Estamos en la página inicial de <b>Cheque Regalo</b>")
	private void backToInitPageChequeRegalo() throws Exception {
		PageChequeRegaloInputData.clickAndWait(ConsultaSaldo.volver, driver);
		int maxSecondsWait = 2;
		checkBackToInitPageChequeRegalo(maxSecondsWait);
	}

	@Validation (
		description="Estamos en la página inicial (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private boolean checkBackToInitPageChequeRegalo(int maxSecondsWait) {
	    return (PageChequeRegaloInputData.isElementInStateUntil(ElementCheque.paginaForm, Present, maxSecondsWait, driver));
	}

	@Step (
		description="Seleccionamos la cantidad de <b>#{importeToClick}</b>",
        expected="Se comprueba que existen el resto de botones y está seleccionado el de <b>#{importeToClick}</b>")
    public void seleccionarCantidades(Importe importeToClick) throws Exception {
        pageChequeRegaloInputData.clickImporteCheque(importeToClick);
        checkImporteSelected();
    }
	
	@Validation
	private ChecksTM checkImporteSelected() {
		ChecksTM validations = ChecksTM.getNew();
	    int maxSecondsWait = 2;
    	validations.add(
    		"Aparece el titulo de la página correctamente (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		PageChequeRegaloInputData.isElementInStateUntil(ElementCheque.titulo, Present, maxSecondsWait, driver), 
    		State.Warn);
    	
	    String importesStr = java.util.Arrays.asList(PageChequeRegaloInputData.Importe.values()).toString();
    	validations.add(
    		"Es posible seleccionar cheques de " + importesStr,
    		pageChequeRegaloInputData.isPresentInputImportes(), State.Defect);
    	validations.add(
    		"Es visible el link de <b>Consultar saldo</b>",
    		PageChequeRegaloInputData.isElementInState(ConsultaSaldo.ir, Present, driver), State.Defect);
    	return validations;
	}

	@Step (
		description="Seleccionar link \"Comprar ahora\"",
        expected="Aparece la capa para introducir los datos de la operación")
    public void clickQuieroComprarChequeRegalo() throws Exception {
        PageChequeRegaloInputData.clickAndWait(ElementCheque.compraAhora, driver);
        int maxSecondsWait = 3;
        checkIsVisibleCapaInputCheque(maxSecondsWait);
    }
	
	@Validation (
		description="Aparece la capa para introducir los datos del cheque regalo (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private boolean checkIsVisibleCapaInputCheque(int maxSecondsWait) {
		return (PageChequeRegaloInputData.isElementInStateUntil(InputCheque.dataProof, Present, maxSecondsWait, driver));
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
    public void inputDataAndClickComprar(ChequeRegalo chequeRegalo) throws Exception {
        pageChequeRegaloInputData.inputDataCheque(chequeRegalo);
        pageChequeRegaloInputData.clickButtonComprar(chequeRegalo);
        PageCheckoutWrapperStpV.validaIsVersionChequeRegalo(chequeRegalo, driver);
    }    
}
