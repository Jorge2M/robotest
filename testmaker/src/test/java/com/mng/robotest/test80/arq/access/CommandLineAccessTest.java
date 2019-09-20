package com.mng.robotest.test80.arq.access;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.apache.commons.cli.ParseException;

public class CommandLineAccessTest {
	
	private enum Suites {SmokeTest, PagosPaises}
	private enum AppEcom {desktop, movil_web}

	@Test
	public void testOptionMandatory() throws ParseException {
		//Given
    	List<OptionTMaker> options = new ArrayList<>();
    	String nameParam = "mandatoryparam";
    	OptionTMaker optionMandatory = OptionTMaker.builder(nameParam)
            .required(true)
            .hasArgs()
            .desc("Mandatory param")
            .build();
    	options.add(optionMandatory);
    	
    	List<String> listaArgs = getBaseArgs();
    	listaArgs.add("-" + nameParam); listaArgs.add("valueParam");
    	String args[] = getArray(listaArgs);

    	//When
    	CommandLineAccess cmdLineAccess = new CommandLineAccess(args, options, Suites.class, AppEcom.class);
    	StringBuffer storedErrors = new StringBuffer();
    	boolean check = cmdLineAccess.checkSpecificClientValues(storedErrors);	
    	
    	//Then
    	assertTrue(check);
    	assertTrue(storedErrors.length()==0);
	}
	
	private List<String> getBaseArgs() {
		List<String> listArgs = new ArrayList<>();
		listArgs.add("-suite"); listArgs.add("SmokeTest");
		listArgs.add("-browser"); listArgs.add("chrome");
		listArgs.add("-channel"); listArgs.add("desktop");
		listArgs.add("-application"); listArgs.add("shop");
		listArgs.add("-version"); listArgs.add("V1");
		listArgs.add("-url"); listArgs.add("https://shop.pre.mango.com/preHome.faces");
		listArgs.add("-tcases"); listArgs.add("MIC001,FAV001");
		return listArgs;
	}
	
	private String[] getArray(List<String> list) {
		return (list.toArray(new String[list.size()]));
	}
}
