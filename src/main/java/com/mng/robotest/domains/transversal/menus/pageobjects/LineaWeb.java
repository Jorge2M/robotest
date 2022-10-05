package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;

public class LineaWeb extends PageBase implements LineaActions {

	public static enum LineaType { 
		//nuevo("nuevo", "outletN", "nuevo", "nuevo", Arrays.asList(Channel.desktop)), 
		rebajas("rebajas", "outletR", "rebajas", "rebajas", Arrays.asList(Channel.desktop)), 
		she("mujer", "outlet", "she", "she", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		he("hombre", "outletH", "he", "he", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		nina("ninas", "outletA", "kidsA", "nina", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		nino("ninos", "outletO", "kidsO", "nino", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
		kids("kids", "outletX", "kids", "kids", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)),
		teen("teen", "?", "teen", "teen", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)),
		violeta("violeta", "outletV", "violeta", "violeta", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)),
		home("home", "home", "home", "home", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet));
		
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
			if (app!=AppEcom.outlet && (this==nina || this==nino)) {
				return "kids";
			}
			return this.literal;
		}
		
		public String name(AppEcom app) {
			if (app!=AppEcom.outlet && (this==nina || this==nino)) {
				return "kids";
			}
			return name();
		}
		
		public String getNameUpper() {
			return name().toUpperCase();
		}
		
		public String getSufixOutlet(Channel channel) {
			if (this.compareTo(LineaType.she)==0) {
				if (channel.isDevice()) {
					return "outlet ";
				}
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
	
	public static enum SublineaType { 
		teen_nina("teenA", "teenA", "nina", LineaType.teen),
		teen_nino("teenO", "teenO", "nino", LineaType.teen),
		nina_nina("nina", "nina", "nina", LineaType.nina), 
		nina_bebe("babyNina", "babyNina", "bebe", LineaType.nina),
		nino_nino("nino", "nino", "nino", LineaType.nino), 
		nino_bebe("babyNino", "babyNino", "bebe", LineaType.nino); 
		
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
			switch (app) {
			case outlet:
				return idOutlet;
			default:
				return idShop;
			}
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
	
	private final LineaActions lineaActions = LineaActions.make(this, channel);
	
	public LineaWeb(LineaType linea) {
		this.linea = linea;
		this.sublinea = null;
	}
	public LineaWeb(LineaType linea, SublineaType sublinea) {	
		this.linea = linea;
		this.sublinea = sublinea;
	}
	
	@Override
	public void clickLinea() {
		lineaActions.clickLinea();
	}

	@Override
	public void clickSublinea() {
		lineaActions.clickSublinea();
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
