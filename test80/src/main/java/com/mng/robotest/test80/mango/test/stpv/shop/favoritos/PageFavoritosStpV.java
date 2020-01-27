package com.mng.robotest.test80.mango.test.stpv.shop.favoritos;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;

@SuppressWarnings({"static-access"})
public class PageFavoritosStpV {
    
	private final PageFavoritos pageFavoritos;
    private final ModalFichaFavoritosStpV modalFichaFavoritosStpV;
    private final WebDriver driver;
    
    private PageFavoritosStpV(WebDriver driver) {
    	this.pageFavoritos = PageFavoritos.getNew(driver);
    	this.modalFichaFavoritosStpV = ModalFichaFavoritosStpV.getNew(pageFavoritos.getModalFichaFavoritos());
    	this.driver = driver;
    }
    
    public static PageFavoritosStpV getNew(WebDriver driver) {
    	return new PageFavoritosStpV(driver);
    }
    
    public PageFavoritos getPageFavoritos() {
    	return pageFavoritos;
    }
    
    public ModalFichaFavoritosStpV getModalFichaFavoritosStpV() {
    	return modalFichaFavoritosStpV;
    }
    
    @Validation
    public ChecksResult validaIsPageOK(DataFavoritos dataFavoritos) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWaitCapa = 3;
        int maxSecondsToWaitArticles = 1;
    	validations.add(
    		"Está visible la capa de favoritos con artículos (la esperamos hasta " + maxSecondsToWaitCapa + " segundos)",
    		pageFavoritos.isSectionArticlesVisibleUntil(maxSecondsToWaitCapa), State.Defect);
    	validations.add(
    		"Aparecen los artículos (los esperamos hasta " + maxSecondsToWaitArticles + " segundos): <br>" + dataFavoritos.getListArtDescHTML(),
    		pageFavoritos.areVisibleArticlesUntil(dataFavoritos, maxSecondsToWaitArticles), State.Defect);
    	return validations;
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
    public ChecksResult checkShareIsOk() {
    	ChecksResult validations = ChecksResult.getNew();
    	int secondsToWait = 5;
    	validations.add(
    		"Aparece el modal de favoritos compartidos",
    		pageFavoritos.checkShareModalUntill(secondsToWait), State.Defect);
    	validations.add(
            "Aparece el boton de compartir por Telegram",
            pageFavoritos.isShareTelegramFavoritesVisible(), State.Defect);
        validations.add(
            "Aparece el boton de compartir por WhatsApp",
            pageFavoritos.isShareWhatsappFavoritesVisible(), State.Defect);
        validations.add(
            "Aparece la url para copiarla y compartir como texto", 
            pageFavoritos.isShareUrlFavoritesVisible(), State.Defect);
        return validations;
    }
    
    @Step (
        description="Cerramos el modal de favoritos compartidos",
        expected="El modal de favoritos compartidos desaparece correctamente")
    public void closeShareModal() throws Exception {
    	pageFavoritos.closeShareModal();
    	int maxSecondsWait = 2;
    	checkShareIsClosedUntil(maxSecondsWait);
    }
    
    @Validation (
        description="Desaparece el modal de favoritos compartidos (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Warn)
    public boolean checkShareIsClosedUntil(int maxSecondsWait) {
    	return (pageFavoritos.checkShareModalInvisible(maxSecondsWait));
    }
    
    @Step (
        description="Eliminamos de Favoritos el artículo con referencia <b>#{refArticulo}</b> y código de color <b>#{codColor}</b>",
        expected="El artículo desaparece de Favoritos")
    public void clear(String refArticulo, String codColor) throws Exception {
    	pageFavoritos.clearArticuloAndWait(refArticulo, codColor);
        int maxSecondsWait = 5;
        checkArticleDisappearsFromFavoritesUntil(refArticulo, codColor, maxSecondsWait);
    }
    
    @Validation (
    	description="Desaparece de Favoritos el artículo con referencia <b>#{refArticle}</b> y código de color <b>#{codColor}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Defect)
    public boolean checkArticleDisappearsFromFavoritesUntil(String refArticle, String codColor, int maxSecondsWait) {
    	return (pageFavoritos.isInvisibleArticleUntil(refArticle, codColor, maxSecondsWait));
    }
    
    @Step (
    	description="Eliminamos de Favoritos los posibles artículos existentes",
    	expected="No queda ningún artículo en Favoritos")
    public void clearAll(DataCtxShop dCtxSh) throws Exception {
    	pageFavoritos.clearAllArticulos(dCtxSh.channel, dCtxSh.appE);
        checkFavoritosWithoutArticles();
    }
    
    @Validation
    public ChecksResult checkFavoritosWithoutArticles() {
    	ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"No queda ningún artículo en Favoritos",
    		!pageFavoritos.hayArticulos(), State.Defect);
    	validations.add(
            "Aparece el botón \"Inspírate con lo último\"",
            pageFavoritos.isVisibleButtonEmpty(), State.Warn);
        return validations;
    }  
    
    @Step (
    	description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
        expected="El artículo aparece en la bolsa")
    public void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel) 
    throws Exception {
        String refProductoToAdd = artToAddBolsa.getRefProducto();
        String codigoColor = artToAddBolsa.getCodigoColor();
        Talla tallaSelected = pageFavoritos.addArticleToBag(refProductoToAdd, codigoColor, 1);
        artToAddBolsa.setTalla(tallaSelected);
        dataBolsa.addArticulo(artToAddBolsa);
        SecBolsaStpV.validaAltaArtBolsa(dataBolsa, channel, AppEcom.shop, driver);
    }
    
    @Step (
    	description="Desde Favoritos seleccionamos la imagen del artículo <b>#{artToPlay.getRefProducto()}</b>",
        expected="Aparece el modal con la ficha del artículo")
    public void clickArticuloImg(ArticuloScreen artToPlay) {
        String refProducto = artToPlay.getRefProducto();
        String codigoColor = artToPlay.getCodigoColor();
        pageFavoritos.clickImgProducto(refProducto, codigoColor);
        modalFichaFavoritosStpV.validaIsVisibleFicha(artToPlay);
    }
}
