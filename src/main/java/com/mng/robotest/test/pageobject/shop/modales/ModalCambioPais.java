package com.mng.robotest.test.pageobject.shop.modales;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Pais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalCambioPais extends PageBase {
	
	private static final String XPATH_MODAL = "//div[(@id='cambioPais' or @id='seleccionPais') and @class='modalPopUp']"; 
	private static final String XPATH_MODAL_SELECT_PROVINCIA = "//div[@class='wrapper-cell']/div[@id='seleccionProvincia']";
	private static final String XPATH_ASPA_CLOSE = "//div[@data-ga-category='modal-deteccion-ip' and @class[contains(.,'closeModal')]]";
	
	public String getXPath_linkToConfirmPais(String hrefPais) {
		return "//div[@class='navOtherCountry']//a[@href[contains(.,'" + hrefPais + "')]]";
	}
	
	public String getXPath_buttonToChangePais(String urlAccesoPais) {
		return (XPATH_MODAL + "//div[@class[contains(.,'modalConfirmacionPais')]]//a[@class[contains(.,'_langBtn')] and @href[contains(.,'" + urlAccesoPais + "')]]");
	}
	
	public boolean isVisibleModalUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_MODAL)).wait(maxSeconds).check());
	}
	
	public boolean isVisibleModalSelecProvincia() {
		return (state(Visible, By.xpath(XPATH_MODAL_SELECT_PROVINCIA)).check());
	}
	
	public boolean isLinkToConfirmPais(String hrefPais) {
		String xpath = getXPath_linkToConfirmPais(hrefPais);
		return (state(Present, By.xpath(xpath)).check());
	}
	
	public Pais getPaisOfButtonForChangePais(List<Pais> listPaisesCandidatos, String urlBaseTest) 
			throws Exception {
		Iterator<Pais> it = listPaisesCandidatos.iterator();
		while (it.hasNext()) {
			Pais paisCandidato = it.next();
			String urlAccesoPais = paisCandidato.getUrlPaisEstandar(urlBaseTest);
			if (new ModalCambioPais().isButtonToChangePais(urlAccesoPais)) {
				return (paisCandidato);
			}
		}
		
		return null;
	}
	
	public boolean isButtonToChangePais(String urlAccesoPais) {
		String xpath = getXPath_buttonToChangePais(urlAccesoPais);
		return (state(Present, By.xpath(xpath)).check());
	}
	
	public String getTextPaisButtonChagePais() {
		String xpath = getXPath_buttonToChangePais(""/*urlAccesoPais*/);
		return (driver.findElement(By.xpath(xpath)).getText());
	}	
	
	public String getHRefPaisButtonChagePais() {
		String xpath = getXPath_buttonToChangePais(""/*urlAccesoPais*/);
		return (driver.findElement(By.xpath(xpath)).getAttribute("href"));
	}
	
	public void clickButtonChangePais() {
		String xpath = getXPath_buttonToChangePais(""/*urlAccesoPais*/);
		click(By.xpath(xpath)).exec();
	}
	
	public void closeModalIfVisible() {
		if (isVisibleModalUntil(0)) {
			driver.findElement(By.xpath(XPATH_ASPA_CLOSE)).click();
			try {
				new WebDriverWait(driver, 1).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPATH_MODAL)));
			}
			catch (Exception e) {
				/*
				 * Si no se cierra continuamos
				 */
			}
		}
	}
}
