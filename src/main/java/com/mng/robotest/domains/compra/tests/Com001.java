package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.Menu;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.UtilsTest;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

public class Com001 extends TestBase {

	private final DataPago dataPago;
	
	public Com001() throws Exception {
		dataTest.setUserRegistered(true);
		dataTest.setUserConnected("test.performance10@mango.com");
		dataTest.setPasswordUser(GetterSecrets.factory()
			.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
			.getPassword());
		
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos()
				.checkMisCompras()
				.emaiExists()
				.checkSavedCard()
				.userIsEmployee().build();
		
		dataPago = getDataPago(configCheckout);
	}
	
	@Override
	public void execute() throws Exception {
		checkout();
		checkPedidosManto(dataPago.getListPedidos());
	}

	private void checkout() throws Exception {
		if (app==AppEcom.outlet) {
			new BuilderCheckout(dataPago)
					.pago(dataTest.getPais().getPago("VISA"))
					.build()
					.checkout(From.PREHOME);
		} else {
			List<Article> articlesShop = getArticlesShop();
			new BuilderCheckout(dataPago)
					.pago(dataTest.getPais().getPago("VISA"))
					.listArticles(articlesShop)
					.build()
					.checkout(From.PREHOME);
		}
	}

	private List<Article> getArticlesShop() throws NotFoundException, Exception {
		Optional<List<Article>> articlesHomeOpt = getArticlesHome();
		if (!articlesHomeOpt.isPresent()) {
			throw new NotFoundException("Home Garment Not Found");
		}
		
		//TODO actualmente no funciona el buscador por referencia de productos Intimissimi (CFIT-1265)
		//confiamos que esté listo el 1-Junio-2023
		if (!UtilsTest.todayBeforeDate("2023-06-01")) {
			Optional<List<Article>> articlesIntimissimiOpt = getArticlesIntimissimi();
			if (!articlesIntimissimiOpt.isPresent()) {
				throw new NotFoundException("Home Garment Not Found");
			}
			return Arrays.asList(articlesHomeOpt.get().get(0), articlesIntimissimiOpt.get().get(0));
		} else {
			return Arrays.asList(articlesHomeOpt.get().get(0), articlesHomeOpt.get().get(1));
		}
	}
	
	private Optional<List<Article>> getArticlesHome() throws Exception {
		if (app==AppEcom.outlet) {
			return Optional.empty();
		}
		
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigo_alf(), app, driver)
			.linea(HOME)
			.menusCandidates(Arrays.asList(
					Menu.ALBORNOCES, 
					Menu.TOALLAS, 
					Menu.ALFOMBRAS))
			.filter(FilterType.STOCK)
			.build();
		
		return Optional.of(Article.getArticlesCandidateForTest(getterProducts.getAll()));
	}
	
	private Optional<List<Article>> getArticlesIntimissimi() throws Exception {
		if (app==AppEcom.outlet) {
			return Optional.empty();
		}
		
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigo_alf(), app, driver)
			.linea(SHE)
			.menusCandidates(Arrays.asList(
					Menu.SUJETADORES, 
					Menu.BRAGUITAS, 
					Menu.LENCERIA))
			.filter(FilterType.STOCK)
			.build();
		
		return Optional.of(Article.getArticlesCandidateForTest(getterProducts.getAll()));
	}

}
