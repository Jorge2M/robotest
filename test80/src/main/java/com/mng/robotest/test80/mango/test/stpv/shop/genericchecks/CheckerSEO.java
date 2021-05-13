package com.mng.robotest.test80.mango.test.stpv.shop.genericchecks;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.state;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class CheckerSEO implements Checker {

	public ChecksTM check(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		
		ResultadoErrores resValidac = validacionesGenericasSEO(driver);
		String descripValidac = "Se cumplen las validaciones genéricas de SEO";
		if (!resValidac.isOK()) {
			descripValidac += resValidac.getlistaLogError().toString();
		}
		validations.add(
			descripValidac,
			resValidac.isOK(), GenericCheck.SEO.getLevel(), true);
		
		return validations;
	}
	
    /**
     * Validaciones que determinan si una página generada en MANGO es o no correcta
     */
    private ResultadoErrores validacionesGenericasSEO(WebDriver driver) {

        ResultadoErrores resultado = new ResultadoErrores();
        resultado.setResultado(ResultadoErrores.Resultado.OK);
        ArrayList<String> listaProblemas = new ArrayList<>();        
        try {
            //Validaciones a nivel del "Title"
            listaProblemas.addAll(validaTagTitle(driver));
    
            //Validaciones a nivel del meta "Description"
            listaProblemas.addAll(validaMetaDescription(driver));
    
            //Validaciones a nivel del "Canonical"
            listaProblemas.addAll(validaCanonical(driver));

            //Validación a nivel del tag "Robots"
            listaProblemas.addAll(validacionRobots(driver));
        }
        catch (Exception e) {
            listaProblemas.add("<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Se ha producido una excepción (" + e.getMessage() + ") durante las validaciones</c>");
        }
        
        //Si se ha producido algún problema lo recogemos
        if (listaProblemas.size()>0) {
            listaProblemas.add("<br><br><c style=\"color:brown\"><b>SEO:</b> problemas en página con URL <a href=\"" + driver.getCurrentUrl() + "\">" + driver.getCurrentUrl() + "</a>:");
            resultado.setResultado(ResultadoErrores.Resultado.ERRORES);
            resultado.setListaLogError(listaProblemas);
        }

        return resultado;
    }
    
    /**
     * @return lista de errores en formato HTML referidos al tag "Title"
     */
    private ArrayList<String> validaTagTitle(WebDriver driver) {
        ArrayList<String> listaErrorsInHtmlFormat = new ArrayList<>();
        String title = driver.getTitle();
        if (title == null || "".compareTo(title) == 0) {
            listaErrorsInHtmlFormat.add("<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">No existe el title o es nulo</c>");
        }
        return listaErrorsInHtmlFormat;
    }
    
    /**
     * @return lista de errores en formato HTML referidos al meta "Description" 
     */
    private ArrayList<String> validaMetaDescription(WebDriver driver) {
        ArrayList<String> listaErrorsInHtmlFormat = new ArrayList<>();
        
        String xpath = "//meta[@name='description' and @content]";
        if (state(Present, By.xpath(xpath), driver).check() ||
            driver.findElement(By.xpath("//meta[@name='description' and @content]")).getAttribute("content").compareTo("") == 0) {
            listaErrorsInHtmlFormat.add("<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">No existe el meta 'description' o tiene el atributo 'content' a nulo</c>");
        }
        return listaErrorsInHtmlFormat;
    }
    
    /**
     * @return lista de errores en formato HTML referidos al "canonical"
     */
    private ArrayList<String> validaCanonical(WebDriver driver) throws Exception {
        ArrayList<String> listaErrorsInHtmlFormat = new ArrayList<>();
        if (!AllPages.isPresentTagCanonical(driver)) {
            //El canonical ha de aparecer como mínimo en las páginas de Portada, Catálogo y Ficha
        	PageFicha pageFicha = PageFicha.newInstanceFichaNew(Channel.desktop, AppEcom.shop, driver);
        	PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, AppEcom.shop, driver);
            if ((new PageLanding(driver)).isPage() || 
            	((PageGaleriaDesktop)pageGaleria).isPage() || 
            	pageFicha.isPageUntil(0)) {
                String currentURL = driver.getCurrentUrl(); 
                //Hemos de añadir un par de excepciones
                if (!currentURL.contains("catalogPc.faces?") && !currentURL.contains("search?") && !currentURL.contains("favorites.faces?")) {
                    listaErrorsInHtmlFormat.add("<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Estamos en Home (no favorites.faces?), Galeria (no catalogPc.faces? ni search?) o Ficha pero no existe el tag canonical</c>");
                }
            }
        }
        
        return listaErrorsInHtmlFormat;
    }
    
    /**
     * @return la lista de errores en formato HTML relacionados con el tag "Robots" 
     */
    private ArrayList<String> validacionRobots(WebDriver driver) {
        ArrayList<String> listaErrorsInHtmlFormat = new ArrayList<>();
        
        //Buscamos el robots
        boolean robotNoindex = AllPages.isPresentTagRobots(driver);
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
//        if (currentURL.contains("sel=true") || currentURL.contains("m=color&c=")) 
//            operativaRobots = "filtro color/talla";
        
        if (currentURL.contains("/asc/") || currentURL.contains("/desc/")) {
            operativaRobots = "ordenación asc/desc";
        }
        if (currentURL.contains("/search") && currentURL.contains("?kw=")) {
            operativaRobots = "buscador";
        }
        if ("".compareTo(operativaRobots)!=0) {
            if (!robotNoindex) {
                listaErrorsInHtmlFormat.add("<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Estamos en <b>" + operativaRobots + "</b> pero no aparece el tag 'robots'</c>");
            }
        } else {
            if (robotNoindex && !currentURL.contains(".faces")) {
                listaErrorsInHtmlFormat .add(
                    "<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\"> " + 
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
        if (AllPages.isPresentTagCanonical(driver)) {
            String urlTagCanonical = AllPages.getURLTagCanonical(driver);
            if (robotNoindex && urlTagCanonical.compareTo(driver.getCurrentUrl())!=0) {
                listaErrorsInHtmlFormat.add("<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b> <c style=\"color:brown\">Existe el tag robot/noindex junto el canonical apuntando a URL de otra página (" + urlTagCanonical + ")</c>");
            }
        }
        
        return listaErrorsInHtmlFormat;
    }
	
}