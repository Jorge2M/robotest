package com.mng.robotest.test.utils;

import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;


public enum LevelPais {
	TOP(4, 2, 2, 2, 2, 2),
	CON_COMPRA_NO_TOP(2, 1, 1, 1, 1, 1),
	SIN_COMPRA(1, 1, 1, 1, 1, 1);
	
	private final int numBannersShe; 
	private final int numBannersHe;
	private final int numBannersNina; 
	private final int numBannersNino;
	private final int numBannersTeen; 
	private final int numBannersHome;
	private LevelPais(
			int numBannersShe, int numBannersHe, int numBannersNina, 
			int numBannersNino, int numBannersTeen, int numBannersHome) {
		this.numBannersShe = numBannersShe; 
		this.numBannersHe = numBannersHe;
		this.numBannersNina = numBannersNina; 
		this.numBannersNino = numBannersNino;
		this.numBannersTeen = numBannersTeen; 
		this.numBannersHome = numBannersHome;
	}
	
	public int getNumBannersTest(LineaType linea) {
		switch (linea) {
		case she:
			return numBannersShe;
		case he:
			return numBannersHe;
		case nina:
			return numBannersNina;
		case nino:
			return numBannersNino;
		case teen:
			return numBannersTeen;
		case home:
			return numBannersHome;
		default:
			return numBannersHome;
		}
	}
}
