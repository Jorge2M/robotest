package com.mng.robotest.tests.domains.compra.tests;

import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.Menu;
import com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

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
		var pagoVisa = dataTest.getPais().getPago("VISA");
		if (isOutlet()) {
			new BuilderCheckout(dataPago)
					.pago(pagoVisa)
					.build()
					.checkout(From.PREHOME);
		} else {
			new BuilderCheckout(dataPago)
					.pago(pagoVisa)
					.listArticles(getArticlesShop())
					.build()
					.checkout(From.PREHOME);
		}
	}

	private List<Article> getArticlesShop() throws NotFoundException, Exception {
		var articlesHomeOpt = getArticlesHome();
		if (!articlesHomeOpt.isPresent()) {
			throw new NotFoundException("Home Garment Not Found");
		}
		
		return Arrays.asList(articlesHomeOpt.get().get(0), articlesHomeOpt.get().get(1));
	}
	
	private Optional<List<Article>> getArticlesHome() throws Exception {
		if (isOutlet()) {
			return Optional.empty();
		}
		
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigoAlf(), app, driver)
			.linea(HOME)
			.menusCandidates(Arrays.asList(
					Menu.TOALLAS, 
					Menu.ALFOMBRAS))
			.filter(FilterType.STOCK)
			.build();
		
		return Optional.of(Article.getArticlesForTest(getterProducts.getAll()));
	}
	
}
