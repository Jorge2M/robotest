package com.mng.robotest.test80.mango.test.appshop;

import java.io.Serializable;
import java.util.*;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.NodoStatus;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
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
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class TestNodos implements Serializable {

	private static final long serialVersionUID = 1986211132936039272L;
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
	  
    private String index_fact;
    
    private HashMap<String, NodoStatus> listaNodos;
    public NodoStatus nodo;
    public int prioridad;
    public String autAddr;
    public String urlStatus;
    public String urlErrorpage;
    public boolean testLinksPie;
    
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
    
    private DataCtxShop getCtxShForTest() throws Exception {
    	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite(); 
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        dCtxSh.setAppEcom(this.nodo.getAppEcom());
        //dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
        dCtxSh.pais = españa;
        dCtxSh.idioma = castellano;
        return dCtxSh;
    }

    @Test (
    	groups={"Canal:desktop_App:all"},
    	description="Verificar funcionamiento general en un nodo. Validar status, acceso, click banner, navegación por las líneas...")
    public void NOD001_TestNodo() throws Throwable {
    	DataCtxShop dCtxSh = getCtxShForTest();
    	WebDriver driver = TestMaker.getDriverTestCase();
    	TestMaker.getTestCase().setRefineDataName(index_fact);
        AppEcom appE = nodo.getAppEcom();
        AccesoStpV.testNodoState(nodo, driver);
        if (this.nodo.getStatusJSON().isStatusOk()) {
            NodoStatus nodoAnt = findNodoForCompareStatus(listaNodos, nodo);
            if (nodoAnt!=null) {
                AccesoStpV.validaCompareStatusNodos(this.nodo, nodoAnt);
            }
           
            PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, true, driver);
            SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
            if (appE==AppEcom.shop) {
            	secMenusStpV.seleccionLinea(LineaType.nuevo, null, dCtxSh);
                PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, dCtxSh.appE, driver);
                NombreYRefList listArticlesNuevoAct = pageGaleria.getListaNombreYRefArticulos();
                this.nodo.setArticlesNuevo(listArticlesNuevoAct);
                PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
                if (nodoAnt!=null && nodoAnt.getArticlesNuevo()!=null) {
                    pageGaleriaStpV.validaNombresYRefEnOrden(nodoAnt, this.nodo);
                }
                
                pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
            }
			
            SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, driver);
            secMenusDesktopStpV.seleccionLinea(LineaType.she);
            secMenusDesktopStpV.countSaveMenusEntorno(LineaType.she, null, nodo.getIp(), autAddr);
            int maxBannersToLoad = 1;
            SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, driver);
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