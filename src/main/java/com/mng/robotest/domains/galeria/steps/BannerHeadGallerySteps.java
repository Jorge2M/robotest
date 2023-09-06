package com.mng.robotest.domains.galeria.steps;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.domains.galeria.pageobjects.SecBannerHeadGallery;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps.TypeGalery;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;

public class BannerHeadGallerySteps extends StepBase {

	private final PageGaleriaSteps pageGaleriaParent;
	private final SecBannerHeadGallery secBannerHeadDesktop;

	public BannerHeadGallerySteps(PageGaleriaSteps pageGaleriaParent) {
		this.pageGaleriaParent = pageGaleriaParent;
		this.secBannerHeadDesktop = ((PageGaleriaDesktop)PageGaleria.getNew(channel, dataTest.getPais())).getSecBannerHead();
	}

	public void validateBannerSuperiorIfExistsDesktop() {
		boolean bannerIsVisible = secBannerHeadDesktop.isVisible();
		if (bannerIsVisible &&
			!secBannerHeadDesktop.isBannerWithoutTextAccesible()) {
			checkBannerContainsSomeText();
		}
	}

	@Validation (
			description="El Banner de Cabecera contiene algún texto",
			level=Warn)
	public boolean checkBannerContainsSomeText() {
		String textBanner = secBannerHeadDesktop.getText();
		return ("".compareTo(textBanner)!=0);
	}

	@Validation
	public ChecksTM checkBannerContainsText(List<String> possibleTexts) {
		var checks = ChecksTM.getNew();
		String textBanner = secBannerHeadDesktop.getText();
		checks.add(
				"El banner de cabecera contiene el texto <b>" + possibleTexts.get(0) + "</b>",
				textBannersContainsPossibleText(textBanner, possibleTexts));
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
		var checks = ChecksTM.getNew();
		checks.add(
				"<b style=\"color:blue\">Rebajas</b></br>" +
						"Es visible el banner de cabecera",
				secBannerHeadDesktop.isVisible());

		String saleTraduction = UtilsTest.getSaleTraduction(idioma);
		String textBanner = secBannerHeadDesktop.getText();
		checks.add(
				"El banner de cabecera es de rebajas  (contiene un símbolo de porcentaje o " + saleTraduction + ")",
				UtilsTest.textContainsPercentage(textBanner, idioma) || textBanner.contains(saleTraduction));
		checks.add(
				"El banner de cabecera contiene un link de \"Más info\"",
				secBannerHeadDesktop.isVisibleLinkInfoRebajas(), Warn);

		boolean bannerLincable = secBannerHeadDesktop.isLinkable();
		if (typeGalery==TypeGalery.SALES || !pais.isVentaOnline()) {
			checks.add(
					Check.make(
									"El banner de cabecera no es lincable",
									!bannerLincable, Info)
							.store(StoreType.None).build());
		}
		else {
			checks.add(
					"El banner de cabecera sí es lincable",
					bannerLincable, Warn);
		}

		return checks;
	}

	@Validation
	ChecksTM checkBannerHeadSalesOff(IdiomaPais idioma) {
		var checks = ChecksTM.getNew();
		String saleTraduction = UtilsTest.getSaleTraduction(idioma);
		checks.add(
				"<b style=\"color:blue\">Rebajas</b></br>" +
						"El banner de cabecera NO es de rebajas  (NO contiene un símbolo de porcentaje o \"" + saleTraduction + "\")",
				!secBannerHeadDesktop.isSalesBanner(idioma));

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
