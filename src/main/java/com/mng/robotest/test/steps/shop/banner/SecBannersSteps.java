package com.mng.robotest.test.steps.shop.banner;

import java.net.URI;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.AllPages;
import com.mng.robotest.test.pageobject.shop.bannersNew.DataBanner;
import com.mng.robotest.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test.steps.shop.genericchecks.Checker;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;


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
	
	public void testPageBanners(int maximoBanners) throws Exception { 
		String urlPagPrincipal = driver.getCurrentUrl();
		int sizeListBanners = managerBannersScreen.getListDataBanners().size();
		for (int posBanner=1; posBanner<=sizeListBanners && posBanner<=maximoBanners; posBanner++) {
			boolean makeValidations = true;
			seleccionarBanner(posBanner, makeValidations, app, channel, dataTest.pais);
			driver.get(urlPagPrincipal);
			SeleniumUtils.waitForPageLoaded(driver);
			managerBannersScreen.reloadBanners(); //For avoid StaleElement Exception
			sizeListBanners = managerBannersScreen.getListDataBanners().size();
		}
	}
	
	public void seleccionarBanner(int posBanner, boolean validaciones, AppEcom app, Channel channel, Pais pais) 
	throws Exception {
		DataBanner dataBanner = this.managerBannersScreen.getBanner(posBanner);
		seleccionarBanner(dataBanner, validaciones, app, channel, pais);
	}
	
	@Step (
		description=
			"Seleccionar el <b>Banner #{dataBanner.getPosition()}</b> y obtener sus datos:<br>" + 
				"<b>URL</b>: #{dataBanner.getUrlBanner()}<br>" + 
				"<b>imagen</b>: #{dataBanner.getSrcImage()}<br>" + 
				"<b>texto</b>: #{dataBanner.getText()}",
		expected="Aparece una página correcta (con banners o artículos)")
	public void seleccionarBanner(DataBanner dataBanner, boolean validaciones, AppEcom app, Channel channel, Pais pais) 
	throws Exception {
		String urlPagPrincipal = driver.getCurrentUrl();
		URI uriPagPrincipal = new URI(urlPagPrincipal);
		int elementosPagPrincipal = driver.findElements(By.xpath("//*")).size();
		
		this.managerBannersScreen.clickBannerAndWaitLoad(dataBanner);
		dataBanner.setUrlDestino(driver.getCurrentUrl());
		if (validaciones) {
			//Validaciones
			validacionesGeneralesBanner(urlPagPrincipal, uriPagPrincipal, elementosPagPrincipal);
			switch (dataBanner.getDestinoType()) {
			case Ficha:
				PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps();
				pageFichaSteps.validateIsFichaCualquierArticulo();
				break;
			default:				
			case Otros:
				validacionesBannerEstandar(2, channel, app);
				break;
			}
		}
	}
		
	@Validation
	public ChecksTM validacionesGeneralesBanner(String urlPagPadre, URI uriPagPadre, int elementosPagPadre) 
	throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds1 = 3;
		int marginElements = 2;
		int maxSeconds2 = 1;
		
		AllPages allPages = new AllPages(); 
	 	checks.add(
	 		"La URL de la página cambia (lo esperamos hasta un máximo de " + maxSeconds1 + " segundos)",
	 		allPages.validateUrlNotMatchUntil(urlPagPadre, maxSeconds1), State.Defect);  
	 	
	 	boolean urlEqual = false;
	 	boolean elemsEqual = false;
	 	if (urlPagPadre.compareTo(driver.getCurrentUrl())==0) {
	 		urlEqual = true;
	 		elemsEqual = !allPages.validateElementsNotEqualsUntil(elementosPagPadre, marginElements, maxSeconds2);
	 	}
	 	checks.add(
	 		"La página cambia: <br>" + 
	 		"- La URL cambia o <br>" + 
	 		"- El número de elementos DOM ha variado (en " + marginElements + " o más) con respecto al original (" + elementosPagPadre + ")",
	 		(!urlEqual || !elemsEqual), State.Warn); 
	 	
	 	Checker checkImagesBroken = Checker.make(GenericCheck.ImgsBroken);
	 	ChecksTM checksImgs = checkImagesBroken.check(driver);
	 	checks.add(checksImgs.get(0));
	 	
		String urlPagActual = driver.getCurrentUrl();
		URI uriPagActual = new URI(urlPagActual);
	 	checks.add(
	 		"El dominio de la página se corresponde con el de la página padre:" + uriPagPadre.getHost(),
	 		uriPagPadre.getHost().compareTo(uriPagActual.getHost())==0, State.Defect);	
	 	
	 	return checks;
	}

	@Validation (
		description=
			"Esperamos hasta #{maxSeconds} segundos a que aparezca una página con alguno de los siguientes elementos:<br>" + 
			"- Secciones<br>" + 
			"- Galería<br>" + 
			"- Banners<br>" + 
			"- Ficha<br>" +
			"- Bloque de contenido con imágenes o página acceso",
		level=State.Warn)
	public boolean validacionesBannerEstandar(int maxSeconds, Channel channel, AppEcom app) throws Exception {
		for (int i=0; i<maxSeconds; i++) {
			if (validacionesBannerEstandar(channel, app)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validacionesBannerEstandar(Channel channel, AppEcom app) throws Exception {
		if (!pageLanding.haySecc_Art_Banners(app)) {
			if (!pageLanding.hayImgsEnContenido()) {
				PageFicha pageFicha = PageFicha.of(channel);
				return pageFicha.isPageUntil(0);
			}
		}
		return true; 
	}

	@Validation (
		description="El bloque de contenido (homeContent o bannerHome) existe y tiene >= 1 banner o >=1 map o >=1 items-edit",
		level=State.Warn)
	public boolean validaBannEnContenido() {
		boolean existBanners = managerBannersScreen.existBanners();
		boolean existsMaps = pageLanding.hayMaps();
		boolean existsEditItems = pageLanding.hayItemsEdits();
		return (existBanners || existsMaps || existsEditItems);
	}
}