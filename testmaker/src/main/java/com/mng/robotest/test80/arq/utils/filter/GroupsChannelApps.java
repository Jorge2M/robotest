package com.mng.robotest.test80.arq.utils.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;

public class GroupsChannelApps {
	
	private final Channel channelTest;
	private final AppTest appTest;
	private final List<List<Channel>> permutationsChannels;
	private final List<List<AppTest>> permutationsApps;
	private final List<ChannelsAppsDupla> excludedCombinations = new ArrayList<>();
	private final List<ChannelsAppsDupla> includedCombinations = new ArrayList<>();
	
	private GroupsChannelApps(Channel channel, AppTest app) {
		this.channelTest = channel;
		this.appTest = app;
		List<Channel> listChannels = Arrays.asList(Channel.values());
		List<AppTest> listApps = appTest.getValues();
		permutationsChannels = PermutationsUtil.getPermutations(listChannels.size(), listChannels);
		permutationsApps = PermutationsUtil.getPermutations(listApps.size(), listApps);
		setCombinationsIncludedExcluded();
	}
	
	public static GroupsChannelApps getNew(Channel channel, AppTest app) {
		return (new GroupsChannelApps(channel, app));
	}
	
	public List<String> getGroupsExcluded() {
		return (getGroupsFromCombinations(excludedCombinations));
	}
	
	public List<String> getGroupsIncluded() {
		return (getGroupsFromCombinations(includedCombinations));
	}
	
	private List<String> getGroupsFromCombinations(List<ChannelsAppsDupla> listCombinations) {
		List<String> groupsReturn = new ArrayList<>();
		for (ChannelsAppsDupla combination : listCombinations) {
			String listChannelsStr = combination.getChannels().toString().replace("[", "").replace("]", "").replace(" ", "");
			String listAppsStr = combination.getApps().toString().replace("[", "").replace("]", "").replace(" ", "");
			groupsReturn.add("Canal:" + listChannelsStr + "_App:" + listAppsStr);
			if (combination.getChannels().size()==Channel.values().length) {
				groupsReturn.add("Canal:all_App:" + listAppsStr);
			}
			if (combination.getApps().size()==appTest.getValues().size()) {
				groupsReturn.add("Canal:" + listChannelsStr + "_App:all");
			}
			if (combination.getChannels().size()==Channel.values().length &&
				combination.getApps().size()==appTest.getValues().size()) {
				groupsReturn.add("Canal:all_App:all");
			}
		}
		return groupsReturn;
	}
	
	private void setCombinationsIncludedExcluded() {
		for (List<Channel> permutationChannels : permutationsChannels) {
			for (List<AppTest> permutationApps : permutationsApps) {
				ChannelsAppsDupla dupla = new ChannelsAppsDupla(permutationChannels, permutationApps);
				if (!permutationChannels.contains(channelTest) ||
					!permutationApps.contains(appTest)) {
					excludedCombinations.add(dupla);
				} else {
					includedCombinations.add(dupla);
				}
				
			}
		}
	}
	
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
	
	private class ChannelsAppsDupla {
		private final List<Channel> channels;
		private final List<AppTest> apps;
		
		public ChannelsAppsDupla(List<Channel> channels, List<AppTest> apps) {
			this.channels = channels;
			this.apps = apps;
		}
		
		public List<Channel> getChannels() {
			return this.channels;
		}
		
		public List<AppTest> getApps() {
			return this.apps;
		}
	}
}