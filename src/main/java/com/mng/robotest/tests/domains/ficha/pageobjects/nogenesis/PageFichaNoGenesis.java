package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis;

import static com.mng.robotest.tests.domains.ficha.pageobjects.commons.LinksAfterBolsa.COMPARTIR;
import static com.mng.robotest.tests.domains.ficha.pageobjects.commons.LinksAfterBolsa.DETALLE_PRODUCTO;
import static com.mng.robotest.tests.domains.ficha.pageobjects.commons.LinksAfterBolsa.ENVIO_GRATIS_TIENDA;

import java.util.List;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.LinksAfterBolsa;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ModEnvioYdevolNew;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders.Slider;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct.ItemBreadcrumb;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public abstract class PageFichaNoGenesis extends PageBase implements PageFicha {

	protected final SecDataProduct secDataProduct = new SecDataProduct(); //Name, color, talla section
	protected final SecFitFinder secFitFinder = new SecFitFinder(); //Guía de tallas v.Fit Finder
	protected final SecSliders secSliders = new SecSliders();
	protected final SecDetalleProduct secDetalleProductNew = new SecDetalleProduct();
	protected final ModEnvioYdevolNew modEnvioYdev = new ModEnvioYdevolNew();

	private static final String XP_LINK_ENVIO_GRATIS_TIENDA = "//button[@class[contains(.,'freeShipping')]]";
	private static final String XP_LINK_DISPONIBILIDAD_TIENDA = "//button[@id='garmentFinderOption']";
	private static final String XP_LINK_DETALLE_PRODUCTO = "//button[@id='productDetailOption']";
	private static final String XP_LINK_COMPARTIR = "//*[@id='optionsSocialTrigger']";	
	
	private String getXPathLink(LinksAfterBolsa linkType) {
		switch (linkType) {
		case ENVIO_GRATIS_TIENDA:
			return XP_LINK_ENVIO_GRATIS_TIENDA;
		case DISPONIBILIDAD_TIENDA:
			return XP_LINK_DISPONIBILIDAD_TIENDA;
		case DETALLE_PRODUCTO:
			return XP_LINK_DETALLE_PRODUCTO;
		case COMPARTIR:
		default:
			return XP_LINK_COMPARTIR;
		}
	}
	
	@Override
	public boolean isClickableColor(String colourCode) {
		return secDataProduct.isClickableColor(colourCode);
	}
	@Override
	public void clickColor(int numColor) {
		secDataProduct.clickColor(numColor);
	}
	@Override
	public void selectColor(String color) {
		secDataProduct.selectColor(color);
	}
	@Override
	public void selectColorWaitingForAvailability(String colourCode) {
		secDataProduct.selectColorWaitingForAvailability(colourCode);
	}
	@Override
	public String getCodeColor(ColorType colorType) {
		return secDataProduct.getCodeColor(colorType);
	}
	@Override
	public boolean isPresentColor(ColorType colorType) {
		return secDataProduct.isPresentColor(colorType);
	}
	@Override
	public List<String> getColorsGarment() {
		return secDataProduct.getColorsGarment();
	}
	@Override	
	public String getTituloArt() {
		return secDataProduct.getTituloArt();
	}
	@Override
	public String getPrecioFinalArticulo() {
		return secDataProduct.getPrecioFinalArticulo();
	}
	@Override
	public String getNombreColorSelected() {
		return secDataProduct.getNombreColorSelected();
	}
	@Override
	public String getPrecioTachadoFromFichaArt() {
		return secDataProduct.getPrecioTachadoFromFichaArt();
	}
	@Override
	public void selectTallaByValue(String tallaValue) {
		secDataProduct.getSecSelTallas().selectTallaByValue(tallaValue);
	}
	@Override
	public boolean selectGuiaDeTallasIfVisible() {
		return secDataProduct.selectGuiaDeTallasIfVisible();
	}
	@Override
	public boolean isVisibleCapaAvisame() {
		return secDataProduct.isVisibleCapaAvisame();
	}
	@Override
	public boolean isVisibleAvisoSeleccionTalla() {
		return secDataProduct.getSecSelTallas().isVisibleAvisoSeleccionTalla();
	}
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return secDataProduct.getSecSelTallas().isVisibleListTallasForSelectUntil(seconds);
	}
	
	@Override
	public ArticuloScreen getArticuloObject() {
		return secDataProduct.getArticuloObject();
	}

	@Override
	public boolean isTallaUnica() {
		return secDataProduct.getSecSelTallas().isTallaUnica();
	}

	@Override
	public Talla getTallaSelected() {
		var pais = PaisShop.from(dataTest.getCodigoPais());
		return secDataProduct.getSecSelTallas().getTallaSelected(app, pais);
	}
	
	@Override
	public String getTallaSelectedAlf() {
		return secDataProduct.getSecSelTallas().getTallaSelectedAlf(app);
	}

	@Override
	public void selectTallaByValue(Talla talla) {
		secDataProduct.getSecSelTallas().selectTallaByValue(talla);
	}

	@Override
	public void selectTallaByLabel(String tallaLabel) {
		secDataProduct.getSecSelTallas().selectTallaByLabel(tallaLabel);
	}

	@Override
	public void selectTallaByIndex(int posicion) {
		secDataProduct.getSecSelTallas().selectTallaByIndex(posicion);
	}

	@Override
	public void selectFirstTallaAvailable() {
		secDataProduct.getSecSelTallas().selectFirstTallaAvailable();
	}

	@Override
	public String getTallaAlf(int posicion) {
		return secDataProduct.getSecSelTallas().getTallaAlf(posicion);
	}

	@Override
	public String getTallaCodNum(int posicion) {
		return secDataProduct.getSecSelTallas().getTallaCodNum(posicion);
	}

	@Override
	public int getNumOptionsTallasNoDisponibles() {
		return secDataProduct.getSecSelTallas().getNumOptionsTallasNoDisponibles();
	}

	@Override
	public int getNumOptionsTallas() {
		return secDataProduct.getSecSelTallas().getNumOptionsTallas();
	}
	
	@Override
	public void closeTallas() {
		secDataProduct.getSecSelTallas().closeTallas();
	}	

	@Override
	public int getNumColors() {
		return secDataProduct.getNumColors();
	}
	
	@Override
	public boolean isVisibleSlider(Slider slider) {
		return secSliders.isVisible(slider);
	}
	
	@Override
	public int getNumArtVisiblesSlider(Slider slider) {
		return secSliders.getNumVisibleArticles(slider);
	}
	
	@Override
	public void selectEnvioGratisTienda() {
		clickLink(ENVIO_GRATIS_TIENDA);
	}
	
	@Override
	public void selectDetalleDelProducto() {
		clickLink(DETALLE_PRODUCTO);
	}
	
	@Override
	public void selectLinkCompartir() {
		clickLink(COMPARTIR);
	}
	
	@Override
	public boolean isVisibleDescription() {
		return secDetalleProductNew.isVisibleUntil(3);
	}
	
	@Override
	public boolean isVisibleBreadcrumbs(int seconds) {
		return secDetalleProductNew.isVisibleBreadcrumbs(0);
	}
	
	@Override
	public boolean isVisibleItemBreadCrumb(ItemBreadcrumb item) {
		return secDetalleProductNew.isVisibleItemBreadCrumb(item);
	}
	
	@Override
	public boolean isVisibleBlockKcSafety() {
		return secDetalleProductNew.isVisibleBlockKcSafety();
	}
	
	@Override
	public boolean isVisibleModalDatosEnvio(int seconds) {
		return modEnvioYdev.isVisible(seconds);
	}
	
	@Override
	public void closeModalDatosEnvio() {
		modEnvioYdev.clickAspaForClose();
	}
	
	@Override
	public boolean isVisibleGuiaTallas(int seconds) {
		return secFitFinder.isVisibleUntil(seconds);
	}
	@Override
	public boolean isInvisibleGuiaTallas(int seconds) {
		return secFitFinder.isInvisibileUntil(seconds);
	}
	@Override
	public void closeGuiaTallas() {
		secFitFinder.close();
	}
	
	public void clickLink(LinksAfterBolsa linkType) {
		moveToElement(getXPathLink(linkType));
		click(getXPathLink(linkType)).exec();
	}
	
	public boolean checkLinkInState(LinksAfterBolsa linkType, State state) {
		String xpathLink = getXPathLink(linkType);
		return state(state, xpathLink).check();
	}

}
