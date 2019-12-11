package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.wrapper.ElementPage;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class SecModalPersonalizacion extends WebdrvWrapp {

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
		
		String element = null;
		String mobile_element = null;
		
		ModalElement(String element) {
			this.element = element;
		}
		
		ModalElement(String element, String mobile_element) {
			this.element = element;
			this.mobile_element = mobile_element;
		}
		
		public String getXPath(Channel channel) {
			if (channel == Channel.movil_web && this.mobile_element != null) {
				return this.mobile_element;
			}
			return this.element;
		}

		public String getXPath() {
			return element;
		}
	}
}
