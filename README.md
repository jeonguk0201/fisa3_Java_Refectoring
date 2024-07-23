# JAVA Refactoring 프로젝트 ✨
> Lambda와 Stream API를 이용해서 재능기부 프로젝트 리팩토링을 진행했습니다.

### 🙌 개발 팀원
 - [이승언](https://github.com/jeonguk0201), [최수연](https://github.com/lotuxsoo), [손대현](https://github.com/DaeHyeonSon), [이정욱](https://github.com/seungunleeee)
<br/>

### 🏴 반환 타입 Optional로 수정
#### getDonationProject (원본)
 ``` JAVA
 public TalentDonationProject getDonationProject(String projectName) {
	for (TalentDonationProject project : donationProjectList) {
		if(project != null && project.getTalentDonationProjectName().equals(projectName)) {
			return project;
		}
	}
	return null;
}
 ```

#### getDonationProject (수정)
 ``` JAVA
 public Optional<TalentDonationProject>getDonationProject(String projectName) {
	return 	donationProjectList.stream()
			.filter(e -> e.getTalentDonationProjectName().equals(projectName))
			.findFirst();	
	}
 ```
<br/>

### 🏴CRUD 메소드 수정

#### INSERT 메소드 (원본)
``` JAVA
public void donationProjectInsert(TalentDonationProject project) throws Exception {
	
	TalentDonationProject p = getDonationProject(project.getTalentDonationProjectName());
	
	if (p != null) {
		throw new Exception ("해당 project명은 이미 존재합니다.");
	}
	
	donationProjectList.add(project);
}
```

 #### INSERT 메소드 (수정)
 ``` JAVA
 public void donationProjectInsert(TalentDonationProject project) throws Exception {

		Optional<TalentDonationProject> oProject = getDonationProject(project.getTalentDonationProjectName());

		if (oProject.isPresent()) {
			donationProjectList.add(project);
		}
		else throw new Exception("해당 project명은 이미 존재합니다.");
	}
 ```

 #### UPDATE 메소드 (원본)
 ``` JAVA
public void donationProjectUpdate(String projectName, Donator people) throws Exception {

	for (TalentDonationProject project : donationProjectList) {

		if (project != null && project.getTalentDonationProjectName().equals(projectName)) {
			if (people != null) {
				project.setProjectDonator(people);
				break;
			} else {
				throw new Exception("프로젝트 이름은 있으나 기부자 정보 누락 재확인 하세요");
			}
		} else {
			throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
		}
	}
}
```

 #### UPDATE 메소드 (수정)
 ``` JAVA
 public void donationProjectUpdate(String projectName, Donator people) throws Exception {
		
	Optional<TalentDonationProject> project =  this.getDonationProject(projectName);		
	
	if(project.isPresent()) {
		if(people == null) throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
		project.get().setProjectDonator(people);
	} else {
		throw new Exception("프로젝트 이름을 다시 확인해주세요");
	}
 }
 ```

 #### DELETE 메소드 (원본)
 ``` JAVA
 public void donationProjectDelete(String projectName) {
		
	TalentDonationProject project = getDonationProject(projectName);

	if (project != null) {
		donationProjectList.remove(project);
	}
}
 ```


 #### DELETE 메소드 (수정)
 ``` JAVA
 public void donationProjectDelete(String projectName) throws Exception {
		
	Optional<TalentDonationProject> project = getDonationProject(projectName);

	if (project.isPresent()) {
		donationProjectList.remove(project);
	} else {
		throw new Exception("프로젝트 이름과 기부자 정보 재 확인 하세요");
	}
} 
```
<br/>

## 🙄 고찰 사항
- getDonationProject에서 Optional 클래스를 사용하여 객체를 활용함으로써 코드의 재사용성을 증가시킴
- Null 및 예외 처리 관리가 이뤄짐
- NullPointerException의 위험을 줄일 수 있음
- 함수형 프로그래밍 스타일로 더 간결하고 명확한 코드를 작성할 수 있음
