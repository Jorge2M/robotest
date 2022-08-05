package com.mng.robotest.test.steps.shop.favoritos;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.favoritos.PageFavoritos;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;


@SuppressWarnings({"static-access"})
public class PageFavoritosSteps {
	
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
		int maxSecondsToWaitCapa = 3;
		int maxSecondsToWaitArticles = 1;
		checks.add(
			"Está visible la capa de favoritos con artículos (la esperamos hasta " + maxSecondsToWaitCapa + " segundos)",
			pageFavoritos.isSectionArticlesVisibleUntil(maxSecondsToWaitCapa), State.Defect);
		checks.add(
			"Aparecen los artículos (los esperamos hasta " + maxSecondsToWaitArticles + " segundos): <br>" + dataFavoritos.getListArtDescHTML(),
			pageFavoritos.areVisibleArticlesUntil(dataFavoritos, maxSecondsToWaitArticles), State.Defect);
		return checks;
	}
	
	public void clearAll(DataFavoritos dataFavoritos, DataCtxShop dCtxSh) throws Exception {
		dataFavoritos.clear();
		clearAll(dCtxSh);
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
		int secondsToWait = 5;
		checks.add(
			"Aparece el modal de favoritos compartidos",
			pageFavoritos.checkShareModalUntill(secondsToWait), State.Defect);
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
		pageFavoritos.closeShareModal();
		int maxSeconds = 2;
		checkShareIsClosedUntil(maxSeconds);
	}
	
	@Validation (
		description="Desaparece el modal de favoritos compartidos (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean checkShareIsClosedUntil(int maxSeconds) {
		return (pageFavoritos.checkShareModalInvisible(maxSeconds));
	}
	
	@Step (
		description="Eliminamos de Favoritos el artículo con referencia <b>#{refArticulo}</b> y código de color <b>#{codColor}</b>",
		expected="El artículo desaparece de Favoritos")
	public void clear(String refArticulo, String codColor) {
		pageFavoritos.clearArticuloAndWait(refArticulo, codColor);
		int maxSeconds = 5;
		checkArticleDisappearsFromFavoritesUntil(refArticulo, codColor, maxSeconds);
	}
	
	@Validation (
		description="Desaparece de Favoritos el artículo con referencia <b>#{refArticle}</b> y código de color <b>#{codColor}</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkArticleDisappearsFromFavoritesUntil(String refArticle, String codColor, int maxSeconds) {
		return (pageFavoritos.isInvisibleArticleUntil(refArticle, codColor, maxSeconds));
	}
	
	@Step (
		description="Eliminamos de Favoritos los posibles artículos existentes",
		expected="No queda ningún artículo en Favoritos")
	public void clearAll(DataCtxShop dCtxSh) throws Exception {
		pageFavoritos.clearAllArticulos(dCtxSh.channel, dCtxSh.appE);
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
	public void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel, AppEcom app, Pais pais) 
	throws Exception {
		String refProductoToAdd = artToAddBolsa.getRefProducto();
		String codigoColor = artToAddBolsa.getCodigoColor();
		Talla tallaSelected = pageFavoritos.addArticleToBag(refProductoToAdd, codigoColor, 1);
		artToAddBolsa.setTalla(tallaSelected);
		dataBolsa.addArticulo(artToAddBolsa);
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(channel, app, pais);
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
