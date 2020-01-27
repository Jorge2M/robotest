package com.mng.robotest.test80.mango.test.stpv.shop.favoritos;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.ModalFichaFavoritos;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados al modal con la ficha de producto que aparece en la página de Favoritos
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"static-access"})
public class ModalFichaFavoritosStpV {
    
	private final WebDriver driver;
	private final ModalFichaFavoritos modalFichaFavoritos;
	
	private ModalFichaFavoritosStpV(ModalFichaFavoritos modalFichaFavoritos) {
		this.modalFichaFavoritos = modalFichaFavoritos;
		this.driver = modalFichaFavoritos.getWebDriver();
	}
	
	public static ModalFichaFavoritosStpV getNew(ModalFichaFavoritos modalFichaFavoritos) {
		return new ModalFichaFavoritosStpV(modalFichaFavoritos);
	}
	
    @Validation
    public ChecksResult validaIsVisibleFicha(ArticuloScreen articulo) { 
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
    	validations.add(
    		"En Favoritos es visible el modal de la ficha del producto " + articulo.getRefProducto() + " (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		modalFichaFavoritos.isVisibleFichaUntil(articulo.getRefProducto(), maxSecondsWait), State.Warn);
    	validations.add(
            "Aparece seleccionado el color <b>" + articulo.getColor() + "</b>",
            modalFichaFavoritos.isColorSelectedInFicha(articulo.getColor()), State.Warn);
        return validations;
    }  
    
    @Step(
    	description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
    	expected="El artículo aparece en la bolsa")
    public void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel, AppEcom app) 
    throws Exception {
        String refProductoToAdd = artToAddBolsa.getRefProducto();
        Talla tallaSelected = modalFichaFavoritos.addArticleToBag(refProductoToAdd, 1, channel);
        artToAddBolsa.setTalla(tallaSelected);
        dataBolsa.addArticulo(artToAddBolsa);

        //Validaciones
        switch (channel) {
        case desktop:
            SecBolsaStpV.validaAltaArtBolsa(dataBolsa, channel, AppEcom.shop, driver);
            break;
        default:
        case movil_web:
            //En este caso no se hace visible la bolsa después de añadir a Favoritos con lo que sólo validamos el número
            SecBolsaStpV.validaNumArtEnBolsa(dataBolsa, channel, app, driver);
            break;
        }
    }
    
    @Step (
    	description="En Favoritos cerramos la ficha del producto <b>#{articulo.getRefProducto()}</b>",
        expected="Desaparece la ficha")
    public void closeFicha(ArticuloScreen articulo) {
        String refProductoToClose = articulo.getRefProducto();
        modalFichaFavoritos.closeFicha(refProductoToClose);
        
        //Validaciones
        int maxSecondsWait = 2;
        checkFichaDisappearsFromFavorites(articulo.getRefProducto(), maxSecondsWait);
    }
    
    @Validation (
        description="Desaparece de Favoritos la ficha del producto #{refProducto} (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Warn)
    public boolean checkFichaDisappearsFromFavorites(String refProducto, int maxSecondsWait) {
    	return (modalFichaFavoritos.isInvisibleFichaUntil(refProducto, maxSecondsWait));
    }
}
