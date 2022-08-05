package com.mng.robotest.domains.ficha.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.pageobjects.PageFichaArtOld;
import com.mng.robotest.domains.ficha.pageobjects.SecBreadcrumbFichaOld;
import com.mng.robotest.domains.ficha.pageobjects.Slider;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha.TypeFicha;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.ActionFavButton;
import com.mng.robotest.domains.ficha.pageobjects.SecBreadcrumbFichaOld.ItemBCrumb;
import com.mng.robotest.domains.ficha.pageobjects.SecDataProduct.ColorType;
import com.mng.robotest.domains.ficha.pageobjects.SecDataProduct.ProductNav;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

import java.util.Arrays;
import java.util.List;


@SuppressWarnings({"static-access"})
public class PageFichaArtSteps {
	
	private final WebDriver driver = TestMaker.getDriverTestCase();
	Channel channel;
	AppEcom app;
	
	private final PageFicha pageFicha;
	private final SecBolsa secBolsa;
	private final ModEnvioYdevolNewSteps modEnvioYdevolSteps;
	private final SecProductDescrOldSteps secProductDescOldSteps;
	private final SecBolsaButtonAndLinksNewSteps secBolsaButtonAndLinksNewSteps;
	private final SecFotosNewSteps secFotosNewSteps;
	private final SecFitFinderSteps secFitFinderSteps;
	private final SecTotalLookSteps secTotalLookSteps;
	
	public PageFichaArtSteps(AppEcom appE, Channel channel, Pais pais) {
		this.channel = channel;
		this.app = appE;
		this.pageFicha = PageFicha.newInstance(channel, appE);
		this.secBolsa = SecBolsa.make(channel, app, pais);
		this.modEnvioYdevolSteps = new ModEnvioYdevolNewSteps();
		this.secProductDescOldSteps = new SecProductDescrOldSteps(channel, appE);
		this.secTotalLookSteps = new SecTotalLookSteps();
		this.secFitFinderSteps = new SecFitFinderSteps();
		this.secBolsaButtonAndLinksNewSteps = new SecBolsaButtonAndLinksNewSteps();
		this.secFotosNewSteps = new SecFotosNewSteps();
	}
	
	public PageFicha getFicha() {
		return this.pageFicha;
	}
	
