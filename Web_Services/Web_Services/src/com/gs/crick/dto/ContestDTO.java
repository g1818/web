package com.gs.crick.dto;

/**
 * @author Guna.Sekhar
 *
 */
public class ContestDTO {
	

	
	private 	String contestId;
	private 	String contestName;
	private 	String contestFee;
	private 	int contestWinerCount;
	private 	int contestMaxEntryies;
	private 	int entryiesLeft;
	private 	int contestCount;
	
	
	
	
	public int getContestCount() {
		return contestCount;
	}
	public void setContestCount(int contestCount) {
		this.contestCount = contestCount;
	}
	public String getContestId() {
		return contestId;
	}
	public void setContestId(String contestId) {
		this.contestId = contestId;
	}
	public String getContestName() {
		return contestName;
	}
	public void setContestName(String contestName) {
		this.contestName = contestName;
	}
	public String getContestFee() {
		return contestFee;
	}
	public void setContestFee(String contestFee) {
		this.contestFee = contestFee;
	}
	public int getContestWinerCount() {
		return contestWinerCount;
	}
	public void setContestWinerCount(int contestWinerCount) {
		this.contestWinerCount = contestWinerCount;
	}
	public int getContestMaxEntryies() {
		return contestMaxEntryies;
	}
	public void setContestMaxEntryies(int contestMaxEntryies) {
		this.contestMaxEntryies = contestMaxEntryies;
	}
	public int getEntryiesLeft() {
		return entryiesLeft;
	}
	public void setEntryiesLeft(int entryiesLeft) {
		this.entryiesLeft = entryiesLeft;
	}
	
	
	

}
