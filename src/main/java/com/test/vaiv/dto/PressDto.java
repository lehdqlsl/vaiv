package com.test.vaiv.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class PressDto {
    private LocalDate date;
    private String title;

    @QueryProjection
    public PressDto(LocalDate date, String title) {
        this.date = date;
        this.title = title;
    }
}
