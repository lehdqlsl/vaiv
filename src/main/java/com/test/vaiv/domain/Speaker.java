package com.test.vaiv.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "speaker_list")
public class Speaker {

    public static final Pattern PARTY_PATTERN = Pattern.compile("(.*)\\s의원\\((.*)\\).*");
    public static final Pattern ETC_PATTERN = Pattern.compile("(\\S*)\\s(.*)");
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Press press;

    private String name;

    private String party;

    @Builder
    public Speaker(Press press, String name, String party) {
        this.press = press;
        this.name = name;
        this.party = party;
    }

    public static Speaker of(Press press, String speaker) {
        Matcher matcher = PARTY_PATTERN.matcher(speaker.trim());
        Matcher etc_matcher = ETC_PATTERN.matcher(speaker.trim());

        String name = null;
        String party_name = null;

        if (matcher.find()) {
            name = matcher.group(1);
            party_name = matcher.group(2);
        }

        if (etc_matcher.find()) {
            name = etc_matcher.group(1);
            party_name = "기타";
        }

        return Speaker.builder()
                .name(name)
                .party(party_name)
                .press(press)
                .build();
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", press_id=" + press.getId() +
                ", name='" + name + '\'' +
                ", party='" + PARTY_PATTERN + '\'' +
                '}';
    }
}
