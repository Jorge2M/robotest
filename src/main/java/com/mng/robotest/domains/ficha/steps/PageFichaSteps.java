package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.pageobjects.PageFichaDevice;
import com.mng.robotest.domains.ficha.pageobjects.SecDetalleProduct;
import com.mng.robotest.domains.ficha.pageobjects.SecDetalleProduct.ItemBreadcrumb;
import com.mng.robotest.domains.galeria.steps.LocationArticle;
import com.mng.robotest.domains.ficha.pageobjects.Slider;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.ActionFavButton;
import com.mng.robotest.domains.ficha.pageobjects.SecDataProduct.ColorType;
import com.mng.robotest.domains.ficha.pageobjects.SecDataProduct.ProductNav;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

import java.util.Arrays;
import java.util.List;


public class PageFichaSteps extends StepBase {

	private final PageFicha pageFicha = PageFicha.of(channel);
	private final SecBolsa secBolsa = new SecBolsa();
	private final ModEnvioYdevolNewSteps modEnvioYdevolSteps = new ModEnvioYdevolNewSteps();
	private final SecProductDescrOldSteps secProductDescOldSteps = new SecProductDescrOldSteps();
	private final SecBolsaButtonAndLinksNewSteps secBolsaButtonAndLinksNewSteps = new SecBolsaButtonAndLinksNewSteps();
	private final SecFotosNewSteps secFotosNewSteps = new SecFotosNewSteps();
	private final SecFitFinderSteps secFitFinderSteps = new SecFitFinderSteps();
	private final SecTotalLookSteps secTotalLookSteps = new SecTotalLookSteps();

	public PageFicha getFicha() {
		return this.pageFicha;
	}

