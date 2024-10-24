package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.ficha.pageobjects.commons.ActionFavButton.*;
import static com.mng.robotest.tests.domains.ficha.pageobjects.commons.LinksAfterBolsa.*;

import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ModEnvioYdevolNew;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ModNoStock;

public class PageFichaDesktopNoGenesis extends PageFichaNoGenesis {

	public final SecBolsaButtonAndLinksNoGenesis secBolsaButtonAndLinks;
	public final ModEnvioYdevolNew modEnvioYdevolNew;
	public final SecDetalleProduct secProductInfo;
	private ModNoStock modNoStock;

	private static final String XP_HTML_FICHA = "//html[@class[contains(.,'ficha')]]";

	public PageFichaDesktopNoGenesis() {
		this.secBolsaButtonAndLinks = new SecBolsaButtonAndLinksNoGenesis();
		this.modEnvioYdevolNew = new ModEnvioYdevolNew();
		this.secProductInfo = new SecDetalleProduct();
		this.modNoStock = new ModNoStock();
	}

	private String getXPathIsPage(String referencia) {
		return XP_HTML_FICHA + secDataProduct.getXPathLinReferencia(referencia);
	}

	@Override
	public boolean isPage(int seconds) {
		return 
			state(PRESENT, XP_HTML_FICHA).wait(seconds).check() &&
			secDataProduct.getSecSelTallas().isVisibleSelectorTallasUntil(seconds);
	}

	@Override
	public boolean isFichaArticuloUntil(String refArticulo, int seconds) {
		String refSinColor = refArticulo.substring(0,8);
		String xpathFichaRef = getXPathIsPage(refSinColor);
		return state(VISIBLE, xpathFichaRef).wait(seconds).check();
	}

	@Override
	public boolean isVisibleBolsaButton(int seconds) {
		return secBolsaButtonAndLinks.isVisibleBolsaButton(seconds);
	}
	
	@Override
	public void clickAnadirBolsaButtonAndWait() {
		secBolsaButtonAndLinks.clickAnadirBolsaButtonAndWait();
	}

	@Override
	public void selectAnadirAFavoritosButton() {
		secBolsaButtonAndLinks.selectFavoritosButton(ADD);
	}

	@Override
	public void selectRemoveFromFavoritosButton() {
		secBolsaButtonAndLinks.selectFavoritosButton(REMOVE);
	}

	@Override
	public boolean isVisibleButtonElimFavoritos(int seconds) {
		return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(REMOVE, seconds));
	}

	@Override
	public boolean isVisibleButtonAnadirFavoritos(int seconds) {
		return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(ADD, seconds));
	}

	@Override
	public boolean isVisibleBuscarEnTiendaLink() {
		return checkLinkInState(DISPONIBILIDAD_TIENDA, VISIBLE);
	}

	@Override
	public void selectBuscarEnTiendaLink() {
		clickLink(DISPONIBILIDAD_TIENDA);
	}

	@Override
	public boolean isModalNoStockVisible(int seconds) {
		return modNoStock.isModalNoStockVisibleFichaNew(seconds);
	}
	
	@Override
	public boolean isVisibleStickyContent(int seconds) {
		return false;
	}
	@Override
	public boolean isInvisibleStickyContent(int seconds) {
		return true;
	}	
	@Override
	public boolean isVisibleReferenciaStickyContent(String referencia) {
		return false;
	}
	@Override
	public boolean isVisibleTallaLabelStickyContent(String tallaLabel) {
		return false;
	}
	@Override
	public boolean isVisibleColorNameStickyContent(String colorCode) {
		return false;
	}
	
	private static final String XP_ADD_BORDADO = "//button[@class='customization-btn']";
	
	@Override
	public boolean isLinkAddBordado(int seconds) {
		return state(VISIBLE, XP_ADD_BORDADO).wait(seconds).check();
	}
	@Override
	public void clickAddBordado() {
		click(XP_ADD_BORDADO).exec();
	}

	@Override
	public String getSrcImagenCentralDevice() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clickImagenCentralDevice() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isVisibleFichaConZoomDevice(int seconds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean srcImagenCentralConZoomContainsDevice(String pngImgCentralOriginal) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeZoomImageCentralDevice() {
		throw new UnsupportedOperationException();	
	}	
	
}
