package com.mng.robotest.tests.domains.cookiesallowed.services.mangoauditor.beans; 

public class CookiesMangoAuditor{
    private GroupsParty functional;
    private GroupsParty targeting;
    private GroupsParty performance;
    private GroupsParty necessary;
    private GroupsParty social;
    
	public GroupsParty getFunctional() {
		return functional;
	}
	public void setFunctional(GroupsParty functional) {
		this.functional = functional;
	}
	public GroupsParty getTargeting() {
		return targeting;
	}
	public void setTargeting(GroupsParty targeting) {
		this.targeting = targeting;
	}
	public GroupsParty getPerformance() {
		return performance;
	}
	public void setPerformance(GroupsParty performance) {
		this.performance = performance;
	}
	public GroupsParty getNecessary() {
		return necessary;
	}
	public void setNecessary(GroupsParty necessary) {
		this.necessary = necessary;
	}
	public GroupsParty getSocial() {
		return social;
	}
	public void setSocial(GroupsParty social) {
		this.social = social;
	}
}
