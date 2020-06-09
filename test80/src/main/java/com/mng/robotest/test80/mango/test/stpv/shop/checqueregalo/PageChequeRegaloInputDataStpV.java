package com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.*;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageChequeRegaloInputDataStpV extends PageObjTM {

	private final PageChequeRegaloInputData pageChequeRegaloInputData;
	
	public PageChequeRegaloInputDataStpV(WebDriver driver) {
		super(driver);
		this.pageChequeRegaloInputData = new PageChequeRegaloInputData(driver);
	}
	
	public void paginaConsultarSaldo(String numTarjeta) throws Exception {
		clickConsultaSaldo();
		inputDataTarjetaRegalo(numTarjeta);
	}
	
	@Step (
		description="Accedemos a la página de consultar saldo de cheque regalo con sus respectivos campos",
		expected="Aparecen todos los campos y volvemos a la operativa normal")
	public void clickConsultaSaldo() {
		click(ConsultaSaldo.ir.getBy()).exec();
		checkInputDataTarjeta();
	}

	@Validation
	private ChecksTM checkInputDataTarjeta() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece el cuadro de introduccion de datos determinado (lo esperamos hasta " + 2 + " segundos)",
			state(Present, ConsultaSaldo.numeroTarjeta.getBy()).wait(2).check(), 
			State.Defect);
		validations.add(
			"Podemos validar la tarjeta",
			state(Present, ConsultaSaldo.validar.getBy()).check(), 
			State.Defect);
		validations.add(
			"Podemos volver atrás",
			state(Present, ConsultaSaldo.volver.getBy()).check(), 
			State.Defect);
		return validations;
	}
	
	@Step (
		description="Introducimos en el campo de <b>tarjeta regalo</b>#{numTarjeta} para consultar el saldo",
		expected="Se carga la página donde salen nuevos campos visibles como el de <b>cvv</b>")
	public void inputDataTarjetaRegalo(String numTarjeta) throws Exception {
		pageChequeRegaloInputData.introducirTarjetaConsultaSaldo(numTarjeta);
		click(ConsultaSaldo.validar.getBy()).exec();
		checkInputOtherData();
	}
	
	@Validation
	private ChecksTM checkInputOtherData() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Es visible el campo de <b>cvv</b> (lo esperamos hasta 2 segundos)</br>",
			state(Present, ConsultaSaldo.validar.getBy()).wait(2).check(),
			State.Defect);
		validations.add(
			"Es visible el botón de <b>validar</b>",
			state(Present, ConsultaSaldo.validar.getBy()).check(),
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
		validations.add(
			"Se pueden validar los datos (lo esperamos hasta 2 segundos)",
			state(Present, ConsultaSaldo.validar.getBy()).wait(2).check(),
			State.Defect);
		validations.add(
			"Se puede volver atrás",
			state(Present, ConsultaSaldo.volver.getBy()).check(), State.Defect);
		validations.add(
			"La tarjeta introducida no tiene saldo disponible",
			state(Present, ConsultaSaldo.mensajeTarjetaSinSaldo.getBy()).wait(4).check(),
			State.Warn);
		return validations;
	}
	
	@Step (
		description="Usamos el boton de volver",
	    expected="Estamos en la página inicial de <b>Cheque Regalo</b>")
	private void backToInitPageChequeRegalo() {
		click(ConsultaSaldo.volver.getBy()).exec();
		checkBackToInitPageChequeRegalo(2);
	}

	@Validation (
		description="Estamos en la página inicial (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkBackToInitPageChequeRegalo(int maxSeconds) {
		return (state(Present, ElementCheque.paginaForm.getBy()).wait(maxSeconds).check());
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
		validations.add(
			"Aparece el titulo de la página correctamente (lo esperamos hasta 2 segundos)",
			state(Present, ElementCheque.titulo.getBy()).wait(2).check(),
			State.Warn);
		
		String importesStr = java.util.Arrays.asList(PageChequeRegaloInputData.Importe.values()).toString();
		validations.add(
			"Es posible seleccionar cheques de " + importesStr,
			pageChequeRegaloInputData.isPresentInputImportes(), State.Defect);
		validations.add(
			"Es visible el link de <b>Consultar saldo</b>",
			state(Present, ConsultaSaldo.ir.getBy()).check(), 
			State.Defect);
		return validations;
	}

	@Step (
		description="Seleccionar link \"Comprar ahora\"",
		expected="Aparece la capa para introducir los datos de la operación")
	public void clickQuieroComprarChequeRegalo() {
		click(ElementCheque.compraAhora.getBy()).exec();
		checkIsVisibleCapaInputCheque(3);
	}
	
	@Validation (
		description="Aparece la capa para introducir los datos del cheque regalo (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkIsVisibleCapaInputCheque(int maxSeconds) {
		return (state(Present, InputCheque.dataProof.getBy()).wait(maxSeconds).check());
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
	public void inputDataAndClickComprar(ChequeRegalo chequeRegalo) {
		pageChequeRegaloInputData.inputDataCheque(chequeRegalo);
		pageChequeRegaloInputData.clickButtonComprar(chequeRegalo);
		PageCheckoutWrapperStpV.validaIsVersionChequeRegalo(chequeRegalo, driver);
	}
}
