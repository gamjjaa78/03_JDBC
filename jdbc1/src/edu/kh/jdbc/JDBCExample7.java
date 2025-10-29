package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {

	public static void main(String[] args) {
		//1.JDBC 객체참조변수 선언
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //메모리에 로드
			String url = "jdbc:oracle:thin:@localhost:1521:XE"; //
			String userName = "kh_chj";
			String password = "kh1234";
			conn = DriverManager.getConnection(url, userName, password);
		
			//3.SQL 작성
			sc=new Scanner(System.in);
			
			System.out.print("성별조회(M/F):");
			String gender=sc.next().toUpperCase();
			
			System.out.print("급여(최소, 최대 순서로 작성):");
			int min=sc.nextInt();
			int max=sc.nextInt();
			
			System.out.print("급여 정렬(1.ASC 2.DESC):");
			int sort=sc.nextInt();
			
			String sql="""
			SELECT EMP_ID, EMP_NAME,
			DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F') GENDER,
			SALARY, JOB_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE
			FROM EMPLOYEE
			JOIN JOB USING(JOB_CODE)
			LEFT JOIN DEPARTMENT ON(DEPT_CODE=DEPT_ID)
			WHERE DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F') =?
			AND SALARY BETWEEN ? AND ?
			ORDER BY SALARY
					""";
			
			//*******ORDER BY 절에 ?(위치홀더) 사용시 오류:SQL명령어가 올바르게 종료안됨
			//왜? PreparedStatement의 위치홀더(?)는 데이터값(리터럴)을 대체하는 용도로만 사용가능
			//->SQL에서 ORDER BY 절의 정렬기준(ASC/DESC)과 같은
			//->SQL 구문(문법)의 일부는 PreparedStatement의 위치홀더(?)로 대체불가*******
			
			//급여의 오름, 내림차순인지 조건에 따라 SQL 보완하기
			if(sort==1) sql +="ASC";
			else		sql +="DESC";
			
			//4.PrepareStatement 객체생성
			pstmt=conn.prepareStatement(sql);
			
			//5.? 위치홀더에 알맞은 값 세팅
			pstmt.setString(1, gender);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
			
			//6.SQL 수행 후 결과 반환받기
			rs=pstmt.executeQuery();
			
			//7.커서 이용해 한행씩 접근해 컬럼값 얻어와 출력하기
			boolean flag=true; // true:조회결과없음, false:결과있음
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("------------------------------");
			
			while(rs.next()) {
				flag=false; //while문이 1회 이상 반복됨==조회결과가 1행이라고 있음
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String gen = rs.getString("GENDER"); //gender로 하면 위에서 변수선언해서 겹침
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				
				System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
							empId, empName, gen, salary, jobName, deptTitle);
			}
			if(flag) { //flag==true, while 안쪽수행x->조회결과 1행도 없음
				System.out.println("조회결과없음");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { //역순으로 닫쟈
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(sc != null) sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}