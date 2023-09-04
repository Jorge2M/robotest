package com.mng.robotest.domains.ficha.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.conf.AppEcom.*;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.base.PageBase;

public class SecSliders extends PageBase {

	public enum Slider {
		
		DEL_MISMO_ESTILO(
				Arrays.asList(shop, outlet),
				"//*[@data-testid='pdp.crosselling.similars']", 
				"//li[@id[contains(.,'recommendations.getSimilars')]]"),
		COMBINA_PERFECTO(
				Arrays.asList(shop, outlet),
				"//*[@data-testid='pdp.crosselling.lookTotal']", 
				"//li[@id[contains(.,'recommendations.getTotalLook')]]"),
		POPULAR_AHORA_MISMO(
				Arrays.asList(shop),
				"//*[@data-testid='recommendationsProductDetail']", 
				"//li[@id[contains(.,'RecommendationsCarousel')]]"),
		TAMBIEN_HAS_VISTO(
				Arrays.asList(AppEcom.outlet),
				"//*[@data-testid='pdp.crosselling.lastViewed']", 
				"//li[@id[contains(.,'recommendations.getLastViewed')]]");
		
		private List<AppEcom> apps;
		private String xpath;
		private String xpathItem;
		private Slider(List<AppEcom> apps, String xpath, String xpathItem) {
			this.apps = apps;
			this.xpath = xpath;
			this.xpathItem = xpathItem;
		}
		public boolean active(AppEcom app) {
			return apps.contains(app);
		}
		public String getXPath() {
			return this.xpath;
		}
		public String getXPathItem() {
			return xpathItem;
		}
	}	
	
	public boolean isVisible(Slider slider) {
		return state(Present, slider.getXPath()).check();
	}
	
	public int getNumVisibleArticles(Slider slider) {
		moveToElement(slider.getXPath());
		return getNumElementsVisible(slider.getXPathItem());
	}	
	
}
