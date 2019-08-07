package com.mng.robotest.test80.arq.utils.filter;

import java.util.List;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;

public class DataFilterTCases {

	private final Channel channel; 
	private final AppTest appE;
	private List<String> groupsFilter;
	private List<String> testCasesFilter;
	
	public DataFilterTCases(Channel channel, AppTest appE) {
		this.channel = channel;
		this.appE = appE;
	}
	
	public List<String> getGroupsFilter() {
		return groupsFilter;
	}
	public void setGroupsFilter(List<String> groupsFilter) {
		this.groupsFilter = groupsFilter;
	}
	public List<String> getTestCasesFilter() {
		return testCasesFilter;
	}
	public void setTestCasesFilter(List<String> testCasesFilter) {
		this.testCasesFilter = testCasesFilter;
	}
	public Channel getChannel() {
		return channel;
	}
	public AppTest getAppE() {
		return appE;
	}
	
	public boolean isSomeFilterActive() {
		return (
			isActiveFilterByTestCases() ||
			isActiveFilterByGroups()
		);
	}
	
    public boolean isActiveFilterByTestCases() {
    	return (
    		testCasesFilter!=null && 
    		testCasesFilter.size()!=0 && 
    		"*".compareTo(testCasesFilter.get(0))!=0
    	);
    }
    
    public boolean isActiveFilterByGroups() {
    	return (
    		groupsFilter!=null && 
    		groupsFilter.size()!=0 && 
    		"*".compareTo(groupsFilter.get(0))!=0
    	);
    }
}