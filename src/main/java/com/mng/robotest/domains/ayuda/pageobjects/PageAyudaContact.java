package com.mng.robotest.domains.ayuda.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.legal.pageobjects.LTPageAyudaContact;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageAyudaContact extends PageBase {

	private static final String XPATH_ICON = "//*[@data-testid='content-contact-list-item-icon']";
	private static final String XPATH_ESCRIBENOS_UN_MENSAJE = XPATH_ICON + "//self::*[@class[contains(.,'email')]]";
	private static final String XPATH_LLAMA_A_NUESTRO_TELEFONO = XPATH_ICON + "//self::*[@class[contains(.,'phone')]]";
	
	public PageAyudaContact() {
		super(new LTPageAyudaContact());
	}
	
	public boolean isPage() {
		return state(Visible, XPATH_ESCRIBENOS_UN_MENSAJE).check();
	}
	
	public void clickEscribenosUnMensaje() {
		click(XPATH_ESCRIBENOS_UN_MENSAJE).exec();
	}
	
	public void clickLlamaANuestroTelefono() {
		click(XPATH_LLAMA_A_NUESTRO_TELEFONO).exec();
	}
}
