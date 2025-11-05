package edu.kh.jdbc.homework.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.homework.model.dto.Student;

import static edu.kh.jdbc.homework.common.JDBCTemplate.*;

public class StudentDAO {

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public List<Student> selectAll(Connection conn) throws Exception {
		List<Student> stdList = new ArrayList<Student>();
		try {
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR,
					TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM KH_STUDENT
					""";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String major = rs.getString("MAJOR");
				String enrollDate = rs.getString("ENROLL_DATE");
				Student std = new Student(stdNo, stdName, stdAge, major, enrollDate);
				stdList.add(std);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return stdList;
	}

	public int delete(Connection conn, int input) throws SQLException {
		int result = 0;
		try {
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NO=?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

}
