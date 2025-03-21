package com.halo.eventer.domain.duration.service;


import com.halo.eventer.domain.duration.Duration;
import com.halo.eventer.domain.duration.dto.DurationCreateListDto;
import com.halo.eventer.domain.duration.dto.DurationGetDto;
import com.halo.eventer.domain.duration.dto.DurationGetListDto;
import com.halo.eventer.domain.duration.repository.DurationRepository;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.festival.service.FestivalService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DurationService {
    private final DurationRepository durationRepository;
    private final FestivalRepository festivalRepository;

    /** 축제 기간 등록 */
    @Transactional
    public String createDuration(Long festivalId, DurationCreateListDto durationCreateListDto) {
        Festival festival = festivalRepository
                .findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));

        durationCreateListDto.getDurationCreateDtos().stream().map(o->new Duration(o,festival)).forEach(durationRepository::save);

        return "기간 등록 완료";
    }

    /** 축제 기간 조회 */
    public DurationGetListDto getDurations(Long festivalId) {
        List<Duration> durationList = durationRepository.findAllByFestivalId(festivalId);
        List<DurationGetDto> durationGetDtoList = DurationGetDto.fromDurationList(durationList);
        return new DurationGetListDto(durationGetDtoList);
    }
}
