package com.mng.sapfiori.test.testcase.webobject.purchasereqs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.buscar.InputBuscador;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectEstandard;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectMultiValue;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputFilterWithConditions;
import com.mng.sapfiori.test.testcase.generic.webobject.makers.FieldFilterHeadMaker;
import com.mng.sapfiori.test.testcase.generic.webobject.pages.PageFilter;
import com.mng.sapfiori.test.testcase.webobject.iconsmenu.OptionMenu;

public class PageManagePRsByBuyer extends PageFilter {

	public static OptionMenu option = OptionMenu.ManagePRsBuyer;
	
	//Filters
	public final InputBuscador filterBuscar;
	public final SelectEstandard filterSelEstadoEdicion;
	public final InputFilterWithConditions filterPurchaseRequisition;
	public final SelectMultiValue filterSelClaseDocumento;
	
	private final static String XPathLineaPedido = "//tr[@id[contains(.,'ColumnListItem')]]";
	
	private PageManagePRsByBuyer(WebDriver driver) {
		super(option, driver);
		FieldFilterHeadMaker filterHeader = new FieldFilterHeadMaker(driver);
		filterBuscar = filterHeader.getInputBuscador();
		filterSelEstadoEdicion = filterHeader.getSelectEstandard("Estado de edición");
		filterPurchaseRequisition = filterHeader.getInputModalWithSelectConditions("Purchase Requisition");
		filterSelClaseDocumento = filterHeader.getSelectMultiValue("Clase documento");
	}
	
	public static PageManagePRsByBuyer getNew(WebDriver driver) {
		return new PageManagePRsByBuyer(driver);
	}
	
	public boolean checkSolicitudesVisible(int maxSeconds) {
		return (isElementVisibleUntil(driver, By.xpath(XPathLineaPedido), maxSeconds));
	}
}
