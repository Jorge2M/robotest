package com.mng.robotest.test80.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;

import org.apache.commons.cli.CommandLine;

import com.mng.robotest.test80.access.CmdRunTests.TypeCallBackMethod;
import com.mng.robotest.test80.access.CmdRunTests.TypeCallbackSchema;
import com.mng.testmaker.boundary.access.OptionTMaker;
import com.mng.testmaker.domain.InputParamsTM;

public class InputParamsMango extends InputParamsTM {

	public static final String CountrysNameParam = "countrys";
	public static final String LineasNameParam = "lineas";
	public static final String PaymentsNameParam = "payments";
	public static final String UrlMantoParam = "urlmanto";
	public static final String CallBackResourceParam = "callbackresource";
	public static final String CallBackMethodParam = "callbackmethod";
	public static final String CallBackSchemaParam = "callbackschema";
	public static final String CallBackParamsParam = "callbackparams";
	public static final String CallBackUserParam = "callbackuser";
	public static final String CallBackPasswordParam = "callbackpassword";

	@FormParam(CountrysNameParam)
	String listCountrysCommaSeparated;
	
	@FormParam(LineasNameParam)	
	String listLinesCommaSeparated;
	
	@FormParam(PaymentsNameParam)
	String listPaymentsCommaSeparated;
	
	@FormParam(UrlMantoParam)
	String urlManto;
	
	@FormParam(CallBackResourceParam)
	String callBackResource;
	
	@FormParam(CallBackMethodParam)
	String callBackMethod;
	
	@FormParam(CallBackSchemaParam)
	String callBackSchema;
	
	@FormParam(CallBackParamsParam)
	String callBackParams;
	
	@FormParam(CallBackUserParam)
	String callBackUser;
	
	@FormParam(CallBackPasswordParam)
	String callBackPassword;
	
	private final CallBack callBack = new CallBack();

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

		String patternUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		options.add(OptionTMaker.builder(InputParamsMango.UrlMantoParam)
			.required(false)
			.hasArgs()
			.pattern(patternUrl)
			.desc("URL of the Backoffice of mangoshop (Manto application)")
			.build());    
 
		options.add(OptionTMaker.builder(InputParamsMango.CallBackResourceParam)
			.required(false)
			.hasArgs()
			.desc("CallBack URL (without schema and params) to invoke in the end of the TestSuite")
			.build());

		options.add(OptionTMaker.builder(InputParamsMango.CallBackMethodParam)
			.required(false)
			.hasArgs()
			.possibleValues(TypeCallBackMethod.class)
			.desc("Method of the CallBack URL. Possible values: " + Arrays.asList(TypeCallBackMethod.values()))
			.build());        

		options.add(OptionTMaker.builder(InputParamsMango.CallBackSchemaParam)
			.required(false)
			.hasArgs()
			.possibleValues(TypeCallbackSchema.class)
			.desc("Schema of the CallBack URL. Possible values: " + Arrays.asList(TypeCallbackSchema.values()))
			.build());        

		options.add(OptionTMaker.builder(InputParamsMango.CallBackParamsParam)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.desc("Params of the CallBack URL (in format param1:value1,param2:value2...)")
			.build());        

		options.add(OptionTMaker.builder(InputParamsMango.CallBackUserParam)
			.required(false)
			.hasArgs()
			.desc("User credential needed to invoke the CallBack URL")
			.build());        

		options.add(OptionTMaker.builder(InputParamsMango.CallBackPasswordParam)
			.required(false)
			.hasArgs()
			.desc("Password credential needed to invoke the CallBack URL")
			.build());        

		return options;
	}

	@Override
	public void setSpecificDataFromCommandLine(CommandLine cmdLineData) {
		setListaPaises(cmdLineData.getOptionValues(CountrysNameParam));
		setListaLineas(cmdLineData.getOptionValues(LineasNameParam));
		setListaPayments(cmdLineData.getOptionValues(PaymentsNameParam));
		setUrlManto(cmdLineData.getOptionValue(UrlMantoParam));
		if (cmdLineData.getOptionValue(CallBackResourceParam)!=null) {
			callBackResource = cmdLineData.getOptionValue(CallBackResourceParam);
			callBackMethod = cmdLineData.getOptionValue(CallBackMethodParam);
			callBackSchema = cmdLineData.getOptionValue(CallBackUserParam);
			callBackParams = cmdLineData.getOptionValue(CallBackPasswordParam);
			callBackUser = cmdLineData.getOptionValue(CallBackSchemaParam);
			callBackPassword = cmdLineData.getOptionValue(CallBackParamsParam);
		}
	}

	private enum ParamMango {
		Countrys(CountrysNameParam),
		Lineas(LineasNameParam),
		Payments(PaymentsNameParam),
		UrlManto(UrlMantoParam),
		CallBackResource(CallBackResourceParam),
		CallBackMethod(CallBackMethodParam),
		CallBackSchema(CallBackSchemaParam),
		CallBackParams(CallBackParamsParam),
		CallBAckUser(CallBackUserParam),
		CallBackPassword(CallBackPasswordParam);
		
		public String nameParam;
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
		case UrlManto:
			return getUrlManto();
		case CallBackResource:
			return (getCallBack()==null? null : getCallBack().getCallBackResource());
		case CallBackMethod:
			return (getCallBack()==null? null : getCallBack().getCallBackMethod());
		case CallBackSchema:
			return (getCallBack()==null? null : getCallBack().getCallBackSchema());
		case CallBackParams:
			return (getCallBack()==null? null : getCallBack().getCallBackParams());
		case CallBAckUser:
			return (getCallBack()==null? null : getCallBack().getCallBackUser());
		case CallBackPassword:
			return (getCallBack()==null? null : getCallBack().getCallBackPassword());
		}
		return "";
	}

	@Override
	public String getMoreInfo() {
		StringBuilder moreInfo = new StringBuilder();
		List<String> listCountrys = getListaPaises();
		if (listCountrys.size() > 0) {
			moreInfo.append("List Countrys : " + "<br>");
		}
		List<String> listLineas = getListaLineas();
		if (listLineas.size() > 0) {
			moreInfo.append("List Lines: " + "<br>");
		}
		List<String> listPayments = getListaPayments();
		if (listPayments.size() > 0) {
			moreInfo.append("List Payments: " + listPayments);
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
	public void setCallBackResource(String callBackResource) {
		this.callBackResource = callBackResource;
	}
	public void setCallBackMethod(String callBackMethod) {
		this.callBackMethod = callBackMethod;
	}
	public void setCallBackSchema(String callBackSchema) {
		this.callBackSchema = callBackSchema;
	}
	public void setCallBackParams(String callBackParams) {
		this.callBackParams = callBackParams;
	}
	public void setCallBackUser(String callBackUser) {
		this.callBackUser = callBackUser;
	}
	public void setCallBackPassword(String callBackPassword) {
		this.callBackPassword = callBackPassword;
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

	public String getUrlManto() {
		return this.urlManto;
	}

	public void setUrlManto(String urlManto) {
		this.urlManto = urlManto;
	}

	public CallBack getCallBack() {
		setCallBack();
        return callBack;
	}
	
	private void setCallBack() {
        callBack.setCallBackResource(callBackResource);
        callBack.setCallBackMethod(callBackMethod);
        callBack.setCallBackUser(callBackSchema);
        callBack.setCallBackPassword(callBackParams);
        callBack.setCallBackSchema(callBackUser);
        callBack.setCallBackParams(callBackPassword);
	}

	@Override
	public String toString() {
		return 
			super.toString() + lineSeparator +
			getMoreInfo();
	}
}
