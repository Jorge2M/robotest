package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

public class BannerObjectFactory {
	
	private BannerObjectFactory() {}
	
	public static BannerObject make(BannerType bannerType) {
		switch (bannerType) {
		case CABECERA:
			return (new BannerCabeceraObject(bannerType));
		case STANDAR:
			return (new BannerStandarObject(bannerType));
		case EDITS:
			return (new BannerEditsObject(bannerType));
		default:
			return null;
		}
	}
}
