package com.mng.robotest.tests.domains.favoritos.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu;

public class PageFavoritosGenesis extends PageBase implements PageFavoritos {

//	private static final String XP_BLOCK_FAVORITOS = "//div[@class[contains(.,'Favorites_wrapper')]]";
	private static final String XP_BLOCK_FAVORITOS = "//*[@data-testid[contains(.,'favorites.content')]]";
	private static final String XP_ARTICULO = "//li[@data-testid='plp.slot.client']"; //
	private static final String XP_BLOCK_EMPTY = "//*[@data-testid='favorites.content.empty']"; 
	private static final String XP_HEARTH_ARTICULO = "//*[@data-testid='plp.product.favorite.heart.active']";
	
	private String getXPathAncorArticle(String refProducto, String codigoColor) {
		return XP_ARTICULO + "//a[@href[contains(.,'" + refProducto + "?c=" + codigoColor + "')]]";
	}
	
	private String getXPathArticle(String refProducto, String codigoColor) {
		String xpathAncor = getXPathAncorArticle(refProducto, codigoColor);
		return xpathAncor + "//ancestor::li";
	}
	
	private String getXPathHearthForClear(String refProducto, String codigoColor) {
		String xpathArticle = getXPathArticle(refProducto, codigoColor);
		return xpathArticle + XP_HEARTH_ARTICULO;
	}
	
	@Override
	public boolean isSectionArticlesVisible(int seconds) {
		return state(VISIBLE, XP_ARTICULO).wait(seconds).check();
	}
	
	@Override
	public boolean isInvisibleArticle(String refProducto, String codigoColor, int seconds) {
		String xpathArticulo = getXPathAncorArticle(refProducto, codigoColor);
		return state(INVISIBLE, xpathArticulo).wait(seconds).check();
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
	public boolean isArticulos() {
		return state(VISIBLE, XP_ARTICULO).check();
	}
	
	@Override
	public void clearArticulo(String refProducto, String codigoColor) {
		String xpathHearth = getXPathHearthForClear(refProducto, codigoColor);
		click(xpathHearth).exec();
	}
	
	@Override
	public void clearAllArticulos() {
		if (!isSectionVisible()) {
			clickUserMenu(UserMenu.FAVORITOS);
		}
		int i=0; //Avoid infinite loops
		while (isArticulos() && i<10) {
			clear1rstArticulo();
			i+=1;
		}
	}

	@Override
	public void clickProducto(String refProducto, String codigoColor) {
		String xpathArticulo = getXPathAncorArticle(refProducto, codigoColor);
		click(xpathArticulo).exec();
	}
	@Override
	public boolean isListEmpty() {
		return state(VISIBLE, XP_BLOCK_EMPTY).check();
	}
	
	private void clear1rstArticulo() {
		if (isArticulos()) {
			var hearthArticulo = getElement(XP_HEARTH_ARTICULO);
			moveToElement(hearthArticulo);
			click(hearthArticulo).exec();
			state(INVISIBLE, hearthArticulo).wait(8).check();
		}
	}	
	
	private boolean isSectionVisible() {
		return state(VISIBLE, XP_BLOCK_FAVORITOS).check();
	}
	
	private boolean isVisibleArticle(String refArticulo, String codigoColor, int seconds) {
		String xpathArt = getXPathArticle(refArticulo, codigoColor);
		return state(VISIBLE, xpathArt).wait(seconds).check();
	}

	@Override
	public void openShareModal() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean checkShareModal(int seconds) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isShareWhatsappFavoritesVisible() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isShareTelegramFavoritesVisible() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isShareUrlFavoritesVisible() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void closeSharedModal() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean checkShareModalInvisible(int seconds) {
		throw new UnsupportedOperationException();
	}

}
