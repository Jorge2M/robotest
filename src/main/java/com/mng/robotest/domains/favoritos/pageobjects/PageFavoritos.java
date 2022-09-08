package com.mng.robotest.domains.favoritos.pageobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa.StateBolsa;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFavoritos extends PageBase {
  
	private final ModalFichaFavoritos modalFichaFavoritos = new ModalFichaFavoritos();
	
	private static final String XPATH_BLOCK_FAVORITOS = "//div[@data-pais and @class[contains(.,'favorites')]]";
	private static final String XPATH_BLOCK_FAV_WITH_ART = XPATH_BLOCK_FAVORITOS + "//div[@class[contains(.,'content-garments')]]";
	private static final String XPATH_ARTICULO = "//ul[@id='contentDataFavs']/li";
	private static final String XPATH_BUTTON_EMPTY = "//a[@class='favorites-empty-btn']";
	private static final String XPATH_SHARE_MODAL_BUTTON = "//span[@id='shareIcon']";
	private static final String XPATH_CLOSE_SHARE_MODAL_BUTTON = "//span[@onclick[contains(.,'showCloseModalShare')]]";
	private static final String XPATH_WHATSAPP_SHARE_BUTTON = "//span[@class='modal-share-whatsapp-icon']";
	private static final String XPATH_TELEGRAM_SHARE_BUTTON = "//span[@class='modal-share-telegram-icon']";
	private static final String XPATH_URL_SHARE_LABEL = "//div[@id='linkShareButton']";
	
	public ModalFichaFavoritos getModalFichaFavoritos() {
		return this.modalFichaFavoritos;
	}
	
	private String getXPathArticle(String refProducto, String codigoColor) {
		String xpathNew = "@style[contains(.,'" + refProducto + "_" + codigoColor + "')]"; //div
		return (XPATH_ARTICULO + "//*[" + xpathNew + "]/ancestor::li");
	}
	
	private String getXPathButtonAddBolsa(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'garment-btn')] or @id[contains(.,'garmentBtn')]]");
	}
	
	private String getXPathImgProducto(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor) + "//div[@class[contains(.,'garment-image')]]");
	}	
	
	private String getXPathAspaBorrar(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor)  + "//span[@class[contains(.,'icofav-eliminar')]]");
	}
	
	private String getXPathCapaTallas(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'modalSelectSize')]]");
	}

	private String getXPathTalla(String refProducto, String codigoColor) {
		return (getXPathCapaTallas(refProducto, codigoColor) + "//li[@onclick[contains(.,'changeSize')]]");
	} 
	
	// Funcionalidad de Share Favorites (pre)
	
	private String getXPathWithIdItem(int numArticulo) {
		String xpathArt = "(" + XPATH_ARTICULO + ")[" + numArticulo + "]";
		String idItem = getElement(xpathArt).getAttribute("id");
		return (XPATH_ARTICULO + "[@id='" + idItem + "']");
	}
	
	public void openShareModal() {
		click(XPATH_SHARE_MODAL_BUTTON).exec();
	}

	public void closeShareModal() {
		click(XPATH_CLOSE_SHARE_MODAL_BUTTON).type(javascript).exec();
	}

	public boolean checkShareModalUntill(int maxSeconds) {
		return state(Visible, XPATH_CLOSE_SHARE_MODAL_BUTTON).wait(maxSeconds).check();
	}
	
	public boolean isShareFavoritesVisible() {
		return state(Visible, XPATH_SHARE_MODAL_BUTTON).check();
	}
	
	public boolean isShareWhatsappFavoritesVisible() {
		return state(Visible, XPATH_WHATSAPP_SHARE_BUTTON).check();
	}
	
	public boolean isShareTelegramFavoritesVisible() {
		return state(Visible, XPATH_TELEGRAM_SHARE_BUTTON).check();
	}
	
	public boolean isShareUrlFavoritesVisible() {
		return state(Visible, XPATH_URL_SHARE_LABEL).check();
	}
	
	public void closeSharedModal() {
		click(XPATH_CLOSE_SHARE_MODAL_BUTTON).exec();
		if (!checkShareModalInvisible(1)) {
			click(XPATH_CLOSE_SHARE_MODAL_BUTTON).exec();
		}
	}
	
	public boolean checkShareModalInvisible(int maxSeconds) {
		return state(Invisible, XPATH_CLOSE_SHARE_MODAL_BUTTON).wait(maxSeconds).check();
	}

	public boolean isSectionVisible() {
		return state(Visible, XPATH_BLOCK_FAVORITOS).check();
	}
	
	public boolean isSectionArticlesVisibleUntil(int maxSeconds) {
		return state(Visible, XPATH_BLOCK_FAV_WITH_ART).wait(maxSeconds).check();
	}
	
	public void clearArticuloAndWait(String refArticulo, String codColorArticulo) {
		String xpathBorrar = getXPathAspaBorrar(refArticulo, codColorArticulo);
		
		//Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
		click(xpathBorrar).type(javascript).exec();
	}
	
	public boolean isInvisibleArticleUntil(String referencia, String codColor, int maxSeconds) {
		String xpathArticulo = getXPathArticle(referencia, codColor);
		return state(Invisible, xpathArticulo).wait(maxSeconds).check();
	}
	
	public void clearAllArticulos() {
		if (!isSectionVisible()) {
			SecMenusWrap secMenus = new SecMenusWrap();
			secMenus.getMenusUser().clickMenuAndWait(UserMenu.favoritos);
		}
		int i=0; //Para evitar posibles bucles infinitos
		while (hayArticulos() && i<50) {
			clear1rstArticuloAndWait();
			i+=1;
		}
	}
	
	public boolean hayArticulos() {
		waitMillis(500);
		return state(Present, XPATH_ARTICULO).check();
	}
	
	public boolean areVisibleArticlesUntil(DataFavoritos dataFavoritos, int maxSecondsToWait) {
		if (dataFavoritos.isEmpty()) {
			return (!hayArticulos());
		}
		
		Iterator<ArticuloScreen> itArticulos = dataFavoritos.getListArticulos().iterator();
		while (itArticulos.hasNext()) {
			ArticuloScreen articulo = itArticulos.next();
			if (!isVisibleArticleUntil(articulo.getRefProducto(), articulo.getCodigoColor(), maxSecondsToWait)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isVisibleArticleUntil(String refArticulo, String codigoColor, int maxSeconds) {
		String xpathArt = getXPathArticle(refArticulo, codigoColor);
		return state(Visible, xpathArt).wait(maxSeconds).check();
	}
	
	public void clear1rstArticuloAndWait() {
		if (hayArticulos()) {
			String xpathArtWithIdItem = getXPathWithIdItem(1);
			
			//Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
			String xpath = xpathArtWithIdItem + "//span[@class[contains(.,'icofav-eliminar')]]";
			click(xpath).type(javascript).exec();
			state(State.Invisible, xpathArtWithIdItem).wait(3).build();
		}
	}

	public Talla addArticleToBag(String refProducto, String codigoColor, int posicionTalla) throws Exception {
		clickButtonAddToBagAndWait(refProducto, codigoColor);
		Talla tallaSelected = selectTallaAvailableAndWait(refProducto, codigoColor, posicionTalla);
		return tallaSelected;
	}
	
	public void clickButtonAddToBagAndWait(String refProducto, String codigoColor) throws Exception {
		clickButtonAddToBag(refProducto, codigoColor);
		String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
		state(State.Visible, xpathCapaTallas).wait(1).build();
	}
	
	private void clickButtonAddToBag(String refProducto, String codigoColor) {
		String xpathAdd = getXPathButtonAddBolsa(refProducto, codigoColor);
		try {
			driver.findElement(By.xpath(xpathAdd)).click();
		} catch (ElementClickInterceptedException e) {
			//En ocasiones en el canal móvil se solapa el div del Asistente Online de ayuda
			//así que esperamos un tiempo prudencial hasta que se pliegue
			waitMillis(2000);
			getElement(xpathAdd).click();
		}
	}
	
	public void clickImgProducto(String refProducto, String codigoColor) {
		String xpathImg = getXPathImgProducto(refProducto, codigoColor);
		getElement(xpathImg).click();
	}
	
	public List<WebElement> getListaTallas(String refProducto, String codigoColor) {
		String xpathTalla = getXPathTalla(refProducto, codigoColor);
		return getElements(xpathTalla);
	}
	
	public String selectTallaAndWait(String refProducto, String codigoColor, int posicionTalla) {
		List<WebElement> listaTallas = getListaTallas(refProducto, codigoColor);
		WebElement talla = listaTallas.get(posicionTalla);
		String litTalla = talla.getText();
		talla.click();
		int maxSecondsToWait = 2;
		
		SecBolsa secBolsa = SecBolsa.make(channel, app);
		secBolsa.isInStateUntil(StateBolsa.OPEN, maxSecondsToWait);
		return litTalla;
	}
	
	public Talla selectTallaAvailableAndWait(String refProducto, String codigoColor, int posicionTalla) {
		//Filtramos y nos quedamos sólo con las tallas disponibles
		List<WebElement> listTallas = getListaTallas(refProducto, codigoColor);
		List<WebElement> listTallasAvailable = new ArrayList<>();
		for (WebElement talla : listTallas) {
			if (!state(Present, talla).by(By.xpath("./span")).check()) {
				listTallasAvailable.add(talla);
			}
		}
	   
		WebElement tallaDisponible = listTallasAvailable.get(posicionTalla - 1); 
		Talla talla = Talla.fromLabel(tallaDisponible.getText());
		tallaDisponible.click();
		String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
		state(State.Invisible, xpathCapaTallas).wait(1).build();
		
		return talla;
	}
	
	public boolean isVisibleButtonEmpty() {
		return state(Visible, XPATH_BUTTON_EMPTY).check();
	}
}