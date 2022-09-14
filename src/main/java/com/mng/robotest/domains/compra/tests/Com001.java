package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.Menu;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.UtilsTest;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Com001 extends TestBase {

	private final DataPago dataPago;
	
	public Com001() throws Exception {
		dataTest.userRegistered = true;
		dataTest.userConnected = "test.performance10@mango.com";
		dataTest.passwordUser = GetterSecrets.factory()
			.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
			.getPassword();
		
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
		checkPedidos();
	}

	private void checkout() throws Exception {
		if (app==AppEcom.outlet) {
			new BuilderCheckout(dataPago)
					.pago(dataTest.pais.getPago("VISA"))
					.build()
					.checkout(From.PREHOME);
		} else {
			List<GarmentCatalog> articlesShop = getArticlesShop();
			new BuilderCheckout(dataPago)
					.pago(dataTest.pais.getPago("VISA"))
					.listArticles(articlesShop)
					.build()
					.checkout(From.PREHOME);
		}
	}

	private void checkPedidos() throws Exception {
		new CompraSteps().checkPedidosManto(dataPago.getListPedidos()); 
	}
	
	private List<GarmentCatalog> getArticlesShop() throws NotFoundException, Exception {
		Optional<List<GarmentCatalog>> articlesHomeOpt = getArticlesHome();
		if (articlesHomeOpt.isEmpty()) {
			throw new NotFoundException("Home Garment Not Found");
		}
		
		//TODO actualmente no funciona el buscador por referencia de productos Intimissimi (CFIT-1265)
		//confiamos que esté listo el 1-octubre-2022
		if (!UtilsTest.dateBeforeToday("2022-11-01")) {
			Optional<List<GarmentCatalog>> articlesIntimissimiOpt = getArticlesIntimissimi();
			if (articlesIntimissimiOpt.isEmpty()) {
				throw new NotFoundException("Home Garment Not Found");
			}
			return Arrays.asList(articlesHomeOpt.get().get(0), articlesIntimissimiOpt.get().get(0));
		} else {
			return Arrays.asList(articlesHomeOpt.get().get(0), articlesHomeOpt.get().get(1));
		}
	}
	
	private Optional<List<GarmentCatalog>> getArticlesHome() throws Exception {
		if (app==AppEcom.outlet) {
			return Optional.empty();
		}
		
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.pais.getCodigo_alf(), app, driver)
			.linea(LineaType.home)
			.menusCandidates(Arrays.asList(
					Menu.Albornoces, 
					Menu.Toallas, 
					Menu.Alfombras))
			.build();
		
		return Optional.of(getterProducts.getFiltered(FilterType.Stock));
	}
	
	private Optional<List<GarmentCatalog>> getArticlesIntimissimi() throws Exception {
		if (app==AppEcom.outlet) {
			return Optional.empty();
		}
		
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.pais.getCodigo_alf(), app, driver)
			.linea(LineaType.she)
			.menusCandidates(Arrays.asList(
					Menu.Sujetadores, 
					Menu.Braguitas, 
					Menu.Lenceria))
			.build();
		
		return Optional.of(getterProducts.getFiltered(FilterType.Stock));
	}

}
