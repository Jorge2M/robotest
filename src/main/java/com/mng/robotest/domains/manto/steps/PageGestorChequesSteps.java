package com.mng.robotest.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageGestorCheques;
import com.mng.robotest.domains.manto.pageobjects.PageGestorCheques.ButtonsCheque;
import com.mng.robotest.domains.manto.pageobjects.PageGestorCheques.TablaCheque;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGestorChequesSteps extends StepMantoBase {
	
	private final PageGestorCheques pageGestorCheques = new PageGestorCheques();
	
	public void validateIsPage() {
		validatePage();
		validateButtons();
	}

	@Validation(description="1) Estamos en la página Gestord de Cheques")
	public boolean validatePage() {
		return (pageGestorCheques.isPage());
	}

	@Step(
		description="Introducimos el email <b>#{mail}</b> y damos click al botón \"Correo del cliente\"",
		expected="Muestra los cheques asociados al mail correctamente",
		saveErrorData=SaveWhen.Never)
	public void inputMailAndClickCorreoCliente(String mail) {
		pageGestorCheques.inputMailAndClickCorreoReceptorButton(mail);
		validateInitData(100, mail);
	}

	@Validation
	public ChecksTM validateInitData(int numPedidos, String mail) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparecen más de \"" + numPedidos + "\" pedidos",
			pageGestorCheques.comprobarNumeroPedidos(numPedidos));
		checks.add(
			"La columna correo de la primera línea es \""+ mail +"\"",
			pageGestorCheques.isMailCorrecto(mail));
		return checks;
	}

	@Step(
		description="Damos click al pedido de la #{numFila}a fila",
		expected="Muestra la página de detalles del pedido",
		saveErrorData=SaveWhen.Never)
	public void clickPedido(int numFila, String mail) {
		String pedido = pageGestorCheques.clickPedido(numFila, mail);
		validateDetailsCheques(pedido, mail);
		validateDataFromCheque();
	}

	@Validation
	public ChecksTM validateDetailsCheques(String pedido, String mail) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de" + PageGestorCheques.TITULO_DETALLES,
			pageGestorCheques.isPageDetalles());
		
		checks.add(
			"Como email del apartado \"Cheque número\" aparece" + mail,
			pageGestorCheques.comprobarMailDetallesCheque(mail));
		
		checks.add("Como id del pedido aparece\"" + pedido  + "\"",
			pageGestorCheques.comprobarPedidoDetallesCheque(pedido));
		
		return checks;
	}

	@Step(
		description="Damos click a <b>Volver a cheques</b>",
		expected="Muestra la página de información sobre los cheques",
		saveErrorData=SaveWhen.Never)
	public void volverCheques () {
		click(ButtonsCheque.VOLVER_CHEQUES.getBy()).exec();
		validateButtons();
	}

	@Validation
	public ChecksTM validateButtons() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Existe el botón de <b>Id del pedido</b>",
			state(Present, ButtonsCheque.ID_PEDIDO.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Numero de cheque</b>",
			state(Present, ButtonsCheque.NUM_CHEQUE.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Id de compra</b>",
			state(Clickable, ButtonsCheque.ID_COMPRA.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Correo del receptor</b>",
			state(Clickable, ButtonsCheque.CORREO_RECEPTOR.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Correo del comprador</b>",
			state(Clickable, ButtonsCheque.CORREO_COMPRADOR.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation
	public ChecksTM validateSecondDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"En la tabla activo existe un apartado para <b>ACTIVO</b>",
			state(Present, TablaCheque.ACTIVO.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla activo existe un apartado para <b>CHARGEBACK</b>",
			state(Present, TablaCheque.CHARGE_BACK.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation(description="Aparece el botón para <b>Volver a cheques</b>")
	public boolean validateReturnCheques() {
		return (state(Present, ButtonsCheque.VOLVER_CHEQUES.getBy()).wait(3).check());
	}

	@Validation
	public ChecksTM validateThirdDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"En la tabla divisa existe un apartado para <b>DIVISA</b>",
			state(Present, TablaCheque.DIVISA.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>VALOR TOTAL</b>",
			state(Present, TablaCheque.VALOR_TOTAL.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>SALDO</b>",
			state(Clickable, TablaCheque.SALDO.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>FECHA DE COMPRA</b>",
			state(Clickable, TablaCheque.FECHA_COMPRA.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>VALIDEZ</b>",
			state(Clickable, TablaCheque.VALIDEZ.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation
	public ChecksTM validatePedidosData() {
		var checks = ChecksTM.getNew();
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Id</b>",
			state(Present, TablaCheque.ID_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Fecha</b>",
			state(Present, TablaCheque.FECHA_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Total</b>",
			state(Present, TablaCheque.TOTAL_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Usuario</b>",
			state(Present, TablaCheque.USUARIO_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Accion</b>",
			state(Present, TablaCheque.ACTIVO_PEDIDOS.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation
	public ChecksTM validateButtonsDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Existe el boton para <b>Modificar</b>",
			state(Present, ButtonsCheque.MODIFICAR.getBy()).wait(3).check());
//		checks.add(
//			"Existe el boton para <b>Añadir</b>",
//			state(Present, ButtonsCheque.add.getBy()).wait(3).check());
		checks.add(
			"Existe el boton para <b>Reenviar</b>",
			state(Present, ButtonsCheque.REENVIAR.getBy()).wait(3).check());
		checks.add(
			"Existe el boton para <b>Editar</b>",
			state(Present, ButtonsCheque.EDITAR.getBy()).wait(3).check());
//		checks.add(
//			"Existe el boton para <b>Desactivar</b>",
//			state(Present, ButtonsCheque.desactivar.getBy()).wait(3).check());
		return checks;
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

	@Validation(description="Aparece el numero de cheque <b>#{cheque}</b> en la tabla de datos")
	public boolean validateDataCheque(String cheque) {
		return (!state(Present, ButtonsCheque.VOLVER_CHEQUES.getBy()).wait(3).check());
	}

	@Step(
		description="Accedemos al numero de cheque",
		expected="Aparece toda la información de dicho cheque pero no un email",
		saveErrorData=SaveWhen.Never)
	public void chequeDetails () {
		click(ButtonsCheque.CHEQUE_DATA.getBy()).exec();
		validateEmptyMail();
		validateDataFromCheque();
	}

	@Validation(description="El dato de <b>mail</b> corresponde a un registro <b>vacio</b>")
	public boolean validateEmptyMail() {
		return (!pageGestorCheques.isMailCorrecto(""));
	}

	@Validation
	public ChecksTM validateInitDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Existe la tabla que contiene <b>Activo</b>",
			state(Present, TablaCheque.ACTIVO.getBy()).wait(3).check());
		checks.add(
			"Existe la tabla que contiene <b>Divisa</b>",
			state(Present, TablaCheque.DIVISA.getBy()).wait(3).check());
		checks.add(
			"Existe la tabla que contiene <b>Pedidos Realizados</b>",
			state(Present, TablaCheque.PEDIDOS_REALIZADOS.getBy()).wait(3).check());
		checks.add(
			"Existe la tabla que contiene <b>Pedidos Eliminados</b>",
			state(Present, TablaCheque.PEDIDOS_ELIMINADOS.getBy()).wait(3).check());
		return checks;
	}
}
