package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

@SuppressWarnings("javadoc")
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
    
    public static PageGaleriaStpV getInstance(Channel channel, AppEcom app, DataFmwkTest dFTest) throws Exception {
        return (new PageGaleriaStpV(channel, app, dFTest));
    }

    public void selectArticuloEnPestanyaAndBack(LocationArticle locationArt) 
    throws Exception {
        String galeryWindowHandle = dFTest.driver.getWindowHandle();
        String detailWindowHandle = "";
        DataFichaArt datosArticulo = new DataFichaArt();

        
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el artículo " + locationArt + " en una pestaña aparte", 
            "Aparece la ficha del artículo seleccionado en una pestaña aparte");
        try {
            //Almacenamos el nombre del artículo y su referencia
            WebElement articulo = pageGaleria.getArticulo(locationArt);
            datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
            datosArticulo.setReferencia(PageGaleria.getRefArticulo(articulo));
    
            //Seleccionamos el artículo y lo cargamos en una pestaña aparte
            detailWindowHandle = pageGaleria.openArticuloPestanyaAndGo(articulo, app);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(app, channel, dFTest);
        pageFichaStpV.validaDetallesProducto(datosArticulo, datosStep);
        
        //Cerramos la pestaña y cambiamos a la ventana padre
        dFTest.driver.switchTo().window(detailWindowHandle);
        dFTest.driver.close();
        dFTest.driver.switchTo().window(galeryWindowHandle);
    }
    
    public ResultSelectArtStep selectArticulo(LocationArticle locationArt, DataCtxShop dCtxSh) 
    throws Exception {
        DataFichaArt datosArticulo = new DataFichaArt();
        String urlGaleria = dFTest.driver.getCurrentUrl();
        
        DatosStep datosStep = new DatosStep (
            "Seleccionar el artículo " + locationArt, 
            "Aparece la ficha del artículo seleccionado");
        try { 
            //Almacenamos el nombre del artículo y su referencia
            WebElement articulo = pageGaleria.getArticulo(locationArt);
            datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
            datosArticulo.setReferencia(PageGaleriaDesktop.getRefArticulo(articulo));
                    
            //Seleccionar el artículo
            pageGaleria.clickArticulo(articulo);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dFTest);
        pageFichaStpV.validaDetallesProducto(datosArticulo, datosStep);
        pageFichaStpV.validaPrevNext(locationArt, dCtxSh, datosStep);

        //Validaciones sección BreadCrumb + Next
        if (dCtxSh.channel==Channel.desktop) {
            if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old)
                pageFichaStpV.validaBreadCrumbFichaOld(urlGaleria, datosStep);
        }
        
        return (ResultSelectArtStep.getNew(datosStep, datosArticulo));
    }
    
    public void selectLinkAddArticuloToBagDesktop(int posArticulo)
    throws Exception {
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
        DatosStep datosStep = new DatosStep (
            "Posicionarse sobre el artículo en la posición <b>" + posArticulo + "</b>, esperar que aparezca el link \"Añadir\" y seleccionarlo", 
            "Aparece la capa con la información de las tallas");
        try { 
            pageGaleriaDesktop.selectLinkAddArticleToBagDesktop(posArticulo);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //Validaciones
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece la capa con la información de las tallas (la esperamos hasta " + maxSecondsToWait + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageGaleriaDesktop.isVisibleArticleCapaTallasDesktopUntil(posArticulo, maxSecondsToWait))
                fmwkTest.addValidation(1, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }    
    
    /**
     * @return si el artículo estaba disonible
     */
    public boolean selectTallaArticuloDesktop(int posArticulo, int posTalla, DataBag dataBag, DataCtxShop dCtxSh) 
    throws Exception {
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
        ArticuloScreen articulo = null;
        DatosStep datosStep = new DatosStep (
            "Del " + posArticulo + "o artículo, seleccionamos la " + posTalla + "a talla", 
            "Se da de alta correctamente el artículo en la bolsa");
        datosStep.setGrabHTML(true);
        try { 
            articulo = pageGaleriaDesktop.selectTallaArticle(posArticulo, posTalla);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        boolean isVisibleAvisame = ModalArticleNotAvailableStpV.validateState(StateModal.notvisible, datosStep, dFTest);
        if (!isVisibleAvisame) {
            dataBag.addArticulo(articulo);
            SecBolsaStpV.validaAltaArtBolsa(datosStep, dataBag, dCtxSh.channel, dCtxSh.appE, dFTest);
        }
        
        return !isVisibleAvisame;
    }
    
    /**
     * Escrollamos hasta llegar a la página indicada en toPage
     * @param toPage indica el número de página en el que nos queremos posicionar. Si es PageGaleria.scrollToLast asumimos que queremos llegar hasta el final del catálogo
     */
    public DataScroll scrollFromFirstPage(DataForScrollStep dataForScroll, DataCtxShop dCtxSh) 
    throws Exception {
        DataScroll datosScroll = null;
        int pageToScroll = dataForScroll.numPageToScroll;
        if (dCtxSh.channel == Channel.movil_web)
        	pageToScroll = 3;
        
        String idPage = pageToScroll + "a";
        String apareceElFooter = "No";
        if (pageToScroll>=PageGaleriaDesktop.maxPageToScroll) {
            idPage = "última";
            apareceElFooter = "Sí";
        }
        
        //Step
        int numArticulosInicio = pageGaleria.getNumArticulos();
        DatosStep datosStep = new DatosStep (
            "Escrollar hasta posicionarse en la " + idPage + " página", 
            "Se escrolla correctamente");
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
        	datosScroll = pageGaleria.scrollToPageFromFirst(pageToScroll, dCtxSh.appE);
                              
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones.
        String descripValidac = 
            "1) " + apareceElFooter + " aparece el footer";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (pageToScroll>=PageGaleriaDesktop.maxPageToScroll) {
                if (!SecFooter.isVisible(dCtxSh.appE, dFTest.driver)) 
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
            else {
                if (SecFooter.isVisible(dCtxSh.appE, dFTest.driver)) 
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); } 

        if (pageToScroll < PageGaleriaDesktop.maxPageToScroll) {
            //Validaciones.
            descripValidac = 
                "1) En pantalla aparecen más artículos (" + datosScroll.articulosMostrados + ") " + 
                   "de los que había inicialmente (" + numArticulosInicio + ")";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (datosScroll.articulosMostrados <= numArticulosInicio)
                    fmwkTest.addValidation(1, State.Warn, listVals);
                    
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        
        if (dataForScroll.ordenacionExpected != FilterOrdenacion.NOordenado) {
            //Validaciones
            descripValidac = "1) Los artículos aparecen " + dataForScroll.ordenacionExpected;
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!pageGaleria.articlesInOrder(dataForScroll.ordenacionExpected)) 
                    fmwkTest.addValidation(1, State.Defect, listVals);
                    
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        
        //Validaciones
        descripValidac = 
            "1) No aparece ningún artículo repetido";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            ArrayList<NombreYRef> productsRepeated = pageGaleria.searchArticleRepeatedInGallery();
            if (productsRepeated!=null && productsRepeated.size()>0) {
                fmwkTest.addValidation(1, State.Defect, listVals);
                descripValidac+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: " + 
                				"hay productos " + productsRepeated.size() + " repetidos, " + 
                				"por ejemplo el <b>" + productsRepeated.get(0).toString() + "</b>";
            }
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        if (dataForScroll.validateArticlesExpected) {
            //Validación
            descripValidac = 
                "1) En pantalla aparecen exactamente " + dataForScroll.numArticlesExpected + " artículos " + 
                   "(están apareciendo " + datosScroll.articulosTotalesPagina + ")";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (dataForScroll.numArticlesExpected != datosScroll.articulosTotalesPagina)
                    fmwkTest.addValidation(1, State.Info, listVals);
                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
            
            datosScroll.datosStep = datosStep;
        }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, dataForScroll.validaImgBroken, datosStep, dFTest);
        
        //VALIDACIONES - PARA ANALYTICS (sólo para firefox y NetAnalysis)
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, datosStep, dFTest);
        
        datosScroll.datosStep = datosStep;
        return datosScroll;
   }

   
   public int seleccionaOrdenacionGaleria(FilterOrdenacion typeOrdenacion, String tipoPrendasGaleria, int numArticulosValidar, 
		   										 DataCtxShop dCtxSh) throws Exception {
       //Step. Seleccionar el link "Descendente" / "Ascendente"
       DatosStep datosStep = new DatosStep       (
           "Seleccionar la ordenación " + typeOrdenacion.name(), 
           "Los artículos se ordenan correctamente");
       try {
           SecFiltros secFiltros = SecFiltros.newInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
           secFiltros.selecOrdenacionAndReturnNumArticles(typeOrdenacion); 
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }         
       
       //Validaciones.
       String descripValidac = 
           "1) Aparece una pantalla en la que el title contiene \"" + tipoPrendasGaleria;
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!dFTest.driver.getTitle().toLowerCase().contains(tipoPrendasGaleria)) 
               fmwkTest.addValidation(1, State.Warn, listVals);
   
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
       
       //Validaciones
       int numArticulosPant = 0;
       String validacion2 = ""; 
       if (numArticulosValidar >=0 )
           validacion2 = "2) Aparecen " + numArticulosValidar + " artículos<br>"; 
       
       descripValidac = 
           "1) Aparecen > 1 prendas<br>" + 
           validacion2 +
           "3) Los artículos aparecen ordenados por " + typeOrdenacion.name();
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           numArticulosPant = pageGaleria.getNumArticulos() + pageGaleria.getNumArticulos();
           if (numArticulosPant <= 1) 
               fmwkTest.addValidation(1, State.Warn, listVals);
           //2)
           if (numArticulosValidar >= 0) {
               if (numArticulosValidar != numArticulosPant)
                   fmwkTest.addValidation(2, State.Info, listVals);
           }
           //3)
           if (!pageGaleria.articlesInOrder(typeOrdenacion))
               fmwkTest.addValidation(3, State.Warn, listVals);
   
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }                

       //Validaciones estándar. 
       AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
       
       return numArticulosPant;
   }
   
   /**
    * Paso/Validación consistente en seleccionar el icono de la flecha up (de la galería de productos) para volver al 1er artículo de la página
    * @throws InterruptedException 
    */
   public void backTo1erArticleMobilStep(DataCtxShop dCtxSh) throws Exception {
       //Step
       DatosStep datosStep = new DatosStep (
           "Volver al 1er artículo de la galería (mediante selección del icono de la flecha Up)", 
           "Se visualiza el 1er elemento");
       try {
           //Volvemos al 1er elemento
           pageGaleria.backTo1erArticulo();
           
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }
       
       //Validaciones
       String descripValidac = 
           "1) Es clickable el 1er elemento de la lista<br>" +
           "2) Es clickable el bloque de filtros (esperamos 2 segundos)";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!pageGaleria.isClickableArticuloUntil(5, 0/*seconds*/))
               fmwkTest.addValidation(1, State.Warn, listVals);
           //2)
           int maxSecondsToWait = 2;
           SecFiltros secFiltros = SecFiltros.newInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
           if (!secFiltros.isClickableFiltroUntil(maxSecondsToWait))
               fmwkTest.addValidation(2, State.Warn, listVals);
          
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }                
   }

   /**
    * Pasos/Validaciones consistentes en seleccionar un determinado color de un determinado artículo
    * @param posColor posición del color en la lista de colores del artículo
    * @param numArtConColores posición del artículo entre el conjunto de artículos con variedad de colores
    * @return src de la imagen obtenida al ejecutar el click sobre el color
    */
   public String selecColorFromArtGaleriaStep(int numArtConColores, int posColor) 
   throws Exception {
       String TAGsrcPng2oColor = "@srcPng2oColor";
       String TAGnombre1erArt = "@nombre1erArt";
       String TAGprecio1erArt = "@precio1erArt";
       String srcImg1erArt = "";
       String srcImgAfterClickColor = "";
       WebElement articuloColores = null;
       WebElement colorToClick = null;
       
       //Step. Seleccionar el Xo color (posColor) del Y artículo (numArtConColores) con variedad de colores
       DatosStep datosStep = new DatosStep       (
           "Seleccionar el " + posColor + "o color (" + TAGsrcPng2oColor +") no seleccionado del " + numArtConColores + "o artículo con variedad de colores (" + TAGnombre1erArt + ", " + TAGprecio1erArt +")", 
           "Se selecciona el color");
       try {
           //En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
           WebdrvWrapp.waitForPageLoaded(dFTest.driver, 2/*waitSeconds*/);
           
           articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
           datosStep.setDescripcion(datosStep.getDescripcion().replace(TAGnombre1erArt, pageGaleria.getNombreArticulo(articuloColores)));
           colorToClick = pageGaleria.getColorArticulo(articuloColores, false/*selected*/, posColor);
           datosStep.setDescripcion(datosStep.getDescripcion().replace(TAGprecio1erArt, pageGaleria.getPrecioArticulo(articuloColores)));
           srcImg1erArt = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
           datosStep.setDescripcion(datosStep.getDescripcion().replace(TAGsrcPng2oColor, colorToClick.getAttribute("src")));
           
           WebdrvWrapp.forceClick(dFTest.driver, colorToClick, null);
           Thread.sleep(100);
           
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }         
       
       //Validaciones.
       String descripValidac = 
           "1) Se modifica la imagen correspondiente al artículo ";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           srcImgAfterClickColor = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
           if (srcImgAfterClickColor.contains(srcImg1erArt)) 
               fmwkTest.addValidation(1, State.Defect, listVals);
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }               
       
       return srcImgAfterClickColor;
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
    public String clicksSliderArticuloConColores(int numArtConColores, ArrayList<TypeSlider> typeSliderList, String srcImageExpected) 
    throws Exception {
       if (channel!=Channel.desktop) {
           throw new RuntimeException("Method clickSliderArticuloConColores doesn't support channel " + channel);
       }
       
       PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       String TAGnombreArt = "@nombreArt";
       String TAGsrc1erSlider = "@srcPng2oColor";
       String srcImg1erSlider = "";
       WebElement articuloColores = null;
       
       String slidersListStr = getStringSliderList(typeSliderList);
       DatosStep datosStep = new DatosStep       (
           "Clickar la siguiente secuencia de sliders: <b>" + slidersListStr + "</b> del " + numArtConColores + "o artículo con variedad de colores (" + TAGnombreArt + "). Previamente realizamos un \"Hover\" sobre dicho artículo", 
           "Aparece el artículo original(" + TAGnombreArt + ")");
       try {
           //En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
           WebdrvWrapp.waitForPageLoaded(dFTest.driver, 2/*waitSeconds*/);
           
           articuloColores = pageGaleriaDesktop.getArticuloConVariedadColoresAndHoverNoDoble(numArtConColores);
           datosStep.setDescripcion(datosStep.getDescripcion().replace(TAGnombreArt, pageGaleria.getNombreArticulo(articuloColores)));
           srcImg1erSlider = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
           datosStep.setDescripcion(datosStep.getDescripcion().replace(TAGsrc1erSlider, srcImg1erSlider));
                  	   
           pageGaleriaDesktop.clickSliderAfterHoverArticle(articuloColores, typeSliderList);
          
           //PageGaleriaDesktop.slideAction(numArtConColores, dFTest.driver);
           
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }
       
       //Validaciones.
       String srcImg2oSlider = "";
       String descripValidac = 
           "1) Se modifica la imagen asociada al artículo (<b>antes</b>: " + srcImg1erSlider + ", <b>ahora</b>: " + srcImg2oSlider + ")";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           srcImg2oSlider = pageGaleria.getImagenArticulo(articuloColores).getAttribute("src");
           if (srcImg2oSlider.compareTo(srcImg1erSlider)==0)
               fmwkTest.addValidation(1, State.Defect, listVals);
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
       
       if ("".compareTo(srcImageExpected)!=0) {
           descripValidac = 
               "1) El src de la imagen <b>ahora</b> (" + srcImg2oSlider + ") es la <b>original</b> (" + srcImageExpected + ")";
           datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
           try {
               List<SimpleValidation> listVals = new ArrayList<>();
               //1)
               if (srcImg2oSlider.compareTo(srcImageExpected)!=0)
                   fmwkTest.addValidation(1, State.Defect, listVals);
                   
               datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
           }
           finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
       }
       
       return srcImg2oSlider;
   }
    
   private static String getStringSliderList(ArrayList<TypeSlider> typeSliderList) {
       String listStr = "";
       for (TypeSlider typeSlider : typeSliderList)
           listStr+=(typeSlider + ", ");
       
       return listStr;
   }
    
   @SuppressWarnings("static-access")
   public void selecArticuloGaleriaStep(int numArtConColores) throws Exception {
       WebElement articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
       String nombre1erArt = pageGaleria.getNombreArticulo(articuloColores);
       String precio1erArt = pageGaleria.getPrecioArticulo(articuloColores);
       
       //Step. Seleccionar el Xer artículo con variedad de colores
       DatosStep datosStep = new DatosStep       (
           "Seleccionar el " + numArtConColores + "o artículo con variedad de colores (" + nombre1erArt + " " + precio1erArt + ")", 
           "Aparece el artículo original(" + nombre1erArt + " " + precio1erArt + ")");
       datosStep.setGrabNettrafic(dFTest.ctx);       
       try {
           pageGaleria.clickArticulo(articuloColores);
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       }
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }         

       //Validaciones.
       int maxSecondsToWait = 3;
       String descripValidac = 
           "1) Aparece la página de ficha (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
           "2) Aparece el artículo anteriormente seleccionado: <br>" +
           "   - Nombre " + nombre1erArt + "<br>" +
           "   - Precio " + precio1erArt;
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           PageFicha pageFicha = PageFicha.newInstance(app, channel, dFTest.driver);
           if (!pageFicha.isPageUntil(maxSecondsToWait))
               fmwkTest.addValidation(1, State.Warn, listVals);
           //2)
           String nombreArtFicha = pageFicha.secDataProduct.getTituloArt(channel, dFTest.driver);
           String precioArtFicha = pageFicha.secDataProduct.getPrecioFinalArticulo(dFTest.driver);
           if (!nombreArtFicha.toUpperCase().contains(nombre1erArt.toUpperCase()))
               fmwkTest.addValidation(2, State.Info_NoHardcopy, listVals);
                
           if (!precioArtFicha.replaceAll(" ", "").toUpperCase().contains(precio1erArt.replaceAll(" ", "").toUpperCase()))
               fmwkTest.addValidation(2, State.Info_NoHardcopy, listVals);
               
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

       //Validaciones estándar. 
       AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
       
       //VALIDACIONES - PARA ANALYTICS (sólo para firefox y NetAnalysis)
       PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, datosStep, dFTest);        
   }
   
   public void hayPanoramicasEnGaleriaDesktop(float porcentaje, DatosStep datosStep) {
       //Validaciones
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       String descripValidac = 
           "1) Como mínimo el " + porcentaje + " % de los productos son panorámicas";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           float numArtTotal = pageGaleria.getNumArticulos();
           float numArtPanoramicos = pageGaleriaDesktop.getNumArticulos(TypeArticleDesktop.Panoramica);
           if (articlesUnderPercentage(numArtTotal, numArtPanoramicos, porcentaje))
               fmwkTest.addValidation(1, State.Info_NoHardcopy, listVals);
       
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
   }
   
   private static boolean articlesUnderPercentage(float numArtTotal, float numArtToMesure, float percentage) {
	   if (numArtTotal==0)
		   return true;
	   
	   return ((numArtToMesure / numArtTotal) < (percentage / 100));
   }
   
   public void validaHayVideoEnGaleria(DatosStep datosStep) {
       //Validaciones
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       String descripValidac = 
           "1) Existe algún vídeo en la galería";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!pageGaleriaDesktop.isPresentAnyArticle(TypeArticleDesktop.Video))
               fmwkTest.addValidation(1,State.Warn, listVals);
       
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
   }
   
   // Validación que comprueba que está apareciendo una galería de artículos
   public void validaArtEnContenido(DatosStep datosStep) {
       String descripValidac = "1) Aparece una página con artículos (la esperamos 3 segundos)";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           boolean articulos = pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, 3/*seconds*/);
           if (!articulos)
               fmwkTest.addValidation(1, State.Warn, listVals);

           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       } 
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
   }   
   
   public DatosStep clickArticlesHearthIcons(List<Integer> posIconsToClick, TypeActionFav actionFav, DataCtxShop dCtxSh, 
		   									 DataFavoritos dataFavoritos) throws Exception {
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
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }       
       
       //Validaciones
       String descripValidac = 
           "1) Quedan " + estadoFinal + " los iconos asociados a los artículos con posiciones <b>" + posIconsToClick.toString() + "</b>";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!pageGaleria.iconsInCorrectState(posIconsToClick, actionFav))
               fmwkTest.addValidation(1, State.Warn, listVals);

           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       } 
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
       
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
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }         

       //Validaciones.
       String descripValidac = 
           "1) Aparece el layout correspondiente al listado a <b>" + numColumnas.name() + " columnas</b>";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (pageGaleria.getLayoutNumColumnas() != pageGaleriaDesktop.getNumColumnas(numColumnas))
               fmwkTest.addValidation(1, State.Warn, listVals);
                
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }       
       
       //Obtenemos y almacenamos los artículos de la galería Nuevo
       NombreYRefList listArticlesGaleriaAct = pageGaleria.getListaNombreYRefArticulos();
       
       int articulosComprobar = 20;
       if (listArticlesGaleriaAnt!=null) {
           descripValidac = 
               "1) Los primeros " + articulosComprobar + " artículos de la galería a " + numColumnas.name() + " columnas son iguales a los de la anterior galería";
           datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
           try {
               List<SimpleValidation> listVals = new ArrayList<>();

               //1)
             //1)
               if (!listArticlesGaleriaAct.isArticleListEquals(listArticlesGaleriaAnt, articulosComprobar)) {
                   fmwkTest.addValidation(2, State.Info, listVals);
                   NombreYRef articleGaleryActualNotFit = listArticlesGaleriaAct.getFirstArticleThatNotFitWith(listArticlesGaleriaAnt);
                   descripValidac+="<br><b style=\"color:" + State.Info.getColorCss() + "\">Warning!</b>: hay productos de la galería que no cuadran con los de la galería anterior (por ejemplo <b>" + articleGaleryActualNotFit.toString() + "</b>). ";
                   descripValidac+=listArticlesGaleriaAct.getTableHTLMCompareArticlesGaleria(listArticlesGaleriaAnt);
               }
               
               datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
           }
           finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (nodoAct.getArticlesNuevo().size()!=nodoAct.getArticlesNuevo().size())
               fmwkTest.addValidation(1, State.Warn, listVals);
           //2)
           NombreYRef articleGaleryActualNotFit = nodoAct.getArticleNuevoThatNotFitWith(nodoAnt);
           if (articleGaleryActualNotFit!=null) {
               fmwkTest.addValidation(2, State.Warn, listVals);
               descripValidac+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay productos de la galería que no cuadran con los de la galería del nodo " + nodoAnt.getIp() + " (por ejemplo <b>" + articleGaleryActualNotFit.toString() + "</b>). ";
               descripValidac+=nodoAct.getArticlesNuevo().getTableHTLMCompareArticlesGaleria(nodoAnt.getArticlesNuevo());
           }
       
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
   }

   @SuppressWarnings("static-access")
   public void validateBannerSuperiorIfExistsDesktop(DatosStep datosStep) {
	   boolean bannerIsVisible = PageGaleriaDesktop.secBannerHead.isVisible(dFTest.driver);
	   if (bannerIsVisible) {
		   if (!PageGaleriaDesktop.secBannerHead.isBannerWithoutTextAccesible(dFTest.driver)) {
		       String descripValidac =
	               "1) El Banner de Cabecera contiene algún texto";
	           datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
	           try {
	               List<SimpleValidation> listVals = new ArrayList<>();
	               //1)
	               String textBanner = PageGaleriaDesktop.secBannerHead.getText(dFTest.driver);
	               if ("".compareTo(textBanner)==0)
	                   fmwkTest.addValidation(1, State.Defect, listVals);          
	                
	               datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	           }
	           finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
	       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }     
	       
	       validaArtEnContenido(datosStep);
	   }
   }
   
   @SuppressWarnings("static-access")
   public void validaRebajasHasta70Jun2018(IdiomaPais idioma, DatosStep datosStep) {
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
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!PageGaleriaDesktop.secBannerHead.isVisible(dFTest.driver))
               fmwkTest.addValidation(1, State.Defect, listVals);
           //2)
           String textBanner = PageGaleriaDesktop.secBannerHead.getText(dFTest.driver);
           if (!UtilsTestMango.textContainsSetenta(textBanner, idioma))
               fmwkTest.addValidation(2, State.Warn, listVals);
           //3)
           int menusDescVisibles = SecMenusDesktop.secMenusFiltroDiscount.getNumberOfVisibleMenus(dFTest.driver);
           if (filtrosPercActivated) {
	           if (menusDescVisibles < minMenusVisibles)
	               fmwkTest.addValidation(3, State.Defect, listVals);      
           }
           else {
        	   if (menusDescVisibles > 0)
        		   fmwkTest.addValidation(3, State.Warn, listVals);
           }
            
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
   }
   
   @SuppressWarnings("static-access")
   public void validaRebajasJun2018Desktop(boolean isGaleriaSale, Pais pais, IdiomaPais idioma, LineaType lineaType, 
		   								   bloqueMenu menuType, DatosStep datosStep) throws Exception {
       //Validaciones
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       String descripValidac = 
           "1) Estamos en la página de Galería";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!pageGaleriaDesktop.isPage())
               fmwkTest.addValidation(1, State.Warn, listVals);
                
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }       
       
       //Validaciones.
       String saleTraduction = UtilsTestMango.getSaleTraduction(idioma);
       descripValidac =
           "<b style=\"color:blue\">Rebajas</b></br>" +
           "1) Es visible el banner de cabecera<br>" +
           "2) El banner de cabecera es de rebajas  (contiene un símbolo de porcentaje o \"" + saleTraduction + "\")<br>" +
           "3) El banner de cabecera no es lincable<br>" +
           "4) El banner de cabecera contiene un link de \"Más info\"";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!PageGaleriaDesktop.secBannerHead.isVisible(dFTest.driver))
               fmwkTest.addValidation(1, State.Defect, listVals);
           //2)
           String textBanner = PageGaleriaDesktop.secBannerHead.getText(dFTest.driver);
           if (!UtilsTestMango.textContainsPercentage(textBanner, idioma) &&
               !textBanner.contains(saleTraduction))
               fmwkTest.addValidation(2, State.Defect, listVals);
           //3)
           if (PageGaleriaDesktop.secBannerHead.isLinkable(dFTest.driver))
               fmwkTest.addValidation(3, State.Info, listVals);
           //4)
           if (!PageGaleriaDesktop.secBannerHead.isVisibleLinkInfoRebajas(dFTest.driver))
               fmwkTest.addValidation(4, State.Warn, listVals);           
                
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
       
       SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(Channel.desktop, AppEcom.shop, dFTest.driver);
       if (!isGaleriaSale) {
           //Validaciones.
           descripValidac =
               "<b style=\"color:blue\">Rebajas</b></br>" +
               "1) Son visibles los menús laterales de filtro a nivel detemporadas (Collection)<br>" +
               "2) Aparece el filtro para todas las temporadas <b>All</b>)<br>" +
               "3) Aparece el filtro para las ofertas <b>Sale</b><br>" +
               "4) Aparece el filtro para la nueva temporada <b>Next season preview</b>";
           datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
           try {
               List<SimpleValidation> listVals = new ArrayList<>();
               //1)
               if (!filtrosCollection.isVisible())
                   fmwkTest.addValidation(1, State.Defect, listVals);
               //2)
               if (!filtrosCollection.isVisibleMenu(FilterCollection.all))
                   fmwkTest.addValidation(2, State.Warn, listVals);           
               //3)
               if (!filtrosCollection.isVisibleMenu(FilterCollection.sale))
                   fmwkTest.addValidation(3, State.Warn, listVals);
               //4)
               if (!filtrosCollection.isVisibleMenu(FilterCollection.nextSeason))
                   fmwkTest.addValidation(4, State.Warn, listVals);
                    
               datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
           }
           finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }       
       }
       else {
           //Validaciones.
           descripValidac =
               "<b style=\"color:blue\">Rebajas</b></br>" +
               "1) No son visibles los menús laterales de filtro a nivel detemporadas (Collection)<b>";
           datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
           try {
               List<SimpleValidation> listVals = new ArrayList<>();
               //1)
               if (filtrosCollection.isVisible())
                   fmwkTest.addValidation(1, State.Defect, listVals);
                    
               datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
           }
           finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
       }
       
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
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           State levelErrorValidation2 = State.Info_NoHardcopy;
           //1)
           String ref1rstArticle = pageGaleria.getReferencia(1/*posArticle*/);
           int temporada1rstArticle = 0;
           if ("".compareTo(ref1rstArticle)!=0)
        	   temporada1rstArticle = Integer.valueOf(ref1rstArticle.substring(0,1));
           
           if (!ordenType.getTemporadasIniciales().contains(temporada1rstArticle)) {
               fmwkTest.addValidation(1, State.Warn, listVals);
               levelErrorValidation2 = State.Warn;
           }
           //2)
           String notInOrder = pageGaleria.getAnyArticleNotInOrder(ordenType);
           if ("".compareTo(notInOrder)!=0) {
               descripValidac+=
                   "<br>" +
                   "<b>Warning!</b> " + notInOrder;
               fmwkTest.addValidation(2, levelErrorValidation2, listVals);
           }
                
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }       

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
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadasX(ControlTemporada.articlesFromOther, listTemporadas);
           if (validaNotNewArticles) {
        	   listArtWrong = PageGaleria.getNotNewArticlesFrom(listArtWrong);
           }
           
           if (listArtWrong.size() > 0) {
               fmwkTest.addValidation(1, levelError, listVals);
               descripValidac+=
                   "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
                   "hay " + listArtWrong.size() + " artículos que no pertenecen a las temporadas " + listTemporadas + ":<br>";
               for (String nameWrong : listArtWrong)
            	   descripValidac+=(nameWrong + "<br>");
               descripValidac+="</lin>";
           }
                
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }	   
   }
   
   public void validaNotArticlesOfTypeDesktop(TypeArticle typeArticle, State levelError, DatosStep datosStep) {
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
       String descripValidac =
           "<b style=\"color:blue\">Rebajas</b></br>" +
           "1) No hay ningún artículo del tipo <b>" + typeArticle + "</b>";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           ArrayList<String> listArtWrong = pageGaleriaDesktop.getArticlesOfType(typeArticle);
           if (listArtWrong.size() > 0) {
               fmwkTest.addValidation(1, levelError, listVals);
               descripValidac+=
                   "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
                   "hay " + listArtWrong.size() + " artículos que son del tipo <b>" + typeArticle + "</b><br>:";
               for (String nameWrong : listArtWrong)
            	   descripValidac+=(nameWrong + "<br>");
               descripValidac+="</lin>";
           }
                
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }	   
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
       finally { datosStep.setStepNumber( fmwkTest.grabStep(datosStep, dFTest)); }         

       //Validaciones
       int maxSecondsToWait = 1;
       String descripValidac = 
           "<b style=\"color:blue\">Rebajas</b></br>" +
           "1) Se despliega la información relativa a las rebajas (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
           "2) Aparece el link de <b>Menos info</b>";
       datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
       try {
           List<SimpleValidation> listVals = new ArrayList<>();
           //1)
           if (!PageGaleriaDesktop.secBannerHead.isVisibleInfoRebajasUntil(maxSecondsToWait, dFTest.driver))
           	   fmwkTest.addValidation(1, State.Warn, listVals);
           //2)
           if (!PageGaleriaDesktop.secBannerHead.isVisibleLinkTextInfoRebajas(TypeLinkInfo.less, dFTest.driver))
               fmwkTest.addValidation(2, State.Warn, listVals);
                
           datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }	   
   }
}