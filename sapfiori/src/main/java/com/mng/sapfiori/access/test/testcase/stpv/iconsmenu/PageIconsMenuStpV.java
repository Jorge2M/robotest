package com.mng.sapfiori.access.test.testcase.stpv.iconsmenu;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.stpv.pedidos.PageGestionSolPedidoBuyerStpV;
import com.mng.sapfiori.access.test.testcase.stpv.reclassifprods.PageSelProdsToReclassifyStpV;
import com.mng.sapfiori.access.test.testcase.webobject.iconsmenu.PageIconsMenu;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PageIconsMenuStpV {

	private final PageIconsMenu pageIconsMenu;
	
	private PageIconsMenuStpV(WebDriver driver) {
		this.pageIconsMenu = PageIconsMenu.getNew(driver);
	}
	private PageIconsMenuStpV(PageIconsMenu pageInitial) {
		this.pageIconsMenu = pageInitial;
	}
	
	public static PageIconsMenuStpV getNew(WebDriver driver) {
		return new PageIconsMenuStpV(driver);
	}
	public static PageIconsMenuStpV getNew(PageIconsMenu pageInitial) {
		return new PageIconsMenuStpV(pageInitial);
	}
    
    @Validation (
    	description=
    		"Aparece la página inicial de la aplicación cuando el acceso en Español " +
    		"(la esperamos hasta #{maxSeconds} segundos)",
    	level=State.Defect)
    public boolean checkIsInitialPageSpanish(int maxSeconds) {
    	return (pageIconsMenu.checkIsInitialPageSpanish(maxSeconds));
    }
    
	@Step (
		description="Seleccionar el option <b>Clasificar Productos</b>",
		expected="Aparece la página para clasificar los productos")
	public PageSelProdsToReclassifyStpV clickClasificarProductos() throws Exception {
		PageSelProdsToReclassifyStpV pageClassifProductosStpV = PageSelProdsToReclassifyStpV.getNew(
			pageIconsMenu.clickClasificarProductos());
		
		pageClassifProductosStpV.checkIsVisiblePage(5);
		return pageClassifProductosStpV;
	}
	
	@Step (
		description="Seleccionar el option <b>Manage Purchase Requisitions (Buyer)</b>",
		expected="Aparece la página correcta")
	public PageGestionSolPedidoBuyerStpV clickManagePurchaseRequisitionsBuyer() throws Exception {
		PageGestionSolPedidoBuyerStpV pageGestionPedidoStpV = PageGestionSolPedidoBuyerStpV.getNew(
			pageIconsMenu.clickManagePurchaseRequisitionsBuyer());
		
		pageGestionPedidoStpV.checkIsVisiblePage(5);
		return pageGestionPedidoStpV;
	}
}
