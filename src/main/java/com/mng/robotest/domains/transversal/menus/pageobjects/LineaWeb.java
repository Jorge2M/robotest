package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;

public class LineaWeb extends PageBase implements LineaActions {

	public enum LineaType { 
		SHE("mujer", "outlet", "she", "she", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		HE("hombre", "outletH", "he", "he", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		NINA("ninas", "outletA", "kidsA", "nina", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		NINO("ninos", "outletO", "kidsO", "nino", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		KIDS("kids", "outletX", "kids", "kids", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)),
		TEEN("teen", "?", "teen", "teen", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)),
		HOME("home", "home", "home", "home", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet));
		
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
			if (this.compareTo(LineaType.SHE)==0 &&
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
	
	public enum SublineaType { 
		TEEN_NINA("teenA", "teenA", "nina", LineaType.TEEN),
		TEEN_NINO("teenO", "teenO", "nino", LineaType.TEEN),
		NINA_NINA("nina", "nina", "nina", LineaType.NINA), 
		NINA_BEBE("babyNina", "babyNina", "bebe", LineaType.NINA),
		NINO_NINO("nino", "nino", "nino", LineaType.NINO), 
		NINO_BEBE("babyNino", "babyNino", "bebe", LineaType.NINO); 
		
		String idShop = "";
		String idOutlet = "";
		String text;
		LineaType parentLine;
		private SublineaType(String idShop, String idOutlet, String text, LineaType parentLine) {
			this.idShop = idShop;
			this.idOutlet = idOutlet;
			this.text = text;
			this.parentLine = parentLine;
		}
		
		public String getId(AppEcom app) {
			if (app==AppEcom.outlet) {
				return idOutlet;
			}
			return idShop;
		}
		
		public String getText() {
			return text;
		}
		
		public String getNameUpper() {
			return name().toUpperCase();
		}
		
		public LineaType getParentLine() {
			return this.parentLine;
		}
	}	
	
	private final LineaType linea;
	private final SublineaType sublinea;
	private final LineaActions lineaActions;
	
	public LineaWeb(LineaType linea) {
		this.linea = linea;
		this.sublinea = null;
		this.lineaActions = LineaActions.make(this, channel);
	}
	public LineaWeb(LineaType linea, SublineaType sublinea) {	
		this.linea = linea;
		this.sublinea = sublinea;
		this.lineaActions = LineaActions.make(this, channel);
	}
	
	@Override
	public void clickLinea() {
		lineaActions.clickLinea();
		if (!lineaActions.isLineaSelected(1)) {
			lineaActions.clickLinea();
		}
	}
	@Override
	public void clickSublinea() {
		clickLineaControllingNoSuchElement();
		if (!lineaActions.isSublineaSelected(1)) {
			lineaActions.clickSublinea();
		}
	}
	private void clickLineaControllingNoSuchElement() {
		try {
			lineaActions.clickSublinea();
		} 
		catch (NoSuchElementException e) {
			if (channel.isDevice()) {
				clickLinea();
			} else {
				hoverLinea();
			}
		}
	}
	
	@Override
	public void hoverLinea() {
		lineaActions.hoverLinea();
		if (!lineaActions.isLineaSelected(1)) {
			lineaActions.hoverLinea();
		}
	}
	@Override
	public void hoverSublinea() {
		for (int i=0; i<3; i++) {
			lineaActions.hoverSublinea();
			if (lineaActions.isSublineaSelected(1)) {
				break;
			}
		}
	}
	@Override
	public boolean isLineaPresent(int seconds) {
		return lineaActions.isLineaPresent(seconds);
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return lineaActions.isSublineaPresent(seconds);
	}
	
	@Override
	public boolean isLineaSelected(int seconds) {
		return lineaActions.isLineaSelected(seconds);
	}
	
	@Override
	public boolean isSublineaSelected(int seconds) {
		return lineaActions.isSublineaSelected(seconds);
	}
	
	public LineaType getLinea() {
		return linea;
	}
	public SublineaType getSublinea() {
		return sublinea;
	}
	
}
