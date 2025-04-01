package com.aianlaysis.airequest.model;

import java.util.List;
import java.util.Objects;

public class SrvyResultVO {

	 private String srvyRsltNo;  // 만족도 조사 고유 키
	 private String email;  // 만족도 조사 결과를 등록하는 회원 이메일
	 private String chcRslt;      // 만족도 조사 선택 결과(객관식)
	 private String textRslt;      // 만족도 조사 선택 결과(단답식)
	 private List<String> submittedSrvys; // 만족도 문항 리스트
	 private String regDt; // 등록 날짜
	 //private String surveyNo; // 해당 만족도 조사 문항 번호 (그룹no로 묶어야해서 삭제)
	 private String memId; // 만족도 조사하는 회원 이메일
	 private String groupNo; // 문항 묶기 위한 그룹
	 
	 // 설문 통계 관련
	 private String surveyNo; // 만족도 조사 문항번호
	 private String srvyCn;  // 조사 질문
	 private String chc;      // 선택지 리스트
	 private String srvyType; // 문항 타입
	 private String count;   // 선택된 개수 (int → Integer 변경)
	 private String percentage; // 선택 비율 (%)
	 
	 // 통계 관련
	 private int multipleChoiceCount;
	 private int shortAnswerCount;
	 private int totalResponses;
	 private String[] multipleChoiceResponses; // 배열로 변경
	 private String[] shortAnswerResponses; // 배열로 변경
	 private String[] responsePercentages;      // 응답 비율
	 
	 // 기본 생성자
	 public SrvyResultVO() {}
	 
	 // 모든 필드 포함된 생성자 (선택적 사용)
	 public SrvyResultVO(String srvyRsltNo, String email, String chcRslt, String textRslt,List<String> submittedSrvys, String regDt, 
			 String srvyCn, String chc, String srvyType, 
			 String count, String percentage, String memId, String groupNo, String surveyNo) {
	        this.srvyRsltNo = srvyRsltNo;
	        this.email = email;
	        this.chcRslt = chcRslt;
	        this.textRslt = textRslt;
	        this.submittedSrvys = submittedSrvys;
	        this.regDt = regDt;
	        this.srvyCn = srvyCn;
	        this.chc = chc;
	        this.srvyType = srvyType;
	        this.count = count;
	        this.percentage = percentage;
	        this.memId = memId;
	        this.groupNo = groupNo;
	        this.surveyNo = surveyNo;
	    }

	//getter, setter
	public String getSrvyRsltNo() {
		return srvyRsltNo;
	}

	public void setSrvyRsltNo(String srvyRsltNo) {
		this.srvyRsltNo = srvyRsltNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChcRslt() {
		return chcRslt;
	}

	public void setChcRslt(String chcRslt) {
		this.chcRslt = chcRslt;
	}

	public List<String> getSubmittedSrvys() {
		return submittedSrvys;
	}

	public void setSubmittedSrvys(List<String> submittedSrvys) {
		this.submittedSrvys = submittedSrvys;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
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

	public String getSrvyType() {
		return srvyType;
	}

	public void setSrvyType(String srvyType) {
		this.srvyType = srvyType;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
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

	
	public String getTextRslt() {
		return textRslt;
	}

	public void setTextRslt(String textRslt) {
		this.textRslt = textRslt;
	}
	
	
	public String getSurveyNo() {
		return surveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}

	// 객체를 문자열로 변환하는 toString() 추가
    @Override
    public String toString() {
        return "SrvyResultVO{" +
                "srvyRsltNo='" + srvyRsltNo + '\'' +
                ", email='" + email + '\'' +
                ", chcRslt='" + chcRslt + '\'' +
                ", textRslt='" + textRslt + '\'' +
                ", submittedSrvys=" + submittedSrvys +
                ", regDt=" + regDt +
                ", srvyCn='" + srvyCn + '\'' +
                ", chc='" + chc + '\'' +
                ", srvyType='" + srvyType + '\'' +
                ", count=" + count + 
                ", percentage=" + percentage +
                ", memId='" + memId + '\'' +
                ", groupNo='" + groupNo + '\'' +
                ", surveyNo='" + surveyNo + '\'' +
                '}';
    }

	

	public int getMultipleChoiceCount() {
		return multipleChoiceCount;
	}

	public void setMultipleChoiceCount(int multipleChoiceCount) {
		this.multipleChoiceCount = multipleChoiceCount;
	}

	public int getShortAnswerCount() {
		return shortAnswerCount;
	}

	public void setShortAnswerCount(int shortAnswerCount) {
		this.shortAnswerCount = shortAnswerCount;
	}

	public int getTotalResponses() {
		return totalResponses;
	}

	public void setTotalResponses(int totalResponses) {
		this.totalResponses = totalResponses;
	}

	public String[] getMultipleChoiceResponses() {
		return multipleChoiceResponses;
	}

	public void setMultipleChoiceResponses(String[] multipleChoiceResponses) {
		this.multipleChoiceResponses = multipleChoiceResponses;
	}

	public String[] getShortAnswerResponses() {
		return shortAnswerResponses;
	}

	public void setShortAnswerResponses(String[] shortAnswerResponses) {
		this.shortAnswerResponses = shortAnswerResponses;
	}
	
	 // 설문 응답 수와 비율 계산 메서드
    public void calculatePercentages() {
        if (multipleChoiceResponses != null && multipleChoiceResponses.length > 0 && multipleChoiceCount > 0) {
            responsePercentages = new String[multipleChoiceResponses.length];
            
            for (int i = 0; i < multipleChoiceResponses.length; i++) {
                // 비율 계산 (정수형 선택 수 기준)
                double percentage = (double) countChoiceResponses(multipleChoiceResponses[i]) / multipleChoiceCount * 100;
                responsePercentages[i] = String.format("%.2f", percentage);  // 소수점 둘째 자리까지 표시
            }
        }
    }
    
    // 선택된 응답 개수 계산 (예시: '만족' 또는 '불만족'에 대한 선택 개수)
    private int countChoiceResponses(String choice) {
        int count = 0;
        // 해당 선택지에 대해 선택된 응답 수를 계산하는 로직 구현
        // 예시로 "만족" 선택지에 대해 개수 세기
        for (String response : multipleChoiceResponses) {
            if (response.equals(choice)) {
                count++;
            }
        }
        return count;
    }

	public String[] getResponsePercentages() {
		return responsePercentages;
	}

	public void setResponsePercentages(String[] responsePercentages) {
		this.responsePercentages = responsePercentages;
	}

}