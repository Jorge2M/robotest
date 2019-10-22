package com.mng.testmaker.utils.filter;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.testfilter.GroupsChannelApps;
import com.mng.testmaker.service.testab.manager.AppEcom;

public class GroupsChannelAppsTest {

	@Test
	public void getGroupsExcludedTest() {
		GroupsChannelApps groups = GroupsChannelApps.getNew(Channel.desktop, AppEcom.shop);
		
		List<String> groupsExcluded = groups.getGroupsExcluded();
		List<String> groupsIncluded = groups.getGroupsIncluded();
		
		assertTrue(groupsExcluded.contains("Canal:desktop_App:outlet"));
		assertTrue(groupsExcluded.contains("Canal:desktop_App:outlet,votf"));
		assertTrue(groupsExcluded.contains("Canal:desktop_App:votf,outlet"));
		assertTrue(groupsExcluded.contains("Canal:movil_web_App:shop"));
		assertTrue(groupsExcluded.contains("Canal:movil_web_App:all"));
		
		assertTrue(!groupsExcluded.contains("Canal:desktop_App:shop"));
		assertTrue(!groupsExcluded.contains("Canal:all_App:shop"));
		assertTrue(!groupsExcluded.contains("Canal:desktop_App:all"));
		assertTrue(!groupsExcluded.contains("Canal:all_App:all"));
		
		assertTrue(groupsIncluded.contains("Canal:desktop_App:shop"));
		assertTrue(groupsIncluded.contains("Canal:all_App:shop"));
		assertTrue(groupsIncluded.contains("Canal:desktop_App:all"));
		assertTrue(groupsIncluded.contains("Canal:all_App:all"));
	}
}
