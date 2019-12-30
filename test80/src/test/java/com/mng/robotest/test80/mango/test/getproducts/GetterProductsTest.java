package com.mng.robotest.test80.mango.test.getproducts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.getproducts.data.Garment;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;

public class GetterProductsTest {
	
	private static GetterProducts getterProducts;
	private final int numProducts = 20;
	
	public GetterProductsTest() throws Exception {
		if (getterProducts==null) {
			Pais españa = PaisGetter.get(PaisShop.España);
			getterProducts = new GetterProducts.Builder("https://shop.mango.com/", españa.getCodigo_alf(), AppEcom.shop).
					linea(LineaType.she).
					seccion("prendas").
					galeria("camisas").
					familia("14").
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
	public void testGetProductCompletaTuLook() {
		//When
		Garment product = getterProducts.getOneWithTotalLook();
		
		//Then
		assertTrue(product!=null);
	}
}
