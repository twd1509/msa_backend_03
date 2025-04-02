package com.aianlaysis.airequest.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.aianlaysis.airequest.model.SrvyResultVO;

@Mapper
public interface SrvyResultMapper {

	// 만족도 조사 사용자 결과 삽입
    void insertSurveyResult(SrvyResultVO srvyResultVO);
    
    // 설문 결과 조회 (리스트 반환, SrvyResultVO 객체 형태로 반환)
    List<Map<String, Object>> selectSurveyResults(Map<String, Object> paramMap);
    
    // 특정 이메일과 그룹 번호에 대해 설문 결과가 존재하는지 확인
	int checkSrvyResult(String email, String groupNo);
	//int countSurveysSubmittedWithin24Hours(String email, String groupNo);
	
	// 특정 이메일과 그룹 번호로 설문 결과 조회
	SrvyResultVO findByEmailAndGroupNo(String email, String groupNo);
	
	// 설문 결과 업데이트
	void updateSurveyResult(SrvyResultVO srvyResultVO);
	
	// 관리자용 설문 데이터 조회
	List<Map<String, Object>> selectSurveyData(Map<String, Object> paramMap);
	
	// 객관식 응답에 대한 선택한 인원 수 조회
	int selectMccA(Map<String, Object> params);
}
