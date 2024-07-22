package com.mng.robotest.tests.domains.favoritos.pageobjects;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.utils.UtilsTest;

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
		if (isGenesis(pais, app)) {
			return new PageFavoritosGenesis();
		}
		return new PageFavoritosOld();
	}
	
	public static boolean isGenesis(Pais pais, AppEcom app) {
		return UtilsTest.paisConCompra(pais, app);
	}
	
}
