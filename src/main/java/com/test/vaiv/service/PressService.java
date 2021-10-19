package com.test.vaiv.service;

import com.test.vaiv.domain.Press;
import com.test.vaiv.dto.PressDto;
import com.test.vaiv.dto.SearchDto;
import com.test.vaiv.repository.PressQueryRepository;
import com.test.vaiv.repository.PressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PressService {
    private final PressRepository pressRepository;
    private final PressQueryRepository pressQueryRepository;

    public Press save(String pressData) {
        // \t를 기준으로 문자열 split
        String[] dataArr = pressData.split("\t");
        String date = dataArr[0];
        String time = dataArr[1];
        String title = dataArr[2];
        String speakers = dataArr[3];

        try {
            LocalDate lDate = LocalDate.parse(date);
            LocalTime lTime = LocalTime.parse(time);
            return pressRepository.save(new Press(lDate, lTime, title, speakers));
        } catch (DateTimeParseException e) {
            log.error("Invalid format, Skip this data");
            return null;
        }
    }

    public Press save(Press pressData) {
        return pressRepository.save(pressData);
    }

    public List<Press> findByDate(LocalDate startDate, LocalDate endDate) {
        return pressRepository.findByDateIsBetween(startDate, endDate);
    }

    public List<PressDto> findPressByOption(SearchDto searchDto) {
        return pressQueryRepository.findPressByOption(searchDto);
    }

}
