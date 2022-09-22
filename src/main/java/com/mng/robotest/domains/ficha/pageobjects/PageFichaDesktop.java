package com.mng.robotest.domains.ficha.pageobjects;


import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.ActionFavButton;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.LinksAfterBolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageFichaDesktop extends PageFicha {

	public final SecBolsaButtonAndLinksNew secBolsaButtonAndLinks;
	public final ModEnvioYdevolNew modEnvioYdevolNew;
	public final SecFotosNew secFotos;
	public final SecDetalleProductNew secProductInfo;
	public final SecSlidersNew secSliders;

	public SecModalPersonalizacion secModalPersonalizacion;
	public ModNoStock modNoStock;

	private static final String XPATH_HTML_FICHA = "//html[@class[contains(.,'ficha')]]";

	public PageFichaDesktop() {
		this.secBolsaButtonAndLinks = new SecBolsaButtonAndLinksNew();
		this.modEnvioYdevolNew = new ModEnvioYdevolNew();
		this.secFotos = new SecFotosNew();
		this.secProductInfo = new SecDetalleProductNew();
		this.secSliders = new SecSlidersNew();
	}

	private String getXPathIsPage(String referencia) {
		return XPATH_HTML_FICHA + secDataProduct.getXPathLinReferencia(referencia);
	}

	@Override
	public boolean isPageUntil(int seconds) {
		return (state(Present, XPATH_HTML_FICHA).wait(seconds).check() &&
				secDataProduct.getSecSelTallas().isVisibleSelectorTallasUntil(seconds));
	}

	@Override
	public boolean isFichaArticuloUntil(String refArticulo, int seconds) {
		String refSinColor = refArticulo.substring(0,8);
		String xpathFichaRef = getXPathIsPage(refSinColor);
		return state(Visible, xpathFichaRef).wait(seconds).check();
	}

	@Override
	public void clickAnadirBolsaButtonAndWait() {
		secBolsaButtonAndLinks.clickAnadirBolsaButtonAndWait();
	}

	@Override
	public void selectAnadirAFavoritosButton() {
		secBolsaButtonAndLinks.selectFavoritosButton(ActionFavButton.ADD);
	}

	@Override
	public void selectRemoveFromFavoritosButton() {
		secBolsaButtonAndLinks.selectFavoritosButton(ActionFavButton.REMOVE);
	}

	@Override
	public boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return (secBolsaButtonAndLinks.isVisibleDivAnadiendoAFavoritosUntil(seconds));
	}

	@Override
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return (secBolsaButtonAndLinks.isInvisibleDivAnadiendoAFavoritosUntil(seconds));
	}

	@Override
	public boolean isVisibleButtonElimFavoritos() {
		return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(ActionFavButton.REMOVE));
	}

	@Override
	public boolean isVisibleButtonAnadirFavoritos() {
		return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(ActionFavButton.ADD));
	}

	@Override
	public String getNameLinkBuscarEnTienda() {
		return "Link Disponibilidad en tienda";
	}

	@Override
	public boolean isVisibleBuscarEnTiendaLink() {
		return secBolsaButtonAndLinks.checkLinkInState(LinksAfterBolsa.DISPONIBILIDAD_TIENDA, State.Visible);
	}

	@Override
	public void selectBuscarEnTiendaLink() {
		secBolsaButtonAndLinks.clickLinkAndWaitLoad(LinksAfterBolsa.DISPONIBILIDAD_TIENDA);
	}

	@Override
	public boolean isVisibleSlider(Slider typeSlider) {
		return (secSliders.isVisible(typeSlider));
	}

	@Override
	public int getNumArtVisiblesSlider(Slider typeSlider) {
		return (secSliders.getNumVisibleArticles(typeSlider));
	}

	@Override
	public boolean isModalNoStockVisible(int seconds) {
		return modNoStock.isModalNoStockVisibleFichaNew(seconds);
	}
}
