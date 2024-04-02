package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;

public class BannerObjectFactory extends PageBase {
	
	private BannerObjectFactory() {}
	
	public static BannerObject make(BannerType bannerType, AppEcom app) {
		switch (bannerType) {
		case CABECERA:
			return (new BannerCabeceraObject(bannerType));
		case STANDAR:
			if (app==AppEcom.outlet) {
				return (new BannerStandarOutletObject(bannerType));
			}
			return (new BannerStandarShopObject(bannerType));
		case EDITS:
			return (new BannerEditsObject(bannerType));
		default:
			return null;
		}
	}
}
