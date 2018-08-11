package com.gs.crick.dto;

/**
 * @author Guna.Sekhar
 *
 */
public class QuestionsDTO {
	

	
	private 	String matchQuestionsID;
	private 	String questionsID;
	private 	String questionDesc;
	private 	String firstOption;
	private 	String secondOption;
	private 	String answers;
	
	
	public String getQuestionsID() {
		return questionsID;
	}
	public void setQuestionsID(String questionsID) {
		this.questionsID = questionsID;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	
	public String getMatchQuestionsID() {
		return matchQuestionsID;
	}
	public void setMatchQuestionsID(String matchQuestionsID) {
		this.matchQuestionsID = matchQuestionsID;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	public String getFirstOption() {
		return firstOption;
	}
	public void setFirstOption(String firstOption) {
		this.firstOption = firstOption;
	}
	public String getSecondOption() {
		return secondOption;
	}
	public void setSecondOption(String secondOption) {
		this.secondOption = secondOption;
	}
	
	

}
