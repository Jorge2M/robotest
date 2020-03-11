package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment.Article;
import com.mng.robotest.test80.mango.test.stpv.votfcons.ConsolaVotfStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class ConsolaVotf {

	@Test (
		groups={"Canal:desktop_App:votf"},
		description="[PRE] Generar pedido mediante la consola de VOTF")
	public void VTF001_GenerarPedido() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();

		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		String paginaIniVOTF = inputParamsSuite.getUrlBase();
		ConsolaVotfStpV.accesoPagInicial(paginaIniVOTF, driver);
		ConsolaVotfStpV.selectEntornoTestAndCons("Preproducción", driver);
		
		int numProdsMax = 10;
		List<Article> listArticles = getArticlesAvailable(numProdsMax);
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

	private List<Article> getArticlesAvailable(int numProductsMax) throws Exception {
		Pais españa = PaisGetter.get(PaisShop.España);
		GetterProducts getterProducts = new GetterProducts.Builder("https://shop.mango.com/", españa.getCodigo_alf(), AppEcom.votf).
				linea(LineaType.she).
				seccion("prendas").
				galeria("camisas").
				familia("14").
				numProducts(numProductsMax).
				pagina(1).
				build();
		
		
		List<Article> listArticles = new ArrayList<>();
		for (Garment garment : getterProducts.getAll()) {
			listArticles.add(garment.getArticleWithMoreStock());
		}
		return listArticles;
	}
}