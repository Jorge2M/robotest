package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.TestMakerSuite;
import com.mng.robotest.test80.mango.test.xmlprogram.CommonMangoData;

public class SmokeTestXML extends TestMakerSuite {

    public SmokeTestXML(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setClassesWithTests(getClasses(params.getChannel()));
    	setDependencyGroups(getDependencyGroups());
    	setParameters(CommonMangoData.getParametersSuiteShop(params));
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
    
    private void setParallelism() {
    	if ()
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }

    private static List<String> getClasses(Channel channel) {
        List<String> listClasses = new ArrayList<>();
        if (channel==Channel.desktop) {
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.Otras");
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.SEO");
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.IniciarSesion");
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.Bolsa");
        }
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.FichaProducto");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Ayuda");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Buscador");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Footer");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Registro");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.PaisIdioma");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.GaleriaProducto");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Compra");
        listClasses.add("com.mng.robotest.test80.mango.test.factoryes.ListPagosEspana");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.MiCuenta");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Favoritos");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Reembolsos");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Loyalty");
        return listClasses;
    }
    
    private Map<String,String> getDependencyGroups() {
    	Map<String,String> dependencyGroups = new HashMap<>();
    	return dependencyGroups;
    }
}
