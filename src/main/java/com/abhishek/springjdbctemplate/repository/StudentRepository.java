package com.abhishek.springjdbctemplate.repository;

import com.abhishek.springjdbctemplate.dto.Student;
import com.abhishek.springjdbctemplate.repository.mapper.StudentMapper;
import com.abhishek.springjdbctemplate.repository.mapper.StudentResultSetExtractor;
import com.abhishek.springjdbctemplate.repository.querydetails.StudentQueries;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Component
public class StudentRepository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final StudentQueries queries;

    public StudentRepository(@Qualifier("applicationDataSource") DataSource dataSource, StudentQueries queries) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = initJdbcTemplate(jdbcTemplate);
        this.queries = queries;
    }

    private NamedParameterJdbcTemplate initJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public <RES> List<RES> executeQuery(MapSqlParameterSource mapSqlParameterSource, String queryName, RowMapper<RES> rowMapper) {
        String sqlQuery = getQuery(queryName);
        return namedParameterJdbcTemplate.query(sqlQuery, mapSqlParameterSource, rowMapper);
    }

    private String getQuery(String queryName) {
        String query = null;

        switch (queryName) {
            case "getAllStudent":
                query = queries.getFindAll();
                break;
            case "getById":
                query = queries.getGetById();
                break;
            case "insert":
                query = queries.getInsert();
                break;
            case "findStudentNameById":
                query = queries.getFindStudentNameById();
                break;
            default:
                break;
        }
        Assert.isTrue(query != null, "no sql query mapped for queryName " + queryName);

        return query;
    }

    public List<Student> getAllStudent(StudentMapper studentMapper) {
        String sqlQuery = getQuery("getAllStudent");
        return namedParameterJdbcTemplate.query(sqlQuery, studentMapper);
    }

    public void addStudent(Student student) {
        String sqlQuery = getQuery("insert");
        jdbcTemplate.update(sqlQuery, preparedStatement -> {
            preparedStatement.setString(1, student.getName());
        });
    }

    public void addAllStudents(List<Student> students) {
        String sqlQuery = getQuery("insert");
        jdbcTemplate.batchUpdate(sqlQuery, students, 10,
                (ps, student) -> {
                    ps.setString(1, student.getName());
                });
    }

    public Student getStudentById(String id) {
        String sqlQuery = getQuery("getById");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id, Types.INTEGER);

        return namedParameterJdbcTemplate.query(sqlQuery, mapSqlParameterSource, new StudentResultSetExtractor());
    }

    public String getStudentNameById(String id) {
        String sqlQuery = getQuery("findStudentNameById");
        return jdbcTemplate.queryForObject(sqlQuery, String.class, Long.valueOf(id));
    }
}
