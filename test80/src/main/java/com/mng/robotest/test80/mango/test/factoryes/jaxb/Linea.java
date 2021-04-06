package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;


public class Linea implements Serializable {

	private static final long serialVersionUID = -4709433951288421080L;

	public static enum LineaType { 
    	nuevo("nuevo", "outletN", "nuevo", "nuevo", Arrays.asList(Channel.desktop)), 
    	rebajas("rebajas", "outletR", "rebajas", "rebajas", Arrays.asList(Channel.desktop)), 
    	she("mujer", "outlet", "she", "she", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
    	he("hombre", "outletH", "he", "he", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
    	nina("ninas", "outletA", "kidsA", "nina", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
    	nino("ninos", "outletO", "kidsO", "nino", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
    	kids("kids", "outletX", "kids", "kids", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet)), 
    	violeta("violeta", "outletV", "violeta", "violeta", Arrays.asList(Channel.desktop, Channel.mobile, Channel.tablet));
    	
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
    
    
    public static enum TypeContentDesk {banners, articulos, vacio}
    public static enum TypeContentMobil {bloquesnuevo, bloquesrebaj, sublineas, menus2, articulos}
    
    String id;
    String outlet;
    String shop;
    String menus;
    String menusart;
    String contentDesk;
    String contentMovil;
    String carrusels;
    String panoramicas;
    String extended = "n";
	
    public String getId() {
        return this.id;
    }
	
    @XmlAttribute(name="id")
    public void setId(String id) {
        this.id = id;
    }
	
    public String getShop() {
        return this.shop;
    }
	
    @XmlAttribute(name="shop")
    public void setShop(String shop) {
        this.shop = shop;
    }
	
    public String getOutlet() {
        return this.outlet;
    }
        
    @XmlAttribute(name="outlet")
    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }	
	
    public String getContentDesk() {
        return this.contentDesk;
    }
	
    @XmlAttribute(name="menus")
    public void setMenus(String menus) {
        this.menus = menus;
    }
        
    public String getMenus() {
        return this.menus;
    }
        
    @XmlAttribute(name="menusart")
    public void setMenusart(String menusart) {
        this.menusart = menusart;
    }
        
    public String getMenusart() {
        return this.menusart;
    }        
	
    @XmlAttribute(name="contentDesk")
    public void setContentDesk(String contentDesk) {
        this.contentDesk = contentDesk;
    }
	
    public String getContentMovil() {
        return this.contentMovil;
    }
	        
    @XmlAttribute(name="contentMovil")
    public void setContentMovil(String contentMovil) {
        this.contentMovil = contentMovil;
    }
        
    public String getCarrusels() {
        return this.carrusels;
    }
        
    public String[] getListCarrusels() {
        return (this.carrusels.split(","));
    }
                
    @XmlAttribute(name="carrusels")
    public void setCarrusels(String carrusels) {
        this.carrusels = carrusels;
    }        
    
    public String getPanoramicas() {
        return this.panoramicas;
    }
    
    public String getExtended() {
        return this.extended;
    }
        
    @XmlAttribute(name="extended")
    public void setExtended(String extended) {
        this.extended = extended;
    }    
                
    @XmlAttribute(name="panoramicas")
    public void setPanoramicas(String panoramicas) {
        this.panoramicas = panoramicas;
    }        
        
    public LineaType getType() {
        return (LineaType.valueOf(getId()));
    }
    
    public boolean isActiveIn(Channel channel) {
    	return getType().isActiveIn(channel);
    }
    
    public boolean isActiveIn(AppEcom app) {
    	switch (app) {
    	case outlet:
    		return "s".compareTo(getOutlet())==0;
    	case shop:
    	case votf:
    	default:
    		return "s".compareTo(getShop())==0;
    	}
    }
        
    @XmlElement(name="sublinea") 
    List<Sublinea> listSublineas = new LinkedList<>();
        
    private List<Sublinea> getListSublineas() {
        return this.listSublineas;
    }
    public List<Sublinea> getListSublineas(AppEcom app) {
    	List<Sublinea> sublineasApp = new ArrayList<>();
    	for (Sublinea sublinea : listSublineas) {
    		if (sublinea.isActiveIn(app)) {
    			sublineasApp.add(sublinea);
    		}
    	}
    	return sublineasApp;
    }
    
    public Sublinea getSublineaNinos(SublineaNinosType sublineaType) {
        Sublinea sublineaRet = null;
        boolean encontrada = false;
        Iterator<Sublinea> it = getListSublineas().iterator();
        while (!encontrada && it.hasNext()) {
            Sublinea sublineaTmp = it.next();
            if (sublineaTmp.getTypeSublinea() == sublineaType) {
                sublineaRet = sublineaTmp;
                encontrada = true;
            }
        }
        
        return sublineaRet;            
    }
    
    public boolean existsSublineas() {
        return (getListSublineas().size() > 0);
    }
    
    public TypeContentDesk getContentDeskType() {
        return TypeContentDesk.valueOf(getContentDesk());
    }
    
    public TypeContentMobil getContentMobilType() {
        return TypeContentMobil.valueOf(getContentMovil());
    }   
        
    @Override
    public String toString() {
        return "Linea [id="+ this.id + ", existe=" + this.shop + ", contentDesk=" + this.contentDesk + ", contentMovil=" + this.contentMovil + ", carrusels=" + this.carrusels + ", panoramicas=" + this.panoramicas +
                ", toString()=" + super.toString() + "]";
    }
}