package com.test.vaiv.service;


import com.test.vaiv.domain.Press;
import com.test.vaiv.dto.PressDto;
import com.test.vaiv.dto.SearchDto;
import com.test.vaiv.repository.PressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PressServiceImplTest {

    private final PressService pressService;
    private final PressRepository pressRepository;

    public PressServiceImplTest(PressService pressService, PressRepository pressRepository) {
        this.pressService = pressService;
        this.pressRepository = pressRepository;
    }

    @Test
    @Transactional
    public void save() {
        // given
        String data = "2014-11-05\t11:12\t김흥석 육군법무실장 엄중 문책 및 고등군사법원장 내정 철회 촉구 기자회견\t박혜자 의원(새정치민주연합), 남인순 의원(새정치민주연합), 서영교 의원(새정치민주연합), 신상진 전 국회의원, 홍익표 의원(새정치민주연합) 외";

        // when
        Press newPress = pressService.save(data);

        // then
        Press findPress = pressRepository.findById(newPress.getId()).get();

        Assertions.assertThat(newPress.getTitle()).isEqualTo(findPress.getTitle());
        Assertions.assertThat(newPress.getSpeakers().size()).isEqualTo(findPress.getSpeakers().size());
    }

    @Test
    @Transactional
    public void searchByDate(){

        // given
        LocalDate startDate = LocalDate.of(2006,1,1);
        LocalDate endDate = LocalDate.of(2007,1,1);

        // when
        List<Press> findList = pressService.findByDate(startDate,endDate);

        // then
        Assertions.assertThat(findList.size()).isEqualTo(685);

    }

    @Test
    @Transactional
    public void searchByDate2(){
        // given
        LocalDate startDate = LocalDate.of(2010,4,29);
        LocalDate endDate = LocalDate.of(2010,4,30);

        // when
        List<Press> findList = pressService.findByDate(startDate,endDate);

        // then
        Assertions.assertThat(findList.size()).isEqualTo(17);

    }

    @Test
    @Transactional
    public void searchByName(){
        // given
        SearchDto searchDto = new SearchDto();
        searchDto.setName("문재인");

        // when
        List<PressDto> findList = pressService.findPressByOption(searchDto);

        // then
        Assertions.assertThat(findList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void searchByParty(){
        // given
        SearchDto searchDto = new SearchDto();
        searchDto.setParty("국민의힘");

        // when
        List<PressDto> findList = pressService.findPressByOption(searchDto);

        // then
        Assertions.assertThat(findList.size()).isEqualTo(390);
    }

    @Test
    @Transactional
    public void searchByTotal(){
        //2021-01-20	13:58	세월호참사특별수사단 기무사 세월호 유가족 사찰 결과 발표 관련 기자회견	신원식 의원(국민의힘) 외
        // given
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDate(LocalDate.of(2021,1,20));
        searchDto.setEndDate(LocalDate.of(2021,1,20));
        searchDto.setParty("국민의힘");
        searchDto.setName("신원식");

        // when
        List<PressDto> findList = pressService.findPressByOption(searchDto);

        // then
        Assertions.assertThat(findList.size()).isEqualTo(1);
    }
}
