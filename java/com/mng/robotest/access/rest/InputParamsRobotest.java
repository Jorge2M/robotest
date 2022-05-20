package com.mng.robotest.access.rest;

import java.util.List;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.conftestmaker.Suites;


public class InputParamsRobotest {

	private final Suites suite;
	private final String driver;
	private final Channel channel;
	private final AppEcom appName;
	private final String urlName;
	private String tcaseName;
	private String version;
	private boolean asyncExec;
	private List<String> countrys;
	
	private InputParamsRobotest(Suites suite, String driver, Channel channel, AppEcom appName, String urlName) {
		this.suite = suite;
		this.driver = driver;
		this.channel = channel;
		this.appName = appName;
		this.urlName = urlName;
	}
	
	public static InputParamsRobotest from(Suites suite, String driver, Channel channel, AppEcom appName, String urlName) {
		return new InputParamsRobotest(suite, driver, channel, appName, urlName);
	}
	
	public Form getParamsForm() {
		Form formParams = new Form();
		
		MultivaluedMap<String, String> mapParams = formParams.asMap();
		mapParams.putSingle(InputParamsMango.SuiteNameParam, suite.name());
		mapParams.putSingle(InputParamsMango.DriverNameParam, driver);
		mapParams.putSingle(InputParamsMango.ChannelNameParam, channel.name());
		mapParams.putSingle(InputParamsMango.AppNameParam, appName.name());
		mapParams.putSingle(InputParamsMango.URLNameParam, urlName);
		mapParams.putSingle(InputParamsMango.TCaseNameParam, tcaseName);
		mapParams.putSingle(InputParamsMango.VersionNameParam, version);
		mapParams.putSingle(InputParamsMango.AsyncExecParam, String.valueOf(asyncExec));
		
		if (countrys!=null) {
			mapParams.putSingle(InputParamsMango.CountrysNameParam, String.join(",", countrys));
		}
		
		return formParams;
	}
	
	public InputParamsRobotest tcase(String tcaseName) {
		this.tcaseName = tcaseName;
		return this;
	}
	public InputParamsRobotest asyncExec(boolean asyncExec) {
		this.asyncExec = asyncExec;
		return this;
	}
	public InputParamsRobotest version(String version) {
		this.version = version;
		return this;
	}
	public InputParamsRobotest countrys(List<String> countrys) {
		this.countrys = countrys;
		return this;
	}
}
