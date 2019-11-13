package com.mng.sapfiori.test.testcase.stpv.reclassifprods;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.stpv.modals.ModalSelectItemStpV;
import com.mng.sapfiori.test.testcase.webobject.reclassifprods.PageReclassifProducts;
import com.mng.sapfiori.test.testcase.webobject.reclassifprods.PageSelProdsToReclassify;
import com.mng.sapfiori.test.testcase.webobject.reclassifprods.PageSelProdsToReclassify.ProductData;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PageSelProdsToReclassifyStpV {
	
	private final PageSelProdsToReclassify pageSelProductsToReclassify;
	
	private PageSelProdsToReclassifyStpV(WebDriver driver) {
		pageSelProductsToReclassify = PageSelProdsToReclassify.getNew(driver);
	}
	private PageSelProdsToReclassifyStpV(PageSelProdsToReclassify pageClassifProductos) {
		this.pageSelProductsToReclassify = pageClassifProductos;
	}
	
	public static PageSelProdsToReclassifyStpV getNew(WebDriver driver) {
		return new PageSelProdsToReclassifyStpV(driver);
	}
	public static PageSelProdsToReclassifyStpV getNew(PageSelProdsToReclassify pageClassifProductos) {
		return new PageSelProdsToReclassifyStpV(pageClassifProductos);
	}
	
	public PageSelProdsToReclassify getPageObject() {
		return pageSelProductsToReclassify;
	}
	
	@Validation (
		description = "Aparece la página de <b>Clasificación de productos</b> (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsVisiblePage(int maxSeconds) {
		return (pageSelProductsToReclassify.isVisiblePage(maxSeconds));
	}
	
	@Step (
		description = "Clickar el icono del filtro <b>Esquema numeración</b>",
		expected = "Aparece el modal para definir los datos del filtro")
	public ModalSelectItemStpV clickIconEsquemaNumeracion() throws Exception {
		return (
			ModalSelectItemStpV.getNew(
				pageSelProductsToReclassify.filterEsquemaNumeracion.clickIconSetFilter()));
	}
	
	@Step (
		description = "Clickar el icono del filtro <b>Producto</b>",
		expected = "Aparece el modal para definir los datos del filtro")
	public ModalSelectItemStpV clickIconProducto() throws Exception {
		return (
			ModalSelectItemStpV.getNew(
				pageSelProductsToReclassify.filterProducto.clickIconSetFilter()));
	}
	
	@Step (
		description = "Clickar Botón <b>Ir</b>",
		expected = "Aparece una lista de Productos")
	public void clickIrButton() throws Exception {
		pageSelProductsToReclassify.clickIrButton();
	}

	@Step (
		description = "Seleccionar los siguientes productos: <b>#{listProductsToSelect}</b>",
		expected = "Los productos quedan seleccionados")
	public void selectProducts(List<String> listProductsToSelect) {
		pageSelProductsToReclassify.selectProducts(listProductsToSelect);
	}
	
	@Step (
		description = "Seleccionar el link <b>Volver a clasificar</b>",
		expected = "Aparece la página de Reclasificación de productos")
	public PageReclassifProductsStpV clickVolverAclasificar() throws Exception {
		PageReclassifProducts pageReclassifProducts = 
			pageSelProductsToReclassify.clickVolverAclasificar();
		
		PageReclassifProductsStpV pageReclassifProductsStpV = 
			PageReclassifProductsStpV.getNew(pageReclassifProducts);
		pageReclassifProductsStpV.checkIsPageUntil(3);
		
		return pageReclassifProductsStpV;
	}
	
	@Validation (
		description = "Todos los productos #{productsToReclassify} tienen el nuevo código <b>#{newCodEstadMerc}</b>",
		level=State.Defect)
	public boolean checkCodMercModified(String newCodEstadMerc, List<String> productsToReclassify) throws Exception {
    	List<ProductData> productsDataNew = pageSelProductsToReclassify.getData(productsToReclassify);
		for (ProductData product : productsDataNew) {
			if (newCodEstadMerc.compareTo(product.codEstadMerc)!=0) {
				return false;
			}
		}
		return true;
	}
}
