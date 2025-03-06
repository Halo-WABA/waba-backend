package com.halo.eventer.domain.notice.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.image.Image;
import com.halo.eventer.domain.image.ImageRepository;
import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.domain.notice.dto.*;
import com.halo.eventer.domain.notice.exception.NoticeNotFoundException;
import com.halo.eventer.domain.notice.repository.NoticeRepository;
import com.halo.eventer.global.common.ArticleType;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final ImageRepository imageRepository;

  private final FestivalRepository festivalRepository;

  /** 공지사항 등록 */
  @Transactional
  public String registerNotice(NoticeRegisterDto NoticeRegisterDto, Long festivalId) {
    Festival festival = festivalRepository.findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    Notice notice = new Notice(NoticeRegisterDto);
    notice.setFestival(festival);
    notice.setImages(
        NoticeRegisterDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
    noticeRepository.save(notice);
    return "저장 완료";
  }

  /** 공지사항 타입별로 조회 */
  @Transactional
  public NoticeInquireListDto inquireNoticeList(Long festivalId, ArticleType type) {
    Festival festival = festivalRepository.findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
    List<Notice> notices = noticeRepository.findAllByFestivalAndType(festival, type);

    return new NoticeInquireListDto(
        notices.stream().map(NoticeInquireDto::new).collect(Collectors.toList()));
  }

  /** 단일 공지사항 / 이벤트 조회하기 */
  @Transactional
  public Notice getNotice(Long id) {
    return noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFoundException(id));
  }

  /** 배너 등록, 해제 */
  @Transactional
  public String changeBanner(Long noticeId, Boolean pick) {
    Notice notice = getNotice(noticeId);
    notice.setPicked(pick);
    return "반영 완료";
  }

  /** 공지사항 수정 */
  @Transactional
  public String updateNotice(Long noticeId, NoticeRegisterDto NoticeRegisterDto) {
    Notice notice = getNotice(noticeId);

    imageRepository.deleteByIdIn(NoticeRegisterDto.getDeleteIds());
    notice.setAll(NoticeRegisterDto);
    notice.setImages(
        NoticeRegisterDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
    return "수정완료";
  }

  /** 공지사항 삭제 */
  @Transactional
  public String deleteNotice(Long noticeId) {
    Notice notice = getNotice(noticeId);
    noticeRepository.delete(notice);
    return "삭제완료";
  }

  /** 등록된 배너 전체 조회 */
  public RegisteredBannerGetListDto getRegisteredBanner(Long festivalId) {
    return new RegisteredBannerGetListDto(
        noticeRepository.findAllByPickedAndFestival_Id(true, festivalId).stream()
            .map(RegisteredBannerGetDto::new)
            .collect(Collectors.toList()));
  }

  /** 배너 순서 등록 */
  @Transactional
  public String editBannerRank(BannerEditListDto bannerEditListDto) {
    List<Notice> notices =
        noticeRepository.findAllById(
            bannerEditListDto.getBannerEditListDto().stream()
                .map(BannerEditDto::getNoticeId)
                .collect(Collectors.toList()));

    for (Notice notice : notices) {
      for (BannerEditDto b : bannerEditListDto.getBannerEditListDto()) {
        if (b.getNoticeId() == notice.getId()) {
          notice.setRank(b.getRank());
          break;
        }
      }
    }
    return "수정완료";
  }
}
