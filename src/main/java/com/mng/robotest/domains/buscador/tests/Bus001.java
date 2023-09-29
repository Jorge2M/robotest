package com.mng.robotest.domains.buscador.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.transversal.home.steps.PageLandingSteps;
import com.mng.robotest.repository.productlist.GetterProducts;
import com.mng.robotest.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.repository.productlist.entity.GarmentCatalog.Article;

public class Bus001 extends TestBase {
		
	private final String categoriaProdExistente; 
	private final String catProdInexistente;
	
	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageLandingSteps pageLandingSteps = new PageLandingSteps();
	
	public Bus001(String categoriaProdExistente, String catProdInexistente) {
		super();
		this.categoriaProdExistente = categoriaProdExistente;
		this.catProdInexistente = catProdInexistente;
	}
	
	@Override
	public void execute() throws Exception {
		access();
		pageLandingSteps.validateIsPageWithCorrectLineas();
		
		GarmentCatalog product = getProduct();
		secBuscadorSteps.searchArticulo(Article.getArticleForTest(product));
		secBuscadorSteps.busquedaCategoriaProducto(categoriaProdExistente, true);
		secBuscadorSteps.busquedaCategoriaProducto(catProdInexistente, false);			
	}
	
	private GarmentCatalog getProduct() throws Exception {
		var getterProducts = new GetterProducts
				.Builder(dataTest.getPais().getCodigo_alf(), app, driver).build();
		
		return getterProducts.getAll().get(0);
	}
}
