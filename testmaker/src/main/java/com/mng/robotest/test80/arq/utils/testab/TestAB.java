package com.mng.robotest.test80.arq.utils.testab;

import java.util.List;

import com.mng.robotest.test80.arq.utils.otras.Channel;

public interface TestAB {

	public enum TypeTestAB {
		GoogleExperiments,
		Optimize;
	}
	
	public TypeTestAB getType();
	public List<Integer> getVariantes();
	public List<Channel> getChannels();
	public List<Enum<?>> getApps();
}


