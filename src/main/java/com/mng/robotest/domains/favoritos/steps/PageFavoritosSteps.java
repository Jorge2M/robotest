package com.mng.robotest.domains.favoritos.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.favoritos.pageobjects.PageFavoritos;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageFavoritosSteps extends StepBase {
	
	private final PageFavoritos pageFavoritos = new PageFavoritos();
	
	public PageFavoritos getPageFavoritos() {
		return pageFavoritos;
	}
	
	@Validation
	public ChecksTM validaIsPageOK() {
		var checks = ChecksTM.getNew();
		int secondsCapa = 3;
		int secondsArticles = 2;
		checks.add(
			"Está visible la capa de favoritos con artículos (la esperamos hasta " + secondsCapa + " segundos)",
			pageFavoritos.isSectionArticlesVisibleUntil(secondsCapa));
		
		checks.add(
			"Aparecen los artículos (los esperamos hasta " + secondsArticles + " segundos): <br>" + dataTest.getDataFavoritos().getListArtDescHTML(),
			pageFavoritos.areVisibleArticlesUntil(secondsArticles));
		
		return checks;
	}
	
	public void clear(ArticuloScreen articulo) {
		dataTest.getDataFavoritos().removeArticulo(articulo);
		clear(articulo.getReferencia(), articulo.getCodigoColor());
	}
	
	@Step (
		description="Seleccionar el link de favoritos compartidos",
		expected="El modal de favoritos compartidos aparece correctamente")
	public void clickShareIsOk() {
		pageFavoritos.openShareModal();
		checkShareIsOk();
	}
	
	@Validation
	public ChecksTM checkShareIsOk() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el modal de favoritos compartidos",
			pageFavoritos.checkShareModalUntill(5));
		
		checks.add(
			"Aparece el boton de compartir por Telegram",
			pageFavoritos.isShareTelegramFavoritesVisible());
		
		checks.add(
			"Aparece el boton de compartir por WhatsApp",
			pageFavoritos.isShareWhatsappFavoritesVisible());
		
		checks.add(
			"Aparece la url para copiarla y compartir como texto", 
			pageFavoritos.isShareUrlFavoritesVisible());
		
		return checks;
	}
	
	@Step (
		description="Cerramos el modal de favoritos compartidos",
		expected="El modal de favoritos compartidos desaparece correctamente")
	public void closeShareModal() {
		pageFavoritos.closeSharedModal();
		checkShareIsClosedUntil(2);
	}
	
	@Validation (
		description="Desaparece el modal de favoritos compartidos (lo esperamos hasta #{seconds} segundos)",
		level=Warn)
	public boolean checkShareIsClosedUntil(int seconds) {
		return (pageFavoritos.checkShareModalInvisible(seconds));
	}
	
	@Step (
		description="Eliminamos de Favoritos el artículo con referencia <b>#{refArticulo}</b> y código de color <b>#{codColor}</b>",
		expected="El artículo desaparece de Favoritos")
	public void clear(String refArticulo, String codColor) {
		pageFavoritos.clearArticuloAndWait(refArticulo, codColor);
		checkArticleDisappearsFromFavoritesUntil(refArticulo, codColor, 5);
	}
	
	@Validation (
		description="Desaparece de Favoritos el artículo con referencia <b>#{refArticle}</b> y código de color <b>#{codColor}</b> (lo esperamos hasta #{seconds} segundos)")
	public boolean checkArticleDisappearsFromFavoritesUntil(String refArticle, String codColor, int seconds) {
		return (pageFavoritos.isInvisibleArticleUntil(refArticle, codColor, seconds));
	}
	
	@Step (
		description="Eliminamos de Favoritos los posibles artículos existentes",
		expected="No queda ningún artículo en Favoritos")
	public void clearAll() {
		dataTest.getDataFavoritos().clear();
		pageFavoritos.clearAllArticulos();
		checkFavoritosWithoutArticles();
	}
	
	@Validation
	public ChecksTM checkFavoritosWithoutArticles() {
		var checks = ChecksTM.getNew();
		checks.add(
			"No queda ningún artículo en Favoritos",
			!pageFavoritos.hayArticulos());
		checks.add(
			"Aparece el botón \"Inspírate con lo último\"",
			pageFavoritos.isVisibleButtonEmpty(), Warn);
		return checks;
	}  
	
	@Step (
		description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
		expected="El artículo aparece en la bolsa")
	public void addArticuloToBag(ArticuloScreen artToAddBolsa) throws Exception {
		String refProductoToAdd = artToAddBolsa.getRefProducto();
		String codigoColor = artToAddBolsa.getCodigoColor();
		Talla tallaSelected = pageFavoritos.addArticleToBag(refProductoToAdd, codigoColor, 1);
		artToAddBolsa.setTalla(tallaSelected);
		dataTest.getDataBag().addArticulo(artToAddBolsa);
		
		new SecBolsaSteps().validaAltaArtBolsa();
	}
	
	@Step (
		description="Desde Favoritos seleccionamos la imagen del artículo <b>#{artToPlay.getRefProducto()}</b>",
		expected="Aparece el modal con la ficha del artículo")
	public void clickArticuloImg(ArticuloScreen artToPlay) {
		String refProducto = artToPlay.getRefProducto();
		String codigoColor = artToPlay.getCodigoColor();
		pageFavoritos.clickImgProducto(refProducto, codigoColor);
		new PageFichaSteps().checkIsFichaArtDisponible(refProducto, 2);
	}
}
