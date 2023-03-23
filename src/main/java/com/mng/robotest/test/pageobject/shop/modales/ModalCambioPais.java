package com.mng.robotest.test.pageobject.shop.modales;

import java.util.Iterator;
import java.util.List;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.beans.Pais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalCambioPais extends PageBase {
	
	private static final String XPATH_MODAL = "//div[(@id='cambioPais' or @id='seleccionPais') and @class='modalPopUp']"; 
	private static final String XPATH_MODAL_SELECT_PROVINCIA = "//div[@class='wrapper-cell']/div[@id='seleccionProvincia']";
	private static final String XPATH_ASPA_CLOSE = "//div[@data-ga-category='modal-deteccion-ip' and @class[contains(.,'closeModal')]]";
	
	public String getXPath_linkToConfirmPais(String hrefPais) {
		return "//div[@class='navOtherCountry']//a[@href[contains(.,'" + hrefPais + "')]]";
	}
	
	public String getXPathButtonToChangePais(String urlAccesoPais) {
		return (XPATH_MODAL + "//div[@class[contains(.,'modalConfirmacionPais')]]//a[@class[contains(.,'_langBtn')] and @href[contains(.,'" + urlAccesoPais + "')]]");
	}
	
	public boolean isVisibleModalUntil(int seconds) {
		return state(Visible, XPATH_MODAL).wait(seconds).check();
	}
	
	public boolean isVisibleModalSelecProvincia() {
		return state(Visible, XPATH_MODAL_SELECT_PROVINCIA).check();
	}
	
	public boolean isLinkToConfirmPais(String hrefPais) {
		String xpath = getXPath_linkToConfirmPais(hrefPais);
		return state(Present, xpath).check();
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
		String xpath = getXPathButtonToChangePais(urlAccesoPais);
		return state(Present, xpath).check();
	}
	
	public String getTextPaisButtonChagePais() {
		String xpath = getXPathButtonToChangePais("");
		return getElement(xpath).getText();
	}	
	
	public String getHRefPaisButtonChagePais() {
		String xpath = getXPathButtonToChangePais("");
		return getElement(xpath).getAttribute("href");
	}
	
	public void clickButtonChangePais() {
		click(getXPathButtonToChangePais("")).exec();
	}
	
	public void closeModalIfVisible() {
		if (isVisibleModalUntil(0)) {
			getElementVisible(XPATH_ASPA_CLOSE).click();
			state(Invisible, XPATH_MODAL).wait(1).check();
		}
	}
}
