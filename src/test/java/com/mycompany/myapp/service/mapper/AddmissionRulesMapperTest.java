package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddmissionRulesMapperTest {

    private AddmissionRulesMapper addmissionRulesMapper;

    @BeforeEach
    public void setUp() {
        addmissionRulesMapper = new AddmissionRulesMapperImpl();
    }
}
