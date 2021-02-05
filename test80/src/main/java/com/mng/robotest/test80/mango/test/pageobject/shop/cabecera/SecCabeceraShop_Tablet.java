package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Clickable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;


public class SecCabeceraShop_Tablet extends SecCabecera {

	private final static String XPathNumArticlesBolsa = "//span[@class[contains(.,'shopping-bag-items')]]";
	
	public enum IconoCabeceraShop_Tablet implements ElementPage {
		bolsa("//div[@id='shoppingBag']//span[@class='icon']"),
		lupa("//span[@class='menu-search-label']");
		
		private By by;
		private IconoCabeceraShop_Tablet(String xpath) {
			by = By.xpath(xpath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}
	
    private SecCabeceraShop_Tablet(WebDriver driver) {
    	super(Channel.tablet, AppEcom.shop, driver);
    }
    
    public static SecCabeceraShop_Tablet getNew(WebDriver driver) {
    	return (new SecCabeceraShop_Tablet(driver));
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return XPathNumArticlesBolsa;
    }
    
    @Override
    public boolean isInStateIconoBolsa(State state, int maxSeconds) {
    	return (isElementInStateUntil(IconoCabeceraShop_Tablet.bolsa, state, maxSeconds));
    }
    
    @Override
    public void clickIconoBolsa() {
    	click(IconoCabeceraShop_Tablet.bolsa);
    }
    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsToWait) {
    	clickIfClickableUntil(IconoCabeceraShop_Tablet.bolsa, maxSecondsToWait);
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverIcono(IconoCabeceraShop_Tablet.bolsa);
    }
    
    public boolean isElementInStateUntil(IconoCabeceraShop_Tablet icono, State state, int maxSeconds) {
    	return (state(state, icono.getBy()).wait(maxSeconds).check());
    }

	 public boolean isClickableUntil(IconoCabeceraShop_Tablet icono, int maxSeconds) {
		return (state(Clickable, icono.getBy()).wait(maxSeconds).check());
	}

	public void clickIfClickableUntil(IconoCabeceraShop_Tablet icono, int maxSecondsToWait) {
		if (isClickableUntil(icono, maxSecondsToWait)) {
			click(icono);
		}
	}
	
    public void clickIconoAndWait(IconoCabeceraShop_Tablet icono) {
    	click(icono.getBy()).type(TypeClick.javascript).exec(); //TODO
    }
    
    public boolean isIconoInStateUntil(IconoCabeceraShop_Tablet icono, State state, int maxSeconds) {
    	return (state(state, icono.getBy()).wait(maxSeconds).check());
    }

	public void click(IconoCabeceraShop_Tablet icono) {
		click(icono, app);
	}

	public void click(IconoCabeceraShop_Tablet icono, AppEcom app) {
		click(icono.getBy()).type(TypeClick.javascript).exec();
	}

	public void hoverIcono(IconoCabeceraShop_Tablet icono) {
		moveToElement(icono.getBy(), driver);
	}
	
}
