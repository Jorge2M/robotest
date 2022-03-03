package com.mng.robotest.domains.apiproduct.test;

import java.util.Arrays;
import java.util.Optional;

import org.testng.annotations.InitObject;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.apiproduct.entity.ProductRedis;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.getdata.products.GetterProductApiCanonical;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.MenuI;
import com.mng.robotest.test.getdata.products.data.Garment;
import com.mng.robotest.test.utils.PaisGetter;


public class ApiProduct {
	
	private final Pais pais;
	private final LineaType linea;
	private final String idioma;
	private final MenuI menu;
	
	public ApiProduct(String pais, String idioma, String linea, MenuI menu) {
		this.pais = PaisGetter.get(pais);
		this.linea = LineaType.valueOf(linea);
		this.idioma = idioma;
		this.menu = menu;
	}
	
	@Test (
		groups={"Api", "Canal:desktop,mobile_App:all"}, alwaysRun=true,
		description="Prueba Canonical API Product",
		create=InitObject.None)
	public void API001_CanonicalProduct() throws Exception {
		
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		AppEcom app = (AppEcom)inputParamsSuite.getApp();		
		
//		GetterProducts getterProducts = new GetterProducts.Builder(pais.getCodigo_alf(), app, null)
//				.idiom(idioma)
//				.linea(linea)
//				.menusCandidates(Arrays.asList(menu))
//				.minProducts(40)
//				.build();
//		
//		for (Garment garment : getterProducts.getAll()) {
//			System.out.println(garment);
//		}
		
		getProductFromApi();
	}
	
	private void getProductFromApi() throws Exception {
		GetterProductApiCanonical getterProduct = new GetterProductApiCanonical("ES", "ES", "17020341");
		Optional<ProductRedis> product = getterProduct.getProduct();
		if (product.isPresent()) {
			System.out.println("Collection: " + product.get().getCollection());
		}
	}
}
