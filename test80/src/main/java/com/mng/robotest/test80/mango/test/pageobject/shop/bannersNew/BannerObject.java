package com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public abstract class BannerObject {

	public BannerType bannerType;
	public String XPathBanner;
	
	public BannerObject(BannerType bannerType, String XPathBanner) {
		this.bannerType = bannerType;
		this.XPathBanner = XPathBanner;
	}
	
	abstract protected String getUrlBanner(WebElement bannerScreen);
	abstract protected String getSrcImageBanner(WebElement bannerScreen);
	
    public List<DataBanner> getListBannersData(int maxBannersToLoad, WebDriver driver) {
    	List<DataBanner> listDataBannersReturn = new ArrayList<>();
    	List<WebElement> listBannersScreen = getDisplayedBanners(driver);
        Iterator<WebElement> itBannerScreen = listBannersScreen.iterator();
        int i=0;
        while (itBannerScreen.hasNext() && i<maxBannersToLoad) {
        	WebElement bannerScreen = itBannerScreen.next();
	    	DataBanner dataBanner = new DataBanner();
	    	dataBanner.setBannerType(getBannerType());
	    	dataBanner.setBannerWeb(bannerScreen);
	    	dataBanner.setLocation(bannerScreen.getLocation());
	    	dataBanner.setSize(bannerScreen.getSize());
	        dataBanner.setUrlBanner(getUrlBanner(bannerScreen));
	        dataBanner.setSrcImage(getSrcImageBanner(bannerScreen));
	        dataBanner.setDirectText(getTextBanner(bannerScreen));
	        dataBanner.setFloatingText(getFloatingTextBanner(bannerScreen));
	        listDataBannersReturn.add(dataBanner);
	        i+=1;
    	}
    	
    	return listDataBannersReturn;
    }
    
    public List<DataBanner> getListBannersDataUntil(int maxBannersToLoad, int maxSecondsWait, WebDriver driver) {
    	List<DataBanner> listBanners = new ArrayList<>();
    	for (int i=0; i<maxSecondsWait; i++) {
    		listBanners = getListBannersData(maxBannersToLoad, driver);
    		if (listBanners.size()>0) {
    			break;
    		}
    		myWait(1000);
    	}
    	
    	return listBanners;
    }
    
    private void myWait(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		}
		catch (Exception e) {
			//
		}
    }
    
    public String getTextBanner(WebElement bannerScreen) {
    	String texto = bannerScreen.getAttribute("textContent"); 	
    	return (getTextBannerNormalized(texto));
    }
    
    public String getFloatingTextBanner(WebElement bannerScreen) {
    	String texto = bannerScreen.findElement(By.xpath("./..")).getText();
    	return (getTextBannerNormalized(texto));
    }
    
    private String getTextBannerNormalized(String texto) {
    	texto = texto.replaceAll("\t", "").trim();
    	while (texto.contains("\n\n") || texto.contains("\n ")) {
    		texto = texto.replaceAll("\n\n", "\n");
    		texto = texto.replaceAll("\n ", "\n");
    	}
    	
    	return texto;
    }
	
    protected List<WebElement> getDisplayedBanners(WebDriver driver) {
        List<WebElement> listBanners = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathBanner));
        return listBanners;
    }
    
    protected String getUrlDestinoSearchingForAnchor(WebElement banner) {
        if (WebdrvWrapp.isElementPresent(banner, By.xpath(".//a")))
            return (banner.findElement(By.xpath(".//a")).getAttribute("href"));
        
        return "";
    }
    
    public BannerType getBannerType() {
    	return this.bannerType;
    }
    
    public void clickBannerAndWaitLoad(DataBanner dataBanner, WebDriver driver) throws Exception {
    	WebElement bannerWeb = dataBanner.getBannerWeb();
    	WebdrvWrapp.clickAndWaitLoad(driver, bannerWeb, 10, WebdrvWrapp.TypeOfClick.javascript);
    }
}
