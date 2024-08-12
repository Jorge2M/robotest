package com.mng.robotest.tests.domains.menus.entity;

import static com.github.jorge2m.testmaker.conf.Channel.desktop;
import static com.github.jorge2m.testmaker.conf.Channel.mobile;
import static com.github.jorge2m.testmaker.conf.Channel.tablet;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;

public enum LineaType { 
	SHE("mujer", "outlet", "she", "she", Arrays.asList(desktop, mobile, tablet)), 
	HE("hombre", "outletH", "he", "he", Arrays.asList(desktop, mobile, tablet)), 
	NINA("ninas", "outletA", "kidsA", "nina", Arrays.asList(desktop, mobile, tablet)), 
	NINO("ninos", "outletO", "kidsO", "nino", Arrays.asList(desktop, mobile, tablet)), 
	KIDS("kids", "outletX", "nina", "nina", Arrays.asList(desktop, mobile, tablet)),
	TEEN("teen", "?", "teen", "teen", Arrays.asList(desktop, mobile, tablet)),
	HOME("home", "home", "home", "home", Arrays.asList(desktop, mobile, tablet));
	
	String literal = "";
	String sufixOutlet = "";
	String id2 = "";
	String id3 = "";
	List<Channel> channels;
	private LineaType(String literal, String sufixOutlet, String id2, String id3, List<Channel> channels) {
		this.literal = literal;
		this.sufixOutlet = sufixOutlet;
		this.id2 = id2;
		this.id3 = id3;
		this.channels = channels;
	}
	
	public String getLiteral(AppEcom app) {
		if (app!=AppEcom.outlet && (this==NINA || this==NINO)) {
			return "kids";
		}
		return this.literal;
	}
	
	public String name(AppEcom app) {
		if (app!=AppEcom.outlet && (this==NINA || this==NINO)) {
			return "kids";
		}
		return name();
	}
	
	public String getNameUpper() {
		return name().toUpperCase();
	}
	
	public String getSufixOutlet(Channel channel) {
		if (this.compareTo(SHE)==0 &&
			channel.isDevice()) {
			return "outlet ";
		}
		return this.sufixOutlet;
	}
	
	public String getId2() {
		return this.id2;
	}
	
	public String getId3() {
		return this.id3;
	}
	
	public boolean isActiveIn(Channel channel) {
		for (Channel channelValid : channels) {
			if (channelValid == channel) {
				return true;
			}
		}
		return false;
	}
	
	public static LineaType getLineaType(String id) {
		for (LineaType lineaType : LineaType.values()) {
			if (lineaType.toString().compareTo(id)==0) {
				return lineaType;
			}
		}
		return null;
	} 
	
}	


