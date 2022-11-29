package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimetableMapperTest {

    private TimetableMapper timetableMapper;

    @BeforeEach
    public void setUp() {
        timetableMapper = new TimetableMapperImpl();
    }
}
