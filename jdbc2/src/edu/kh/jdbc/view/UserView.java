package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dto.User;
import edu.kh.jdbc.model.service.UserService;

//View:사용자와 직접 상호작용하는 화면(UI)를 담당,
//(사용자에게)입력받고 결과출력하는 역할
public class UserView {

	// 필드
	private UserService service = new UserService();
	private Scanner sc = new Scanner(System.in);

	// user 관리 프로그램 메인 메뉴 UI
	public void mainMenu() {
		int input = 0; // 메뉴선택용 변수
		do {
			try {
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");

				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거

				switch (input) {
				case 1:
					insertUser();
					break;
				case 2:
					selectAll();
					break;
				case 3:
					selectName();
					break;
				case 4:
					selectUser();
					break;
				case 5:
					deleteUser();
					break;
				case 6:
					updateName();
					break;
				case 7:
					insertUser2();
					break;
				case 8:
					multiInsertUser();
					break;
				case 0:
					System.out.println("\n[프로그램 종료]\n");
					break;
				default:
					System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}

				System.out.println("\n-------------------------------------\n");

			} catch (InputMismatchException e) {
				// Scanner를 이용한 입력시 자료형이 잘못됐을때
				System.out.println("\n**잘못 입력함**\n");
				input = -1;// 잘못 입력해서 while문 멈추는걸 방지
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자 제거

			} catch (Exception e) {
				e.printStackTrace(); // 발생되는 예외를 모두 해당 catch 구문으로 모아 처리
			}

		} while (input != 0);
	}

	/**
	 * 8.여러 User 등록하기
	 * 
	 */
	private void multiInsertUser() throws Exception {
		System.out.println("\n===7.User 등록 (아이디 중복검사)======\n");
		System.out.println("등록할 User 수:");
		int input = sc.nextInt();
		sc.nextLine(); // 버퍼 개행문자 제거, 다음에 문자값 받을거면 해주는게 좋음

		// 입력받은 회원 정보를 저장할 List 객체 생성
		List<User> userList = new ArrayList<User>();

		for (int i = 0; i < input; i++) {
			String userId = null; // 입력된 아이디를 저장할 변수

			while (true) {
				System.out.println((i + 1) + "번째 ID:");
				userId = sc.next();

				// 입력받은 userId가 중복인지 검사하는 서비스 호출 후 결과반환받기
				// int, 중복==1, 아니면==0) 반환받기
				int count = service.idCheck(userId);

				if (count == 0) { // 중복아닌경우
					System.out.println("사용가능 아이디");
					break;
				}
				System.out.println("사용중이야, 다시 입력해");
			}

			// pw,id 입력받기
			System.out.print((i + 1) + "번째 PW:");
			String userPw = sc.next();

			System.out.print((i + 1) + "번째 Name:");
			String userName = sc.next();

			// 입력받은 값 3개를 한번에 묶어서 전달할 수 있도록
			// User DTO 객체 생성 후 필드에 값세팅
			User user = new User(); // 기본생성자, heap에 생성되고 여긴 값이 없을 수 없음, JVM에 의해 String 기본값인 null로 세팅됨

			// setter 이용, 매개변수가 3개인 생성자없어서 객체 생성 후 세터로 값세팅
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);

			// userList에 user 객체 추가
			userList.add(user);
		} // for문 종료

		// 입력받은 모든 사용자를 insert 하는 서비스 호출
		// 결과로 삽입된 행의 갯수 반환

		int result = service.multiInsertUser(userList);

