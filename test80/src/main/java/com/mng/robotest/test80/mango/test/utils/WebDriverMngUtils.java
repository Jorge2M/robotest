package com.mng.robotest.test80.mango.test.utils;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest.TypeEvidencia;
import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.generic.ResultadoErrores;
import com.mng.robotest.test80.mango.test.generic.stackTrace;

public class WebDriverMngUtils {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    /**
     * Se realiza una captura de ./errorPage.faces pues allí se pueden encontrar los datos de la instancia
     */
    public static void capturaErrorPage(DataFmwkTest dFTest, int stepNumber) throws Exception {
        if (dFTest.typeDriver!=TypeDriver.browserstack) {
            //Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella
            //BrowserStack parece que no soporta abrir ventanas aparte
            String windowHandle = loadErrorPage(dFTest.driver);
            try {
                String methodWithFactory = fmwkTest.getMethodWithFactory(dFTest.meth, dFTest.ctx);
                String nombreErrorFile = fmwkTest.getPathFileEvidenciaStep(dFTest.ctx, methodWithFactory, stepNumber, TypeEvidencia.errorpage);
                File errorImage = new File(nombreErrorFile);
                try (FileWriter fw = new FileWriter(errorImage)) {
                    fw.write(dFTest.driver.getPageSource());
                }
            } 
            catch (Exception e) {
                throw e;
            } 
            finally {
                // Cerramos la pestaña
                JavascriptExecutor js = (JavascriptExecutor) dFTest.driver;
                js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
    
                // Restauramos la pantalla a la que apunta webdriver
                dFTest.driver.switchTo().window(windowHandle);
            }
        }
    }
    
    /**
     * Cargamos el errorPage y de allí extraemos el nodo
     */
    public static String getNodeFromErrorPage(final WebDriver driver) throws Exception {

        //Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella 
        String windowHandle = loadErrorPage(driver);
        String nodo = "";
        
        try {
            nodo = driver.findElement(By.xpath("//h2[text()[contains(.,'IP :')]]/following::p")).getText();
        } catch (Exception e) {
            throw e;
        } finally {
            // Cerramos la pestaña
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.close('" + Thread.currentThread().getName() + "');");

            // Restauramos la pantalla a la que apunta webdriver
            driver.switchTo().window(windowHandle);
        }
        
        return nodo;
    }

    public static stackTrace stackTaceException(WebDriver driver, ITestContext context) throws Exception {
        stackTrace resExcep = new stackTrace();
        resExcep.setException("");
        resExcep.setExiste(false);
        resExcep.setRepetida(false);
        resExcep.setNumExcepciones(0);
        String stackTrace = getStackTraceFromErrorPage(driver);
        resExcep.setException(stackTrace);
        if (stackTrace.trim().compareTo("")!=0) {
            resExcep.setExiste(true);
            int numExcepciones = 0;
            if (context.getAttribute(stackTrace) != null) {
                numExcepciones = (Integer)context.getAttribute(stackTrace);
                resExcep.setRepetida(true);
            }
            numExcepciones+=1;
            context.setAttribute(stackTrace, Integer.valueOf(numExcepciones));
            resExcep.setNumExcepciones(numExcepciones);
        }
            
        return resExcep;
    }    
    
    /**
     * Cargamos el errorPage y de allí extraemos el Stack Trace
     */
    public static String getStackTraceFromErrorPage(final WebDriver driver) throws Exception {
        //Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella 
        String windowHandle = loadErrorPage(driver);
        String stackTrace = "";
        
        try {
            stackTrace = driver.findElement(By.xpath("//h2[text()[contains(.,'Stack Trace :')]]/following::p")).getText();
        } catch (Exception e) {
            throw e;
        } finally {
            // Cerramos la pestaña
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("window.close('" + Thread.currentThread().getName() + "');");

            // Restauramos la pantalla a la que apunta webdriver
            driver.switchTo().window(windowHandle);
        }
        
        return stackTrace;
    }
    
    /**
     * Carga la página errorPage.faces en una pestaña aparte y nos devuelve el windowHandle
     */
    public static String loadErrorPage(WebDriver driver) throws Exception {
        String currentURL = driver.getCurrentUrl();
        URI uri = new URI(currentURL);

        // Guardamos la referencia a la ventana padre
        String windowHandle = driver.getWindowHandle();

        // Calculamos un timestamp/nombre de página
        //java.util.Date date = new java.util.Date();
        //String timestampNombre = new Timestamp(date.getTime()).toString().trim().replace(":", "").replace(" ", "");

        // Abrimos una nueva pestaña en la que cargamos la página de errorPage (sólo con JS es compatible con todos los navegadores)
        String titlePant = Thread.currentThread().getName();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.open('" + uri.getScheme() + "://" + uri.getHost() + "/errorPage.faces" + "', '" + titlePant + "');");
        driver.switchTo().window(titlePant);
        
        //Esperamos hasta que la página esté disponible
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.className("stackTrace")));
        }
        catch (Exception e) {
            //
        }
        
        driver.getPageSource();
        return windowHandle;
    }
    
    /**
     * @param maxErrors máximo de errores a partir del cual ya no hemos de mostrar warning
     * @return recopilación de todos los problemas detectados
     */
    public static ResultadoErrores imagesBroken(WebDriver driver, Channel channel, int maxErrors) throws Exception {
        int maxImages = 5000;
        ITestContext ctx = TestCaseData.getdFTest().ctx;

        //En el caso de móvil sólo procesaremos 200 imágenes para paliar el caso en el que el script se ejecuta contra un dispositivo físico y el rendimiento es limitado
        if (channel==Channel.movil_web) {
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
    public static ResultadoErrores imagesBroken(WebDriver driver, int maxImages, int maxErrors, ITestContext ctx) throws Exception {
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
                    Thread.sleep(2000);
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
                            pLogger.warn(driver.getCurrentUrl() + descError);
                        }
                        else {
                            pLogger.warn("Imagen con SRC {} y status OK (2xx) pero con tamaño nulo en la página {} and id {}", currentImageUrl, i, imageId);
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
    
    private static String getImageSrc(WebElement image) {
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
    
    public static boolean verifyImgHttpActive(WebElement imgElement) {
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
            pLogger.warn("Problem verifying Image Active", e);
        }
        
        return imgActive;
    }
    
    /**
     * Decide si se ha de revisar o no que la imagen esté cortada (hay URLs que cargan un píxel transparente pero que son válidas)
     */
    private static boolean revisionBrokenHttp(final WebElement tagHttp) {
        boolean broken = true;
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
}
