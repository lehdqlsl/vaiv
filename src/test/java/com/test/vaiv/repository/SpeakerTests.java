package com.test.vaiv.repository;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class SpeakerTests {

    private final SpeakerRepository speakerRepository;

    public SpeakerTests(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @BeforeEach
    public void init(){

    }

}
