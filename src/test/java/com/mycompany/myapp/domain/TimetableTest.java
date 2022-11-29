package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TimetableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timetable.class);
        Timetable timetable1 = new Timetable();
        timetable1.setId(1L);
        Timetable timetable2 = new Timetable();
        timetable2.setId(timetable1.getId());
        assertThat(timetable1).isEqualTo(timetable2);
        timetable2.setId(2L);
        assertThat(timetable1).isNotEqualTo(timetable2);
        timetable1.setId(null);
        assertThat(timetable1).isNotEqualTo(timetable2);
    }
}
