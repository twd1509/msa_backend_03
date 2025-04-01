package com.aianlaysis.airequest.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aianlaysis.airequest.mapper.SrvyMapper;
import com.aianlaysis.airequest.mapper.SrvyResultMapper;
import com.aianlaysis.airequest.model.SrvyResultVO;
import com.aianlaysis.airequest.model.SrvyVO;

@Service
public class SrvyService {

	@Autowired
	SrvyMapper srvymapper;
	
	@Autowired
	SrvyResultMapper srvyRsltMapper;
	
	// 만족도 조사 DB에 넣는 곳
	public void insertSrvy(SrvyVO srvyVo) {
		srvymapper.insertSurvey(srvyVo);
	}
	
	// 만족도 조사 삭제
	public void deleteSrvy(String surveyNo, String email, String groupNo) {
		
		// 로그 추가
	    System.out.println("Service: Deleting survey with surveyNo: " + surveyNo + ", email: " + email + ", groupNo: " + groupNo);
		
		srvymapper.deleteSrvy(surveyNo, email);
	}
	
	// 특정 이메일과 그룹 번호로 전체 삭제하는 메서드 추가
	public void deleteAllSrvy(String email, String groupNo) {
	    srvymapper.deleteAllSrvy(email, groupNo);
	}
	
	
	// 관리자가 만족도 조사 update하기
	public void updateSrvy(SrvyVO srvyVo) {
		srvymapper.updateSrvy(srvyVo);
	}
	
	// 관리자가 만족도 조사에 응한 회원들의 조사 결과를 정리하는 곳
	public List<SrvyResultVO> getSurveyResults(Map<String, Object> paramMap) {
	    List<Map<String, Object>> results = srvyRsltMapper.selectSurveyResults(paramMap);

	    List<SrvyResultVO> surveyResults = new ArrayList<>();
	    for (Map<String, Object> map : results) {
	        SrvyResultVO vo = new SrvyResultVO();
	        
	        vo.setSrvyType(map.get("srvyType") != null ? map.get("srvyType").toString() : null);
	        vo.setSrvyCn(map.get("srvyCn") != null ? map.get("srvyCn").toString() : null);
	        vo.setChc(map.get("chc") != null ? map.get("chc").toString() : null);
	        vo.setGroupNo(map.get("group_no") != null ? map.get("group_no").toString() : null);
	        //vo.setSurveyNo(map.get("surveyNo") != null ? map.get("surveyNo").toString() : null);

	        //  객관식 응답 값 저장
	        if (map.get("chcRslt") != null) {
	            vo.setChcRslt(map.get("chcRslt").toString());
	        }

	        //  단답형 응답 값 저장
	        if (map.get("textRslt") != null) {
	            vo.setTextRslt(map.get("textRslt").toString());
	        }
	        
	        vo.setCount(map.get("count") != null ? map.get("count").toString() : "0");
	        vo.setPercentage(map.get("percentage") != null ? map.get("percentage").toString() : "0");

	        surveyResults.add(vo);
	    }

	    return surveyResults;
	}

	
	// 일반 회원이 만족도 조사를 보기 위해 조사지를 받아오는 함수
	public ArrayList<SrvyVO> forupdateSrvy(String email, String groupNo) {
		return srvymapper.selectByEmail(email, groupNo);
	}
	
	// 일반 회원이 만족도 조사를 보기 위해 조사지를 받아오는 함수
	public List<SrvyVO> getSrvy(String email, String groupNo) {
		return srvymapper.selectSurveyQuestions(email, groupNo);
	}
	
	// 사용자가 조사지를 submit 해서 DB에 넣는 곳
	public void insertSrvyResult(SrvyResultVO srvyRsultVO) {
		srvyRsltMapper.insertSurveyResult(srvyRsultVO);
	}
	
	public int checkSrvyResult(String email, String groupNo) {
	    return srvyRsltMapper.checkSrvyResult(email, groupNo);
	}
	
	
	// 설문 결과가 이미 존재하는지 확인
	public void checkAndUpdateSrvyResult(SrvyResultVO srvyResultVO) {
	    // 이메일과 그룹 번호로 기존 데이터를 조회
	    SrvyResultVO existingResult = srvyRsltMapper.findByEmailAndGroupNo(srvyResultVO.getEmail(), srvyResultVO.getGroupNo());
	    
	    if (existingResult != null) {
	        // 데이터가 이미 존재하면 최신 응답으로 업데이트
	        try {
	            // 기존의 regDt (등록 일시) 값과 새로 받은 regDt를 비교
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            LocalDateTime existingRegDt = LocalDateTime.parse(existingResult.getRegDt(), formatter);  // 기존 regDt를 LocalDateTime으로 변환
	            LocalDateTime newRegDt = LocalDateTime.parse(srvyResultVO.getRegDt(), formatter);  // 새로 받은 regDt를 LocalDateTime으로 변환
	            
	            // 기존 응답 시간이 더 오래된 경우만 업데이트
	            //if (existingRegDt.isBefore(newRegDt)) {
	                srvyRsltMapper.updateSurveyResult(srvyResultVO); // 업데이트 쿼리 실행
	            //}
	        } catch (DateTimeParseException e) {
	            // 날짜 형식이 맞지 않으면 예외 처리
	            System.err.println("Error parsing date: " + e.getMessage());
	        }
	    } else {
	        // 데이터가 없으면 새로 삽입
	        srvyRsltMapper.insertSurveyResult(srvyResultVO); // 삽입 쿼리 실행
	    }
	}
	
