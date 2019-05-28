package com.mng.robotest.test80.mango.test.stpv.shop.home;

import static com.mng.robotest.test80.arq.utils.otras.Constantes.PrefixRebajas;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.DataBanner;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV.TypeHome;

public class BannerSpringIsHere2019StpV {
	
	final List<String> textsPossible;
	final DataCtxShop dCtxSh;
	final WebDriver driver;
	final SecBannersStpV secBannersStpV;
	final ManagerBannersScreen managerBannersScreen;
	int posBannerSpringIsHere = 0;
	
	public BannerSpringIsHere2019StpV(List<String> textsPossible, DataCtxShop dCtxSh, WebDriver driver) {
		this.textsPossible = textsPossible;
		this.dCtxSh = dCtxSh;
		this.driver = driver;

		//Existe un problema según el cual en ocasiones se recoge como 1a campaña una de Kids que está oculta 
		//pero que está en el mismo 'location' que la de Spring Is Here
		int maxBannersToLoad = 2;
		this.secBannersStpV = new SecBannersStpV(maxBannersToLoad, driver);
        managerBannersScreen = secBannersStpV.getManagerBannerScreen();
        for (int i=0; i<managerBannersScreen.getListDataBanners().size(); i++) {
        	String textBanner = managerBannersScreen.getBanner(i+1).getDirectText();
        	if (textBannersContainsPossibleText(textBanner)) {
        		posBannerSpringIsHere = i+1;
        		break;
        	}
        }
	}
	
	private DataBanner getBanner() {
		if (posBannerSpringIsHere>0) {
			return (managerBannersScreen.getBanner(posBannerSpringIsHere)); 
		}
		
		return null;
	}
	
	final static String csselectorSwipperCircle = "div.swiper-pagination-clickable circle.vsv-circle";
	@Step (
		description="Congelamos la campaña asociada al <b>#{circleAssociatedToCampaign}o</b> círculo",
		expected="El carrusel queda parado en la campaña deseada")
	public static void clickCircleToForceStopInCampaing(int circleAssociatedToCampaign, WebDriver driver) {
		if (circleAssociatedToCampaign<1) {
			throw new IllegalArgumentException("The circle associated to a campaign must be > 0");
		}
		
	    if (WebdrvWrapp.isElementVisible(driver, By.cssSelector(csselectorSwipperCircle))) {
	    	int firstCircleToClick = 1;
	    	if (circleAssociatedToCampaign==1) {
	    		firstCircleToClick=2;
	    	}
	    	driver.findElements(By.cssSelector(csselectorSwipperCircle)).get(firstCircleToClick-1).click();
	    	driver.findElements(By.cssSelector(csselectorSwipperCircle)).get(circleAssociatedToCampaign-1).click();
	    }
	}
	
    @Validation
    public ChecksResultWithFlagBannerExists checkBanner(TypeHome typeHome) {
    	ChecksResultWithFlagBannerExists validations = ChecksResultWithFlagBannerExists.getNew();
    	boolean existenBanners = managerBannersScreen.existBanners();
    	validations.setexistBanner(posBannerSpringIsHere > 0);
    	validations.add(
    		PrefixRebajas + "Existen banners",
    		existenBanners, State.Defect);    	
    	if (existenBanners) {
        	validations.add(
        		"El 1er Banner contiene el texto " + textsPossible.get(0),
        		posBannerSpringIsHere > 0, State.Warn);
    
        	if (posBannerSpringIsHere>0 && typeHome==TypeHome.Multimarca) {
	        	List<Linea> listLineas = dCtxSh.pais.getShoponline().getListLineasTiendas(dCtxSh.appE);
	        	if (listLineas.size()>1) {
	                for (Linea linea : listLineas) {
	                    String urlLink = getBanner().getUrlLinkLinea(linea.getType());
	                    String textToBeContainedInUrl = "seccion=hellosummer_" + linea.getType().getId3();
	                	validations.add(
	                		"El link del 1er banner contiene " + textToBeContainedInUrl,
	                		urlLink.contains(textToBeContainedInUrl), State.Warn);
	                }
	        	}
        	}
    	}
    	
    	return validations;
    }
    
    @Validation (
    	description="No aparece el banner de <b>Spring is Here</b>",
    	level=State.Warn)
    public boolean checkIsNotBannerVisible() {
    	return (posBannerSpringIsHere==0);
    }
    
    public void clickBanner() throws Exception {
    	secBannersStpV.seleccionarBanner(posBannerSpringIsHere, true/*applyValidations*/, dCtxSh.appE, dCtxSh.channel);
    }
    
	private boolean textBannersContainsPossibleText(String textBanner) {
		for (String possibleText : textsPossible) {
			if (textBanner.toLowerCase().contains(possibleText.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
}
