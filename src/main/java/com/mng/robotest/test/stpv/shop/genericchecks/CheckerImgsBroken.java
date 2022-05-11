package com.mng.robotest.test.stpv.shop.genericchecks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

import static com.github.jorge2m.testmaker.testreports.html.ResultadoErrores.Resultado.*;

public class CheckerImgsBroken implements Checker {

	private final static int MAX_ERRORES = 1;
	private final static List<String> WHITELIST = Arrays.asList(
			"https://st.mngbcn.com/images/imgWar/loadingGif/teen.gif");
	
	@Override
	public ChecksTM check(WebDriver driver) {
		ResultadoErrores resultadoImgs = imagesBroken(driver, Channel.desktop, MAX_ERRORES);
		String descripValidac = "No hay imágenes cortadas";
		if (resultadoImgs.getResultado()!=OK) {
			descripValidac+=resultadoImgs.getlistaLogError().toString();
		}
		
		boolean isCheckOk = 
				resultadoImgs.getResultado()==OK || 
				maxErroresReachedInAllImages(resultadoImgs) ||
				allImagesBrokenAreInWhitelist(resultadoImgs);
		
		ChecksTM checks = ChecksTM.getNew();
		checks.add(descripValidac, isCheckOk, GenericCheck.ImgsBroken.getLevel());
		return checks;
	}
	
