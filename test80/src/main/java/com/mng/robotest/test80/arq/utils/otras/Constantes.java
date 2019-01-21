package com.mng.robotest.test80.arq.utils.otras;
import java.util.regex.Pattern;

@SuppressWarnings("javadoc")
public class Constantes {

    public static final String XMLPaises = "/inicio_paises_v3.xml";
    
    public static final String directoryOutputTests = "output-library";
    public static final String nameReportHTMLTSuite = "ReportTSuite.html";
    
    public static final int CONST_HTML = 0;
	
    public static final int IDFRAME = 0;
    public static final int SRCFRAME = 1;
    public static final int RUTAFRAME = 2;
	
    public static final String pass_standard = "sirjorge74";
    public static final String mail_standard = "listablanca003@mango.com";
	
    public static final String CSS = "CSS";
    public static final String IMAGE = "IMAGE";
    public static final String JS = "JS";
    public static final String FRAME = "FRAME";
    public static final String IMAGE_CSS = "IMAGE_CSS";
    public static final String UNDEFINED = "UNDEFINED";
	
    public static final int BOTON_INICIAR_SESION = 0;
    public static final int BOTON_REGISTRATE = 1;
    public static final int BOTON_CERRAR = 2;
    
    public static final String userManto = "jorge.munoz";
    public static final String passwordManto = "2010martina";
    
	
    public static final String CODIGO_ESPAÑA = "001";
	
    public static final int PORC_PANORAMICAS = 5;
	
    //dentifica el máximo número de tests en paralelo que nos permite la licencia de BrowserStack
    public static final int BSTACK_PARALLEL = 2;
	
    public static final String LISTA_PEDIDOS = "listaPaisPedidos";
	
    //Literal que definimos en el caso en que en el detalle del producto no figure el color (p.e. en el caso de móvil-web) 
    public static final String colorDesconocido = "desconocido";
	
    public static final String DIRECTORIO_FRAMES = "frames";
	
    public static Pattern BUSY_EXCEPTION_PATTERN = Pattern.compile("^\\[SQLITE_BUSY\\]");

    //Atributo almacenado en la sesión correspondiente a la página que aparece posteriormente al acceso del usuario (después de la selección de país/idioma)
    public static String attrUrlPagPostAcceso = "paginaPostAcceso";
	
    //Posibles webdrivers
    public enum TypeDriver { firefox, firefoxhless, chrome, chromehless, explorer, appium, browserstack, phantomjs, htmlunit, safari }	
	
    public static String BROWSERSTACK = "browserstack";
	
    public enum ThreeState {TRUE, FALSE, UNKNOWN}
	
    //Conjunto de las validaciones disponibles a nivel de Analítica
    public enum AnalyticsVal {GoogleAnalytics, Criteo, Bing, Polyvore, NetTraffic, DataLayer} 
	
    //Máximo de banners a probar según el tipo de país
    public static final int MAX_BAN_PAIS_TOP_SHOP = 8;
    public static final int MAX_BAN_PAIS_SICOMPRA_SHOP = 3;
    public static final int MAX_BAN_PAIS_NOCOMPRA_SHOP = 1;
    public static final int MAX_BAN_PAIS_TOP_OUTLET = 4;
    public static final int MAX_BAN_PAIS_SICOMPRA_OUTLET = 2;
    public static final int MAX_BAN_PAIS_NOCOMPRA_OUTLET = 1;
    
    //ID parámetros contexto relacionados con BrowserStack
    public static final String UserBStack = "UserBStack";
    public static final String PassBStack = "PassBStack";
	
    public static final String paramSuiteExecInCtx = "idSuiteExecution";
    public static final String paramVersionSuite = "Version";
    public static final String paramApplicationDNS = "applicationDNS";
    public static final String paramTypeAccessFmwk = "typeAccessFmwk";
    public static final String paramChannelSuite = "Channel";
    public static final String paramAppEcomSuite = "AppEcom";
    public static final String paramBrowser = "Browser";
    public static final String paramBrwsPath = "brwsr-path";
    public static final String paramRecycleWD = "recycleWD";
    public static final String paramNetAnalysis = "netAnalysis";

    public static final String paramUrlmanto = "urlmanto";
    public static final String paramUsrmanto = "usrmanto";
    public static final String paramPasmanto = "pasmanto";

    
    public static final String paramOutputDirectorySuite = "outputDirectorySuite";
    
    public static final String paramEnvioCorreo = "envioCorreo";
    public static final String paramSiempreMail = "siempreMail";
    public static final String paramToListMail = "toListMail";
    public static final String paramCcListMail = "CcListMail";
    public static final String paramAsuntoMail = "asuntoMail";
    
    public static final String paramCallBackMethod = "CallBackMethod";
    public static final String paramCallBackResource = "CallBackResource";
    public static final String paramCallBackSchema = "CallBackSchema";
    public static final String paramCallBackParams = "CallBackParams";
    public static final String paramCallBackUser = "CallBackUser";
    public static final String paramCallBackPassword = "CallBackPassword";
    
    public static final String paramCountrys = "countrys";
    public static final String paramLineas = "lineas";
    public static final String paramPayments = "payments";
    public static final String paramUrlBase = "urlBase";
	
    public static final String URL_SOFTWAREISHARD = "http://www.softwareishard.com/har/viewer/?inputUrl=";
}