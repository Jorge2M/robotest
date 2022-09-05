package com.mng.robotest.test.appshop.nodos;

import java.util.Iterator;
import java.util.Map;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.factoryes.NodoStatus;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;
import com.mng.robotest.test.steps.shop.banner.SecBannersSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusDesktopSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Nod001 extends TestBase {

	private final Map<String, NodoStatus> listaNodos;
	private final NodoStatus nodo;
	private final String autAddr;
	
	public Nod001(Map<String, NodoStatus> listaNodos, NodoStatus nodo, String autAddr) {
		this.listaNodos = listaNodos;
		this.nodo = nodo;
		this.autAddr = autAddr;
	}
	
	public void execute() throws Exception {
		NodoStatus nodoAnt = findNodoForCompareStatus(listaNodos, nodo);
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter(true);
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		if (app==AppEcom.shop) {
			selectMenuPantalones();
			PageGaleria pageGaleria = PageGaleria.getNew(channel, app);
			ListDataArticleGalery listArticlesPant = pageGaleria.getListDataArticles();
			this.nodo.setArticlesNuevo(listArticlesPant);
			PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
			if (nodoAnt!=null && nodoAnt.getArticlesNuevo()!=null) {
				pageGaleriaSteps.validaNombresYRefEnOrden(nodoAnt, this.nodo);
			}

			pageGaleriaSteps.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}
		
		SecMenusDesktopSteps secMenusDesktopSteps = new SecMenusDesktopSteps();
		secMenusDesktopSteps.seleccionLinea(LineaType.she);
		secMenusDesktopSteps.countSaveMenusEntorno(LineaType.she, null, nodo.getIp(), autAddr);
		int maxBannersToLoad = 1;
		SecBannersSteps secBannersSteps = new SecBannersSteps(maxBannersToLoad);
		secBannersSteps.testPageBanners(1);
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		secMenusSteps.accesoMenuXRef(menuVestidos);
		
		secMenusSteps.seleccionLinea(LineaType.he, null);
		secMenusDesktopSteps.countSaveMenusEntorno (LineaType.he, null, nodo.getIp(), autAddr);
		secMenusSteps.seleccionLinea(LineaType.nina, SublineaType.nina_nina);	
		secMenusDesktopSteps.countSaveMenusEntorno(LineaType.nina, SublineaType.nina_nina, nodo.getIp(), autAddr);
		secMenusSteps.seleccionLinea(LineaType.nino, SublineaType.nino_bebe);	 
		secMenusDesktopSteps.countSaveMenusEntorno(LineaType.nino, SublineaType.nino_bebe, nodo.getIp(), autAddr);

		this.nodo.setTested(true);		
	}
	
	private void selectMenuPantalones() throws Exception {
		Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(
				app, KeyMenu1rstLevel.from(
					LineaType.nina, 
					SublineaType.nina_nina, "pantalones"));
			new SecMenusWrapperSteps().selectMenu1rstLevelTypeCatalog(menuPantalones);
	}
	
	/**
	 * Se busca un nodo de entre la lista de nodos con el que poder realizar la comparativa a nivel de Status con el nodo actual
	 * Se prioriza la devolución de un nodo que ya esté completamente testeado 
	 */
	protected NodoStatus findNodoForCompareStatus(Map<String, NodoStatus> listaNodosI, NodoStatus nodoAct) {
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
