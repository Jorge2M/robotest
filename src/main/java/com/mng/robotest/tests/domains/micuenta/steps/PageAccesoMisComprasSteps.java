package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageAccesoMisCompras;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageAccesoMisComprasSteps extends StepBase {

	private final PageAccesoMisCompras pgAccesoMisCompras = new PageAccesoMisCompras();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Aparece la página de \"Acceso a Mis Compras\" " + getLitSecondsWait(seconds),
			pgAccesoMisCompras.isPage(seconds), WARN);
		
		seconds = 3;
		checks.add(
			"Aparece el bloque \"Ya estoy registrado\" " + getLitSecondsWait(seconds),
			pgAccesoMisCompras.isPresentBlock(TypeBlock.SI_REGISTRADO, seconds), WARN);
		
		checks.add(
			"Aparece el bloque de \"No estoy registrado\"",
			pgAccesoMisCompras.isPresentBlock(TypeBlock.NO_REGISTRADO), WARN);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el bloque \"#{typeBlock}\"", 
		expected="Se hace visible el bloque de #{typeBlock}")
	public void clickBlock(TypeBlock typeBlock) {
		pgAccesoMisCompras.clickBlock(typeBlock);
		checkIsVisibleBlock(typeBlock, 1);
	}
	
	@Validation (
		description="Se hace visible el bloque de \"#{typeBlock}\" " + SECONDS_WAIT,
		level=WARN)
	private boolean checkIsVisibleBlock(TypeBlock typeBlock, int seconds) {
		return (pgAccesoMisCompras.isVisibleBlockUntil(typeBlock, seconds));
	}
	
	@Step (
		description="En el bloque de \"Si Registrado\", introducir el usuario/password (#{usuario}/#{password}) y pulsar \"Entrar\"", 
		expected="Aparece la página de \"Mis compras\"")
	public void enterForSiRegistrado(String usuario, String password) {
		pgAccesoMisCompras.inputUserPasswordBlockSi(usuario, password); 
		pgAccesoMisCompras.clickEntrarBlockSi();
		new PageMisComprasSteps().validateIsPage();
	}

	static final String TAG_USUARIO = "@TagUsuario";
	@Step (
		description=
			"En el bloque de \"No Registrado\", introducir el usuario/núm pedido " + 
			"(" + TAG_USUARIO + " / <b style=\"color:blue;\">#{dataPedido.getCodpedido()}</b>)" + 
			" y pulsar \"Buscar pedido\"", 
		expected="Aparece la página de detalle del pedido")
	public void buscarPedidoForNoRegistrado(DataPedido dataPedido) {
		String usuario = dataPedido.getEmailCheckout();
		replaceStepDescription(TAG_USUARIO, usuario);

		pgAccesoMisCompras.inputUserAndNumPedidoBlockNo(usuario, dataPedido.getCodpedido()); 
		pgAccesoMisCompras.clickBuscarPedidoBlockNo(); 

		new PageDetallePedidoSteps().validateIsPageOk(dataPedido);
	}
}
