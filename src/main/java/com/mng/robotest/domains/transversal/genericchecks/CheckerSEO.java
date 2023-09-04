package com.mng.robotest.domains.transversal.genericchecks;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.genericchecks.Checker;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.domains.transversal.home.pageobjects.PageLanding;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

public class CheckerSEO extends PageBase implements Checker {

	public static final String XPATH_TAG_CANONICAL = "//link[@rel='canonical']";
	public static final String XPATH_TAG_ROBOTS = "//meta[@name='robots' and @content[contains(.,'noindex')]]";
	
	private final State level;
	
	public CheckerSEO(State level) {
		this.level = level;
	}
	
	public ChecksTM check(WebDriver driver) {
		var checks = ChecksTM.getNew();
		
		ResultadoErrores resValidac = validacionesGenericasSEO(driver);
		String descripValidac = "Se cumplen las validaciones genéricas de SEO";
		if (!resValidac.isOK()) {
			descripValidac += resValidac.getlistaLogError().toString();
		}
		checks.add(
			Check.make(
			    descripValidac,
			    resValidac.isOK(), level)
			.store(StoreType.None).build());
		
		return checks;
	}
	
	private ResultadoErrores validacionesGenericasSEO(WebDriver driver) {

		ResultadoErrores resultado = new ResultadoErrores();
		resultado.setResultado(ResultadoErrores.Resultado.OK);
		ArrayList<String> listaProblemas = new ArrayList<>();		
		try {
			//Validaciones a nivel del "Title"
			listaProblemas.addAll(validaTagTitle(driver));
	
//			//Validaciones a nivel del meta "Description"
//			listaProblemas.addAll(validaMetaDescription(driver));
	
			//Validaciones a nivel del "Canonical"
			listaProblemas.addAll(validaCanonical(driver));

			//Validación a nivel del tag "Robots"
			listaProblemas.addAll(validacionRobots(driver));
		}
		catch (Exception e) {
			listaProblemas.add("<br><b style=\"color:" + Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Se ha producido una excepción (" + e.getMessage() + ") durante las validaciones</c>");
		}
		
		if (!listaProblemas.isEmpty()) {
			listaProblemas.add("<br><br><c style=\"color:brown\"><b>SEO:</b> problemas en página con URL <a href=\"" + driver.getCurrentUrl() + "\">" + driver.getCurrentUrl() + "</a>:");
			resultado.setResultado(ResultadoErrores.Resultado.ERRORES);
			resultado.setListaLogError(listaProblemas);
		}

		return resultado;
	}
	
	/**
	 * @return lista de errores en formato HTML referidos al tag "Title"
	 */
	private List<String> validaTagTitle(WebDriver driver) {
		List<String> listaErrorsInHtmlFormat = new ArrayList<>();
		String title = driver.getTitle();
		if (title == null || "".compareTo(title) == 0) {
			listaErrorsInHtmlFormat.add("<br><b style=\"color:" + Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">No existe el title o es nulo</c>");
		}
		return listaErrorsInHtmlFormat;
	}
	
	private List<String> validaCanonical(WebDriver driver) {
		List<String> listaErrorsInHtmlFormat = new ArrayList<>();
		if (!isPresentTagCanonical()) {
			//El canonical ha de aparecer como mínimo en las páginas de Portada, Catálogo y Ficha
			PageFicha pageFicha = PageFicha.of(channel);
			PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop);
			if ((new PageLanding()).isPage() || 
				((PageGaleriaDesktop)pageGaleria).isPage() || 
				pageFicha.isPageUntil(0)) {
				String currentURL = driver.getCurrentUrl(); 
				//Hemos de añadir un par de excepciones
				if (!currentURL.contains("catalogPc.faces?") && !currentURL.contains("search?") && !currentURL.contains("favorites.faces?")) {
					listaErrorsInHtmlFormat.add("<br><b style=\"color:" + Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Estamos en Home (no favorites.faces?), Galeria (no catalogPc.faces? ni search?) o Ficha pero no existe el tag canonical</c>");
				}
			}
		}
		
		return listaErrorsInHtmlFormat;
	}
	
	private List<String> validacionRobots(WebDriver driver) {
		List<String> listaErrorsInHtmlFormat = new ArrayList<>();
		
		//Buscamos el robots
		boolean robotNoindex = isPresentTagRobots();
		String operativaRobots = "";
		String currentURL = driver.getCurrentUrl();
		
		//El tag con name = 'robots' y content[contains(.,'noindex')] sólo aparece en el buscador de ítems y en ficha.faces, catalog.faces y iframe.faces
		if (currentURL.contains("ficha.faces")) {
			operativaRobots = "ficha.faces";
		}
		if (currentURL.contains("catalog.faces")) {
			operativaRobots = "catalog.faces";
		}
		if (currentURL.contains("iframe.faces")) {
			operativaRobots = "iframe.faces";
		}
//		if (currentURL.contains("sel=true") || currentURL.contains("m=color&c=")) 
//			operativaRobots = "filtro color/talla";
		
		if (currentURL.contains("/asc/") || currentURL.contains("/desc/")) {
			operativaRobots = "ordenación asc/desc";
		}
		if (currentURL.contains("/search") && currentURL.contains("?kw=")) {
			operativaRobots = "buscador";
		}
		if ("".compareTo(operativaRobots)!=0) {
			if (!robotNoindex) {
				listaErrorsInHtmlFormat.add("<br><b style=\"color:" + Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Estamos en <b>" + operativaRobots + "</b> pero no aparece el tag 'robots'</c>");
			}
		} else {
			if (robotNoindex && !currentURL.contains(".faces")) {
				listaErrorsInHtmlFormat .add(
					"<br><b style=\"color:" + Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\"> " + 
					"Está apareciendo el tag 'robots' pero no estamos en:<br>" +
					"1) el buscador de ítems o<br>" +
					//"2) el filtro de color/talla o<br>" +
					"3) la ordenación asc/desc o<br>" +
					"4) el buscador de ítems o<br>" +
					"5) En una petición .faces</c>"
				);
			}
		}
		
		//Si existe el tag canonical (apuntando a la propia página) no ha de exitir el tag robot/noindex
		if (isPresentTagCanonical()) {
			String urlTagCanonical = getURLTagCanonical();
			if (robotNoindex && urlTagCanonical.compareTo(driver.getCurrentUrl())!=0) {
				listaErrorsInHtmlFormat.add("<br><b style=\"color:" + Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Existe el tag robot/noindex junto el canonical apuntando a URL de otra página (" + urlTagCanonical + ")</c>");
			}
		}
		
		return listaErrorsInHtmlFormat;
	}
	
	private boolean isPresentTagCanonical() {
		return state(Present, XPATH_TAG_CANONICAL).check();
	}

	private boolean isPresentTagRobots() {
		return state(Present, XPATH_TAG_ROBOTS).check();
	}

	private WebElement getTagCanonincal() {
		return getElement(XPATH_TAG_CANONICAL);
	}
	
	private String getURLTagCanonical() {
		String urlTagCanonical = "";
		if (isPresentTagCanonical()) {
			urlTagCanonical = getTagCanonincal().getAttribute("href");
		}
		return urlTagCanonical;
	}
	
}
