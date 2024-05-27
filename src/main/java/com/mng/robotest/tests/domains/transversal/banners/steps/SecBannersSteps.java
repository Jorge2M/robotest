package com.mng.robotest.tests.domains.transversal.banners.steps;

import java.net.URI;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.transversal.banners.pageobjects.DataBanner;
import com.mng.robotest.tests.domains.transversal.banners.pageobjects.ManagerBannersScreen;
import com.mng.robotest.tests.domains.transversal.home.pageobjects.PageLanding;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecBannersSteps extends StepBase {
	
	int maxBannersToLoad;
	private final ManagerBannersScreen managerBannersScreen;
	private final PageLanding pageLanding = new PageLanding();
	
	public SecBannersSteps(int maxBannersToLoad) {
		managerBannersScreen = new ManagerBannersScreen(maxBannersToLoad);
	}
	
	public ManagerBannersScreen getManagerBannerScreen() {
		return managerBannersScreen;
	}
	
	public void checkPageBanners(int maximoBanners) throws Exception { 
		String urlPagPrincipal = driver.getCurrentUrl();
		int sizeListBanners = managerBannersScreen.getListDataBanners().size();
		for (int posBanner=1; posBanner<=sizeListBanners && posBanner<=maximoBanners; posBanner++) {
			boolean makeValidations = true;
			selectBanner(posBanner, makeValidations);
			driver.get(urlPagPrincipal);
			PageObjTM.waitForPageLoaded(driver);
			managerBannersScreen.reloadBanners(); //For avoid StaleElement Exception
			sizeListBanners = managerBannersScreen.getListDataBanners().size();
		}
	}
	
	public void selectBanner(int posBanner, boolean validaciones) throws Exception {
		DataBanner dataBanner = managerBannersScreen.getBanner(posBanner);
		selectBanner(dataBanner, validaciones);
	}
	
	@Step (
		description=
			"Seleccionar el <b>Banner #{dataBanner.getPosition()}</b> y obtener sus datos:<br>" + 
				"<b>URL</b>: #{dataBanner.getUrlBanner()}<br>" + 
				"<b>imagen</b>: #{dataBanner.getSrcImage()}<br>" + 
				"<b>texto</b>: #{dataBanner.getText()}",
		expected="Aparece una página correcta (con banners o artículos)")
	public void selectBanner(DataBanner dataBanner, boolean validaciones) throws Exception {
		String urlPagPrincipal = driver.getCurrentUrl();
		URI uriPagPrincipal = new URI(urlPagPrincipal);
		int elementosPagPrincipal = driver.findElements(By.xpath("//*")).size();
		
		managerBannersScreen.clickBannerAndWaitLoad(dataBanner);
		dataBanner.setUrlDestino(driver.getCurrentUrl());
		if (validaciones) {
			checkGeneralesBanner(urlPagPrincipal, uriPagPrincipal, elementosPagPrincipal);
			switch (dataBanner.getDestinoType()) {
			case FICHA:
				new FichaSteps().checkIsFicha(2);
				break;
			default:				
			case OTROS:
				validacionesBannerEstandar(3);
				break;
			}
		}
	}
		
	public void checkGeneralesBanner(String urlPagPadre, URI uriPagPadre, int elementosPagPadre) 
			throws Exception {
		checksGeneralesBanner(urlPagPadre, uriPagPadre, elementosPagPadre);
		checksGeneric().imgsBroken().execute();
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
	 		managerBannersScreen.checkUrlNotMatchUntil(urlPagPadre, seconds1));  
	 	
	 	boolean urlEqual = false;
	 	boolean elemsEqual = false;
	 	if (urlPagPadre.compareTo(driver.getCurrentUrl())==0) {
	 		urlEqual = true;
	 		elemsEqual = !managerBannersScreen.checkElementsNotEqualsUntil(elementosPagPadre, marginElements, seconds2);
	 	}
	 	checks.add(
	 		"La página cambia: <br>" + 
	 		"- La URL cambia o <br>" + 
	 		"- El número de elementos DOM ha variado (en " + marginElements + " o más) con respecto al original (" + elementosPagPadre + ")",
	 		(!urlEqual || !elemsEqual), WARN); 
	 	
		String urlPagActual = driver.getCurrentUrl();
		URI uriPagActual = new URI(urlPagActual);
	 	checks.add(
	 		"El dominio de la página se corresponde con el de la página padre:" + uriPagPadre.getHost(),
	 		uriPagPadre.getHost().compareTo(uriPagActual.getHost())==0);	
	 	
	 	return checks;
	}

	@Validation (
		description=
			"Esperamos hasta #{seconds} segundos a que aparezca una página con alguno de los siguientes elementos:<br>" + 
			"- Secciones<br>" + 
			"- Galería<br>" + 
			"- Banners<br>" + 
			"- Ficha<br>" +
			"- Bloque de contenido con imágenes o página acceso",
		level=WARN)
	public boolean validacionesBannerEstandar(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (checksStandardBanners()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	private boolean checksStandardBanners() {
		if (!pageLanding.isSeccArtBanners() &&
			!pageLanding.hayImgsEnContenido()) {
			var pageFicha = PageFicha.make(channel, app, dataTest.getPais());
			return pageFicha.isPage(0);
		}
		return true; 
	}

	@Validation (
		description="El bloque de contenido (homeContent o bannerHome) existe y tiene >= 1 banner o >=1 map o >=1 items-edit")
	public boolean checkBannersInContent() {
		boolean existBanners = managerBannersScreen.existBanners();
		boolean existsMaps = pageLanding.hayMaps();
		boolean existsEditItems = pageLanding.hayItemsEdits();
		return (existBanners || existsMaps || existsEditItems);
	}
	
}