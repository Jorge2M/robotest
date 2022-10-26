package com.mng.robotest.domains.compra.tests;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;


public class Com011 extends TestBase {

	public Com011() throws Exception {
		dataTest.setUserRegistered(true);
		dataTest.setPais(PaisGetter.get(PaisShop.DEUTSCHLAND));
	}
	
	@Override
	public void execute() throws Exception {
//		List<GarmentCatalog> articles = getArticles(100);
//		for (GarmentCatalog article : articles) {
//			System.out.println("", article.get)
//		}
	}
//	
//	private Optional<ProductRedis> getProductFromApi(GarmentCatalog garment) throws Exception {
//		GetterProductApiCanonical getterProduct = new GetterProductApiCanonical(pais.getCodigo_alf(), idioma);
//		return getterProduct.getProduct(garment.getGarmentId());
//	}	

}
