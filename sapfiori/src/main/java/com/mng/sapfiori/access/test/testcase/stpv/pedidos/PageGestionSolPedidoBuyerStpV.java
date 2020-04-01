package com.mng.sapfiori.access.test.testcase.stpv.pedidos;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.stpv.modals.ModalSelectConditionsStpV;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectConditions;
import com.mng.sapfiori.access.test.testcase.webobject.pedidos.PageGestionSolPedidoBuyer;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PageGestionSolPedidoBuyerStpV {

	private final PageGestionSolPedidoBuyer pageObject;
	
	private PageGestionSolPedidoBuyerStpV(WebDriver driver) {
		pageObject = PageGestionSolPedidoBuyer.getNew(driver);
	}
	private PageGestionSolPedidoBuyerStpV(PageGestionSolPedidoBuyer pageObject) {
		this.pageObject = pageObject;
	}
	
	public static PageGestionSolPedidoBuyerStpV getNew(WebDriver driver) {
		return new PageGestionSolPedidoBuyerStpV(driver);
	}
	
	public static PageGestionSolPedidoBuyerStpV getNew(PageGestionSolPedidoBuyer pageObject) {
		return new PageGestionSolPedidoBuyerStpV(pageObject);
	}
	
	@Validation (
		description="Aparece la página de <b>Gestión de solicitudes de pedido</b> (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsVisiblePage(int maxSeconds) {
		return (pageObject.isVisiblePage(maxSeconds));
	}
	
	@Step (
		description="Introducir el texto #{textToSearch} en el buscador y pulsar el icono de la lupa",
		expected="Se muestran solicitudes de pedido")
	public void searchSolicitudExistente(String textToSearch) {
		pageObject.filterBuscar.sendText(textToSearch);
		pageObject.filterBuscar.clickLupaForSearch();
		checkSolicitudesVisible(5);
	}
	
	@Validation ( 
		description="Se muestran solicitudes de pedido (las esperamos hasta #{maxSeconds} segundos",
		level=State.Defect)
	public boolean checkSolicitudesVisible(int maxSeconds) {
		return (pageObject.checkSolicitudesVisible(maxSeconds));
	}
	
	@Step (
		description="Seleccionar el valor <b>#{optionToSelect}</b> en el desplegable \"Estado de edición\"",
		expected="El valor se selecciona correctamente")
	public void selectEstadoEdicion(String optionToSelect) {
		pageObject.filterSelEstadoEdicion.selectByValue(optionToSelect);
	}
	
	@Step (
		description="Seleccionar el icono del input \"Purchase Requisition\"",
		expected="Se muestra el modal para la introducción de las condiciones")
	public ModalSelectConditionsStpV clickIconPurchaseRequisition() throws Exception {
		ModalSelectConditions modalSelectConditions = 
			pageObject.filterPurchaseRequisition.clickIconSetFilter();
		
		return ModalSelectConditionsStpV.getNew(modalSelectConditions);
	}
	
	@Step (
		description="Seleccionar el valor <b>#{optionToSelect}</b> en el desplegable \"Clase documento\"",
		expected="El valor se selecciona correctamente")
	public void selectClaseDocumento(String optionToSelect) {
		pageObject.filterSelClaseDocumento.selectByValue(optionToSelect);
	}
	
	@Step (
		description="Seleccionar los valores <b>#{optionsToSelect}</b> en el desplegable \"Clase documento\"",
		expected="El valor se selecciona correctamente")
	public void selectClaseDocumento(List<String> optionsToSelect) {
		pageObject.filterSelClaseDocumento.selectByValue(optionsToSelect);
	}
	
	@Step (
		description="Clickar Botón <b>Ir</b>",
		expected="Aparece una lista de Productos",
		saveImagePage=SaveWhen.Always)
	public void clickIrButton() {
		pageObject.clickIrButton();
	}
	
	@Step (
		description="Seleccionar el icono + para añadir un pedido",
		expected="Aparece la página de \"Solicitud de Pedido\"")
	public PageSolicitudPedidoStpV clickIconAñadirPedido() {
		PageSolicitudPedidoStpV pageSolPedidosStpV = 
			PageSolicitudPedidoStpV.getNew(
				pageObject.clickIconAñadirPedido());
		
		pageSolPedidosStpV.checkIsPage(5);
		return pageSolPedidosStpV;
	}
}
