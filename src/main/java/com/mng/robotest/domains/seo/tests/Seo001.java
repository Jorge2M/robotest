package com.mng.robotest.domains.seo.tests;

import java.net.URI;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.seo.steps.SeoSteps;
import com.mng.robotest.domains.transversal.TestBase;

public class Seo001 extends TestBase {

	private final SeoSteps browserSteps = new SeoSteps();
	
	public void execute() throws Exception {
		if (!isPRO()) {
			return;
		}
		
		String urlBase = inputParamsSuite.getUrlBase();
		if (app==AppEcom.shop) {
			browserSteps.inputRobotsURLandValidate(urlBase);
		}

		URI uriBase = new URI(urlBase);
		String urlSitemap = urlBase.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
		browserSteps.inputSitemapURLandValidate(urlSitemap);
	}

}
