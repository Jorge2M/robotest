package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.Mensajes;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecSearchDesktopOutlet extends PageObjTM implements SecSearch {
	
	private final static String XPathIconoLupa = "//span[@class='menu-search-icon']";
	private final static String XPathInputBuscador = "//input[@class='search-input']";
	private final static String XPathCloseAspa = "//span[@class='search-close']";

	private SecSearchDesktopOutlet(WebDriver driver) {
		super(driver);
	}
    
    public static SecSearchDesktopOutlet getNew(WebDriver driver) {
    	return (new SecSearchDesktopOutlet(driver));
    }
    
    @Override
    public void search(String referencia) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Mensajes.getXPathCapaCargando())));
        selectBuscador();
        setTextAndReturn(referencia);
    }
    
	@Override
	public void close() {
		click(By.xpath(XPathCloseAspa)).exec();
	}

	private void selectBuscador() {
		driver.findElement(By.xpath(XPathIconoLupa)).click(); 
		if (!state(Visible, By.xpath(XPathInputBuscador)).wait(1).check()) {
			driver.findElement(By.xpath(XPathIconoLupa)).click();
			state(Visible, By.xpath(XPathInputBuscador)).wait(1).check();
		}

		//No nos queda más remedio que incluir un delay puesto que el input_subrayado toma su tiempo para expandirse hacia la derecha
		waitMillis(700);
	}

    /**
     * Introducimos la referencia en el buscador y seleccionamos RETURN
     */
    private void setTextAndReturn(String referencia) {
        WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
        //sendKeysWithRetry(5, input, referencia);
        input.clear();
        input.sendKeys(referencia);
        input.sendKeys(Keys.RETURN);
        waitForPageLoaded(driver);
    }
}
