package com.mng.robotest.tests.domains.votfconsole.tests;

import java.util.List;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.votfconsole.steps.ConsolaVotfSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;

public class Vtf001 extends TestBase {

	private final ConsolaVotfSteps conVotfSteps = new ConsolaVotfSteps();  
	
	@Override
	public void execute() throws Exception {
		conVotfSteps.accesoPagInicial();
		conVotfSteps.selectEntornoTestAndCons("PRE HTTPS INTERNAL K8S");

		String idArticle = searchForArticleAvailable(20);
		conVotfSteps.consultarDispEnvTienda(idArticle);
		String codigoPedido = conVotfSteps.realizarSolicitudTienda(idArticle);
		String codigoPedidoFull = conVotfSteps.obtenerPedidos(codigoPedido);
		conVotfSteps.seleccionarPedido(codigoPedidoFull);
		conVotfSteps.selectPreconfPedido(codigoPedidoFull);
		conVotfSteps.selectConfPedido(codigoPedidoFull);
	}
	
	private String searchForArticleAvailable(int numProdsMax) 
			throws Exception {
		var listArticles = getArticlesAvailable(numProdsMax);
		String idArticle = listArticles.get(0).getArticleId();
		conVotfSteps.inputArticleAndTiendaDisp(idArticle, "00011459");
		conVotfSteps.consultarTiposEnvio();
		
		for (int i=0; i<numProdsMax; i++) {
			idArticle = listArticles.get(i).getArticleId();
			if (conVotfSteps.consultarDispEnvDomic(idArticle)) {
				break;
			}
		}
		return idArticle;
	}

	private List<Article> getArticlesAvailable(int numProductsMax) throws Exception {
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigoAlf(), AppEcom.votf, driver)
				.numProducts(numProductsMax)
				.build();		
		
		return Article.getArticlesForTest(getterProducts.getAll());
	}

}
