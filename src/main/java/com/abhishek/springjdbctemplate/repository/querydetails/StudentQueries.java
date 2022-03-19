package com.abhishek.springjdbctemplate.repository.querydetails;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:queries/queries.properties")
@Getter
public class StudentQueries {

    private final String findAll;

    private final String getById;

    private final String insert;

    public StudentQueries(@Value("${FIND_ALL_STUDENT}") String findAll,
                          @Value("${FIND_BY_ID}") String getById,
                          @Value("${INSERT}") String insert) {
        this.findAll = findAll;
        this.getById = getById;
        this.insert = insert;
    }
}
