package com.mng.robotest.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageRedsysSim extends PageObjTM {

	public enum OptionRedSys {
		Autenticacion_con_exito("AUTENTICADA"),
		Denegar_autenticacion("NO_AUTENTICADA"),
		Tarjeta_no_registrada_Finanet("NO_FINANET");
		
		private String value;
		private OptionRedSys(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public String getNombre() {
			return name().replace("_", "");
		}
	}
	
	private static final String XPathButtonEnviar = "//input[@id='boton']";
	
	private String getXPathOption(OptionRedSys option) {
		return "//input[@value='" + option.getValue() + "']";
	}
	
	public PageRedsysSim(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage() {
		By byOption = By.xpath(getXPathOption(OptionRedSys.Autenticacion_con_exito));
		return state(State.Present, byOption).check();
	}
	
	public void selectOption(OptionRedSys option) {
		By byOption = By.xpath(getXPathOption(option));
		click(byOption).exec();
	}
	
	public void clickEnviar() {
		click(By.xpath(XPathButtonEnviar)).exec();
	}
	
}
