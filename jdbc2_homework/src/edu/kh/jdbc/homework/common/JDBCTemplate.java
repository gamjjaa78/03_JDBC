package edu.kh.jdbc.homework.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

//JDBCTemplate
//JDBC 관련 작업을 위한 코드를 미리 작성해서 제공하는 클래스
//Connection 생성
//AutoCommit false
//commit / rollback
//각종 자원 반환 close()

public class JDBCTemplate {

	private static Connection conn = null;

	// 메서드
	// 호출시 Connection 객체를 생성하여 호출한 곳으로 반환하는 메서드
	// +AutoCommit 끄기
	// @return conn
	public static Connection getConnection() {
		try {
			// 이전에 Connection 객체가 만들어졌고(존재하고)
			// 아직 close()된 상태가 아니라면
			// 새로 만들지 않고, 기존 Connection 반환
			if (conn != null && !conn.isClosed())
				return conn;

			Properties prop = new Properties();
			prop.loadFromXML(new FileInputStream("driver.xml"));
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("userName"),
					prop.getProperty("password"));
			conn.setAutoCommit(false);

		} catch (Exception e) {
			System.out.println("커넥션 생성 중 예외발생(JDBCTemplate의 getConnection())");
			e.printStackTrace();
		}
		return conn;
	}

	public static void commit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.commit();
		} catch (Exception e) {
			System.out.println("커밋 중 예외발생");
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.rollback();
		} catch (Exception e) {
			System.out.println("롤백 중 예외발생");
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			System.out.println("커넥션 close()중 예외발생");
			e.printStackTrace();
		}
	}

	// 전달받은 Statement or PreparedStatement 둘 다 close()할 수 있는 메서드
	// +다형성의 업캐스팅 적용->PreparedStatement는 Statement의 자식
	// 오버로딩:메서드에 같은 이름 사용, 매개변수 타입,개수,순서 중 1개라도 달라야함
	// @param stmt
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed())
				stmt.close();
		} catch (Exception e) {
			System.out.println("Statement close() 중 예외발생");
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
		} catch (Exception e) {
			System.out.println("ResultSet close() 중 예외발생");
			e.printStackTrace();
		}
	}
}