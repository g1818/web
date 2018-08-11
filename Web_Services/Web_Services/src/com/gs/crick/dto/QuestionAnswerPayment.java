package com.gs.crick.dto;






/**
 * Created by Guna Sekhar on 26-06-2018.
 */

public class QuestionAnswerPayment  {


    private 	String matchQuestionsID;
    private 	String answers;
    private     String matchId;
    private     String useremail;
    private     String contestId;
    private     String paymentStatus;
    private     String questionID;
    

    public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public String getMatchQuestionsID() {
        return matchQuestionsID;
    }

    public void setMatchQuestionsID(String matchQuestionsID) {
        this.matchQuestionsID = matchQuestionsID;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