	public void checkIsFichaAccordingTypeProduct(Article article) {
		checkIsFichaArtDisponible(article.getArticleId(), 3);
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(
				GenericCheck.ImgsBroken,
				GenericCheck.NetTraffic)).checks();
	}

	@Validation (
		description=
			"Aparece la página correspondiente a la ficha del artículo #{refArticulo}" +
			" (La esperamos hasta #{seconds} segundos)")
	public boolean checkIsFichaArtDisponible(String refArticulo, int seconds) {
		return pageFicha.isFichaArticuloUntil(refArticulo, seconds);
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
			int seconds = 3;
			checks.add(
				"Aparece la página con los datos de la ficha del producto " + datosArticulo.getReferencia() +
				" (la esperamos hasta " + seconds + " segundos)",
				pageFicha.isFichaArticuloUntil(datosArticulo.getReferencia(), seconds), State.Defect);
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
		description="Queda seleccionada la talla <b>#{talla.name()}</b> (esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	private boolean checkTallaSelected(Talla talla, int seconds) {
		for (int i=0; i<seconds; i++) {
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
	public boolean selectAnadirALaBolsaTallaPrevNoSelected() {
		selectAnadirALaBolsaStep();
		boolean isTallaUnica = pageFicha.isTallaUnica();
		if (!isTallaUnica && channel.isDevice()) {
			checkListaTallasVisible();
		} else {
			checkAvisoTallaUnica(isTallaUnica);
		}

		return isTallaUnica;
	}

	@Validation
	public ChecksTM checkAvisoTallaUnica(boolean isTallaUnica) {
		ChecksTM checks = ChecksTM.getNew();
		boolean isVisibleAviso = pageFicha.getSecDataProduct().getSecSelTallas()
				.isVisibleAvisoSeleccionTalla();
		
		if (isTallaUnica) {
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
	public void selectAnadirALaBolsaTallaPrevSiSelected(ArticuloScreen articulo) throws Exception {
		selectAnadirALaBolsaStep();
		dataTest.getDataBag().addArticulo(articulo);
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		secBolsaSteps.validaAltaArtBolsa();
	}

	@Step (
		description="Seleccionar el botón <b>\"Añadir a Favoritos\"</b>",
		expected="El artículo se añade a Favoritos")
	public void selectAnadirAFavoritos() {
		pageFicha.selectAnadirAFavoritosButton();
		ArticuloScreen articulo = pageFicha.getArticuloObject();
		dataTest.getDataFavoritos().addArticulo(articulo);
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
		int seconds1 = 3;
		checks.add(
				"Aparece una capa superior de \"Añadiendo artículo a favoritos...\" (lo esperamos hasta " + seconds1 + " segundos)",
				pageFicha.isVisibleDivAnadiendoAFavoritosUntil(seconds1), State.Info);

		int seconds2 = 3;
		checks.add(
				"La capa superior acaba desapareciendo (lo esperamos hasta " + seconds2 + " segundos)",
				pageFicha.isInvisibleDivAnadiendoAFavoritosUntil(seconds2), State.Warn);

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

	private static final String TAG_NAME_LINK = "@TagNameLink";
	@Step (
		description="Seleccionar <b>" + TAG_NAME_LINK + "</b>",
		expected="Aparece un resultado de la búsqueda correcta")
	public void selectBuscarEnTiendaButton() {
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_NAME_LINK, pageFicha.getNameLinkBuscarEnTienda());
		pageFicha.selectBuscarEnTiendaLink();
		new ModalBuscadorTiendasSteps().validaBusquedaConResultados();
	}

	@Step (
		description="Si está visible, Seleccionar el link \"<b>Guía de tallas</b>\"",
		expected="Aparece la página asociada a la guía de tallas")
	public void selectGuiaDeTallas(AppEcom app) {
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
	public ChecksTM validaPrevNext(LocationArticle locationArt) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 5;
		boolean isVisiblePrevLink = pageFicha.getSecDataProduct().isVisiblePrevNextUntil(ProductNav.PREV, seconds);
		if (locationArt.isFirstInGalery()) {
			checks.add(
					"No es visible el link <b>Prev</b> (lo esperamos hasta " + seconds + " segundos)",
					!isVisiblePrevLink, State.Warn);
		} else {
			checks.add(
					"Sí es visible el link <b>Prev</b> (lo esperamos hasta " + seconds + " segundos)",
					isVisiblePrevLink, State.Warn);
		}
		if (app==AppEcom.outlet || channel==Channel.desktop) {
			checks.add(
					"Es visible el link <b>Next</b>",
					pageFicha.getSecDataProduct().isVisiblePrevNextUntil(ProductNav.NEXT, 0), State.Warn);
		}
		return checks;
	}

	@Step (
		description="Seleccionamos el link #{productNav}</b>",
		expected="Aparece una página de ficha correcta")
	public void selectLinkNavigation(ProductNav productNav, String refProductOrigin) {
		pageFicha.getSecDataProduct().selectLinkNavigation(productNav);
		if (productNav==ProductNav.PREV) {
			checkIsFichaArtDisponible(refProductOrigin, 3);
		}

		if (productNav==ProductNav.NEXT) {
			LocationArticle location2onArticle = LocationArticle.getInstanceInCatalog(2);
			validaPrevNext(location2onArticle);
		}
	}

	//------------------------------------------------------------------------
	//Específic Ficha Old

	@Validation (
		description="Existe más de una imagen de carrusel a la izquierda de la imagen principal",
		level=State.Warn)
	public boolean validaExistsImgsCarruselIzqFichaOld() {
		return (((PageFichaDevice)pageFicha).getNumImgsCarruselIzq() >= 2);
	}

	@Step (
		description="Seleccionar la #{numImagen}a imagen del carrusel izquierdo",
		expected="La imagen se carga aumentada en la imagen central")
	public void selectImgCarruselIzqFichaOld(int numImagen) {
		String pngImagenCarrusel = ((PageFichaDevice)pageFicha).getSrcImgCarruselIzq(numImagen);
		((PageFichaDevice)pageFicha).clickImgCarruselIzq(numImagen);

		//Validaciones
		checkImgCentralIsAssociatedToCarruselSelect(pngImagenCarrusel);
	}

	@Validation (
		description="La imagen central se corresponde con la imagen del carrusel seleccionada (<b>#{pngImagenCarrusel}</b>)",
		level=State.Defect)
	private boolean checkImgCentralIsAssociatedToCarruselSelect(String pngImagenCarrusel) {
		return (((PageFichaDevice)pageFicha).srcImagenCentralCorrespondsToImgCarrusel(pngImagenCarrusel));
	}

	@Step (
		description="Seleccionar la imagen/ficha central",
		expected="Se produce un zoom sobre la imagen")
	public void selectImagenCentralFichaOld() {
		String pngImgCentralOriginal = ((PageFichaDevice)pageFicha).getSrcImagenCentral();
		((PageFichaDevice)pageFicha).clickImagenFichaCentral();
		checkImgCentralAfterZoom(pngImgCentralOriginal);
	}

	@Validation
	public ChecksTM checkImgCentralAfterZoom(String pngImgCentralOriginal) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
				"Se aplica un Zoom sobre la imagen central",
				((PageFichaDevice)pageFicha).isVisibleFichaConZoom(), State.Defect);

		checks.add(
				"La imagen central con Zoom sigue conteniendo la imagen original: " + pngImgCentralOriginal,
				((PageFichaDevice)pageFicha).srcImagenCentralConZoomContains(pngImgCentralOriginal), State.Defect);

		return checks;
	}

	@Step (
		description="Seleccionar el aspa para cerrar la imagen central con Zoom",
		expected="La imagen con Zoom desaparece")
	public void closeZoomImageCentralDevice() {
		((PageFichaDevice)pageFicha).closeZoomImageCentralDevice();
	}

	@Validation (
		description="La imagen central se corresponde con la imagen del carrusel seleccionada (<b>#{pngImagenCarrusel}</b>)",
		level=State.Defect)
	private boolean checkZoomImageCentralDissapeared() {
		return !((PageFichaDevice)pageFicha).isVisibleFichaConZoom();
	}

	@Validation
	public ChecksTM validaBreadCrumbFicha(String urlGaleryOrigin) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Existen el bloque correspondiente a las <b>BreadCrumb</b>",
			new SecDetalleProduct().isVisibleBreadcrumbs(0), State.Defect);

		String urlGaleryBC = new SecDetalleProduct().getUrlItemBreadCrumb(ItemBreadcrumb.GALERIA);
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