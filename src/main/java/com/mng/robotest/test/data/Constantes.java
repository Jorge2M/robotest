package com.mng.robotest.test.data;

import java.util.regex.Pattern;

public class Constantes {

	public static final int IDFRAME = 0;
	public static final int SRCFRAME = 1;
	public static final int RUTAFRAME = 2;
	
	public static final String CSS = "CSS";
	public static final String IMAGE = "IMAGE";
	public static final String JS = "JS";
	public static final String FRAME = "FRAME";
	public static final String IMAGE_CSS = "IMAGE_CSS";
	public static final String UNDEFINED = "UNDEFINED";
	
	public static final int BOTON_INICIAR_SESION = 0;
	public static final int BOTON_REGISTRATE = 1;
	public static final int BOTON_CERRAR = 2;
   
	public static final String CODIGO_ESPANA = "001";
	
	public static final int PORC_PANORAMICAS = 5;
	
	public static final String MAIL_PERSONAL = "test.performace02@mango.com";
	
	//dentifica el máximo número de tests en paralelo que nos permite la licencia de BrowserStack
	public static final int BSTACK_PARALLEL = 2;
	
	public static final String LISTA_PEDIDOS = "listaPaisPedidos";
	
	//Literal que definimos en el caso en que en el detalle del producto no figure el color (p.e. en el caso de móvil-web) 
	public static final String COLOR_DESCONOCIDO = "desconocido";
	public static final String DIRECTORIO_FRAMES = "frames";
	
	public static final Pattern BUSY_EXCEPTION_PATTERN = Pattern.compile("^\\[SQLITE_BUSY\\]");

	//Atributo almacenado en la sesión correspondiente a la página que aparece posteriormente al acceso del usuario (después de la selección de país/idioma)
	public static final String ATTR_URL_PAG_POST_ACCESO = "paginaPostAcceso";
	
	public static final String BROWSERSTACK = "browserstack";
	
	public enum ThreeState {TRUE, FALSE, UNKNOWN}

	public static final String PARAM_URL_MANTO = "urlmanto";
	public static final String PARAM_USR_MANTO = "usrmanto";
	public static final String PARAM_PAS_MANTO = "pasmanto";

	public static final String PARAM_COUNTRYS = "countrys";
	public static final String PARAM_LINEAS = "lineas";
	public static final String PARAM_PAYMENTS = "payments";
	public static final String PARAM_CATALOGS = "catalogs";

	public static final String PREFIX_REBAJAS = "<b style=\"color:blue\">Rebajas</b></br>";
}