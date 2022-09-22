package com.mng.robotest.test.steps.shop.galeria;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.SecBannerHeadGallery;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps.TypeGalery;
import com.mng.robotest.test.utils.UtilsTest;

public class BannerHeadGallerySteps {

	final PageGaleriaSteps pageGaleriaParent;
	final SecBannerHeadGallery secBannerHeadDesktop;
	final WebDriver driver;
	
	private BannerHeadGallerySteps(PageGaleriaSteps pageGaleriaParent, WebDriver driver) {
		this.pageGaleriaParent = pageGaleriaParent;
		this.secBannerHeadDesktop = new PageGaleriaDesktop().getSecBannerHead();
		this.driver = driver;
	}
	
	public static BannerHeadGallerySteps newInstance (PageGaleriaSteps pageGaleriaParent, WebDriver driver) {
		return (new BannerHeadGallerySteps(pageGaleriaParent, driver));
	}
	
	public void validateBannerSuperiorIfExistsDesktop() {
		boolean bannerIsVisible = secBannerHeadDesktop.isVisible();
		if (bannerIsVisible) {
			if (!secBannerHeadDesktop.isBannerWithoutTextAccesible()) {
				checkBannerContainsSomeText();
			}
		}
	}

	@Validation (
		description="El Banner de Cabecera contiene algún texto",
		level=State.Warn)
	public boolean checkBannerContainsSomeText() {
		String textBanner = secBannerHeadDesktop.getText();
		return ("".compareTo(textBanner)!=0);
	}

	@Validation
	public ChecksTM checkBannerContainsText(List<String> possibleTexts) {
		ChecksTM checks = ChecksTM.getNew();
		String textBanner = secBannerHeadDesktop.getText();
		checks.add(
			"El banner de cabecera contiene el texto <b>" + possibleTexts.get(0) + "</b>",
			textBannersContainsPossibleText(textBanner, possibleTexts), State.Defect);
		return checks;
	}

	@Step (
		description="Seleccionar el banner superior de la Galería", 
		expected="Aparece una galería de artículos")
	public void clickBannerSuperiorIfLinkableDesktop() {
		secBannerHeadDesktop.clickBannerIfClickable();
		pageGaleriaParent.validaArtEnContenido(3);
	}

	@Validation
	public ChecksTM checkBannerSalesHead(TypeGalery typeGalery, Pais pais, IdiomaPais idioma) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"Es visible el banner de cabecera",
			secBannerHeadDesktop.isVisible(), State.Defect);
		
		String saleTraduction = UtilsTest.getSaleTraduction(idioma);
		String textBanner = secBannerHeadDesktop.getText();
		checks.add(
			"El banner de cabecera es de rebajas  (contiene un símbolo de porcentaje o " + saleTraduction + ")",
			UtilsTest.textContainsPercentage(textBanner, idioma) || textBanner.contains(saleTraduction), 
			State.Defect);
		checks.add(
			"El banner de cabecera contiene un link de \"Más info\"",
			secBannerHeadDesktop.isVisibleLinkInfoRebajas(), State.Warn);	
		
		boolean bannerLincable = secBannerHeadDesktop.isLinkable();
		if (typeGalery==TypeGalery.SALES || !pais.isVentaOnline()) {
		 	checks.add(
		 	    Check.make(
		 		    "El banner de cabecera no es lincable",
		 		    !bannerLincable, State.Info)
		 	    .store(StoreType.None).build());
		}
		else {
		 	checks.add(
		 		"El banner de cabecera sí es lincable",
		 		bannerLincable, State.Warn);
		}
		
		return checks;
	}

	@Validation
	ChecksTM checkBannerHeadSalesOff(IdiomaPais idioma) {
		ChecksTM checks = ChecksTM.getNew();
		String saleTraduction = UtilsTest.getSaleTraduction(idioma);
		checks.add(
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"El banner de cabecera NO es de rebajas  (NO contiene un símbolo de porcentaje o \"" + saleTraduction + "\")",
			!secBannerHeadDesktop.isSalesBanner(idioma), State.Defect);
		
		return checks;
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
