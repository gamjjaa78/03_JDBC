package edu.kh.jdbc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTD (Data Transfer Object:데이터 전송객체)
//값을 묶어서 전달하는 용도의 객체
//DB에 데이터를 묶어서 전달하거나 DB에서 조회한 결과를 가져올때 사용 (데이터 교환위한 객체)
//저장할 수 있는 형태로 class 정의

//lombok:자바코드에서 반복적으로 작성해야하는 코드(보일러플레이트코드/겟셋,생성자 등)를
//내부적으로 자동완성해주는 라이브러리
//-->사용하고자하는 프로젝트에 라이브러리추가, java코드 작성하고 있는 IDE(툴) 설치

@Getter
@Setter
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 매개변수가 있는 생성자
@ToString
public class User {

	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private String enrollDate;
	// java.sql.Date 타입이 아니라 String으로 받았는지?
	// 개발자 마음, DB조회 시 날짜데이터를 원하는 형태의 문자열로 변환해서 조회할 예정
	// ->TO_CHAR() 이용시-> 2025년 10월 28일

}
