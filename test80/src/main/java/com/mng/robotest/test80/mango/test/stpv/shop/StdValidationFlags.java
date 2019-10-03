package com.mng.robotest.test80.mango.test.stpv.shop;

import com.mng.testmaker.utils.State;

public class StdValidationFlags {
	public boolean validaSEO = false;
	public State stateValidaSEO = State.Warn;
	public boolean validaJS = false;
	public State stateValidaJS = State.Warn;
	public boolean validaImgBroken = false;
	public State stateValidaImgBroken = State.Warn;
	
	private StdValidationFlags() {}
	public static StdValidationFlags newOne() {
		return (new StdValidationFlags());
	}
}
