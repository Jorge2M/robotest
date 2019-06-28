package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.NodoStatus;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper.UserMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
import com.mng.robotest.test80.mango.test.stpv.votf.PageLoginVOTFStpV;
import com.mng.robotest.test80.mango.test.stpv.votf.PageSelectIdiomaVOTFStpV;
import com.mng.robotest.test80.mango.test.stpv.votf.PageSelectLineaVOTFStpV;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

@SuppressWarnings({"static-access"})
public class AccesoStpV {

	final static String tagNombrePais = "@TagNombrePais";
	final static String tagLiteralIdioma = "@TagLiteralIdioma";
	final static String tagRegistro = "@TagRegistro";
	@Step (
		description="Acceder a Mango (" + tagNombrePais + "/" + tagLiteralIdioma + ")<br>" + tagRegistro, 
        expected="Se accede correctamente",
        saveNettraffic=SaveWhen.Always)
    public static void accesoAplicacionEnUnPaso(DataCtxShop dCtxSh, boolean clearArticulos, WebDriver driver) 
    throws Exception {
        String registro = "";
        if (dCtxSh.userRegistered && dCtxSh.appE!=AppEcom.votf) {
            registro = "Identificarse con el usuario <b>" + dCtxSh.userConnected + "</b><br>"; 
        }
       
        if (clearArticulos) {
            registro+= "Borrar la Bolsa<br>";        
        }
        
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();
        datosStep.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
        datosStep.replaceInDescription(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
        datosStep.replaceInDescription(tagRegistro, registro);
        
        AccesoNavigations.accesoHomeAppWeb(dCtxSh, driver);
        if (dCtxSh.userRegistered && dCtxSh.appE!=AppEcom.votf) {
            PageIdentificacion.iniciarSesion(dCtxSh, driver);
        }
        
        if (clearArticulos) {
            SecBolsa.clearArticulos(dCtxSh, driver);
        }
        
        if (dCtxSh.userRegistered && dCtxSh.appE!=AppEcom.votf) {
            validaIdentificacionEnShop(dCtxSh, driver);
        }
    }
    
    public static void validaIdentificacionEnShop(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		checkLinksAfterLogin(dCtxSh, driver);
		
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = false;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
        
        //Validaciones para Analytics (sólo para firefox y NetAnalysis)´
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(Constantes.AnalyticsVal.GoogleAnalytics,
                                                                  Constantes.AnalyticsVal.NetTraffic,
                                                                  Constantes.AnalyticsVal.DataLayer);
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, driver);
    }
	
	@Validation
	private static ChecksResult checkLinksAfterLogin(DataCtxShop dCtxSh, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
        MenusUserWrapper userMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, driver).getMenusUser();
    	validations.add(
    		"Aparece el link \"Mi cuenta\" (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		userMenus.isPresentMiCuentaUntil(maxSecondsWait), State.Defect);
		
		boolean isVisibleMenuFav = userMenus.isMenuInStateUntil(UserMenu.favoritos, StateElem.Present, 0);
		if (dCtxSh.appE==AppEcom.outlet) { 
	    	validations.add(
	    		"NO aparece el link \"Favoritos\"",
	    		!isVisibleMenuFav, State.Defect);
	    	validations.add(
	    		"Aparece el link \"Mis Pedidos\"",
	    		userMenus.isPresentPedidos(), State.Defect);
		} else {
	    	validations.add(
	    		"Aparece el link \"Favoritos\"",
	    		isVisibleMenuFav, State.Defect);
	    	
	    	boolean isPresentLinkMisCompras = userMenus.isPresentMisCompras();
	    	if (dCtxSh.pais.isMisCompras()) {
		    	validations.add(
		    		"Aparece el link \"Mis Compras\"",
		    		isPresentLinkMisCompras, State.Defect);
	    	} else {
		    	validations.add(
		    		"No aparece el link \"Mis Compras\"",
		    		!isPresentLinkMisCompras, State.Defect);
	    	}
		}
		
    	validations.add(
    		"Aparece el link \"Ayuda\"",
    		userMenus.isPresentAyuda(), State.Defect);
    	validations.add(
    		"Aparece el link \"Cerrar sesión\"",
    		userMenus.isPresentCerrarSesion(), State.Defect);
    	
        if (dCtxSh.channel==Channel.desktop) {
        	validations.add(
        		"Aparece una página con menús de MANGO",
        		SecMenusDesktop.secMenuSuperior.secLineas.isPresentLineasMenuWrapp(driver), State.Warn);
        }
        
        return validations;
	}

