package com.mng.sapfiori.access.test.testcase.webobject.iconsmenu;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalLoading;
import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import com.mng.sapfiori.access.test.testcase.webobject.pedidos.PageGestionSolPedidoBuyer;
import com.mng.sapfiori.access.test.testcase.webobject.reclassifprods.PageSelProdsToReclassify;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageIconsMenu extends PageObject {
	
	private final static String XPathLabelInitialPageSpanish = "//h1[text()='Página inicial']";

	
	private PageIconsMenu(WebDriver driver) {
		super(driver);
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
		return (state(Visible, By.xpath(XPathLabelInitialPageSpanish))
				.wait(maxSeconds).check());
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

	private void clickOption(List<String> textsInIcon) throws Exception {
		String xpath = getXPathOption(textsInIcon);
		clickAndWaitLoad(driver, By.xpath(xpath));
		ModalLoading modalLoading = elementsMaker.getModalLoading();
		modalLoading.isVisibleUntil(3);
		modalLoading.isInvisibleUntil(10);
	}
}
