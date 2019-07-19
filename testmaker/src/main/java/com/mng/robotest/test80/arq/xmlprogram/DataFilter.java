package com.mng.robotest.test80.arq.xmlprogram;

import java.util.List;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;

public interface DataFilter {
    public AppTest getAppE();
    public Channel getChannel();
    public List<String> getGroupsFilter();
    public List<String> getTestCasesFilter();
}
