package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.ButtonsCheque;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.TablaCheque;


public class PageGestorChequesStpV {
	
	private final WebDriver driver;
	private final PageGestorCheques pageGestorCheques;
	
	public PageGestorChequesStpV(WebDriver driver) {
		this.driver = driver;
		this.pageGestorCheques = new PageGestorCheques(driver);
	}

	public void validateIsPage() {
		validatePage();
		validateButtons();
	}

	@Validation(
		description="1) Estamos en la página Gestord de Cheques",
		level=State.Defect)
	public boolean validatePage() {
		return (pageGestorCheques.isPage());
	}

	@Step(
		description="Introducimos el email <b>#{mail}</b> y damos click al botón \"Correo del cliente\"",
		expected="Muestra los cheques asociados al mail correctamente",
		saveErrorData=SaveWhen.Never)
	public void inputMailAndClickCorreoCliente(String mail) throws Exception {
		pageGestorCheques.inputMailAndClickCorreoReceptorButton(mail);
		validateInitData(100, mail);
	}

	@Validation
	public ChecksTM validateInitData(int numPedidos, String mail) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparecen más de \"" + numPedidos + "\" pedidos",
			pageGestorCheques.comprobarNumeroPedidos(numPedidos), State.Defect);
		validations.add(
			"La columna correo de la primera línea es \""+ mail +"\"",
			pageGestorCheques.isMailCorrecto(mail), State.Defect);
		return validations;
	}

	@Step(
		description="Damos click al pedido de la #{numFila}a fila",
		expected="Muestra la página de detalles del pedido",
		saveErrorData=SaveWhen.Never)
	public void clickPedido(int numFila, String mail) throws Exception {
		String pedido;
		pedido = pageGestorCheques.clickPedido(numFila, mail);
		validateDetailsCheques(pedido, mail);
		validateDataFromCheque();
	}

	@Validation
	public ChecksTM validateDetailsCheques(String pedido, String mail) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la página de" + PageGestorCheques.tituloDetalles,
			pageGestorCheques.isPageDetalles(), State.Defect);
		validations.add(
			"Como email del apartado \"Cheque número\" aparece" + mail,
			pageGestorCheques.comprobarMailDetallesCheque(mail), State.Defect);
		validations.add("Como id del pedido aparece\"" + pedido  + "\"",
			pageGestorCheques.comprobarPedidoDetallesCheque(pedido), State.Defect);
		return validations;
	}

	@Step(
		description="Damos click a <b>Volver a cheques</b>",
		expected="Muestra la página de información sobre los cheques",
		saveErrorData=SaveWhen.Never)
	public void volverCheques () throws Exception {
		PageGestorCheques.clickAndWait(ButtonsCheque.volverCheques, driver);
		validateButtons();
	}

	@Validation
	public ChecksTM validateButtons() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Existe el botón de <b>Id del pedido</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.idPedido, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Numero de cheque</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.numCheque, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Id de compra</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.idCompra, Clickable, 3, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Correo del receptor</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoReceptor, Clickable, 3, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Correo del comprador</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoComprador, Clickable, 3, driver), State.Defect);
		return validations;
	}

	@Validation
	public ChecksTM validateSecondDataCheque() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"En la tabla activo existe un apartado para <b>ACTIVO</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.activo, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla activo existe un apartado para <b>CHARGEBACK</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.chargeBack, Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation(
		description="1) Aparece el botón para <b>Volver a cheques</b>",
		level=State.Defect)
	public boolean validateReturnCheques() {
		return (PageGestorCheques.isElementInStateUntil(ButtonsCheque.volverCheques, Present, 3, driver));
	}

	@Validation
	public ChecksTM validateThirdDataCheque() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"En la tabla divisa existe un apartado para <b>DIVISA</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>VALOR TOTAL</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.valorTotal, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>SALDO</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.saldo, Clickable, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>FECHA DE COMPRA</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.fechaCompra, Clickable, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>VALIDEZ</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.validez, Clickable, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation
	public ChecksTM validatePedidosData() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Id</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.idPedidos, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Fecha</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.fechaPedidos, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Total</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.totalPedidos, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Usuario</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.usuarioPedidos, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Accion</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.activoPedidos, Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation
	public ChecksTM validateButtonsDataCheque() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Existe el boton para <b>Modificar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.modificar, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Añadir</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.add, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Reenviar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.reenviar, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Editar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.editar, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Desactivar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.desactivar, Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	public void validateDataFromCheque () {
		validateInitDataCheque();
		validateSecondDataCheque();
		validateThirdDataCheque();
		validatePedidosData();
		validateButtonsDataCheque();
		validateReturnCheques();
	}

	@Step(
		description="Introducimos el numero de cheque con valor: <b>#{cheque}</b>",
		expected="Muestra los cheques asociados al mail correctamente",
		saveErrorData=SaveWhen.Never)
	public void inputCheque (String cheque) throws Exception {
		pageGestorCheques.inputChequeAndConfirm(cheque);
		validateDataCheque(cheque);
	}

	@Validation(
		description="1) Aparece el numero de cheque <b>#{cheque}</b> en la tabla de datos",
		level=State.Defect)
	public boolean validateDataCheque(String cheque) {
		return (!PageGestorCheques.isElementInStateUntil(ButtonsCheque.volverCheques, Present, 3, driver));
	}

	@Step(
		description="Accedemos al numero de cheque",
		expected="Aparece toda la información de dicho cheque pero no un email",
		saveErrorData=SaveWhen.Never)
	public void chequeDetails () throws Exception {
		PageGestorCheques.clickAndWait(ButtonsCheque.chequeData, driver);
		validateEmptyMail();
		validateDataFromCheque();
	}

	@Validation(
		description="1) El dato de <b>mail</b> corresponde a un registro <b>vacio</b>",
		level=State.Defect)
	public boolean validateEmptyMail() {
		return (!pageGestorCheques.isMailCorrecto(""));
	}

	@Validation
	public ChecksTM validateInitDataCheque() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Existe la tabla que contiene <b>Activo</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.activo, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Divisa</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Pedidos Realizados</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosRealizados, Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Pedidos Eliminados</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosEliminados, Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}
}
