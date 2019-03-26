package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageBolsas;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMenusManto;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Menús en Manto
 * @author jorge.munoz
 *
 */

public class PageMenusMantoStpV {

	@Step (
		description="Desde la página de menús, seleccionamos el menú \"#{subMenu}\"", 
        expected="Aparece la página al menú seleccionado",
        saveErrorPage=SaveWhen.Never)
    public static void goToMainMenusAndClickMenu(String subMenu, WebDriver driver) throws Exception {
        if (!PageMenusManto.isPage(driver)) {
        	Thread.sleep(1000);
            SecCabecera.clickLinkVolverMenuAndWait(driver, 60);
        }
        String textAlert = PageMenusManto.clickMenuAndAcceptAlertIfExists(subMenu, driver);
        checkIsPageOfSubmenu(subMenu, textAlert, driver);
    }
	
	@Validation
	private static ChecksResult checkIsPageOfSubmenu(String subMenu, String textAlertObtained, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la página asociada al menú <b>" + subMenu + "</b><br>",
			PageMenusManto.validateIsPage(subMenu, driver), State.Defect);
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
        PageConsultaTiendaStpV.validateIsPage(driver);
    }

    /**
     * Se accede a la opción de menú de "ID/EANS" (sólo en caso de que no estemos ya en ella)
     */
	public static void goToIdEans(WebDriver driver)  throws Exception{
		goToMainMenusAndClickMenu("EANS", driver);
        PageConsultaIdEansStpV.validateIsPage(driver);
	}

	/**
     * Se accede a la opción de menú de "Gestionar Clientes" (sólo en caso de que no estemos ya en ella)
     */
	public static void goToGestionarClientes(WebDriver driver) throws Exception{
        goToMainMenusAndClickMenu("Gestionar Clientes", driver);
        PageGestionarClientesStpV.validateIsPage(driver);
	}
	
	/**
     * Se accede a la opción de menú de "Gestor de Cheques" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorCheques(WebDriver driver) throws Exception {
        goToMainMenusAndClickMenu("Gestor de Cheques", driver);
        PageGestorChequesStpV.validateIsPage(driver);
	}
	
	/**
     * Se accede a la opción de menú de "Estadísticas Pedidos" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorEstadisticasPedido(WebDriver driver) throws Exception {
        goToMainMenusAndClickMenu("Estadisticas Pedidos", driver);
        PageGestorEstadisticasPedidoStpV.validateIsPage(driver);
	}
	
	
	/**
     * Se accede a la opción de menú de "Gestor de Saldos de TPV" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorSaldosTPV(WebDriver driver) throws Exception {
        goToMainMenusAndClickMenu("Gestor de Saldos de TPV", driver);
        PageGestorSaldosTPVStpV.validateIsPage(driver);
	}
	
	/**
     * Se accede a la opción de menú de "Consulta y cambio de familia" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorConsultaCambioFamilia(WebDriver driver) throws Exception {
        goToMainMenusAndClickMenu("Gestor de familias", driver);
        PageGestorConsultaCambioFamiliaStpV.validateIsPage(driver);
		
	}
	
	/**
     * Se accede a la opción de menú de "Ordenacion de prendas" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	
	public static void goToOrdenadorDePrendas(WebDriver driver) throws Exception {
		goToMainMenusAndClickMenu("Ordenador de Prendas", driver);
		PageOrdenacionDePrendasStpV.validateIsPage(driver);
	}
	
	public static void comprobarMenusManto(String cabeceraName, String cabeceraNameNext, WebDriver driver) 
	throws Exception {
		ArrayList<String> listSubMenuNames = PageMenusManto.getListSubMenusName(cabeceraName, cabeceraNameNext, driver);
		for (String subMenu : listSubMenuNames) {
		    goToMainMenusAndClickMenu(subMenu, driver);
		}
	}
}