package com.test.vaiv.repository;

import com.test.vaiv.domain.Press;
import com.test.vaiv.domain.Speaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PressTests {

    private final PressRepository pressRepository;
    private final SpeakerRepository speakerRepository;

    public PressTests(PressRepository pressRepository, SpeakerRepository speakerRepository) {
        this.pressRepository = pressRepository;
        this.speakerRepository = speakerRepository;
    }

    @BeforeEach
    public void init() {
        Press press = Press.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .title("제목")
                .build();


        List<Speaker> list = new ArrayList<>();

        Speaker speaker1 = Speaker.builder()
                .press(press)
                .name("홍길동")
                .party("국민의힘")
                .build();

        Speaker speaker2 = Speaker.builder()
                .press(press)
                .name("김영희")
                .party("더불어민주당")
                .build();

        list.add(speaker1);
        list.add(speaker2);

        press.setSpeakers(list);

        pressRepository.save(press);

    }

    @Test
    public void findPress() {
        Press press = pressRepository.findById(1L).get();
        Assertions.assertThat(press.getTitle()).isEqualTo("제목");
    }

    @Test
    public void countChild() {
        // 연관관계 맵핑 여부 확인
        Press press = pressRepository.findById(1L).get();
        Assertions.assertThat(press.getSpeakers().size()).isEqualTo(2);
    }

    @Test
    public void chkRelationship() {
        // 연관관계 맵핑 여부 확인
        Speaker speaker = speakerRepository.findById(2L).get();
        Assertions.assertThat(speaker.getPress().getId()).isEqualTo(1);
    }


}
