package com.mng.robotest.test.utils.testab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.service.testab.TestABGoogleExp;
import com.mng.robotest.conf.AppEcom;
import com.github.jorge2m.testmaker.conf.Channel;

public enum TestABGoogleExpImpl implements TestABGoogleExp {

	SELECTOR_FICHA_MOBIL(
		"vpc6sLvUTbObe3jyJ97xzQ",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Nuevo"),
		Arrays.asList(Channel.mobile,Channel.tablet),
		Arrays.asList(AppEcom.shop, AppEcom.votf)
	);
		
	private String valueCookieShop;
	private String valueCookieOutlet;
	private List<Integer> variantesInt;
	private List<Channel> channels;
	private List<AppEcom> apps;
	private TestABGoogleExpImpl(
			String valueCookieShop, String valueCookieOutlet, List<Integer> variantesInt, List<String> variantes, 
			List<Channel> channels, List<AppEcom> apps) {
		this.valueCookieShop = valueCookieShop;
		this.valueCookieOutlet = valueCookieOutlet;
		this.variantesInt = variantesInt;
		this.channels = channels;
		this.apps = apps;
	}
	
	@Override
	public List<Integer> getVariantes() {
		return this.variantesInt;
	}
	@Override
	public List<Channel> getChannels() {
		return this.channels;
	}
	@Override
	public List<Enum<?>> getApps() {
		List<Enum<?>> listReturn = new ArrayList<>();
		for (Enum<?> app : this.apps) {
			listReturn.add(app);
		}
		return listReturn;
	}
	@Override
	public String getValueCookie(Enum<?> app) {
		AppEcom appE = (AppEcom)app;
		switch (appE) {
		case outlet:
			return valueCookieOutlet;
		default:
		case shop:
			return valueCookieShop;
		}
	}
}
