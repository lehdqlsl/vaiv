package com.test.vaiv.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "speaker_list")
public class Speaker {

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

    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", press_id=" + press.getId() +
                ", name='" + name + '\'' +
                ", party='" + party + '\'' +
                '}';
    }
}
