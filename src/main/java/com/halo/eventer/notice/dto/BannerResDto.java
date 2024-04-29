package com.halo.eventer.notice.dto;

import com.halo.eventer.notice.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BannerResDto {
    private Long id;
    private Integer rank;
    private String thumbnail;

    public BannerResDto(Notice n) {
        this.id = n.getId();
        this.thumbnail = n.getThumbnail();
        this.rank = n.getBannerRank();
    }
}
