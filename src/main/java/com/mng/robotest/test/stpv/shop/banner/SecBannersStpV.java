package com.mng.robotest.test.stpv.shop.banner;

import java.net.URI;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.AllPages;
import com.mng.robotest.test.pageobject.shop.bannersNew.DataBanner;
import com.mng.robotest.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test.stpv.shop.genericchecks.Checker;
import com.mng.robotest.test.stpv.shop.genericchecks.CheckerImgsBroken;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;


public class SecBannersStpV {
	
	int maxBannersToLoad;
	private final ManagerBannersScreen managerBannersScreen;
	private final PageLanding pageLanding;
	private final WebDriver driver;
	
	public SecBannersStpV(int maxBannersToLoad, WebDriver driver) {
		this.driver = driver;
		managerBannersScreen = new ManagerBannersScreen(maxBannersToLoad, driver);
		pageLanding = new PageLanding(driver);
	}
	
	public ManagerBannersScreen getManagerBannerScreen() {
		return managerBannersScreen;
	}
	
	public void testPageBanners(DataCtxShop dCtxSh, int maximoBanners) throws Exception { 
		String urlPagPrincipal = driver.getCurrentUrl();
		int sizeListBanners = managerBannersScreen.getListDataBanners().size();
		for (int posBanner=1; posBanner<=sizeListBanners && posBanner<=maximoBanners; posBanner++) {
			boolean makeValidations = true;
			seleccionarBanner(posBanner, makeValidations, dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
			driver.get(urlPagPrincipal);
			SeleniumUtils.waitForPageLoaded(driver);
			managerBannersScreen.reloadBanners(driver); //For avoid StaleElement Exception
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
		
		this.managerBannersScreen.clickBannerAndWaitLoad(dataBanner, driver);
		dataBanner.setUrlDestino(driver.getCurrentUrl());
		if (validaciones) {
			//Validaciones
			validacionesGeneralesBanner(urlPagPrincipal, uriPagPrincipal, elementosPagPrincipal);
			switch (dataBanner.getDestinoType()) {
			case Ficha:
				PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(app, channel, pais);
				pageFichaStpV.validateIsFichaCualquierArticulo();
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
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds1 = 3;
		int marginElements = 2;
		int maxSeconds2 = 1;
	 	validations.add(
	 		"La URL de la página cambia (lo esperamos hasta un máximo de " + maxSeconds1 + " segundos)",
	 		AllPages.validateUrlNotMatchUntil(urlPagPadre, maxSeconds1, driver), State.Defect);  
	 	
	 	boolean urlEqual = false;
	 	boolean elemsEqual = false;
	 	if (urlPagPadre.compareTo(driver.getCurrentUrl())==0) {
	 		urlEqual = true;
	 		elemsEqual = !AllPages.validateElementsNotEqualsUntil(elementosPagPadre, marginElements, maxSeconds2, driver);
	 	}
	 	validations.add(
	 		"La página cambia: <br>" + 
	 		"- La URL cambia o <br>" + 
	 		"- El número de elementos DOM ha variado (en " + marginElements + " o más) con respecto al original (" + elementosPagPadre + ")",
	 		(!urlEqual || !elemsEqual), State.Warn); 
	 	
	 	Checker checkImagesBroken = Checker.make(GenericCheck.ImgsBroken);
	 	ChecksTM checksImgs = checkImagesBroken.check(driver);
	 	validations.add(checksImgs.get(0));
	 	
//	 	int maxErrors = 1;
//		ResultadoErrores resultadoImgs = CheckerImgsBroken.imagesBroken(driver, Channel.desktop, maxErrors);
//		if (resultadoImgs.getResultado() != ResultadoErrores.Resultado.OK) { // Si hay error lo pintamos en la descripción de la validación
//		 	validations.add(
//		 		"No hay imágenes cortadas" + resultadoImgs.getlistaLogError().toString(),
//		 		resultadoImgs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES, State.Defect);	 
//		}

		String urlPagActual = driver.getCurrentUrl();
		URI uriPagActual = new URI(urlPagActual);
	 	validations.add(
	 		"El dominio de la página se corresponde con el de la página padre:" + uriPagPadre.getHost(),
	 		uriPagPadre.getHost().compareTo(uriPagActual.getHost())==0, State.Defect);	
	 	
	 	return validations;
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
				PageFicha pageFicha = PageFicha.newInstance(channel, app, driver);
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