package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageErrorBusqueda;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFichaArtOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBreadcrumbFichaOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.Slider;
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
    
    public PageFichaArtStpV(AppEcom appE, Channel channel) {
        this.driver = TestCaseData.getdFTest().driver;
        this.channel = channel;
        this.app = appE;
        this.pageFicha = PageFicha.newInstance(appE, channel, driver);
    }
    
    public PageFicha getFicha() {
        return this.pageFicha;
    }
    
    public void validateIsFichaAccordingTypeProduct(ArticleStock articulo) throws Exception {
        switch (articulo.getType()) {
        case articlesNotExistent:
            validateIsFichaArtNoDisponible(articulo.getReference());
            break;
        case articlesWithoutStock:
        	validateIsArticleNotAvailable(articulo);
            break;
        case articlesWithMoreOneColour:
        case articlesWithTotalLook: 
        case articlesOnlyInThisStore:
        default:            
            validateIsFichaArtDisponible(articulo.getReference(), 3);
        }
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Validation (
    	description=
    		"Aparece la página correspondiente a la ficha del artículo #{refArticulo}" + 
    		" (La esperamos hasta #{maxSecondsWait} segundos)")
    public boolean validateIsFichaArtDisponible(String refArticulo, int maxSecondsWait) { 
    	return (pageFicha.isFichaArticuloUntil(refArticulo, maxSecondsWait));
    }
    
    @Validation
    public ChecksResult validateIsFichaArtNoDisponible(String refArticulo) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la página de resultado de una búsqueda KO<br>",
			PageErrorBusqueda.isPage(driver), State.Warn);    	
	 	validations.add(
			"En el texto de resultado de la búsqueda aparece la referencia " + refArticulo,
			PageErrorBusqueda.isCabeceraResBusqueda(driver, refArticulo), State.Warn);    		 	
    	return validations;
    }
    
    @Validation
    public ChecksResult validateIsArticleNotAvailable(ArticleStock article) {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
	 	validations.add(
			"Aparece la página correspondiente a la ficha del artículo " + article.getReference() + 
			" (la esperamos hasta " + maxSecondsWait + " segundos)<br>",
			pageFicha.isFichaArticuloUntil(article.getReference(), maxSecondsWait), State.Defect);   
	 	validations.add(
			"No está disponible La talla <b>" + article.getSize() + "</b> del color <b>" + article.getColourCode() + "</b>",
			!pageFicha.secDataProduct.isTallaAvailable(article.getSize(), pageFicha.getTypeFicha(), driver), State.Warn); 
	 	return validations;
    }        
    
    @Validation
    public ChecksResult validateIsFichaArtAlgunoColorNoDisponible(String refArticulo) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la página correspondiente a la ficha del artículo " + refArticulo + "<br>",
			pageFicha.isFichaArticuloUntil(refArticulo, 0), State.Defect); 
	 	validations.add(
			"Aparece algún color no disponible",
			pageFicha.secDataProduct.isElementInState(ColorType.Unavailable, StateElem.Present, driver), State.Defect); 
	 	return validations;
    }
    
    @Validation (
    	description="Aparece la página de Ficha",
    	level=State.Warn)
    public boolean validateIsFichaCualquierArticulo() {
        return (pageFicha.isPageUntil(0));  
    }
    
    @Validation
    public ChecksResult validaDetallesProducto(DataFichaArt datosArticulo) {
    	ChecksResult validations = ChecksResult.getNew();
        if (datosArticulo.availableReferencia()) {
            int maxSecondsWait = 3;
		 	validations.add(
				"Aparece la página con los datos de la ficha del producto " + datosArticulo.getReferencia() +
				"(la esperamos hasta " + maxSecondsWait + " segundos)<br>",
				pageFicha.isFichaArticuloUntil(datosArticulo.getReferencia(), maxSecondsWait), State.Defect);
        }
            
        if (datosArticulo.availableNombre()) {
        	String nombreArtFicha = pageFicha.secDataProduct.getTituloArt(channel, driver).trim();
    	 	validations.add(
				"Como nombre del artículo aparece el seleccionado: " + datosArticulo.getNombre(),
				datosArticulo.getNombre().toLowerCase().compareTo(nombreArtFicha.toLowerCase())==0, State.Warn); 
        }
        
        return validations;
    }

    public void selectColorAndSaveData(ArticuloScreen articulo) throws Exception {
        selectColor(articulo.getCodigoColor());
        articulo.setColor(pageFicha.secDataProduct.getNombreColorSelected(channel, driver));
    }
    
    @Step (
    	description="Seleccionar el color con código <b>#{codigoColor}</b>", 
        expected="Se muestra la ficha correspondiente al color seleccionado")
    public void selectColor(String codigoColor) throws Exception {
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
    
    public void selectTallaAndSaveData(ArticuloScreen articulo) throws Exception {
        selectTalla(articulo.getTallaNum());
        articulo.setTallaAlf(pageFicha.getTallaAlfSelected());
        articulo.setTallaNum(pageFicha.getTallaNumSelected());
    }
    
    public void selectTalla(int positionTalla) throws Exception {
    	String tallaCodNum = pageFicha.getTallaCodNum(positionTalla);
    	selectTalla(tallaCodNum);
	}
    
    @Step (
    	description="Seleccionar la talla con código <b>#{tallaCodNum}</b> (previamente, si está abierta, cerramos la capa de la bolsa)", 
        expected="Se cambia la talla correctamente")
    public void selectTalla(String tallaCodNum) throws Exception {
    	SecBolsa.setBolsaToStateIfNotYet(StateBolsa.Closed, channel, app, driver);
        pageFicha.selectTallaByValue(tallaCodNum);
        checkTallaSelected(tallaCodNum);
    }
    
    @Validation (
    	description="Queda seleccionada la talla <b>#{tallaCodNum}<b>",
    	level=State.Defect)
    private boolean checkTallaSelected(String tallaCodNum) {
        String tallaSelected = pageFicha.getTallaNumSelected(); 
        return (tallaSelected.compareTo(tallaCodNum)==0);
    }
    
    /**
     * Precondición: estamos en una ficha correspondiente a un artículo con algún color no disponible 
     * Selección de un color no disponible + posterior selección de una talla (que necesariamente ha de estar no disponible)
     */
    public void selectColorAndTallaNoDisponible(ArticleStock artNoDisp) throws Exception {
    	selectColorWithTallaNoDisp(artNoDisp);
    	selectTallaNoDisp(artNoDisp);
    }
    
    @Step (
    	description="Seleccionar el color <b>#{artNoDisp.getColourCode()}<b>", 
	    expected="La talla #{artNoDisp.getSize()} no está disponible")
    public void selectColorWithTallaNoDisp(ArticleStock artNoDisp) throws Exception {
    	pageFicha.secDataProduct.selectColorWaitingForAvailability(artNoDisp.getColourCode(), driver);
    }
    
    @Step (
    	description="Seleccionar la talla <b>#{artNoDisp.getSize()} </b>", 
	    expected="Aparece una capa de introducción email para aviso")
    public void selectTallaNoDisp(ArticleStock artNoDisp) {
	    pageFicha.secDataProduct.selectTallaByValue(artNoDisp.getSize(), pageFicha.getTypeFicha(), driver);
	    
	    //Validaciones
	    checkAppearsCapaAvisame();
    }
    
    @Validation
    public ChecksResult checkAppearsCapaAvisame() {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"No aparece el botón \"COMPRAR\"<br>",
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
    public void selectAnadirALaBolsaStep() throws Exception {
    	pageFicha.clickAnadirBolsaButtonAndWait();
    }
    
    /**
     * Selección del botón "Añadir a la bolsa" en un contexto en el que previamente NO se ha seleccionado una talla
     * @return si el artículo seleccionado tenía talla única
     */
    public boolean selectAnadirALaBolsaTallaPrevNoSelected() throws Exception {
        //Step
    	selectAnadirALaBolsaStep();
        
        //Validations
        boolean isTallaUnica = pageFicha.isTallaUnica();
        TypeFicha typeFichaAct = pageFicha.getTypeFicha();
        checkAvisoTallaUnica(isTallaUnica, typeFichaAct);
        if (!isTallaUnica && typeFichaAct==TypeFicha.New) {
        	checkListaTallasVisible();
        }
            
        return isTallaUnica;
    }
    
    @Validation
    public ChecksResult checkAvisoTallaUnica(boolean isTallaUnica, TypeFicha typeFichaAct) {
    	ChecksResult validations = ChecksResult.getNew();
    	boolean isVisibleAviso = pageFicha.secDataProduct.isVisibleAvisoSeleccionTalla(driver);
    	if (isTallaUnica || typeFichaAct==TypeFicha.New) {
		 	validations.add(
		 		"NO aparece un aviso indicando que hay que seleccionar la talla",
		 		!isVisibleAviso, State.Defect);
    	}
    	else {
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
        //Step, Validation
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

        //Validaciones
        checkCapaAltaFavoritos();
        validateVisibleButtonFavoritos(ActionFavButton.Remove);
    }
    
    @Validation
    private ChecksResult checkCapaAltaFavoritos() {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait1 = 3;
	 	validations.add(
			"Aparece una capa superior de \"Añadiendo artículo a favoritos...\" (lo esperamos hasta " + maxSecondsWait1 + " segundos)<br>",
			pageFicha.isVisibleDivAnadiendoAFavoritosUntil(maxSecondsWait1), State.Info);
        int maxSecondsWait2 = 3;
	 	validations.add(
			"La capa superior acaba desapareciendo (lo esperamos hasta " + maxSecondsWait2 + " segundos)",
			pageFicha.isInvisibleDivAnadiendoAFavoritosUntil(maxSecondsWait2), State.Warn);
    	return validations;
    }
    
    @Step (
    	description="Seleccionar el botón <b>\"Eliminar de Favoritos\"</b>", 
        expected="El artículo se elimina de Favoritos")
    public void selectRemoveFromFavoritos() throws Exception {
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
    public void selectBuscarEnTiendaButton() throws Exception {
    	TestCaseData.getDatosCurrentStep().replaceInDescription(tagNameLink, pageFicha.getNameLinkBuscarEnTienda());
        pageFicha.selectBuscarEnTiendaLink();             

        //Validaciones
        ModalBuscadorTiendasStpV.validaBusquedaConResultados(driver);
    }
    
    @Step (
    	description="Si está visible, Seleccionar el link \"<b>Guía de tallas</b>\"", 
        expected="Aparece el Fit Finder")
    public void selectGuiaDeTallas() throws Exception {
    	boolean isVisible = pageFicha.secDataProduct.selectGuiaDeTallasIfVisible(driver);              
        if (isVisible) {
            secFitFinder.validateIsOkAndClose(driver);
        }
    }
    
    public void validateSliderIfExists(Slider typeSlider) {
    	boolean isVisibleSlider = checkSliderVisible(typeSlider);
        if (isVisibleSlider) {
        	checkNumArticlesSlider(1, typeSlider);
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
    public ChecksResult validaPrevNext(LocationArticle locationArt, DataCtxShop dCtxSh) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
    	boolean isVisiblePrevLink = pageFicha.secDataProduct.isVisiblePrevNextUntil(ProductNav.Prev, maxSecondsWait, driver);
        if (locationArt.isFirstInGalery()) {
		 	validations.add(
		 		"No es visible el link <b>Prev</b> (lo esperamos hasta " + maxSecondsWait + " segundos)<br>",
		 		!isVisiblePrevLink, State.Warn);
        }
        else {
		 	validations.add(
		 		"Sí es visible el link <b>Prev</b> (lo esperamos hasta " + maxSecondsWait + " segundos)<br>",
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
    public void selectLinkNavigation(ProductNav productNav, DataCtxShop dCtxSh, String refProductOrigin) 
    throws Exception {
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
    public void selectImagenCentralFichaOld() throws Exception {
        String pngImgCentralOriginal = ((PageFichaArtOld)pageFicha).getSrcImagenCentral();
        ((PageFichaArtOld)pageFicha).clickImagenFichaCentral();       
                    
        //Validaciones
        checkImgCentralAfterZoom(pngImgCentralOriginal);
    }    
    
    @Validation
    public ChecksResult checkImgCentralAfterZoom(String pngImgCentralOriginal) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
	 		"Se aplica un Zoom sobre la imagen central<br>",
	 		((PageFichaArtOld)pageFicha).isVisibleFichaConZoom(), State.Defect);
	 	validations.add(
	 		"La imagen central con Zoom sigue conteniendo la imagen original: " + pngImgCentralOriginal,
	 		((PageFichaArtOld)pageFicha).srcImagenCentralConZoomContains(pngImgCentralOriginal), State.Defect);
	 	return validations;
    }
    
    @Validation
    public ChecksResult validaBreadCrumbFichaOld(String urlGaleryOrigin) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
	 		"Existen el bloque correspondiente a las <b>BreadCrumb</b><br>",
	 		SecBreadcrumbFichaOld.isVisibleBreadCrumb(driver), State.Defect);    	
	 	String urlGaleryBC = SecBreadcrumbFichaOld.getUrlItemBreadCrumb(ItemBCrumb.Galery, driver);
	 	validations.add(
	 		"El link correspondiente a la Galería del artículo linca a la URL " + urlGaleryOrigin,
	 		urlGaleryOrigin.contains(urlGaleryBC), State.Warn); 
	 	return validations;
    }
}