package com.mng.robotest.tests.domains.favoritos.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.favoritos.entity.Favorite;
import com.mng.robotest.tests.domains.favoritos.pageobjects.PageFavoritos;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;

import static com.github.jorge2m.testmaker.conf.State.*;

public class FavoritosSteps extends StepBase {
	
	private final PageFavoritos pgFavoritos = PageFavoritos.make(dataTest.getPais(), app);
	
	public PageFavoritos getPageFavoritos() {
		return pgFavoritos;
	}
	
	@Validation
	public ChecksTM checkPage() {
		var checks = ChecksTM.getNew();
		int secondsCapa = 3;
		int secondsArticles = 2;
		checks.add(
			"Está visible la capa de favoritos con artículos " + getLitSecondsWait(secondsCapa),
			pgFavoritos.isSectionArticlesVisible(secondsCapa));
		
		checks.add(
			"Aparecen los artículos " + getLitSecondsWait(secondsArticles) + ": <br>" + dataTest.getDataFavoritos().getListArtDescHTML(),
			pgFavoritos.isVisibleArticles(secondsArticles));
		
		return checks;
	}
	
	public void clear(Favorite favorite) {
		dataTest.getDataFavoritos().removeArticulo(favorite);
		clear(favorite.getReferencia(), favorite.getCodigoColor());
	}
	
	@Step (
		description="Seleccionar el link de favoritos compartidos",
		expected="El modal de favoritos compartidos aparece correctamente")
	public void clickShareIsOk() {
		pgFavoritos.openShareModal();
		checkShareIsOk();
	}
	
	@Validation
	public ChecksTM checkShareIsOk() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el modal de favoritos compartidos",
			pgFavoritos.checkShareModal(5));
		
		checks.add(
			"Aparece el boton de compartir por Telegram",
			pgFavoritos.isShareTelegramFavoritesVisible());
		
		checks.add(
			"Aparece el boton de compartir por WhatsApp",
			pgFavoritos.isShareWhatsappFavoritesVisible());
		
		checks.add(
			"Aparece la url para copiarla y compartir como texto", 
			pgFavoritos.isShareUrlFavoritesVisible());
		
		return checks;
	}
	
	@Step (
		description="Cerramos el modal de favoritos compartidos",
		expected="El modal de favoritos compartidos desaparece correctamente")
	public void closeShareModal() {
		pgFavoritos.closeSharedModal();
		checkShareIsClosedUntil(2);
	}
	
	@Validation (
		description="Desaparece el modal de favoritos compartidos " + SECONDS_WAIT,
		level=WARN)
	public boolean checkShareIsClosedUntil(int seconds) {
		return pgFavoritos.checkShareModalInvisible(seconds);
	}
	
	@Step (
		description="Eliminamos de Favoritos el artículo con referencia <b>#{refArticulo}</b> y código de color <b>#{codColor}</b>",
		expected="El artículo desaparece de Favoritos")
	public void clear(String refArticulo, String codColor) {
		pgFavoritos.clearArticulo(refArticulo, codColor);
		checkArticleDisappearsFromFavoritesUntil(refArticulo, codColor, 8);
	}
	
	@Validation (
		description="Desaparece de Favoritos el artículo con referencia <b>#{refArticle}</b> y código de color <b>#{codColor}</b> " + SECONDS_WAIT)
	public boolean checkArticleDisappearsFromFavoritesUntil(String refArticle, String codColor, int seconds) {
		return pgFavoritos.isInvisibleArticle(refArticle, codColor, seconds);
	}
	
	@Step (
		description="Eliminamos de Favoritos los posibles artículos existentes",
		expected="No queda ningún artículo en Favoritos")
	public void clearAll() {
		dataTest.getDataFavoritos().clear();
		pgFavoritos.clearAllArticulos();
		checkFavoritosWithoutArticles();
	}
	
	@Validation
	public ChecksTM checkFavoritosWithoutArticles() {
		var checks = ChecksTM.getNew();
		checks.add(
			"No queda ningún artículo en Favoritos",
			!pgFavoritos.isArticulos());
		
		checks.add(
			"Aparece el botón \"Inspírate con lo último\"",
			pgFavoritos.isListEmpty(), WARN);
		
		return checks;
	}  
	
	@Step (
		description="Desde Favoritos seleccionamos la imagen del artículo <b>#{artToPlay.getReferencia()}</b>",
		expected="Aparece el modal con la ficha del artículo")
	public void clickArticuloImg(Favorite artToPlay) {
		String refProducto = artToPlay.getReferencia();
		String codigoColor = artToPlay.getCodigoColor();
		pgFavoritos.clickProducto(refProducto, codigoColor);
		new FichaSteps().checkIsFichaArtDisponible(refProducto, 2);
	}
	
}
