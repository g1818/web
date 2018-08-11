package com.gs.crick.dto;

/**
 * Created by Guna Sekhar on 26-06-2018.
 */

public class UserQuestionAnswerDTO  {


	 private 	String questionDesc;
	    private 	String userAnswers;
	    private     String currentAnswers;
	    private     String answerPoint;
	    
	    
		public String getQuestionDesc() {
			return questionDesc;
		}
		public void setQuestionDesc(String questionDesc) {
			this.questionDesc = questionDesc;
		}
		public String getUserAnswers() {
			return userAnswers;
		}
		public void setUserAnswers(String userAnswers) {
			this.userAnswers = userAnswers;
		}
		public String getCurrentAnswers() {
			return currentAnswers;
		}
		public void setCurrentAnswers(String currentAnswers) {
			this.currentAnswers = currentAnswers;
		}
		public String getAnswerPoint() {
			return answerPoint;
		}
		public void setAnswerPoint(String answerPoint) {
			this.answerPoint = answerPoint;
		}
	  
	    


  
}
