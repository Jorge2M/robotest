package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import java.util.*;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.NodoStatus;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRefList;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

public class TestNodos extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	  
    private String index_fact;
    
    private HashMap<String, NodoStatus> listaNodos;
    public NodoStatus nodo;
    public int prioridad;
    public String autAddr;
    public String urlStatus;
    public String urlErrorpage;
    public boolean testLinksPie;
    private DataCtxShop dCtxSh;
    
    /**
     * @param listaNodos lista de todos los nodos que está previsto testear
     * @param nodo nodo concreto que se testeará en el test
     * @param autAddr URL de acceso a la shop/outlet
     * @param urlStatus URL correspondiente al servicio que devuelve datos a nivel del 'status' del nodo
     * @param urlErrorpage URL correspondiente al errorPage
     * @param testLinksPie si se prueban o no los links a pié de página
     */
    public TestNodos(HashMap<String, NodoStatus> listaNodos, NodoStatus nodo, int prioridad, String autAddr, String urlStatus, String urlErrorpage, boolean testLinksPie) {
        this.index_fact = "Nodo-" + nodo.getIp();
        this.listaNodos = listaNodos;
		this.nodo = nodo;
		this.prioridad = prioridad;
		this.autAddr = autAddr;
		this.urlStatus = urlStatus;
		this.urlErrorpage = urlErrorpage;
		this.testLinksPie = testLinksPie;
    }	  
	  
    @BeforeMethod
    @Parameters({"brwsr-path", "urlBase", "Channel"})
    public void login(String bpath, String urlAcceso, String channel, ITestContext context, Method method) throws Exception {
        //Recopilación de parámetros
        dCtxSh = new DataCtxShop();
        dCtxSh.setChannel(channel);
        dCtxSh.setAppEcom(this.nodo.getAppEcom());
        dCtxSh.urlAcceso = urlAcceso;

        //Si no existe, obtenemos el país España
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        dCtxSh.pais = this.españa;
        dCtxSh.idioma = this.castellano;
        
        Utils.storeDataShopForTestMaker(bpath, this.index_fact, dCtxSh, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }	
	
    @Test (description="Verificar funcionamiento general en un nodo. Validar status, acceso, click banner, navegación por las líneas...")
    public void NOD001_TestNodo() throws Throwable {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        AppEcom appE = this.nodo.getAppEcom();
        
        //Step+Validation. Acceso y testeo del estado del nodo (+ almacenamiento de datos en el gestor JSON)
        AccesoStpV.testNodoState(this.nodo, dFTest.driver);
		
        //Sólo validamos el nodo si se encuentra en estado ok
        if (this.nodo.getStatusJSON().isStatusOk()) {

            //Buscamos un nodo anterior similar al actual con el que comparar el resultado del Status
            NodoStatus nodoAnt = this.findNodoForCompareStatus(this.listaNodos, this.nodo);
            
            //Si hemos encontrado un nodo con el que comparar, pues eso, comparamos
            //Validaciones comparativa del status de los 2 nodos (actual y anteior) concretos
            if (nodoAnt!=null) {
                AccesoStpV.validaCompareStatusNodos(this.nodo, nodoAnt);
            }
           
            //Step+Validacs. Accedemos a España con idioma Español
            PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, true/*execValidacs*/, dFTest.driver);
            
            SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, dFTest.driver);
            if (appE==AppEcom.shop) {
            	secMenusStpV.seleccionLinea(LineaType.nuevo, null, dCtxSh);

                //Obtenemos y almacenamos los artículos de la galería Nuevo
                PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, dCtxSh.appE, dFTest.driver);
                NombreYRefList listArticlesNuevoAct = pageGaleria.getListaNombreYRefArticulos();
                this.nodo.setArticlesNuevo(listArticlesNuevoAct);
                
                //Validamos que los artículos de la galería son los mismos (y están igualmente ordenados) que en el nodo anterior
                PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
                if (nodoAnt!=null && nodoAnt.getArticlesNuevo()!=null) {
                    pageGaleriaStpV.validaNombresYRefEnOrden(nodoAnt, this.nodo);
                }
                
                //Validaciones. En shop validamos que exista un porcentaje mínimo de panorámicas
                pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
            }
			
            //Step. Seleccionar la línea She
            SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, dFTest.driver);
            secMenusDesktopStpV.seleccionLinea(LineaType.she);
			
            //Step. Contamos / Validamos / almacenamos los menús de She (comprobamos que son iguales que en anteriores nodos)
            secMenusDesktopStpV.countSaveMenusEntorno(LineaType.she, null, nodo.getIp(), autAddr);
			
            //Step. Seleccionamos / Validamos el 1er Banner
            int maxBannersToLoad = 1;
            SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, dFTest.driver);
            secBannersStpV.testPageBanners(dCtxSh, 1);
            if (appE==AppEcom.outlet) {
                Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
                secMenusStpV.accesoMenuXRef(menuVestidos, dCtxSh);
            } else {
            	Linea lineaNuevo = dCtxSh.pais.getShoponline().getLinea(LineaType.nuevo);
            	String idCarruselMujer = lineaNuevo.getListCarrusels()[0];
            	secMenusDesktopStpV.stepSeleccionaCarrusel(LineaType.nuevo, idCarruselMujer);
            }
			
            secMenusStpV.seleccionLinea(LineaType.he, null, dCtxSh);
            secMenusDesktopStpV.countSaveMenusEntorno (LineaType.he, null, nodo.getIp(), autAddr);
            secMenusStpV.seleccionLinea(LineaType.nina, SublineaNinosType.nina, dCtxSh);	
            secMenusDesktopStpV.countSaveMenusEntorno(LineaType.nina, SublineaNinosType.nina, nodo.getIp(), autAddr);
            secMenusStpV.seleccionLinea(LineaType.nino, SublineaNinosType.bebe_nino, dCtxSh);     
            secMenusDesktopStpV.countSaveMenusEntorno(LineaType.nino, SublineaNinosType.bebe_nino, nodo.getIp(), autAddr);
            secMenusStpV.seleccionLinea(LineaType.violeta, null, dCtxSh);	
            secMenusDesktopStpV.countSaveMenusEntorno (LineaType.violeta, null, nodo.getIp(), autAddr);
            
            this.nodo.setTested(true);
        }
    }
    
    /**
     * Se busca un nodo de entre la lista de nodos con el que poder realizar la comparativa a nivel de Status con el nodo actual
     * Se prioriza la devolución de un nodo que ya esté completamente testeado 
     */
    protected NodoStatus findNodoForCompareStatus(HashMap<String, NodoStatus> listaNodosI, NodoStatus nodoAct) {
        NodoStatus nodoAnt = null;
        boolean encontrado = false;
        Iterator<NodoStatus> itNodos = listaNodosI.values().iterator();
        while (itNodos.hasNext() && !encontrado) { 
            NodoStatus nodoCandidato = itNodos.next();
            
            //Si ya se cargó en el nodo los datos del servicio JSON y es del mismo canal (shop/outlet)
            if (nodoCandidato.getStatusJSON().getDataStatusNode() != null &&
                nodoCandidato.getAppEcom() == nodoAct.getAppEcom() &&
                nodoCandidato.getIp().compareTo(nodoAct.getIp())!=0) {
                nodoAnt = nodoCandidato;
                if (this.nodo.getTested()) {
                    encontrado = true;
                }
            }
        }
        
        return nodoAnt;
    }
}