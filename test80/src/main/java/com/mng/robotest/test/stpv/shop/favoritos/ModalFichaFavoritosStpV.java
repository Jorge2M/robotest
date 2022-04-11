package com.mng.robotest.test.stpv.shop.favoritos;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.favoritos.ModalFichaFavoritos;
import com.mng.robotest.test.stpv.shop.SecBolsaStpV;

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
	public ChecksTM validaIsVisibleFicha(ArticuloScreen articulo) { 
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 2;
		validations.add(
			"En Favoritos es visible el modal de la ficha del producto " + articulo.getRefProducto() + " (lo esperamos hasta " + maxSeconds + " segundos)",
			modalFichaFavoritos.isVisibleFichaUntil(articulo.getRefProducto(), maxSeconds), State.Warn);
		validations.add(
			"Aparece seleccionado el color <b>" + articulo.getColor() + "</b>",
			modalFichaFavoritos.isColorSelectedInFicha(articulo.getColor()), State.Warn);
		return validations;
	}  
	
	@Step(
		description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
		expected="El artículo aparece en la bolsa")
	public void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel, AppEcom app, Pais pais) 
	throws Exception {
		String refProductoToAdd = artToAddBolsa.getRefProducto();
		Talla tallaSelected = modalFichaFavoritos.addArticleToBag(refProductoToAdd, 1, channel, app, pais);
		artToAddBolsa.setTalla(tallaSelected);
		dataBolsa.addArticulo(artToAddBolsa);
		SecBolsaStpV secBolsaStpV = new SecBolsaStpV(channel, app, pais, driver);
		
		switch (channel) {
		case desktop:
			secBolsaStpV.validaAltaArtBolsa(dataBolsa);
			break;
		default:
		case mobile:
			//En este caso no se hace visible la bolsa después de añadir a Favoritos con lo que sólo validamos el número
			secBolsaStpV.validaNumArtEnBolsa(dataBolsa);
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
		int maxSeconds = 2;
		checkFichaDisappearsFromFavorites(articulo.getRefProducto(), maxSeconds);
	}
	
	@Validation (
		description="Desaparece de Favoritos la ficha del producto #{refProducto} (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean checkFichaDisappearsFromFavorites(String refProducto, int maxSeconds) {
		return (modalFichaFavoritos.isInvisibleFichaUntil(refProducto, maxSeconds));
	}
}