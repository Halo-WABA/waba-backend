package com.halo.eventer.domain.concert.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertRegisterDto {

    private Long durationId;

    private String thumbnail;

    private List<String> images;
}
