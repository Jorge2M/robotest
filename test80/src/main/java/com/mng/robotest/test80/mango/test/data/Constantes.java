package com.mng.robotest.test80.mango.test.data;

import java.util.regex.Pattern;

public class Constantes {

    public static final int IDFRAME = 0;
    public static final int SRCFRAME = 1;
    public static final int RUTAFRAME = 2;
	
    public static final String pass_standard = "sirjorge74";
    public static final String mail_standard = "jorge.munoz@mango.com";
	
    public static final String CSS = "CSS";
    public static final String IMAGE = "IMAGE";
    public static final String JS = "JS";
    public static final String FRAME = "FRAME";
    public static final String IMAGE_CSS = "IMAGE_CSS";
    public static final String UNDEFINED = "UNDEFINED";
	
    public static final int BOTON_INICIAR_SESION = 0;
    public static final int BOTON_REGISTRATE = 1;
    public static final int BOTON_CERRAR = 2;
    
//    public static final String userManto = "00556106";
//    public static final String passwordManto = "Enero2020";
	public static final String userManto = "testjam01";
	public static final String passwordManto = "k48CJuyd";
    
	
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
	
    public static String BROWSERSTACK = "browserstack";
	
    public enum ThreeState {TRUE, FALSE, UNKNOWN}
	
    //Conjunto de las validaciones disponibles a nivel de Analítica
    public enum AnalyticsVal {GoogleAnalytics, Criteo, Bing, Polyvore, NetTraffic, DataLayer} 

    //ID parámetros contexto relacionados con BrowserStack
    public static final String UserBrowserStack = "equipoqa1";
    public static final String PassBrowserStack = "qp3dr5VJbFMAxPsT4k1b";

    public static final String paramUrlmanto = "urlmanto";
    public static final String paramUsrmanto = "usrmanto";
    public static final String paramPasmanto = "pasmanto";
    


    public static final String paramCountrys = "countrys";
    public static final String paramLineas = "lineas";
    public static final String paramPayments = "payments";

    
	public final static String PrefixRebajas = "<b style=\"color:blue\">Rebajas</b></br>";
}