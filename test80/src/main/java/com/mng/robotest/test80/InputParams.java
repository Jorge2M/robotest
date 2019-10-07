package com.mng.robotest.test80;

import java.util.Arrays;

import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.utils.otras.TypeAccessFmwk;

public class InputParams extends InputParamsTestMaker {

    private String[] listaPaises = {};
    private String[] listaLineas = {};
    private String[] listaPayments = {};   
    private String urlManto = null;
    private TypeAccessFmwk typeAccess = TypeAccessFmwk.CommandLine;
    private CallBack callBack = null;
    
    private static String lineSeparator = System.getProperty("line.separator");
    
    @Override
    public String getMoreInfo() {
    	StringBuilder moreInfo = new StringBuilder();
    	if (listaPaises!=null && listaPaises.length > 0) {
    		moreInfo.append("List Countrys : " + Arrays.asList(listaPaises) + lineSeparator);
    	}
    	if (listaLineas!=null && listaLineas.length > 0) {
    		moreInfo.append("List Lines: " + Arrays.asList(listaLineas) + lineSeparator);
    	}
    	if (listaPayments!=null && listaPayments.length > 0) {
    		moreInfo.append("List Payments: " + Arrays.asList(listaPayments));
    	}
    	
    	return moreInfo.toString();
    }
    
    public static InputParams getNew() {
    	return new InputParams();
    }

    public TypeAccessFmwk getTypeAccess() {
        return this.typeAccess;
    }
    
    void setTypeAccess(TypeAccessFmwk typeAccess) {
        if (typeAccess!=null) {
            this.typeAccess = typeAccess;
        }
    }
    
    void setTypeAccessFromStr(String typeAccess) {
        if (typeAccess!=null) {
            this.typeAccess = TypeAccessFmwk.valueOf(typeAccess);
        }
    }
    
    void setTypeAccessIfNotSetted(TypeAccessFmwk typeAccess) {
        if (this.typeAccess==null) {
            setTypeAccess(typeAccess);
        }
    }    

    public String[] getListaPaises() {
        return this.listaPaises;
    }
    
    public String getListaPaisesStr() {
        if (getListaPaises()!=null && getListaPaises().length>0) {
            return String.join(",", getListaPaises());
        }
        return "";
    }    
    
    public String[] getListaLineas() {
        return this.listaLineas;
    }
    
    public String getListaLineasStr() {
        if (getListaLineas()!=null && getListaLineas().length>0) {
            return String.join(",", getListaLineas());
        }
        return "";
    }
    
    public String[] getListaPayments() {
        return this.listaPayments;
    }
    
    public String getListaPaymentsStr() {
        if (getListaPayments()!=null && getListaPayments().length>0) {
            return String.join(",", getListaPayments());
        }
        return "";
    }    
    
    public void setListaLineas(String[] ListaLineas) {
        this.listaLineas = ListaLineas;
    }
    
    public void setListaPayments(String[] ListaPayments) {
        this.listaPayments = ListaPayments;
    }    
    
    public void setListaPaises(String[] ListaPaises) {
        this.listaPaises = ListaPaises;
    }
    
    public void setListaPaises(String ListaPaises) {
        if (ListaPaises!=null) {
            this.listaPaises = ListaPaises.split("\\s*,\\s*");
        }
    }
    
    public String getUrlManto() {
        return this.urlManto;
    }
    
    public void setUrlManto(String urlManto) {
        this.urlManto = urlManto;
    }
    
    public CallBack getCallBack() {
        return this.callBack;
    }
    
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
    
    @Override
    public String toString() {
        return 
        	super.toString() + lineSeparator +
        	getMoreInfo();
    }        
}
