package com.mng.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.mng.testmaker.boundary.access.OptionTMaker;

public class InputParamsBasic extends InputParamsTM {
	
	private final List<OptionTMaker> specificOptions;
	
	public InputParamsBasic(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		super(suiteEnum, appEnum);
		this.specificOptions = new ArrayList<>();
	}
	
	public InputParamsBasic(List<OptionTMaker> specificOptions, Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		super(suiteEnum, appEnum);
		this.specificOptions = specificOptions;
	}
	
	@Override
	public List<OptionTMaker> getSpecificParameters() {
		return specificOptions;
	}
	
	@Override
	public void setSpecificDataFromCommandLine(CommandLine cmdLine) {}
	
}
