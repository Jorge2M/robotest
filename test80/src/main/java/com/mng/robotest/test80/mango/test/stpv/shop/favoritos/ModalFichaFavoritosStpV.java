package com.mng.robotest.test80.mango.test.stpv.shop.favoritos;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados al modal con la ficha de producto que aparece en la página de Favoritos
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"javadoc", "static-access"})
public class ModalFichaFavoritosStpV {
    
    @Validation
    public static ListResultValidation validaIsVisibleFicha(ArticuloScreen articulo, DatosStep datosStep, WebDriver driver) { 
    	ListResultValidation validations = ListResultValidation.getNew(datosStep);
    	int maxSecondsWait = 2;
    	validations.add(
    		"En Favoritos es visible el modal de la ficha del producto " + articulo.getRefProducto() + " (lo esperamos hasta " + maxSecondsWait + " segundos) <br>",
    		PageFavoritos.modalFichaFavoritos.isVisibleFichaUntil(articulo.getRefProducto(), maxSecondsWait, driver), State.Warn);
    	validations.add(
            "Aparece seleccionado el color " + articulo.getColor(),
            PageFavoritos.modalFichaFavoritos.isColorSelectedInFicha(articulo.getColor(), driver), State.Warn);
        return validations;
    }  
    
    public static DatosStep addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel, 
    										 AppEcom app, DataFmwkTest dFTest) throws Exception {
        String refProductoToAdd = artToAddBolsa.getRefProducto();
        
        //Step
        DatosStep datosStep = new DatosStep(
            "Desde Favoritos añadimos el artículo: " + refProductoToAdd + " (1a talla disponible) a la bolsa",
            "El artículo aparece en la bolsa");
        try {
            String tallaSelected = PageFavoritos.modalFichaFavoritos.addArticleToBag(refProductoToAdd, 1/*posicionTalla*/, channel, dFTest.driver);
            artToAddBolsa.setTallaAlf(tallaSelected);
            dataBolsa.addArticulo(artToAddBolsa);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        switch (channel) {
        case desktop:
            SecBolsaStpV.validaAltaArtBolsa(datosStep, dataBolsa, channel, AppEcom.shop, dFTest);
            break;
        default:
        case movil_web:
            //En este caso no se hace visible la bolsa después de añadir a Favoritos con lo que sólo validamos el número
            SecBolsaStpV.validaNumArtEnBolsa(dataBolsa, channel, app, datosStep, dFTest);
            break;
        }
        
        return datosStep;
    }
    
    public static DatosStep closeFicha(ArticuloScreen articulo, DataFmwkTest dFTest) {
        String refProductoToClose = articulo.getRefProducto();
        
        //Step
        DatosStep datosStep = new DatosStep(
            "En Favoritos cerramos la ficha del producto: " + refProductoToClose,
            "Desaparece la ficha");
        try {
            PageFavoritos.modalFichaFavoritos.closeFicha(refProductoToClose, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        int maxSecondsWait = 2;
        checkFichaDisappearsFromFavorites(articulo.getRefProducto(), maxSecondsWait, datosStep, dFTest.driver);

        return datosStep;
    }
    
    @Validation (
        description="Desaparece de Favoritos la ficha del producto #{refProducto} (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Warn)
    public static boolean checkFichaDisappearsFromFavorites(String refProducto, int maxSecondsWait, DatosStep datosStep, WebDriver driver) {
    	return (PageFavoritos.modalFichaFavoritos.isInvisibleFichaUntil(refProducto, maxSecondsWait, driver));
    }
}
