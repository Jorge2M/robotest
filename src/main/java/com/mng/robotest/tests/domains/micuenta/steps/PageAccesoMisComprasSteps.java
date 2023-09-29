package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageAccesoMisCompras;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageAccesoMisComprasSteps extends StepBase {

	private final PageAccesoMisCompras pageAccesoMisCompras = new PageAccesoMisCompras();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Aparece la página de \"Acceso a Mis Compras\" " + getLitSecondsWait(seconds),
			pageAccesoMisCompras.isPage(seconds), Warn);
		
		seconds = 3;
		checks.add(
			"Aparece el bloque \"Ya estoy registrado\" " + getLitSecondsWait(seconds),
			pageAccesoMisCompras.isPresentBlock(TypeBlock.SI_REGISTRADO, seconds), Warn);
		
		checks.add(
			"Aparece el bloque de \"No estoy registrado\"",
			pageAccesoMisCompras.isPresentBlock(TypeBlock.NO_REGISTRADO), Warn);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el bloque \"#{typeBlock}\"", 
		expected="Se hace visible el bloque de #{typeBlock}")
	public void clickBlock(TypeBlock typeBlock) {
		pageAccesoMisCompras.clickBlock(typeBlock);
		checkIsVisibleBlock(typeBlock, 1);
	}
	
	@Validation (
		description="Se hace visible el bloque de \"#{typeBlock}\" " + SECONDS_WAIT,
		level=Warn)
	private boolean checkIsVisibleBlock(TypeBlock typeBlock, int seconds) {
		return (pageAccesoMisCompras.isVisibleBlockUntil(typeBlock, seconds));
	}
	
	@Step (
		description="En el bloque de \"Si Registrado\", introducir el usuario/password (#{usuario}/#{password}) y pulsar \"Entrar\"", 
		expected="Aparece la página de \"Mis compras\"")
	public void enterForSiRegistrado(String usuario, String password) {
		pageAccesoMisCompras.inputUserPasswordBlockSi(usuario, password); 
		pageAccesoMisCompras.clickEntrarBlockSi();
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
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_USUARIO, usuario);

		pageAccesoMisCompras.inputUserAndNumPedidoBlockNo(usuario, dataPedido.getCodpedido()); 
		pageAccesoMisCompras.clickBuscarPedidoBlockNo(); 

		new PageDetallePedidoSteps().validateIsPageOk(dataPedido);
	}
}
