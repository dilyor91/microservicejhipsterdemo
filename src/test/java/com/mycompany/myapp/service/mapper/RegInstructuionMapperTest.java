package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegInstructuionMapperTest {

    private RegInstructuionMapper regInstructuionMapper;

    @BeforeEach
    public void setUp() {
        regInstructuionMapper = new RegInstructuionMapperImpl();
    }
}
