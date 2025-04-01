package com.aianlaysis.airequest.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aianlaysis.airequest.model.SrvyResultVO;
import com.aianlaysis.airequest.model.SrvyVO;
import com.aianlaysis.airequest.service.SrvyService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController // 추가: RESTful API를 위한 어노테이션
public class SrvyController {

	 @Autowired
	    public SrvyService srvyService;
	    
	    // 관리자가 해당 요청에 대하여 만족도 조사를 등록하는 곳
	    @RequestMapping(value = "/api/SrvyReg", method = {RequestMethod.POST})
	    public void insertSrvy(@RequestBody Map<String, Object> requestBody) {
	        String email = (String) requestBody.get("email");
	        
	        List<Map<String, String>> submittedSrvys = (List<Map<String, String>>) requestBody.get("submittedSrvys");

	        for (Map<String, String> survey : submittedSrvys) {
	            SrvyVO vo = new SrvyVO();
	            vo.setEmail(email);
	            vo.setSrvyType(survey.get("svyType"));
	            vo.setSrvyCn(survey.get("srvyCn"));
	            vo.setChc(survey.get("chc"));
	            vo.setGroupNo(survey.get("groupNo"));
	            
	            srvyService.insertSrvy(vo);
	        }
	    }
	    
	    // 관리자가 해당 요청에 대하여 만족도 조사를 수정하는 곳
	    @RequestMapping(value = "/api/SrvyUpdate/{email}/{groupNo}", method = {RequestMethod.POST})
	    public void updateSrvy(@RequestBody Map<String, Object> requestBody) {
	        String email = (String) requestBody.get("email");
	        String groupNo = (String) requestBody.get("groupNo");
	        List<Map<String, String>> submittedSrvys = (List<Map<String, String>>) requestBody.get("submittedSrvys");
	        
	        // submittedSrvys 리스트의 내용 확인
	        //System.out.println("submittedSrvys: " + submittedSrvys);
	        
	        // 기존 데이터 삭제 (surveyNo를 넘겨서 삭제)
	        for (Map<String, String> survey : submittedSrvys) {
	            String surveyNo = survey.get("surveyNo");  // surveyNo 추가
	            //System.out.println("Deleting survey with surveyNo: " + surveyNo);  // 삭제되는 surveyNo 출력
	            
	            //srvyService.deleteSrvy(surveyNo, email, groupNo);  // surveyNo도 전달
	            
	            // surveyNo가 존재하면 삭제 처리
	            if (surveyNo != null && !surveyNo.isEmpty()) {
	            	//System.out.println("Deleting survey with surveyNo: " + surveyNo);
	                srvyService.deleteSrvy(surveyNo, email, groupNo);  // surveyNo도 전달
	            } else {
	                //System.out.println("Deleting all surveys for email: " + email + " and groupNo: " + groupNo);
	                srvyService.deleteAllSrvy(email, groupNo);  // 전체 삭제하는 서비스 추가
	                break;  // 모든 데이터 삭제했으므로 루프 종료
	            }
	        }
	        
	        // 새 데이터 추가
	        for (Map<String, String> survey : submittedSrvys) {
	            SrvyVO vo = new SrvyVO();
	            vo.setEmail(email);
	            vo.setGroupNo(groupNo);
	            vo.setSrvyType(survey.get("svyType"));
	            vo.setSrvyCn(survey.get("srvyCn"));
	            vo.setChc(survey.get("chc"));
	            
	            srvyService.insertSrvy(vo);
	        }
	    }
	    
	    // 관리자가 해당 요청에 대하여 등록되어있는 만족도 조사를 삭제하는 곳
	    @RequestMapping(value = "/api/SrvyDelete", method = {RequestMethod.POST})
	    public void deleteSrvy(
	    		@RequestParam String email, @RequestParam String groupNo, @RequestParam String surveyNo) {
	    	
	    	// 삭제 전 로그 추가
	        //System.out.println("Deleting survey with surveyNo: " + surveyNo + ", email: " + email + ", groupNo: " + groupNo);
	    	
	    	srvyService.deleteSrvy(surveyNo, email, groupNo);
	    }
	    
	    // 관리자가 업로드 한 만족도 조사를 수정하기 위해서 리스트를 가져오는 곳
	    @RequestMapping(value = "/api/GetSurveyData/{email}/{groupNo}", method = {RequestMethod.GET})
	    public ArrayList<SrvyVO> updateSrvy(
	    		@PathVariable String email, @PathVariable String groupNo) {
	    	ArrayList<SrvyVO> srvy = srvyService.forupdateSrvy(email, groupNo);
	    	
	    	//System.out.println("Survey data found: " + srvy); // 반환된 데이터 확인
		    return srvy;
	    }
	    
	    
	    // 특정 memId에 대한 만족도 조사 결과 조회
	    @RequestMapping(value = "/api/SrvyRslt", method = {RequestMethod.GET})
	    public List<SrvyResultVO> getSurveyResults(@RequestParam String groupNo, @RequestParam String memId) {

	        Map<String, Object> paramMap = new HashMap<>();

	        paramMap.put("groupNo", groupNo);
	        paramMap.put("memId", memId); 
	        
	        List<SrvyResultVO> results = srvyService.getSurveyResults(paramMap);
	        
	        // 결과가 비어 있을 경우 예외 방지
	        if (results == null || results.isEmpty()) {
	            System.out.println("만족도 조사 결과가 없습니다.");
	            return Collections.emptyList(); // 빈 리스트 반환
	        }
	        
	        return results;
	    }
	    
