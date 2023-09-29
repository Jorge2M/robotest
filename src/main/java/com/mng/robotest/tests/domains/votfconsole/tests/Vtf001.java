package com.mng.robotest.tests.domains.votfconsole.tests;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.votfconsole.steps.ConsolaVotfSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;

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
		var listArticles = getArticlesAvailable(numProdsMax, driver);
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
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigoAlf(), AppEcom.votf, driver)
				.numProducts(numProductsMax)
				.build();		
		
		return Article.getArticlesForTest(getterProducts.getAll());
	}

}
