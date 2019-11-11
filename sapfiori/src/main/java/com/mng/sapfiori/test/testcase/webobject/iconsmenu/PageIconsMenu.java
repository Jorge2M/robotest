package com.mng.sapfiori.test.testcase.webobject.iconsmenu;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.webobject.pedidos.PageGestionSolPedidoBuyer;
import com.mng.sapfiori.test.testcase.webobject.reclassifprods.PageSelProdsToReclassify;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageIconsMenu extends WebdrvWrapp {
	
	private final WebDriver driver;
	
	private final static String XPathLabelInitialPageSpanish = "//h1[text()='Página inicial']";

	
	private PageIconsMenu(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageIconsMenu getNew(WebDriver driver) {
		return new PageIconsMenu(driver);
	}
	
	private String getXPathOption(List<String> textsInIcon) {
		String xpath = "//div[";
		Iterator<String> it = textsInIcon.iterator();
		while (it.hasNext()) {
			xpath+="@aria-label[contains(.,'" + it.next() + "')]";
			if (it.hasNext()) {
				xpath+=" and ";
			}
		}
		xpath+="]";
		return xpath;
	}
	
	public boolean checkIsInitialPageSpanish(int maxSeconds) {
		return (
			WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathLabelInitialPageSpanish), maxSeconds));
	}
	
	public PageSelProdsToReclassify clickClasificarProductos() throws Exception {
		List<String> textsInIcon = PageSelProdsToReclassify.option.getTextsInIcon();
		clickOption(textsInIcon);
		return PageSelProdsToReclassify.getNew(driver);
	}
	
	public PageGestionSolPedidoBuyer clickManagePurchaseRequisitionsBuyer() throws Exception {
		List<String> textsInIcon = PageGestionSolPedidoBuyer.option.getTextsInIcon();
		clickOption(textsInIcon);
		return PageGestionSolPedidoBuyer.getNew(driver);
	}

	private final static String XPathDivLoading = "//div[@class[contains(.,'sapUiLocalBusy')]]";
	
	private void clickOption(List<String> textsInIcon) throws Exception {
		String xpath = getXPathOption(textsInIcon);
		clickAndWaitLoad(driver, By.xpath(xpath));
		isElementVisibleUntil(driver, By.xpath(XPathDivLoading), 3);
		isElementInvisibleUntil(driver, By.xpath(XPathDivLoading), 10);
	}
}
