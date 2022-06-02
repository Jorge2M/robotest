package com.mng.robotest.test.appshop;

import java.io.Serializable;
import java.util.*;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.factoryes.NodoStatus;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;
import com.mng.robotest.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class TestNodos implements Serializable {

	private static final long serialVersionUID = 1986211132936039272L;
	
	private final static Pais espana = PaisGetter.get(PaisShop.Espana);
	private final static IdiomaPais castellano = espana.getListIdiomas().get(0);
	  
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
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite(); 
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.setAppEcom(this.nodo.getAppEcom());
		dCtxSh.pais = espana;
		dCtxSh.idioma = castellano;
		return dCtxSh;
	}

	@Test (
		groups={"Canal:desktop_App:all"},
		description="Verificar funcionamiento general en un nodo. Validar status, acceso, click banner, navegación por las líneas...")
	public void NOD001_TestNodo() throws Throwable {
		DataCtxShop dCtxSh = getCtxShForTest();
		WebDriver driver = TestMaker.getDriverTestCase();
		TestCaseTM.addNameSufix(this.index_fact);
		AppEcom appE = nodo.getAppEcom();
		NodoStatus nodoAnt = findNodoForCompareStatus(listaNodos, nodo);

		new PagePrehomeStpV(dCtxSh, driver).seleccionPaisIdiomaAndEnter(true);
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		if (appE==AppEcom.shop) {
			selectMenuPantalones(dCtxSh, driver);
			PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, dCtxSh.appE, driver);
			ListDataArticleGalery listArticlesPant = pageGaleria.getListDataArticles();
			this.nodo.setArticlesNuevo(listArticlesPant);
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
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		secMenusStpV.accesoMenuXRef(menuVestidos, dCtxSh);
		
		secMenusStpV.seleccionLinea(LineaType.he, null, dCtxSh);
		secMenusDesktopStpV.countSaveMenusEntorno (LineaType.he, null, nodo.getIp(), autAddr);
		secMenusStpV.seleccionLinea(LineaType.nina, SublineaType.nina_nina, dCtxSh);	
		secMenusDesktopStpV.countSaveMenusEntorno(LineaType.nina, SublineaType.nina_nina, nodo.getIp(), autAddr);
		secMenusStpV.seleccionLinea(LineaType.nino, SublineaType.nino_bebe, dCtxSh);	 
		secMenusDesktopStpV.countSaveMenusEntorno(LineaType.nino, SublineaType.nino_bebe, nodo.getIp(), autAddr);

		this.nodo.setTested(true);
	}

	private void selectMenuPantalones(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(
				dCtxSh.appE, KeyMenu1rstLevel.from(
					LineaType.nina, 
					SublineaType.nina_nina, "pantalones"));
			SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
			secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh);
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
			if (nodoCandidato.getAppEcom() == nodoAct.getAppEcom() &&
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