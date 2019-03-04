package com.mng.robotest.test80.mango.test.stpv.shop.favoritos;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
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
@SuppressWarnings({"static-access"})
public class ModalFichaFavoritosStpV {
    
    @Validation
    public static ListResultValidation validaIsVisibleFicha(ArticuloScreen articulo, WebDriver driver) { 
    	ListResultValidation validations = ListResultValidation.getNew();
    	int maxSecondsWait = 2;
    	validations.add(
    		"En Favoritos es visible el modal de la ficha del producto " + articulo.getRefProducto() + " (lo esperamos hasta " + maxSecondsWait + " segundos) <br>",
    		PageFavoritos.modalFichaFavoritos.isVisibleFichaUntil(articulo.getRefProducto(), maxSecondsWait, driver), State.Warn);
    	validations.add(
            "Aparece seleccionado el color " + articulo.getColor(),
            PageFavoritos.modalFichaFavoritos.isColorSelectedInFicha(articulo.getColor(), driver), State.Warn);
        return validations;
    }  
    
    @Step(
    	description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
    	expected="El artículo aparece en la bolsa")
    public static void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel, 
    									AppEcom app, DataFmwkTest dFTest) throws Exception {
        String refProductoToAdd = artToAddBolsa.getRefProducto();
        String tallaSelected = PageFavoritos.modalFichaFavoritos.addArticleToBag(refProductoToAdd, 1/*posicionTalla*/, channel, dFTest.driver);
        artToAddBolsa.setTallaAlf(tallaSelected);
        dataBolsa.addArticulo(artToAddBolsa);

        //Validaciones
        switch (channel) {
        case desktop:
            SecBolsaStpV.validaAltaArtBolsa(dataBolsa, channel, AppEcom.shop, dFTest.driver);
            break;
        default:
        case movil_web:
            //En este caso no se hace visible la bolsa después de añadir a Favoritos con lo que sólo validamos el número
            SecBolsaStpV.validaNumArtEnBolsa(dataBolsa, channel, app, dFTest.driver);
            break;
        }
    }
    
    @Step (
    	description="En Favoritos cerramos la ficha del producto <b>#{articulo.getRefProducto()}</b>",
        expected="Desaparece la ficha")
    public static void closeFicha(ArticuloScreen articulo, DataFmwkTest dFTest) {
        String refProductoToClose = articulo.getRefProducto();
        PageFavoritos.modalFichaFavoritos.closeFicha(refProductoToClose, dFTest.driver);
        
        //Validaciones
        int maxSecondsWait = 2;
        checkFichaDisappearsFromFavorites(articulo.getRefProducto(), maxSecondsWait, dFTest.driver);
    }
    
    @Validation (
        description="Desaparece de Favoritos la ficha del producto #{refProducto} (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Warn)
    public static boolean checkFichaDisappearsFromFavorites(String refProducto, int maxSecondsWait, WebDriver driver) {
    	return (PageFavoritos.modalFichaFavoritos.isInvisibleFichaUntil(refProducto, maxSecondsWait, driver));
    }
}
