package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import java.util.List;

import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.NumColumnas;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop.SecFiltrosDesktopNoGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.menus.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.data.Color;


public class PageGaleriaDesktopGenesis extends PageGaleriaGenesis {

	private static final String XP_HEADER_ARTICLES = "//h1[@data-testid[contains(.,'plp.products.list')]]";
	private static final String XP_2_COLUMN_LINK = "//*[@data-testid='column-selector-2']";
	private static final String XP_4_COLUMN_LINK = "//*[@data-testid='column-selector-4']";
	
	@Override
	public List<String> searchForArticlesNoValid(List<String> articleNames) {
		forcePagination();
		backTo1erArticulo();
		return getArticlesNoValid(articleNames);
	}
	
	@Override
	public boolean isHeaderArticlesVisible(String textHeader) {
		if (state(VISIBLE, XP_HEADER_ARTICLES).check()) {
			return getElement(XP_HEADER_ARTICLES).getText().toLowerCase().contains(textHeader.toLowerCase());
		}
		return false;
	}	
	
	private String getXPathLinkNumColumnas(NumColumnas numColumnas) {
		if (numColumnas==NumColumnas.DOS) {
			return XP_2_COLUMN_LINK;
		}
		return XP_4_COLUMN_LINK;
	}
	
	@Override
	public void clickLinkColumnas(NumColumnas numColumnas) {
		if (numColumnas!=NumColumnas.DOS && numColumnas!=NumColumnas.CUATRO) {
			throw new UnsupportedOperationException();
		}
		click(getXPathLinkNumColumnas(numColumnas)).exec();
	}
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecFiltrosDesktopNoGenesis().isVisibleColorTags(colors);
	}
	
	@Override
	public boolean isVisibleSubMenuDesktop(String submenu) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void clickSubMenuDesktop(String submenu) {
		throw new UnsupportedOperationException();
	}
	
	@Override
    public boolean isVisibleBannerHead() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public boolean isBannerHeadLinkable() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public void clickBannerHeadIfClickable() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public boolean isBannerHeadWithoutTextAccesible() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public String getTextBannerHead() {
    	throw new UnsupportedOperationException();
    }
	@Override
    public boolean isBannerHeadSalesBanner(IdiomaPais idioma) {
    	throw new UnsupportedOperationException();
    }
	@Override
    public boolean isVisibleLinkInfoRebajasBannerHead() {
		throw new UnsupportedOperationException();
    }
	@Override
    public boolean isVisibleLinkInfoRebajasBannerHead(TypeLinkInfo typeLinkInfo) {
		throw new UnsupportedOperationException();
    }
	
	private void forcePagination() {
		moveToElement("(" + getXPathArticulo() + ")[5]");
	}
	
}
