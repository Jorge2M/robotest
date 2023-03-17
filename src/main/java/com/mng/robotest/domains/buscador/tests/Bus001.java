package com.mng.robotest.domains.buscador.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.steps.shop.home.PageHomeMarcasSteps;

public class Bus001 extends TestBase {
		
	private final String categoriaProdExistente; 
	private final String catProdInexistente;
	
	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageHomeMarcasSteps pageHomeMarcasSteps = new PageHomeMarcasSteps();
	
	public Bus001(String categoriaProdExistente, String catProdInexistente) {
		super();
		this.categoriaProdExistente = categoriaProdExistente;
		this.catProdInexistente = catProdInexistente;
	}
	
	@Override
	public void execute() throws Exception {
		access();
		pageHomeMarcasSteps.validateIsPageWithCorrectLineas();
		GarmentCatalog product = getProduct();
		
		secBuscadorSteps.searchArticulo(Article.getArticleCandidateForTest(product));
		secBuscadorSteps.busquedaCategoriaProducto(categoriaProdExistente, true);
		secBuscadorSteps.busquedaCategoriaProducto(catProdInexistente, false);			
	}
	
	private GarmentCatalog getProduct() throws Exception {
		GetterProducts getterProducts = new GetterProducts
				.Builder(dataTest.getPais().getCodigo_alf(), app, driver).build();
		
		return getterProducts.getAll().get(0);
	}
}
