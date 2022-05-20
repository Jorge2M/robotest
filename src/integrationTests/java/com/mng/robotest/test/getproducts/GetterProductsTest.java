package com.mng.robotest.test.getproducts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.Menu;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.ProductLabel;
import com.mng.robotest.test.utils.PaisGetter;

public class GetterProductsTest {
	
	private static GetterProducts getterProducts;
	private final int numProducts = 20;
	
	public GetterProductsTest() throws Exception {
		if (getterProducts==null) {
			Pais españa = PaisGetter.get(PaisShop.España);
			getterProducts = new GetterProducts.Builder("https://shop.mango.com/", españa.getCodigo_alf(), AppEcom.shop, null)
					.linea(LineaType.she)
					.menu(Menu.Shorts)
					.numProducts(numProducts)
					.pagina(1)
					.build();
		}
	}
	
	@Test
	public void testGetProducts() throws Exception {
		//When
		List<GarmentCatalog> listProducts = getterProducts.getAll();
		
		//Then
		assertTrue(listProducts.size()==numProducts);
		assertTrue(listProducts.get(0).getStock()>0);
	}
	
	@Test
	public void testGetProductsManyColors() throws Exception {
		//When
		List<GarmentCatalog> listProducts = getterProducts.getFiltered(FilterType.ManyColors);
		
		//Then
		assertTrue(listProducts.size()>0);
		assertTrue(listProducts.get(0).getColors().size()>0);
	}

	@Test
	public void testGetProductCompletaTuLook() throws Exception {
		//When
		Optional<GarmentCatalog> product = getterProducts.getOneFiltered(FilterType.TotalLook);
		
		//Then
		assertTrue(product.isPresent());
	}
	
	@Test
	public void testGetProductNoOnline() throws Exception {
		//When
		List<GarmentCatalog> products = getterProducts.getFiltered(FilterType.NoOnline);
		GarmentCatalog garmentOnline = getGarmentOnline(products);
		
		//Then
		assertTrue(garmentOnline==null);
	}
	
	@Test
	public void testGetProductOnline() throws Exception {
		//When
		List<GarmentCatalog> products = getterProducts.getFiltered(FilterType.Online);
		GarmentCatalog garmentNoOnline = getGarmentNoOnline(products);
		
		//Then
		assertTrue(garmentNoOnline==null);
	}
	
	private GarmentCatalog getGarmentOnline(List<GarmentCatalog> garments) {
		for (GarmentCatalog garment: garments) {
			if (isGarmentOnline(garment)) {
				return garment;
			}
		}
		return null;
	}
	
	private GarmentCatalog getGarmentNoOnline(List<GarmentCatalog> garments) {
		for (GarmentCatalog garment: garments) {
			if (!isGarmentOnline(garment)) {
				return garment;
			}
		}
		return null;
	}
	
	private boolean isGarmentOnline(GarmentCatalog garment) {
		for (ProductLabel productLabel : garment.getLabels().getProductLabels()) {
			if (productLabel!=null && "exclusivo_online".compareTo(productLabel.getKey())==0) {
				return true;
			}
		}
		return false;
	}
}
