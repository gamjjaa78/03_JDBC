package edu.kh.jdbc.homework.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.homework.model.dto.Student;
import edu.kh.jdbc.homework.model.service.StudentService;
import edu.kh.jdbc.model.dto.User;

public class StudentView {
	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);

	
	
	public void mainMenu() {
		int input = 0;
		do {
			try {
				System.out.println("\n****학생관리 프로그램****\n");
				System.out.println("1.학생 등록");
				System.out.println("2.전체 학생조회");
				System.out.println("3.학생 정보수정");
				System.out.println("4.학생 삭제");
				System.out.println("5.전공별 학생조회");
				System.out.println("0. 프로그램 종료");

				System.out.print("메뉴선택 : ");
				input = sc.nextInt();
				sc.nextLine();

				switch (input) {
				case 1:
					insertStudent();
					break;
				case 2:
					selectAll();
					break;
				case 3:
					update();
					break;
				case 4:
					delete();
					break;
				case 5:
					select();
					break;
				case 0:
					System.out.println("\n****종료****\n");
					break;
				default:
					System.out.println("\n****번호만 써라****\n");
				}
				System.out.println("\n-------------------------------------\n");

			} catch (InputMismatchException e) {
				System.out.println("\n**잘못 입력함**\n");
				input = -1;
				sc.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (input != 0);
	}

	// 새로운 학생 정보 삽입
	private void insertStudent() throws Exception {
		System.out.println("\n****학생 등록****\n");

		System.out.print("이름:");
		String stdName = sc.next();

		System.out.print("나이:");
		int stdAge = sc.nextInt();

		System.out.print("전공:");
		String major = sc.next();

		Student std=new Student();
		
		std.setStdName(stdName);
		std.setStuAge(stdAge);
		std.setStuMajor(major);

		int result = service.insertStudent(std); // service(UserService)에 있는 insertUser() 메서드 호출

		if (result > 0) {
			System.out.println("\n****등록됐다\n");
		} else {
			System.out.println("\n****실패여\n");
		}
	}
		
		
		
	

	// 모든 학생 정보 조회
	private void selectAll() throws Exception {
		System.out.println("\n****전체 학생조회****\n");
		List<Student> stdList = service.selectAll();
		if (stdList.isEmpty()) {
			System.out.println("\n****학생없어\n");
			return;
		}
		for (Student st : stdList) {
			System.out.println(st);
		}

	}

	// 이름, 나이, 전공 변경 가능
	private void update() throws Exception {

	}

	// 학번 기준 삭제
	private void delete() throws Exception {
		System.out.println("\n****학생 삭제****\n");

		System.out.print("삭제할 학번:");
		int input = sc.nextInt();
		int result = service.deleteUser(input);
		if (result > 0)
			System.out.println("\n****삭제성공\n");
		else
			System.out.println("\n****일치학생없어\n"); 
	}

	// 특정 전공 학생만 필터링 조회
	private void select() throws Exception {

	}

}
