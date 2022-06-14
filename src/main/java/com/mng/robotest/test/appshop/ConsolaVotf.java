package com.mng.robotest.test.appshop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.Menu;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog.Article;
import com.mng.robotest.test.stpv.votfcons.ConsolaVotfStpV;
import com.mng.robotest.test.utils.PaisGetter;

public class ConsolaVotf {

	@Test (
		groups={"Canal:desktop_App:votf"},
		description="[PRE] Generar pedido mediante la consola de VOTF")
	public void VTF001_GenerarPedido() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();

		ConsolaVotfStpV.accesoPagInicial(driver);
		ConsolaVotfStpV.selectEntornoTestAndCons("Prepro k8s", driver);
		
		int numProdsMax = 15;
		List<Article> listArticles = getArticlesAvailable(numProdsMax, driver);
		String idArticle = listArticles.get(0).getArticleId();
		ConsolaVotfStpV.inputArticleAndTiendaDisp(idArticle, "00011459", driver);
		ConsolaVotfStpV.consultarTiposEnvio(driver);
		for (int i=0; i<numProdsMax; i++) {
			//Iteramos hasta que damos con un artículo que existe en la tienda
			idArticle = listArticles.get(i).getArticleId();
			if (ConsolaVotfStpV.consultarDispEnvDomic(idArticle, driver)) {
				break;
			}
		}
		
		ConsolaVotfStpV.consultarDispEnvTienda(idArticle, driver);
		String codigoPedido = ConsolaVotfStpV.realizarSolicitudTienda(idArticle, driver);
		String codigoPedidoFull = ConsolaVotfStpV.obtenerPedidos(codigoPedido, driver);
		ConsolaVotfStpV.seleccionarPedido(codigoPedidoFull, driver);
		ConsolaVotfStpV.selectPreconfPedido(codigoPedidoFull, driver);
		ConsolaVotfStpV.selectConfPedido(codigoPedidoFull, driver);
	}

	private List<Article> getArticlesAvailable(int numProductsMax, WebDriver driver) throws Exception {
		Pais espana = PaisGetter.get(PaisShop.ESPANA);
		GetterProducts getterProducts = new GetterProducts.Builder("https://shop.mango.com/", espana.getCodigo_alf(), AppEcom.votf, driver).
				linea(LineaType.she).
				menu(Menu.Shorts).
				numProducts(numProductsMax).
				pagina(1).
				build();
		
		
		List<Article> listArticles = new ArrayList<>();
		for (GarmentCatalog garment : getterProducts.getAll()) {
			listArticles.add(garment.getArticleWithMoreStock());
		}
		return listArticles;
	}
}