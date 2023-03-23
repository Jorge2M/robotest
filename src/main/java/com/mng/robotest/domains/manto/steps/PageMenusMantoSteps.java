package com.mng.robotest.domains.manto.steps;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageBolsas;
import com.mng.robotest.domains.manto.pageobjects.PageMenusManto;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos;
import com.mng.robotest.domains.manto.pageobjects.SecCabecera;
import com.mng.robotest.domains.manto.steps.pedidos.PageGestorEstadisticasPedidoSteps;
import com.mng.robotest.domains.manto.tests.MenusFact.Section;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageMenusMantoSteps extends StepMantoBase {

	private final PageMenusManto pageMenusManto = new PageMenusManto();
	
	@Step (
		description="Desde la página de menús, seleccionamos el menú \"#{subMenu}\"", 
		expected="Aparece la página al menú seleccionado",
		saveErrorData=SaveWhen.Never)
	public void goToMainMenusAndClickMenu(String subMenu) {
		if (!pageMenusManto.isPage()) {
			waitMillis(1000);
			new SecCabecera().clickLinkVolverMenuAndWait(60);
		}
		String textAlert = pageMenusManto.clickMenuAndAcceptAlertIfExists(subMenu);
		checkIsPageOfSubmenu(subMenu, textAlert);
	}
	
	@Validation
	private ChecksTM checkIsPageOfSubmenu(String subMenu, String textAlertObtained) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
			"Aparece la página asociada al menú <b>" + subMenu + "</b> (la esperamos hasta " + seconds + " segundos)",
			pageMenusManto.validateIsPage(subMenu, seconds), Defect);
	 	
	 	checks.add(
			"No aparece ninguna ventana de alerta",
			"".compareTo(textAlertObtained)==0, Warn);
	 	
	 	return checks;
	}
	
	public void goToBolsas() {
		if (!new PageBolsas().isPage()) {
			goToMainMenusAndClickMenu("Bolsas");
			checkIsVisiblePageBolsas(); 
		}
	}
	
	@Validation (
		description="Aparece la página de Bolsas",
		level=Defect)
	private boolean checkIsVisiblePageBolsas() {
		return new PageBolsas().isPage();
	}
	
	public void goToPedidosStep() {
		if (!new PagePedidos().isPage()) {
			goToMainMenusAndClickMenu("Pedidos");
			checkIsVisiblePagePedidos();
		}		
	}	
	
	@Validation (
		description="Aparece la página de Pedidos",
		level=Defect)
	private boolean checkIsVisiblePagePedidos() {
		return new PagePedidos().isPage();
	}
	
	public void goToIdEans()  {
		goToMainMenusAndClickMenu("EANS");
		new PageConsultaIdEansSteps().validateIsPage();
	}

	public void goToGestionarClientes() {
		goToMainMenusAndClickMenu("Gestionar Clientes");
		new PageGestionarClientesSteps().validateIsPage();
	}
	
	public void goToGestorCheques() {
		goToMainMenusAndClickMenu("Gestor de Cheques");
		new PageGestorChequesSteps().validateIsPage();
	}
	
	public void goToGestorEstadisticasPedido() {
		goToMainMenusAndClickMenu("Estadisticas Pedidos");
		new PageGestorEstadisticasPedidoSteps().validateIsPage();
	}
	
	public void goToOrdenadorDePrendas() {
		goToMainMenusAndClickMenu("Ordenador de Prendas");
		new PageOrdenacionDePrendasSteps().validateIsPage();
	}
	
	public void comprobarMenusManto(Section section) {
		List<String> listSubMenuNames = pageMenusManto.getListSubMenusName(section);
		for (String subMenu : listSubMenuNames) {
			goToMainMenusAndClickMenu(subMenu);
		}
	}
}