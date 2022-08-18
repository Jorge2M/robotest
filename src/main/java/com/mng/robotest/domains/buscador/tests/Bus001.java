package com.mng.robotest.domains.buscador.tests;

import com.mng.robotest.domains.buscador.steps.SecBuscadorSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.home.PageHomeMarcasSteps;


public class Bus001 extends TestBase {
		
	private final String categoriaProdExistente; 
	private final String catProdInexistente;
	
	private final SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps();
	private final PageHomeMarcasSteps pageHomeMarcasSteps = new PageHomeMarcasSteps(channel, app);
	
	public Bus001(String categoriaProdExistente, String catProdInexistente) throws Exception {
		super();
		this.categoriaProdExistente = categoriaProdExistente;
		this.catProdInexistente = catProdInexistente;
	}
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().oneStep(dataTest, false);
		pageHomeMarcasSteps.validateIsPageWithCorrectLineas(dataTest.pais);
		GarmentCatalog product = getProduct();
		
		secBuscadorSteps.searchArticulo(product, dataTest.pais);
		secBuscadorSteps.busquedaCategoriaProducto(categoriaProdExistente, true);
		secBuscadorSteps.busquedaCategoriaProducto(catProdInexistente, false);			
	}
	
	private GarmentCatalog getProduct() throws Exception {
		GetterProducts getterProducts = new GetterProducts
				.Builder(dataTest.pais.getCodigo_alf(), app, driver).build();
		
		return getterProducts.getAll().get(0);
	}
}
