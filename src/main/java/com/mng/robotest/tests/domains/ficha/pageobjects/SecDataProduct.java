package com.mng.robotest.tests.domains.ficha.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.tallas.SSecSelTallasFicha;
import com.mng.robotest.testslegacy.data.Constantes;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.utils.ImporteScreen;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.testslegacy.data.PaisShop.CROATIA;

public class SecDataProduct extends PageBase {

	private final SSecSelTallasFicha secSelTallas = SSecSelTallasFicha.make(channel, app);

	private static final String XP_NOMBRE_ARTICULO_DESKTOP = "//h1[@itemprop='name']";

	//Existe un Test A/B que hace que el nombre del artículo salga debajo del botón de "Añadir a la bolsa" o en la cabecera, por eso el or.
	private static final String XP_NOMBRE_ARTICULO_MOBIL = "//*[@class[contains(.,'product-info-name')] or @class='headerMobile__text']";

	public SSecSelTallasFicha getSecSelTallas() {
		return secSelTallas;
	}

	//xpaths asociados a los colores
	private static final String XP_COLOR = "//*[@class[contains(.,'color-container')]]";
	private static final String CLASS_COLOR_NO_DISP = "@class[contains(.,'--no-stock')] or @class[contains(.,'--cross-out')]";
	public enum ColorType implements ElementPage {
		SELECTED("//div[@class[contains(.,'color-container--selected')]]"),
		LAST(XP_COLOR + "[last()]"),
		AVAILABLE(XP_COLOR + "/img[not(" + CLASS_COLOR_NO_DISP + ")]/.."),
		UNAVAILABLE(XP_COLOR + "/img[" + CLASS_COLOR_NO_DISP + "]/..");

		String xpath;
		By by;
		private ColorType(String xPath) {
			xpath = xPath;
			by = By.xpath(xpath);
		}

		@Override
		public By getBy() {
			return by;
		}
		public String getXPath() {
			return xpath;
		}
		public String getXpathIcon() {
			return xpath + "/img";
		}
	}

	private static final String XP_NOMBRE_COLOR_SELECTED_DESKTOP = "//*[@class='colors-info-name']";

	//xpaths asociados al tema tallas
	private static final String XP_CAPA_AVISAME = "//*[@id='bocataAvisame']";
	private static final String XP_GUIA_DE_TALLAS_LINK = "//*[@id='productFormSizesGuide']";

	//xpaths asociados a los precios
	private static final String XP_PRECIO_FINAL = "//span[@data-testid='currentPrice']";
	private static final String XP_PRECIO_FINAL_CROATIA = XP_PRECIO_FINAL + "/span/span";
	private static final String XP_PRECIO_REBAJADO = "//span[@data-testid[contains(.,'crossedOutPrice')]]";

	//xpaths asociados a los colores de la prenda
	private static final String XP_COLORES_PRENDA_SIN_IDENTIFICAR = "//*[@class[contains(.,'color-container')]]";

	private String getXPathPastillaColorClick(String codigoColor) {
		return ("//*[@class[contains(.,'color-container')] and @id='" + codigoColor + "']/img");
	}

	//xpath asociados a los datos básicoos del artículo (nombre y referencia)
	public String getXPathLinReferencia(String referencia) {
		return "//*[@class[contains(.,'-reference')] and text()[contains(.,'" + referencia + "')]]";
	}

	private String getXPathNombreArt() {
		if (channel.isDevice()) {
			return XP_NOMBRE_ARTICULO_MOBIL;
		}
		return XP_NOMBRE_ARTICULO_DESKTOP;
	}
	private String getXPathPrecioFinal() {
		if (CROATIA.isEquals(dataTest.getPais())) {
			return XP_PRECIO_FINAL_CROATIA;
		}
		return XP_PRECIO_FINAL;
	}

	public ArticuloScreen getArticuloObject() {
		var articulo = new ArticuloScreen();
		articulo.setReferencia(getReferenciaProducto());
		articulo.setNombre(getTituloArt());
		articulo.setPrecio(getPrecioFinalArticulo());
		articulo.setCodigoColor(getCodeColor(ColorType.SELECTED));
		articulo.setColorName(getNombreColorSelected());
		articulo.setTalla(secSelTallas.getTallaSelected(app, PaisShop.from(dataTest.getCodigoPais())));
		articulo.setNumero(1);
		return articulo;
	}

