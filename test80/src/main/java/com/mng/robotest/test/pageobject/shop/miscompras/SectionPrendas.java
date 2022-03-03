package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class SectionPrendas extends PageObjTM {

	private static String XPathArticulo = "//*[@data-testid='myPurchases.detail.product']";
	private static String XPathDataArticulo = "//*[@data-testid[contains(.,'detail.productInfo')]]/../..";
	private static String XPathReferenciaArticulo = "//*[@data-testid[contains(.,'detail.reference')]]";
	private static String XPathNombreArticulo = "//button[@class[contains(.,'sg-action')]]";
	private static String XPathPrecioArticulo = "//*[@data-testid[contains(.,'detail.productPrice')]]/div";
	
	public SectionPrendas(WebDriver driver) {
		super(driver);
	}
	
	public int getNumPrendas() {
		return (driver.findElements(By.xpath(XPathArticulo)).size());
	}
	
	private String getXPathArticulo(int position) {
		return "(" + XPathArticulo + ")[" + position + "]";
	}
	private String getXPathLinkArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + "//button";
	}
	private String getXPathDataArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + XPathDataArticulo;
	}
	private String getXPathReferenciaArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XPathReferenciaArticulo);
	}
	private String getXPathNombreArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XPathNombreArticulo);
	}	
	private String getXPathPrecioArticulo(int posArticulo) {
		String xpathDataArticulo = getXPathDataArticulo(posArticulo);
		return (xpathDataArticulo + XPathPrecioArticulo);
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
