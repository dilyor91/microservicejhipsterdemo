package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegInstructuionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegInstructuionDTO.class);
        RegInstructuionDTO regInstructuionDTO1 = new RegInstructuionDTO();
        regInstructuionDTO1.setId(1L);
        RegInstructuionDTO regInstructuionDTO2 = new RegInstructuionDTO();
        assertThat(regInstructuionDTO1).isNotEqualTo(regInstructuionDTO2);
        regInstructuionDTO2.setId(regInstructuionDTO1.getId());
        assertThat(regInstructuionDTO1).isEqualTo(regInstructuionDTO2);
        regInstructuionDTO2.setId(2L);
        assertThat(regInstructuionDTO1).isNotEqualTo(regInstructuionDTO2);
        regInstructuionDTO1.setId(null);
        assertThat(regInstructuionDTO1).isNotEqualTo(regInstructuionDTO2);
    }
}
