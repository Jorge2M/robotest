package com.mng.robotest.test.utils;

import com.mng.robotest.conftestmaker.AppEcom;

public enum LevelPais {
	TOP(3,3),
	CON_COMPRA_NO_TOP(2,2),
	SIN_COMPRA(1,1);
	
	int numBannersTestShop;
	int numBannersTestOutlet;
	private LevelPais(int numBannersTestShop, int numBannersTestOutlet) {
		this.numBannersTestShop = numBannersTestShop;
		this.numBannersTestOutlet = numBannersTestOutlet;
	}
	
	public int getNumBannersTest(AppEcom app) {
		switch (app) {
		case shop:
			return numBannersTestShop;
		default:
			return numBannersTestOutlet;
		}
	}
}
