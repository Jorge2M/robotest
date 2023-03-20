// Funciones genéricas basadas en el uso de WebDriver

package com.mng.robotest.test.generic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;
import com.mng.robotest.test.pageobject.shop.navigations.ArticuloNavigations;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class UtilsMangoTest extends PageBase {

	public void goToPaginaInicio() throws Exception {
		boolean existeLogo = new SecCabeceraMostFrequent().clickLogoMango();
		if (!existeLogo) {
			ITestContext ctx = getTestCase().getTestRunParent().getTestNgContext();
			String urlPaginaPostAcceso = (String)ctx.getAttribute(Constantes.ATTR_URL_PAG_POST_ACCESO); 
			if (urlPaginaPostAcceso!=null) {
				driver.get(urlPaginaPostAcceso);
			} else {
				if (state(Present, "//base").check()) {
					String urlBase = getElement("//base").getAttribute("href");
					driver.get(urlBase);
				}
			}
		}
	}	
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}

	public ArticuloScreen addArticuloBolsa(Article selArticulo) {
		var articulo = new ArticuloNavigations().selectArticuloTallaColorByRef(selArticulo);
		PageFicha.of(channel).clickAnadirBolsaButtonAndWait(); 
		return articulo;
	}
	
	public static float round(final float d, final int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
		return bd.floatValue();
	}

	/**
	 * Comprueba que dos floats están en un intervalo de %
	 */
	public static boolean importesEnIntervalo(final float importe1, final float importe2, final float intervalo) {
		boolean enIntervalo = false;
		if (importe1 >= importe2 * (1 - intervalo / 100.0) && importe1 <= importe2 * (1 + intervalo / 100.0)) {
			enIntervalo = true;
		}
		return enIntervalo;
	}
	
	/**
	 * Comprueba que el importe1 <= (importe2 * intervalo * (1/100))
	 */
	public static boolean importeEnIntervalo(final float importe1, final float importe2, final float intervalo) {
		boolean enIntervalo = false;
		if (importe1 <= (importe2 * (1 + (intervalo /100))) &&
			importe1 >= (importe2 * (1 - (intervalo /100)))) {
			enIntervalo = true;
		}

		return enIntervalo;
	}

	
	/**
	 * Funciona que coge los datos de una hashmap y los formatea para mostrarlos en el report HTML
	 */
	public static String listaCamposHTML(final Map<String, String> datosRegistro) {
		String resultado = "";
		Iterator<Map.Entry<String, String>> it = datosRegistro.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> dupla = it.next();
			resultado = resultado + "<br><b>" + dupla.getKey() + "</b>: " + dupla.getValue();
		}

		return resultado;
	}

	public static int randInt(final int min, final int max) throws NoSuchAlgorithmException{
		Random rand = SecureRandom.getInstanceStrong();
		return rand.nextInt(max - min + 1) + min;
	}
	
	/**
	 * Abrimos un enlacen en una nueva pestaña
	 */
	public void openLinkInNewTab(By by) {
		WebElement element = driver.findElement(by);
		openLinkInNewTab(element);
	}
	
	public void openLinkInNewTab(WebElement element) {
		Actions a = new Actions(driver);
		PageObjTM.moveToElement(element, driver);
		waitMillis(500);
		PageObjTM.moveToElement(element, driver);
		waitMillis(500);
		a.moveToElement(element)
			.keyDown(Keys.CONTROL)
			.click()
			.keyUp(Keys.CONTROL).build().perform();
		waitMillis(500);
	}
	
	/** Metodo de acceso a cualquier menú de la pantalla principal de Manto.
	 * Requiere de por lo menos un criterio de búsqueda, hasta dos opcionales (y en modo AND lógico) y el WebDriver para hacer un waitForPageLoaded 
	 */
	public void accesoMenusManto(String criterio1, String criterio2) {
		if (criterio1 != null && !criterio1.isEmpty() && "".compareTo(criterio1)!=0) {
			if (criterio2 != null && !criterio2.isEmpty() && "".compareTo(criterio2)!=0) {
				String xpathElem = "//a[text()[contains(.,'" + criterio1 + "') and contains(.,'" + criterio2 + "')]]";
				click(xpathElem).exec();
			} else {
				String xpathElem = "//a[text()[contains(.,'" + criterio1 + "')]]";
				click(xpathElem).exec();
			}
		}
		
		PageObjTM.waitForPageLoaded(driver, 5);
	}
	
	public boolean validarLinea(Linea linea) {
		return linea.isActiveIn(channel);
	}
	
	public static String getEmailForCheckout(Pais pais, boolean emailThatExists) {
		String emailCheckout = "";
		if (pais != null && pais.getEmailuser() != null && pais.getEmailuser().trim().compareTo("") != 0) {
			emailCheckout = pais.getEmailuser();
		} else {
			if (emailThatExists) {
				emailCheckout = Constantes.MAIL_PERSONAL;
			} else {
				emailCheckout = DataMango.getEmailNonExistentTimestamp();
			}
		}
		
		return emailCheckout;
	}
}