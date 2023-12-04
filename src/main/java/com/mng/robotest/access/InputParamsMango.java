package com.mng.robotest.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;

import org.apache.commons.cli.CommandLine;

import com.github.jorge2m.testmaker.boundary.access.OptionTMaker;
import com.github.jorge2m.testmaker.domain.InputParamsTM;

public class InputParamsMango extends InputParamsTM {

	private static final String COUNTRYS_NAME_PARAM = "countrys";
	private static final String LINEAS_NAME_PARAM = "lineas";
	private static final String PAYMENTS_NAME_PARAM = "payments";
	private static final String CATALOGS_NAME_PARAM = "catalogs";
	private static final String URL_MANTO_PARAM = "urlmanto";
	private static final String GENERIC_CHECKS_PARAM = "genericchecks";

	@FormParam(COUNTRYS_NAME_PARAM)
	String listCountrysCommaSeparated;
	
	@FormParam(LINEAS_NAME_PARAM)	
	String listLinesCommaSeparated;
	
	@FormParam(PAYMENTS_NAME_PARAM)
	String listPaymentsCommaSeparated;
	
	@FormParam(CATALOGS_NAME_PARAM)
	String listCatalogsCommaSeparated;
	
	@FormParam(URL_MANTO_PARAM)
	String urlManto;
	
	@FormParam(GENERIC_CHECKS_PARAM)
	String genericChecks;
	
	private static String lineSeparator = System.getProperty("line.separator");

	public InputParamsMango() {
		super();
	}
	public InputParamsMango(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		super(suiteEnum, appEnum);
	}
	public static InputParamsMango getNew(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		return new InputParamsMango(suiteEnum, appEnum);
	}

