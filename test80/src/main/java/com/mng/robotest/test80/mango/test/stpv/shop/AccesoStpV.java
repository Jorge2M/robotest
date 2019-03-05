package com.mng.robotest.test80.mango.test.stpv.shop;

import static org.testng.Assert.assertTrue;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.NodoStatus;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusUserWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
import com.mng.robotest.test80.mango.test.stpv.shop.favoritos.PageFavoritosStpV;
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
            registro = "Identificarse con el usuario " + dCtxSh.userConnected + "<br>"; 
        }
       
        if (clearArticulos) {
            registro+= "Borrar la Bolsa/Favoritos<br>";        
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
            if (dCtxSh.appE==AppEcom.shop) {
                PageFavoritos.clearAllArticulos(dCtxSh.channel, dCtxSh.appE, driver);
            }
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
	private static ListResultValidation checkLinksAfterLogin(DataCtxShop dCtxSh, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 5;
    	validations.add(
    		"Aparece el link \"Mi cuenta\" (lo esperamos hasta " + maxSecondsWait + " segundos)<br>",
    		SecMenusWrap.secMenusUser.isPresentMiCuentaUntil(dCtxSh.channel, maxSecondsWait, driver), State.Defect);
		
		boolean isVisibleLinkFavoritos = SecMenusWrap.secMenusUser.isPresentFavoritos(dCtxSh.channel, driver);
		if (dCtxSh.appE==AppEcom.outlet) { 
	    	validations.add(
	    		"NO aparece el link \"Favoritos\"<br>",
	    		!isVisibleLinkFavoritos, State.Defect);
	    	validations.add(
	    		"Aparece el link \"Mis Pedidos\"<br>",
	    		SecMenusWrap.secMenusUser.isPresentPedidos(dCtxSh.channel, driver), State.Defect);
		}
		else {
	    	validations.add(
	    		"Aparece el link \"Favoritos\"<br>",
	    		isVisibleLinkFavoritos, State.Defect);
	    	
	    	boolean isPresentLinkMisCompras = SecMenusWrap.secMenusUser.isPresentMisCompras(dCtxSh.channel, driver);
	    	if (dCtxSh.pais.isMisCompras()) {
		    	validations.add(
		    		"Aparece el link \"Mis Compras\"<br>",
		    		isPresentLinkMisCompras, State.Defect);
	    	}
	    	else {
		    	validations.add(
		    		"No aparece el link \"Mis Compras\"<br>",
		    		!isPresentLinkMisCompras, State.Defect);
	    	}
		}
		
    	validations.add(
    		"Aparece el link \"Ayuda\"<br>",
    		SecMenusWrap.secMenusUser.isPresentAyuda(dCtxSh.channel, driver), State.Defect);
    	validations.add(
    		"Aparece el link \"Cerrar sesión\"<br>",
    		SecMenusWrap.secMenusUser.isPresentCerrarSesion(dCtxSh.channel, driver), State.Defect);
    	
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
        }
        else {
            PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, false/*execValidacs*/, driver);
            if (dCtxSh.userRegistered) {
                identificacionEnMango(dCtxSh, driver);
                SecBolsaStpV.clear(dCtxSh, driver);
                if (dCtxSh.appE==AppEcom.shop) {
                    PageFavoritosStpV.clearAll(dCtxSh, driver);
                }
            }
        }
    }    
    
    public static void identificacionEnMango(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        if (!SecMenusUserWrap.isPresentCerrarSesion(dCtxSh.channel, driver)) {
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
        PageSelectLineaVOTFStpV.selectMenuAndLogoMango(1/*numMenu*/, dCtxSh.pais, driver);
    }
    
    //Accedemos a un país/idioma desde la prehome y realizamos un cambio a otro país/idioma
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
        
        // STEP (TEMPORAL) INFORMAR DE LOS PAÍSES / NODOS
        DatosStep datosStep = new DatosStep(
            "Datos del cambio de país <br>" + 
            "<b>" + dCtxSh.pais.getNombre_pais() + "</b> (" + dCtxSh.pais.getCodigo_pais() + "), <b>idioma " + dCtxSh.idioma.getCodigo().getLiteral() + "</b>. Nodo: <b style=\"color:blue\">" + nodoOrigen + "</b><br>" +  
            "<b>" + paisDestino.getNombre_pais() + "</b> (" + paisDestino.getCodigo_pais() + "), <b>idioma " + idiomaDestino.getCodigo().getLiteral() + "</b>. Nodo: <b style=\"color:blue\">" + nodoDestino +  "</b>",
            "Se accede a la shop de " + paisDestino.getNombre_pais() + " en " + idiomaDestino.getCodigo().getLiteral());
        try {
            //No hacemos nada, simplemente es un paso informativo
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep);}
        
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
    public static Pais accesoConURLPaisNoIP(String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisPrevConf, int vecesPaisConfPrev, List<Pais> listPaisAsocIP, DataFmwkTest dFTest) 
    throws Exception {
        Pais paisAsocIP = null;
        
        //Obtenemos la URL de acceso asociada al país (la asociada al 1er idioma del país)
        String urlAccesoPaisNoIp = paisAccesoNoIP.getUrlPaisEstandar(urlBaseTest);
        IdiomaPais idiomaOrigen = paisAccesoNoIP.getListIdiomas().get(0);
        
        //Step. Accede a la shop vía la URL de un país <> al asociado a la IP
        DatosStep datosStep = new DatosStep(
            "Acceder a la shop vía la URL <b>" + urlAccesoPaisNoIp + "</b> (" + paisAccesoNoIP.getNombre_pais() + " / " + idiomaOrigen.getCodigo().getLiteral() + ")", 
            "Aparece un modal solicitando confirmación del país");
        try {
            // Introducir la URL asociada al país/idioma
            dFTest.driver.get(urlAccesoPaisNoIp);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep);}

        if (vecesPaisConfPrev < 2)
            //Si se ha confirmado el país < 2 veces debería aparecer el modal del cambio de país
            paisAsocIP = validacAccesoSiApareceModal(datosStep, urlBaseTest, paisAccesoNoIP, paisAccesoPrevio, paisPrevConf, listPaisAsocIP, dFTest);
        else
            //Si el país se ha confirmado > 1 vez no debería aparecer el modal de cambio de país
            validacAccesoNoApareceModal(datosStep, urlBaseTest, paisPrevConf, dFTest);
            
        return paisAsocIP;
    }
        
    /**
     * Valicaciones correspondientes al caso en que después del acceso SÍ aparece el modal de confirmacióin del país
     * @return el país asociado a la IP (al que te proponen cambiar en el modal)
     */
    private static Pais validacAccesoSiApareceModal(DatosStep datosStep, String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisConfirmado, List<Pais> listPaisAsocIP, DataFmwkTest dFTest) 
    throws Exception {
        Pais paisAsocIP = null;
        
        //Obtenemos el String con la lista de posibles países asociados a la IP del usuario
        String paisesAsocIP = "";
        Iterator<Pais> it = listPaisAsocIP.iterator();
        while (it.hasNext())
            paisesAsocIP = paisesAsocIP + ", " + it.next().getNombre_pais();
        
        //La 2a validación será diferente según si previamente se ha intentado acceder por la URL de otro país o no
        String validacion2 = "";
        if (paisAccesoPrevio==null) {
            validacion2 = 
            "2) En el modal NO aparece un link con la opción de confirmar el país " + paisAccesoNoIP.getNombre_pais() + " (" + paisAccesoNoIP.getCodigo_pais() + ")<br>";
        }
        else {
            //La 2a validación será diferente según si previamente se ha confirmado el cambio de acceso por un país o no
            String sino = "";
            if (paisConfirmado==null)
                sino = "SÍ";
            else
                sino = "NO";
            
            validacion2 = 
            "2) En el modal " + sino + " aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")<br>";
        }
        
        String descripValidac = 
            "1) Aparece un modal solicitando confirmación de país<br>" +
            validacion2 +
            "3) En el modal aparece un botón con la opción de cambiar a uno de los posibles países asociados a la IP (" + paisesAsocIP + ")";
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalCambioPais.isVisibleModalUntil(dFTest.driver, 0)) {
                listVals.add(1, State.Defect);
            }
            if (paisAccesoPrevio==null) {
                 if (ModalCambioPais.isLinkToConfirmPais(dFTest.driver, paisAccesoNoIP.getNombre_pais())) {
                     listVals.add(2, State.Defect);
                 }
            }
            else {
                if (paisConfirmado==null) { 
                    if (!ModalCambioPais.isLinkToConfirmPais(dFTest.driver, paisAccesoPrevio.getUrlPaisEstandar(urlBaseTest))) {
                        listVals.add(2, State.Defect);
                    }
                }
                else {
                    if (ModalCambioPais.isLinkToConfirmPais(dFTest.driver, paisAccesoPrevio.getNombre_pais())) {
                        listVals.add(2, State.Defect);
                    }
                }
            }
            //3)
            boolean hayBoton = false;
            it = listPaisAsocIP.iterator();
            while (it.hasNext() && !hayBoton) {
                Pais paisTmp = it.next();
                String urlAccesoPais = paisTmp.getUrlPaisEstandar(urlBaseTest);
                if (ModalCambioPais.isButtonToChangePais(dFTest.driver, urlAccesoPais)) {
                    paisAsocIP = paisTmp;
                    hayBoton = true;
                }
            }
                 
            if (!hayBoton)
                listVals.add(3, State.Defect);
                        
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return paisAsocIP;
    }
    
    /**
     * Valicaciones correspondientes al caso en que después del acceso NO aparece el modal de confirmacióin del país
     * @throws Exception
     */
    private static void validacAccesoNoApareceModal(DatosStep datosStep, String urlBaseTest, Pais paisPrevConf, DataFmwkTest dFTest) throws Exception {
        String nombrePaisPrevConf = paisPrevConf.getNombre_pais();
        String hrefPaisPrevConf = paisPrevConf.getUrlPaisEstandar(urlBaseTest);
        
        //Validaciones 
        String descripValidac = 
            "1) No aparece un modal solicitando confirmación de país<br>" +
            "2) Se ha redirigido a la URL del país confirmado previamente <b>" + nombrePaisPrevConf + "</b> (" + hrefPaisPrevConf + ")";
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (ModalCambioPais.isVisibleModalUntil(dFTest.driver, 0)) { 
                listVals.add(1, State.Defect);
            }
            if (!(dFTest.driver.getCurrentUrl().toLowerCase().contains(hrefPaisPrevConf.toLowerCase()))) {
                listVals.add(2, State.Defect);
            }
                    
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
    }
    
    /**
     * Se selecciona el botón de confirmación del país existente en el modal de cambio de país
     */
    public static DatosStep selectConfirmPaisModal(DataFmwkTest dFTest) throws Exception {
        //Obtenemos el país y el HRef asociados al botón para confirmar el cambio de país
        String paisBotonCambio = ModalCambioPais.getTextPaisButtonChagePais(dFTest.driver);
        String hrefBotonCambioPais = ModalCambioPais.getHRefPaisButtonChagePais(dFTest.driver);
        
        //Step. Confirmamos el país indicado en el modal
        DatosStep datosStep = new DatosStep(
            "Confirmamos la propuesta de país del modal <b>" + paisBotonCambio + "</b>", 
            "Se redirige a la URL " + hrefBotonCambioPais);
        try {
            ModalCambioPais.clickButtonChangePais(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep);}        
        
        String descripValidac = 
        	"1) Se redirige a la URL del país " + paisBotonCambio + " (" + hrefBotonCambioPais + ")"; 
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!(dFTest.driver.getCurrentUrl().toLowerCase().contains(hrefBotonCambioPais.toLowerCase()))) {
                listVals.add(1, State.Defect);
            }
    
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }

    
    /**
     * Accede y testea el estado de un nodo concreto de amazon (especificado mediante cookies de sesión). Almacena los datos obtenidos mediante llamada a la URL de status 
     * @param urlControl url para testear el estado del nodo
     */
    public static DatosStep testNodoState(NodoStatus nodo, DataFmwkTest dFTest) throws Exception {
        //Step. Comprobamos si el nodo responde
        DatosStep datosStep = new DatosStep       (
            "Invocamos a <b>" + nodo.getStatusJSON().pathStatus + "</b> para obtener los datos JSON", 
            "El nodo se encuentra en un estado correcto");
        datosStep.setSaveHtmlPage(SaveWhen.Always);
        try {
            //Cargamos al URL de status y almacenamos los datos JSON obtenidos
            nodo.setDataStateNodeFromBrowser(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
                
        //Validaciones
        String descripValidac = 
            "1) El estatus es \"UP\"";
        datosStep.setNOKstateByDefault();        
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            assertTrue(nodo.getStatusJSON().isStatusOk());
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }

    /**
     * Validaciones comparativa del status de 2 nodos concretos
     */
    public static void validaCompareStatusNodos(DatosStep datosStep, NodoStatus nodoAct, NodoStatus nodoAnt)
    throws Exception {
        //Obtenemos el stock actual
        JSONObject stockAct = nodoAct.getStatusJSON().getWarehouses();
        
        //Obtenemos la versión del shopconfig
        String vShopCAct = nodoAct.getStatusJSON().getVShopconfig();
        String vShopCAnt = nodoAnt.getStatusJSON().getVShopconfig();
        
        //Obtenemos el contador de sesiones
        int sessionCAnt = nodoAnt.getStatusJSON().getSessionCount();
        int sessionCAct = nodoAct.getStatusJSON().getSessionCount();
        
        //Validaciones
        String descripValidac = 
            "1) El stock de los almacenes (" + stockAct + ") coincide (+-10%) con el del nodo " + nodoAnt.getIp() + "<br>" +
            "2) La versión del shopconfig (" + vShopCAct + ") es igual que la del nodo " + nodoAnt.getIp() + "<br>" +
            "3) El contador de sesiones (" + sessionCAct + ") coincide (+-50%) con el del nodo " + nodoAnt.getIp() + " (" + sessionCAnt + ")";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!nodoAct.comparaStocksWarehouses(nodoAnt, 0.10)) {
                listVals.add(1, State.Warn);
            }
            if (vShopCAct.compareTo(vShopCAnt)!=0) {
                listVals.add(2, State.Warn);
            }
            float divisor = Math.abs(sessionCAct - sessionCAnt);
            float dividendo = Math.max(sessionCAct, sessionCAnt);
            if ((divisor / dividendo) > 0.5) {
                listVals.add(3, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
