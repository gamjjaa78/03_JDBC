package edu.kh.jdbc.homework.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.homework.common.JDBCTemplate;
import edu.kh.jdbc.homework.model.dao.StudentDAO;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentService {
	private StudentDAO dao = new StudentDAO();

	public List<Student> selectAll() throws Exception {
		Connection conn = JDBCTemplate.getConnection();
		List<Student> stdList = dao.selectAll(conn);
		JDBCTemplate.close(conn);
		return stdList;
	}

	public int deleteUser(int input) throws Exception {
			Connection conn = JDBCTemplate.getConnection();
			int result = dao.delete(conn, input);
			if (result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
			JDBCTemplate.close(conn);
			return result;
	}

	public int insertStudent(Student std) {
		// TODO Auto-generated method stub
		return 0;
	}

}
