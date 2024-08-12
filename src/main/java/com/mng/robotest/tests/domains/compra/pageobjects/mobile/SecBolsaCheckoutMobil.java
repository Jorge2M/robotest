package com.mng.robotest.tests.domains.compra.pageobjects.mobile;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class SecBolsaCheckoutMobil extends PageBase {

	private static final String XP_SEC_BOLSA = "//div[@id='checkout-bag-layer']/div";
	private static final String XP_LINE_ARTICLE = "//div[@class[contains(.,'loadedOnMobile')]]";
//	private static final String XP_AMOUNT = "//*[@data-testid='bag.item.info']/p[2]";
	private static final String XP_IMPORTS = ".//div[@class[contains(.,'card__header-right')]]/div/p";
	private static final String XP_CLOSE_ASPA = "//button[text()='✕']";
	
	public boolean isVisible() {
		return state(VISIBLE, XP_SEC_BOLSA).check();
	}
	
	public void close() {
		try {
			closePage();
		} catch (StaleElementReferenceException e) {
			waitMillis(100);
			closePage();
		}
	}	
	
	private void closePage() {
		click(XP_CLOSE_ASPA).exec();
	}
	
	public float getSumPreciosArticles() {
		try {
			return getSumPreciosArticlesInternal();
		} catch (StaleElementReferenceException e) {
			waitMillis(1000);
			return getSumPreciosArticlesInternal();
		}
	}
	
	private float getSumPreciosArticlesInternal() {
		float sumPrecios = 0.0f;
		for (var article : getElements(XP_LINE_ARTICLE)) {
			sumPrecios+=getMinPrice(article);
		}
		return sumPrecios;
	}
	
	private float getMinPrice(WebElement article) {
		float minPrice = 999999999f;
		for (var priceElem : getImports(article)) {
			String textImport = priceElem.getText();
			float price = ImporteScreen.getFloatFromImporteMangoScreen(textImport);
			if (price<minPrice) {
				minPrice = price;
			}
		}
		return minPrice;
	}
	
	private List<WebElement> getImports(WebElement article) {
		try {
			return getElements(article, XP_IMPORTS);
		}
		catch (StaleElementReferenceException e) {
			waitMillis(500);
			return getElements(article, XP_IMPORTS);
		}
	}

}
