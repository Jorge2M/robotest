package com.mng.robotest.tests.domains.galeria.steps;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;

public class BannerHeadGallerySteps extends StepBase {

	private final GaleriaSteps galeriaParentSteps;
	private final PageGaleria pgGaleria = PageGaleria.make(Channel.desktop);

	public BannerHeadGallerySteps(GaleriaSteps galeriaParentSteps) {
		this.galeriaParentSteps = galeriaParentSteps;
	}

	public void checkBannerSuperiorIfExistsDesktop() {
		boolean bannerIsVisible = pgGaleria.isVisibleBannerHead();
		if (bannerIsVisible &&
			!pgGaleria.isBannerHeadWithoutTextAccesible()) {
			checkBannerContainsSomeText();
		}
	}

	@Validation (
		description="El Banner de Cabecera contiene algún texto",
		level=WARN)
	public boolean checkBannerContainsSomeText() {
		String textBanner = pgGaleria.getTextBannerHead();
		return ("".compareTo(textBanner)!=0);
	}

	@Validation
	public ChecksTM checkBannerContainsText(List<String> possibleTexts) {
		var checks = ChecksTM.getNew();
		String textBanner = pgGaleria.getTextBannerHead();
		checks.add(
			"El banner de cabecera contiene el texto <b>" + possibleTexts.get(0) + "</b>",
			textBannersContainsPossibleText(textBanner, possibleTexts));
		
		return checks;
	}

	@Step (
			description="Seleccionar el banner superior de la Galería",
			expected="Aparece una galería de artículos")
	public void clickBannerSuperiorIfLinkableDesktop() {
		pgGaleria.clickBannerHeadIfClickable();
		galeriaParentSteps.validaArtEnContenido(3);
	}

	@Validation
	ChecksTM checkBannerHeadSalesOff(IdiomaPais idioma) {
		var checks = ChecksTM.getNew();
		String saleTraduction = UtilsTest.getSaleTraduction(idioma);
		checks.add(
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"El banner de cabecera NO es de rebajas  (NO contiene un símbolo de porcentaje o \"" + saleTraduction + "\")",
			!pgGaleria.isBannerHeadSalesBanner(idioma));

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
