package edu.kh.jdbc.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//Model 중 하나, DAO(Data Access Objecr 데이터 접근 객체)
//데이터가 저장된 곳(DB)에 접근하는 용도의 객체
//->DB에 접근하여 Java에서 원하는 결과 얻기 위해 SQL 수행 후 결과 반환받는 역할
public class UserDAO {
	//필드
	//DB접근 관련한 JDBC 객체참조변수 선언
	private Statement stmt=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
}
