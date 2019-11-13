package com.mng.sapfiori.test.testcase.webobject.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.buscar.InputBuscador;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectEstandard;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectMultiValue;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputWithIconForDefineConditions;
import com.mng.sapfiori.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.sapfiori.test.testcase.generic.webobject.pages.PageFilter;
import com.mng.sapfiori.test.testcase.webobject.iconsmenu.OptionMenu;

public class PageGestionSolPedidoBuyer extends PageFilter {

	public static OptionMenu option = OptionMenu.ManagePRsBuyer;
	
	//Filters
	public final InputBuscador filterBuscar;
	public final SelectEstandard filterSelEstadoEdicion;
	public final InputWithIconForDefineConditions filterPurchaseRequisition;
	public final SelectMultiValue filterSelClaseDocumento;
	
	private final static String XPathLineaPedido = "//tr[@id[contains(.,'ColumnListItem')]]";
	private final static String XPathIconSolPedido = "//button[@id[contains(.,'-addEntry')]]";

	
	private PageGestionSolPedidoBuyer(WebDriver driver) {
		super(option, driver);
		StandarElementsMaker elementsMaker = StandarElementsMaker.getNew(driver);
		filterBuscar = elementsMaker.getInputBuscador();
		filterSelEstadoEdicion = elementsMaker.getSelectEstandard("Estado de edición");
		filterPurchaseRequisition = elementsMaker.getInputWithIconForDefineConditions("Purchase Requisition");
		filterSelClaseDocumento = elementsMaker.getSelectMultiValue("Clase documento");
	}
	
	public static PageGestionSolPedidoBuyer getNew(WebDriver driver) {
		return new PageGestionSolPedidoBuyer(driver);
	}

	public PageSolicitudPedido clickIconAñadirPedido() throws Exception {
		waitForPageFinished(driver);
		clickAndWaitLoad(driver, By.xpath(XPathIconSolPedido));
		return PageSolicitudPedido.getNew(driver);
	}
	
	public boolean checkSolicitudesVisible(int maxSeconds) {
		return (isElementVisibleUntil(driver, By.xpath(XPathLineaPedido), maxSeconds));
	}
}
