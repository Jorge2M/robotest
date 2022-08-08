package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;


public class SectionPrendas extends PageBase {

	private final static String XPATH_ARTICULO = "//*[@data-testid='myPurchases.detail.product']";
	private final static String XPATH_DATA_ARTICULO = "//*[@data-testid[contains(.,'detail.productInfo')]]/../..";
	private final static String XPATH_REFERENCIA_ARTICULO = "//*[@data-testid[contains(.,'detail.reference')]]";
	private final static String XPATH_NOMBRE_ARTICULO = "//button[@class[contains(.,'text-title')]]";
	private final static String XPATH_PRECIO_ARTICULO = "//*[@data-testid[contains(.,'detail.productPrice')]]/div";
	
	public int getNumPrendas() {
		return (driver.findElements(By.xpath(XPATH_ARTICULO)).size());
	}
	
	private String getXPathArticulo(int position) {
		return "(" + XPATH_ARTICULO + ")[" + position + "]";
	}
	private String getXPathLinkArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + "//button";
	}
	private String getXPathDataArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + XPATH_DATA_ARTICULO;
	}
	private String getXPathReferenciaArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XPATH_REFERENCIA_ARTICULO);
	}
	private String getXPathNombreArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XPATH_NOMBRE_ARTICULO);
	}	
	private String getXPathPrecioArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XPATH_PRECIO_ARTICULO);
	}
	
	public String getReferenciaArticulo(int posArticulo) {
		String xpathReferencia = getXPathReferenciaArticulo(posArticulo);
		return (driver.findElement(By.xpath(xpathReferencia)).getText().replaceAll("\\D+",""));
	}
	public String getNombreArticulo(int posArticulo) {
		String xpathNombre = getXPathNombreArticulo(posArticulo);
		return (driver.findElement(By.xpath(xpathNombre)).getText());
	}
	public String getPrecioArticulo(int posArticulo) {
		String xpathPrecio = getXPathPrecioArticulo(posArticulo);
		return (driver.findElement(By.xpath(xpathPrecio)).getText());
	}
	public void selectArticulo(int posArticulo) {
		String xpathLinkArticulo = getXPathLinkArticulo(posArticulo);
		driver.findElement(By.xpath(xpathLinkArticulo)).click();
	}
	
}
