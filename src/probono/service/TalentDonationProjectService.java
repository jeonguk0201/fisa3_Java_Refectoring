/** 
 * PROJECT  : 재능기부 프로젝트
 * NAME  :  TalentDonationProjectService.java
 * DESC  :  재능 기부 프로젝트를 저장, 수정, 삭제, 검색하는 서비스 로직
 * 
 * @author  
 * @version 1.0
*/

package probono.service;

import java.util.ArrayList;
import java.util.Optional;

import probono.model.dto.Beneficiary;
import probono.model.dto.Donator;
import probono.model.dto.TalentDonationProject;

public class TalentDonationProjectService {

	// singleton design pattern
	// 서비스클래스에서 일을 제일 많이 함.
	private static TalentDonationProjectService instance = new TalentDonationProjectService();

	/**
	 * 브라우저 입력창 (form) -> 입력없이 데이터 전송 -> "" 문자열 객체, 길이 0을 수신함 미존재하는 요청 = server에선
	 * null
	 * 
	 * 진행중인 Project를 저장
	 */
	private ArrayList<TalentDonationProject> donationProjectList = new ArrayList<TalentDonationProject>();

	private TalentDonationProjectService() {
	}

	public static TalentDonationProjectService getInstance() {
		return instance;
	}

	/**
	 * 모든 Project 검색
	 * 
	 * @return 모든 Project
	 */
	public ArrayList<TalentDonationProject> getDonationProjectsList() {
		return donationProjectList;
	}

	// TO DO - 구현 및 완성해야 할 job
	/**
	 * Project 이름으로 검색 - 객체된 Project 반환하기
	 * 
	 * @param projectName 프로젝트 이름
	 * @return TalentDonationProject 검색된 프로젝트
	 */
	public Optional<TalentDonationProject>getDonationProject(String projectName) {
		return 	donationProjectList.stream()
				.filter(e -> e.getTalentDonationProjectName().equals(projectName))
				.findFirst();	
	}

	// TO DO
	/**
	 * 새로운 Project 추가
	 * 
	 * @param project 저장하고자 하는 새로운 프로젝트
	 */

	/*
	 * Controller의 메소드 public void donationProjectInsert(TalentDonationProject
	 * project){}
	 */
	public void donationProjectInsert(TalentDonationProject project) throws Exception {
		Optional<TalentDonationProject> oProject = getDonationProject(project.getTalentDonationProjectName());

		if (oProject.isPresent()) {
			donationProjectList.add(project);
		}
		else throw new Exception("해당 project명은 이미 존재합니다.");
	}

	/**
	 * Project의 기부자 수정 - 프로젝트 명으로 검색해서 해당 프로젝트의 기부자 수정
	 * 
	 * @param projectName 프로젝트 이름
	 * @param people      기부자
	 */
	public void donationProjectUpdate(String projectName, Donator people) throws Exception{
		
			Optional<TalentDonationProject> project =  this.getDonationProject(projectName);		
			
			if(project.isPresent()) {
				if(people == null) throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
				project.get().setProjectDonator(people);
			} else {
				throw new Exception("프로젝트 이름을 다시 확인해주세요");
			}

	}

	// TO DO
	/**
	 * Project의 수혜자 수정 - 프로젝트 명으로 검색해서 해당 프로젝트의 수혜자 수정
	 * 
	 * @param projectName 프로젝트 이름
	 * @param people      수혜자
	 * @throws Exception 
	 */
	public void beneficiaryProjectUpdate(String projectName, Beneficiary people) throws Exception {

		Optional<TalentDonationProject> project = getDonationProject(projectName);
		
		if(project.isPresent()) {
			if(people == null) throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
			project.get().setProjectBeneficiary(people);
		}
	}

	// TO DO
	/**
	 * Project 삭제 - 프로젝트 명으로 해당 프로젝트 삭제
	 * 
	 * @param projectName 삭제하고자 하는 프로젝트 이름
	 * @throws Exception 
	 */
	public void donationProjectDelete(String projectName) throws Exception {
		Optional<TalentDonationProject> project = getDonationProject(projectName);

		if (project.isPresent()) {
			donationProjectList.remove(project);
		}else {
			throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
		}

	}

}
