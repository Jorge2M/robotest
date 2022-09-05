package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.micuenta.pageobjects.PageAccesoMisCompras;
import com.mng.robotest.domains.micuenta.pageobjects.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.shop.pedidos.PageDetallePedidoSteps;

public class PageAccesoMisComprasSteps extends StepBase {

	private final PageAccesoMisCompras pageAccesoMisCompras = new PageAccesoMisCompras();
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Aparece la página de \"Acceso a Mis Compras\" (la esperamos hasta " + maxSeconds + " segundos)",
			pageAccesoMisCompras.isPage(maxSeconds), State.Warn);
		
		maxSeconds = 3;
		checks.add(
			"Aparece el bloque \"Ya estoy registrado\" (lo esperamos hasta " + maxSeconds + "segundos)",
			pageAccesoMisCompras.isPresentBlock(TypeBlock.SI_REGISTRADO, maxSeconds), State.Warn);
		
		checks.add(
			"Aparece el bloque de \"No estoy registrado\"",
			pageAccesoMisCompras.isPresentBlock(TypeBlock.NO_REGISTRADO), State.Warn);
		
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
		description="Se hace visible el bloque de \"#{typeBlock}\" (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkIsVisibleBlock(TypeBlock typeBlock, int maxSeconds) {
		return (pageAccesoMisCompras.isVisibleBlockUntil(typeBlock, maxSeconds));
	}
	
	@Step (
		description="En el bloque de \"Si Registrado\", introducir el usuario/password (#{usuario}/#{password}) y pulsar \"Entrar\"", 
		expected="Aparece la página de \"Mis compras\"")
	public void enterForSiRegistrado(String usuario, String password) {
		pageAccesoMisCompras.inputUserPasswordBlockSi(usuario, password); 
		pageAccesoMisCompras.clickEntrarBlockSi();
		new PageMisComprasSteps().validateIsPage();
	}

	static final String tagUsuario = "@TagUsuario";
	@Step (
		description=
			"En el bloque de \"No Registrado\", introducir el usuario/núm pedido " + 
			"(" + tagUsuario + " / <b style=\"color:blue;\">#{dataPedido.getCodpedido()}</b>)" + 
			" y pulsar \"Buscar pedido\"", 
		expected="Aparece la página de detalle del pedido")
	public void buscarPedidoForNoRegistrado(DataPedido dataPedido) {
		String usuario = dataPedido.getEmailCheckout();
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagUsuario, usuario);

		pageAccesoMisCompras.inputUserAndNumPedidoBlockNo(usuario, dataPedido.getCodpedido()); 
		pageAccesoMisCompras.clickBuscarPedidoBlockNo(); 

		new PageDetallePedidoSteps(channel, app).validateIsPageOk(dataPedido);
	}
}
