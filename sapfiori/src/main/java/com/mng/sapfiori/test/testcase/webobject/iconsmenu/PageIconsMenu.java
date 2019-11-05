package com.mng.sapfiori.test.testcase.webobject.iconsmenu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.webobject.purchasereqs.PageManagePRsByBuyer;
import com.mng.sapfiori.test.testcase.webobject.reclassifprods.PageSelProdsToReclassify;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageIconsMenu extends WebdrvWrapp {
	
	private final WebDriver driver;
	
	private final static String XPathLabelInitialPageSpanish = "//h1[text()='Página inicial']";
	private final static String TagNameOption = "@TagNameOption";
	private final static String XPathOptionWithTag = "//div[@aria-label[contains(.,'" + TagNameOption + "')]]";

	
	private PageIconsMenu(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageIconsMenu getNew(WebDriver driver) {
		return new PageIconsMenu(driver);
	}
	
	private String getXPathOption(String title) {
		return XPathOptionWithTag.replace(TagNameOption, title);
	}
	
	public boolean checkIsInitialPageSpanish(int maxSeconds) {
		return (
			WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathLabelInitialPageSpanish), maxSeconds));
	}
	
	public PageSelProdsToReclassify clickClasificarProductos() throws Exception {
		String title = PageSelProdsToReclassify.option.getTitleIcon();
		clickOption(title);
		return PageSelProdsToReclassify.getNew(driver);
	}
	
	public PageManagePRsByBuyer clickManagePurchaseRequisitionsBuyer() throws Exception {
		String title = PageManagePRsByBuyer.option.getTitleIcon();
		clickOption(title);
		return PageManagePRsByBuyer.getNew(driver);
	}

	private final static String XPathDivLoading = "//div[@class[contains(.,'sapUiLocalBusy')]]";
	
	private void clickOption(String title) throws Exception {
		String xpath = getXPathOption(title);
		clickAndWaitLoad(driver, By.xpath(xpath));
		isElementVisibleUntil(driver, By.xpath(XPathDivLoading), 3);
		isElementInvisibleUntil(driver, By.xpath(XPathDivLoading), 10);
	}
}
