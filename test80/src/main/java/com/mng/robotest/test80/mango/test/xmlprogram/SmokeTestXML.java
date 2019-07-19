package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.TestMaker;
import com.mng.robotest.test80.arq.jdbc.dao.CorreosGroupDAO;
import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.filter.DataFilterTCases;
import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.TestMakerSuiteXML;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;
import com.mng.robotest.test80.mango.test.xmlprogram.CommonMangoData;

public class SmokeTestXML extends TestMakerSuiteXML {

    public SmokeTestXML(ParamsBean params) {
    	super(params.getDataFilter());
    	Channel channel = params.getChannel();
    	setClassesWithTests(getClasses(channel));
    	setDependencyGroups(getDependencyGroups());
    	setParameters(CommonMangoData.getParametersSuiteShop(params));
    }

    private Map<String,String> getDependencyGroups() {
    	Map<String,String> dependencyGroups = new HashMap<>();
    	return dependencyGroups;
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
}