	public void validateIsFichaAccordingTypeProduct(GarmentCatalog product) throws Exception {			
		validateIsFichaArtDisponible(product.getGarmentId(), 3);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.ImgsBroken,
				GenericCheck.NetTraffic)).checks(driver);
	}
	
	@Validation (
		description=
			"Aparece la página correspondiente a la ficha del artículo #{refArticulo}" + 
			" (La esperamos hasta #{maxSeconds} segundos)")
	public boolean validateIsFichaArtDisponible(String refArticulo, int maxSeconds) { 
		return (pageFicha.isFichaArticuloUntil(refArticulo, maxSeconds));
	}
	
	@Validation
	public ChecksTM validateIsFichaArtAlgunoColorNoDisponible(String refArticulo) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página correspondiente a la ficha del artículo " + refArticulo,
			pageFicha.isFichaArticuloUntil(refArticulo, 0), State.Defect); 
	 	checks.add(
			"Aparece algún color no disponible",
			state(Present, ColorType.UNAVAILABLE.getBy(), driver).check(), State.Defect); 
	 	return checks;
	}
	
	@Validation (
		description="Aparece la página de Ficha",
		level=State.Warn)
	public boolean validateIsFichaCualquierArticulo() {
		return (pageFicha.isPageUntil(0));  
	}
	
	@Validation
	public ChecksTM validaDetallesProducto(DataFichaArt datosArticulo) {
		ChecksTM checks = ChecksTM.getNew();
		if (datosArticulo.availableReferencia()) {
			int maxSeconds = 3;
		 	checks.add(
				"Aparece la página con los datos de la ficha del producto " + datosArticulo.getReferencia() +
				"(la esperamos hasta " + maxSeconds + " segundos)",
				pageFicha.isFichaArticuloUntil(datosArticulo.getReferencia(), maxSeconds), State.Defect);
		}
			
		if (datosArticulo.availableNombre()) {
			String nombreArtFicha = pageFicha.getSecDataProduct().getTituloArt().trim();
		 	checks.add(
				"Como nombre del artículo aparece el seleccionado: " + datosArticulo.getNombre(),
				datosArticulo.getNombre().toLowerCase().compareTo(nombreArtFicha.toLowerCase())==0, State.Warn); 
		}
		
		return checks;
	}

	public void selectColorAndSaveData(ArticuloScreen articulo) {
		selectColor(articulo.getCodigoColor());
		articulo.setColorName(pageFicha.getSecDataProduct().getNombreColorSelected());
	}
	
	@Step (
		description="Seleccionar el color con código <b>#{codigoColor}</b>", 
		expected="Se muestra la ficha correspondiente al color seleccionado")
	public void selectColor(String codigoColor) {
		if (pageFicha.getSecDataProduct().isClickableColor(codigoColor)) {
			pageFicha.getSecDataProduct().selectColorWaitingForAvailability(codigoColor);
		}

		checkIsSelectedColor(codigoColor);
	}
	
	@Step (
		description="Seleccionar el color en la posición <b>#{numColor}</b>", 
		expected="El color se selecciona correctamente")
	public void selectColor(int numColor) {
		pageFicha.getSecDataProduct().clickColor(numColor);
	}
	
	@Validation (
		description="Está seleccionado el color con código <b>#{codigoColor}<b>",
		level=State.Defect)
	private boolean checkIsSelectedColor(String codigoColor) {
		String codigoColorPage = pageFicha.getSecDataProduct().getCodeColor(ColorType.SELECTED); 
		return (codigoColorPage.contains(codigoColor));
	}
	
	public void selectTallaAndSaveData(ArticuloScreen articulo) {
		selectTalla(articulo.getTalla());
		articulo.setTalla(pageFicha.getTallaSelected());
	}
	
	public void selectFirstTallaAvailable() {
		pageFicha.selectFirstTallaAvailable();
	}
	
	@Step (
		description="Seleccionar la talla con código <b>#{talla.name()}</b> (previamente, si está abierta, cerramos la capa de la bolsa)", 
		expected="Se cambia la talla correctamente")
	public void selectTalla(Talla talla) {
		secBolsa.setBolsaToStateIfNotYet(StateBolsa.CLOSED);
		pageFicha.selectTallaByValue(talla);
		checkTallaSelected(talla, 2);
	}

	@Validation (
		description="Queda seleccionada la talla <b>#{talla.name()}</b> (esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkTallaSelected(Talla talla, int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
			Talla tallaSelected = pageFicha.getTallaSelected(); 
			if (tallaSelected==talla) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	@Step (
		description="Seleccionar la talla <b>#{talla.name()} </b>", 
		expected="Aparece una capa de introducción email para aviso")
	public void selectTallaNoDisp(Talla talla) {
		pageFicha.getSecDataProduct().getSecSelTallas().selectTallaByValue(talla);
		checkAppearsCapaAvisame();
	}
	
	@Validation
	public ChecksTM checkAppearsCapaAvisame() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"No aparece el botón \"COMPRAR\"",
			!secBolsa.isVisibleBotonComprar(), State.Defect);
	 	boolean isVisibleAvisame = pageFicha.getSecDataProduct().isVisibleCapaAvisame();
	 	checks.add(
			"Aparece la capa de introducción de avísame",
			isVisibleAvisame, State.Defect);
	 	return checks;
	}
	
	@Step (
		description="Seleccionar el botón <b>\"Añadir a la bolsa\"</b>", 
		expected="El comportamiento es el esperado... :-)")
	public void selectAnadirALaBolsaStep() {
		pageFicha.clickAnadirBolsaButtonAndWait();
	}
	
	/**
	 * Selección del botón "Añadir a la bolsa" en un contexto en el que previamente NO se ha seleccionado una talla
	 * @return si el artículo seleccionado tenía talla única
	 */
	public boolean selectAnadirALaBolsaTallaPrevNoSelected() throws Exception {
		selectAnadirALaBolsaStep();
		
		boolean isTallaUnica = pageFicha.isTallaUnica();
		TypeFicha typeFichaAct = pageFicha.getTypeFicha();
		checkAvisoTallaUnica(isTallaUnica, typeFichaAct);
		if (!isTallaUnica && typeFichaAct==TypeFicha.NEW) {
			checkListaTallasVisible();
		}
			
		return isTallaUnica;
	}
	
	@Validation
	public ChecksTM checkAvisoTallaUnica(boolean isTallaUnica, TypeFicha typeFichaAct) {
		ChecksTM checks = ChecksTM.getNew();
		boolean isVisibleAviso = pageFicha.getSecDataProduct().isVisibleAvisoSeleccionTalla();
		if (isTallaUnica || typeFichaAct==TypeFicha.NEW) {
		 	checks.add(
		 		"NO aparece un aviso indicando que hay que seleccionar la talla",
		 		!isVisibleAviso, State.Defect);
		} else {
		 	checks.add(
		 		"SÍ aparece un aviso indicando que hay que seleccionar la talla",
		 		isVisibleAviso, State.Defect);
		}
		return checks;
	}
	
	@Validation (
		description="Se hace visible la lista de tallas",
		level=State.Warn)
	public boolean checkListaTallasVisible() {
		return (pageFicha.getSecDataProduct().getSecSelTallas().isVisibleListTallasForSelectUntil(0));
	}
	
	/**
	 * Selección del botón "Añadir a la bolsa" en un contexto en el que previamente SÍ se ha seleccionado una talla
	 */
	public void selectAnadirALaBolsaTallaPrevSiSelected(ArticuloScreen articulo, DataCtxShop dCtxSh) 
	throws Exception {
		selectAnadirALaBolsaStep();
		DataBag dataBag = new DataBag();
		dataBag.addArticulo(articulo);
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh);
		secBolsaSteps.validaAltaArtBolsa(dataBag);
	}	

	public void selectAnadirAFavoritos() throws Exception {
		DataFavoritos dataFavoritos = new DataFavoritos();
		selectAnadirAFavoritos(dataFavoritos);
	}
	
	@Step (
		description="Seleccionar el botón <b>\"Añadir a Favoritos\"</b>", 
		expected="El artículo se añade a Favoritos")
	public void selectAnadirAFavoritos(DataFavoritos dataFavoritos) throws Exception {
		pageFicha.selectAnadirAFavoritosButton();
		ArticuloScreen articulo = pageFicha.getArticuloObject();
		dataFavoritos.addArticulo(articulo);
		checkCapaAltaFavoritos();
		validateVisibleButtonFavoritos(ActionFavButton.REMOVE);
	}

	@Step (
		description="Cambiar de color dentro de la misma ficha volviendo al color/talla originales",
		expected="El articulo es cambiado de color.")
	public void changeColorGarment() {
		ArticuloScreen articulo = pageFicha.getArticuloObject();
		List<String> colors = pageFicha.getSecDataProduct().getColorsGarment();
		String codeColor = getColorNotSelected(colors, articulo);
		pageFicha.getSecDataProduct().selectColor(codeColor);

		validateNotVisibleButtonFavoritos(ActionFavButton.ADD);

		pageFicha.getSecDataProduct().selectColor(articulo.getCodigoColor());
		pageFicha.getSecDataProduct().getSecSelTallas().selectTallaByValue(articulo.getTalla());
	}
	
	private String getColorNotSelected(List<String> listColors, ArticuloScreen articulo) {
		for (String color : listColors) {
			if (color.compareTo(articulo.getCodigoColor())!=0) {
				return color;
			}
		}
		return listColors.get(0);
	}

	@Validation (
	   description="No aparece el icono de favorito marcado al cambiar de color",
	   level=State.Defect)
	public boolean validateNotVisibleButtonFavoritos(ActionFavButton buttonType) {
		switch (buttonType) {
			case REMOVE:
				return (pageFicha.isVisibleButtonElimFavoritos());
			case ADD:
			default:
				return (pageFicha.isVisibleButtonAnadirFavoritos());
		}
	}
	
	@Validation
	private ChecksTM checkCapaAltaFavoritos() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds1 = 3;
	 	checks.add(
			"Aparece una capa superior de \"Añadiendo artículo a favoritos...\" (lo esperamos hasta " + maxSeconds1 + " segundos)",
			pageFicha.isVisibleDivAnadiendoAFavoritosUntil(maxSeconds1), State.Info);
		int maxSeconds2 = 3;
	 	checks.add(
			"La capa superior acaba desapareciendo (lo esperamos hasta " + maxSeconds2 + " segundos)",
			pageFicha.isInvisibleDivAnadiendoAFavoritosUntil(maxSeconds2), State.Warn);
		return checks;
	}

	@Step (
		description="Seleccionar el botón <b>\"Eliminar de Favoritos\"</b>", 
		expected="El artículo se elimina de Favoritos")
	public void selectRemoveFromFavoritos() {
		pageFicha.selectRemoveFromFavoritosButton();
		validateVisibleButtonFavoritos(ActionFavButton.ADD);
	}

	@Validation (
		description="Aparece el botón de #{buttonType} a Favoritos",
		level=State.Defect)
	public boolean validateVisibleButtonFavoritos(ActionFavButton buttonType) {
		switch (buttonType) {
		case REMOVE:
			return (pageFicha.isVisibleButtonElimFavoritos());
		case ADD:
		default:				
			return (pageFicha.isVisibleButtonAnadirFavoritos());
		}
	}


	@Validation (
		description="Es visible el link de <b>Disponibilidad en Tienda</b>",
		level=State.Defect)
	public boolean checkLinkDispTiendaVisible() {
		return pageFicha.isVisibleBuscarEnTiendaLink();
	}
	
	@Validation (
		description="Es invisible el link de <b>Disponibilidad en Tienda</b>",
		level=State.Defect)
	public boolean checkLinkDispTiendaInvisible() {
		return !pageFicha.isVisibleBuscarEnTiendaLink();
	}
	
	final String tagNameLink = "@TagNameLink";
	@Step (
		description="Seleccionar <b>" + tagNameLink + "</b>", 
		expected="Aparece un resultado de la búsqueda correcta")
	public void selectBuscarEnTiendaButton() {
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagNameLink, pageFicha.getNameLinkBuscarEnTienda());
		pageFicha.selectBuscarEnTiendaLink();
		new ModalBuscadorTiendasSteps(channel, app, driver).validaBusquedaConResultados();
	}

	@Step (
		description="Si está visible, Seleccionar el link \"<b>Guía de tallas</b>\"", 
		expected="Aparece la página asociada a la guía de tallas")
	public void selectGuiaDeTallas(AppEcom app) throws Exception {
		boolean isVisibleLink = pageFicha.getSecDataProduct().selectGuiaDeTallasIfVisible();		   
		if (isVisibleLink) {
			switch (app) {
			case outlet:
				PageComoMedirmeSteps pageComoMedirmeSteps = new PageComoMedirmeSteps();
				pageComoMedirmeSteps.isPageInNewTab();
				break;
			case shop:
			default:
				secFitFinderSteps.validateIsOkAndClose();
			}
		}
	}
	
	public void validateSliderIfExists(Slider typeSlider) {
		boolean isVisibleSlider = checkSliderVisible(typeSlider);
		if (isVisibleSlider) {
			checkNumArticlesSlider(0, typeSlider);
		}
	}
	
	@Validation (
		description="Es visible el slider de artículos de tipo <b>#{typeSlider}</b>",
		level=State.Info,
		store=StoreType.None)
	public boolean checkSliderVisible(Slider typeSlider) {
		return (pageFicha.isVisibleSlider(typeSlider));
	}
	
	@Validation (
		description="El número de artículos del slider de tipo <b>#{typeSlider}</b> es > #{numArtMin}",
		level=State.Warn)
	public boolean checkNumArticlesSlider(int numArtMin, Slider typeSlider) {
		return (pageFicha.getNumArtVisiblesSlider(typeSlider) > numArtMin); 
	}
	
	@Validation
	public ChecksTM validaPrevNext(LocationArticle locationArt, DataCtxShop dCtxSh) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		boolean isVisiblePrevLink = pageFicha.getSecDataProduct().isVisiblePrevNextUntil(ProductNav.PREV, maxSeconds);
		if (locationArt.isFirstInGalery()) {
		 	checks.add(
		 		"No es visible el link <b>Prev</b> (lo esperamos hasta " + maxSeconds + " segundos)",
		 		!isVisiblePrevLink, State.Warn);
		} else {
		 	checks.add(
		 		"Sí es visible el link <b>Prev</b> (lo esperamos hasta " + maxSeconds + " segundos)",
		 		isVisiblePrevLink, State.Warn);
		}
		if (dCtxSh.appE==AppEcom.outlet || dCtxSh.channel==Channel.desktop) {
		 	checks.add(
		 		"Es visible el link <b>Next</b>",
		 		pageFicha.getSecDataProduct().isVisiblePrevNextUntil(ProductNav.NEXT, 0), State.Warn);
		}
		
		return checks;
	}
	
	@Step (
		description="Seleccionamos el link #{productNav}</b>", 
		expected="Aparece una página de ficha correcta")
	public void selectLinkNavigation(ProductNav productNav, DataCtxShop dCtxSh, String refProductOrigin) {
		pageFicha.getSecDataProduct().selectLinkNavigation(productNav);
		if (productNav==ProductNav.PREV) {
			validateIsFichaArtDisponible(refProductOrigin, 3);
		}
		
		if (productNav==ProductNav.NEXT) {
			LocationArticle location2onArticle = LocationArticle.getInstanceInCatalog(2);
			validaPrevNext(location2onArticle, dCtxSh);
		}
	}
	
	//------------------------------------------------------------------------
	//Específic Ficha Old
	
	@Validation (
		description="Existe más de una imagen de carrusel a la izquierda de la imagen principal",
		level=State.Warn)
	public boolean validaExistsImgsCarruselIzqFichaOld() {
		return (((PageFichaArtOld)pageFicha).getNumImgsCarruselIzq() >= 2);
	}
	
	@Step (
		description="Seleccionar la #{numImagen}a imagen del carrusel izquierdo", 
		expected="La imagen se carga aumentada en la imagen central")
	public void selectImgCarruselIzqFichaOld(int numImagen) throws Exception { 
		String pngImagenCarrusel = ((PageFichaArtOld)pageFicha).getSrcImgCarruselIzq(numImagen);
		((PageFichaArtOld)pageFicha).clickImgCarruselIzq(numImagen);		   
					
		//Validaciones
		checkImgCentralIsAssociatedToCarruselSelect(pngImagenCarrusel);
	}
	
	@Validation (
		description="La imagen central se corresponde con la imagen del carrusel seleccionada (<b>#{pngImagenCarrusel}</b>)",
		level=State.Defect)
	private boolean checkImgCentralIsAssociatedToCarruselSelect(String pngImagenCarrusel) {
		return (((PageFichaArtOld)pageFicha).srcImagenCentralCorrespondsToImgCarrusel(pngImagenCarrusel));
	}
	
	@Step (
		description="Seleccionar la imagen/ficha central", 
		expected="Se produce un zoom sobre la imagen")
	public void selectImagenCentralFichaOld() {
		String pngImgCentralOriginal = ((PageFichaArtOld)pageFicha).getSrcImagenCentral();
		((PageFichaArtOld)pageFicha).clickImagenFichaCentral();
		checkImgCentralAfterZoom(pngImgCentralOriginal);
	}	
	
	@Validation
	public ChecksTM checkImgCentralAfterZoom(String pngImgCentralOriginal) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
	 		"Se aplica un Zoom sobre la imagen central",
	 		((PageFichaArtOld)pageFicha).isVisibleFichaConZoom(), State.Defect);
	 	checks.add(
	 		"La imagen central con Zoom sigue conteniendo la imagen original: " + pngImgCentralOriginal,
	 		((PageFichaArtOld)pageFicha).srcImagenCentralConZoomContains(pngImgCentralOriginal), State.Defect);
	 	return checks;
	}
	
	@Step (
		description="Seleccionar el aspa para cerrar la imagen central con Zoom", 
		expected="La imagen con Zoom desaparece")
	public void closeZoomImageCentralDevice() {
		((PageFichaArtOld)pageFicha).closeZoomImageCentralDevice();
	}
	
	@Validation (
		description="La imagen central se corresponde con la imagen del carrusel seleccionada (<b>#{pngImagenCarrusel}</b>)",
		level=State.Defect)
	private boolean checkZoomImageCentralDissapeared() {
		return !((PageFichaArtOld)pageFicha).isVisibleFichaConZoom();
	}
	
	@Validation
	public ChecksTM validaBreadCrumbFichaOld(String urlGaleryOrigin) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
	 		"Existen el bloque correspondiente a las <b>BreadCrumb</b>",
	 		SecBreadcrumbFichaOld.isVisibleBreadCrumb(driver), State.Defect);		
	 	String urlGaleryBC = SecBreadcrumbFichaOld.getUrlItemBreadCrumb(ItemBCrumb.GALERY, driver);
	 	checks.add(
	 		"El link correspondiente a la Galería del artículo linca a la URL " + urlGaleryOrigin,
	 		urlGaleryOrigin.contains(urlGaleryBC), State.Warn); 
	 	return checks;
	}

	public SecProductDescrOldSteps getSecProductDescOldSteps() {
		return secProductDescOldSteps;
	}

	public SecBolsaButtonAndLinksNewSteps getSecBolsaButtonAndLinksNewSteps() {
		return secBolsaButtonAndLinksNewSteps;
	}

	public SecFotosNewSteps getSecFotosNewSteps() {
		return secFotosNewSteps;
	}

	public SecFitFinderSteps getSecFitFinderSteps() {
		return secFitFinderSteps;
	}

	public SecTotalLookSteps getSecTotalLookSteps() {
		return secTotalLookSteps;
	}
	
	public ModEnvioYdevolNewSteps getModEnvioYdevolSteps() {
		return this.modEnvioYdevolSteps;
	}


}