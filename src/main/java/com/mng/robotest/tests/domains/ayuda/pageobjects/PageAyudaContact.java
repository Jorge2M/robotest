package com.mng.robotest.tests.domains.ayuda.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.FORMULARIO_DE_AYUDA_LEGAL_TEXTS;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageAyudaContact extends PageBase {

	private static final String XP_ICON = "//*[@data-testid='content-contact-list-item-icon']";
	private static final String XP_ESCRIBENOS_UN_MENSAJE = XP_ICON + "//self::*[@class[contains(.,'email')]]";
	private static final String XP_LLAMA_A_NUESTRO_TELEFONO = XP_ICON + "//self::*[@class[contains(.,'phone')]]";
	
	public PageAyudaContact() {
		super(FORMULARIO_DE_AYUDA_LEGAL_TEXTS);
	}
	
	public boolean isPage() {
		return state(Visible, XP_ESCRIBENOS_UN_MENSAJE).check();
	}
	
	public void clickEscribenosUnMensaje() {
		click(XP_ESCRIBENOS_UN_MENSAJE).exec();
	}
	
	public void clickLlamaANuestroTelefono() {
		click(XP_LLAMA_A_NUESTRO_TELEFONO).exec();
	}
}
