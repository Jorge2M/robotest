package com.mng.robotest.tests.domains.ficha.pageobjects;


import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.tests.domains.base.PageBase;

public class SecModalPersonalizacion extends PageBase {

	public enum ModalElement implements ElementPage {
		SECCION("//div[@class='customization-form']"),
		ANADIR_BORDADO_LINK("//button[@class='customization-btn']", "//button[@id='productFormPersonalize']"),
		PANTALLA_PREVIA("div[@class='customization-action']"),
		STEP_PROOF("//li[@class[contains(.,'breadcrumb--active')]]"),
		STEP1_PROOF("//*[text()[contains(.,'1. ')]]"),
		STEP2_PROOF("//*[text()[contains(.,'2. ')]]"),
		STEP3_PROOF("//*[text()[contains(.,'3. ')]]"),
		STEP4_PROOF("//*[text()[contains(.,'4. ')]]"),
		HEADER_PROOF("//*[@class='customization-header']"),
		BACK_PROOF("//button[@class='back-btn']"),
		
		//TODO me han de pasar el data-testid 
		//BolsaProof("//div[@class='sbi-customization-content' or @class[contains(.,'_3hjWl')]]"),
		BOLSA_PROOF("//*[text()='Personalizado']"),
		
		SIGUIENTE("//button[@class[contains(.,'sg-button-primary')]]"),
		ADD_TO_BAG("", "//button[@class[contains(.,'sg-s-btn')]]"),
		BOTON_LUGAR_BORDAD("//button[@class='customization-position-item']"),
		MODAL("//div[@class[contains(.,'customization')]]", "//div[@class[contains(.,'customization')]]"),
		ICONS("//div[@class='custom-icons']"),
		INITIALS("//button[@class='motif-option'][1]"),
		BUTTON_UN_ICONO("//button[@class[contains(.,'motif-option')]][1]"),
		ICON_SELECTION("//div[@class[contains(.,'custom-icons')]]", "//img[@alt='Abejorro']"),
		POSITION_BUTTON("//div[@role='button' and @class[contains(.,'position')]]"),
		COLORS_CONTAINER("//div[@role='button' and @class[contains(.,'color')]]"),
		SIZE_CONTAINER("//div[@role='button' and @class[contains(.,'size')]]"),
		GO_TO_BACK("//span[@class[contains(.,'cart-button')]]");
		
		By by = null;
		By byMobil = null;
		ModalElement(String xPath) {
			by = By.xpath(xPath);
		}
		
		ModalElement(String xPath, String xPathMobil) {
			this.by = By.xpath(xPath);
			this.byMobil = By.xpath(xPathMobil);
		}
		
		@Override
		public By getBy() {
			return by;
		}
		
		@Override
		public By getBy(Channel channel) {
			if (channel.isDevice() && this.byMobil != null) {
				return this.byMobil;
			}
			return this.by;
		}
	}
}
