package com.mng.robotest.test80.arq.utils.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;

public class GroupsChannelApps {
	
	private final Channel channelTest;
	private final AppTest appTest;
	private final List<List<Channel>> permutationsChannels;
	private final List<List<AppTest>> permutationsApps;
	private final Map<List<Channel>, List<AppTest>> excludedCombinations;
	
	private GroupsChannelApps(Channel channel, AppTest app) {
		this.channelTest = channel;
		this.appTest = app;
//		permutationsChannels = generatePermutations(Arrays.asList(Channel.values()));
//		permutationsApps = generatePermutations(appTest.getValues());
		https://www.baeldung.com/java-combinations-algorithm
		permutationsChannels = Permutations.of(2, Arrays.asList(Channel.values()));
		permutationsApps = generatePermutations(appTest.getValues());
		excludedCombinations = getExcludedCombinations();
	}
	
	public static GroupsChannelApps getNew(Channel channel, AppTest app) {
		return (new GroupsChannelApps(channel, app));
	}
	
	public List<String> getGroupsExcluded() {
		List<String> excludedGrouns = new ArrayList<>();
		for (Map.Entry<List<Channel>, List<AppTest>> permutationExcluded : excludedCombinations.entrySet()) {
			List<Channel> listChannels = permutationExcluded.getKey();
			List<AppTest> listApps = permutationExcluded.getValue();
			String listChannelsStr = listChannels.toString().replace("[", "").replace("]", "").replace(" ", "");
			String listAppsStr = listApps.toString().replace("[", "").replace("]", "").replace(" ", "");
			excludedGrouns.add("Canal:" + listChannelsStr + "_App:" + listAppsStr);
			if (listChannels.size()==Channel.values().length) {
				excludedGrouns.add("Canal:all_App:" + listAppsStr);
			}
			if (listApps.size()==appTest.getValues().size()) {
				excludedGrouns.add("Canal:" + listChannelsStr + "_App:all");
			}
		}
		return excludedGrouns;
	}
	
	private Map<List<Channel>,List<AppTest>> getExcludedCombinations() {
		Map<List<Channel>, List<AppTest>> excludedCombinations = new HashMap<>();
		for (List<Channel> permutationChannels : permutationsChannels) {
			for (List<AppTest> permutationApps : permutationsApps) {
				if (!permutationChannels.contains(channelTest) &&
					!permutationApps.contains(appTest)) {
					excludedCombinations.put(permutationChannels, permutationApps);
				}
			}
		}
		
		return excludedCombinations;
	}

//	//TODO no se si devuelve también combinaciones de 1 solo elemento
//	private <E> List<List<E>> generatePermutations(List<E> original, int maxElements) {
//	    if (original.size() == 0) {
//	    	List<List<E>> result = new ArrayList<List<E>>(); 
//		    result.add(new ArrayList<E>()); 
//		    return result; 
//	    }
//	    E firstElement = original.get(0);
//	    List<List<E>> returnValue = new ArrayList<List<E>>();
//	    List<List<E>> permutations = generatePermutations(original.subList(1, original.size()), maxElements);
//	    for (List<E> smallerPermutated : permutations) {
//	    	for (int index=0; index <= smallerPermutated.size(); index++) {
//	    		List<E> temp = new ArrayList<E>(smallerPermutated);
//	    		temp.add(index, firstElement);
//	    		returnValue.add(temp);
//	    	}
//	    }
//	    return returnValue;
//	}
	
//  public List<String> getListGroupsToInclude() {
//	  ArrayList<String> listOfGroups = new ArrayList<>();
//	  Channel channel = dFilter.getChannel();
//	  AppTest app = dFilter.getAppE();
//	  listOfGroups.add("Canal:all_App:all");
//	  listOfGroups.add("Canal:all_App:" + app);
//	  listOfGroups.add("Canal:" + channel + "_App:all");
//	  listOfGroups.add("Canal:" + channel + "_App:" + app);
//	  return listOfGroups;
//  }

}
