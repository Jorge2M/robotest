package com.mng.robotest.test80.mango.test.appshop;

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
		Article article = getArticleAvailable();
		String idArticle = article.getArticleId();
		
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		String paginaIniVOTF = inputParamsSuite.getUrlBase();
		ConsolaVotfStpV.accesoPagInicial(paginaIniVOTF, driver);
		ConsolaVotfStpV.selectEntornoTestAndCons("Preproducción", driver);
		ConsolaVotfStpV.inputArticleAndTiendaDisp(idArticle, "00011459", driver);
		ConsolaVotfStpV.consultarTiposEnvio(driver);
		ConsolaVotfStpV.consultarDispEnvDomic(idArticle, driver);	
		ConsolaVotfStpV.consultarDispEnvTienda(idArticle, driver);
		String codigoPedido = ConsolaVotfStpV.realizarSolicitudTienda(idArticle, driver);
		String codigoPedidoFull = ConsolaVotfStpV.obtenerPedidos(codigoPedido, driver);
		ConsolaVotfStpV.seleccionarPedido(codigoPedidoFull, driver);
		ConsolaVotfStpV.selectPreconfPedido(codigoPedidoFull, driver);
		ConsolaVotfStpV.selectConfPedido(codigoPedidoFull, driver);
	}

	private Article getArticleAvailable() throws Exception {
		Pais españa = PaisGetter.get(PaisShop.España);
		GetterProducts getterProducts = new GetterProducts.Builder("https://shop.mango.com/", españa.getCodigo_alf(), AppEcom.votf).
				linea(LineaType.she).
				seccion("prendas").
				galeria("camisas").
				familia("14").
				numProducts(1).
				pagina(1).
				build();
		
		Garment garment = getterProducts.getAll().get(0);
		return garment.getArticleWithMoreStock();
	}
}