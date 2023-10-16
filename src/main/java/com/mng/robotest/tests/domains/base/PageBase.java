package com.mng.robotest.tests.domains.base;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ClickElement.BuilderClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.BuilderSelect;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.BuilderState;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.datatest.DataMantoTest;
import com.mng.robotest.tests.domains.base.datatest.DataTest;
import com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts;
import com.mng.robotest.tests.domains.legal.legaltexts.LegalTextsPage;
import com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageBase extends PageObjTM {

	protected final Channel channel;
	protected final AppEcom app;
	protected final InputParamsMango inputParamsSuite;
	protected DataTest dataTest = TestBase.DATA_TEST.get();
	protected DataMantoTest dataMantoTest = TestMantoBase.DATA_MANTO_TEST.get();
	protected LegalTextsPage legalTexts;
	
	public PageBase() {
		super();
		if (TestCaseTM.getTestCaseInExecution().isPresent()) {
			this.inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
			this.app = (AppEcom)inputParamsSuite.getApp();
			this.channel = inputParamsSuite.getChannel();
		} else {
			this.inputParamsSuite = null;
			this.app = AppEcom.shop;
			this.channel = Channel.desktop;			
		}
	}
	
	public PageBase(PageLegalTexts legalTexts) {
		this();
		this.legalTexts = FactoryLegalTexts.make(legalTexts);
	}
	
	public PageBase(WebDriver driver) {
		super(driver);
		this.inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		this.app = (AppEcom)inputParamsSuite.getApp();
		this.channel = inputParamsSuite.getChannel();
	}
	
	public PageBase(Channel channel, AppEcom app) {
		super();
		InputParamsMango inputParams = null;
		try {
			inputParams = (InputParamsMango)TestMaker.getInputParamsSuite();
		} catch (Exception e) {
			inputParams = null;
		}
		this.inputParamsSuite = inputParams;
		this.channel = channel;
		this.app = app;
	}	
	
	public BuilderClick click(String xpath) {
		return new BuilderClick(By.xpath(xpath), driver);
	}
	
	public BuilderState state(State state, String xpath) {
		return new BuilderState(state, By.xpath(xpath), driver);
	}
	
	public BuilderSelect select(String xpath, String value) {
		return new BuilderSelect(By.xpath(xpath), value, driver);
	}
	
	public WebElement getElement(String xpath) {
		return getElement(By.xpath(xpath));
	}
	public List<WebElement> getElements(String xpath) {
		return getElements(By.xpath(xpath));
	}
	public List<WebElement> getElements(WebElement element, String xpath) {
		return element.findElements(By.xpath(xpath));
	}	
	public WebElement getElement(By by) {
		return driver.findElement(by);
	}
	public WebElement getElement(WebElement element, String xpathChild) {
		return element.findElement(By.xpath(xpathChild));
	}
	public WebElement getElement(WebElement element, By byChild) {
		return element.findElement(byChild);
	}	
	public List<WebElement> getElements(By by) {
		return driver.findElements(by);
	}
	
	public WebElement getElementVisible(String xpath) {
		return getElementVisible(driver, By.xpath(xpath));
	}
	
	public List<WebElement> getElementsVisible(String xpath) {
		return getElementsVisible(driver, By.xpath(xpath));
	}
	
	public int getNumElementsVisible(String xpath) {
		return getNumElementsVisible(driver, By.xpath(xpath));
	}
	
    public List<WebElement> getElementsVisible(WebElement elementInput, String xpath) {
    	List<WebElement> listaReturn = new ArrayList<>();
        for (WebElement element : elementInput.findElements(By.xpath(xpath))) {
            if (element.isDisplayed()) {
                listaReturn.add(element);
            }
        }
        return listaReturn;
    }	

	public WebElement getElementPriorizingDisplayed(String xpath) {
		List<WebElement> elementsVisible = getElementsVisible(xpath);
		if (!elementsVisible.isEmpty()) {
			return elementsVisible.get(0);
		}
		List<WebElement> elementsPresent = getElements(xpath);
		if (!elementsPresent.isEmpty()) {
			return elementsPresent.get(0);
		}
		return null;
	}

	public WebElement getElementWeb(String xpath) {
		return getElementWeb(By.xpath(xpath), driver);
	}
	
    public void moveToElement(String xpath) {
        moveToElement(By.xpath(xpath));
    }
    public void moveToElement(By by) {
        WebElement webElem = driver.findElement(by);
        moveToElement(webElem);
    }
    public void moveToElement(WebElement element) {
    	moveToElement(element, driver);
    }
    
	public boolean isPRO() {
		if (TestCaseTM.getTestCaseInExecution().isEmpty()) {
			return false;
		}
		if (isEnvPRO()) {
			return true;
		}
		return isPRO(driver.getCurrentUrl());
	}
	
	public static boolean isEnvPRO() {
		String urlBase = TestMaker.getInputParamsSuite().getUrlBase();
		return isPRO(urlBase);
	}
    
	public static boolean isPRO(String url) {
		List<String> urlsPro   = Arrays.asList(
				"shop.mango.com", "shoptest.pro.mango.com", "www.mangooutlet.com", "outlettest.pro.mango.com");
		Iterator<String> itURLsPRO = null;
		itURLsPRO = urlsPro.iterator();
		while (itURLsPRO.hasNext()) {
			String urlStr = itURLsPRO.next();
			if (url.contains(urlStr)) {
				return true;
			}
		}
		return false;
	}	    
    
	protected void waitLoadPage() {
		waitForPageLoaded(driver);
	}
	
	protected void clickUserMenu(UserMenu userMenu) {
		new MenusUserWrapper().clickMenuAndWait(userMenu);
	}
	
	protected enum BringTo { FRONT, BACKGROUND }
	
	protected void bringElement(WebElement element, BringTo action) {
		String script = "arguments[0].style.display='none';";
		if (action==BringTo.FRONT) {
			script = 
					"arguments[0].style.display='block';" + 
					"arguments[0].style.zIndex='999';" +
					"arguments[0].style.position='absolute';";
		}
		((JavascriptExecutor) driver).executeScript(script, element);
	}
	
	protected Optional<LegalTextsPage> getLegalTextsPage() {
		if (legalTexts==null) {
			return Optional.empty();
		}
		return Optional.of(legalTexts);
	}
	
	protected boolean goToPageInNewTab(String windowFatherHandle) {
		String newPageHandle = switchToAnotherWindow(driver, windowFatherHandle);
		if (newPageHandle.compareTo(windowFatherHandle)==0) {
			return false;
		}
		waitForPageLoaded(driver, 10);
		return true;
	}
	
	protected void scrollVertical(int y) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + y + ")", "");
	}
	
	protected void keyDown(int times) {
		pushKey(Keys.ARROW_DOWN, times);
	}
	protected void keyUp(int times) {
		pushKey(Keys.ARROW_UP, times);
	}	
	private void pushKey(Keys key, int times) {
		var actions = new Actions(driver);
		for (int i=0; i<times; i++) {
			actions.keyDown(key).perform();
			waitMillis(100);
		}
	}
	
	protected void refreshPage() {
		driver.navigate().refresh();
	}
	
	protected void backPage() {
		driver.navigate().back();
		waitForPageLoaded(driver, 10);
	}
	
	public boolean isPresentElementWithText(String text, int seconds) {
		String xpath = "//*[text()[contains(.,'" + text + "')]]";
		return state(Present, xpath).wait(seconds).check();
	}
	
	public boolean isTitleAssociatedToMenu(String menuName) {
		String titlePage = driver.getTitle().toLowerCase();
		if (titlePage.contains(menuName.toLowerCase())) {
			return true;
		}
		if (menuName.contains(" ")) {
			return Arrays.stream(titlePage.split(" "))
				.filter(s -> !titlePage.contains(s))
				.findAny()
				.isEmpty();
		}
		return false;
	}	

}
