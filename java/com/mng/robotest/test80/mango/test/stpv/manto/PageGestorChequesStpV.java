package com.mng.robotest.test80.mango.test.stpv.manto;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.ButtonsCheque;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.TablaCheque;


public class PageGestorChequesStpV extends PageObjTM {
	
	private final PageGestorCheques pageGestorCheques;
	
	public PageGestorChequesStpV(WebDriver driver) {
		super(driver);
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
		click(ButtonsCheque.volverCheques.getBy()).exec();
		validateButtons();
	}

	@Validation
	public ChecksTM validateButtons() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Existe el botón de <b>Id del pedido</b>",
			state(Present, ButtonsCheque.idPedido.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el botón de <b>Numero de cheque</b>",
			state(Present, ButtonsCheque.numCheque.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el botón de <b>Id de compra</b>",
			state(Clickable, ButtonsCheque.idCompra.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el botón de <b>Correo del receptor</b>",
			state(Clickable, ButtonsCheque.correoReceptor.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el botón de <b>Correo del comprador</b>",
			state(Clickable, ButtonsCheque.correoComprador.getBy()).wait(3).check(), State.Defect);
		return validations;
	}

	@Validation
	public ChecksTM validateSecondDataCheque() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"En la tabla activo existe un apartado para <b>ACTIVO</b>",
			state(Present, TablaCheque.activo.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla activo existe un apartado para <b>CHARGEBACK</b>",
			state(Present, TablaCheque.chargeBack.getBy()).wait(3).check(), State.Defect);
		return validations;
	}

	@Validation(
		description="1) Aparece el botón para <b>Volver a cheques</b>",
		level=State.Defect)
	public boolean validateReturnCheques() {
		return (state(Present, ButtonsCheque.volverCheques.getBy()).wait(3).check());
	}

	@Validation
	public ChecksTM validateThirdDataCheque() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"En la tabla divisa existe un apartado para <b>DIVISA</b>",
			state(Present, TablaCheque.divisa.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>VALOR TOTAL</b>",
			state(Present, TablaCheque.valorTotal.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>SALDO</b>",
			state(Clickable, TablaCheque.saldo.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>FECHA DE COMPRA</b>",
			state(Clickable, TablaCheque.fechaCompra.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>VALIDEZ</b>",
			state(Clickable, TablaCheque.validez.getBy()).wait(3).check(), State.Defect);
		return validations;
	}

	@Validation
	public ChecksTM validatePedidosData() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Id</b>",
			state(Present, TablaCheque.idPedidos.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Fecha</b>",
			state(Present, TablaCheque.fechaPedidos.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Total</b>",
			state(Present, TablaCheque.totalPedidos.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Usuario</b>",
			state(Present, TablaCheque.usuarioPedidos.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Accion</b>",
			state(Present, TablaCheque.activoPedidos.getBy()).wait(3).check(), State.Defect);
		return validations;
	}

	@Validation
	public ChecksTM validateButtonsDataCheque() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Existe el boton para <b>Modificar</b>",
			state(Present, ButtonsCheque.modificar.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el boton para <b>Añadir</b>",
			state(Present, ButtonsCheque.add.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el boton para <b>Reenviar</b>",
			state(Present, ButtonsCheque.reenviar.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el boton para <b>Editar</b>",
			state(Present, ButtonsCheque.editar.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe el boton para <b>Desactivar</b>",
			state(Present, ButtonsCheque.desactivar.getBy()).wait(3).check(), State.Defect);
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
	public void inputCheque (String cheque) {
		pageGestorCheques.inputChequeAndConfirm(cheque);
		validateDataCheque(cheque);
	}

	@Validation(
		description="1) Aparece el numero de cheque <b>#{cheque}</b> en la tabla de datos",
		level=State.Defect)
	public boolean validateDataCheque(String cheque) {
		return (!state(Present, ButtonsCheque.volverCheques.getBy()).wait(3).check());
	}

	@Step(
		description="Accedemos al numero de cheque",
		expected="Aparece toda la información de dicho cheque pero no un email",
		saveErrorData=SaveWhen.Never)
	public void chequeDetails () throws Exception {
		click(ButtonsCheque.chequeData.getBy()).exec();
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
		validations.add(
			"Existe la tabla que contiene <b>Activo</b>",
			state(Present, TablaCheque.activo.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Divisa</b>",
			state(Present, TablaCheque.divisa.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Pedidos Realizados</b>",
			state(Present, TablaCheque.pedidosRealizados.getBy()).wait(3).check(), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Pedidos Eliminados</b>",
			state(Present, TablaCheque.pedidosEliminados.getBy()).wait(3).check(), State.Defect);
		return validations;
	}
}
