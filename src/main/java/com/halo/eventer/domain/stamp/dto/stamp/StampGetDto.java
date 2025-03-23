package com.halo.eventer.domain.stamp.dto.stamp;

import com.halo.eventer.domain.stamp.Stamp;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StampGetDto {
    private Long stampId;
    private boolean stampOn;
    private Integer stampFinishCnt;

    public static List<StampGetDto> fromStampList(List<Stamp> stampList) {
        return stampList.stream()
                .map(StampGetDto::from)
                .collect(Collectors.toList());
    }

    public static StampGetDto from(Stamp stamp) {
        return new StampGetDto(
                stamp.getId(),
                stamp.isStampOn(),
                stamp.getStampFinishCnt()
        );
    }
}
