package com.mng.robotest.tests.domains.buscador.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.tests.domains.transversal.home.steps.PageLandingSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;

public class Bus001 extends TestBase {
		
	private final String categoriaProdExistente; 
	private final String catProdInexistente;
	
	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageLandingSteps pgLandingSteps = new PageLandingSteps();
	
	public Bus001(String categoriaProdExistente, String catProdInexistente) {
		super();
		this.categoriaProdExistente = categoriaProdExistente;
		this.catProdInexistente = catProdInexistente;
	}
	
	@Override
	public void execute() throws Exception {
		access();
		pgLandingSteps.checkIsPageWithCorrectLineas();
		
		GarmentCatalog product = getProduct();
		secBuscadorSteps.searchArticulo(Article.getArticleForTest(product));
		secBuscadorSteps.busquedaCategoriaProducto(categoriaProdExistente, true);
		secBuscadorSteps.busquedaCategoriaProducto(catProdInexistente, false);			
	}
	
	private GarmentCatalog getProduct() throws Exception {
		var getterProducts = new GetterProducts
				.Builder(dataTest.getPais().getCodigoAlf(), app, driver).build();
		
		return getterProducts.getAll().get(0);
	}
}
