package com.halo.eventer.domain.up_widget.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.up_widget.UpWidget;
import com.halo.eventer.domain.up_widget.dto.UpWidgetCreateDto;
import com.halo.eventer.domain.up_widget.exception.UpWidgetNotFoundException;
import com.halo.eventer.domain.up_widget.repository.UpWidgetRepository;
import com.halo.eventer.global.common.response.SuccessCode;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpWidgetService {

  private final UpWidgetRepository upWidgetRepository;
  private final FestivalRepository festivalRepository;

  /** 상단 팝업 생성 */
  public SuccessCode createUpWidget(Long festivalId, UpWidgetCreateDto upWidgetCreateDto) {
    Festival festival = festivalRepository
            .findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    upWidgetRepository.save(new UpWidget(upWidgetCreateDto,festival));
    return SuccessCode.SAVE_SUCCESS;
  }

  /** 상단 팝업 리스트 조회 */
  public List<UpWidget> getUpWidgetList(Long festivalId) {
    Festival festival = festivalRepository
            .findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    return upWidgetRepository.findAllByFestival(festival);
  }

  /** 상단 팝업 리스트 단일 조회 */
  public UpWidget getUpWidget(Long id) {
    return upWidgetRepository
        .findById(id)
        .orElseThrow(() -> new UpWidgetNotFoundException(id));
  }

  /** 유저용 datetime으로 팝업 리스트 조회 */
  public List<UpWidget> getUpWidgetListByDateTime(Long festivalId, LocalDateTime dateTime) {
    Festival festival = festivalRepository
            .findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    return upWidgetRepository.findAllByFestivalWithDateTime(festival, dateTime);
  }

  /** 상단 위젯 수정 */
  @Transactional
  public UpWidget updateUpWidget(Long id, UpWidgetCreateDto widgetCreateDto) {
    UpWidget upWidget =
        upWidgetRepository
            .findById(id)
            .orElseThrow(() -> new UpWidgetNotFoundException(id));
    upWidget.update(widgetCreateDto);
    return upWidget;
  }

  /** 상단 위젯 삭제 */
  @Transactional
  public SuccessCode deleteUpWidget(Long upWidgetId) {
    upWidgetRepository.deleteById(upWidgetId);
    return SuccessCode.SAVE_SUCCESS;
  }
}
