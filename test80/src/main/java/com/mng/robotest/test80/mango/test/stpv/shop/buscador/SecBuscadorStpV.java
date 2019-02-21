package com.mng.robotest.test80.mango.test.stpv.shop.buscador;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageErrorBusqueda;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecBuscadorWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;


public class SecBuscadorStpV {
    /**
     * Utiliza el buscador para localizar una referencia y aplica validaciones específicas según la disponibilidad: disponible, no_disponible, algún_color_no_disponible
     */
    public static DatosStep searchArticuloAndValidateBasic(TypeArticleStock typeArticle, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
    	ArticleStock articulo = ManagerArticlesStock.getArticleStock(typeArticle, dCtxSh);
    	return (searchArticuloAndValidateBasic(articulo, dCtxSh, dFTest));
    }
    
    /**
     * Utiliza el buscador para localizar una referencia y aplica validaciones específicas según la disponibilidad: disponible, no_disponible, algún_color_no_disponible
     */
    public static DatosStep searchArticuloAndValidateBasic(ArticleStock articulo, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Buscar un producto de tipo: <b>" + articulo.getType() + "</b> " +
            "(" + articulo.getReference() + " color:" + articulo.getColourCode() + ", size:" + articulo.getSize() + ")", 
            "Aparece una ficha de producto de typo <b>" + articulo.getType() + "</b>");
        try {
            SecBuscadorWrapper.buscarArticulo(articulo, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }         

        //Validations
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dFTest);
        pageFichaStpV.validateIsFichaAccordingTypeProduct(articulo, datosStep);
        
        return datosStep;
    }
    
    
    public static void busquedaCategoriaProducto(String categoriaABuscar, boolean categoriaExiste, AppEcom app, 
    											 Channel channel, DataFmwkTest dFTest) throws Exception {
    	PageGaleria pageGaleria = (PageGaleria)PageGaleria.getInstance(channel, app, dFTest.driver); 
    	String categoriaExisteStr = "No";
        if (categoriaExiste)
            categoriaExisteStr = "Sí";
        
        DatosStep datosStep = new DatosStep       (
            "Introducir la categoría de producto <b>" + categoriaABuscar + " </b>(" + categoriaExisteStr + " existe)</b>", 
            "El resultado de la búsqueda es el correcto :-)");
        try {
            //Introducimos la categoría de producto existente en el buscador y lo seleccionamos
            SecBuscadorWrapper.buscarTexto(categoriaABuscar, channel, app, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver);           
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }           
            
        if (categoriaExiste) { 
            //Validaciones
            String producSin1erCaracter = categoriaABuscar.substring(1, categoriaABuscar.length()-1).toLowerCase();
            int maxSecondsWait = 3;
            String descripValidac = 
                "1) Aparece como mínimo un producto de tipo " + producSin1erCaracter + " (lo esperamos hasta " + maxSecondsWait + " segundos)<br>" +
                "2) Aparece la categoría en el resultado de la búsqueda";
            datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                if ("".compareTo(pageGaleria.getArticuloWithText(producSin1erCaracter, maxSecondsWait))==0) {
                    listVals.add(1, State.Defect);
                }
                if (!pageGaleria.isCabeceraResBusqueda(producSin1erCaracter)) {
                    listVals.add(2, State.Defect);  
                }
    
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); } 
        }
        else {
            //Validaciones
            String descripValidac =
                "1) Aparece la página de error en la búsqueda con el encabezado<b>" + categoriaABuscar + "</b>";
            datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!PageErrorBusqueda.isCabeceraResBusqueda(dFTest.driver, categoriaABuscar)) {
                    listVals.add(1, State.Warn);
                }
                  
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(false/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/);
    }
}
