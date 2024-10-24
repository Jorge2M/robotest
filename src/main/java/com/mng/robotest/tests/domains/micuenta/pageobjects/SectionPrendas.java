package com.mng.robotest.tests.domains.micuenta.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

public class SectionPrendas extends PageBase {

	private static final String XP_ARTICULO = "//*[@data-testid='myPurchases.detail.product']";
	private static final String XP_DATA_ARTICULO = "//*[@data-testid[contains(.,'detail.productInfo')]]/../..";
	private static final String XP_REFERENCIA_ARTICULO = "//*[@data-testid[contains(.,'detail.reference')]]";
	private static final String XP_NOMBRE_ARTICULO = "//button[@class[contains(.,'text-title')]]";
	private static final String XP_PRECIO_ARTICULO = "//*[@data-testid[contains(.,'myPurchases.detail.paidPrice')]]";	
	
	public int getNumPrendas() {
		return getElements(XP_ARTICULO).size();
	}
	
	private String getXPathArticulo(int position) {
		return "(" + XP_ARTICULO + ")[" + position + "]";
	}
	private String getXPathLinkArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + "//button";
	}
	private String getXPathDataArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + XP_DATA_ARTICULO;
	}
	private String getXPathReferenciaArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XP_REFERENCIA_ARTICULO);
	}
	private String getXPathNombreArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XP_NOMBRE_ARTICULO);
	}	
	private String getXPathPrecioArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XP_PRECIO_ARTICULO);
	}
	
	public String getReferenciaArticulo(int posArticulo) {
		String xpathReferencia = getXPathReferenciaArticulo(posArticulo);
		return getElement(xpathReferencia).getText().replaceAll("\\D+","");
	}
	public String getNombreArticulo(int posArticulo) {
		String xpathNombre = getXPathNombreArticulo(posArticulo);
		return getElement(xpathNombre).getText();
	}
	public String getPrecioArticulo(int posArticulo) {
		String xpathPrecio = getXPathPrecioArticulo(posArticulo);
		return getElement(xpathPrecio).getText();
	}
	public void selectArticulo(int posArticulo) {
		String xpathLinkArticulo = getXPathLinkArticulo(posArticulo);
		getElement(xpathLinkArticulo).click();
	}
	
}
