package com.test.vaiv.config;

import com.test.vaiv.domain.Press;
import com.test.vaiv.repository.PressRepository;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("local")
@Slf4j
public class initSampleData {

    private final PressRepository pressRepository;

    @Value("${data.file}")
    private String dataFile;

    @PostConstruct
    @Transactional
    public void init() throws IOException {
        File file = new File(dataFile);
        if (!file.exists()) return;

        List<Press> presses = new BufferedReader(new FileReader(file))
                .lines()
                .skip(1)
                .map(data -> Press.toPress(data))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        pressRepository.saveAll(presses);
    }
}


