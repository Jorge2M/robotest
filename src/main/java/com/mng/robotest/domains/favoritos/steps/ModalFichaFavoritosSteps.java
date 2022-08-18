package com.mng.robotest.domains.favoritos.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.favoritos.pageobjects.ModalFichaFavoritos;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;


@SuppressWarnings({"static-access"})
public class ModalFichaFavoritosSteps extends StepBase {
	
	private final ModalFichaFavoritos modalFichaFavoritos = new ModalFichaFavoritos();
	
	@Validation
	public ChecksTM validaIsVisibleFicha(ArticuloScreen articulo) { 
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"En Favoritos es visible el modal de la ficha del producto " + articulo.getRefProducto() + " (lo esperamos hasta " + maxSeconds + " segundos)",
			modalFichaFavoritos.isVisibleFichaUntil(articulo.getRefProducto(), maxSeconds), State.Warn);
		checks.add(
			"Aparece seleccionado el color <b>" + articulo.getColor() + "</b>",
			modalFichaFavoritos.isColorSelectedInFicha(articulo.getColor()), State.Warn);
		return checks;
	}  
	
	@Step(
		description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
		expected="El artículo aparece en la bolsa")
	public void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Pais pais) 
			throws Exception {
		String refProductoToAdd = artToAddBolsa.getRefProducto();
		Talla tallaSelected = modalFichaFavoritos.addArticleToBag(refProductoToAdd, 1);
		artToAddBolsa.setTalla(tallaSelected);
		dataBolsa.addArticulo(artToAddBolsa);
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(pais);
		
		switch (channel) {
		case desktop:
			secBolsaSteps.validaAltaArtBolsa(dataBolsa);
			break;
		default:
		case mobile:
			//En este caso no se hace visible la bolsa después de añadir a Favoritos con lo que sólo validamos el número
			secBolsaSteps.validaNumArtEnBolsa(dataBolsa);
			break;
		}
	}
	
	@Step (
		description="En Favoritos cerramos la ficha del producto <b>#{articulo.getRefProducto()}</b>",
		expected="Desaparece la ficha")
	public void closeFicha(ArticuloScreen articulo) {
		String refProductoToClose = articulo.getRefProducto();
		modalFichaFavoritos.closeFicha(refProductoToClose);
		checkFichaDisappearsFromFavorites(articulo.getRefProducto(), 2);
	}
	
	@Validation (
		description="Desaparece de Favoritos la ficha del producto #{refProducto} (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean checkFichaDisappearsFromFavorites(String refProducto, int maxSeconds) {
		return (modalFichaFavoritos.isInvisibleFichaUntil(refProducto, maxSeconds));
	}
}
