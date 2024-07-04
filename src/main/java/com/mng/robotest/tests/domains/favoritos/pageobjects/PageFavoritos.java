package com.mng.robotest.tests.domains.favoritos.pageobjects;

import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;

public interface PageFavoritos {

	void openShareModal();
	boolean checkShareModal(int seconds);
	boolean isShareWhatsappFavoritesVisible();
	boolean isShareTelegramFavoritesVisible();
	boolean isShareUrlFavoritesVisible();
	void closeSharedModal();
	boolean checkShareModalInvisible(int seconds);
	boolean isSectionArticlesVisible(int seconds);
	void clearArticulo(String refArticulo, String codColorArticulo);
	boolean isInvisibleArticle(String referencia, String codColor, int seconds);
	void clearAllArticulos();
	boolean isArticulos();
	boolean isVisibleArticles(int seconds);
	void clickProducto(String refProducto, String codigoColor);
	boolean isListEmpty();
	
	public static PageFavoritos make(Pais pais) {
		if (PaisShop.ANDORRA.isEquals(pais)) {
			return new PageFavoritosGenesis();
		}
		return new PageFavoritosOld();
	}
	
}