		if (result == userList.size()) { // result==input
			System.out.println("전체삽입성공!");
		} else {
			System.out.println("삽입실패!!");
		}
	}

	/**
	 * 7.User 등록(아이디 중복검사)
	 */
	private void insertUser2() throws Exception {
		System.out.println("\n===7.User 등록 (아이디 중복검사)======\n");

		String userId = null; // 입력된 아이디를 저장할 변수

		while (true) {
			System.out.print("ID:");
			userId = sc.next();

			// 입력받은 userId가 중복인지 검사하는 서비스 호출 후 결과반환받기
			// int, 중복==1, 아니면==0) 반환받기
			int count = service.idCheck(userId);

			if (count == 0) { // 중복아닌경우
				System.out.println("사용가능 아이디");
				break;
			}
			System.out.println("사용중이야, 다시 입력해");
		}

		// pw,name 입력받기
		System.out.print("PW:");
		String userPw = sc.next();
		System.out.println("Name:");
		String userName = sc.next();

		// 입력받은 값 3개를 한번에 묶어 전달할 수 있도록
		// User DTO 객체 생성 후 필드에 값세팅

		User user = new User();
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);

		// 서비스 호출 후 결과반환받기
		int result = service.insertUser(user);

		if (result > 0) {
			System.out.println("사용자 등록됨");
		} else {
			System.out.println("등록 실패");
		}
	}

	/**
	 * 6.ID,PW가 일치하는 회원있을(SELECT)경우 이름수정(UPDATE)
	 */
	private void updateName() throws Exception {
		System.out.println("\n====6.ID,PW가 일치하는 회원이름수정=====\n");

		System.out.print("ID:");
		String userId = sc.next();

		System.out.print("PW:");
		String userPw = sc.next();

		// 입력받은 ID, PW가 일치하는 회원있는지 조회(SELECT)
		// 수정할때 필요한 데이터 USER_NO 조회해오기
		int userNo = service.selectUserNo(userId, userPw);

		// 조회결과 없을때
		if (userNo == 0) {
			System.out.println("아이디,비밀번호 일치자 없음");
			return;
		}
		// 조회결과 있을때
		System.out.println("수정할 이름 입력:");
		String name = sc.next();

		// 위에서 조회된 회원(userNo)의 이름수정
		// 서비스 호출 후 결과 반환 받기
		int result = service.updateName(name, userNo);

		if (result > 0)
			System.out.println("수정성공!!");
		else
			System.out.println("수정실패");
	}

	/**
	 * 5. USER_NO를 입력받아 일치하는 User 삭제(DELETE) * DML 이다!! -- 삭제 성공했을 때 : 삭제 성공 -- 삭제
	 * 실패했을 때 : 사용자 번호가 일치하는 User가 존재하지 않음
	 */
	private void deleteUser() throws Exception {
		System.out.println("\n====5.USER_NO 입력받아 일치하는 User 삭제=====\n");

		System.out.print("삭제할 사용자번호:");
		int input = sc.nextInt();

		int result = service.deleteUser(input);

		if (result > 0)
			System.out.println("삭제성공");
		else
			System.out.println("일치하는 User 없어"); // 없는 번호 입력해도 오류나는게 아니라
												// 수행은 되는데 업데이트가 없어
	}

	 // 4.USER_NO 를 입력받아 일치하는 User 조회 * 딱 1행만 조회되거나 or 일치하는 것 못찾았을때
	 //찾았을 때 :
	 //User 객체출력, 없을 때 : USER_NO가 일치하는 회원 없음
	 	private void selectUser() throws Exception {
		System.out.println("\n========4.USER_NO로 회원 조회===========\n");

		System.out.print("사용자번호 입력:");
		int input = sc.nextInt();

		// service 호출 후 결과 반환받기
		// USER_NO (PK) == 중복불가!
		// 일치시 딱 1행만 조회, 1행의 조회결과를 담기 위해 User DTO 객체 1개 사용
		User user = service.selectUser(input); // 전달값줘야 DB가서 메서드
		
		// 조회 결과가 없으면 null , 있으면 null 이 아님
		if (user == null) {
			System.out.println("USER_NO가 일치하는 회원이 없습니다.");
			return;
		}
		System.out.println(user);
	}

	/**
	 * 3.User 중 이름에 검색어가 포함된 회원 조회 검색어 입력:유
	 */
	private void selectName() throws Exception {
		System.out.println("\n====3. User 중 이름에 검색어가 포함된 회원 조회===\n");

		System.out.print("검색어:");
		String keyword = sc.next();

		// 서비스 호출 후 결과 반환받기
		List<User> searchList = service.selectName(keyword);

		if (searchList.isEmpty()) {
			System.out.println("검색 결과 없음");
			return;
		}

		for (User user : searchList) {
			System.out.println(user);
		}
	}

	/**
	 * 2.User 전체 조회 관련 View (SELECT)
	 */
	private void selectAll() throws Exception {
		System.out.println("\n======2.User 전체조회======\n");

		// 서비스 호출(SELECT) 후 결과 반환(List<User>)받기
		List<User> userList = service.selectAll(); // 전달인자없음

		// 조회결과 없을 경우
		if (userList.isEmpty()) {
			System.out.println("\n****결과없어****\n");
			return;
		}
		// 조회결과 있을 경우
		// userList에 있는 모든 User객체 출력
		// 향상된 for문 이용!
		for (User us : userList) {
			System.out.println(us);
		}
	}

	/**
	 * 1.User 등록 관련된 View
	 */
	private void insertUser() throws Exception {
		System.out.println("\n=======1.User 등록========\n");

		System.out.print("ID:");
		String userId = sc.next();

		System.out.print("PW:");
		String userPw = sc.next();

		System.out.print("Name:");
		String userName = sc.next();

		// 입력받은 값 3개를 한번에 묶어서 전달할 수 있도록
		// User DTO 객체 생성 후 필드에 값세팅
		User user = new User(); // 기본생성자, heap에 생성되고 여긴 값이 없을 수 없음, JVM에 의해 String 기본값인 null로 세팅됨

		// setter 이용, 매개변수가 3개인 생성자없어서 객체 생성 후 세터로 값세팅
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);

		// 서비스 호출(INSERT) 후 결과반환(int, 결과행의 갯수-DML)받기
		int result = service.insertUser(user); // service(UserService)에 있는 insertUser() 메서드 호출

		// 반환된 결과에 따라 출력할 내용 선택
		if (result > 0) {
			System.out.println("\n" + userId + " 사용자가 등록되었습니다.\n");

		} else {
			System.out.println("\n***등록 실패***\n");
		}
	}

}
