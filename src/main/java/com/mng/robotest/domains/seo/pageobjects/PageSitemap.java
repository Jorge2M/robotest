package com.mng.robotest.domains.seo.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import java.io.StringReader;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.openqa.selenium.By;

import com.mng.robotest.domains.seo.beans.Sitemapindex;
import com.mng.robotest.domains.transversal.PageBase;

public class PageSitemap extends PageBase {
	
	public boolean isCorrect() {
		return getSiteMap().isPresent();
	}
	
	public Optional<Sitemapindex> getSiteMap() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Sitemapindex.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			String contentSitemap = getPageSource();
			StringReader reader = new StringReader(contentSitemap);
			return Optional.of((Sitemapindex)jaxbUnmarshaller.unmarshal(reader));
		}
		catch (Exception e) {
			return Optional.empty();
		}
	}
	
	private String getPageSource() {
		String xpathWebkit = "//*[@id='webkit-xml-viewer-source-xml']";
		if (state(Present, xpathWebkit).check()) {
			return getElement(xpathWebkit).getAttribute("innerHTML");
		}
		return driver.getPageSource();
	}

	
}