	private boolean allImagesBrokenAreInWhitelist(ResultadoErrores resultadoImgs) {
		for (String img : resultadoImgs.getlistaLogError()) {
			if (!WHITELIST.contains(img)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean maxErroresReachedInAllImages(ResultadoErrores resultadoImgs) {
		return (resultadoImgs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES);
	}
	
	/**
	 * @param maxErrors máximo de errores a partir del cual ya no hemos de mostrar warning
	 * @return recopilación de todos los problemas detectados
	 */
	ResultadoErrores imagesBroken(WebDriver driver, Channel channel, int maxErrors) {
		int maxImages = 500;
		ITestContext ctx = TestMaker.getTestCase().get().getTestRunParent().getTestNgContext();
				
		//En el caso de móvil sólo procesaremos 200 imágenes para paliar el caso en el que el script se ejecuta contra un dispositivo físico y el rendimiento es limitado
		if (channel.isDevice()) {
			imagesBroken(driver,  200, maxErrors, ctx);
		}
		
		return (imagesBroken(driver, maxImages, maxErrors, ctx));
	}
	
	/**
	 * Función que nos dice si existen imágenes cortadas en la página actual
	 * @param maxImages máximo de imágenes que procesaremos
	 * @param maxErrors máximo de errores a partir del cual ya no hemos de mostrar warning
	 * @return recopilación de todos los problemas detectados
	 */
	private ResultadoErrores imagesBroken(WebDriver driver, int maxImages, int maxErrors, ITestContext ctx) {
		ResultadoErrores resultado = new ResultadoErrores();
		resultado.setResultado(ResultadoErrores.Resultado.OK);
		ArrayList<String> listaImgBroken = new ArrayList<>();
		EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);

		// Storing all the image elemt in the variable allImages
		List<WebElement> allImages = eventFiringWebDriver.findElements(By.xpath("//img[@src and not(@src=\"\")]"));
		
		//Si el tamaño de la lista de imágenes es superior al máximo obtenemos un subconjunto
		if (allImages.size() > maxImages) {
			allImages = allImages.subList(0, maxImages-1);
		}
		
		// Declaring a dynamic string of array which will store src of all the broken images
		List<String> BrokenimageUrl = new ArrayList<>();
		String script = "return (typeof arguments[0].naturalWidth!=\"undefined\" &&  arguments[0].naturalWidth>0)";
		int i = 0;
		for (WebElement image : allImages) { 
			
			// En ocasiones falla la ejecución del JavaScript porque la página no está preparada
			// así que reintentamos la petición 5 veces durante 10 segundos
			int intentos = 0;
			boolean excep_stale = false;
			Object imgStatus = new Object();
			do {
				try {
					intentos += 1;
					imgStatus = eventFiringWebDriver.executeScript(script, image); 
				} catch (StaleElementReferenceException e) {
					excep_stale = true;
					PageObjTM.waitMillis(2000);
				}
			} while (excep_stale && intentos < 5);

			if (imgStatus.equals(Boolean.valueOf(false))) {
				String imageSrc = getImageSrc(image);
				String imageId = image.getAttribute("id");
				if (imageSrc != null && imageSrc.trim().compareTo("") != 0 && !imageSrc.toLowerCase().contains(".svg")) {
					if (revisionBrokenHttp(image)) {
						String currentImageUrl = imageSrc;
						if (!verifyImgHttpActive(image)) {
							String imageUrl = currentImageUrl;
							BrokenimageUrl.add(imageUrl);
	
							// Buscamos el error en el contexto y obtenemos el número
							String descError = ". <br><b>Image Broken!</b> " + ",id:" + imageId + ",src: " + currentImageUrl + " ";
							int numErrors = 0;
							if (ctx.getAttribute(descError) != null) {
								numErrors = (Integer)ctx.getAttribute(descError);
							}
	
							// Actualizamos el número de errores en el contexto
							numErrors += 1;
							ctx.setAttribute(descError, Integer.valueOf(numErrors));
	
							// Sólo si hemos superado el máximo de errores (para todas las imágenes cortadas detectadas) mostraremos el warning en el caso de prueba
							if (numErrors > maxErrors) {
								if (!(resultado.getResultado() == ResultadoErrores.Resultado.ERRORES)) {
									resultado.setResultado(ResultadoErrores.Resultado.MAX_ERRORES);
								}
							} else {
								resultado.setResultado(ResultadoErrores.Resultado.ERRORES);
							}
	
							// Siempre pintaremos el mensaje de warning en el caso de prueba y en la consola
							listaImgBroken.add(driver.getCurrentUrl() + descError);
							Log4jTM.getLogger().warn(driver.getCurrentUrl() + descError);
						}
						else {
							Log4jTM.getLogger().warn("Imagen con SRC {} y status OK (2xx) pero con tamaño nulo en la página {} and id {}", currentImageUrl, i, imageId);
						}
					}
				} else {
					// No podemos considerar este caso como un error pues en MANGO hay imágenes 'válidas' sin SRC. P.e:
					// <img onclick="zoomOut()" />
					// <img id="zoomFicha_img" class="_mng_zoomImage" />
				}
			}
			
			i += 1;
		}

		resultado.setListaLogError(listaImgBroken);
		return resultado;
	}
	
	private String getImageSrc(WebElement image) {
		String src = "";
		String data_src = "";
		src = image.getAttribute("src");
		if (src==null || "".compareTo(src)==0) {
			data_src = image.getAttribute("data-src");
			if (data_src!=null && "".compareTo(data_src)!=0) {
				src = data_src;
			}
		}
		
		return src;
	}
	
	/**
	 * Decide si se ha de revisar o no que la imagen esté cortada (hay URLs que cargan un píxel transparente pero que son válidas)
	 */
	private boolean revisionBrokenHttp(final WebElement tagHttp) {
		boolean broken = true;
		try {
			String src = getImageSrc(tagHttp);
			String id = tagHttp.getAttribute("id");

			// Hay un caso concreto de imágenes que se utilizan en Reino Unido y Nederlands que implementan redirecciones pero que son válidas
			ArrayList<String> dominiosOK = new ArrayList<>();
			dominiosOK.add("ib.adnxs.com");
			dominiosOK.add("ad.yieldlab.net");
			dominiosOK.add("pixel.prfct.co");
			dominiosOK.add("doubleclick.net");
			dominiosOK.add("adnxs.com");
			dominiosOK.add("bat.r.msn.com");
			dominiosOK.add("bat.bing.com");
			dominiosOK.add("trc.taboola.com");
			dominiosOK.add("ads.admized.com");
			dominiosOK.add("sync.rhythmxchange.com");
			dominiosOK.add("pixel-geo.prfct.co");
			for (int i = 0; i < dominiosOK.size(); i++) {
				if (src.contains(dominiosOK.get(i))) {
					broken = false;
					break;
				}
			}
	
			if (id.contains("sonar-tracking")) {
				broken = false;
			}
			return broken;
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn(e);
			return false;
		}
	}
	
	private boolean verifyImgHttpActive(WebElement imgElement) {
		boolean imgActive = false;
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			HttpGet request = new HttpGet(getImageSrc(imgElement));
			try (CloseableHttpResponse response = client.execute(request)) {
				// verifying response code he HttpStatus should be 2xx if not, increment as invalid images count
				if (String.valueOf(response.getStatusLine().getStatusCode()).matches("[2]\\d\\d")) {
					imgActive = true;
				}
			}
		} catch (Exception e) {
			Log4jTM.getLogger().warn("Problem verifying Image Active", e);
		}
		
		return imgActive;
	}
}
