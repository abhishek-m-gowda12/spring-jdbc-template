package com.abhishek.springjdbctemplate.repository.mapper;

import com.abhishek.springjdbctemplate.dto.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSetExtractor implements ResultSetExtractor<Student> {
    @Override
    public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
        Student student = null;
        if (rs.next()) {
            student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
        }

        return student;
    }
}