    /**
     * Accedemos a la aplicación (shop/outlet/votf)
     * Se ejecutan cada acción en un paso
     */
    public static void accesoAplicacionEnVariosPasos(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        if (dCtxSh.appE==AppEcom.votf && !dCtxSh.userRegistered) { //En VOTF no tiene sentido identificarte con las credenciales del cliente
            AccesoStpV.accesoVOTFtoHOME(dCtxSh, driver);                    
        } else {
            PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, false/*execValidacs*/, driver);
            if (dCtxSh.userRegistered) {
                identificacionEnMango(dCtxSh, driver);
                SecBolsaStpV.clear(dCtxSh, driver);
//                if (dCtxSh.appE==AppEcom.shop) {
//                    PageFavoritosStpV.clearAll(dCtxSh, driver);
//                }
            }
        }
    }    
    
    public static void identificacionEnMango(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	MenusUserWrapper userMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, driver).getMenusUser();
        if (!userMenus.isPresentCerrarSesion()) {
        	iniciarSesion(dCtxSh, driver);
        }
    }
    
    @Step (
    	description="Seleccionar \"Iniciar Sesión\" e identificarse con #{dCtxSh.getUserConnected()} / #{dCtxSh.getPasswordUser()}", 
        expected="La identificación es correcta",
        saveHtmlPage=SaveWhen.Always,
        saveNettraffic=SaveWhen.Always)
    private static void iniciarSesion(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        PageIdentificacion.iniciarSesion(dCtxSh, driver);
        validaIdentificacionEnShop(dCtxSh, driver);
    }
    
    //Acceso a VOTF (identificación + selección idioma + HOME she)
    public static void accesoVOTFtoHOME(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	ITestContext ctx = TestCaseData.getdFTest().ctx;
        String urlAcceso = (String)ctx.getAttribute("appPath");
        int numIdiomas = dCtxSh.pais.getListIdiomas().size();
        PageLoginVOTFStpV.goToAndLogin(urlAcceso, dCtxSh, driver);
        if (numIdiomas > 1) {
            PageSelectIdiomaVOTFStpV.selectIdiomaAndContinue(dCtxSh.idioma, driver);
        }
        
        PageSelectLineaVOTFStpV.validateIsPage(driver);
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = false;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
        PageSelectLineaVOTFStpV.selectMenuAndLogoMango(1, dCtxSh, driver);
    }

    
    final static String tagNombrePaisOrigen = "@TagPaisOrigen";
    final static String tagCodigoPaisOrigen = "@TagCodigoPaisOrigen";
    final static String tagNombreIdiomaOrigen = "@TagIdiomaOrigen";
    final static String tagNodoOrigen = "@TagNodoOrigen";
    final static String tagNodoDestino = "@TagNodoDestino";
    @Step (
    	description=
    		"Datos del cambio de país <br>" + 
    	    "<b>" + tagNombrePaisOrigen + "</b> (" + tagCodigoPaisOrigen + "), <b>idioma " + tagNombreIdiomaOrigen + "</b>. Nodo: <b style=\"color:blue\">" + tagNodoOrigen + "</b><br>" +  
    	    "<b>#{paisDestino.getNombre_pais()}</b> (#{paisDestino.getCodigo_pais()}), <b>idioma #{idiomaDestino.getLiteral()}</b>. Nodo: <b style=\"color:blue\">" + tagNodoDestino +  "</b>",
    	expected=
    		"Se accede a la shop de #{paisDestino.getNombre_pais()} en #{idiomaDestino.getLiteral()}")
    public static void accesoPRYCambioPais(DataCtxShop dCtxSh, Pais paisDestino, IdiomaPais idiomaDestino, WebDriver driver) 
    throws Exception {
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, driver);

        //TODO esto es temporal
        // Se realiza una captura de ./errorPage.faces pues allí se pueden encontrar los datos de la instancia
        String nodoOrigen = WebDriverMngUtils.getNodeFromErrorPage(driver);
        
        // STEPs REALIZAMOS EL PROCESO DE CAMBIO DE PAÍS
        Pais paisOriginal = dCtxSh.pais;
        IdiomaPais idiomaOriginal = dCtxSh.idioma;
        dCtxSh.pais = paisDestino;
        dCtxSh.idioma = idiomaDestino;
        SecFooterStpV.cambioPais(dCtxSh, driver);
        dCtxSh.pais = paisOriginal;
        dCtxSh.idioma = idiomaOriginal;
        
        //TODO esto es temporal
        String nodoDestino = WebDriverMngUtils.getNodeFromErrorPage(driver);
        
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();
        datosStep.replaceInDescription(tagNombrePaisOrigen, dCtxSh.pais.getNombre_pais());
        datosStep.replaceInDescription(tagCodigoPaisOrigen, dCtxSh.pais.getCodigo_pais());
        datosStep.replaceInDescription(tagNombreIdiomaOrigen, dCtxSh.idioma.getLiteral());
        datosStep.replaceInDescription(tagNodoOrigen, nodoOrigen);
        datosStep.replaceInDescription(tagNodoDestino, nodoDestino);        

        //No hacemos nada, simplemente es un paso informativo
    }
    
    /**
     * Acceso vía URL de un país (no asociado a la IP del usuario)
     * @param paisAccesoNoIP un país que no es el asociado a la IP del usuario
     * @param paisAccesoPrevio en caso de ser <> null indica el país por cuya URL se ha accedido previamente
     * @param paisPrevConf en caso de ser <> null indica el país que previamente se ha confirmado vía la opción de cambio existente en el modal
     * @param vecesPaisConfPrev indica las veces que "paisConfirmado" se ha confirmado previamente 
     * @param urlBaseTest URL base del test
     * @param listPaisAsocIP lista de posibles países asociados a la IP del usuario (actualmente España, Irlanda o USA)
     * @return el país asociado a la IP (al que te proponen cambiar en el modal)
     */
    final static String tagUrlAccesoPaisNoIp = "@TagUrlAccesoPaisNoIp";
    final static String tagLiteralIdiomaOrigen = "@TagLiteralIdiomaOrigen";
    @Step (
    	description="Acceder a la shop vía la URL <b>" + tagUrlAccesoPaisNoIp + "</b> (#{paisAccesoNoIP.getNombre_pais()} / " + tagLiteralIdiomaOrigen + ")", 
        expected="Aparece un modal solicitando confirmación del país")
    public static Pais accesoConURLPaisNoIP(String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisPrevConf, 
    										int vecesPaisConfPrev, List<Pais> listPaisAsocIP, WebDriver driver) throws Exception {
        Pais paisAsocIP = null;
        String urlAccesoPaisNoIp = paisAccesoNoIP.getUrlPaisEstandar(urlBaseTest);
        IdiomaPais idiomaOrigen = paisAccesoNoIP.getListIdiomas().get(0);
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagUrlAccesoPaisNoIp, urlAccesoPaisNoIp);
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagLiteralIdiomaOrigen, idiomaOrigen.getLiteral());
        
        driver.get(urlAccesoPaisNoIp);
        WebdrvWrapp.waitForPageLoaded(driver);
        if (vecesPaisConfPrev < 2) {
            //Si se ha confirmado el país < 2 veces debería aparecer el modal del cambio de país
        	ResultValWithPais resultVal = validacAccesoSiApareceModal(urlBaseTest, paisAccesoNoIP, paisAccesoPrevio, paisPrevConf, listPaisAsocIP, driver);
            paisAsocIP = resultVal.getPais();
        } else {
            //Si el país se ha confirmado > 1 vez no debería aparecer el modal de cambio de país
            validacAccesoNoApareceModal(urlBaseTest, paisPrevConf, driver);
        }
            
        return paisAsocIP;
    }
        
    /**
     * Valicaciones correspondientes al caso en que después del acceso SÍ aparece el modal de confirmacióin del país
     * @return el país asociado a la IP (al que te proponen cambiar en el modal)
     */
    @Validation 
    private static ResultValWithPais validacAccesoSiApareceModal(String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisConfirmado, 
    															 List<Pais> listPaisAsocIP, WebDriver driver) throws Exception {
    	ResultValWithPais validations = ResultValWithPais.getNew();
    	validations.add(
    		"Aparece un modal solicitando confirmación de país",
    		ModalCambioPais.isVisibleModalUntil(driver, 0), State.Defect);
    	
        if (paisAccesoPrevio==null) {
        	validations.add(
        		"En el modal <b>No</b> aparece un link con la opción de confirmar el país " + paisAccesoNoIP.getNombre_pais() + 
        		" (" + paisAccesoNoIP.getCodigo_pais() + ")",
        		!ModalCambioPais.isLinkToConfirmPais(driver, paisAccesoNoIP.getNombre_pais()), State.Defect);
        } else {
        	if (paisConfirmado==null) {
	        	validations.add(
	        		"En el modal <b>Sí</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
	        		paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
	        		ModalCambioPais.isLinkToConfirmPais(driver, paisAccesoPrevio.getUrlPaisEstandar(urlBaseTest)), State.Defect);
        	} else {
	        	validations.add(
	        		"En el modal <b>No</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
	        		paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
	        		!ModalCambioPais.isLinkToConfirmPais(driver, paisAccesoPrevio.getNombre_pais()), State.Defect);
        	}
        }
    	
        String paisesAsocIP = "";
        Iterator<Pais> it = listPaisAsocIP.iterator();
        while (it.hasNext()) {
            paisesAsocIP = paisesAsocIP + ", " + it.next().getNombre_pais();
        }
        Pais paisButtonAssociated = ModalCambioPais.getPaisOfButtonForChangePais(listPaisAsocIP, urlBaseTest, driver);
    	validations.add(
    		"En el modal aparece un botón con la opción de cambiar a uno de los posibles países asociados a la IP (" + paisesAsocIP + ")",
    		paisButtonAssociated!=null, State.Defect);
    	
    	validations.setPais(paisButtonAssociated);
    	return (validations);
    }
    
    /**
     * Valicaciones correspondientes al caso en que después del acceso NO aparece el modal de confirmacióin del país
     * @throws Exception
     */
    @Validation
    private static ChecksResult validacAccesoNoApareceModal(String urlBaseTest, Pais paisPrevConf, WebDriver driver) 
    throws Exception {
    	ResultValWithPais validations = ResultValWithPais.getNew();
    	validations.add(
    		"No aparece un modal solicitando confirmación de país",
    		!ModalCambioPais.isVisibleModalUntil(driver, 0), State.Defect);
    	
        String nombrePaisPrevConf = paisPrevConf.getNombre_pais();
        String hrefPaisPrevConf = paisPrevConf.getUrlPaisEstandar(urlBaseTest);
    	validations.add(
    		"Se ha redirigido a la URL del país confirmado previamente <b>" + nombrePaisPrevConf + "</b> (" + hrefPaisPrevConf + ")",
    		(driver.getCurrentUrl().toLowerCase().contains(hrefPaisPrevConf.toLowerCase())), State.Defect);
    	return validations;
    }
    
    final static String tagPaisBotonCambio = "@TagPaisBotonCambio";
    final static String tagHrefBotonCambio = "@TagHrefBotonCambio";
    @Step (
    	description="Confirmamos la propuesta de país del modal <b>" + tagPaisBotonCambio + "</b>", 
        expected="Se redirige a la URL " + tagHrefBotonCambio)
    public static void selectConfirmPaisModal(WebDriver driver) throws Exception {
        String paisBotonCambio = ModalCambioPais.getTextPaisButtonChagePais(driver);
        String hrefBotonCambioPais = ModalCambioPais.getHRefPaisButtonChagePais(driver);
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagPaisBotonCambio, paisBotonCambio);
        TestCaseData.getDatosCurrentStep().replaceInExpected(tagHrefBotonCambio, hrefBotonCambioPais);
        
        ModalCambioPais.clickButtonChangePais(driver);       
        checkIsDoneRedirectToCountry(paisBotonCambio, hrefBotonCambioPais, driver);
    }
    
    @Validation (
    	description="Se redirige a la URL del país #{paisBotonCambio} (#{hrefBotonCambioPais})",
    	level=State.Defect)
    private static boolean checkIsDoneRedirectToCountry(@SuppressWarnings("unused") String paisBotonCambio, 
    													String hrefBotonCambioPais, WebDriver driver) {
    	return (driver.getCurrentUrl().toLowerCase().contains(hrefBotonCambioPais.toLowerCase()));
    }
    
    /**
     * Accede y testea el estado de un nodo concreto de amazon (especificado mediante cookies de sesión). Almacena los datos obtenidos mediante llamada a la URL de status 
     * @param urlControl url para testear el estado del nodo
     */
    final static String tagPathStatus = "@TagPathStatus";
    @Step (
    	description="Invocamos a <b>" + tagPathStatus + "</b> para obtener los datos JSON", 
        expected="El nodo se encuentra en un estado correcto",
        saveHtmlPage=SaveWhen.Always)
    public static void testNodoState(NodoStatus nodo, WebDriver driver) throws Exception {
    	TestCaseData.getDatosCurrentStep().replaceInDescription(tagPathStatus, nodo.getStatusJSON().pathStatus);
        nodo.setDataStateNodeFromBrowser(driver);
        checkStatusUp(nodo);
    }
    
    @Validation (
    	description="El estatus es <b>UP</b>",
    	level=State.Defect)
    private static boolean checkStatusUp(NodoStatus nodo) {
    	return (nodo.getStatusJSON().isStatusOk());
    }

    @Validation
    public static ChecksResult validaCompareStatusNodos(NodoStatus nodoAct, NodoStatus nodoAnt) throws Exception {
       	ChecksResult validations = ChecksResult.getNew();
        JSONObject stockAct = nodoAct.getStatusJSON().getWarehouses();
        String vShopCAct = nodoAct.getStatusJSON().getVShopconfig();
        String vShopCAnt = nodoAnt.getStatusJSON().getVShopconfig();
        int sessionCAnt = nodoAnt.getStatusJSON().getSessionCount();
        int sessionCAct = nodoAct.getStatusJSON().getSessionCount();
        
    	validations.add(
    		"El stock de los almacenes (" + stockAct + ") coincide (+-10%) con el del nodo " + nodoAnt.getIp(),
    		nodoAct.comparaStocksWarehouses(nodoAnt, 0.10), State.Warn);
    	validations.add(
    		"La versión del shopconfig (" + vShopCAct + ") es igual que la del nodo " + nodoAnt.getIp(),
    		vShopCAct.compareTo(vShopCAnt)==0, State.Warn);
    	
        float divisor = Math.abs(sessionCAct - sessionCAnt);
        float dividendo = Math.max(sessionCAct, sessionCAnt);
    	validations.add(
    		"El contador de sesiones (" + sessionCAct + ") coincide (+-50%) con el del nodo " + nodoAnt.getIp() + " (" + sessionCAnt + ")",
    		(divisor / dividendo) <= 0.5, State.Warn);
    	
    	return validations;
    }
    
    public static class ResultValWithPais extends ChecksResult {
    	Pais pais;
    	private ResultValWithPais() {
    		super();
    	}
    	public static ResultValWithPais getNew() {
    		return (new ResultValWithPais());
    	}
    	
    	public Pais getPais() {
    		return this.pais;
    	}
    	
    	public void setPais(Pais pais) {
    		this.pais = pais;
    	}
    }
}