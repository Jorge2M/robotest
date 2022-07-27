package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.ActionFavButton;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.LinksAfterBolsa;


@SuppressWarnings({"static-access"})
public class PageFichaArt_DesktopShop extends PageFicha {

	public final SecBolsaButtonAndLinksNew secBolsaButtonAndLinks;
	public final ModEnvioYdevolNew modEnvioYdevolNew;
	public final SecFotosNew secFotos;
	public final SecDetalleProductNew secProductInfo;
	public final SecSlidersNew secSliders;
	
	public SecModalPersonalizacion secModalPersonalizacion;
	public ModNoStock modNoStock;
	
	private static final String XPATH_HTML_FICHA = "//html[@class[contains(.,'ficha')]]";
	
	private PageFichaArt_DesktopShop(Channel channel, AppEcom app, WebDriver driver) {
		super(TypeFicha.NEW, channel, app, driver);
		this.secBolsaButtonAndLinks = new SecBolsaButtonAndLinksNew(driver);
		this.modEnvioYdevolNew = new ModEnvioYdevolNew(driver);
		this.secFotos = new SecFotosNew(driver);
		this.secProductInfo = new SecDetalleProductNew(driver);
		this.secSliders = new SecSlidersNew(driver);
	}
	
	public static PageFichaArt_DesktopShop getNewInstance(Channel channel, AppEcom app, WebDriver driver) {
		return (new PageFichaArt_DesktopShop(channel, app, driver));
	}
	
	private String getXPathIsPage(String referencia) {
		return XPATH_HTML_FICHA + secDataProduct.getXPathLinReferencia(referencia);
	}
	
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_HTML_FICHA)).wait(maxSeconds).check() &&
				secDataProduct.getSecSelTallas().isVisibleSelectorTallasUntil(maxSeconds));
	}
	
	@Override
	public boolean isFichaArticuloUntil(String refArticulo, int maxSeconds) {
		String refSinColor = refArticulo.substring(0,8); 
		String xpathFichaRef = getXPathIsPage(refSinColor);
		return (state(Visible, By.xpath(xpathFichaRef)).wait(maxSeconds).check());
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
	public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait) {	
		return (secBolsaButtonAndLinks.isVisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait));
	}
	
	@Override
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait) {	
		return (secBolsaButtonAndLinks.isInvisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait));		
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
	public boolean isModalNoStockVisible(int maxSecondsToWait) {
		return modNoStock.isModalNoStockVisibleFichaNew(maxSecondsToWait);
	}
}
