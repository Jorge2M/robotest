package com.mng.robotest.test80.arq.utils.filter;

import java.util.List;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;

public class DataFilterTCases {

	private List<String> groupsFilter;
	private List<String> testCasesFilter;
	private Channel channel; 
	private AppTest appE;
	
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
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public AppTest getAppE() {
		return appE;
	}
	public void setAppE(AppTest appE) {
		this.appE = appE;
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