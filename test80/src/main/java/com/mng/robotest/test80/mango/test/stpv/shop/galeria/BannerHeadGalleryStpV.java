package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV.TypeGalery;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public class BannerHeadGalleryStpV {

	final PageGaleriaStpV pageGaleriaParent;
	final WebDriver driver;
	
	private BannerHeadGalleryStpV(PageGaleriaStpV pageGaleriaParent, WebDriver driver) {
		this.pageGaleriaParent = pageGaleriaParent;
		this.driver = driver;
	}
	
	public static BannerHeadGalleryStpV newInstance (PageGaleriaStpV pageGaleriaParent, WebDriver driver) {
		return (new BannerHeadGalleryStpV(pageGaleriaParent, driver));
	}
	
    @SuppressWarnings("static-access")
    public void validateBannerSuperiorIfExistsDesktop() {
	   boolean bannerIsVisible = PageGaleriaDesktop.secBannerHead.isVisible(driver);
	   if (bannerIsVisible) {
		   if (!PageGaleriaDesktop.secBannerHead.isBannerWithoutTextAccesible(driver)) {
			   checkBannerContainsSomeText();
		   }
	   }
    }
  
    @SuppressWarnings("static-access")
	@Validation (
    	description="El Banner de Cabecera contiene algún texto",
    	level=State.Defect)
    public boolean checkBannerContainsSomeText() {
        String textBanner = PageGaleriaDesktop.secBannerHead.getText(driver);
        return ("".compareTo(textBanner)!=0);
	}
    
    @Validation
    @SuppressWarnings("static-access")
    public ChecksResult checkBannerContainsText(List<String> possibleTexts) {
    	ChecksResult validations = ChecksResult.getNew();
    	String textBanner = PageGaleriaDesktop.secBannerHead.getText(driver);
     	validations.add(
     		"El banner de cabecera contiene el texto <b>" + possibleTexts.get(0) + "</b>",
     		textBannersContainsPossibleText(textBanner, possibleTexts), State.Defect);
     	return validations;
    }
    
    @SuppressWarnings("static-access")
    @Step (
        description="Seleccionar el banner superior de la Galería", 
        expected="Aparece una galería de artículos")
    public void clickBannerSuperiorIfLinkableDesktop() throws Exception {
	    PageGaleriaDesktop.secBannerHead.clickBannerIfClickable(driver);     
	    pageGaleriaParent.validaArtEnContenido(3);
    }
   
    public void checkBannerHeadSalesOn(IdiomaPais idioma) {
	    checkBannerSalesHead(TypeGalery.Sales, idioma);
    }
   
    @SuppressWarnings("static-access")
    @Validation
    public ChecksResult checkBannerSalesHead(TypeGalery typeGalery, IdiomaPais idioma) {
    	ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"<b style=\"color:blue\">Rebajas</b></br>" +
    		"Es visible el banner de cabecera",
    		PageGaleriaDesktop.secBannerHead.isVisible(driver), State.Defect);
    	
    	String saleTraduction = UtilsTestMango.getSaleTraduction(idioma);
    	String textBanner = PageGaleriaDesktop.secBannerHead.getText(driver);
    	validations.add(
    		"El banner de cabecera es de rebajas  (contiene un símbolo de porcentaje o " + saleTraduction + ")",
    		UtilsTestMango.textContainsPercentage(textBanner, idioma) || textBanner.contains(saleTraduction), 
    		State.Defect);
    	validations.add(
    		"El banner de cabecera contiene un link de \"Más info\"",
    		PageGaleriaDesktop.secBannerHead.isVisibleLinkInfoRebajas(driver), State.Warn);	
    	
    	boolean bannerLincable = PageGaleriaDesktop.secBannerHead.isLinkable(driver);
    	if (typeGalery==TypeGalery.Sales) {
	     	validations.add(
	     		"El banner de cabecera no es lincable",
	     		!bannerLincable, State.Info);
    	}
    	else {
	     	validations.add(
	     		"El banner de cabecera sí es lincable",
	     		bannerLincable, State.Warn);
    	}
    	
    	return validations;
    }

    @SuppressWarnings("static-access")
    @Validation
    ChecksResult checkBannerHeadSalesOff(IdiomaPais idioma) {
    	ChecksResult validations = ChecksResult.getNew();
    	String saleTraduction = UtilsTestMango.getSaleTraduction(idioma);
    	validations.add(
    		"<b style=\"color:blue\">Rebajas</b></br>" +
    		"El banner de cabecera NO es de rebajas  (NO contiene un símbolo de porcentaje o \"" + saleTraduction + "\")",
    		!PageGaleriaDesktop.secBannerHead.isSalesBanner(idioma, driver), State.Defect);
    	
    	return validations;
    }
   
	private boolean textBannersContainsPossibleText(String textBanner, List<String> textsPossible) {
		for (String possibleText : textsPossible) {
			if (textBanner.toLowerCase().contains(possibleText.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
}