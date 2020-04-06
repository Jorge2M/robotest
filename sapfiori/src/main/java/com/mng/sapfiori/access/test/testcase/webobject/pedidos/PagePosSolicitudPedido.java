package com.mng.sapfiori.access.test.testcase.webobject.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputLabel;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputWithIconForSelectItem;
import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePosSolicitudPedido extends PageObject {

	public enum SectionPageSolPedido {
		InfGeneral("Información general"),
		InfContacto("Información de contacto"),
		FuenteAprov("Fuente de aprovisionamiento"),
		CantidadFecha("Cantidad y fecha"),
		Notas("Notas"),
		Anexo("Anexo");
		
		public String xpath;
		private SectionPageSolPedido(String label) {
			this.xpath = "//bdi[text()='" + label + "']";
		}
	}
	
	public static final String TitlePage = "Posición de solicitud de pedido";
	private static final String XPathTitle = "//h1[text()[contains(.,'" + TitlePage + "')]]";
	private static final String XPathAplicarButotn = "//button//bdi[text()='Aplicar']";
	
	private PagePosSolicitudPedido(WebDriver driver) {
		super(driver);
	}
	public static PagePosSolicitudPedido getNew(WebDriver driver) {
		return new PagePosSolicitudPedido(driver);
	}
	
	public boolean checkIsPage(int maxSeconds) throws Exception {
		return (state(Visible, By.xpath(XPathTitle)).wait(maxSeconds).check());
	}
	
	public void selectSection(SectionPageSolPedido section) {
		if (!isSectionSelected(section)) {
			click(By.xpath(section.xpath)).exec();
		}
	}
	
	private boolean isSectionSelected(SectionPageSolPedido section) {
		String idSection = driver.findElement(By.xpath(section.xpath)).getAttribute("id");
		if (idSection!=null) {
			return idSection.contains("BarButtonSelected");
		}
		return false;
	}
	
	public InputWithIconForSelectItem getInputWithIcon(InputFieldPedido inputType) throws IllegalArgumentException {
		if (!inputType.isIcon) {
			throw new IllegalArgumentException("Argument " + inputType + " has not flag isIcon = true");
		}
		return elementsMaker.getInputWithIconForSelectItem(inputType.label);
	}
	
	public InputLabel getInputWithoutIcon(InputFieldPedido inputType) throws IllegalArgumentException {
		if (inputType.isIcon) {
			throw new IllegalArgumentException("Argument " + inputType + " has flag isIcon = true");
		}
		return elementsMaker.getInputWithoutIcon(inputType.label);
	}
	
	public InputLabel getInput(InputFieldPedido inputType) {
		if (inputType.isIcon) {
			return getInputWithIcon(inputType);
		}
		return getInputWithoutIcon(inputType);
	}
	
	public PageSolicitudPedido clickAplicar() {
		click(By.xpath(XPathAplicarButotn)).exec();
		return PageSolicitudPedido.getNew(driver);
	}
}
