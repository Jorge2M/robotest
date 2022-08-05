package com.mng.robotest.test.steps.manto;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test.pageobject.manto.PageBolsas;
import com.mng.robotest.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test.pageobject.shop.PageMenusManto;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Menús en Manto
 * @author jorge.munoz
 *
 */

public class PageMenusMantoSteps {

	@Step (
		description="Desde la página de menús, seleccionamos el menú \"#{subMenu}\"", 
		expected="Aparece la página al menú seleccionado",
		saveErrorData=SaveWhen.Never)
	public static void goToMainMenusAndClickMenu(String subMenu, WebDriver driver) throws Exception {
		if (!PageMenusManto.isPage(driver)) {
			Thread.sleep(1000);
			SecCabecera.clickLinkVolverMenuAndWait(driver, 60);
		}
		String textAlert = PageMenusManto.clickMenuAndAcceptAlertIfExists(subMenu, driver);
		checkIsPageOfSubmenu(subMenu, textAlert, driver);
	}
	
	@Validation
	private static ChecksTM checkIsPageOfSubmenu(String subMenu, String textAlertObtained, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 2;
	 	validations.add(
			"Aparece la página asociada al menú <b>" + subMenu + "</b> (la esperamos hasta " + maxSeconds + " segundos)",
			PageMenusManto.validateIsPage(subMenu, maxSeconds, driver), State.Defect);
	 	validations.add(
			"No aparece ninguna ventana de alerta",
			"".compareTo(textAlertObtained)==0, State.Warn);
	 	return validations;
	}
	
	/**
	 * Se accede a la opción de menú de "Bolsas" (sólo en caso de que no estemos ya en ella)
	 */
	public static void goToBolsas(WebDriver driver) throws Exception {
		if (!PageBolsas.isPage(driver)) {
			goToMainMenusAndClickMenu("Bolsas", driver);
			checkIsVisiblePageBolsas(driver); 
		}
	}
	
	@Validation (
		description="Aparece la página de Bolsas",
		level=State.Defect)
	private static boolean checkIsVisiblePageBolsas(WebDriver driver) {
		return (PageBolsas.isPage(driver));
	}
	
	public static void goToPedidos(WebDriver driver) throws Exception {
		//Si ya estamos en la página en cuestión no hacemos nada
		if (!PagePedidos.isPage(driver)) {
			goToMainMenusAndClickMenu("Pedidos", driver);
			checkIsVisiblePagePedidos(driver);
		}		
	}	
	
	@Validation (
		description="Aparece la página de Pedidos",
		level=State.Defect)
	private static boolean checkIsVisiblePagePedidos(WebDriver driver) {
		return (PagePedidos.isPage(driver));
	}
	
	public static void goToConsultarTiendas(WebDriver driver) throws Exception {
		goToMainMenusAndClickMenu("Consultar Tiendas", driver);
		PageConsultaTiendaSteps.validateIsPage(driver);
	}

	/**
	 * Se accede a la opción de menú de "ID/EANS" (sólo en caso de que no estemos ya en ella)
	 */
	public static void goToIdEans(WebDriver driver)  throws Exception{
		goToMainMenusAndClickMenu("EANS", driver);
		PageConsultaIdEansSteps.validateIsPage(driver);
	}

	/**
	 * Se accede a la opción de menú de "Gestionar Clientes" (sólo en caso de que no estemos ya en ella)
	 */
	public static void goToGestionarClientes(WebDriver driver) throws Exception{
		goToMainMenusAndClickMenu("Gestionar Clientes", driver);
		PageGestionarClientesSteps.validateIsPage(driver);
	}
	
	/**
	 * Se accede a la opción de menú de "Gestor de Cheques" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
	 */
	public static void goToGestorCheques(WebDriver driver) throws Exception {
		goToMainMenusAndClickMenu("Gestor de Cheques", driver);
		PageGestorChequesSteps pageGestorSteps = new PageGestorChequesSteps(driver);
		pageGestorSteps.validateIsPage();
	}
	
	/**
	 * Se accede a la opción de menú de "Estadísticas Pedidos" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
	 */
	public static void goToGestorEstadisticasPedido(WebDriver driver) throws Exception {
		goToMainMenusAndClickMenu("Estadisticas Pedidos", driver);
		PageGestorEstadisticasPedidoSteps.validateIsPage(driver);
	}
	
	
	/**
	 * Se accede a la opción de menú de "Gestor de Saldos de TPV" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
	 */
	public static void goToGestorSaldosTPV(WebDriver driver) throws Exception {
		goToMainMenusAndClickMenu("Gestor de Saldos de TPV", driver);
		PageGestorSaldosTPVSteps.validateIsPage(driver);
	}
	
	/**
	 * Se accede a la opción de menú de "Consulta y cambio de familia" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
	 */
	public static void goToGestorConsultaCambioFamilia(WebDriver driver) throws Exception {
		goToMainMenusAndClickMenu("Gestor de familias", driver);
		PageGestorConsultaCambioFamiliaSteps.validateIsPage(driver);
		
	}
	
	/**
	 * Se accede a la opción de menú de "Ordenacion de prendas" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
	 */
	
	public static void goToOrdenadorDePrendas(WebDriver driver) throws Exception {
		goToMainMenusAndClickMenu("Ordenador de Prendas", driver);
		new PageOrdenacionDePrendasSteps(driver).validateIsPage();
	}
	
	public static void comprobarMenusManto(String cabeceraName, String cabeceraNameNext, WebDriver driver) 
	throws Exception {
		ArrayList<String> listSubMenuNames = PageMenusManto.getListSubMenusName(cabeceraName, cabeceraNameNext, driver);
		for (String subMenu : listSubMenuNames) {
			goToMainMenusAndClickMenu(subMenu, driver);
		}
	}
}