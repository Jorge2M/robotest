package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import java.util.List;

import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop.SecFiltrosDesktopNoGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.menus.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.data.Color;


public class PageGaleriaDesktopGenesis extends PageGaleriaGenesis {

	private static final String XP_HEADER_ARTICLES = "//h1[@data-testid[contains(.,'plp.products.list')]]";
	
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
	
	@Override
	public boolean isVisibleSelectorPrecios() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int getMinImportFilter() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int getMaxImportFilter() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void showFilters() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void acceptFilters() {
		throw new UnsupportedOperationException();
	}	
	
	private void forcePagination() {
		moveToElement("(" + getXPathArticulo() + ")[5]");
	}
	
}
