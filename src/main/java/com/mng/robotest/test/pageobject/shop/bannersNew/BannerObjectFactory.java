package com.mng.robotest.test.pageobject.shop.bannersNew;

public class BannerObjectFactory {
	
	public static BannerObject make(BannerType bannerType) {
		switch (bannerType) {
		case Cabecera:
			return (new BannerCabeceraObject(bannerType));
		case Standar:
			return (new BannerStandarObject(bannerType));
		case Edits:
			return (new BannerEditsObject(bannerType));
		default:
			return null;
		}
	}
}
