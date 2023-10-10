package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Talla;

public abstract class SecTallasArticulo extends PageBase {

	public abstract boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds);
	public abstract Talla selectTallaAvailableArticle(int posArticulo) throws Exception;
	public abstract boolean isVisibleTallaNotAvailable();
	public abstract void selectTallaArticleNotAvalaible();
	public abstract void bringSizesBack(WebElement articulo);
	
	protected final String xpathArticulo;
	
	public static SecTallasArticulo make(AppEcom app, Pais pais, String xpathArticulo) {
		if (pais.isGaleriaKondo(app)) {
			return new SecTallasArticuloKondo(xpathArticulo);
		}
		return new SecTallasArticuloNormal(xpathArticulo);
	}
	
	public SecTallasArticulo(String xpathArticulo) {
		this.xpathArticulo = xpathArticulo;
	}
	
	protected String getXPathArticulo(int position) {
		return "(" + xpathArticulo + ")[" + position + "]";
	}
	
	public boolean selectTallaNotAvailableIfVisible() {
		if (isVisibleTallaNotAvailable()) {
			selectTallaArticleNotAvalaible();
			return true;
		}
		return false;
	}

}
