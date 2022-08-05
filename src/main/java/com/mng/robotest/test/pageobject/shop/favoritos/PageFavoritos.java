package com.mng.robotest.test.pageobject.shop.favoritos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageFavoritos extends PageObjTM {
  
	private final ModalFichaFavoritos modalFichaFavoritos;
	
	private static final String XPATH_BLOCK_FAVORITOS = "//div[@data-pais and @class[contains(.,'favorites')]]";
	private static final String XPATH_BLOCK_FAV_WITH_ART = XPATH_BLOCK_FAVORITOS + "//div[@class[contains(.,'content-garments')]]";
	private static final String XPATH_ARTICULO = "//ul[@id='contentDataFavs']/li";
	private static final String XPATH_BUTTON_EMPTY = "//a[@class='favorites-empty-btn']";
	private static final String XPATH_SHARE_MODAL_BUTTON = "//span[@id='shareIcon']";
	private static final String XPATH_CLOSE_SHARE_MODAL_BUTTON = "//span[@onclick[contains(.,'showCloseModalShare')]]";
	private static final String XPATH_WHATSAPP_SHARE_BUTTON = "//span[@class='modal-share-whatsapp-icon']";
	private static final String XPATH_TELEGRAM_SHARE_BUTTON = "//span[@class='modal-share-telegram-icon']";
	private static final String XPATH_URL_SHARE_LABEL = "//div[@id='linkShareButton']";
	
	private PageFavoritos(WebDriver driver) {
		super(driver);
		this.modalFichaFavoritos = new ModalFichaFavoritos();
	}
	
	public static PageFavoritos getNew(WebDriver driver) {
		return new PageFavoritos(driver);
	}
	
	public ModalFichaFavoritos getModalFichaFavoritos() {
		return this.modalFichaFavoritos;
	}
	
	private String getXPathArticle(String refProducto, String codigoColor) {
		String xpathNew = "@style[contains(.,'" + refProducto + "_" + codigoColor + "')]"; //div
		return (XPATH_ARTICULO + "//*[" + xpathNew + "]/ancestor::li");
	}
	
	private String getXPathButtonAddBolsa(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'garment-btn')] or @id[contains(.,'garmentBtn')]]");
	}
	
	private String getXPathImgProducto(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor) + "//div[@class[contains(.,'garment-image')]]");
	}	
	
	private String getXPathAspaBorrar(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor)  + "//span[@class[contains(.,'icofav-eliminar')]]");
	}
	
	private String getXPathCapaTallas(String refProducto, String codigoColor) {
		return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'modalSelectSize')]]");
	}

	private String getXPathTalla(String refProducto, String codigoColor) {
		return (getXPathCapaTallas(refProducto, codigoColor) + "//li[@onclick[contains(.,'changeSize')]]");
	} 
	
	// Funcionalidad de Share Favorites (pre)
	
	private String getXPathWithIdItem(int numArticulo) {
		String idItem = driver.findElement(By.xpath("(" + XPATH_ARTICULO + ")[" + numArticulo + "]")).getAttribute("id");
		return (XPATH_ARTICULO + "[@id='" + idItem + "']");
	}
	
	public void openShareModal() {
		driver.findElement(By.xpath(XPATH_SHARE_MODAL_BUTTON)).click();
	}

	public void closeShareModal() {
		click(By.xpath(XPATH_CLOSE_SHARE_MODAL_BUTTON)).type(javascript).exec();
	}

	public boolean checkShareModalUntill(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_CLOSE_SHARE_MODAL_BUTTON), driver)
				.wait(maxSeconds).check());
	}
	
	public boolean isShareFavoritesVisible() {
		return (state(Visible, By.xpath(XPATH_SHARE_MODAL_BUTTON), driver).check());
	}
	
	public boolean isShareWhatsappFavoritesVisible() {
		return (state(Visible, By.xpath(XPATH_WHATSAPP_SHARE_BUTTON), driver).check());
	}
	
	public boolean isShareTelegramFavoritesVisible() {
		return (state(Visible, By.xpath(XPATH_TELEGRAM_SHARE_BUTTON), driver).check());
	}
	
	public boolean isShareUrlFavoritesVisible() {
		return (state(Visible, By.xpath(XPATH_URL_SHARE_LABEL), driver).check());
	}
	
	public boolean checkShareModalInvisible(int maxSeconds) {
		return (state(Invisible, By.xpath(XPATH_CLOSE_SHARE_MODAL_BUTTON), driver)
				.wait(maxSeconds).check());
	}

	public boolean isSectionVisible() {
		return (state(Visible, By.xpath(XPATH_BLOCK_FAVORITOS), driver).check());
	}
	
	public boolean isSectionArticlesVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_BLOCK_FAV_WITH_ART), driver)
				.wait(maxSeconds).check());
	}
	
	public void clearArticuloAndWait(String refArticulo, String codColorArticulo) {
		String xpathBorrar = getXPathAspaBorrar(refArticulo, codColorArticulo);
		
		//Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
		click(By.xpath(xpathBorrar)).type(javascript).exec();
	}
	
	public boolean isInvisibleArticleUntil(String referencia, String codColor, int maxSeconds) {
		String xpathArticulo = getXPathArticle(referencia, codColor);
		return (state(Invisible, By.xpath(xpathArticulo), driver)
				.wait(maxSeconds).check());
	}
	
	@SuppressWarnings("static-access")
	public void clearAllArticulos(Channel channel, AppEcom appE) {
		//Si la sección no es visible clickamos en favoritos
		if (!isSectionVisible()) {
			SecMenusWrap secMenus = SecMenusWrap.getNew(channel, appE, driver);
			secMenus.getMenusUser().clickMenuAndWait(UserMenu.favoritos);
		}
		int i=0; //Para evitar posibles bucles infinitos
		while (hayArticulos() && i<50) {
			clear1rstArticuloAndWait();
			i+=1;
		}
	}
	
	public boolean hayArticulos() {
		return (state(Present, By.xpath(XPATH_ARTICULO), driver).check());
	}
	
	public boolean areVisibleArticlesUntil(DataFavoritos dataFavoritos, int maxSecondsToWait) {
		if (dataFavoritos.isEmpty()) {
			return (!hayArticulos());
		}
		
		Iterator<ArticuloScreen> itArticulos = dataFavoritos.getListArticulos().iterator();
		while (itArticulos.hasNext()) {
			ArticuloScreen articulo = itArticulos.next();
			if (!isVisibleArticleUntil(articulo.getRefProducto(), articulo.getCodigoColor(), maxSecondsToWait)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isVisibleArticleUntil(String refArticulo, String codigoColor, int maxSeconds) {
		String xpathArt = getXPathArticle(refArticulo, codigoColor);
		return (state(Visible, By.xpath(xpathArt), driver)
				.wait(maxSeconds).check());
	}
	
	public void clear1rstArticuloAndWait() {
		if (hayArticulos()) {
			String xpathArtWithIdItem = getXPathWithIdItem(1);
			
			//Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
			By byElem = By.xpath(xpathArtWithIdItem + "//span[@class[contains(.,'icofav-eliminar')]]");
			click(byElem).type(javascript).exec();
			new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathArtWithIdItem)));
		}
	}

	public Talla addArticleToBag(String refProducto, String codigoColor, int posicionTalla) throws Exception {
		clickButtonAddToBagAndWait(refProducto, codigoColor);
		Talla tallaSelected = selectTallaAvailableAndWait(refProducto, codigoColor, posicionTalla);
		return tallaSelected;
	}
	
	public void clickButtonAddToBagAndWait(String refProducto, String codigoColor) throws Exception {
		clickButtonAddToBag(refProducto, codigoColor);
		
		//Wait to Div tallas appears
		String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCapaTallas)));
	}
	
	private void clickButtonAddToBag(String refProducto, String codigoColor) {
		String xpathAdd = getXPathButtonAddBolsa(refProducto, codigoColor);
		try {
			driver.findElement(By.xpath(xpathAdd)).click();
		} catch (ElementClickInterceptedException e) {
			//En ocasiones en el canal móvil se solapa el div del Asistente Online de ayuda
			//así que esperamos un tiempo prudencial hasta que se pliegue
			waitMillis(2000);
			driver.findElement(By.xpath(xpathAdd)).click();
		}
	}
	
	public void clickImgProducto(String refProducto, String codigoColor) {
		String xpathImg = getXPathImgProducto(refProducto, codigoColor);
		driver.findElement(By.xpath(xpathImg)).click();
	}
	
	public List<WebElement> getListaTallas(String refProducto, String codigoColor) {
		String xpathTalla = getXPathTalla(refProducto, codigoColor);
		return (driver.findElements(By.xpath(xpathTalla)));
	}
	
	public String selectTallaAndWait(String refProducto, String codigoColor, int posicionTalla, Channel channel, AppEcom app, Pais pais) {
		List<WebElement> listaTallas = getListaTallas(refProducto, codigoColor);
		WebElement talla = listaTallas.get(posicionTalla);
		String litTalla = talla.getText();
		talla.click();
		int maxSecondsToWait = 2;
		
		SecBolsa secBolsa = SecBolsa.make(channel, app, pais, driver);
		secBolsa.isInStateUntil(StateBolsa.OPEN, maxSecondsToWait);
		return litTalla;
	}
	
	public Talla selectTallaAvailableAndWait(String refProducto, String codigoColor, int posicionTalla) {
		//Filtramos y nos quedamos sólo con las tallas disponibles
		List<WebElement> listTallas = getListaTallas(refProducto, codigoColor);
		List<WebElement> listTallasAvailable = new ArrayList<>();
		for (WebElement talla : listTallas) {
			if (!state(Present, talla, driver).by(By.xpath("./span")).check()) {
				listTallasAvailable.add(talla);
			}
		}
	   
		WebElement tallaDisponible = listTallasAvailable.get(posicionTalla - 1); 
		Talla talla = Talla.fromLabel(tallaDisponible.getText());
		tallaDisponible.click();
		
		//Wait to Div tallas disappears
		String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
		new WebDriverWait(driver, 1).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathCapaTallas)));
		
		return talla;
	}
	
	public boolean isVisibleButtonEmpty() {
		return (state(Visible, By.xpath(XPATH_BUTTON_EMPTY), driver).check());
	}
}