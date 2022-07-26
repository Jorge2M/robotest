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
/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PageFichaArt_DesktopShop extends PageFicha {

	public static SecBolsaButtonAndLinksNew secBolsaButtonAndLinks; //Button bolsa and UnderLinks "Detalle Producto"...
	public static ModEnvioYdevolNew modEnvioYdevolNew; //Modal que aparece al seleccionar el link "Envío y devoluciones"
	public static SecFotosNew secFotos; //Foto central y líneas inferiores
	public static SecDetalleProductNew secProductInfo; //Apartado con la descripción y la composición/lavado
	public static SecSlidersNew secSliders; //Completa Tu Look, Elegido para ti, Lo último que has visto
	public static ModNoStock modNoStock; //Modal que aparece cuando no hay stock
	public static SecModalPersonalizacion secModalPersonalizacion; //Modal para la personalización de bordados
	
	private static final String XPATH_HTML_FICHA = "//html[@class[contains(.,'ficha')]]";
	
	private PageFichaArt_DesktopShop(Channel channel, AppEcom app, WebDriver driver) {
		super(TypeFicha.NEW, channel, app, driver);
	}
	
	//Static constructor
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
		secBolsaButtonAndLinks.clickAnadirBolsaButtonAndWait(this.driver);
	}
	
	@Override
	public void selectAnadirAFavoritosButton() {
		secBolsaButtonAndLinks.selectFavoritosButton(ActionFavButton.ADD, driver);
	}
	
	@Override
	public void selectRemoveFromFavoritosButton() {
		secBolsaButtonAndLinks.selectFavoritosButton(ActionFavButton.REMOVE, driver);
	}	
	
	@Override
	public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait) {	
		return (secBolsaButtonAndLinks.isVisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait, driver));
	}
	
	@Override
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait) {	
		return (secBolsaButtonAndLinks.isInvisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait, driver));		
	}
	
	@Override
	public boolean isVisibleButtonElimFavoritos() {
		return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(ActionFavButton.REMOVE, this.driver));
	}
	
	@Override
	public boolean isVisibleButtonAnadirFavoritos() {
		return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(ActionFavButton.ADD, this.driver));
	}	
	
	@Override
	public String getNameLinkBuscarEnTienda() {
		return "Link Disponibilidad en tienda";
	}
	
	@Override
	public boolean isVisibleBuscarEnTiendaLink() {
		return secBolsaButtonAndLinks.checkLinkInState(LinksAfterBolsa.DISPONIBILIDAD_TIENDA, State.Visible, driver);
	}
	
	@Override
	public void selectBuscarEnTiendaLink() {
		secBolsaButtonAndLinks.clickLinkAndWaitLoad(LinksAfterBolsa.DISPONIBILIDAD_TIENDA, this.driver);
	}
	
	@Override
	public boolean isVisibleSlider(Slider typeSlider) {
		return (secSliders.isVisible(typeSlider, this.driver));
	}
	
	@Override
	public int getNumArtVisiblesSlider(Slider typeSlider) {
		return (secSliders.getNumVisibleArticles(typeSlider, driver));
	}
	
	@Override
	public boolean isModalNoStockVisible(int maxSecondsToWait) {
		return modNoStock.isModalNoStockVisibleFichaNew(maxSecondsToWait, driver);
	}
}
