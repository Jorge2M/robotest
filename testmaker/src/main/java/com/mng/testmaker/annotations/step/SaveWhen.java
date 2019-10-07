package com.mng.testmaker.annotations.step;

public enum SaveWhen {
	Always, 
	Never, 
	IfProblem;
	
	boolean IfProblemSave() {
		return (this==Always || this==IfProblem);
	}
}
