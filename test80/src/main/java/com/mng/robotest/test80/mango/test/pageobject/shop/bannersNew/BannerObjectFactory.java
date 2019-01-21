package com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew;

public class BannerObjectFactory {
	
	public static BannerObject make(BannerType bannerType) {
		switch (bannerType) {
		case Standar:
			return (new BannerStandarObject(bannerType));
		case Cabecera:
			return (new BannerCabeceraObject(bannerType));
		case Edits:
			return (new BannerEditsObject(bannerType));
		default:
			return null;
		}
	}
}
