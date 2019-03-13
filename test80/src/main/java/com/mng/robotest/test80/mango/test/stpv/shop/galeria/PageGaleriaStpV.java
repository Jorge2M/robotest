package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.ResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.factoryes.NodoStatus;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.jdbc.dao.RebajasPaisDAO;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.SecFiltros;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable.StateModal;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.ControlTemporada;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeSlider;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.SecBannerHead.TypeLinkInfo;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroDiscount.TypeMenuDiscount;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataScroll;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRef;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRefList;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public class PageGaleriaStpV {

    public static SecSelectorPreciosStpV secSelectorPrecios;
    public static SecCrossSellingStpV secCrossSelling;
    
    public enum TypeActionFav {Marcar, Desmarcar}
    PageGaleria pageGaleria = null;
    DataFmwkTest dFTest = null;
    Channel channel = null;
    AppEcom app = null;
    
    private PageGaleriaStpV(Channel channel, AppEcom app, DataFmwkTest dFTest) throws Exception {
    	this.dFTest = dFTest;
    	this.channel = channel;
    	this.app = app;
    	pageGaleria = PageGaleria.getInstance(channel, app, dFTest.driver);
    }
    
    public static PageGaleriaStpV getInstance(Channel channel, AppEcom app) throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        return (new PageGaleriaStpV(channel, app, dFTest));
    }

    @Step (
    	description="Seleccionamos el artículo #{locationArt} en una pestaña aparte", 
        expected="Aparece la ficha del artículo seleccionado en una pestaña aparte")
    public void selectArticuloEnPestanyaAndBack(LocationArticle locationArt) 
    throws Exception {
        String galeryWindowHandle = dFTest.driver.getWindowHandle();
        DataFichaArt datosArticulo = new DataFichaArt();
        
        //Almacenamos el nombre del artículo y su referencia
        WebElement articulo = pageGaleria.getArticulo(locationArt);
        datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
        datosArticulo.setReferencia(PageGaleria.getRefArticulo(articulo));

        //Seleccionamos el artículo y lo cargamos en una pestaña aparte
        String detailWindowHandle = pageGaleria.openArticuloPestanyaAndGo(articulo, app);
        
        //Validaciones
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(app, channel);
        pageFichaStpV.validaDetallesProducto(datosArticulo);
        
        //Cerramos la pestaña y cambiamos a la ventana padre
        dFTest.driver.switchTo().window(detailWindowHandle);
        dFTest.driver.close();
        dFTest.driver.switchTo().window(galeryWindowHandle);
    }
    
    @Step (
    	description="Seleccionar el artículo #{locationArt}", 
        expected="Aparece la ficha del artículo seleccionado")
    public DataFichaArt selectArticulo(LocationArticle locationArt, DataCtxShop dCtxSh) 
    throws Exception {
        DataFichaArt datosArticulo = new DataFichaArt();
        String urlGaleria = dFTest.driver.getCurrentUrl();
        
        //Almacenamos el nombre del artículo y su referencia
        WebElement articulo = pageGaleria.getArticulo(locationArt);
        datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
        datosArticulo.setReferencia(PageGaleriaDesktop.getRefArticulo(articulo));
                
        //Seleccionar el artículo
        pageGaleria.clickArticulo(articulo);
        
        //Validaciones
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaStpV.validaDetallesProducto(datosArticulo);
        pageFichaStpV.validaPrevNext(locationArt, dCtxSh);

        //Validaciones sección BreadCrumb + Next
        if (dCtxSh.channel==Channel.desktop) {
            if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
                pageFichaStpV.validaBreadCrumbFichaOld(urlGaleria);
            }
        }
        
        return (datosArticulo);
    }
    
    @Step (
    	description="Posicionarse sobre el artículo en la posición <b>#{posArticulo}</b>, esperar que aparezca el link \"Añadir\" y seleccionarlo", 
        expected="Aparece la capa con la información de las tallas")
    public void selectLinkAddArticuloToBagDesktop(int posArticulo)
    throws Exception {
    	((PageGaleriaDesktop)pageGaleria).selectLinkAddArticleToBagDesktop(posArticulo);
        int maxSecondsWait = 1;
        checkIsVisibleCapaInfoTallas(posArticulo, maxSecondsWait);
    }   
    
    @Validation (
    	description="Aparece la capa con la información de las tallas (la esperamos hasta #{maxSecondsWait}",
    	level=State.Warn)
    private boolean checkIsVisibleCapaInfoTallas(int posArticulo, int maxSecondsWait) {
        return (((PageGaleriaDesktop)pageGaleria).isVisibleArticleCapaTallasDesktopUntil(posArticulo, maxSecondsWait));
    }
    
    @Step (
    	description="Del #{posArticulo}o artículo, seleccionamos la #{posTalla}a talla", 
        expected="Se da de alta correctamente el artículo en la bolsa",
        saveHtmlPage=SaveWhen.Always)
    public boolean selectTallaArticuloDesktop(int posArticulo, int posTalla, DataBag dataBag, DataCtxShop dCtxSh) 
    throws Exception {
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
        ArticuloScreen articulo = pageGaleriaDesktop.selectTallaArticle(posArticulo, posTalla);
        boolean notVisibleAvisame = ModalArticleNotAvailableStpV.validateState(1, StateModal.notvisible, dFTest.driver);
        if (notVisibleAvisame) {
            dataBag.addArticulo(articulo);
            SecBolsaStpV.validaAltaArtBolsa(dataBag, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        }
        
        return notVisibleAvisame;
    }
    
    /**
     * Escrollamos hasta llegar a la página indicada en toPage
     * @param toPage indica el número de página en el que nos queremos posicionar. Si es PageGaleria.scrollToLast asumimos que queremos llegar hasta el final del catálogo
     */
    final static String tagIdPage = "@TagIdPage";
    @Step (
    	description="Escrollar hasta posicionarse en la " + tagIdPage + " página", 
        expected="Se escrolla correctamente",
        saveNettraffic=SaveWhen.Always)
    public DataScroll scrollFromFirstPage(DataForScrollStep dataForScroll, DataCtxShop dCtxSh) 
    throws Exception {
        DataScroll datosScroll = null;
        int pageToScroll = dataForScroll.numPageToScroll;
        if (dCtxSh.channel == Channel.movil_web) {
        	pageToScroll = 3;
        }
        
        String idPage = pageToScroll + "a";
        if (pageToScroll>=PageGaleriaDesktop.maxPageToScroll) {
            idPage = "última";
        }
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagIdPage, idPage);
        int numArticulosInicio = pageGaleria.getNumArticulos();
        datosScroll = pageGaleria.scrollToPageFromFirst(pageToScroll, dCtxSh.appE);
        
        checkVisibilityFooter(pageToScroll, dCtxSh.appE, dFTest.driver);
        if (pageToScroll < PageGaleriaDesktop.maxPageToScroll) {
        	checkAreMoreArticlesThatInitially(datosScroll.articulosMostrados, numArticulosInicio);
        }
        if (dataForScroll.ordenacionExpected != FilterOrdenacion.NOordenado) {
        	checkArticlesOrdered(dataForScroll.ordenacionExpected);
        }
        checkNotRepeatedArticles();
        if (dataForScroll.validateArticlesExpected) {
        	checkNumArticlesInScreen(datosScroll.articulosTotalesPagina, dataForScroll.numArticlesExpected);
        }
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = dataForScroll.validaImgBroken;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, dFTest.driver);
        
        datosScroll.datosStep = TestCaseData.getDatosCurrentStep();
        return datosScroll;
    }
    
    @Validation
    private ListResultValidation checkVisibilityFooter(int pageToScroll, AppEcom app, WebDriver driver) throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
        boolean isVisibleFooter = SecFooter.isVisible(app, driver);
        if (pageToScroll>=PageGaleriaDesktop.maxPageToScroll) {
          	validations.add(
        		"Sí aparece el footer",
        		isVisibleFooter, State.Warn);
        }
        return validations;
    }
    
    @Validation (
    	description=
    		"En pantalla aparecen más artículos (#{numArticlesCurrently}) " + 
    		"de los que había inicialmente (#{numArticlesInit})",
    	level=State.Warn)
    private boolean checkAreMoreArticlesThatInitially(int numArticlesCurrently, int numArticlesInit) {
	    return (numArticlesCurrently > numArticlesInit);
    }
    
    @Validation (
    	description="Los artículos aparecen ordenados por #{orderExpected}",
    	level=State.Defect)
    private boolean checkArticlesOrdered(FilterOrdenacion orderExpected) {
	    return (pageGaleria.articlesInOrder(orderExpected));
    }
    
    @Validation
    private ListResultValidation checkNotRepeatedArticles() {
    	ListResultValidation validations = ListResultValidation.getNew();
        ArrayList<NombreYRef> productsRepeated = pageGaleria.searchArticleRepeatedInGallery();
        String producRepeatedWarning = "";
        if (productsRepeated!=null && productsRepeated.size()>0) {
        	producRepeatedWarning+=
            	"<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: " + 
            	"hay " + productsRepeated.size() + " productos repetidos, " + 
            	"por ejemplo el <b>" + productsRepeated.get(0).toString() + "</b>";
        }
      	validations.add(
    		"No aparece ningún artículo repetido" + producRepeatedWarning,
    		productsRepeated==null || productsRepeated.size()==0, State.Defect);
      	
      	return validations;
    }
    
    @Validation (
    	description=
    		"En pantalla aparecen exactamente #{numArticlesInPage} artículos " + 
	        "(están apareciendo #{numArticlesExpected}",
	    level=State.Info)
    private boolean checkNumArticlesInScreen(int numArticlesInPage, int numArticlesExpected) {
	    return (numArticlesInPage==numArticlesExpected);
    }
   
    @Step (
    	description="Seleccionar la ordenación #{typeOrdenacion}", 
        expected="Los artículos se ordenan correctamente")
    public int seleccionaOrdenacionGaleria(FilterOrdenacion typeOrdenacion, String tipoPrendasGaleria, int numArticulosValidar, 
		   								   DataCtxShop dCtxSh) throws Exception {
        SecFiltros secFiltros = SecFiltros.newInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        secFiltros.selecOrdenacionAndReturnNumArticles(typeOrdenacion);    
        
        checkIsVisiblePageWithTitle(tipoPrendasGaleria);
        int numArticulosPant = pageGaleria.getNumArticulos() + pageGaleria.getNumArticulos();
        checkOrderListArticles(typeOrdenacion, numArticulosPant, numArticulosValidar);

        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
       
        return numArticulosPant;
    }
    
    @Validation (
    	description="Aparece una pantalla en la que el title contiene <b>#{tipoPrendasGaleria}",
    	level=State.Warn)
    private boolean checkIsVisiblePageWithTitle(String tipoPrendasGaleria) {
    	return (dFTest.driver.getTitle().toLowerCase().contains(tipoPrendasGaleria));
    }
    
    @Validation
    private ListResultValidation checkOrderListArticles(FilterOrdenacion typeOrdenacion, int numArticulosPant, int numArticulosValidar) {
    	ListResultValidation validations = ListResultValidation.getNew();
      	validations.add(
    		"Aparecen > 1 prendas<br>",
    		numArticulosPant > 1, State.Warn);
	    if (numArticulosValidar>=0) {
	      	validations.add(
        		"Aparecen " + numArticulosValidar + " artículos<br>",
        		numArticulosValidar==numArticulosPant, State.Info);
	    }
      	validations.add(
    		"Los artículos aparecen ordenados por " + typeOrdenacion.name(),
    		pageGaleria.articlesInOrder(typeOrdenacion), State.Warn);
      	
      	return validations;
    }
   
    @Step (
    	description="Volver al 1er artículo de la galería (mediante selección del icono de la flecha Up)", 
        expected="Se visualiza el 1er elemento")
    public void backTo1erArticleMobilStep(DataCtxShop dCtxSh) throws Exception {
        pageGaleria.backTo1erArticulo();
        checkBackTo1ersElementOk(dCtxSh);
    }
    
    @Validation
    private ListResultValidation checkBackTo1ersElementOk(DataCtxShop dCtxSh) throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
      	validations.add(
    		"Es clickable el 1er elemento de la lista<br>",
    		pageGaleria.isClickableArticuloUntil(1, 0), State.Warn);
      	
        SecFiltros secFiltros = SecFiltros.newInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        int maxSecondsWait = 2;
      	validations.add(
    		"Es clickable el bloque de filtros (esperamos hasta " + maxSecondsWait + " segundos)",
    		secFiltros.isClickableFiltroUntil(maxSecondsWait), State.Warn);
      	
      	return validations;
    }

    /**
     * Pasos/Validaciones consistentes en seleccionar un determinado color de un determinado artículo
     * @param posColor posición del color en la lista de colores del artículo
     * @param numArtConColores posición del artículo entre el conjunto de artículos con variedad de colores
     * @return src de la imagen obtenida al ejecutar el click sobre el color
     */
    final static String tagSrcPng2oColor = "@srcPng2oColor";
    final static String tagNombre1erArtic = "@nombre1erArt";
    final static String tagPrecio1erArtic = "@precio1erArt";
    @Step (
    	description=
    		"Seleccionar el #{posColor}o color (" + tagSrcPng2oColor +") no seleccionado del #{numArtConColores}o " + 
    		"artículo con variedad de colores (" + tagNombre1erArtic + ", " + tagPrecio1erArtic +")", 
        expected="Se selecciona el color")
    public String selecColorFromArtGaleriaStep(int numArtConColores, int posColor) 
    throws Exception {
        //En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
        WebdrvWrapp.waitForPageLoaded(dFTest.driver, 2/*waitSeconds*/);
       
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();
        WebElement articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
        datosStep.replaceInDescription(tagNombre1erArtic, pageGaleria.getNombreArticulo(articuloColores));
       
        WebElement colorToClick = pageGaleria.getColorArticulo(articuloColores, false/*selected*/, posColor);
        datosStep.replaceInDescription(tagPrecio1erArtic, pageGaleria.getPrecioArticulo(articuloColores));
       
        String srcImg1erArt = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
        datosStep.replaceInDescription(tagSrcPng2oColor, colorToClick.getAttribute("src"));
       
        WebdrvWrapp.forceClick(dFTest.driver, colorToClick, null);
        Thread.sleep(100);
        
        String srcImgAfterClickColor = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
        checkImageIsModified(srcImg1erArt, srcImgAfterClickColor);
       
        return srcImgAfterClickColor;
    }
    
    @Validation (
    	description="Se modifica la imagen correspondiente al artículo",
    	level=State.Defect)
    private boolean checkImageIsModified(String srcImg1erArt, String srcImgAfterClickColor) {
        return (!srcImgAfterClickColor.contains(srcImg1erArt));        
    }
    
    /**
     * @return src de la imagen obtenida al ejecutar los clicks
     */
    public String clicksSliderArticuloConColores(int numArtConColores, ArrayList<TypeSlider> typeSliderList) 
    throws Exception {
        return (clicksSliderArticuloConColores(numArtConColores, typeSliderList, ""));        
    }
   
    /**
     * @param srcImageExpected el src esperado para la imagen resultante de la secuencia de clicks en el slider. Si tiene valor "" no aplicamos validación
     */
    final static String tagNombreArt = "@TagNombreArt";
    final static String tagSliderList = "@TagSliderList";
    @Step (
    	description=
    		"Clickar la siguiente secuencia de sliders: <b>" + tagSliderList + "</b> del #{numArtConColores}o " + 
    		" artículo con variedad de colores (" + tagNombreArt + "). Previamente realizamos un \"Hover\" sobre dicho artículo", 
        expected="Aparece el artículo original(" + tagNombreArt + ")")
    public String clicksSliderArticuloConColores(int numArtConColores, ArrayList<TypeSlider> typeSliderList, String srcImageExpected) 
    throws Exception {
       if (channel!=Channel.desktop) {
           throw new RuntimeException("Method clickSliderArticuloConColores doesn't support channel " + channel);
       }
       
       String slidersListStr = getStringSliderList(typeSliderList);
       DatosStep datosStep = TestCaseData.getDatosCurrentStep();
       datosStep.replaceInDescription(tagSliderList, slidersListStr);
       
       //En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. 
	   //En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
       WebdrvWrapp.waitForPageLoaded(dFTest.driver, 2);
       PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       WebElement articuloColores = pageGaleriaDesktop.getArticuloConVariedadColoresAndHoverNoDoble(numArtConColores);
       datosStep.replaceInDescription(tagNombreArt, pageGaleria.getNombreArticulo(articuloColores));
       datosStep.replaceInExpected(tagNombreArt, pageGaleria.getNombreArticulo(articuloColores));
       String srcImg1erSlider = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
       pageGaleriaDesktop.clickSliderAfterHoverArticle(articuloColores, typeSliderList);
       
       String srcImg2oSlider = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
       checkImageSliderArticleHasChanged(srcImg1erSlider, srcImg2oSlider);
       if ("".compareTo(srcImageExpected)!=0) {
    	   checkActualImgSliderIsTheExpected(srcImg2oSlider, srcImageExpected);
       }
       return srcImg2oSlider;
   }
    
   @Validation (
	  description="Se modifica la imagen asociada al artículo (<b>antes</b>: #{srcImg1erSlider}, <b>ahora</b>: #{srcImg2oSlider})",
	  level=State.Defect)
   private boolean checkImageSliderArticleHasChanged(String srcImg1erSlider, String srcImg2oSlider) {
       return (srcImg2oSlider.compareTo(srcImg1erSlider)!=0); 
   }
   
   @Validation (
	   description="El src de la imagen <b>ahora</b> (#{srcImgActual}) es la <b>original</b> (#{srcImgOriginalExpected})",
	   level=State.Defect)
   private boolean checkActualImgSliderIsTheExpected(String srcImgActual, String srcImgOriginalExpected) {
       return (srcImgActual.compareTo(srcImgOriginalExpected)==0);
   }
    
   private static String getStringSliderList(ArrayList<TypeSlider> typeSliderList) {
       String listStr = "";
       for (TypeSlider typeSlider : typeSliderList) {
           listStr+=(typeSlider + ", ");
       }
       return listStr;
   }
    
   	final static String tagNumArtConColores = "@TagNumArtConColores";
   	final static String tagNombre1erArt = "@TagNombre1erArt";
   	final static String tagPrecio1erArt = "@TagPrecio1erArt";
    @Step (
    	description="Seleccionar el " + tagNumArtConColores + "o artículo con variedad de colores (" + tagNombre1erArt + " " + tagPrecio1erArt + ")", 
        expected="Aparece el artículo original(" + tagNombre1erArt + " " + tagPrecio1erArt + ")",
        saveNettraffic=SaveWhen.Always)
    public void selecArticuloGaleriaStep(int numArtConColores) throws Exception {
	    WebElement articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
	    String nombre1erArt = pageGaleria.getNombreArticulo(articuloColores);
	    String precio1erArt = pageGaleria.getPrecioArticulo(articuloColores);
	    DatosStep datosStep = TestCaseData.getDatosCurrentStep();
	    datosStep.replaceInDescription(tagNumArtConColores, String.valueOf(numArtConColores));
	    datosStep.replaceInDescription(tagNombre1erArt, nombre1erArt);
	    datosStep.replaceInDescription(tagPrecio1erArt, precio1erArt);
	    
        pageGaleria.clickArticulo(articuloColores);
        int maxSecondsWait = 3;
        checkIsFichaArticle(nombre1erArt, precio1erArt, maxSecondsWait);

        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
        PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, dFTest.driver);        
    }
   
   	@SuppressWarnings("static-access")
    @Validation
    private ListResultValidation checkIsFichaArticle(String nombre1erArt, String precio1erArt, int maxSecondsWait) {
    	ListResultValidation validations = ListResultValidation.getNew();
    	
    	PageFicha pageFicha = PageFicha.newInstance(app, channel, dFTest.driver);
      	validations.add(
    		"Aparece la página de ficha (la esperamos hasta " + maxSecondsWait + " segundos)<br>",
    		pageFicha.isPageUntil(maxSecondsWait), State.Warn);
      	
        String nombreArtFicha = pageFicha.secDataProduct.getTituloArt(channel, dFTest.driver);
        String precioArtFicha = pageFicha.secDataProduct.getPrecioFinalArticulo(dFTest.driver);
      	validations.add(
    		"Aparece el artículo anteriormente seleccionado: <br>\" +\n" + 
    		"   - Nombre " + nombre1erArt + "<br>" + 
    		"   - Precio " + precio1erArt,
    		nombreArtFicha.toUpperCase().contains(nombre1erArt.toUpperCase()) &&
    		precioArtFicha.replaceAll(" ", "").toUpperCase().contains(precio1erArt.replaceAll(" ", "").toUpperCase()),
    		State.Info_NoHardcopy);
    	
      	return validations;
    }
    
   @Validation(
	description = "Como mínimo el #{porcentaje} % de los productos son panorámicas",
	level=State.Info_NoHardcopy)
   public boolean hayPanoramicasEnGaleriaDesktop(float porcentaje) {
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       float numArtTotal = pageGaleria.getNumArticulos();
       float numArtPanoramicos = pageGaleriaDesktop.getNumArticulos(TypeArticleDesktop.Panoramica);
       return (!articlesUnderPercentage(numArtTotal, numArtPanoramicos, porcentaje));
   }
   
   private static boolean articlesUnderPercentage(float numArtTotal, float numArtToMesure, float percentage) {
	   if (numArtTotal==0) {
		   return true;
	   }
	   return ((numArtToMesure / numArtTotal) < (percentage / 100));
   }
   
    @Validation (
    	description="Existe algún vídeo en la galería",
    	level=State.Warn)
    public boolean validaHayVideoEnGaleria() {
	    PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
      	return (pageGaleriaDesktop.isPresentAnyArticle(TypeArticleDesktop.Video));
    }
   
    @Validation (
    	description="Aparece una página con artículos (la esperamos #{maxSecondsWait} segundos)",
    	level=State.Warn)
    public boolean validaArtEnContenido(int maxSecondsWait) {
    	return (pageGaleria.isVisibleArticleUntil(1, maxSecondsWait));
   }   
   
   public DatosStep clickArticlesHearthIcons(List<Integer> posIconsToClick, TypeActionFav actionFav, DataFavoritos dataFavoritos) 
   throws Exception {
       String estadoFinal = "";
       switch (actionFav) {
       case Marcar:
           estadoFinal = "Marcados";
           break;
       case Desmarcar:
           estadoFinal = "Desmarcados";
           break;
       default:
           break;
       }

       //Step
       DatosStep datosStep = new DatosStep       (
           "Seleccionamos (para <b>" + actionFav + "</b>) los \"Hearth Icons\" asociados a los artículos con posiciones <b>" + posIconsToClick + "</b>", 
           "Los \"Hearth Icons\" quedan " + estadoFinal);
       try {
           ArrayList<ArticuloScreen> listAddFav = pageGaleria.clickArticleHearthIcons(posIconsToClick);
           switch (actionFav) {
           case Marcar:
               dataFavoritos.addToLista(listAddFav);
               break;
           case Desmarcar:
               dataFavoritos.removeFromLista(listAddFav);
               break;
           default:
               break;
           }
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { StepAspect.storeDataAfterStep(datosStep); }       
       
       //Validaciones
       String descripValidac = 
           "1) Quedan " + estadoFinal + " los iconos asociados a los artículos con posiciones <b>" + posIconsToClick.toString() + "</b>";
       datosStep.setNOKstateByDefault();
       ListResultValidation listVals = ListResultValidation.getNew(datosStep);
       try {
           if (!pageGaleria.iconsInCorrectState(posIconsToClick, actionFav)) {
               listVals.add(1, State.Warn);
           }

           datosStep.setListResultValidations(listVals);
       } 
       finally { listVals.checkAndStoreValidations(descripValidac); }
       
       return datosStep;
   }
   
   public NombreYRefList selectListadoXColumnasDesktop(NumColumnas numColumnas, NombreYRefList listArticlesGaleriaAnt) 
   throws Exception {
       //Step
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       DatosStep datosStep = new DatosStep       (
           "Seleccionar el link del listado a <b>" + numColumnas.name() + " columnas</b>", 
           "Aparece un listado de artículos a " + numColumnas.name() + " columnas");
       try {
           pageGaleriaDesktop.clickLinkColumnas(numColumnas);
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { StepAspect.storeDataAfterStep(datosStep); }         

       //Validaciones.
       String descripValidac = 
           "1) Aparece el layout correspondiente al listado a <b>" + numColumnas.name() + " columnas</b>";
       datosStep.setNOKstateByDefault();  
       ListResultValidation listVals = ListResultValidation.getNew(datosStep);
       try {
           if (pageGaleria.getLayoutNumColumnas() != pageGaleriaDesktop.getNumColumnas(numColumnas)) {
               listVals.add(1, State.Warn);
           }
                
           datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }       
       
       //Obtenemos y almacenamos los artículos de la galería Nuevo
       NombreYRefList listArticlesGaleriaAct = pageGaleria.getListaNombreYRefArticulos();
       
       int articulosComprobar = 20;
       if (listArticlesGaleriaAnt!=null) {
           descripValidac = 
               "1) Los primeros " + articulosComprobar + " artículos de la galería a " + numColumnas.name() + 
               " columnas son iguales a los de la anterior galería";
           datosStep.setNOKstateByDefault();  
           listVals = ListResultValidation.getNew(datosStep);
           try {
               if (!listArticlesGaleriaAct.isArticleListEquals(listArticlesGaleriaAnt, articulosComprobar)) {
                   listVals.add(2, State.Info);
                   NombreYRef articleGaleryActualNotFit = listArticlesGaleriaAct.getFirstArticleThatNotFitWith(listArticlesGaleriaAnt);
                   descripValidac+="<br><b style=\"color:" + State.Info.getColorCss() + "\">Warning!</b>: hay productos de la galería que no cuadran con los de la galería anterior (por ejemplo <b>" + articleGaleryActualNotFit.toString() + "</b>). ";
                   descripValidac+=listArticlesGaleriaAct.getTableHTLMCompareArticlesGaleria(listArticlesGaleriaAnt);
               }
               
               datosStep.setListResultValidations(listVals);
           }
           finally { listVals.checkAndStoreValidations(descripValidac); }
       }
       
       return listArticlesGaleriaAct;
   }
   
   /**
    * Valida que por pantalla está apareciendo una lista análoga (en contenido y orden) que la que le pasamos como parámetro (perteneciente a otro nodo)
    */
   public void validaNombresYRefEnOrden(NodoStatus nodoAnt, NodoStatus nodoAct, DatosStep datosStep) {
       String descripValidac = 
           "1) El número de artículos de la galería Nuevo (" + nodoAct.getArticlesNuevo().size() + ") es igual al del nodo " + nodoAnt.getIp() + " (" + nodoAnt.getArticlesNuevo().size() + ")<br>" +
           "2) El orden y contenido de los artículos en ambos nodos es el mismo";
       datosStep.setNOKstateByDefault(); 
       ListResultValidation listVals = ListResultValidation.getNew(datosStep);
       try {
           if (nodoAct.getArticlesNuevo().size()!=nodoAct.getArticlesNuevo().size()) {
               listVals.add(1, State.Warn);
           }
           NombreYRef articleGaleryActualNotFit = nodoAct.getArticleNuevoThatNotFitWith(nodoAnt);
           if (articleGaleryActualNotFit!=null) {
               listVals.add(2, State.Warn);
               descripValidac+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay productos de la galería que no cuadran con los de la galería del nodo " + nodoAnt.getIp() + " (por ejemplo <b>" + articleGaleryActualNotFit.toString() + "</b>). ";
               descripValidac+=nodoAct.getArticlesNuevo().getTableHTLMCompareArticlesGaleria(nodoAnt.getArticlesNuevo());
           }
       
           datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }
   }

   @SuppressWarnings("static-access")
   public void validateBannerSuperiorIfExistsDesktop() {
	   DatosStep datosStep = TestCaseData.getDatosLastStep();
	   boolean bannerIsVisible = PageGaleriaDesktop.secBannerHead.isVisible(dFTest.driver);
	   if (bannerIsVisible) {
		   if (!PageGaleriaDesktop.secBannerHead.isBannerWithoutTextAccesible(dFTest.driver)) {
		       String descripValidac =
	               "1) El Banner de Cabecera contiene algún texto";
	           datosStep.setNOKstateByDefault();  
               ListResultValidation listVals = ListResultValidation.getNew(datosStep);            
	           try {
	               String textBanner = PageGaleriaDesktop.secBannerHead.getText(dFTest.driver);
	               if ("".compareTo(textBanner)==0) {
	                   listVals.add(1, State.Defect);
	               }
	                
	               datosStep.setListResultValidations(listVals);
	           }
	           finally { listVals.checkAndStoreValidations(descripValidac); }
		   }
	   }
   }
   
   @SuppressWarnings("static-access")
   public void clickBannerSuperiorIfLinkableDesktop() throws Exception {
	   boolean bannerIsLincable = PageGaleriaDesktop.secBannerHead.isLinkable(dFTest.driver);
	   if (bannerIsLincable) {
	       //Step
	       DatosStep datosStep = new DatosStep       (
	           "Seleccionar el banner superior", 
	           "Aparece una galería de artículos");
	       try {
	           PageGaleriaDesktop.secBannerHead.clickBannerIfClickable(dFTest.driver);
	               
	           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	       }
	       finally { StepAspect.storeDataAfterStep(datosStep); }     
	       
	       int maxSecondsWait = 3;
	       validaArtEnContenido(maxSecondsWait);
	   }
   }
   
   @SuppressWarnings("static-access")
   public void validaRebajasHasta70Jun2018(IdiomaPais idioma) {
	   DatosStep datosStep = TestCaseData.getDatosLastStep();
	   boolean filtrosPercActivated = false;
	   String maxPercDiscount = "70";
	   int minMenusVisibles = 1;
       String descripValidac =
           "<b style=\"color:blue\">Rebajas</b></br>" +
           "1) Es visible el banner de cabecera<br>" +
           "2) El banner de cabecera contiene el porcentaje de descuento<b>" + maxPercDiscount + "</b><br>";
       
       if (filtrosPercActivated)
    	   descripValidac+=
           "3) Como mínimo son visibles " + minMenusVisibles + " de entre todos los tipos de filtros de descuento " + 
           "(<b>" + Arrays.asList(TypeMenuDiscount.values()) + ")</b><br>";
       else
    	   descripValidac+=
    	   "3) No aparece ningún filtro de descuento";
       datosStep.setNOKstateByDefault();      
       ListResultValidation listVals = ListResultValidation.getNew(datosStep);
       try {
           if (!PageGaleriaDesktop.secBannerHead.isVisible(dFTest.driver)) {
               listVals.add(1, State.Defect);
           }
           String textBanner = PageGaleriaDesktop.secBannerHead.getText(dFTest.driver);
           if (!UtilsTestMango.textContainsSetenta(textBanner, idioma)) {
               listVals.add(2, State.Warn);
           }
           int menusDescVisibles = SecMenusDesktop.secMenusFiltroDiscount.getNumberOfVisibleMenus(dFTest.driver);
           if (filtrosPercActivated) {
	           if (menusDescVisibles < minMenusVisibles) {
	               listVals.add(3, State.Defect);      
	           }
           }
           else {
        	   if (menusDescVisibles > 0) {
        		   listVals.add(3, State.Warn);
        	   }
           }
            
           datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }
   }
   
   @SuppressWarnings("static-access")
   public void validaRebajasJun2018Desktop(boolean salesOnInCountry, boolean isGaleriaSale, Pais pais, IdiomaPais idioma, LineaType lineaType, bloqueMenu menuType) 
   throws Exception {
       //Validaciones
	   DatosStep datosStep = TestCaseData.getDatosLastStep();
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       String descripValidac = 
           "1) Estamos en la página de Galería";
       datosStep.setNOKstateByDefault();     
       ListResultValidation listVals = ListResultValidation.getNew(datosStep);
       try {
           if (!pageGaleriaDesktop.isPage()) {
               listVals.add(1, State.Warn);
           }
                
           datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }       
       
       //Validaciones.
       if (salesOnInCountry) {
	       String saleTraduction = UtilsTestMango.getSaleTraduction(idioma);
	       descripValidac =
	           "<b style=\"color:blue\">Rebajas</b></br>" +
	           "1) Es visible el banner de cabecera<br>" +
	           "2) El banner de cabecera es de rebajas  (contiene un símbolo de porcentaje o \"" + saleTraduction + "\")<br>" +
	           "3) El banner de cabecera no es lincable<br>" +
	           "4) El banner de cabecera contiene un link de \"Más info\"";
	       datosStep.setNOKstateByDefault();   
	       listVals = ListResultValidation.getNew(datosStep);
	       try {
	           if (!PageGaleriaDesktop.secBannerHead.isVisible(dFTest.driver)) {
	               listVals.add(1, State.Defect);
	           }
	           String textBanner = PageGaleriaDesktop.secBannerHead.getText(dFTest.driver);
	           if (!UtilsTestMango.textContainsPercentage(textBanner, idioma) &&
	               !textBanner.contains(saleTraduction)) {
	               listVals.add(2, State.Defect);
	           }
	           if (PageGaleriaDesktop.secBannerHead.isLinkable(dFTest.driver)) {
	               listVals.add(3, State.Info);
	           }
	           if (!PageGaleriaDesktop.secBannerHead.isVisibleLinkInfoRebajas(dFTest.driver)) {
	               listVals.add(4, State.Warn);           
	           }
	                
	           datosStep.setListResultValidations(listVals);
	       }
	       finally { listVals.checkAndStoreValidations(descripValidac); }
       }
       else {
	       String saleTraduction = UtilsTestMango.getSaleTraduction(idioma);
	       descripValidac =
	           "<b style=\"color:blue\">Rebajas</b></br>" +
	           "1) El banner de cabecera NO es de rebajas  (NO contiene un símbolo de porcentaje o \"" + saleTraduction + "\")";
	       datosStep.setNOKstateByDefault();   
	       listVals = ListResultValidation.getNew(datosStep);
	       try {
	           if (PageGaleriaDesktop.secBannerHead.isVisible(dFTest.driver)) {
		           String textBanner = PageGaleriaDesktop.secBannerHead.getText(dFTest.driver);
		           if (UtilsTestMango.textContainsPercentage(textBanner, idioma) ||
		               textBanner.contains(saleTraduction)) {
		               listVals.add(1, State.Defect);
		           }
	           }
	                
	           datosStep.setListResultValidations(listVals);
	       }
	       finally { listVals.checkAndStoreValidations(descripValidac); }
       }
       
       SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(Channel.desktop, AppEcom.shop, dFTest.driver);
       if (salesOnInCountry) {
	       if (!isGaleriaSale) {
	           //Validaciones.
	           descripValidac =
	               "<b style=\"color:blue\">Rebajas</b></br>" +
	               "1) Son visibles los menús laterales de filtro a nivel detemporadas (Collection)<br>" +
	               "2) Aparece el filtro para todas las temporadas <b>All</b>)<br>" +
	               "3) Aparece el filtro para las ofertas <b>Sale</b><br>" +
	               "4) Aparece el filtro para la nueva temporada <b>Next season preview</b>";
	           datosStep.setNOKstateByDefault();    
	           listVals = ListResultValidation.getNew(datosStep);
	           try {
	               if (!filtrosCollection.isVisible()) {
	                   listVals.add(1, State.Defect);
	               }
	               if (!filtrosCollection.isVisibleMenu(FilterCollection.all)) {
	                   listVals.add(2, State.Warn);           
	               }
	               if (!filtrosCollection.isVisibleMenu(FilterCollection.sale)) {
	                   listVals.add(3, State.Warn);
	               }
	               if (!filtrosCollection.isVisibleMenu(FilterCollection.nextSeason)) {
	                   listVals.add(4, State.Warn);
	               }
	                    
	               datosStep.setListResultValidations(listVals);
	           }
	           finally { listVals.checkAndStoreValidations(descripValidac); }       
	       }
	       else {
	           //Validaciones.
	           descripValidac =
	               "<b style=\"color:blue\">Rebajas</b></br>" +
	               "1) No son visibles los menús laterales de filtro a nivel detemporadas (Collection)<b>";
	           datosStep.setNOKstateByDefault();   
	           listVals = ListResultValidation.getNew(datosStep);
	           try {
	               if (filtrosCollection.isVisible()) {
	                   listVals.add(1, State.Defect);
	               }
	                    
	               datosStep.setListResultValidations(listVals);
	           }
	           finally { listVals.checkAndStoreValidations(descripValidac); }
	       }
       }
       else {
           //Validaciones.
           descripValidac =
               "<b style=\"color:blue\">Rebajas</b></br>" +
               "1) No aparece el filtro para las ofertas <b>Sale</b>";
           datosStep.setNOKstateByDefault();    
           listVals = ListResultValidation.getNew(datosStep);
           try {
               if (filtrosCollection.isVisibleMenu(FilterCollection.sale)) {
                   listVals.add(1, State.Warn);
               }
                    
               datosStep.setListResultValidations(listVals);
           }
           finally { listVals.checkAndStoreValidations(descripValidac); }       
       }
       
       if (salesOnInCountry) {
	       //Validaciones.
	       FilterOrdenacion ordenType;
	       List<String> lineasInvertidas = RebajasPaisDAO.getLineasInvertidas(pais.getCodigo_pais(), menuType);
	       boolean temporadaInvertida = (lineasInvertidas!=null && lineasInvertidas.contains(lineaType.toString()));
	       if (!temporadaInvertida || isGaleriaSale)
	    	   ordenType = FilterOrdenacion.BloqueTemporadas_2y3_despues_la_4; 
	       else
	    	   ordenType = FilterOrdenacion.BloqueTemporada_4_despues_la_2y3;
	       
	       descripValidac =
	           "<b style=\"color:blue\">Rebajas</b></br>" +
	           "1) El 1er artículo pertenece a alguna de las temporadas " + ordenType.getTemporadasIniciales() + " <br>" +
	           "2) Los artículos aparecen ordenados por <b>" + ordenType.toString() + "</b>";
	       datosStep.setNOKstateByDefault(); 
	       listVals = ListResultValidation.getNew(datosStep);
	       try {
	           State levelErrorValidation2 = State.Info_NoHardcopy;
	           String ref1rstArticle = pageGaleria.getReferencia(1/*posArticle*/);
	           int temporada1rstArticle = 0;
	           if ("".compareTo(ref1rstArticle)!=0) {
	        	   temporada1rstArticle = Integer.valueOf(ref1rstArticle.substring(0,1));
	           }
	           
	           if (!ordenType.getTemporadasIniciales().contains(temporada1rstArticle)) {
	               listVals.add(1, State.Warn);
	               levelErrorValidation2 = State.Warn;
	           }
	           String notInOrder = pageGaleria.getAnyArticleNotInOrder(ordenType);
	           if ("".compareTo(notInOrder)!=0) {
	               descripValidac+=
	                   "<br>" +
	                   "<b>Warning!</b> " + notInOrder;
	               listVals.add(2, levelErrorValidation2);
	           }
	                
	           datosStep.setListResultValidations(listVals);
	       }
	       finally { listVals.checkAndStoreValidations(descripValidac); }      
       }
   }
   
   public void validaArticlesOfTemporadas(List<Integer> listTemporadas, State levelError, DatosStep datosStep) {
	   validaArticlesOfTemporadas(listTemporadas, false, levelError, datosStep);
   }
   
   public void validaArticlesOfTemporadas(List<Integer> listTemporadas, DatosStep datosStep) {
	   validaArticlesOfTemporadas(listTemporadas, false, State.Warn, datosStep);
   }
   
   public void validaArticlesOfTemporadas(List<Integer> listTemporadas, boolean validaNotNewArticles, DatosStep datosStep) {
	   validaArticlesOfTemporadas(listTemporadas, validaNotNewArticles, State.Warn, datosStep);
   }
   
   public void validaArticlesOfTemporadas(List<Integer> listTemporadas, boolean validaNotNewArticles, 
		   								  State levelError, DatosStep datosStep) {
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       String descripValidac =
           "<b style=\"color:blue\">Rebajas</b></br>" +
           "1) Todos los artículos pertenecen a las temporadas <b>" + listTemporadas.toString() + "</b>";
       if (validaNotNewArticles)
    	   descripValidac+=" y no contienen alguna de las etiquetas de artículo nuevo (" + PageGaleria.listLabelsNew + ")";
       datosStep.setNOKstateByDefault();        
       ListResultValidation listVals = ListResultValidation.getNew(datosStep);
       try {
           List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadasX(ControlTemporada.articlesFromOther, listTemporadas);
           if (validaNotNewArticles) {
        	   listArtWrong = PageGaleria.getNotNewArticlesFrom(listArtWrong);
           }
           
           if (listArtWrong.size() > 0) {
               listVals.add(1, levelError);
               descripValidac+=
                   "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
                   "hay " + listArtWrong.size() + " artículos que no pertenecen a las temporadas " + listTemporadas + ":<br>";
               for (String nameWrong : listArtWrong)
            	   descripValidac+=(nameWrong + "<br>");
               descripValidac+="</lin>";
           }
                
           datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }	   
   }
   
   @Validation
   public ListResultValidation validaNotArticlesOfTypeDesktop(TypeArticle typeArticle, State levelError) {
	   	ListResultValidation validations = ListResultValidation.getNew();
	   	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
	   	List<String> listArtWrong = pageGaleriaDesktop.getArticlesOfType(typeArticle);
	   	validations.add(
	   		"<b style=\"color:blue\">Rebajas</b></br>" +
	   		"1) No hay ningún artículo del tipo <b>" + typeArticle + "</b>",
	   		listArtWrong.size()==0, levelError);
   		if (listArtWrong.size() > 0) {
   			addInfoArtWrongToDescription(listArtWrong, typeArticle, validations.get(0));
   		}
	   	return validations; 
   }   
   
   private void addInfoArtWrongToDescription(List<String> listArtWrong, TypeArticle typeArticle, ResultValidation validation) {
       String textToAdd =
           "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
           "hay " + listArtWrong.size() + " artículos que son del tipo <b>" + typeArticle + "</b><br>:";
       for (String nameWrong : listArtWrong) {
    	   textToAdd+=(nameWrong + "<br>");
       }
       textToAdd+="</lin>";
       String descriptionOrigin = validation.getDescription();
       validation.setDescription(descriptionOrigin + textToAdd);
   }
   
   @SuppressWarnings("static-access")
   public static void clickMoreInfoBannerRebajasJun2018(DataFmwkTest dFTest) throws Exception {
       //Step
       DatosStep datosStep = new DatosStep       (
           "Seleccionamos el link <b>Más Info</b>", 
           "Se hace visible el aviso legal");
       try {
    	   PageGaleriaDesktop.secBannerHead.clickLinkInfoRebajas(dFTest.driver);
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { StepAspect.storeDataAfterStep(datosStep); }         

       //Validaciones
       int maxSecondsToWait = 1;
       String descripValidac = 
           "<b style=\"color:blue\">Rebajas</b></br>" +
           "1) Se despliega la información relativa a las rebajas (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
           "2) Aparece el link de <b>Menos info</b>";
       datosStep.setNOKstateByDefault();      
       ListResultValidation listVals = ListResultValidation.getNew(datosStep);
       try {
           if (!PageGaleriaDesktop.secBannerHead.isVisibleInfoRebajasUntil(maxSecondsToWait, dFTest.driver)) {
           	   listVals.add(1, State.Warn);
           }
           if (!PageGaleriaDesktop.secBannerHead.isVisibleLinkTextInfoRebajas(TypeLinkInfo.less, dFTest.driver)) {
               listVals.add(2, State.Warn);
           }
                
           datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }	   
   }
}