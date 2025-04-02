package com.aianlaysis.airequest.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aianlaysis.airequest.model.SrvyVO;

@Mapper
public interface SrvyMapper {
	// 만족도 설문조사 문제 삽입
    void insertSurvey(SrvyVO srvyVo);
    void deleteSrvy(String surveyNo, String email);
    void updateSrvy(SrvyVO srvyVo);
	ArrayList<SrvyVO> selectByEmail(String email, String groupNo);
	ArrayList<SrvyVO> selectSurveyQuestions(String email, String groupNo);
	void deleteAllSrvy(String email, String groupNo);
	List<SrvyVO> findByGroupNo(String groupNo);
}
