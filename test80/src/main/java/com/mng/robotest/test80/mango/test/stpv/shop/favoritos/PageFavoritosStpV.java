package com.mng.robotest.test80.mango.test.stpv.shop.favoritos;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;

@SuppressWarnings({"static-access"})
public class PageFavoritosStpV {
    
    public static ModalFichaFavoritosStpV modalFichaFavoritos;
    
    @Validation
    public static ChecksResult validaIsPageOK(DataFavoritos dataFavoritos, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWaitCapa = 3;
        int maxSecondsToWaitArticles = 1;
    	validations.add(
    		"Está visible la capa de favoritos con artículos (la esperamos hasta " + maxSecondsToWaitCapa + " segundos)<br>",
    		PageFavoritos.isSectionArticlesVisibleUntil(maxSecondsToWaitCapa, driver), State.Defect);
    	validations.add(
    		"Aparecen los artículos (los esperamos hasta " + maxSecondsToWaitArticles + " segundos): <br>" + dataFavoritos.getListArtDescHTML(),
    		PageFavoritos.areVisibleArticlesUntil(dataFavoritos, maxSecondsToWaitArticles, driver), State.Defect);
    	return validations;
    }
    
    public static void clearAll(DataFavoritos dataFavoritos, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        dataFavoritos.clear();
        clearAll(dCtxSh, driver);
    }
    
    public static void clear(ArticuloScreen articulo, DataFavoritos dataFavoritos, DataFmwkTest dFTest) throws Exception {
        dataFavoritos.removeArticulo(articulo);
        clear(articulo.getReferencia(), articulo.getCodigoColor(), dFTest);
    }
    
    @Step (
    	description="Seleccionar el link de favoritos compartidos",
    	expected="El modal de favoritos compartidos aparece correctamente")
    public static void clickShareIsOk(DataFmwkTest dFTest) {
    	PageFavoritos.openShareModal(dFTest.driver);
    	checkShareIsOk(dFTest.driver);
    }
    
    @Validation
    public static ChecksResult checkShareIsOk(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	int secondsToWait = 5;
    	validations.add(
    		"Aparece el modal de favoritos compartidos <br>",
    		PageFavoritos.checkShareModalUntill(secondsToWait, driver), State.Defect);
    	validations.add(
            "Aparece el boton de compartir por Telegram <br>",
            PageFavoritos.isShareTelegramFavoritesVisible(driver), State.Defect);
        validations.add(
            "Aparece el boton de compartir por WhatsApp <br>",
            PageFavoritos.isShareWhatsappFavoritesVisible(driver), State.Defect);
        validations.add(
            "Aparece la url para copiarla y compartir como texto", 
            PageFavoritos.isShareUrlFavoritesVisible(driver), State.Defect);
        return validations;
    }
    
    @Step (
        description="Cerramos el modal de favoritos compartidos",
        expected="El modal de favoritos compartidos desaparece correctamente")
    public static void closeShareModal(DataFmwkTest dFTest) throws Exception {
       	PageFavoritos.closeShareModal(dFTest.driver);
    	int maxSecondsWait = 2;
    	checkShareIsClosedUntil(maxSecondsWait, dFTest.driver);
    }
    
    @Validation (
        description="Desaparece el modal de favoritos compartidos (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Warn)
    public static boolean checkShareIsClosedUntil(int maxSecondsWait, WebDriver driver) {
    	return (PageFavoritos.checkShareModalInvisible(driver, maxSecondsWait));
    }
    
    @Step (
        description="Eliminamos de Favoritos el artículo con referencia <b>#{refArticulo}</b> y código de color <b>#{codColor}</b>",
        expected="El artículo desaparece de Favoritos")
    public static void clear(String refArticulo, String codColor, DataFmwkTest dFTest) throws Exception {
        PageFavoritos.clearArticuloAndWait(refArticulo, codColor, dFTest.driver);
        int maxSecondsWait = 5;
        checkArticleDisappearsFromFavoritesUntil(refArticulo, codColor, maxSecondsWait, dFTest.driver);
    }
    
    @Validation (
    	description="Desaparece de Favoritos el artículo con referencia <b>#{refArticle}</b> y código de color <b>#{codColor}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Defect)
    public static boolean checkArticleDisappearsFromFavoritesUntil(String refArticle, String codColor, int maxSecondsWait, WebDriver driver) {
    	return (PageFavoritos.isInvisibleArticleUntil(refArticle, codColor, maxSecondsWait, driver));
    }
    
    @Step (
    	description="Eliminamos de Favoritos los posibles artículos existentes",
    	expected="No queda ningún artículo en Favoritos")
    public static void clearAll(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        PageFavoritos.clearAllArticulos(dCtxSh.channel, dCtxSh.appE, driver);
        checkFavoritosWithoutArticles(driver);
    }
    
    @Validation
    public static ChecksResult checkFavoritosWithoutArticles(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"No queda ningún artículo en Favoritos<br>",
    		!PageFavoritos.hayArticulos(driver), State.Defect);
    	validations.add(
            "Aparece el botón \"Inspírate con lo último\"",
            PageFavoritos.isVisibleButtonEmpty(driver), State.Warn);
        return validations;
    }  
    
    @Step (
    	description="Desde Favoritos añadimos el artículo <b>#{artToAddBolsa.getRefProducto()}</b> (1a talla disponible) a la bolsa",
        expected="El artículo aparece en la bolsa")
    public static void addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        String refProductoToAdd = artToAddBolsa.getRefProducto();
        String codigoColor = artToAddBolsa.getCodigoColor();
        String tallaSelected = PageFavoritos.addArticleToBag(refProductoToAdd, codigoColor, 1, dFTest.driver);
        artToAddBolsa.setTallaAlf(tallaSelected);
        dataBolsa.addArticulo(artToAddBolsa);
        SecBolsaStpV.validaAltaArtBolsa(dataBolsa, channel, AppEcom.shop, dFTest.driver);
    }
    
    @Step (
    	description="Desde Favoritos seleccionamos la imagen del artículo <b>#{artToPlay.getRefProducto()}</b>",
        expected="Aparece el modal con la ficha del artículo")
    public static void clickArticuloImg(ArticuloScreen artToPlay, DataFmwkTest dFTest) {
        String refProducto = artToPlay.getRefProducto();
        String codigoColor = artToPlay.getCodigoColor();
        PageFavoritos.clickImgProducto(refProducto, codigoColor, dFTest.driver);
        modalFichaFavoritos.validaIsVisibleFicha(artToPlay, dFTest.driver);
    }
}
