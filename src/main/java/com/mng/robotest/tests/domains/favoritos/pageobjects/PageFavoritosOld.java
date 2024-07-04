package com.mng.robotest.tests.domains.favoritos.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFavoritosOld extends PageBase implements PageFavoritos {
  
	private static final String XP_BLOCK_FAVORITOS = "//div[@data-pais and @class[contains(.,'favorites')]]";
	private static final String XP_BLOCK_FAV_WITH_ART = XP_BLOCK_FAVORITOS + "//div[@class[contains(.,'content-garments')]]";
	private static final String XP_ARTICULO = "//ul[@id='contentDataFavs']/li";
	private static final String XP_BUTTON_EMPTY = "//a[@class='favorites-empty-btn']";
	private static final String XP_SHARE_MODAL_BUTTON = "//span[@id='shareIcon']";
	private static final String XP_CLOSE_SHARE_MODAL_BUTTON = "//span[@onclick[contains(.,'showCloseModalShare')]]";
	private static final String XP_WHATSAPP_SHARE_BUTTON = "//span[@class='modal-share-whatsapp-icon']";
	private static final String XP_TELEGRAM_SHARE_BUTTON = "//span[@class='modal-share-telegram-icon']";
	private static final String XP_URL_SHARE_LABEL = "//div[@id='linkShareButton']";
	
	private String getXPathArticle(String refProducto, String codigoColor) {
		String xpathNew = "@style[contains(.,'" + refProducto + "_" + codigoColor + "')]"; //div
		return (XP_ARTICULO + "//*[" + xpathNew + "]/ancestor::li");
	}
	
	private String getXPathImgProducto(String refProducto, String codigoColor) {
		return getXPathArticle(refProducto, codigoColor) + "//div[@class[contains(.,'garment-image')]]";
	}	
	
	private String getXPathAspaBorrar(String refProducto, String codigoColor) {
		return getXPathArticle(refProducto, codigoColor)  + "//span[@class[contains(.,'icofav-eliminar')]]";
	}
	
	// Funcionalidad de Share Favorites (pre)
	
	private String getXPathWithIdItem(int numArticulo) {
		String xpathArt = "(" + XP_ARTICULO + ")[" + numArticulo + "]";
		String idItem = getElement(xpathArt).getAttribute("id");
		return (XP_ARTICULO + "[@id='" + idItem + "']");
	}
	
	@Override
	public void openShareModal() {
		click(XP_SHARE_MODAL_BUTTON).exec();
	}

	@Override
	public boolean checkShareModal(int seconds) {
		return state(VISIBLE, XP_CLOSE_SHARE_MODAL_BUTTON).wait(seconds).check();
	}
	
	@Override
	public boolean isShareWhatsappFavoritesVisible() {
		return state(VISIBLE, XP_WHATSAPP_SHARE_BUTTON).check();
	}
	
	@Override
	public boolean isShareTelegramFavoritesVisible() {
		return state(VISIBLE, XP_TELEGRAM_SHARE_BUTTON).check();
	}
	
	@Override
	public boolean isShareUrlFavoritesVisible() {
		return state(VISIBLE, XP_URL_SHARE_LABEL).check();
	}
	
	@Override
	public void closeSharedModal() {
		click(XP_CLOSE_SHARE_MODAL_BUTTON).exec();
		if (!checkShareModalInvisible(1)) {
			click(XP_CLOSE_SHARE_MODAL_BUTTON).exec();
		}
	}
	
	@Override
	public boolean checkShareModalInvisible(int seconds) {
		return state(INVISIBLE, XP_CLOSE_SHARE_MODAL_BUTTON).wait(seconds).check();
	}

	@Override
	public boolean isSectionArticlesVisible(int seconds) {
		return state(VISIBLE, XP_BLOCK_FAV_WITH_ART).wait(seconds).check();
	}
	
	@Override
	public void clearArticulo(String refArticulo, String codColorArticulo) {
		String xpathBorrar = getXPathAspaBorrar(refArticulo, codColorArticulo);
		
		//Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
		click(xpathBorrar).type(JAVASCRIPT).exec();
	}
	
	@Override
	public boolean isInvisibleArticle(String referencia, String codColor, int seconds) {
		String xpathArticulo = getXPathArticle(referencia, codColor);
		return state(INVISIBLE, xpathArticulo).wait(seconds).check();
	}
	
	@Override
	public void clearAllArticulos() {
		if (!isSectionVisible() && !isOutlet()) {
			clickUserMenu(UserMenu.FAVORITOS);
		}
		int i=0; //Para evitar posibles bucles infinitos
		while (isArticulos() && i<10) {
			clear1rstArticuloAndWait();
			i+=1;
		}
	}
	
	@Override
	public boolean isArticulos() {
		waitMillis(500);
		return state(PRESENT, XP_ARTICULO).check();
	}
	
	@Override
	public boolean isVisibleArticles(int seconds) {
		if (dataTest.getDataFavoritos().isEmpty()) {
			return (!isArticulos());
		}
		
		var itArticulos = dataTest.getDataFavoritos().getListArticulos().iterator();
		while (itArticulos.hasNext()) {
			var articulo = itArticulos.next();
			if (!isVisibleArticle(articulo.getRefProducto(), articulo.getCodigoColor(), seconds)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void clickProducto(String refProducto, String codigoColor) {
		//Ejecutamos el click mediante JavaScript porque hay un error en la shop que hace 
		//que en ocasiones el artículo quede parcialmente tapado por el footer.
		String xpathImg = getXPathImgProducto(refProducto, codigoColor);
		click(xpathImg).type(JAVASCRIPT).exec();
	}
	
	@Override
	public boolean isListEmpty() {
		return state(VISIBLE, XP_BUTTON_EMPTY).check();
	}	

	private boolean isSectionVisible() {
		return state(VISIBLE, XP_BLOCK_FAVORITOS).check();
	}
	
	private boolean isVisibleArticle(String refArticulo, String codigoColor, int seconds) {
		String xpathArt = getXPathArticle(refArticulo, codigoColor);
		return state(VISIBLE, xpathArt).wait(seconds).check();
	}
	
	private void clear1rstArticuloAndWait() {
		if (isArticulos()) {
			String xpathArtWithIdItem = getXPathWithIdItem(1);
			moveToElement(xpathArtWithIdItem);
			
			//Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
			String xpath = xpathArtWithIdItem + "//span[@class[contains(.,'icofav-eliminar')]]";
			click(xpath).type(JAVASCRIPT).exec();
			state(INVISIBLE, xpathArtWithIdItem).wait(3).build();
		}
	}


	
}