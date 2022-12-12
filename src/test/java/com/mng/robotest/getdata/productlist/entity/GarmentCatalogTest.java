package com.mng.robotest.getdata.productlist.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.mng.robotest.getdata.canonicalproduct.entity.EntityColor;
import com.mng.robotest.getdata.canonicalproduct.entity.EntityProduct;
import com.mng.robotest.getdata.canonicalproduct.entity.EntitySize;

class GarmentCatalogTest extends GarmentCatalog {

	@Test
	void testRemoveArticlesWithoutMaxStock() {
		GarmentCatalog garmentMock = getMockGarmentCatalog();
		garmentMock.removeArticlesWithoutMaxStock();
		Color color = garmentMock.getColors().get(0);
		Size size = color.getSizes().get(0);
		
		assertEquals(1, garmentMock.getColors().size());
		assertEquals(1, color.getSizes().size());
		assertEquals("Blanco", color.getId());
		assertEquals(19, size.getId());
	}
	
	private GarmentCatalog getMockGarmentCatalog() {
		GarmentCatalog garment = new GarmentCatalog("12345678");
		
		Size size1 = new Size();
		size1.setId(19);
		size1.setStock(10);
		
		Size size2 = new Size();
		size2.setId(20);
		size2.setStock(20);
		
		Size size3 = new Size();
		size3.setId(19);
		size3.setStock(30);
		
		Size size4 = new Size();
		size4.setId(18);
		size3.setStock(25);
		
		Color color1 = new Color();
		color1.setId("Rojo");
		color1.setSizes(Arrays.asList(size1, size2));
		
		Color color2 = new Color();
		color2.setId("Blanco");
		color2.setSizes(Arrays.asList(size3, size4));
		
		garment.setColors(Arrays.asList(color1, color2));
		garment.setCanonicalProduct(getMockCanonical());
		
		return garment;
	}

	private EntityProduct getMockCanonical() {
		EntityProduct product = new EntityProduct();
		product.setId("12345678");
		
		EntitySize size1 = new EntitySize();
		size1.setId("19");
		
		EntitySize size2 = new EntitySize();
		size2.setId("20");
		
		EntitySize size3 = new EntitySize();
		size3.setId("19");
		
		EntitySize size4 = new EntitySize();
		size4.setId("18");
		
		EntityColor color1 = new EntityColor();
		color1.setId("Rojo");
		color1.setSizes(Arrays.asList(size1, size2));
		
		EntityColor color2 = new EntityColor();
		color2.setId("Blanco");
		color2.setSizes(Arrays.asList(size3, size4));
		
		product.setColors(Arrays.asList(color1, color2));
		
		return product;
	}
	
}