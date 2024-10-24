package com.mng.robotest.tests.domains.favoritos.pageobjects;

import com.mng.robotest.tests.conf.AppEcom;
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
	
	public static PageFavoritos make(Pais pais, AppEcom app) {
		return new PageFavoritosGenesis();
	}
	
	public static boolean isGenesis(Pais pais, AppEcom app) {
		return !PaisShop.CHIPRE_DEL_NORTE.isEquals(pais);
	}
	
}
