package com.mng.robotest.test80.mango.test.getproducts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.Menu;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;

public class GetterProductsTest {
	
	private static GetterProducts getterProducts;
	private final int numProducts = 20;
	
	public GetterProductsTest() throws Exception {
		if (getterProducts==null) {
			Pais españa = PaisGetter.get(PaisShop.España);
			getterProducts = new GetterProducts.Builder("https://shop.mango.com/", españa.getCodigo_alf(), AppEcom.shop, null).
					linea(LineaType.she).
					menu(Menu.Camisas).
					numProducts(numProducts).
					pagina(1).
					build();
		}
	}
	
	@Test
	public void testGetProducts() throws Exception {
		//When
		List<Garment> listProducts = getterProducts.getAll();
		
		//Then
		assertTrue(listProducts.size()==numProducts);
		assertTrue(listProducts.get(0).getStock()>0);
	}
	
	@Test
	public void testGetProductsManyColors() throws Exception {
		//When
		List<Garment> listProducts = getterProducts.getWithManyColors();
		
		//Then
		assertTrue(listProducts.size()>0);
		assertTrue(listProducts.get(0).getColors().size()>0);
	}

	@Test
	public void testGetProductCompletaTuLook() throws Exception {
		//When
		Garment product = getterProducts.getOneWithTotalLook(null);
		
		//Then
		assertTrue(product!=null);
	}
}
