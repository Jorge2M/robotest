package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("javadoc")
public class DataFoto {
    private String srcRegexGeneral = ".*?rcs/pics/static/T(.\\d?)/fotos(|/pasarela|/outfit)/S(.\\d?)/(|A\\d/)(.\\d+)_(\\w{2})_*(.*|.?).(jpg|png).*";
    private String srcRegexPropios = ".*?rcs/pics/static/T(.\\d?)/colv3/(.\\d+)_(.\\d+)_*(.*|.?).(jpg|png).*";
    private String srcRegexVideos = ".*?rcs/pics/static/T(.\\d?)/videos/(.*)/(.\\d+)_(.\\d+).(mp4|avi).*";
    private String srcRegexBase = ".*?/col_nrf_v3/(.\\d+)_(.\\d+).(jpg|png).*";
    
    public String srcFoto = "";
    public String temporada = ""; 
    public String size = "";
    public String referencia = ""; 
    public String color = "";
    public TipoImagenProducto typeImage;  
    
    public DataFoto(String srcFoto) {
        this.srcFoto = srcFoto;
        if (getDataFotoBase())
            return;
        if (getDataFotoPropios())
            return;
        if (getDataFotoVideos())
            return;        
        if (getDataFotoGeneral())
            return;
    }
    
    private boolean getDataFotoGeneral() {
        Pattern pattern = Pattern.compile(this.srcRegexGeneral);
        Matcher matcher = pattern.matcher(this.srcFoto);
        if (!matcher.matches())
            return false;
        
        this.temporada = matcher.group(1);  
        this.size = matcher.group(3);
        this.referencia = matcher.group(5);
        this.color = matcher.group(6);
        if ("".compareTo(matcher.group(7))==0)
            this.typeImage = getTipoForBlankSufix();
        else
            this.typeImage = TipoImagenProducto.getTipoImagenProductoFromSufijo(matcher.group(7));
        
        return true;
    }    
    
    private boolean getDataFotoPropios() {
        Pattern pattern = Pattern.compile(this.srcRegexPropios);
        Matcher matcher = pattern.matcher(this.srcFoto);
        if (!matcher.matches())
            return false;
        
        this.temporada = matcher.group(1);  
        this.referencia = matcher.group(2);
        this.color = matcher.group(3);
        if ("".compareTo(matcher.group(4))==0)
            this.typeImage = getTipoForBlankSufix();
        else
            this.typeImage = TipoImagenProducto.getTipoImagenProductoFromSufijo(matcher.group(4));
        
        return true;        
    }
    
    private boolean getDataFotoVideos() {
        Pattern pattern = Pattern.compile(this.srcRegexVideos);
        Matcher matcher = pattern.matcher(this.srcFoto);
        if (!matcher.matches())
            return false;
        
        this.temporada = matcher.group(1);  
        this.referencia = matcher.group(3);
        this.color = matcher.group(4);
        this.typeImage = TipoImagenProducto.VIDEO;
        return true;        
    }
    
    private boolean getDataFotoBase() {
        Pattern pattern = Pattern.compile(this.srcRegexBase);
        Matcher matcher = pattern.matcher(this.srcFoto);
        if (!matcher.matches())
            return false;
        
        this.referencia = matcher.group(1);
        this.color = matcher.group(2);
        this.typeImage = TipoImagenProducto.BASE;
        return true;        
    }
    
    private TipoImagenProducto getTipoForBlankSufix() {
        if (this.srcFoto.contains("/pasarela/"))
            return TipoImagenProducto.PASARELA;
        if (this.srcFoto.contains("/videos/"))
            return TipoImagenProducto.VIDEO;
        if (this.srcFoto.contains("col_nrf_v3"))
            return TipoImagenProducto.BASE;
            
        return TipoImagenProducto.DETALLES;        
    }
}
