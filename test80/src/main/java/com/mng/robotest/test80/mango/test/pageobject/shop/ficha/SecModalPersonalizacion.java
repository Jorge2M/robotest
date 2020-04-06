package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;
import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;

public class SecModalPersonalizacion extends SeleniumUtils {

	public enum ModalElement implements ElementPage {
		Seccion("//div[@class='customization-form']"),
		AñadirBordadoLink("//button[@class='customization-btn']", "//button[@id='productFormPersonalize']"),
		PantallaPrevia("div[@class='customization-action']"),
		StepProof("//li[@class[contains(.,'breadcrumb--active')]]"),
		Step1Proof("//*[text()[contains(.,'1. ')]]"),
		Step2Proof("//*[text()[contains(.,'2. ')]]"),
		Step3Proof("//*[text()[contains(.,'3. ')]]"),
		Step4Proof("//*[text()[contains(.,'4. ')]]"),
		HeaderProof("//*[@class='customization-header']"),
		BackProof("//button[@class='back-btn']"),
		BolsaProof("//div[@class='sbi-customization-content']"),
		Siguiente("//button[@class[contains(.,'sg-button-primary')]]"),
		addToBag("", "//button[@class[contains(.,'sg-s-btn')]]"),
		botonLugarBordado("//button[@class='customization-position-item']"),
		Modal("//div[@class[contains(.,'customization')]]", "//div[@class[contains(.,'customization')]]"),
		Icons("//div[@class='custom-icons']"),
		Initials("//button[@class='motif-option'][1]"),
		ButtonUnIcono("//button[@class[contains(.,'motif-option')]][1]"),
		IconSelecction("//div[@class[contains(.,'custom-icons')]]", "//img[@alt='Abejorro']"),
		PositionButton("//div[@role='button' and @class[contains(.,'position')]]"),
		ColorsContainer("//div[@role='button' and @class[contains(.,'color')]]"),
		SizeContainer("//div[@role='button' and @class[contains(.,'size')]]"),
		GoToBag("//span[@class[contains(.,'cart-button')]]");
		
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
			if (channel == Channel.movil_web && this.byMobil != null) {
				return this.byMobil;
			}
			return this.by;
		}
	}
}
