package com.mng.sapfiori.test.testcase.webobject.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputBase;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputWithIconForSelectItem;
import com.mng.sapfiori.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PagePosSolicitudPedido extends WebdrvWrapp {

	private final WebDriver driver;
	private final StandarElementsMaker standarMaker;

	private enum Section {
		InfGeneral("Información general"),
		InfContacto("Información de contacto"),
		FuenteAprov("Fuente de aprovisionamiento"),
		CantidadFecha("Cantidad y fecha"),
		Notas("Notas"),
		Anexo("Anexo");
		
		public String xpath;
		private Section(String label) {
			this.xpath = "//bdi[text()='" + label + "']";
		}
	}
	
	public enum InputPage {
		Material("Material", Section.InfGeneral, true),
		Centro("Centro", Section.InfGeneral, true),
		Temporada("Temporada", Section.InfGeneral, true),
		AñoTemporada("Año de temporada", Section.InfGeneral, true),
		OrgCompras("Purchasing Organization", Section.InfContacto, true),
		GrupoCompras("Purchasing Group", Section.InfContacto, true),
		Customer("Customer", Section.InfContacto, false),
		Quantity("Quantity", Section.CantidadFecha, false),
		FechaEntrega("Fecha de entrega", Section.CantidadFecha, false),
		IdCurvaDistrib("ID curva distrib.", Section.CantidadFecha, true);
		
		public String label;
		public Section section;
		public boolean isIcon;
		private InputPage(String label, Section section, boolean isIcon) {
			this.label = label;
			this.section = section;
			this.isIcon = isIcon;
		}
	}
	
	public static final String TitlePage = "Posición de solicitud de pedido";
	private static final String XPathTitle = "//h1[text()[contains(.,'" + TitlePage + "')]]";
	private static final String XPathAplicarButotn = "//button//bdi[text()='Aplicar']";
	
	private PagePosSolicitudPedido(WebDriver driver) {
		this.driver = driver;
		this.standarMaker = StandarElementsMaker.getNew(driver);
	}
	public static PagePosSolicitudPedido getNew(WebDriver driver) {
		return new PagePosSolicitudPedido(driver);
	}
	
	public boolean checkIsPage(int maxSeconds) throws Exception {
		return isElementVisibleUntil(driver, By.xpath(XPathTitle), maxSeconds);
	}
	
	public void selectSection(Section section) throws Exception {
		if (!isSectionSelected(section)) {
			clickAndWaitLoad(driver, By.xpath(section.xpath));
		}
	}
	
	private boolean isSectionSelected(Section section) {
		String idSection = driver.findElement(By.xpath(section.xpath)).getAttribute("id");
		if (idSection!=null) {
			return idSection.contains("BarButtonSelected");
		}
		return false;
	}
	
	public InputWithIconForSelectItem getInputWithIcon(InputPage inputType) throws IllegalArgumentException {
		if (!inputType.isIcon) {
			throw new IllegalArgumentException("Argument " + inputType + " has not flag isIcon = true");
		}
		return standarMaker.getInputWithIconForSelectItem(inputType.label);
	}
	
	public InputBase getInputWithoutIcon(InputPage inputType) throws IllegalArgumentException {
		if (inputType.isIcon) {
			throw new IllegalArgumentException("Argument " + inputType + " has flag isIcon = true");
		}
		return standarMaker.getInputWithoutIcon(inputType.label);
	}
	
	public InputBase getInput(InputPage inputType) {
		if (inputType.isIcon) {
			return getInputWithIcon(inputType);
		}
		return getInputWithoutIcon(inputType);
	}
	
	public PageSolicitudPedido clickAplicar() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathAplicarButotn));
		return PageSolicitudPedido.getNew(driver);
	}
}
