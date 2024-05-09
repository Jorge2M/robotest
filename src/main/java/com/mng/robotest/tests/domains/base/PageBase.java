package com.mng.robotest.tests.domains.base;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.datatest.DataMantoTest;
import com.mng.robotest.tests.domains.base.datatest.DataTest;
import com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts;
import com.mng.robotest.tests.domains.legal.legaltexts.LegalTextsPage;
import com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.data.CodIdioma;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu;

public class PageBase extends PageObjTM {

	protected final Channel channel;
	protected final AppEcom app;
	protected final InputParamsMango inputParamsSuite;
	protected DataTest dataTest = TestBase.DATA_TEST.get();
	protected DataMantoTest dataMantoTest = TestMantoBase.DATA_MANTO_TEST.get();
	protected LegalTextsPage legalTexts;
	protected static final String KEYS_CLEAR_INPUT = Keys.chord(Keys.CONTROL,"a", Keys.DELETE);
	protected static final String TAG = "@TAG_ELEMENT";
	
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
	
	protected void scrollEjeY(int y) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + y + ")", "");
	}
	
	protected void keyDown(int times) {
		pushKey(Keys.ARROW_DOWN, times);
	}
	protected void keyUp(int times) {
		pushKey(Keys.ARROW_UP, times);
	}
	
	protected boolean isMobile() {
		return channel==Channel.mobile;
	}
	protected boolean isDevice() {
		return channel.isDevice();
	}
	protected boolean isDesktop() {
		return channel==Channel.desktop;
	}
	protected boolean isTablet() {
		return channel==Channel.tablet;
	}	
	protected boolean isOutlet() {
		return app==AppEcom.outlet;
	}
	protected boolean isShop() {
		return app==AppEcom.shop;
	}	
	protected boolean isIdioma(CodIdioma codIdioma) {
		return codIdioma.isEquals(dataTest.getIdioma());
	}
	protected boolean isCountry(PaisShop paisShop) {
		return paisShop.isEquals(dataTest.getPais());
	}

	protected Optional<WebElement> findElement(String xpath) {
	    try {
	        return Optional.of(getElement(xpath));
	    } catch (NoSuchElementException e) {
	        return Optional.empty();
	    }
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
		return state(PRESENT, xpath).wait(seconds).check();
	}
	
	public boolean isTitleAssociatedToMenu(String menuName, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isTitleAssociatedToMenu(menuName)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
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
	
	public void waitForPageLoaded() {
		waitForPageLoaded(driver);
	}
	
	public boolean isTextInURL(String text, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (driver.getCurrentUrl().contains(text)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	protected void inputClearAndSendKeys(String xpath, String keys) {
		var element = getElement(xpath);
		element.clear();
		element.sendKeys(keys);
	}

}
