package probono.controller;

import probono.model.dto.Beneficiary;
import probono.model.dto.Donator;
import probono.model.dto.TalentDonationProject;
import probono.service.TalentDonationProjectService;
import probono.view.EndView;
import probono.view.FailView;

public class TalentDonationProjectController {

	// singleton design pattern
	/* 객체 생성 수 제약
	 * 단 하나의 객체를 다른 외부 클래스에 받아서 사용 가능하게는 함
	 *  - 공유 개념, 보유하고 있는 멤버변수나 메소드 외부 호출 가능하게 하기 위함
	 *  - 주의사항
	 *  	- 외부에서 해당 객체를 삭제, 수정 금지
	 *  	- 해당 객체를 보유하고 참조하는 변수값 수정 금지
	 *  	- 생성자 외부 호출 : private 생성자
	 *  	- 객체를 공유한다는 건 객체의 주소값 공유
	 * 변수명 : instance / 메소드명 : getInstance() 명 singleton일 경우가 크다.
	 */
	private static TalentDonationProjectController instance = new TalentDonationProjectController();

	private static TalentDonationProjectService service = TalentDonationProjectService.getInstance();

	private TalentDonationProjectController() {} // 외부 생성 금지 

	public static TalentDonationProjectController getInstance() {
		return instance; // 주소값을 공유해서 
	}
  
	
	/**
	 * 모든 Project 검색
	 * 
	 * @return 모든 Project
	 */
	public void getDonationProjectsList() {
		EndView.projectListView(service.getDonationProjectsList());	
	}


	/**
	 * Project 이름으로 검색 - 객체된 Project 반환하기
	 * 
	 * @param projectName 프로젝트 이름
	 * @return TalentDonationProject 검색된 프로젝트
	 */
	public void getDonationProject(String projectName) {
		EndView.projectView(service.getDonationProject(projectName));
	}

	
	/**
	 * 새로운 Project 추가
	 * 
	 * @param project 저장하고자 하는 새로운 프로젝트
	 */
	
	public void donationProjectInsert(TalentDonationProject project){
		// 입력 데이터 중 프로젝트 이름만 반환 (기준 데이터, 구분 데이터, pk)
		String projectName = project.getTalentDonationProjectName();
		/* 입력 여부 검증
		 * projectName != null
		 * 	- 입력 존재 자체 없이 요청이 올 경우 null  -> 이건 무슨 상황? 예를들어?
		 * projectName.length() != 0
		 * 	- 브라우저 입력시 입력없이 엔터를 누르면 서버는 "" 문자열 길이가 0인 String 객체로 서버는 수신한다.
		 */
		
		//if 조건문은 -> 데이터 입력 여부만 검증하는 조건식
		if(projectName != null && projectName.length() != 0) {
			
			// 중복 id(프로젝트명) 여부 검증
			// db 연동하면서 검증
			try {
				
				service.donationProjectInsert(project);
				EndView.successMessage("새로운 프로젝트 등록 성공했습니다.");
				
			} catch (Exception e) {
				FailView.failViewMessage(e.getMessage()); //실패인 경우 예외로 end user 서비스
				e.printStackTrace();
			}
		}else {
			FailView.failViewMessage("입력 부족, 재 확인 하세요~~");
		}
	}

	/**
	 * Project의 기부자 수정 - 프로젝트 명으로 검색해서 해당 프로젝트의 기부자 수정
	 * 
	 * @param projectName 프로젝트 이름
	 * @param people      기부자
	 */
	public void donationProjectUpdate(String projectName, Donator people) {
		
		try {
			service.donationProjectUpdate(projectName, people);
		} catch (Exception e) {
			FailView.failViewMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Project의 수혜자 수정 - 프로젝트 명으로 검색해서 해당 프로젝트의 수혜자 수정
	 * 
	 * @param projectName 프로젝트 이름
	 * @param people      수혜자
	 * @throws Exception 
	 */
	public void beneficiaryProjectUpdate(String projectName, Beneficiary people) throws Exception {
		service.beneficiaryProjectUpdate(projectName, people);
	}

	/**
	 * Project 삭제 - 프로젝트 명으로 해당 프로젝트 삭제
	 * 
	 * @param projectName 삭제하고자 하는 프로젝트 이름
	 * @throws Exception 
	 */
	public void donationProjectDelete(String projectName) throws Exception {
		service.donationProjectDelete(projectName);
	}

}
