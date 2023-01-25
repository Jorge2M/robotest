package com.mng.robotest.domains.votfconsole.tests;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.votfconsole.steps.ConsolaVotfSteps;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.Menu;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;


public class Vtf001 extends TestBase {

	private final ConsolaVotfSteps consolaVotfSteps = new ConsolaVotfSteps();  
	
	@Override
	public void execute() throws Exception {
		consolaVotfSteps.accesoPagInicial();
		consolaVotfSteps.selectEntornoTestAndCons("PRE HTTPS INTERNAL K8S");

		String idArticle = searchForArticleAvailable(20, consolaVotfSteps, driver);
		consolaVotfSteps.consultarDispEnvTienda(idArticle);
		String codigoPedido = consolaVotfSteps.realizarSolicitudTienda(idArticle);
		String codigoPedidoFull = consolaVotfSteps.obtenerPedidos(codigoPedido);
		consolaVotfSteps.seleccionarPedido(codigoPedidoFull);
		consolaVotfSteps.selectPreconfPedido(codigoPedidoFull);
		consolaVotfSteps.selectConfPedido(codigoPedidoFull);
	}
	
	private String searchForArticleAvailable(int numProdsMax, ConsolaVotfSteps consolaVotfSteps, WebDriver driver) 
			throws Exception {
		List<Article> listArticles = getArticlesAvailable(numProdsMax, driver);
		String idArticle = listArticles.get(0).getArticleId();
		consolaVotfSteps.inputArticleAndTiendaDisp(idArticle, "00011459");
		consolaVotfSteps.consultarTiposEnvio();
		
		for (int i=0; i<numProdsMax; i++) {
			idArticle = listArticles.get(i).getArticleId();
			if (consolaVotfSteps.consultarDispEnvDomic(idArticle)) {
				break;
			}
		}
		return idArticle;
	}

	private List<Article> getArticlesAvailable(int numProductsMax, WebDriver driver) throws Exception {
		Pais espana = PaisGetter.from(PaisShop.ESPANA);
		GetterProducts getterProducts = new GetterProducts.Builder("https://shop.mango.com/", espana.getCodigo_alf(), AppEcom.votf, driver).
				linea(LineaType.SHE).
				menu(Menu.SHORTS).
				numProducts(numProductsMax).
				pagina(1).
				build();
		
		return Article.getArticlesCandidateForTest(getterProducts.getAll());
	}

}
