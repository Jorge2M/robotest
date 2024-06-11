package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ModCompartirNew;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ModCompartirNew.IconSocial;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders.Slider;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.PageFichaDeviceNoGenesis;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ActionFavButton;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct.ItemBreadcrumb;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecProductDescrDevice.TypePanel;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.pageobject.utils.DataFichaArt;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import java.util.List;

import static com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType.*;
import static com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct.ItemBreadcrumb.GALERIA;
import static com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct.ItemBreadcrumb.LINEA;
import static com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct.ItemBreadcrumb.SUBGALERIA;
import static com.mng.robotest.tests.domains.ficha.pageobjects.commons.ActionFavButton.*;
import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class FichaSteps extends StepBase {

	private final PageFicha pageFicha = PageFicha.make(channel, app, dataTest.getPais());
	private final SecBolsa secBolsa = new SecBolsa();
	private final SecProductDescrDeviceSteps secProductDescOldSteps = new SecProductDescrDeviceSteps();

	public PageFicha getFicha() {
		return this.pageFicha;
	}

	public void checkIsFichaAccordingTypeProduct(Article article) {
		checkIsFichaArtDisponible(article.getArticleId(), 5);
		checksDefault();
		checksGeneric()
			.imgsBroken()
			.netTraffic().execute();
	}

	@Validation (description=
		"Aparece la página correspondiente a la ficha del artículo #{refArticulo} " + SECONDS_WAIT)
	public boolean checkIsFichaArtDisponible(String refArticulo, int seconds) {
		return pageFicha.isFichaArticuloUntil(refArticulo, seconds);
	}

	@Validation
	public ChecksTM checkIsFichaArtAlgunoColorNoDisponible(String refArticulo) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página correspondiente a la ficha del artículo " + refArticulo,
			pageFicha.isFichaArticuloUntil(refArticulo, 0));

		checks.add(
			"Aparece algún color no disponible",
			pageFicha.isPresentColor(UNAVAILABLE));

		return checks;
	}

	public void loadFichaWithRetry(String urlFicha) {
		boolean checkOk = loadFicha(urlFicha, WARN);
		if (!checkOk) {
			loadFicha(urlFicha, DEFECT);
		}
	}
	
	public void loadFicha(String urlFicha) {
		loadFicha(urlFicha, DEFECT);
	}
	
    @Step(
    	description="Cargamos la <b>Ficha</b> <a href='#{urlFicha}'>#{urlFicha}</a>", 
    	expected="Aparece un catálogo con artículos")    		
    private boolean loadFicha(String urlFicha, State state) {
    	driver.get(urlFicha);
    	return checkFichaButtons(10, state).areAllChecksOvercomed();
    }	
	
	@Validation (description="Aparece la página de Ficha " + SECONDS_WAIT)
	public boolean checkIsFicha(int seconds) {
		return pageFicha.isPage(seconds);
	}

    @Validation 
    private ChecksTM checkFichaButtons(int seconds, State state) {
    	var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el botón <b>Añadir a la bolsa</b> o el <b>❤</b> " + getLitSecondsWait(seconds),				
			pageFicha.isVisibleBolsaButton(seconds) || pageFicha.isVisibleButtonAnadirFavoritos(seconds),
			state);
		return checks;
    }
	
	@Validation
	public ChecksTM checkDetallesProducto(DataFichaArt datosArticulo) {
		var checks = ChecksTM.getNew();
		if (datosArticulo.availableReferencia()) {
			int seconds = 5;
			checks.add(
				"Aparece la página con los datos de la ficha del producto " + datosArticulo.getReferencia() +
				getLitSecondsWait(seconds),
				pageFicha.isFichaArticuloUntil(datosArticulo.getReferencia(), seconds));
		}

		if (datosArticulo.availableNombre()) {
			String nombreArtFicha = pageFicha.getTituloArt().trim();
			checks.add(
				"Como nombre del artículo aparece el seleccionado: " + datosArticulo.getNombre(),
				datosArticulo.getNombre().toLowerCase().compareTo(nombreArtFicha.toLowerCase())==0, WARN);
		}

		return checks;
	}

	public void selectColorAndSaveData(ArticuloScreen articulo) {
		selectColor(articulo.getCodigoColor());
		articulo.setColorName(pageFicha.getNombreColorSelected());
	}

	@Step (
		description="Seleccionar el color con código <b>#{codigoColor}</b>",
		expected="Se muestra la ficha correspondiente al color seleccionado")
	public void selectColor(String codigoColor) {
		if (pageFicha.isClickableColor(codigoColor)) {
			pageFicha.selectColorWaitingForAvailability(codigoColor);
		}
		checkIsSelectedColor(codigoColor, 5);
	}

	@Step (
		description="Seleccionar el color en la posición <b>#{posColor}</b>",
		expected="El color se selecciona correctamente")
	public void selectColor(int posColor) {
		pageFicha.clickColor(posColor);
	}

	@Validation (description="Está seleccionado el color con código <b>#{codigoColor}<b> "  + SECONDS_WAIT)
	private boolean checkIsSelectedColor(String codigoColor, int seconds) {
		for (int i=0; i<=seconds; i++) {
			String codigoColorPage = pageFicha.getCodeColor(ColorType.SELECTED);
			if (codigoColorPage.contains(codigoColor)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	public void selectTallaAndSaveData(ArticuloScreen articulo) {
		selectTalla(articulo.getTalla());
		articulo.setTalla(pageFicha.getTallaSelected());
		articulo.setTallaAlf(pageFicha.getTallaSelectedAlf());
	}

	public void selectFirstTallaAvailable() {
		pageFicha.selectFirstTallaAvailable();
		waitLoadPage();
	}

	@Step (
		description="Seleccionar la talla con código <b>#{talla.name()}</b> (previamente, si está abierta, cerramos la capa de la bolsa)",
		expected="Se cambia la talla correctamente")
	public void selectTalla(Talla talla) {
		secBolsa.setBolsaToStateIfNotYet(StateBolsa.CLOSED);
		pageFicha.selectTallaByValue(talla);
		checkTallaSelected(talla, 2);
	}

	@Validation (description="Queda seleccionada la talla <b>#{talla.name()}</b> " + SECONDS_WAIT)
	private boolean checkTallaSelected(Talla talla, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (pageFicha.getTallaSelected()==talla) {
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
		pageFicha.selectTallaByValue(talla);
		checkAppearsCapaAvisame();
	}

	@Validation
	public ChecksTM checkAppearsCapaAvisame() {
		var checks = ChecksTM.getNew();
		checks.add(
			"No aparece el botón \"COMPRAR\"",
			!secBolsa.isVisibleBotonComprar());

		boolean isVisibleAvisame = pageFicha.isVisibleCapaAvisame();
		checks.add(
			"Aparece la capa de introducción de avísame",
			isVisibleAvisame);

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
	
	@Step (
		description="Cerrar el desplegable de tallas",
		expected="Desaparece el desplegable de tallas")
	public void closeTallas() {
		pageFicha.closeTallas();
	}

	@Validation
	public ChecksTM checkAvisoTallaUnica(boolean isTallaUnica) {
		var checks = ChecksTM.getNew();
		boolean isVisibleAviso = pageFicha.isVisibleAvisoSeleccionTalla();
		
		if (isTallaUnica) {
			checks.add(
				"NO aparece un aviso indicando que hay que seleccionar la talla",
				!isVisibleAviso);
		} else {
			checks.add(
				"SÍ aparece un aviso indicando que hay que seleccionar la talla",
				isVisibleAviso);
		}
		return checks;
	}

	@Validation (description="Se hace visible la lista de tallas", level=WARN)
	public boolean checkListaTallasVisible() {
		return pageFicha.isVisibleListTallasForSelectUntil(0);
	}

	/**
	 * Selección del botón "Añadir a la bolsa" en un contexto en el que previamente SÍ se ha seleccionado una talla
	 */
	public void selectAnadirALaBolsaTallaPrevSiSelected(ArticuloScreen articulo) throws Exception {
		selectAnadirALaBolsaStep();
		dataTest.getDataBag().addArticulo(articulo);
		new SecBolsaSteps().checkArticlesAddedToBag();
	}

	@Step (
		description="Seleccionar el botón <b>\"Añadir a Favoritos\"</b>",
		expected="El artículo se añade a Favoritos")
	public void selectAnadirAFavoritos() {
		pageFicha.selectAnadirAFavoritosButton();
		ArticuloScreen articulo = pageFicha.getArticuloObject();
		dataTest.getDataFavoritos().addArticulo(articulo);
		checkVisibleButtonFavoritos(REMOVE);
	}

	@Step (
		description="Cambiar de color dentro de la misma ficha volviendo al color/talla originales",
		expected="El articulo es cambiado de color.")
	public void changeColorGarment() {
		var articulo = pageFicha.getArticuloObject();
		var colors = pageFicha.getColorsGarment();
		String codeColor = getColorNotSelected(colors, articulo);
		pageFicha.selectColor(codeColor);
		checkNotVisibleButtonFavoritos(ADD);

		pageFicha.selectColor(articulo.getCodigoColor());
		pageFicha.selectTallaByValue(articulo.getTalla());
	}

	private String getColorNotSelected(List<String> listColors, ArticuloScreen articulo) {
		for (String color : listColors) {
			if (color.compareTo(articulo.getCodigoColor())!=0) {
				return color;
			}
		}
		return listColors.get(0);
	}

	@Validation (description="No aparece el icono de favorito marcado al cambiar de color")
	public boolean checkNotVisibleButtonFavoritos(ActionFavButton buttonType) {
		if (buttonType == ActionFavButton.REMOVE) {
			return pageFicha.isVisibleButtonElimFavoritos(3);
		}
		return pageFicha.isVisibleButtonAnadirFavoritos(3);
	}

	@Step (
		description="Seleccionar el botón <b>\"Eliminar de Favoritos\"</b>",
		expected="El artículo se elimina de Favoritos")
	public void selectRemoveFromFavoritos() {
		pageFicha.selectRemoveFromFavoritosButton();
		checkVisibleButtonFavoritos(ADD);
	}

	@Validation (description="Aparece el botón de #{buttonType} a Favoritos")
	public boolean checkVisibleButtonFavoritos(ActionFavButton buttonType) {
		switch (buttonType) {
			case REMOVE:
				return (pageFicha.isVisibleButtonElimFavoritos(1));
			case ADD:
			default:
				return (pageFicha.isVisibleButtonAnadirFavoritos(1));
		}
	}

	@Validation (description="Es visible el link de <b>Disponibilidad en Tienda</b>")
	public boolean checkLinkDispTiendaVisible() {
		return pageFicha.isVisibleBuscarEnTiendaLink();
	}
	
	public boolean checkLinkDispTiendaInvisible() {
		//These feature is still in developping fase by Kaliope (11-junio-2024)
		if (!UtilsTest.todayBeforeDate("2024-09-06")) {
			return checkLinkDispTiendaInvisibleOriginal();
		}
		return true;
	}
	
	@Validation (description="Es invisible el link de <b>Disponibilidad en Tienda</b>")
	private boolean checkLinkDispTiendaInvisibleOriginal() {
		return !pageFicha.isVisibleBuscarEnTiendaLink();
	}

	@Step (
		description="Seleccionar la opción <b>Disponibilidad en tienda</b>",
		expected="Aparece un resultado de la búsqueda correcta")
	public void selectBuscarEnTienda() {
		pageFicha.selectBuscarEnTiendaLink();
		new ModalBuscarEnTiendaSteps().checkBusquedaConResultados();
	}

	@Step (
		description="Si está visible, Seleccionar el link \"<b>Guía de tallas</b>\"",
		expected="Aparece la página asociada a la guía de tallas")
	public void selectGuiaDeTallas(AppEcom app) {
		boolean isVisibleLink = pageFicha.selectGuiaDeTallasIfVisible();
		if (isVisibleLink) {
			if (app==AppEcom.outlet) {
				new PageComoMedirmeSteps().isPageInNewTab();
			} else {
				checkGuiaDeTallasModal();
			}
		}
	}

	public void checkSliderIfExists(Slider slider) {
		if (checkSliderVisible(slider)) {
			checkNumArticlesSlider(0, slider);
		}
	}

	@Validation (
		description="Es visible el slider de artículos de tipo <b>#{slider}</b>",
		level=INFO,	store=NONE)
	public boolean checkSliderVisible(Slider slider) {
		return pageFicha.isVisibleSlider(slider);
	}

	@Validation (
		description="El número de artículos del slider de tipo <b>#{typeSlider}</b> es > #{numArtMin}",
		level=WARN)
	public boolean checkNumArticlesSlider(int numArtMin, Slider typeSlider) {
		return (pageFicha.getNumArtVisiblesSlider(typeSlider) > numArtMin);
	}
	
	@Validation
	public ChecksTM checkStickyContentVisible(ArticuloScreen articulo) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Es visible la capa superior de Sticky Content " + getLitSecondsWait(seconds),
			pageFicha.isVisibleStickyContent(seconds));

		checks.add(
			"Es visible la talla seleccionada <b>" + articulo.getTallaAlf() + "</b>",
			pageFicha.isVisibleTallaLabelStickyContent(articulo.getTallaAlf()));
		
		checks.add(
			"Es visible el artículo con referencia <b>" + articulo.getReferencia() + "</b>",
			pageFicha.isVisibleReferenciaStickyContent(articulo.getReferencia()));
		
		checks.add(
			"Es visible el nombre del color seleccionado <b>" + articulo.getColorName() + "</b>",
			pageFicha.isVisibleColorNameStickyContent(articulo.getColorName()));		

		return checks;
	}	
	
	@Validation(description="No es visible la capa superior de Sticky Content " + SECONDS_WAIT)
	public boolean checkStickyContentInvisible(int seconds) {
		return pageFicha.isInvisibleStickyContent(seconds);
	}
	
	@Step (
		description="Seleccionar el link <b>Envío gratis a tienda</b>",
		expected="Aparece el modal con los datos a nivel de envío y devolución")
	public void selectEnvioGratisTienda() {
		pageFicha.selectEnvioGratisTienda();
		isVisibleModalDatosEnvio(2);
	}
	
	@Step (
		description="Seleccionar el link <b>Detalle del producto</b>",
		expected="Se scrolla hasta el apartado de \"Descripción\"")
	public void selectDetalleDelProducto(LineaType lineaType) {
		pageFicha.selectDetalleDelProducto();
		checkScrollToDescription();
		if (!dataTest.getPais().isFichaGenesis(app)) {
			checkBreadCrumbs();
		}
		if (TypePanel.KC_SAFETY.getListApps().contains(app) &&
			(lineaType==LineaType.NINA || lineaType==LineaType.NINO)) {
			checkKcSafety();
		}
	}
	
	@Validation (description="Se scrolla hasta el apartado de \"Descriptión\"")
	private boolean checkScrollToDescription() {
		return pageFicha.isVisibleDescription();
	}
	
	@Validation
	private ChecksTM checkBreadCrumbs() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Figura el bloque de BreadCrumbs",
			pageFicha.isVisibleBreadcrumbs(0), WARN);
	 	checks.add(
			"Es visible el item " + LINEA,
			pageFicha.isVisibleItemBreadCrumb(LINEA), WARN);
	 	checks.add(
			"Es visible el item " + SUBGALERIA,
			pageFicha.isVisibleItemBreadCrumb(SUBGALERIA), WARN);
	 	checks.add(
			"Es visible el item " + GALERIA,
			pageFicha.isVisibleItemBreadCrumb(GALERIA), WARN);
	 	
	 	return checks;
	}
	
	@Validation (description="Aparece el bloque de \"KcSafety\"")
	private boolean checkKcSafety() {
		return pageFicha.isVisibleBlockKcSafety();
	}	
	
	@Step (
		description="Seleccionar el link <b>Compartir</b>",
		expected="Aparece el modal para compartir el enlace")
	public void selectLinkCompartir(String codigoPais) {
		pageFicha.selectLinkCompartir();
		checkAppearsModalShareSocial(codigoPais);
	}
	
	@Validation
	private ChecksTM checkAppearsModalShareSocial(String codigoPais) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
	 		"Aparece el modal para compartir a nivel social " + getLitSecondsWait(seconds),
	 		new ModCompartirNew().isVisibleUntil(seconds));
		
		boolean isPaisChina = (codigoPais.compareTo("720")==0);
		for (IconSocial icon : IconSocial.values()) {
			boolean isVisibleIcon = new ModCompartirNew().isVisibleIcon(icon);
			if (isPaisChina != icon.isSpecificChina()) {
			 	checks.add(
			 		"No es visible el icono de " + icon,
			 		!isVisibleIcon, WARN);
			} else {
			 	checks.add(
			 		"Sí es visible el icono de " + icon,
			 		isVisibleIcon, WARN);
			}
		}

		return checks;
	}	
		
	//------------------------------------------------------------------------
	//Específic Ficha Old

	@Validation (
		description="Existe más de una imagen de carrusel a la izquierda de la imagen principal",
		level=WARN)
	public boolean validaExistsImgsCarruselIzqFichaOld() {
		return (((PageFichaDeviceNoGenesis)pageFicha).getNumImgsCarruselIzq() >= 2);
	}

	@Step (
		description="Seleccionar la #{numImagen}a imagen del carrusel izquierdo",
		expected="La imagen se carga aumentada en la imagen central")
	public void selectImgCarruselIzqFichaOld(int numImagen) {
		String pngImagenCarrusel = ((PageFichaDeviceNoGenesis)pageFicha).getSrcImgCarruselIzq(numImagen);
		((PageFichaDeviceNoGenesis)pageFicha).clickImgCarruselIzq(numImagen);
		checkImgCentralIsAssociatedToCarruselSelect(pngImagenCarrusel);
	}

	@Validation (
		description="La imagen central se corresponde con la imagen del carrusel seleccionada (<b>#{pngImagenCarrusel}</b>)")
	private boolean checkImgCentralIsAssociatedToCarruselSelect(String pngImagenCarrusel) {
		return (((PageFichaDeviceNoGenesis)pageFicha).srcImagenCentralCorrespondsToImgCarrusel(pngImagenCarrusel));
	}

	@Step (
		description="Seleccionar la imagen/ficha central",
		expected="Se produce un zoom sobre la imagen")
	public void selectImagenCentralFichaOld() {
		String pngImgCentralOriginal = ((PageFichaDeviceNoGenesis)pageFicha).getSrcImagenCentral();
		((PageFichaDeviceNoGenesis)pageFicha).clickImagenFichaCentral();
		checkImgCentralAfterZoom(pngImgCentralOriginal);
	}

	@Validation
	public ChecksTM checkImgCentralAfterZoom(String pngImgCentralOriginal) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Se aplica un Zoom sobre la imagen central",
			((PageFichaDeviceNoGenesis)pageFicha).isVisibleFichaConZoom());

		checks.add(
			"La imagen central con Zoom sigue conteniendo la imagen original: " + pngImgCentralOriginal,
			((PageFichaDeviceNoGenesis)pageFicha).srcImagenCentralConZoomContains(pngImgCentralOriginal));

		return checks;
	}

	@Step (
		description="Seleccionar el aspa para cerrar la imagen central con Zoom",
		expected="La imagen con Zoom desaparece")
	public void closeZoomImageCentralDevice() {
		((PageFichaDeviceNoGenesis)pageFicha).closeZoomImageCentralDevice();
	}

	@Validation (
		description="La imagen central se corresponde con la imagen del carrusel seleccionada (<b>#{pngImagenCarrusel}</b>)")
	private boolean checkZoomImageCentralDissapeared() {
		return !((PageFichaDeviceNoGenesis)pageFicha).isVisibleFichaConZoom();
	}

	@Validation
	public ChecksTM checkBreadCrumbFicha(String urlGaleryOrigin) {
		var checks = ChecksTM.getNew();
		
		//TODO activar si en algún momento se añaden las BreadCrumb en la ficha Génesis
		if (!dataTest.getPais().isFichaGenesis(app)) {
			checks.add(
				"Existen el bloque correspondiente a las <b>BreadCrumb</b>",
				new SecDetalleProduct().isVisibleBreadcrumbs(0));
		}

		String urlGaleryBC = new SecDetalleProduct().getUrlItemBreadCrumb(ItemBreadcrumb.GALERIA);
		checks.add(
			"El link correspondiente a la Galería del artículo linca a la URL " + urlGaleryOrigin,
			urlGaleryOrigin.contains(urlGaleryBC), WARN);

		return checks;
	}
	
	@Validation (
		description="Aparece el modal con los datos a nivel de envío y devolución " + SECONDS_WAIT)
	public boolean isVisibleModalDatosEnvio(int seconds) {
		return pageFicha.isVisibleModalDatosEnvio(seconds);
	}

	@Step (
		description="Seleccionar el aspa para cerrar el modal de \"Envío y devolución\"",
		expected="Desaparece el modal")
	public void closeModalDatosEnvio() {
		pageFicha.closeModalDatosEnvio();
		checkIsVisibleModalDatosEnvio();
	}
	
	@Validation (
		description="No es visible el modal con los datos a nivel de envío y devolución",
		level=WARN)
	private boolean checkIsVisibleModalDatosEnvio() {
		return !pageFicha.isVisibleModalDatosEnvio(1);
	}	
	
	@Validation
	public ChecksTM checkGuiaDeTallasModal() {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	  	checks.add(
			"Es visible el Wrapper con la guía de tallas " + getLitSecondsWait(seconds),
			pageFicha.isVisibleGuiaTallas(seconds));
	  	
		pageFicha.closeGuiaTallas();
		return checks;
	}

	public SecProductDescrDeviceSteps getSecProductDescDeviceSteps() {
		return secProductDescOldSteps;
	}

	public boolean checkArticleCustomizable() {
		return checkArticleCustomizable(DEFECT);
	}
	
	public boolean checkArticleCustomizable(State levelError) {
		var checks = checkAreArticleCustomizable(levelError);
		for (var check : checks.getListChecks()) {
			if (!check.isOvercomed()) {
				return false;
			}
		}
		return true;
	}
	
	@Validation
	private ChecksTM checkAreArticleCustomizable(State levelError) {
		var checks = ChecksTM.getNew();
		checks.add(
			"El artículo es personalizable (aparece el link \"Añadir bordado\")",
			pageFicha.isLinkAddBordado(1), levelError);
		return checks;
	}

	@Step(
		description="Seleccionamos el link <b>Añadir bordado</b>",
		expected="Aparece el modal para la personalización de la prenda")
	public void selectLinkPersonalizacion () {
		pageFicha.clickAddBordado();
		new SecModalPersonalizacionSteps().checkModal();
		checksDefault();
	}
	
}