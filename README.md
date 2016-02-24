# SIST_Project1
쌍용교육센터 첫 번째 프로젝트 

00. 프로젝트 규칙

>	00-1

>>		마스터 브랜치 관련 푸시나 병합, 커밋은 조장이 담당합니다

>	00-2

>>		변수명명은 카멜표기법, 포맷팅(들여쓰기 등)은 자유롭게 합시다.

>	00-3

>>		조원들의 작업은 자신 이름의 이니셜 등을 접두사로 이용해 브랜치를 만들어 작업.
		예 ) branch 이름 :  msh_bugFixing 
>	00-4

>>		게임 리소스는 종류별로 resource/ 디렉터리 안에 위치시킵니다. 
		디렉토리를 새로 생성도 됨.
		resource/images/ 라던지, recources/wav/ 와 같이...
		네이밍은 의미있는 이름 + 번호로..
		ex)  ship1_normal_01
		     ship1_destoryed_01 
		     
>	00-5


01. 파일 및 디렉토리 설명

>Root
>>- BattleShip/ 				: 프로젝트 폴더
>>		- .gitignore				: 말 그대로...
		- BattleShip_plan.docx 		: 기획 문서 ( 지속 수정 )
		- README.md 				: This File.

	Root/BattleShip    /* 파일 설명은 생략 * /
		- bin/						: 컴파일된 파일들 ( gitignore 대상 )
		- src/
			- com/	
				- bss/				: BattleShip in Space 약칭 (가제)
					- client/		: 클라이언트 소스 디렉토리
					- 추가예정

			- resources/			: 게임 리소스 파일들과 ResourceLoader 클래스 


