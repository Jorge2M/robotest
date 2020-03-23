package com.mng.testmaker.boundary.access;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class ServerCmdLine {

	final static int PORT_DEFAULT = 80;
	final static int SECUREPORT_DEFAULT= 443;
	
	final static String portparam = "port";
	final static String secureportparam = "secureport";
	
	/** 
	 * -ip address [-port porthttp] [-secureport porthttps] [-help | -h] 
	 */  
	public static ResultCmdServer parse(String[] args) {
		ResultCmdServer resultParse = new ResultCmdServer();
		resultParse.setOk(false);
		Options options = setParamsStructure();
		try {
			resultParse = parseAndGetParamData(options, args);
		} catch (ParseException ex){  
			System.out.println(ex.getMessage());  
			new HelpFormatter().printHelp(ServerCmdLine.class.getCanonicalName(), options );
		} catch (java.lang.NumberFormatException ex){  
			new HelpFormatter().printHelp(ServerCmdLine.class.getCanonicalName(), options );
		}
		
		return resultParse;
	}
	
	private static Options setParamsStructure() {
		Options options = new Options();  
		options.addOption(Option.builder(portparam)
			.required(false)
			.hasArg()
			.desc("HTTP Server Port").build());
			
		options.addOption(Option.builder(secureportparam)
			.required(false)
			.hasArg()
			.desc("HTTPS Server Port").build());
		
		return options;
	}
	
	private static ResultCmdServer parseAndGetParamData(Options options, String[] args) throws ParseException {
		ResultCmdServer resultParse = new ResultCmdServer();
		CommandLineParser parser  = new DefaultParser();
		CommandLine cmdLine = parser.parse(options, args);
		if (cmdLine.hasOption("h")){
			new HelpFormatter().printHelp(ServerCmdLine.class.getCanonicalName(), options );  
			return null;  
		}  

		if (cmdLine.hasOption(portparam)) {
			String port = cmdLine.getOptionValue(portparam);
			if (StringUtils.isNumeric(port)) {
				resultParse.setPort(Integer.parseInt(port));
			} else {
				System.out.println("Param " + portparam + " must be a numeric value");
				resultParse.setOk(false);
				return resultParse;
			}
		}
		
		if (cmdLine.hasOption(secureportparam)) {
			String portsecure = cmdLine.getOptionValue(secureportparam);
			if (StringUtils.isNumeric(portsecure)) {
				resultParse.setSecurePort(Integer.parseInt(cmdLine.getOptionValue(secureportparam)));
			}
			else {
				System.out.println("Param " + secureportparam + " must be a numeric value");
				resultParse.setOk(false);
				return resultParse;
			}
		}
		
		if (resultParse.getPort()==null && resultParse.getSecurePort()==null) {
			resultParse.setSecurePort(SECUREPORT_DEFAULT);  
			resultParse.setPort(PORT_DEFAULT);  
		}
		
		resultParse.setOk(true);
		return resultParse;
	}
	

	public static class ResultCmdServer {
		private boolean ok = false;
		private Integer port = null;  
		private Integer securePort = null;
		
		public boolean isOk() {
			return ok;
		}
		public void setOk(boolean ok) {
			this.ok = ok;
		}
		public Integer getPort() {
			return port;
		}
		public void setPort(Integer port) {
			this.port = port;
		}
		public Integer getSecurePort() {
			return securePort;
		}
		public void setSecurePort(Integer securePort) {
			this.securePort = securePort;
		}
	}
}
