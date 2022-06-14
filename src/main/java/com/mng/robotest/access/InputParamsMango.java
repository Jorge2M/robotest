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

	public static final String CountrysNameParam = "countrys";
	public static final String LineasNameParam = "lineas";
	public static final String PaymentsNameParam = "payments";
	public static final String CatalogsNameParam = "catalogs";
	public static final String UrlMantoParam = "urlmanto";

	@FormParam(CountrysNameParam)
	String listCountrysCommaSeparated;
	
	@FormParam(LineasNameParam)	
	String listLinesCommaSeparated;
	
	@FormParam(PaymentsNameParam)
	String listPaymentsCommaSeparated;
	
	@FormParam(CatalogsNameParam)
	String listCatalogsCommaSeparated;
	
	@FormParam(UrlMantoParam)
	String urlManto;
	
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
		options.add(OptionTMaker.builder(InputParamsMango.CountrysNameParam)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.desc("List of 3-digit codes of countrys comma separated or \'X\' for indicate all countrys")
			.pattern("\\d{3}|X")
			.build());

		options.add(OptionTMaker.builder(InputParamsMango.LineasNameParam)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.desc("List of lines comma separated (p.e. she,he,...)")
			.build());		

		options.add(OptionTMaker.builder(InputParamsMango.PaymentsNameParam)
			.required(false)
			.hasArgs()
			.valueSeparator(',') 
			.desc("List of payments comma separated (p.e. VISA,TARJETA MANGO,...)")
			.build());
		
		options.add(OptionTMaker.builder(InputParamsMango.CatalogsNameParam)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.pattern("\\d{3}_[A-Z0-9]{2,3}_[a-z]+_[a-zA-Z0-9\\-]+_[a-z]+_\\d{1,3}([:\\d{1,3}]*)|X")
			.desc("List of catalogs with format country_idiom_linea_section_galery_family (p.e. 001_ES_home_bano_albornoces_726:14")
			.build());

		String patternUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		options.add(OptionTMaker.builder(InputParamsMango.UrlMantoParam)
			.required(false)
			.hasArgs()
			.pattern(patternUrl)
			.desc("URL of the Backoffice of mangoshop (Manto application)")
			.build());	

		return options;
	}

	@Override
	public void setSpecificDataFromCommandLine(CommandLine cmdLineData) {
		setListaPaises(cmdLineData.getOptionValues(CountrysNameParam));
		setListaLineas(cmdLineData.getOptionValues(LineasNameParam));
		setListaPayments(cmdLineData.getOptionValues(PaymentsNameParam));
		setListaCatalogs(cmdLineData.getOptionValues(CatalogsNameParam));
		setUrlManto(cmdLineData.getOptionValue(UrlMantoParam));
	}

	private enum ParamMango {
		Countrys(CountrysNameParam),
		Lineas(LineasNameParam),
		Payments(PaymentsNameParam),
		Catalogs(CatalogsNameParam),
		UrlManto(UrlMantoParam);
		
		public final String nameParam;
		private ParamMango(String nameParam) {
			this.nameParam = nameParam;
		}
	}
	
	@Override
	public Map<String,String> getSpecificParamsValues() {
		Map<String,String> pairsParamValue = new HashMap<>(); 
		for (ParamMango paramTM : ParamMango.values()) {
			String valueParam = getValueParam(paramTM);
			if (valueParam!=null && "".compareTo(valueParam)!=0) {
				pairsParamValue.put(paramTM.nameParam, valueParam);
			}
		}
		return pairsParamValue;
	}
	
	private String getValueParam(ParamMango paramId) {
		switch (paramId) {
		case Countrys:
			return listCountrysCommaSeparated;
		case Lineas:
			return listLinesCommaSeparated;
		case Payments:
			return listPaymentsCommaSeparated;
		case Catalogs:
			return listCatalogsCommaSeparated;
		case UrlManto:
			return getUrlManto();
		}
		return "";
	}

	@Override
	public String getMoreInfo() {
		StringBuilder moreInfo = new StringBuilder();
		List<String> listCountrys = getListaPaises();
		if (!listCountrys.isEmpty()) {
			moreInfo.append("List Countrys : " + "<br>");
		}
		List<String> listLineas = getListaLineas();
		if (!listLineas.isEmpty()) {
			moreInfo.append("List Lines: " + "<br>");
		}
		List<String> listPayments = getListaPayments();
		if (!listPayments.isEmpty()) {
			moreInfo.append("List Payments: " + listPayments);
		}
		List<String> listCatalogs = getListaCatalogs();
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
		if (listCountrysCommaSeparated!=null) {
			String[] listPaises = listCountrysCommaSeparated.split("\\s*,\\s*");
			return Arrays.asList(listPaises);
		}
		return Arrays.asList();
	}
	public List<String> getListaLineas() {
		if (listLinesCommaSeparated!=null) {
			String[] listPaises = listLinesCommaSeparated.split("\\s*,\\s*");
			return Arrays.asList(listPaises);
		}
		return Arrays.asList();
	}
	public List<String> getListaPayments() {
		if (listPaymentsCommaSeparated!=null) {
			String[] listPaises = listPaymentsCommaSeparated.split("\\s*,\\s*");
			return Arrays.asList(listPaises);
		}
		return Arrays.asList();
	}
	public List<String> getListaCatalogs() {
		if (listCatalogsCommaSeparated!=null) {
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
