package com.pixel.model;

import java.util.List;

public class InterviewQuestionsBean {

	private int pk;
	private String companyName;
	private String interviewQuestion;
	private String shortAnswer;
	private String createdDate;
	
	private List<AddFormDetailModel> answerDetails;
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		
		this.createdDate = createdDate;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getInterviewQuestion() {
		return interviewQuestion;
	}
	public void setInterviewQuestion(String interviewQuestion) {
		this.interviewQuestion = interviewQuestion;
	}
	public String getShortAnswer() {
		return shortAnswer;
	}
	public void setShortAnswer(String shortAnswer) {
		this.shortAnswer = shortAnswer;
	}
	public List<AddFormDetailModel> getAnswerDetails() {
		return answerDetails;
	}
	public void setAnswerDetails(List<AddFormDetailModel> answerDetails) {
		this.answerDetails = answerDetails;
	}
	
}
