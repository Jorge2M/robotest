package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.*;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew.ActionFavButton;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBreadcrumbFichaOld.ItemBCrumb;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDataProduct.ColorType;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDataProduct.ProductNav;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalBuscadorTiendasStpV;

import java.util.ArrayList;

@SuppressWarnings({"static-access"})
public class PageFichaArtStpV {
    
    WebDriver driver;
    Channel channel;
    AppEcom app;
    PageFicha pageFicha;
    
    public static SecProductDescrOldStpV secProductDescOld;
    public static SecBolsaButtonAndLinksNewStpV secBolsaButtonAndLinksNew;
    public static ModEnvioYdevolNewStpV modEnvioYdevol;
    public static SecFotosNewStpV secFotosNew;
    public static SecFitFinderStpV secFitFinder;
    public static SecTotalLookStpV secTotalLook;
    
    public PageFichaArtStpV(AppEcom appE, Channel channel) {
        this.driver = TestMaker.getDriverTestCase();
        this.channel = channel;
        this.app = appE;
        this.pageFicha = PageFicha.newInstance(channel, appE, driver);
    }
    
    public PageFicha getFicha() {
        return this.pageFicha;
    }
    
    public void validateIsFichaAccordingTypeProduct(Garment product) throws Exception {            
        validateIsFichaArtDisponible(product.getGarmentId(), 3);
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
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
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la página correspondiente a la ficha del artículo " + refArticulo,
			pageFicha.isFichaArticuloUntil(refArticulo, 0), State.Defect); 
	 	validations.add(
			"Aparece algún color no disponible",
			state(Present, ColorType.Unavailable.getBy(), driver).check(), State.Defect); 
	 	return validations;
    }
    
    @Validation (
    	description="Aparece la página de Ficha",
    	level=State.Warn)
    public boolean validateIsFichaCualquierArticulo() {
        return (pageFicha.isPageUntil(0));  
    }
    
    @Validation
    public ChecksTM validaDetallesProducto(DataFichaArt datosArticulo) {
    	ChecksTM validations = ChecksTM.getNew();
        if (datosArticulo.availableReferencia()) {
            int maxSeconds = 3;
		 	validations.add(
				"Aparece la página con los datos de la ficha del producto " + datosArticulo.getReferencia() +
				"(la esperamos hasta " + maxSeconds + " segundos)",
				pageFicha.isFichaArticuloUntil(datosArticulo.getReferencia(), maxSeconds), State.Defect);
        }
            
        if (datosArticulo.availableNombre()) {
        	String nombreArtFicha = pageFicha.secDataProduct.getTituloArt(channel, driver).trim();
    	 	validations.add(
				"Como nombre del artículo aparece el seleccionado: " + datosArticulo.getNombre(),
				datosArticulo.getNombre().toLowerCase().compareTo(nombreArtFicha.toLowerCase())==0, State.Warn); 
        }
        
        return validations;
    }

    public void selectColorAndSaveData(ArticuloScreen articulo) {
        selectColor(articulo.getCodigoColor());
        articulo.setColorName(pageFicha.secDataProduct.getNombreColorSelected(channel, driver));
    }
    
    @Step (
    	description="Seleccionar el color con código <b>#{codigoColor}</b>", 
        expected="Se muestra la ficha correspondiente al color seleccionado")
    public void selectColor(String codigoColor) {
        if (pageFicha.secDataProduct.isClickableColor(codigoColor, driver)) {
            pageFicha.secDataProduct.selectColorWaitingForAvailability(codigoColor, driver);
        }

        checkIsSelectedColor(codigoColor);
    }
    
    @Validation (
    	description="Está seleccionado el color con código <b>#{codigoColor}<b>",
    	level=State.Defect)
    private boolean checkIsSelectedColor(String codigoColor) {
        String codigoColorPage = pageFicha.secDataProduct.getCodeColor(ColorType.Selected, driver); 
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
    	SecBolsa.setBolsaToStateIfNotYet(StateBolsa.Closed, channel, app, driver);
        pageFicha.selectTallaByValue(talla);
        checkTallaSelected(talla);
    }

	@Validation (
		description="Queda seleccionada la talla <b>#{talla.name()}<b>",
		level=State.Defect)
	private boolean checkTallaSelected(Talla talla) {
		Talla tallaSelected = pageFicha.getTallaSelected(); 
		return (tallaSelected==talla);
	}

    @Step (
    	description="Seleccionar la talla <b>#{talla.name()} </b>", 
	    expected="Aparece una capa de introducción email para aviso")
    public void selectTallaNoDisp(Talla talla) {
	    pageFicha.secDataProduct.selectTallaByValue(talla, pageFicha.getTypeFicha(), driver);
	    checkAppearsCapaAvisame();
    }
    
    @Validation
    public ChecksTM checkAppearsCapaAvisame() {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"No aparece el botón \"COMPRAR\"",
			!SecBolsa.isVisibleBotonComprar(Channel.desktop, driver), State.Defect);
	 	boolean isVisibleAvisame = pageFicha.secDataProduct.isVisibleCapaAvisame(driver);
	 	validations.add(
			"Aparece la capa de introducción de avísame",
			isVisibleAvisame, State.Defect);
	 	return validations;
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
        if (!isTallaUnica && typeFichaAct==TypeFicha.New) {
        	checkListaTallasVisible();
        }
            
        return isTallaUnica;
    }
    
    @Validation
    public ChecksTM checkAvisoTallaUnica(boolean isTallaUnica, TypeFicha typeFichaAct) {
    	ChecksTM validations = ChecksTM.getNew();
    	boolean isVisibleAviso = pageFicha.secDataProduct.isVisibleAvisoSeleccionTalla(driver);
    	if (isTallaUnica || typeFichaAct==TypeFicha.New) {
		 	validations.add(
		 		"NO aparece un aviso indicando que hay que seleccionar la talla",
		 		!isVisibleAviso, State.Defect);
    	} else {
		 	validations.add(
		 		"SÍ aparece un aviso indicando que hay que seleccionar la talla",
		 		isVisibleAviso, State.Defect);
    	}
    	return validations;
    }
    
    @Validation (
    	description="Se hace visible la lista de tallas",
    	level=State.Warn)
    public boolean checkListaTallasVisible() {
    	return (pageFicha.secDataProduct.secSelTallasNew.isVisibleListTallasForSelectUntil(0, driver));
    }
    
    /**
     * Selección del botón "Añadir a la bolsa" en un contexto en el que previamente SÍ se ha seleccionado una talla
     */
    public void selectAnadirALaBolsaTallaPrevSiSelected(ArticuloScreen articulo, DataCtxShop dCtxSh) 
    throws Exception {
        selectAnadirALaBolsaStep();
        DataBag dataBag = new DataBag();
        dataBag.addArticulo(articulo);
        SecBolsaStpV.validaAltaArtBolsa(dataBag, dCtxSh.channel, dCtxSh.appE, driver);
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
		validateVisibleButtonFavoritos(ActionFavButton.Remove);
	}

    @Step (
    	description="Cambiar de color dentro de la misma ficha volviendo al color/talla originales",
        expected="El articulo es cambiado de color.")
    public void changeColorGarment() {
        ArticuloScreen articulo = pageFicha.getArticuloObject();
        ArrayList<String> colors = SecDataProduct.getColorsGarment(driver);
        String codeColor = colors.get(0);
        SecDataProduct.selectColor(codeColor, driver);

        validateNotVisibleButtonFavoritos(ActionFavButton.Add);

        SecDataProduct.selectColor(articulo.getCodigoColor(), driver);
        SecDataProduct.selectTallaByValue(articulo.getTalla(), pageFicha.getTypeFicha(), driver);

    }

    @Validation (
       description="No aparece el icono de favorito marcado al cambiar de color",
       level=State.Defect)
    public boolean validateNotVisibleButtonFavoritos(ActionFavButton buttonType) {
        switch (buttonType) {
            case Remove:
                return (pageFicha.isVisibleButtonElimFavoritos());
            case Add:
            default:
                return (pageFicha.isVisibleButtonAnadirFavoritos());
        }
    }
    
    @Validation
    private ChecksTM checkCapaAltaFavoritos() {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds1 = 3;
	 	validations.add(
			"Aparece una capa superior de \"Añadiendo artículo a favoritos...\" (lo esperamos hasta " + maxSeconds1 + " segundos)",
			pageFicha.isVisibleDivAnadiendoAFavoritosUntil(maxSeconds1), State.Info);
        int maxSeconds2 = 3;
	 	validations.add(
			"La capa superior acaba desapareciendo (lo esperamos hasta " + maxSeconds2 + " segundos)",
			pageFicha.isInvisibleDivAnadiendoAFavoritosUntil(maxSeconds2), State.Warn);
    	return validations;
    }

	@Step (
		description="Seleccionar el botón <b>\"Eliminar de Favoritos\"</b>", 
		expected="El artículo se elimina de Favoritos")
	public void selectRemoveFromFavoritos() {
		pageFicha.selectRemoveFromFavoritosButton();
		validateVisibleButtonFavoritos(ActionFavButton.Add);
	}

    @Validation (
    	description="Aparece el botón de #{buttonType} a Favoritos",
    	level=State.Defect)
    public boolean validateVisibleButtonFavoritos(ActionFavButton buttonType) {
        switch (buttonType) {
        case Remove:
            return (pageFicha.isVisibleButtonElimFavoritos());
        case Add:
        default:                
            return (pageFicha.isVisibleButtonAnadirFavoritos());
        }
    }
    
    final String tagNameLink = "@TagNameLink";
    @Step (
    	description="Seleccionar <b>" + tagNameLink + "</b>", 
        expected="Aparece un resultado de la búsqueda correcta")
    public void selectBuscarEnTiendaButton() {
    	TestMaker.getCurrentStepInExecution().replaceInDescription(tagNameLink, pageFicha.getNameLinkBuscarEnTienda());
        pageFicha.selectBuscarEnTiendaLink();
        ModalBuscadorTiendasStpV.validaBusquedaConResultados(driver);
    }
    
    @Step (
    	description="Si está visible, Seleccionar el link \"<b>Guía de tallas</b>\"", 
        expected="Aparece la página asociada a la guía de tallas")
    public void selectGuiaDeTallas(AppEcom app) throws Exception {
    	boolean isVisibleLink = pageFicha.secDataProduct.selectGuiaDeTallasIfVisible(driver);           
    	if (isVisibleLink) {
	    	switch (app) {
	    	case outlet:
	    		PageComoMedirmeStpV pageComoMedirmeStpV = PageComoMedirmeStpV.getNew(driver);
	    		pageComoMedirmeStpV.isPageInNewTab();
	    		break;
	    	case shop:
	    	default:
	        	secFitFinder.validateIsOkAndClose(driver);
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
    	avoidEvidences=true)
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
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
    	boolean isVisiblePrevLink = pageFicha.secDataProduct.isVisiblePrevNextUntil(ProductNav.Prev, maxSeconds, driver);
        if (locationArt.isFirstInGalery()) {
		 	validations.add(
		 		"No es visible el link <b>Prev</b> (lo esperamos hasta " + maxSeconds + " segundos)",
		 		!isVisiblePrevLink, State.Warn);
        } else {
		 	validations.add(
		 		"Sí es visible el link <b>Prev</b> (lo esperamos hasta " + maxSeconds + " segundos)",
		 		isVisiblePrevLink, State.Warn);
        }
        if (dCtxSh.appE==AppEcom.outlet || dCtxSh.channel==Channel.desktop) {
		 	validations.add(
		 		"Es visible el link <b>Next</b>",
		 		pageFicha.secDataProduct.isVisiblePrevNextUntil(ProductNav.Next, 0, driver), State.Warn);
        }
        
        return validations;
    }
    
    @Step (
		description="Seleccionamos el link #{productNav}</b>", 
        expected="Aparece una página de ficha correcta")
    public void selectLinkNavigation(ProductNav productNav, DataCtxShop dCtxSh, String refProductOrigin) {
    	pageFicha.secDataProduct.selectLinkNavigation(productNav, driver);
        if (productNav==ProductNav.Prev) {
            validateIsFichaArtDisponible(refProductOrigin, 3);
        }
        
        if (productNav==ProductNav.Next) {
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
                    
        //Validaciones
        checkImgCentralAfterZoom(pngImgCentralOriginal);
    }    
    
    @Validation
    public ChecksTM checkImgCentralAfterZoom(String pngImgCentralOriginal) {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
	 		"Se aplica un Zoom sobre la imagen central",
	 		((PageFichaArtOld)pageFicha).isVisibleFichaConZoom(), State.Defect);
	 	validations.add(
	 		"La imagen central con Zoom sigue conteniendo la imagen original: " + pngImgCentralOriginal,
	 		((PageFichaArtOld)pageFicha).srcImagenCentralConZoomContains(pngImgCentralOriginal), State.Defect);
	 	return validations;
    }
    
    @Validation
    public ChecksTM validaBreadCrumbFichaOld(String urlGaleryOrigin) {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
	 		"Existen el bloque correspondiente a las <b>BreadCrumb</b>",
	 		SecBreadcrumbFichaOld.isVisibleBreadCrumb(driver), State.Defect);    	
	 	String urlGaleryBC = SecBreadcrumbFichaOld.getUrlItemBreadCrumb(ItemBCrumb.Galery, driver);
	 	validations.add(
	 		"El link correspondiente a la Galería del artículo linca a la URL " + urlGaleryOrigin,
	 		urlGaleryOrigin.contains(urlGaleryBC), State.Warn); 
	 	return validations;
    }
}