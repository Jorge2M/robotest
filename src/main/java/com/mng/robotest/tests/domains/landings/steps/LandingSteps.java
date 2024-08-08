package com.mng.robotest.tests.domains.landings.steps;

import static com.github.jorge2m.testmaker.conf.State.*;

import java.net.URI;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.landings.pageobjects.PageEdits;
import com.mng.robotest.tests.domains.landings.pageobjects.PageLanding;
import com.mng.robotest.tests.domains.landings.pageobjects.banners.DataBanner;
import com.mng.robotest.tests.domains.menus.steps.MenuSteps;
import com.mng.robotest.testslegacy.beans.Pais;

public class LandingSteps extends StepBase {

	private final PageLanding pageLanding = new PageLanding();
	
	@Validation (description="Aparece la página de Landing Multimarca o de Línea " + SECONDS_WAIT)
	public boolean checkIsLanding(int seconds) {
		return pageLanding.isPage(seconds);
	}
	
	@Validation (description="Aparece la página de Landing <b>Multimarca</b> " + SECONDS_WAIT)
	public boolean checkIsLandingMultimarca(int seconds) {
		return pageLanding.isLandingMultimarca(seconds);
	}
	
	@Validation(description="Aparece la Landing de línea")
	public boolean checkIsLandingLinea() {
		return pageLanding.isLandingLinea(0);	
	}	
	
	public void checkIsCountryWithCorrectLineas(int seconds) {
		checkUrlOfCountry(dataTest.getPais(), seconds);
		checkIsLanding(0);
		if (!channel.isDevice()) {
			new MenuSteps().checkLineasCountry();
		}
	}

	@Validation (
		description="En la URL figura <b>/#{pais.getCodigoPrehome().toLowerCase()}</b> " + SECONDS_WAIT)
	public boolean checkUrlOfCountry(Pais pais, int seconds) {
		return isTextInURL("/" + pais.getCodigoPrehome().toLowerCase(), seconds);
	}
	
	@Validation (description="No es visible el comms header banner de Loyalty")
	public boolean isInvisibleCommsHeaderBannerLoyalty(int seconds) {
		return !pageLanding.isVisibleCommsHeaderBannerLoyalty(seconds);
	}	

	@Validation (description="No es visible ningún elemento de Loyalty")
	public boolean isInvisibleAnyElementLoyalty() {
		return !pageLanding.isVisibleAnyElementLoyalty();
	}	
	
	public void checkPageBanners(int maximoBanners) throws Exception { 
		String urlPagPrincipal = getCurrentUrl();
		int sizeListBanners = pageLanding.getListDataBanners().size();
		for (int posBanner=1; posBanner<=sizeListBanners && posBanner<=maximoBanners; posBanner++) {
			selectBanner(posBanner, true);
			driver.get(urlPagPrincipal);
			waitForPageLoaded();
			pageLanding.reloadDataBanners();
			sizeListBanners = pageLanding.getListDataBanners().size();
		}
	}
	
	public void selectBanner(int posBanner, boolean validaciones) throws Exception {
		DataBanner dataBanner = pageLanding.getDataBanner(posBanner);
		selectBanner(dataBanner, validaciones);
	}
	
	@Step (
		description=
			"Seleccionar el <b>BANNER #{dataBanner.getPosition()}</b>:<br>" +
		    "<div style=\"padding-left: 10px;\">" +
				"<div><b>Texto:</b> <b style=\"color:blue;\">#{dataBanner.getText()}</b></div>" +
				"<div><b>URL:</b> #{dataBanner.getUrl()}</div>" +  
				"<div><b>Imagen:</b> #{dataBanner.getSrcImage()}</div>" + 
			"</div>", 
		expected="Aparece una página con artículos")
	private void selectBanner(DataBanner dataBanner, boolean checks) throws Exception {
		String urlPagPrincipal = getCurrentUrl();
		URI uriPagPrincipal = new URI(urlPagPrincipal);
		int elementosPagPrincipal = driver.findElements(By.xpath("//*")).size();
		
		pageLanding.clickBannerAndWaitLoad(dataBanner);
		dataBanner.setUrlResultant(getCurrentUrl());
		if (checks) {
			checkSelectBanner(urlPagPrincipal, uriPagPrincipal, elementosPagPrincipal);
		}
	}

	private void checkSelectBanner(String urlPagPadre, URI uriPagPadre, int elementosPagPadre)
			throws Exception {
		checksGeneralesBanner(urlPagPadre, uriPagPadre, elementosPagPadre);
		checksGeneric().imgsBroken().execute();
		isArticlePresent(6);
	}
	
	@Validation (description="Aparece una Galería o Edits con algún artículo visible " + SECONDS_WAIT)	
	public boolean isArticlePresent(int seconds) {
		var pgGaleria = PageGaleria.make(channel);
		var pgEdits = new PageEdits();
		for (int i=0; i<=seconds; i++) {
			if (pgGaleria.isVisibleAnyArticle() ||
				pgEdits.isVisibleAnyArticle()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}	
	
	@Validation
	private ChecksTM checksGeneralesBanner(String urlPagPadre, URI uriPagPadre, int elementosPagPadre) 
			throws Exception {
		var checks = ChecksTM.getNew();
		int seconds1 = 3;
		int marginElements = 2;
		int seconds2 = 1;

	 	checks.add(
	 		"La URL de la página cambia " + getLitSecondsWait(seconds1),
	 		pageLanding.checkUrlNotMatch(urlPagPadre, seconds1));  
	 	
	 	boolean urlEqual = false;
	 	boolean elemsEqual = false;
	 	if (urlPagPadre.compareTo(driver.getCurrentUrl())==0) {
	 		urlEqual = true;
	 		elemsEqual = !pageLanding.checkElementsNotEquals(elementosPagPadre, marginElements, seconds2);
	 	}
	 	checks.add(
	 		"La página cambia: <br>" + 
	 		"- La URL cambia o <br>" + 
	 		"- El número de elementos DOM ha variado (en " + marginElements + " o más) con respecto al original (" + elementosPagPadre + ")",
	 		(!urlEqual || !elemsEqual), WARN); 
	 	
		String urlPagActual = getCurrentUrl();
		URI uriPagActual = new URI(urlPagActual);
	 	checks.add(
	 		"El dominio de la página se corresponde con el de la página padre:" + uriPagPadre.getHost(),
	 		uriPagPadre.getHost().compareTo(uriPagActual.getHost())==0);	
	 	
	 	return checks;
	}

	@Validation (
		description="Existen banners de Landing")
	public boolean checkBannersInContent() {
		return pageLanding.isBanners();
	}

}
