package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import java.util.List;

import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.data.Color;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGaleriaDesktopGenesis extends PageGaleriaGenesis {

	private static final String XP_HEADER_ARTICLES = "//h1[@data-testid[contains(.,'plp.products.list')]]";
	private static final String XP_SUBMENU_HEADER = "//*[@data-testid[contains(.,'plp.products.list.subfamily')]]";
	
	private String getXPathSubmenuHeader(String submenu) {
		return XP_SUBMENU_HEADER + "//a[" + 
			   "text()='" + submenu + "' or " + 
			   "text()='" + capitalizeFirstCharacter(submenu) + "']";
	}

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
	
	@Override
	public boolean isVisibleLabelFiltroColorApplied(List<Color> colorsSelected) {
		return new SecFiltrosGenesis().isVisibleLabelFiltroColorApplied(colorsSelected);
	}
	
	@Override
	public boolean isVisibleSubMenuDesktop(String submenu) {
		return state(VISIBLE, getXPathSubmenuHeader(submenu)).check();
	}
	
	@Override
	public void clickSubMenuDesktop(String submenu) {
		click(getXPathSubmenuHeader(submenu));
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
	
	private void forcePagination() {
		String xp5oArticle = "(" + getXPathArticulo() + ")[5]";
		if (state(PRESENT, xp5oArticle).check()) {
			moveToElement(xp5oArticle);
		}
	}
	
    public static String capitalizeFirstCharacter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
	
}
