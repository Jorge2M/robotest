package com.mng.robotest.domains.favoritos.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.favoritos.pageobjects.PageFavoritos;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.generic.beans.ArticuloScreen;

public class PageFavoritosSteps extends StepBase {
	
	private final PageFavoritos pageFavoritos = new PageFavoritos();
	private final ModalFichaFavoritosSteps modalFichaFavoritosSteps = new ModalFichaFavoritosSteps();
	
	public PageFavoritos getPageFavoritos() {
		return pageFavoritos;
	}
	
	public ModalFichaFavoritosSteps getModalFichaFavoritosSteps() {
		return modalFichaFavoritosSteps;
	}
	
	@Validation
	public ChecksTM validaIsPageOK(DataFavoritos dataFavoritos) {
		ChecksTM checks = ChecksTM.getNew();
		int secondsCapa = 3;
		int secondsArticles = 1;
		checks.add(
			"Está visible la capa de favoritos con artículos (la esperamos hasta " + secondsCapa + " segundos)",
			pageFavoritos.isSectionArticlesVisibleUntil(secondsCapa), State.Defect);
		checks.add(
			"Aparecen los artículos (los esperamos hasta " + secondsArticles + " segundos): <br>" + dataFavoritos.getListArtDescHTML(),
			pageFavoritos.areVisibleArticlesUntil(dataFavoritos, secondsArticles), State.Defect);
		return checks;
	}
	
	public void clearAll(DataFavoritos dataFavoritos) throws Exception {
		dataFavoritos.clear();
		clearAll();
	}
	
	public void clear(ArticuloScreen articulo, DataFavoritos dataFavoritos) throws Exception {
		dataFavoritos.removeArticulo(articulo);
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
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece el modal de favoritos compartidos",
			pageFavoritos.checkShareModalUntill(5), State.Defect);
		
		checks.add(
			"Aparece el boton de compartir por Telegram",
			pageFavoritos.isShareTelegramFavoritesVisible(), State.Defect);
		
		checks.add(
			"Aparece el boton de compartir por WhatsApp",
			pageFavoritos.isShareWhatsappFavoritesVisible(), State.Defect);
		
		checks.add(
			"Aparece la url para copiarla y compartir como texto", 
			pageFavoritos.isShareUrlFavoritesVisible(), State.Defect);
		
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
		level=State.Warn)
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
		description="Desaparece de Favoritos el artículo con referencia <b>#{refArticle}</b> y código de color <b>#{codColor}</b> (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean checkArticleDisappearsFromFavoritesUntil(String refArticle, String codColor, int seconds) {
		return (pageFavoritos.isInvisibleArticleUntil(refArticle, codColor, seconds));
	}
	
	@Step (
		description="Eliminamos de Favoritos los posibles artículos existentes",
		expected="No queda ningún artículo en Favoritos")
	public void clearAll() throws Exception {
		pageFavoritos.clearAllArticulos();
		checkFavoritosWithoutArticles();
	}
	
	@Validation
	public ChecksTM checkFavoritosWithoutArticles() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"No queda ningún artículo en Favoritos",
			!pageFavoritos.hayArticulos(), State.Defect);
		checks.add(
			"Aparece el botón \"Inspírate con lo último\"",
			pageFavoritos.isVisibleButtonEmpty(), State.Warn);
		return checks;
	}  
	
	@Step (
		description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
		expected="El artículo aparece en la bolsa")
	public void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa) throws Exception {
		String refProductoToAdd = artToAddBolsa.getRefProducto();
		String codigoColor = artToAddBolsa.getCodigoColor();
		Talla tallaSelected = pageFavoritos.addArticleToBag(refProductoToAdd, codigoColor, 1);
		artToAddBolsa.setTalla(tallaSelected);
		dataBolsa.addArticulo(artToAddBolsa);
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		secBolsaSteps.validaAltaArtBolsa(dataBolsa);
	}
	
	@Step (
		description="Desde Favoritos seleccionamos la imagen del artículo <b>#{artToPlay.getRefProducto()}</b>",
		expected="Aparece el modal con la ficha del artículo")
	public void clickArticuloImg(ArticuloScreen artToPlay) {
		String refProducto = artToPlay.getRefProducto();
		String codigoColor = artToPlay.getCodigoColor();
		pageFavoritos.clickImgProducto(refProducto, codigoColor);
		modalFichaFavoritosSteps.validaIsVisibleFicha(artToPlay);
	}
}
