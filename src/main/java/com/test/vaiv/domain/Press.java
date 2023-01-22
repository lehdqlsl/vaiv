package com.test.vaiv.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    public static Press toPress(String rawData) {
        String[] dataArr = rawData.split("\t");
        LocalDate lDate = LocalDate.parse(dataArr[0]);
        LocalTime lTime = LocalTime.parse(dataArr[1]);
        return new Press(lDate, lTime, dataArr[2], dataArr[3]);
    }

    public Press(LocalDate date, LocalTime time, String title, String speakers) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.speakers = parseSpeaker(speakers);
    }

    private List<Speaker> parseSpeaker(String speakers) {
        return Arrays.stream(speakers.split(","))
                .map(speaker -> Speaker.of(this, speaker))
                .collect(Collectors.toList());
    }
}
