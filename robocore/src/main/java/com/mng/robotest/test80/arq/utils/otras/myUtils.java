package com.mng.robotest.test80.arq.utils.otras;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class myUtils {
	
    /**
     * Sustituye los carácteres no permitidos a la hora de crear un directorio o un archivo en el PC del usuario.
     */
    public static String adaptToGrab (String chainToAdapt) {
        String chainToReturn = chainToAdapt;
        String charsNotValid = "\\/:*?\"<>|";
        for (int i=0; i<charsNotValid.length(); i++)
            chainToReturn = chainToReturn.replace(charsNotValid.charAt(i), '-');
        
        return chainToReturn;
    }
	
    /**
     * Transforma una fecha (en formato ) a formato local (simplemente adapta el nombre del mes)
     */
    public static String dateFromLocal (String dateIn) {
        String mesLocal = "";
        String mesOrigen = dateIn.substring(3,6);
        switch (mesOrigen) {
        case "ene": 
            mesLocal="jan";
            break;
        case "feb": 
            mesLocal="feb";
            break;
        case "mar": 
            mesLocal="mar";
            break;
        case "abr":  
            mesLocal="apr";
            break;
        case "may":  
            mesLocal="may";
            break;
        case "jun": 
            mesLocal="jun";
            break;
        case "jul": 
            mesLocal="jul";
            break;
        case "ago": 
            mesLocal="aug";
            break;
        case "sep":
            mesLocal="sep";
            break;
        case "oct":
            mesLocal="oct";
            break;
        case "nov": 
            mesLocal="nov";
            break;
        case "dic": 
            mesLocal="dec";
            break;
        default:
            mesLocal = mesOrigen;
        }
            
        String dateOut=dateIn.substring(0,3)+mesLocal+dateIn.substring(6);
        return dateOut;
    }
	
    public static String getNamePC() {
        String hostname = "";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException ex) {
            hostname = "Unknown";
        }
		
        return hostname;
    }
}
