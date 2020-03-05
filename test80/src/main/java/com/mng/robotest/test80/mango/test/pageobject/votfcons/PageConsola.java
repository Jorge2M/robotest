package com.mng.robotest.test80.mango.test.pageobject.votfcons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageConsola extends WebdrvWrapp {

    /**
     * Mensaje que aparece en el caso de una consulta de tipos de envío OK 
     */
    public final static String msgConsTiposEnvioOK = "Servicios - Envio a tienda y a domicilio";
    
    /**
     * @return el xpath correspondiente al desplegable (select) del entorno (aparado "Test servicios VOTF")
     */
    private static final String XPathSelectTestServ = "//div[@class[contains(.,'serviciosVOTF')]]//select[@name='url']";
    
    /**
     * @return el xpath correspondiente al desplegable (select) del entorno (aparado "Cónsola comandos VOTF")
     */
    private static final String XPathSelectConsolaCom = "//div[@class[contains(.,'consolaVOTF')]]//select[@name='urlConsola']";
    
    /**
     * @param textOption especifica el texto que permite identificar el option concreto ("Local", "Test", "Preproducción" o "Producción")
     * @return el xpath correspondiente a una opción concreta del desplegable de entornos (aparado "Test servicios VOTF")
     */
    private static String getXPath_optionSelectTestServ(String textOption) {
        return (XPathSelectTestServ + "/option[text()[contains(.,'" + textOption + "')]]");
    }
    
    /**
     * @param textOption especifica el texto que permite identificar el option concreto ("Local", "Test", "Preproducción" o "Producción")
     * @return el xpath correspondiente a una opción concreta del desplegable de entornos (aparado "Cónsola comandos VOTF")
     */
    private static String getXPath_optionSelectConsolaCom(String textOption) {
        return (XPathSelectTestServ + "/option[text()[contains(.,'" + textOption + "')]]");
    }
    
    /**
     * @return el xpath correspondiente al input donde se introduce la lista de códigos artículo a nivel de disponibilidad
     */
    private static final String XPathInputArticDisponib = "//input[@name='codigoArticulo']";
    
    /**
     * @return el xpath correspondiente al input donde se introduce la lista de códigos artículo a nivel de compra
     */
    private static final String XPathInputArticCompra = "//input[@name='codigoArticuloCompra']";
    private static final String XPathInputTienda = "//input[@name='codigoTienda']";
    private static final String XPathInputTiendaEnvio = "//input[@id='codigoTiendaEnvio']";
    private static final String XPathInputTiendaConsola = "//input[@name='codigoTiendaConsola']";
    
    /**
     * @return el xpath correspondiente al botón "Consultar tipos de Envío" (del apartado "Test servicios VOTF")
     */
    static String XPathButtonConsTiposEnvios = "//a[@id='envioTipos']";
    
    /**
     * @return el xpath correspondiente al botón "Consultar disponibilidad Envío Domicilio" (del apartado "Test servicios VOTF")
     */
    static String XPathButtonDispEnvioDomic = "//a[@id='envioDom']";
   
    /**
     * @return el xpath correspondiente al botón "Consultar disponibilidad Envío Tienda" (del apartado "Test servicios VOTF")
     */
    static String XPathButtonDispEnvioTienda = "//a[@id='envioTienda']";
    
    /**
     * @return el xpath correspondiente al desplegable "Código de transporte"
     */
    static String XPathSelectCodTransporte = "//select[@id='idTransporte']";
   
    /**
     * @return el xpath correspondiente al iframe correspondiente al bloque de resultado
     */
    static String XPathIframeResult = "//iframe[@id='resultFrame']";
    
    static String XPathButtonSolATienda = "//a[@id='envioTiendaBolsa']";
    static String XPathButtonSolADomicilio = "//a[@id='envioDomBolsa']";
    
    /**
     * @return el xpath correspondiente al botón "Obtención de Pedidos"
     */
    static String XPathButtonObtencionPedidos = "//a[@id='obtencionPedidos']";
    
    /**
     * @return el xpath correspondiente al botón "Seleccionar pedido"
     */
    static String XPathButtonSelectPedido = "//a[@id='seleccionPedidos']";
    
    /**
     * @return el xpath correspondiente al botón "Preconfirmar Pedido"
     */
    static String XPathButtonPreconfPedido = "//a[@id='preconfirmarPedidos']";
    
    /**
     * @return el xpath correspondiente al botón "Confirmar Pedido"
     */
    static String XPathButtonConfPedido = "//a[@id='confirmarPedidos']";
    
    /**
     * Nos posicionamos en el iframe del resultado de la operación
     */
    public static void switchToResultIFrame(WebDriver driver) {
        driver.switchTo().frame(driver.findElement(By.xpath(XPathIframeResult)));
    }
    
    /**
     * @return si existe el apartado de "Test servicios VOTF"
     */
    public static boolean existTestServVOTF(WebDriver driver) {
        return (isElementPresent(driver, By.xpath("//span[text()[contains(.,'Test servicios VOTF')]]")));
    }
    
    /**
     * @return si existe el apartado de "Consola comandos VOTF"
     */
    public static boolean existConsolaComVOTF(WebDriver driver) {
        return (isElementPresent(driver, By.xpath("//span[text()[contains(.,'Consola comandos VOTF')]]")));
    }
    
    /**
     * @param textOption especifica el texto que permite identificar el option concreto ("Local", "Test", "Preproducción" o "Producción")
     * @return el texto correspondiente a una opción concreta del desplegable de entornos (aparado "Test servicios VOTF")
     */
    public static String getText_optionSelectTestServ(WebDriver driver, String textOption) {
        String xpathOption = getXPath_optionSelectTestServ(textOption);
        return (driver.findElement(By.xpath(xpathOption)).getText());
    }
    
    /**
     * @param textOption especifica el texto que permite identificar el option concreto ("Local", "Test", "Preproducción" o "Producción")
     * @return el texto correspondiente a una opción concreta del desplegable de entornos (aparado "Cónsola comandos VOTF")
     */
    public static String getText_optionSelectConsolaCom(WebDriver driver, String textOption) {
        String xpathOption = getXPath_optionSelectConsolaCom(textOption);
        return (driver.findElement(By.xpath(xpathOption)).getText());
    }    
    
    /**
     * Selecciona un determinado entorno del desplegable "Entorno" del apartado "Test servicios VOTF" 
     * @param driver
     * @param entorno
     */
    public static void selectEntornoTestServ(WebDriver driver, String entorno) {
        //Obtenemos el texto asociado a la opción del desplegable
        String optionTestPRE = getText_optionSelectTestServ(driver, entorno);
        
        //Seleccionamos dicha opción
        new Select(driver.findElement(By.xpath(XPathSelectTestServ))).selectByVisibleText(optionTestPRE);
    }
    
    /**
     * Selecciona un determinado entorno del desplegable "Entorno" del apartado "Cónsola comandos VOTF" 
     */
    public static void selectEntornoConsolaCom(WebDriver driver, String entorno) {
        //Obtenemos el texto asociado a la opción del desplegable
        String optionTestPRE = getText_optionSelectConsolaCom(driver, entorno);
        
        //Seleccionamos dicha opción
        new Select(driver.findElement(By.xpath(XPathSelectConsolaCom))).selectByVisibleText(optionTestPRE);
    }    
    
    /**
     * Establece un determinado artículo en el campo de input correspondiente a la lista de "Código artículo disponibilidad" 
     */
    public static void inputArticDisponib(WebDriver driver, String articulo) {
        
        //Buscamos el value que actualmente está introducido en el input
        String valueInput = driver.findElement(By.xpath(XPathInputArticDisponib)).getAttribute("value");
        
        //Si no coincide con el parámetro 'articulo' lo establecemos
        if (valueInput.compareTo(articulo)!=0) {
            driver.findElement(By.xpath(XPathInputArticDisponib)).clear();
            driver.findElement(By.xpath(XPathInputArticDisponib)).sendKeys(articulo);        
        }
    }
    
    /**
     * Establece un determinado artículo en el campo de input correspondiente a la lista de "Código artículo compra" 
     */
    public static void inputArticCompra(WebDriver driver, String articulo) {
        //Buscamos el value que actualmente está introducido en el input
        String valueInput = driver.findElement(By.xpath(XPathInputArticCompra)).getAttribute("value");
        
        //Si no coincide con el parámetro 'articulo' lo establecemos
        if (valueInput.compareTo(articulo)!=0) {
            driver.findElement(By.xpath(XPathInputArticCompra)).clear();
            driver.findElement(By.xpath(XPathInputArticCompra)).sendKeys(articulo);        
        }
    }    
    
    /**
     * Si no lo está ya, introduce un determinado código de artículo en los inputs de "Código artículo disponibilidad" y "Código artículo compra"
     */
    public static void inputArticDispYCompra(WebDriver driver, String articulo) {
        //Introducimos el código de artículo en el input correspondiente a la lista de códigos de artículo (a nivel de disponibilidad)
        inputArticDisponib(driver, articulo);
        
        //Introducimos el código de artículo en el input correspondiente a la lista de códigos de artículo (a nivel de compra)
        inputArticCompra(driver, articulo);
    }
    
    public static void inputTiendas(String tienda, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputTienda)).clear();
        driver.findElement(By.xpath(XPathInputTienda)).sendKeys(tienda);
        driver.findElement(By.xpath(XPathInputTiendaEnvio)).clear();
        driver.findElement(By.xpath(XPathInputTiendaEnvio)).sendKeys(tienda);
        driver.findElement(By.xpath(XPathInputTiendaConsola)).clear();
        driver.findElement(By.xpath(XPathInputTiendaConsola)).sendKeys(tienda);        
    }
    
    /**
     * Selección del botón "Consultar Tipos Envío"
     */
    public static void clickButtonConsTiposEnvios(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonConsTiposEnvios));
    }
    
    /**
     * Consulta disponibilidad envío a domicilio (mediante selección del botón correspondient) y devuelve los datos de transporte obtenidos
     * @return valores del desplegable "Código de transporte" obtenido
     */
    public static void clickButtonConsultarDispEnvioDomicilio(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonDispEnvioDomic));
    }
    
    /**
     * Esperamos hasta que existen valores (options) en el desplegable (select) de "Código transporte" (tardan un poco en cargarse)
     */
    public static boolean isDataSelectCodigoTransporte(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathSelectCodTransporte + "/option"), maxSecondsToWait));
    }
    
        
    public static String getCodigoTransporte(WebDriver driver) { 
        return driver.findElement(By.xpath(XPathSelectCodTransporte)).getText();
    }
    
    /**
     * Seleccionamos el botón "Consultar Disponibilidad Envío Tienda" (y esperamos hasta que se carga la página)
     */
    public static void consDispEnvioTienda(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonDispEnvioTienda));
    }
    
    public static void clickButtonSolATienda(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSolATienda));
    }
    
    public static void clickButtonSolADomicilio(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSolADomicilio));
    }
    
    /**
     * Selección del botón "Obtención del pedido" y wait hasta carga de la página
     */
    public static void clickButtonObtenerPedidos(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonObtencionPedidos));
    }
    
    /**
     * Selección del botón "Seleccionar pedido" del apartado "Consola comandos VOTF"
     */
    public static void clickButtonSelectPedido(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSelectPedido));
    }
    
    /**
     * Seleccionamos el botón "Preconfirmar Pedido" del apartado "Consola comandos VOTF"
     */
    public static void clickButtonPreconfPedido(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonPreconfPedido));
    }
    
    /**
     * Seleccionamos el botón "Confirmar Pedido" del apartado "Consola comandos VOTF"
     */    
    public static void clickButtonConfPedido(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonConfPedido));
    }
    
    /**
     * Selecciona un pedido del desplegable 'Pedido' del apartado 'Consola comandos VOTF'
     * @param codigoPedidoFull código de pedido en formato largo (+2 dígitos por la derecha)
     */
    public static void selectPedido(WebDriver driver, String codigoPedidoFull) {
        new Select(driver.findElement(By.id("pedidoConsola"))).selectByVisibleText(codigoPedidoFull);
    }
}