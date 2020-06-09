package com.mng.robotest.test80.access;

import java.net.HttpURLConnection; 
import java.net.URL;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import com.google.common.base.Splitter;
import com.github.jorge2m.testmaker.service.webdriver.utils.WebUtils;

public class CallBack {
	String reportTSuiteURL = "";
	String callBackMethod = null;
	String callBackResource = null;
	String callBackSchema = null;
	String callBackParams = null;
	String callBackUser = null;
	String callBackPassword = null;


	public String getReportTSuiteURL() {
		return this.reportTSuiteURL;
	}

	public void setReportTSuiteURL(String reportTSuiteURL) {
		this.reportTSuiteURL = reportTSuiteURL;
	}

	public String getCallBackMethod() {
		return this.callBackMethod;
	}

	public void setCallBackMethod(String callBackMethod) {
		this.callBackMethod = callBackMethod;
	}

	public String getCallBackResource() {
		return this.callBackResource;
	}

	public void setCallBackResource(String callBackResource) {
		this.callBackResource = callBackResource;
	}

	public String getCallBackSchema() {
		return this.callBackSchema;
	}

	public void setCallBackSchema(String callBackSchema) {
		this.callBackSchema = callBackSchema;
	}

	public String getCallBackParams() {
		return this.callBackParams;
	}

	public void setCallBackParams(String callBackParams) {
		this.callBackParams = callBackParams;
	}

	public String getCallBackUser() {
		return this.callBackUser;
	}

	public void setCallBackUser(String callBackUser) {
		this.callBackUser = callBackUser;
	}

	public String getCallBackPassword() {
		return this.callBackPassword;
	}

	public void setCallBackPassword(String callBackPassword) {
		this.callBackPassword = callBackPassword;
	}

	public HttpURLConnection callURL() throws Exception {
		switch (getCallBackSchema()) {
		case "https":
			return callHTTPS();
		case "http":
		default:
			return callHTTP();
		}
	}

	private HttpURLConnection callHTTP() throws Exception {
		URL httpURL = getComposedURL("http");
		HttpURLConnection connHttp = (HttpURLConnection) httpURL.openConnection();
		connHttp.setRequestMethod(getCallBackMethod());
		connHttp.setRequestProperty("Authorization", "Basic " + getEncondingForBasicAuth());
		connHttp.getResponseCode();
		return connHttp;
	}

	private HttpURLConnection callHTTPS() throws Exception {
		WebUtils.acceptAllCertificates();
		URL httpsURL = getComposedURL("https");
		HttpsURLConnection connHttps = (HttpsURLConnection) httpsURL.openConnection();
		connHttps.setRequestMethod(getCallBackMethod());
		connHttps.setRequestProperty("Authorization", "Basic " + getEncondingForBasicAuth());
		connHttps.getResponseCode();
		return connHttps;
	}

	private String getEncondingForBasicAuth() {
		return (Base64.getEncoder().encodeToString((getCallBackUser() + ":" + getCallBackPassword()).getBytes()));
	}

	private URL getComposedURL(String schema) throws Exception {
		String composedUrl = schema + "://" + getCallBackResource() + "?";
		if (getCallBackParams()!=null) {
			composedUrl+=getParamsForAddToURL();
		}
		composedUrl+=("&callbackreport=" + getReportTSuiteURL());
		return new URL(composedUrl);
	}

	private String getParamsForAddToURL() {
		//Add params from callBackParam
		Map<String, String> pairsKeyValue = Splitter.on(';')
			.trimResults()
			.withKeyValueSeparator(
				Splitter.on(':')
				.limit(2)
				.trimResults())
			.split(getCallBackParams());

		String paramsForURL="";
		Iterator<String> itKeys = pairsKeyValue.keySet().iterator();
		while (itKeys.hasNext()) {
			String key = itKeys.next();
			String value = pairsKeyValue.get(key);
			paramsForURL+=(key + "=" + value);
			if (itKeys.hasNext()) {
				paramsForURL+="&";
			}
		}

		return paramsForURL;
	}
}