	@Override
	public List<OptionTMaker> getSpecificParameters() {
		List<OptionTMaker> options = new ArrayList<>();
		options.add(OptionTMaker.builder(InputParamsMango.COUNTRYS_NAME_PARAM)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.desc("List of 3-digit codes of countrys comma separated or \'X\' for indicate all countrys")
			.pattern("\\d{3}|X")
			.build());

		options.add(OptionTMaker.builder(InputParamsMango.LINEAS_NAME_PARAM)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.desc("List of lines comma separated (p.e. she,he,...)")
			.build());		

		options.add(OptionTMaker.builder(InputParamsMango.PAYMENTS_NAME_PARAM)
			.required(false)
			.hasArgs()
			.valueSeparator(',') 
			.desc("List of payments comma separated (p.e. VISA,TARJETA MANGO,...)")
			.build());
		
		options.add(OptionTMaker.builder(InputParamsMango.CATALOGS_NAME_PARAM)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.pattern("\\d{3}_[A-Z0-9]{2,3}_[a-z]+_[a-zA-Z0-9\\-]+_[a-z]+_\\d{1,3}([:\\d{1,3}]*)|X")
			.desc("List of catalogs with format country_idiom_linea_section_galery_family (p.e. 001_ES_home_bano_albornoces_726:14")
			.build());

		String patternUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		options.add(OptionTMaker.builder(InputParamsMango.URL_MANTO_PARAM)
			.required(false)
			.hasArgs()
			.pattern(patternUrl)
			.desc("URL of the Backoffice of mangoshop (Manto application)")
			.build());	

		options.add(OptionTMaker.builder(InputParamsMango.GENERIC_CHECKS_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Execute generic checks (default false)")
			.build());		

		return options;
	}

	@Override
	public void setSpecificDataFromCommandLine(CommandLine cmdLineData) {
		setListaPaises(cmdLineData.getOptionValues(COUNTRYS_NAME_PARAM));
		setListaLineas(cmdLineData.getOptionValues(LINEAS_NAME_PARAM));
		setListaPayments(cmdLineData.getOptionValues(PAYMENTS_NAME_PARAM));
		setListaCatalogs(cmdLineData.getOptionValues(CATALOGS_NAME_PARAM));
		setUrlManto(cmdLineData.getOptionValue(URL_MANTO_PARAM));
		setGenericChecks(cmdLineData.getOptionValue(GENERIC_CHECKS_PARAM));
	}

	private enum ParamMango {
		COUNTRYS(COUNTRYS_NAME_PARAM),
		LINEAS(LINEAS_NAME_PARAM),
		PAYMENTS(PAYMENTS_NAME_PARAM),
		CATALOGS(CATALOGS_NAME_PARAM),
		URL_MANTO(URL_MANTO_PARAM),
		GENERIC_CHECKS(GENERIC_CHECKS_PARAM);
		
		public final String nameParam;
		private ParamMango(String nameParam) {
			this.nameParam = nameParam;
		}
	}
	
	@Override
	public Map<String,String> getSpecificParamsValues() {
		Map<String,String> pairsParamValue = new HashMap<>(); 
		for (var paramTM : ParamMango.values()) {
			String valueParam = getValueParam(paramTM);
			if (valueParam!=null && "".compareTo(valueParam)!=0) {
				pairsParamValue.put(paramTM.nameParam, valueParam);
			}
		}
		return pairsParamValue;
	}
	
	private String getValueParam(ParamMango paramId) {
		switch (paramId) {
		case COUNTRYS:
			return listCountrysCommaSeparated;
		case LINEAS:
			return listLinesCommaSeparated;
		case PAYMENTS:
			return listPaymentsCommaSeparated;
		case CATALOGS:
			return listCatalogsCommaSeparated;
		case GENERIC_CHECKS:
			return genericChecks;
		case URL_MANTO:
			return getUrlManto();
		}
		return "";
	}

	@Override
	public String getMoreInfo() {
		var moreInfo = new StringBuilder();
		var listCountrys = getListaPaises();
		if (!listCountrys.isEmpty()) {
			moreInfo.append("List Countrys : " + "<br>");
		}
		var listLineas = getListaLineas();
		if (!listLineas.isEmpty()) {
			moreInfo.append("List Lines: " + "<br>");
		}
		var listPayments = getListaPayments();
		if (!listPayments.isEmpty()) {
			moreInfo.append("List Payments: " + listPayments);
		}
		var listCatalogs = getListaCatalogs();
		if (!listCatalogs.isEmpty()) {
			moreInfo.append("List Catalogs: " + listCatalogs);
		}

		return moreInfo.toString();
	}
	
	public String getListaPaisesCommaSeparated() {
		return listCountrysCommaSeparated;
	}
	public void setListaPaisesCommaSeparated(String listCountrysCommaSeparated) {
		this.listCountrysCommaSeparated = listCountrysCommaSeparated;
	}
	public String getListaLineasCommaSeparated() {
		return listLinesCommaSeparated;
	}
	public void setListaLineasCommaSeparated(String listLinesCommaSeparated) {
		this.listLinesCommaSeparated = listLinesCommaSeparated;
	}
	public String getListaPaymentsCommaSeparated() {
		return listPaymentsCommaSeparated;
	}
	public void setListPaymentsCommaSeparated(String listPaymentsCommaSeparated) {
		this.listPaymentsCommaSeparated = listPaymentsCommaSeparated;
	}
	public String getListaCatalogsCommaSeparated() {
		return listCatalogsCommaSeparated;
	}
	public void setListCatalogsCommaSeparated(String listCatalogsCommaSeparated) {
		this.listCatalogsCommaSeparated = listCatalogsCommaSeparated;
	}

	public List<String> getListaPaises() {
		if (listCountrysCommaSeparated!=null && "".compareTo(listCountrysCommaSeparated)!=0) {
			String[] listPaises = listCountrysCommaSeparated.split("\\s*,\\s*");
			return Arrays.asList(listPaises);
		}
		return Arrays.asList();
	}
	public List<String> getListaLineas() {
		if (listLinesCommaSeparated!=null && "".compareTo(listLinesCommaSeparated)!=0) {
			String[] listPaises = listLinesCommaSeparated.split("\\s*,\\s*");
			return Arrays.asList(listPaises);
		}
		return Arrays.asList();
	}
	public List<String> getListaPayments() {
		if (listPaymentsCommaSeparated!=null && "".compareTo(listPaymentsCommaSeparated)!=0) {
			String[] listPaises = listPaymentsCommaSeparated.split("\\s*,\\s*");
			return Arrays.asList(listPaises);
		}
		return Arrays.asList();
	}
	public List<String> getListaCatalogs() {
		if (listCatalogsCommaSeparated!=null && "".compareTo(listCatalogsCommaSeparated)!=0) {
			String[] listCatalogs = listCatalogsCommaSeparated.split("\\s*,\\s*");
			return Arrays.asList(listCatalogs);
		}
		return Arrays.asList();
	}

	public void setListaLineas(String[] listaLineas) {
		if (listaLineas!=null) {
			this.listLinesCommaSeparated = String.join(",", listaLineas);
		} else {
			this.listLinesCommaSeparated = "";
		}
	}
	public void setListaPayments(String[] listaPayments) {
		if (listaPayments!=null) {
			this.listPaymentsCommaSeparated = String.join(",", listaPayments);
		} else {
			this.listPaymentsCommaSeparated = "";
		}
	}
	public void setListaPaises(String[] listaPaises) {
		if (listaPaises!=null) {
			this.listCountrysCommaSeparated = String.join(",", listaPaises);
		} else {
			this.listCountrysCommaSeparated = "";
		}
	}
	public void setListaCatalogs(String[] listaCatalogs) {
		if (listaCatalogs!=null) {
			this.listCatalogsCommaSeparated = String.join(",", listaCatalogs);
		} else {
			this.listCatalogsCommaSeparated = "";
		}
	}

	public boolean getGenericChecks() {
		if (genericChecks!=null) {
	    	return ("true".compareTo(genericChecks)==0);
		}
		return false;
	}
	public void setGenericChecks(String genericChecks) {
		this.genericChecks = genericChecks;
	}	
	
	public String getUrlManto() {
		return this.urlManto;
	}

	public void setUrlManto(String urlManto) {
		this.urlManto = urlManto;
	}

	@Override
	public String toString() {
		return 
			super.toString() + lineSeparator +
			getMoreInfo();
	}
}
