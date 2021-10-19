package com.test.vaiv.config;

import com.test.vaiv.repository.PressRepository;
import com.test.vaiv.service.PressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Profile("local")
@Slf4j
public class initSampleData {

    private final PressService pressService;
    private final PressRepository pressRepository;

    @Value("${data.file}")
    private String dataFile;

    @PostConstruct
    @Transactional
    public void init() throws IOException {

        // 빈 테이블인 경우, 데이터 삽입
        if(pressRepository.count()==0){
            File file = new File(dataFile);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                //파일 첫 줄 생략.
                bufferedReader.lines().skip(1)
                        .forEach(x -> {
                            pressService.save(x);
                        });
            }
        }
    }
}


