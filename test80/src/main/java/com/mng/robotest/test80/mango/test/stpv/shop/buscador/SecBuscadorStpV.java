package com.mng.robotest.test80.mango.test.stpv.shop.buscador;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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

@SuppressWarnings("javadoc")
public class SecBuscadorStpV {
    /**
     * Utiliza el buscador para localizar una referencia y aplica validaciones específicas según la disponibilidad: disponible, no_disponible, algún_color_no_disponible
     */
    public static datosStep searchArticuloAndValidateBasic(TypeArticleStock typeArticle, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
    	ArticleStock articulo = ManagerArticlesStock.getArticleStock(typeArticle, dCtxSh);
    	return (searchArticuloAndValidateBasic(articulo, dCtxSh, dFTest));
    }
    
    /**
     * Utiliza el buscador para localizar una referencia y aplica validaciones específicas según la disponibilidad: disponible, no_disponible, algún_color_no_disponible
     */
    public static datosStep searchArticuloAndValidateBasic(ArticleStock articulo, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Buscar un producto de tipo: <b>" + articulo.getType() + "</b> " +
            "(" + articulo.getReference() + " color:" + articulo.getColourCode() + ", size:" + articulo.getSize() + ")", 
            "Aparece una ficha de producto de typo <b>" + articulo.getType() + "</b>");
        try {
            SecBuscadorWrapper.buscarArticulo(articulo, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }         

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
        
        datosStep datosStep = new datosStep       (
            "Introducir la categoría de producto <b>" + categoriaABuscar + " </b>(" + categoriaExisteStr + " existe)</b>", 
            "El resultado de la búsqueda es el correcto :-)");
        try {
            //Introducimos la categoría de producto existente en el buscador y lo seleccionamos
            SecBuscadorWrapper.buscarTexto(categoriaABuscar, channel, app, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver);           
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        if (categoriaExiste) { 
            //Validaciones
            String producSin1erCaracter = categoriaABuscar.substring(1, categoriaABuscar.length()-1).toLowerCase();
            String descripValidac = 
                "1) Aparece como mínimo un producto de tipo " + producSin1erCaracter + " (lo esperamos 3 segundos)<br>" +
                "2) Aparece la categoría en el resultado de la búsqueda";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if ("".compareTo(pageGaleria.getArticuloWithText(producSin1erCaracter, 3/*secondsWait*/))==0)
                    fmwkTest.addValidation(1, State.Defect, listVals);
                //2)
                if (!pageGaleria.isCabeceraResBusqueda(producSin1erCaracter))
                    fmwkTest.addValidation(2, State.Defect, listVals);  
    
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); } 
        }
        else {
            //Validaciones
            String descripValidac =
                "1) Aparece la página de error en la búsqueda con el encabezado<b>" + categoriaABuscar + "</b>";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!PageErrorBusqueda.isCabeceraResBusqueda(dFTest.driver, categoriaABuscar))
                    fmwkTest.addValidation(1, State.Warn, listVals);
                  
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(false/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/, datosStep, dFTest);
    }
}
