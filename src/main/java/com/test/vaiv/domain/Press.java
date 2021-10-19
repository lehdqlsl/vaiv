package com.test.vaiv.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "press_info")
public class Press {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private LocalDate date;

    private LocalTime time;

    private String title;

    @OneToMany(mappedBy = "press", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Speaker> speakers;

    @Builder
    public Press(long id, LocalDate date, LocalTime time, String title, List<Speaker> speakers) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.speakers = speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }


    public Press(LocalDate date, LocalTime time, String title, String speakers) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.speakers = parseSpeaker(speakers);
    }

    private List<Speaker> parseSpeaker(String speakers) {
        List<Speaker> speakerList = new ArrayList<>();
        // 발언자 파싱
        // 정규식 [% 의원(%)] 경우 추출.
        Pattern isParty = Pattern.compile("(.*)\\s의원\\((.*)\\).*");

        // 의원이 아닌경우, 첫번째 어절이 3글자 인 경우만 추출.
        Pattern etc = Pattern.compile("(...)\\s(.*)");

        for (String str : speakers.split(",")) {
            String name = null;
            String party_name = null;

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

            speakerList.add(
                    Speaker.builder()
                            .name(name)
                            .party(party_name)
                            .press(this)
                            .build()
            );
        }

        return speakerList;
    }
}
