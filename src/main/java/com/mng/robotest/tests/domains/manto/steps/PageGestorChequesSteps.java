package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.manto.pageobjects.PageGestorCheques;
import com.mng.robotest.tests.domains.manto.pageobjects.PageGestorCheques.ButtonsCheque;
import com.mng.robotest.tests.domains.manto.pageobjects.PageGestorCheques.TablaCheque;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageGestorChequesSteps extends StepMantoBase {
	
	private final PageGestorCheques pgGestorCheques = new PageGestorCheques();
	
	public void validateIsPage() {
		validatePage();
		validateButtons();
	}

	@Validation(description="1) Estamos en la página Gestord de Cheques")
	public boolean validatePage() {
		return pgGestorCheques.isPage();
	}

	@Step(
		description="Introducimos el email <b>#{mail}</b> y damos click al botón \"Correo del cliente\"",
		expected="Muestra los cheques asociados al mail correctamente",
		saveErrorData=NEVER)
	public void inputMailAndClickCorreoCliente(String mail) {
		pgGestorCheques.inputMailAndClickCorreoReceptorButton(mail);
		validateInitData(100, mail);
	}

	@Validation
	public ChecksTM validateInitData(int numPedidos, String mail) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparecen más de \"" + numPedidos + "\" pedidos",
			pgGestorCheques.comprobarNumeroPedidos(numPedidos));
		checks.add(
			"La columna correo de la primera línea es \""+ mail +"\"",
			pgGestorCheques.isMailCorrecto(mail));
		return checks;
	}

	@Step(
		description="Damos click al pedido de la #{numFila}a fila",
		expected="Muestra la página de detalles del pedido",
		saveErrorData=NEVER)
	public void clickPedido(int numFila, String mail) {
		String pedido = pgGestorCheques.clickPedido(numFila, mail);
		validateDetailsCheques(pedido, mail);
		validateDataFromCheque();
	}

	@Validation
	public ChecksTM validateDetailsCheques(String pedido, String mail) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de" + PageGestorCheques.TITULO_DETALLES,
			pgGestorCheques.isPageDetalles());
		
		checks.add(
			"Como email del apartado \"Cheque número\" aparece" + mail,
			pgGestorCheques.comprobarMailDetallesCheque(mail));
		
		checks.add("Como id del pedido aparece\"" + pedido  + "\"",
			pgGestorCheques.comprobarPedidoDetallesCheque(pedido));
		
		return checks;
	}

	@Step(
		description="Damos click a <b>Volver a cheques</b>",
		expected="Muestra la página de información sobre los cheques",
		saveErrorData=NEVER)
	public void volverCheques () {
		click(ButtonsCheque.VOLVER_CHEQUES.getBy()).exec();
		validateButtons();
	}

	@Validation
	public ChecksTM validateButtons() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Existe el botón de <b>Id del pedido</b>",
			state(PRESENT, ButtonsCheque.ID_PEDIDO.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Numero de cheque</b>",
			state(PRESENT, ButtonsCheque.NUM_CHEQUE.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Id de compra</b>",
			state(CLICKABLE, ButtonsCheque.ID_COMPRA.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Correo del receptor</b>",
			state(CLICKABLE, ButtonsCheque.CORREO_RECEPTOR.getBy()).wait(3).check());
		
		checks.add(
			"Existe el botón de <b>Correo del comprador</b>",
			state(CLICKABLE, ButtonsCheque.CORREO_COMPRADOR.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation
	public ChecksTM validateSecondDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"En la tabla activo existe un apartado para <b>ACTIVO</b>",
			state(PRESENT, TablaCheque.ACTIVO.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla activo existe un apartado para <b>CHARGEBACK</b>",
			state(PRESENT, TablaCheque.CHARGE_BACK.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation(description="Aparece el botón para <b>Volver a cheques</b>")
	public boolean validateReturnCheques() {
		return (state(PRESENT, ButtonsCheque.VOLVER_CHEQUES.getBy()).wait(3).check());
	}

	@Validation
	public ChecksTM validateThirdDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"En la tabla divisa existe un apartado para <b>DIVISA</b>",
			state(PRESENT, TablaCheque.DIVISA.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>VALOR TOTAL</b>",
			state(PRESENT, TablaCheque.VALOR_TOTAL.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>SALDO</b>",
			state(CLICKABLE, TablaCheque.SALDO.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>FECHA DE COMPRA</b>",
			state(CLICKABLE, TablaCheque.FECHA_COMPRA.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla divisa existe un apartado para <b>VALIDEZ</b>",
			state(CLICKABLE, TablaCheque.VALIDEZ.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation
	public ChecksTM validatePedidosData() {
		var checks = ChecksTM.getNew();
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Id</b>",
			state(PRESENT, TablaCheque.ID_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Fecha</b>",
			state(PRESENT, TablaCheque.FECHA_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Total</b>",
			state(PRESENT, TablaCheque.TOTAL_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Usuario</b>",
			state(PRESENT, TablaCheque.USUARIO_PEDIDOS.getBy()).wait(3).check());
		
		checks.add(
			"En la tabla pedidos realizados existe un apartado para <b>Accion</b>",
			state(PRESENT, TablaCheque.ACTIVO_PEDIDOS.getBy()).wait(3).check());
		
		return checks;
	}

	@Validation
	public ChecksTM validateButtonsDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Existe el boton para <b>Modificar</b>",
			state(PRESENT, ButtonsCheque.MODIFICAR.getBy()).wait(3).check());
//		checks.add(
//			"Existe el boton para <b>Añadir</b>",
//			state(PRESENT, ButtonsCheque.add.getBy()).wait(3).check());
		checks.add(
			"Existe el boton para <b>Reenviar</b>",
			state(PRESENT, ButtonsCheque.REENVIAR.getBy()).wait(3).check());
		checks.add(
			"Existe el boton para <b>Editar</b>",
			state(PRESENT, ButtonsCheque.EDITAR.getBy()).wait(3).check());
//		checks.add(
//			"Existe el boton para <b>Desactivar</b>",
//			state(PRESENT, ButtonsCheque.desactivar.getBy()).wait(3).check());
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
		saveErrorData=NEVER)
	public void inputCheque (String cheque) {
		pgGestorCheques.inputChequeAndConfirm(cheque);
		validateDataCheque(cheque);
	}

	@Validation(description="Aparece el numero de cheque <b>#{cheque}</b> en la tabla de datos")
	public boolean validateDataCheque(String cheque) {
		return (!state(PRESENT, ButtonsCheque.VOLVER_CHEQUES.getBy()).wait(3).check());
	}

	@Step(
		description="Accedemos al numero de cheque",
		expected="Aparece toda la información de dicho cheque pero no un email",
		saveErrorData=NEVER)
	public void chequeDetails () {
		click(ButtonsCheque.CHEQUE_DATA.getBy()).exec();
		validateEmptyMail();
		validateDataFromCheque();
	}

	@Validation(description="El dato de <b>mail</b> corresponde a un registro <b>vacio</b>")
	public boolean validateEmptyMail() {
		return (!pgGestorCheques.isMailCorrecto(""));
	}

	@Validation
	public ChecksTM validateInitDataCheque() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Existe la tabla que contiene <b>Activo</b>",
			state(PRESENT, TablaCheque.ACTIVO.getBy()).wait(3).check());
		checks.add(
			"Existe la tabla que contiene <b>Divisa</b>",
			state(PRESENT, TablaCheque.DIVISA.getBy()).wait(3).check());
		checks.add(
			"Existe la tabla que contiene <b>Pedidos Realizados</b>",
			state(PRESENT, TablaCheque.PEDIDOS_REALIZADOS.getBy()).wait(3).check());
		checks.add(
			"Existe la tabla que contiene <b>Pedidos Eliminados</b>",
			state(PRESENT, TablaCheque.PEDIDOS_ELIMINADOS.getBy()).wait(3).check());
		return checks;
	}
}
