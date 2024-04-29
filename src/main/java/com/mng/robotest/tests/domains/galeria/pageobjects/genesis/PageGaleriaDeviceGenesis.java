package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.device.SecMultiFiltrosDeviceNormal;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.menus.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.data.Color;

public class PageGaleriaDeviceGenesis extends PageGaleriaGenesis {

	private static final String XP_COLOR_ARTICLE_BUTTON = "//*[@data-testid='productCard.colorSheet']/../button";
	
	public void showColors(WebElement articulo) {
		By byColorButton = By.xpath("." + XP_COLOR_ARTICLE_BUTTON);
		click(articulo).by(byColorButton).exec();
	}
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return new SecMultiFiltrosDeviceNormal().isVisibleColorTags(colors);
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
	public boolean isVisibleSelectorPreciosDesktop() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int getMinImportFilterDesktop() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int getMaxImportFilterDesktop() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void clickIntervalImportFilterDesktop(int margenPixelsLeft, int margenPixelsRight) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void showFiltersDesktop() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void acceptFiltersDesktop() {
		throw new UnsupportedOperationException();
	}
	@Override
	public List<String> searchForArticlesNoValid(List<String> articleNames) {
		throw new UnsupportedOperationException();
	}
	
	
}
