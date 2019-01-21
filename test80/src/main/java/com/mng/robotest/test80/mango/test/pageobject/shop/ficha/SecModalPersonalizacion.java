package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class SecModalPersonalizacion extends WebdrvWrapp {

	public enum ModalElement implements ElementPage {
		Seccion("//div[@class='customization-form']"),
		StartProcces("//button[@class[contains(.,'customization-add-bag')]]", null),
		BotonIniciar("//button[@class='customization-btn']", "//button[@id='productFormPersonalize']"),
		PantallaPrevia("div[@class='customization-action']"),

		StepProof("//li[@class[contains(.,'breadcrumb--active')]]", null),
		Step1Proof("//h1[text()[contains(.,'1.')]]"),
		Step2Proof("//h1[text()[contains(.,'2.')]]"),
		Step3Proof("//h1[text()[contains(.,'3.')]]"),
		Step4Proof("//h1[text()[contains(.,'4.')]]"),
		
		HeaderProof("//div[@class='customization-header']", null),
		BackProof("//button[@class='back-btn']", null),
		BolsaProof("//div[@class='sbi-customization-content']", null),
		Continue("//button[@class[contains(.,'sg-t-btn')]]", "//button[@class='sg-s-btn']"),
		addToBag("", "//button[@class[contains(.,'sg-s-btn')]]"),
		
		Modal("//div[@class[contains(.,'customization-wrapper')]]", "//div[@class='customization']"),
		Icons("//div[@class='custom-icons']", null),
		Initials("//button[@class='motif-option'][1]", null),
		RadioIcon("//button[@class='motif-option'][2]", null),
		IconSelecction("//div[@class[contains(.,'custom-icons')]]", "//img[@alt='Abejorro']"),
		PositionButton("//button[@class[contains(.,'customization-position-item')]][1]", "//div[@class='customization-position-item'][1]"),
		ColorsContainer("//div[@class='colors']"),
		SizeContainer("//div[@class='customization-size']"),
		GoToBag("//span[@class[contains(.,'cart-button')]]");
		
		String element;
		String mobile_element;
		
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
