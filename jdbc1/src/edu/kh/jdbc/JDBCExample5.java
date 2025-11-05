package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {

	public static void main(String[] args) {
		//아이디, 비밀번호, 이름 입력받아
		//TB_USER 테이블에 삽입(INSERT)하기
		
		//java.sql.PreparedStatement
		//SQL 중간에 ? (위치홀더, placeholder)를 작성하여
		//? 자리에 java 값을 대입할 준비가 돼있는 Statement <-java값을 받아줄 준비가 돼있음
		//장점1:SQL 작성 간편
		//장점2:?에 값대입시 자료형에 맞는 형태의 리터럴로 대입됨!
		//ex) String 대입 -> '값' (자동으로 '' 추가)
		//ex) int    대입 -> 값
		//장점3:성능,속도에서 우위점함
		
		//***PreparedStatement는 Statement의 자식이다***
		
		//1.JDBC 객체 참조 변수 선언
		Connection conn=null;
		PreparedStatement pstmt=null;
		//SELECT가 아니기 때문에 ResultSet 필요없음, DML의 결과값은 int, 행의 갯수임
		
		Scanner sc=null;
		
		try {
			//2.DriverManager 통해서 Connection 객체생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_chj";
			String password = "kh1234";

			conn = DriverManager.getConnection(url, userName, password);
			
			//3. SQL 작성
			sc=new Scanner(System.in);
			
			System.out.print("아이디 입력:");
			String id=sc.next();
			
			System.out.print("비밀번호 입력:");
			String pw=sc.next();
			
			System.out.print("이름 입력:");
			String name=sc.next();
			
			String sql="""
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					"""; //pstmt임
			
//			stmt=conn.createStatement(); //버스 만든 뒤 sql 태움
//			stmt.executeQuery(sql);

			//위치홀더가 있을때 무조건 pstmt, 없을때 stmt,pstmt / DML이나 SELECT (쿼리종류) 상관없음
			//4. PreparedStatement 객체 생성
			//->객체 생성과 동시에 SQL 담겨짐
			//->미리 ? (위치홀더)에 값을 받을 준비를 해야 되기 때문에 만들때부터 sql 실음
			
			pstmt=conn.prepareStatement(sql); //버스가 만들어질 당시부터 sql 싣고 있음
			
			//5. ? 위치홀더에 알맞은 값 대입
			//pstmt.set자료형(?순서, 대입할값);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			//->여기까지 수행되면 온전한 SQL이 완성된 상태!
			
			//6. SQL(INSERT) 수행 후 결과(int) 반환받기
			//+DML 수행전 해줘야 할 것!!
			//Connection의 AutoCommit 끄기
			//왜? 개발자가 트랜잭션을 마음데로 제어하기 위해서
			conn.setAutoCommit(false);
			
			//executeUpdate(); //sql이 이미 탔기 때문에 또 실으면 안돼, 괄호 비어둠
			//DML 수행, 결과행 갯수(int) 반환
			//->보통 DML 실패 0, 성공시 0 초과된 값이 반환된다
			
			//pstmt에서 executeQuery(), executeUpdate()
			//			ㄴSELECT->ResultSet으로 반환 ㄴDML->int(행수)로 반환
			//매개변수 자리에 sql 들어오면 안됨!!
			int result=pstmt.executeUpdate();
			
			//7.result 값에 따른 결과 처리+트랙잭션 제어처리
			if(result > 0) { //INSERT 성공시
				conn.commit(); //COMMIT 수행-> DB에 INSERT 데이터 영구 반영
				System.out.println(name+"님이 추가되었습니다.");
			} else {//실패
				conn.rollback(); //실패시 ROLLBACK
				System.out.println("추가 실패");
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//8. 사용한 JDBC 객체 자원 반환
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
				if(sc != null) sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
