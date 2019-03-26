package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsPageGaleria {
	
    public static String getCodColorFromSrcImg(String srcImagenColor) {
        String codColor = "";
        Pattern pattern = Pattern.compile("_(.*?)-(.*?)_(.*?).jpg");
        Matcher matcher = pattern.matcher(srcImagenColor);
        if (matcher.find()) {
            codColor = matcher.group(1);
        }
        else {
            pattern = Pattern.compile("_(.*?)(_.*?.jpg|.jpg)");
            matcher = pattern.matcher(srcImagenColor);
            if (matcher.find()) {
                codColor = matcher.group(1);
            }
        }
        
        return codColor;
    }
    
    public static String getReferenciaAndCodColorFromURLficha(String urlFicha) {
    	return (
    		getReferenciaFromURLficha(urlFicha) + 
    		getColorFromURLficha(urlFicha)
    	);
    }
    
    public static String getReferenciaFromURLficha(String urlFicha) {
        String referencia = "";
        Pattern pattern = Pattern.compile("_(.*?).html");
        Matcher matcher = pattern.matcher(urlFicha);
        if (matcher.find())
            referencia = matcher.group(1);
        
        return referencia;
    }

    public static String getColorFromURLficha(String urlFicha) {
        String codColor = "";
        Pattern pattern = Pattern.compile("\\?c=([a-zA-Z0-9]{2})");
        Matcher matcher = pattern.matcher(urlFicha);
        if (matcher.find())
            codColor = matcher.group(1);
        
        return codColor;
    }
}
