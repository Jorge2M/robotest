package com.mng.testmaker.domain;


public enum StateExecution {
	NotStarted(false), 
	Started(false), 
	Finished_Normally(true), 
	Stopping(false), 
	Stopped(true);
	
	boolean finished;
	private StateExecution(boolean finished) {
		this.finished = finished;
	}
	public boolean isFinished() {
		return finished;
	}
}