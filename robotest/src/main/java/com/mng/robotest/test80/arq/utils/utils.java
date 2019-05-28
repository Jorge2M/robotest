package com.mng.robotest.test80.arq.utils;

import java.util.regex.Pattern;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.otras.myUtils;

public class utils {

    public static State getEstadoMethod(final String result_script, final String result_tng) {
        int resultSCRint = Integer.parseInt(result_script);
        int resultTNGint = Integer.parseInt(result_tng);
        return (getEstadoMethod(resultSCRint, resultTNGint));
    }
    
    public static State getEstadoMethod(int resultSCRint, int resultTNGint) {
        if (resultTNGint==2) {
            return State.Nok;
        }
        if (resultTNGint==3) { 
            return State.Skip;
        }
        return State.getState(resultSCRint);
    }
    
    /**
     * Devuelve el literal correspondiente al resultado de un método
     */
    public static String getLitEstadoMethod(final String result_script, final String result_tng) {
        return getLitEstado(getEstadoMethod(result_script, result_tng).getIdNumerid());
    }
    
    public static String getLitEstado(final int result_script) {
        return (State.getState(result_script).getLevel().toString());
    }

    public static String getLitEstado(final String result_script) {
        return (State.getState(Integer.valueOf(result_script)).getLevel().toString());
    }

    /**
     * Obtiene la DNS de un fichero ubicado dentro del contexto de la aplicación de tests
     * @param serverDNS: del tipo "http://robottest.mangodev.net + :port si fuera preciso)  
     */
    public static String obtainDNSFromFile(final String filePath, String applicationDNS) {
        String pathReport = "";
        if (applicationDNS!=null && "".compareTo(applicationDNS)!=0) {
            pathReport = filePath.substring(filePath.indexOf(Constantes.directoryOutputTests));
            pathReport = applicationDNS + "\\" + pathReport;
        } else {
            Pattern patternDrive = Pattern.compile("^[a-zA-Z]:");
            pathReport = patternDrive.matcher(filePath).replaceFirst("\\\\\\\\" + myUtils.getNamePC());
        }

        return pathReport;
    }

    public static TypeAccessFmwk getTypeAccessFmwk(ITestContext context) {
        String typeAccessStr = context.getCurrentXmlTest().getParameter(Constantes.paramTypeAccessFmwk);
        return TypeAccessFmwk.valueOf(typeAccessStr);
    }
}