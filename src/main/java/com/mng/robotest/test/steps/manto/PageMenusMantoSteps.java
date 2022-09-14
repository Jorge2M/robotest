package com.mng.robotest.test.steps.manto;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.manto.PageBolsas;
import com.mng.robotest.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test.pageobject.shop.PageMenusManto;


public class PageMenusMantoSteps extends PageBase {

	private final PageMenusManto pageMenusManto = new PageMenusManto();
	
	@Step (
		description="Desde la página de menús, seleccionamos el menú \"#{subMenu}\"", 
		expected="Aparece la página al menú seleccionado",
		saveErrorData=SaveWhen.Never)
	public void goToMainMenusAndClickMenu(String subMenu) throws Exception {
		if (!pageMenusManto.isPage()) {
			Thread.sleep(1000);
			SecCabecera.clickLinkVolverMenuAndWait(driver, 60);
		}
		String textAlert = pageMenusManto.clickMenuAndAcceptAlertIfExists(subMenu);
		checkIsPageOfSubmenu(subMenu, textAlert);
	}
	
	@Validation
	private ChecksTM checkIsPageOfSubmenu(String subMenu, String textAlertObtained) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
			"Aparece la página asociada al menú <b>" + subMenu + "</b> (la esperamos hasta " + seconds + " segundos)",
			pageMenusManto.validateIsPage(subMenu, seconds), State.Defect);
	 	
	 	checks.add(
			"No aparece ninguna ventana de alerta",
			"".compareTo(textAlertObtained)==0, State.Warn);
	 	
	 	return checks;
	}
	
	public void goToBolsas() throws Exception {
		if (!new PageBolsas().isPage()) {
			goToMainMenusAndClickMenu("Bolsas");
			checkIsVisiblePageBolsas(); 
		}
	}
	
	@Validation (
		description="Aparece la página de Bolsas",
		level=State.Defect)
	private boolean checkIsVisiblePageBolsas() {
		return new PageBolsas().isPage();
	}
	
	public void goToPedidos() throws Exception {
		if (!new PagePedidos().isPage()) {
			goToMainMenusAndClickMenu("Pedidos");
			checkIsVisiblePagePedidos();
		}		
	}	
	
	@Validation (
		description="Aparece la página de Pedidos",
		level=State.Defect)
	private boolean checkIsVisiblePagePedidos() {
		return new PagePedidos().isPage();
	}
	
	public void goToConsultarTiendas() throws Exception {
		goToMainMenusAndClickMenu("Consultar Tiendas");
		new PageConsultaTiendaSteps().validateIsPage();
	}

	public void goToIdEans()  throws Exception{
		goToMainMenusAndClickMenu("EANS");
		new PageConsultaIdEansSteps().validateIsPage();
	}

	public void goToGestionarClientes() throws Exception{
		goToMainMenusAndClickMenu("Gestionar Clientes");
		new PageGestionarClientesSteps().validateIsPage();
	}
	
	public void goToGestorCheques() throws Exception {
		goToMainMenusAndClickMenu("Gestor de Cheques");
		PageGestorChequesSteps pageGestorSteps = new PageGestorChequesSteps(driver);
		pageGestorSteps.validateIsPage();
	}
	
	public void goToGestorEstadisticasPedido() throws Exception {
		goToMainMenusAndClickMenu("Estadisticas Pedidos");
		PageGestorEstadisticasPedidoSteps.validateIsPage(driver);
	}
	
	public void goToGestorSaldosTPV() throws Exception {
		goToMainMenusAndClickMenu("Gestor de Saldos de TPV");
		PageGestorSaldosTPVSteps.validateIsPage(driver);
	}
	
	public void goToGestorConsultaCambioFamilia() throws Exception {
		goToMainMenusAndClickMenu("Gestor de familias");
		PageGestorConsultaCambioFamiliaSteps.validateIsPage(driver);
	}
	
	public void goToOrdenadorDePrendas() throws Exception {
		goToMainMenusAndClickMenu("Ordenador de Prendas");
		new PageOrdenacionDePrendasSteps(driver).validateIsPage();
	}
	
	public void comprobarMenusManto(String cabeceraName, String cabeceraNameNext) 
			throws Exception {
		List<String> listSubMenuNames = pageMenusManto.getListSubMenusName(cabeceraName, cabeceraNameNext);
		for (String subMenu : listSubMenuNames) {
			goToMainMenusAndClickMenu(subMenu);
		}
	}
}