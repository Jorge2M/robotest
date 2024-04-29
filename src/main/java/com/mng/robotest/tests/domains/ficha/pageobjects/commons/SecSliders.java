package com.mng.robotest.tests.domains.ficha.pageobjects.commons;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.conf.AppEcom.*;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pais;

public class SecSliders extends PageBase {

	public enum Slider {
		
		DEL_MISMO_ESTILO(
				Arrays.asList(shop, outlet),
				"//*[@data-testid='pdp.crosselling.similars']",
				"//*[@data-testid='pdp.crossSelling.similars']",
				"//li[@id[contains(.,'recommendations.getSimilars')]]",
				"//*[@data-testid='crossSelling']"),
		COMBINA_PERFECTO(
				Arrays.asList(shop, outlet),
				"//*[@data-testid='pdp.crosselling.lookTotal']", 
				"//*[@data-testid='pdp.crossSelling.totalLook']",
				"//li[@id[contains(.,'recommendations.getTotalLook')]]",
				"//*[@data-testid='crossSelling']"),
		POPULAR_AHORA_MISMO(
				Arrays.asList(shop),
				"//*[@data-testid='recommendationsProductDetail']",
				"??????",
				"//li[@id[contains(.,'RecommendationsCarousel')]]",
				"//*[@data-testid='crossSelling']"),
		TAMBIEN_HAS_VISTO(
				Arrays.asList(outlet),
				"//*[@data-testid='pdp.crosselling.lastViewed']",
				"//*[@data-testid='pdp.crossSelling.lastViewed']",
				"//li[@id[contains(.,'recommendations.getLastViewed')]]",
				"//div[@data-testid='crossSelling']");
		
		private List<AppEcom> apps;
		private String xpathNoGenesis;
		private String xpathGenesis;
		private String xpathItemNoGenesis;
		private String xpathItemGenesis;
		private Slider(
				List<AppEcom> apps, String xpathNoGenesis, String xpathGenesis, 
				String xpathItemNoGenesis, String xpathItemGenesis) {
			this.apps = apps;
			this.xpathNoGenesis = xpathNoGenesis;
			this.xpathGenesis = xpathGenesis;
			this.xpathItemNoGenesis = xpathItemNoGenesis;
			this.xpathItemGenesis = xpathItemGenesis;
		}
		public boolean active(AppEcom app) {
			return apps.contains(app);
		}
		public String getXPath(Pais pais, AppEcom app) {
			if (pais.isFichaGenesis(app)) {
				return getXPathGenesis();
			}
			return getXPathNoGenesis();
		}
		private String getXPathNoGenesis() {
			return this.xpathNoGenesis;
		}
		private String getXPathGenesis() {
			return this.xpathGenesis;
		}
		public String getXPathItem(Pais pais, AppEcom app) {
			if (pais.isFichaGenesis(app)) {
				return getXPathGenesis() + getXPathItemGenesis();
			}
			return getXPathNoGenesis() + getXPathItemNoGenesis();
		}
		private String getXPathItemNoGenesis() {
			return xpathItemNoGenesis;
		}
		private String getXPathItemGenesis() {
			return xpathItemGenesis;
		}
	}	
	
	public boolean isVisible(Slider slider) {
		return state(PRESENT, slider.getXPath(dataTest.getPais(), app)).check();
	}
	
	public int getNumVisibleArticles(Slider slider) {
		moveToElement(slider.getXPath(dataTest.getPais(), app));
		return getNumElementsVisible(slider.getXPathItem(dataTest.getPais(), app));
	}	
	
}
