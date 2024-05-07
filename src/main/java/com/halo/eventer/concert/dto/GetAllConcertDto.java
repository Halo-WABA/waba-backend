package com.halo.eventer.concert.dto;

import com.halo.eventer.concert.Concert;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class GetAllConcertDto {
    private Long concertId;
    private String thumbnail;
    private Integer day;

    public GetAllConcertDto(Concert c) {
        this.concertId = c.getId();
        this.thumbnail = c.getThumbnail();
        this.day = c.getDuration().getDay();
    }
}