	    // 관리자가 회원들이 작성한 만족도 조사를 통계화해서 보여주는 API(전체 결과)
	    @RequestMapping(value = "/api/SrvyData", method = {RequestMethod.GET})
	    public List<SrvyResultVO> getSurveyData(@RequestParam String groupNo) {

	        Map<String, Object> paramMap = new HashMap<>();

	        paramMap.put("groupNo", groupNo);
	        
	        List<SrvyResultVO> results = srvyService.getSurveyData(paramMap);
	        
	        // 결과가 비어 있을 경우 예외 방지
	        if (results == null || results.isEmpty()) {
	            System.out.println("만족도 조사 결과가 없습니다.");
	            return Collections.emptyList(); // 빈 리스트 반환
	        }
	        
	        return results;
	    }
	    
	    // 일반 회원이 조사지를 받아서 화면에 띄워주는 곳
	    @RequestMapping(value = "/api/UserGetSrvy/{email}/{groupNo}", method = {RequestMethod.GET})
	    public List<SrvyVO> getSrvy(
	    		@PathVariable String email, 
	            @PathVariable String groupNo) {
	    	List<SrvyVO> srvy = srvyService.getSrvy(email, groupNo);

	    	return srvy;
	    }
	    
	    // 특정 surveyNo에 대한 만족도 조사 결과 조회
	    @RequestMapping(value = "/api/GetSurveyResults", method = {RequestMethod.GET})
	    public List<SrvyResultVO> getSurveyResults(@RequestParam("group_no") int group_no) {
	        Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("group_no", group_no);
	        return srvyService.getSurveyResults(paramMap);
	    }
	    
	 // 일반 회원이 만족도 조사를 실시하고 결과를 제출하는 API
	    @RequestMapping(value = "/api/SrvyResponse", method = {RequestMethod.POST})
	    public Map<String, String> srvyResponse(@RequestBody Map<String, Object> requestBody) {
	    	System.out.println("Request Body: @@@@" + requestBody);
	    	//public Map<String, String> srvyResponse(@PathVariable String email, @PathVariable String groupNo, @RequestBody Map<String, Object> requestBody) {
	        String email = (String) requestBody.get("email"); // 로그인된 이메일
	        //String memId = (String) requestBody.get("memId"); // 로그인된 사용자의 memId
	        String groupNo = (String) requestBody.get("groupNo");
	        List<Map<String, String>> submittedSrvys = (List<Map<String, String>>) requestBody.get("submittedSrvys");
	        Map<String, String> response = new HashMap<>();
	        
	        // 24시간 내 제출된 설문 개수 확인
	        int submissionCount = srvyService.checkSrvyResult(email, groupNo);
	        
//	        // 24시간 내 최대 15개 질문 제한
//	        if (submissionCount + submittedSrvys.size() > 15) {
//	            Map<String, String> response = new HashMap<>();
//	            response.put("message", "24시간 내 최대 15개의 질문만 제출할 수 있습니다.");
//	            return response; // 데이터 저장 없이 조기 종료
//	        }
	        if(submissionCount <= 0) {
		        // 중복이 없을 때만 데이터 저장
		        for (Map<String, String> surveyRslt : submittedSrvys) {
		            SrvyResultVO vo = new SrvyResultVO();
		            vo.setEmail(email); // 
		            vo.setChcRslt(surveyRslt.get("chcRslt"));
		            vo.setTextRslt(surveyRslt.get("textRslt"));
		            vo.setSrvyCn(surveyRslt.get("srvyCn"));
		            vo.setMemId(surveyRslt.get("memId"));
		            vo.setGroupNo(groupNo);
		            //vo.setSurveyNo(surveyRslt.get("surveyNo"));
		            //vo.setRegDt(LocalDateTime.now().toString()); // 현재 시간 저장
		            
		            //System.out.println("Survey No: " + vo.getSurveyNo());  // surveyNo 값 출력

		            // 날짜 형식 맞추기
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		            String formattedDate = LocalDateTime.now().format(formatter);
		            vo.setRegDt(formattedDate); // 형식 맞춘 문자열을 저장
	
		            srvyService.insertSrvyResult(vo); // DB 저장
		            //srvyService.checkAndUpdateSrvyResult(vo); // 데이터가 있으면 업데이트, 없으면 삽입
		        }
		        
		        response.put("message", "조사 응답이 정상적으로 저장되었습니다.");
	        } else {
	        	response.put("message", "조사 응답이 이미 저장되어 있습니다.");
	        }

	        
	        
	        return response;
	    }
	    
	    @RequestMapping(value = "/api/SrvyList/{groupNo}", method = RequestMethod.GET)
	    public List<SrvyVO> getSurveyList(@PathVariable String groupNo) {
	        System.out.println("Received groupNo: " + groupNo);  // groupNo 값 확인
	        List<SrvyVO> surveys = srvyService.getSurveyList(groupNo);  // 서비스 메소드 호출
	        return surveys;
	    }
	    
	    // 특정 회원이 이미 만족도 조사를 응답했는지 확인하는 API
	    @RequestMapping(value = "/api/SrvyResponse/check", method = RequestMethod.GET)
	    public ResponseEntity<?> checkSurveyResponse(
	            @RequestParam String email,
	            @RequestParam String groupNo) {

	        int submissionCount = srvyService.checkSrvyResult(email, groupNo);

	        if (submissionCount > 0) {
	            // 이미 응답했으면 403 상태 코드 반환
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(Collections.singletonMap("message", "이미 응답한 설문입니다."));
	        }

	        // 응답하지 않았으면 OK 반환
	        return ResponseEntity.ok(Collections.singletonMap("message", "응답 가능"));
	    }
}
