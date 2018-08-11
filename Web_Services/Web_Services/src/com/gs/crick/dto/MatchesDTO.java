package com.gs.crick.dto;

import java.util.Date;

public class MatchesDTO {
	

	
	private 	String  matcheid;
	private 	String firstTeam;
	private 	String secondTeam;
	private 	Date startDate;
	private 	Date end_date;
	private 	String  result;
	private 	String venue;
	private 	String matchType;
	private 	String matchcomment;
	private 	String resultcomment;
	private 	String firstteamscore;
	private 	String secondteamscore;
	
	
	
	public String getMatchcomment() {
		return matchcomment;
	}
	public void setMatchcomment(String matchcomment) {
		this.matchcomment = matchcomment;
	}
	public String getResultcomment() {
		return resultcomment;
	}
	public void setResultcomment(String resultcomment) {
		this.resultcomment = resultcomment;
	}
	public String getFirstteamscore() {
		return firstteamscore;
	}
	public void setFirstteamscore(String firstteamscore) {
		this.firstteamscore = firstteamscore;
	}
	public String getSecondteamscore() {
		return secondteamscore;
	}
	public void setSecondteamscore(String secondteamscore) {
		this.secondteamscore = secondteamscore;
	}
	public String getMatcheid() {
		return matcheid;
	}
	public void setMatcheid(String matcheid) {
		this.matcheid = matcheid;
	}
	public String getFirstTeam() {
		return firstTeam;
	}
	public void setFirstTeam(String firstTeam) {
		this.firstTeam = firstTeam;
	}
	public String getSecondTeam() {
		return secondTeam;
	}
	public void setSecondTeam(String secondTeam) {
		this.secondTeam = secondTeam;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	

}
