package com.mng.robotest.test.appshop.seo;

import java.net.URI;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.steps.otras.BrowserSteps;

public class Seo001 extends TestBase {

	public void execute() throws Exception {
		if (!new UtilsMangoTest().isEntornoPRO()) {
			return;
		}
		
		String urlBase = inputParamsSuite.getUrlBase();
		if (app==AppEcom.shop) {
			BrowserSteps.inputRobotsURLandValidate(urlBase, app, driver);
		}

		URI uriBase = new URI(urlBase);
		String urlSitemap = urlBase.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
		BrowserSteps.inputSitemapURLandValidate(urlSitemap, driver);
	}

}
