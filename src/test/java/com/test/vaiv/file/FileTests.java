package com.test.vaiv.file;

import com.test.vaiv.domain.Press;
import com.test.vaiv.domain.Speaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class FileTests {
    @Value("${data.file}")
    private String dataFile;

    @Test
    public void readFile() throws IOException {
        // Text 파일을 Line 단위로 읽을 수 있는 BufferedReader 사용
        File file = new File(dataFile);
        if (file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (bufferedReader.ready()) {
                System.out.println(bufferedReader.readLine());
            }
        }
    }

    @Test
    public void parseData() {
        String data = "2014-11-05\t11:12\t김흥석 육군법무실장 엄중 문책 및 고등군사법원장 내정 철회 촉구 기자회견\t박혜자 의원(새정치민주연합), 남인순 의원(새정치민주연합), 서영교 의원(새정치민주연합), 신상진 전 국회의원, 홍익표 의원(새정치민주연합) 외";

        List<Speaker> speakerList = new ArrayList<>();

        String[] dataArr = data.split("\t");

        String date = dataArr[0];
        String time = dataArr[1];
        String title = dataArr[2];

        // 기자회견 객체 생성
        Press press = Press.builder()
                .id(1)
                .date(LocalDate.parse(date))
                .time(LocalTime.parse(time))
                .title(title)
                .speakers(speakerList)
                .build();

        String speakers = dataArr[3];

        // 정규식 [% 의원(%)] 경우 추출.
        Pattern isParty = Pattern.compile("(.*)\\s의원\\((.*)\\).*");

        // 의원이 아닌경우, 첫번째 어절이 3글자 인 경우만 추출.
        Pattern etc = Pattern.compile("(...)\\s(.*)");

        for (String str : speakers.split(",")) {
            String name = null;
            String party_name = null;

            // 국회의원
            Matcher matcher = isParty.matcher(str.trim());
            Matcher etc_matcher = etc.matcher(str.trim());

            if (matcher.find()) {
                //이름, 정당명 추출
                name = matcher.group(1);
                party_name = matcher.group(2);
            } else {
                if (etc_matcher.find()) {
                    name = etc_matcher.group(1);
                    party_name = "기타";
                }
            }

            press.getSpeakers().add(Speaker.builder().name(name).party(party_name).press(press).build());
        }

        System.out.println("제목:"+press.getTitle());
        press.getSpeakers().forEach(x-> System.out.println("발언자:"+x));
    }
}