	//Funciones referentes a los datos básicos del artículo
	public String getReferenciaProducto() {
		String url = driver.getCurrentUrl();
		Pattern pattern = Pattern.compile("_(.*?).html");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	public String getTituloArt() {
		String xpathNombreArt = getXPathNombreArt();
		List<WebElement> listArticles = getElementsVisible(xpathNombreArt);
		if (!listArticles.isEmpty()) {
			WebElement tituloArt = listArticles.get(0);
			if (tituloArt!=null) {
				return (tituloArt.getText());
			}
		}
		return "";
	}

//Funciones referentes a los colores

	public int getNumColors() {
		return getListColors().size();
	}

	public void clickColor(int numColor) {
		WebElement color = getListColors().get(numColor - 1);
		click(color).exec();
	}

	private List<WebElement> getListColors() {
		return getElements(ColorType.AVAILABLE.getXPath());
	}

	public String getCodeColor(ColorType colorType) {
		WebElement color = getElementWeb(colorType.getXPath());
		return (color.getAttribute("id"));
	}

	public String getNombreColorMobil(ColorType colorType) {
		WebElement color = getElementWeb(colorType.getXPath());
		if (color!=null) { 
			String colorName = color.getAttribute("aria-label");
			return normalizeColorName(colorName);
		}
		return Constantes.COLOR_DESCONOCIDO;
	}
	private String normalizeColorName(String colorName) {
		//Eliminamos el literal " seleccionado"
		Pattern pattern = Pattern.compile("(.*) (.*)");
		Matcher matcher = pattern.matcher(colorName);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return colorName;
	}

	public String getNombreColorSelected() {
		switch (channel) {
			case desktop:
				if (state(PRESENT, XP_NOMBRE_COLOR_SELECTED_DESKTOP).check()) {
					return getElement(XP_NOMBRE_COLOR_SELECTED_DESKTOP).getText();
				}
				return Constantes.COLOR_DESCONOCIDO;
			case mobile:
			default:
				return (getNombreColorMobil(ColorType.SELECTED));
		}
	}

	public boolean checkPotatoe () {
		return state(PRESENT, XP_NOMBRE_COLOR_SELECTED_DESKTOP).check();
	}

	public void selectColorWaitingForAvailability(String codigoColor) {
		click(getXPathPastillaColorClick(codigoColor))
				.type(JAVASCRIPT)
				.waitLink(3).waitLoadPage(5).exec();
	}

	public boolean isClickableColor(String codigoColor) {
		String xpathColor = getXPathPastillaColorClick(codigoColor);
		return state(CLICKABLE, xpathColor).check();
	}

	//Funciones referentes a los precios
	public String getPrecioFinalArticulo() {
		state(VISIBLE, getXPathPrecioFinal()).wait(1).check();
		var precioElem = getElementVisible(getXPathPrecioFinal());
		String precioArticulo = precioElem.getText();
		return (ImporteScreen.normalizeImportFromScreen(precioArticulo));
	}

	/**
	 * Extrae (si existe) el precio rebajado de la página de ficha de producto. Si no existe devuelve ""
	 */
	public String getPrecioTachadoFromFichaArt() {
		if (state(PRESENT, XP_PRECIO_REBAJADO).check()) {
			String precioRebajado = getElementVisible(XP_PRECIO_REBAJADO).getText();
			return ImporteScreen.normalizeImportFromScreen(precioRebajado);
		}
		return "";
	}

	//Funciones referentes a las tallas (en algunas se actúa a modo de Wrapper)
	public boolean isVisibleCapaAvisame() {
		return state(VISIBLE, XP_CAPA_AVISAME).check();
	}

	public void selectGuiaDeTallasLink() {
		click(XP_GUIA_DE_TALLAS_LINK).exec();
	}

	public boolean selectGuiaDeTallasIfVisible() {
		boolean isVisible = state(VISIBLE, XP_GUIA_DE_TALLAS_LINK).check();
		if (isVisible) {
			selectGuiaDeTallasLink();
		}
		return isVisible;
	}



	//zona de colores dentro de la ficha

	public List<String> getColorsGarment() {
		List<String> colors = new ArrayList<>();
		for (WebElement element : getElements(XP_COLORES_PRENDA_SIN_IDENTIFICAR)) {
			colors.add(element.getAttribute("id"));
		}
		return colors;
	}

	public void selectColor(String codeColor) {
		String xpath = getXPathPastillaColorClick(codeColor);
		click(xpath).exec();
	}
}
