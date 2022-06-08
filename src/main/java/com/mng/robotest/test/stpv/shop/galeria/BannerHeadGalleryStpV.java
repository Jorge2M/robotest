package com.mng.robotest.test.stpv.shop.galeria;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.stpv.shop.galeria.PageGaleriaStpV.TypeGalery;
import com.mng.robotest.test.utils.UtilsTestMango;

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
		level=State.Warn)
	public boolean checkBannerContainsSomeText() {
		String textBanner = PageGaleriaDesktop.secBannerHead.getText(driver);
		return ("".compareTo(textBanner)!=0);
	}

	@Validation
	@SuppressWarnings("static-access")
	public ChecksTM checkBannerContainsText(List<String> possibleTexts) {
		ChecksTM validations = ChecksTM.getNew();
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
	public void clickBannerSuperiorIfLinkableDesktop() {
		PageGaleriaDesktop.secBannerHead.clickBannerIfClickable(driver);
		pageGaleriaParent.validaArtEnContenido(3);
	}

	public void checkBannerHeadSalesOn(Pais pais, IdiomaPais idioma) {
		checkBannerSalesHead(TypeGalery.Sales, pais, idioma);
	}

	@SuppressWarnings("static-access")
	@Validation
	public ChecksTM checkBannerSalesHead(TypeGalery typeGalery, Pais pais, IdiomaPais idioma) {
		ChecksTM validations = ChecksTM.getNew();
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
		if (typeGalery==TypeGalery.Sales || !pais.isVentaOnline()) {
		 	validations.add(
		 		"El banner de cabecera no es lincable",
		 		!bannerLincable, State.Info, StoreType.None);
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
	ChecksTM checkBannerHeadSalesOff(IdiomaPais idioma) {
		ChecksTM validations = ChecksTM.getNew();
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
