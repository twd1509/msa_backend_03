package com.aianlaysis.airequest.model;


public class SrvyVO {

	private String surveyNo;			// 만족도 조사 문항번호
    private String email; 	// 만족도 조사를 등록하는 회원 이메일
    private String srvyType;		// 설문 조사의 질문 유형
    private String srvyCn;			// 설문 조사의 질문 내용
    private String chc;				// 객관식의 경우, 들어갈 보기
    private String regDt;	// 등록일
    private String memId; // 만족도 조사하는 회원 이메일
    private String groupNo; // 문항 묶기 위한 그룹
    
    //getter, setter
	public String getSurveyNo() {
		return surveyNo;
	}
	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSrvyType() {
		return srvyType;
	}
	public void setSrvyType(String srvyType) {
		this.srvyType = srvyType;
	}
	public String getSrvyCn() {
		return srvyCn;
	}
	public void setSrvyCn(String srvyCn) {
		this.srvyCn = srvyCn;
	}
	public String getChc() {
		return chc;
	}
	public void setChc(String chc) {
		this.chc = chc;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	
}