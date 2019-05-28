package com.mng.robotest.test80;

import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;

public class TestNewServiceJSON {

	public static void main(String[] args) throws Exception {
		ManagerArticlesStock managerStock = new ManagerArticlesStock(AppEcom.shop, "https://shop.mango.com/preHome.faces", 5);
		managerStock.getArticles("001");
		managerStock.getArticles("001");
	}

}