	// 관리자가 만족도 조사에 응한 회원들 조사 결과 통계 보는 곳
	public List<SrvyResultVO> getSurveyData(Map<String, Object> paramMap) {
	    List<Map<String, Object>> results = srvyRsltMapper.selectSurveyData(paramMap);

	    List<SrvyResultVO> surveyData = new ArrayList<>();
	    
	    for (Map<String, Object> map : results) {
	        SrvyResultVO vo = new SrvyResultVO();

	        // 매핑된 데이터를 SrvyResultVO에 설정
	        vo.setSrvyCn(map.get("srvyCn") != null ? map.get("srvyCn").toString() : null);
	        
	        // 객관식 응답이 null이 아니고 빈 문자열이 아닌 경우, split으로 배열로 변환
	        if (map.get("multipleChoiceResponses") != null) {
	            String multipleChoiceResponses = map.get("multipleChoiceResponses").toString();
	            vo.setMultipleChoiceResponses(multipleChoiceResponses.split(","));
	        }

	        // 단답형 응답이 null이 아니고 빈 문자열이 아닌 경우, split으로 배열로 변환
	        if (map.get("shortAnswerResponses") != null) {
	            String shortAnswerResponses = map.get("shortAnswerResponses").toString();
	            vo.setShortAnswerResponses(shortAnswerResponses.split(","));
	        }

	        vo.setMultipleChoiceCount(map.get("multipleChoiceCount") != null ? Integer.parseInt(map.get("multipleChoiceCount").toString()) : 0);
	        vo.setShortAnswerCount(map.get("shortAnswerCount") != null ? Integer.parseInt(map.get("shortAnswerCount").toString()) : 0);
	        vo.setTotalResponses(map.get("totalResponses") != null ? Integer.parseInt(map.get("totalResponses").toString()) : 0);

	        // 퍼센트 계산 (객관식 응답 비율)
	        if (vo.getTotalResponses() > 0) {
	            double percentage = (double) vo.getMultipleChoiceCount() / vo.getTotalResponses() * 100;
	            vo.setPercentage(String.format("%.2f", percentage)); // 퍼센트를 소수점 2자리로 표시
	        }

	        // 객관식 응답 별로 '답한 인원' 계산 및 퍼센트 계산
	        if (vo.getMultipleChoiceResponses() != null) {
	            calculateResponsePercentages(vo);
	        }

	        surveyData.add(vo);
	    }
	    return surveyData;
	}
	
	// 객관식 응답별 '답한 인원' 계산 및 퍼센트 계산을 별도의 메서드로 분리
	private void calculateResponsePercentages(SrvyResultVO vo) {
	    if (vo.getMultipleChoiceResponses() != null && vo.getMultipleChoiceResponses().length > 0) {
	        // responsePercentages 배열 초기화
	        vo.setResponsePercentages(new String[vo.getMultipleChoiceResponses().length]);

	        for (int i = 0; i < vo.getMultipleChoiceResponses().length; i++) {
	            String response = vo.getMultipleChoiceResponses()[i];

	            // 파라미터로 전달할 Map 생성
	            Map<String, Object> params = new HashMap<>();
	            params.put("srvyCn", vo.getSrvyCn());  // 질문 항목 (srvyCn)
	            params.put("chcRslt", response);  // 응답 (객관식 답변)

	            // 각 응답에 대해 답한 인원 수 계산
	            int personAnswerCnt = srvyRsltMapper.selectMccA(params);

	            // 퍼센트 계산
	            double personPercentage = (double) personAnswerCnt / vo.getTotalResponses() * 100;

	            // 퍼센트 값을 responsePercentages 배열에 저장
	            vo.getResponsePercentages()[i] = String.format("%.2f", personPercentage);  // 소수점 2자리로 포맷팅

	            // 계산된 결과를 로그로 출력
	            System.out.println("응답: " + response + " - 답한 인원: " + personAnswerCnt + " - 퍼센트: " + String.format("%.2f", personPercentage) + "%");
	        }
	    }
	}

	public List<SrvyVO> getSurveyList(String groupNo) {
		// TODO Auto-generated method stub
		return srvymapper.findByGroupNo(groupNo);
	}
}